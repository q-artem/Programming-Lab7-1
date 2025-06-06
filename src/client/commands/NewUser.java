package client.commands;

import common.Command;
import common.utility.Console;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;

/**
 * Команда 'new_user'. Регистрирует нового пользователя.
 */
public class NewUser extends Command implements Executable, Describable {
    private final Console console;

    public NewUser(Console console) {
        super("new_user <username> <password>", "зарегистрировать нового пользователя");
        this.console = console;
    }

    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 3) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        String username = arguments[1];
        String password = arguments[2];

        return new ExecutionResponse(true, "new_user " + username + " " + password);
    }
} 