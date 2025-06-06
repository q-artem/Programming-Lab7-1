package client.commands;

import common.Command;
import common.utility.Console;
import common.utility.Describable;
import common.utility.Executable;
import common.utility.ExecutionResponse;

/**
 * Команда 'logout'. Выходит из системы.
 */
public class Logout extends Command implements Executable, Describable {
    private final Console console;

    public Logout(Console console) {
        super("logout", "выйти из системы");
        this.console = console;
    }

    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        return new ExecutionResponse(true, "logout");
    }
} 