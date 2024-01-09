package net.kore.commands.impl;

import net.kore.Kore;
import net.kore.commands.Command;
import net.kore.modules.render.InventoryDisplay;
import net.kore.ui.DragGui;

public class EditCommand extends Command {
    public EditCommand()
    {
        super("edit", "move");
    }
    @Override
    public void execute(String[] args) throws Exception {
        Kore.mc.displayGuiScreen(new DragGui());
    }

    @Override
    public String getDescription() {
        return "Shows things";
    }
}
