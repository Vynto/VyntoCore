package me.vynto.core.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookTabCompleter implements TabCompleter {
    List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("title");
            arguments.add("author");
            arguments.add("copy");
        }

        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (String argument: arguments) {
                if (argument.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(argument);
                }
            }
            return result;
        }

        return null;
    }
}
