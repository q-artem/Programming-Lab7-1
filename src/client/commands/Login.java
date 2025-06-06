package client.commands;

import client.managers.CollectionManager;
import common.Command;
import common.utility.Console;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;

/**
 * Команда 'login'. Авторизует пользователя в системе.
 */
public class Login extends Command implements Executable, Describable {
    private final Console console;
    private final CollectionManager collectionManager;

    public Login(Console console, CollectionManager collectionManager) {
        super("login <username> <password>", "войти в систему");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 3) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        String username = arguments[1];
        String password = arguments[2];

        return new ExecutionResponse(true, "login " + username + " " + password);
    }
} 