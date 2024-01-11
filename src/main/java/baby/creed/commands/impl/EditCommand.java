package baby.creed.commands.impl;

import baby.creed.commands.Command;

public class EditCommand extends Command {
    public EditCommand()
    {
        super("edit", "move");
    }
    @Override
    public void execute(String[] args) throws Exception {

    }

    @Override
    public String getDescription() {
        return "Shows things";
    }
}
