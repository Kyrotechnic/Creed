package baby.creed.commands.impl;

import baby.creed.Creed;
import baby.creed.commands.Command;
import baby.creed.util.ModUtils;

public class SetCoinsCommand extends Command {
    public SetCoinsCommand()
    {
        super("coins", "setcoins");
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length < 2)
        {
            ModUtils.sendMessage("Invalid command!");
            return;
        }
        double value = Long.parseLong(args[1]);
        
        Creed.purseSpoofer.additionalCoins.setValue(value);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
