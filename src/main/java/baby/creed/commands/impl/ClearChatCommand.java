package baby.creed.commands.impl;

import baby.creed.Creed;
import baby.creed.commands.Command;
import baby.creed.util.ModUtils;

public class ClearChatCommand extends Command {
    public ClearChatCommand()
    {
        super("clear");
    }
    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 2) {
            ModUtils.sendMessage("Invalid command!");
            return;
        }

        for(int i = 0; i < Integer.parseInt(args[1]); i++) {
            Creed.sendMessage("");
        }
    }

    @Override
    public String getDescription() {
        return "Clears chat, .clear <lines>";
    }
}
