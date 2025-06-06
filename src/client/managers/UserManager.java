package client.managers;

import common.User;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер пользователей. Отвечает за управление пользователями и их аутентификацию.
 */
public class UserManager {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> activeUsers = new ConcurrentHashMap<>(); // sessionId -> username
    private final String usersFile;

    public UserManager(String usersFile) {
        this.usersFile = usersFile;
        loadUsers();
    }

    /**
     * Загружает пользователей из файла.
     */
    private void loadUsers() {
        File file = new File(usersFile);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, User> loadedUsers = (Map<String, User>) ois.readObject();
            users.putAll(loadedUsers);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке пользователей: " + e.getMessage());
        }
    }

    /**
     * Сохраняет пользователей в файл.
     */
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении пользователей: " + e.getMessage());
        }
    }

    /**
     * Создает нового пользователя.
     *
     * @param username имя пользователя
     * @param password пароль
     * @return true если пользователь успешно создан, false если пользователь уже существует
     */
    public boolean createUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        saveUsers();
        return true;
    }

    /**
     * Аутентифицирует пользователя.
     *
     * @param username имя пользователя
     * @param password пароль
     * @param sessionId идентификатор сессии
     * @return true если аутентификация успешна, false в противном случае
     */
    public boolean authenticate(String username, String password, String sessionId) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            activeUsers.put(sessionId, username);
            return true;
        }
        return false;
    }

    /**
     * Завершает сессию пользователя.
     *
     * @param sessionId идентификатор сессии
     */
    public void logout(String sessionId) {
        activeUsers.remove(sessionId);
    }

    /**
     * Проверяет, аутентифицирован ли пользователь.
     *
     * @param sessionId идентификатор сессии
     * @return true если пользователь аутентифицирован, false в противном случае
     */
    public boolean isAuthenticated(String sessionId) {
        return activeUsers.containsKey(sessionId);
    }

    /**
     * Получает имя пользователя по идентификатору сессии.
     *
     * @param sessionId идентификатор сессии
     * @return имя пользователя или null, если сессия не найдена
     */
    public String getUsernameBySessionId(String sessionId) {
        return activeUsers.get(sessionId);
    }
}