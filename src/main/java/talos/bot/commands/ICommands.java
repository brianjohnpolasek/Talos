package talos.bot.commands;

import java.util.List;

public interface ICommands {
    void handle(CommandsContext commandsContext);

    String getName();

    default List<String> getAliases() {
        return List.of();
    }
}
