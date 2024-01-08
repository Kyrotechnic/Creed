package net.kore.modules.render;

import net.kore.Kore;
import net.kore.modules.Module;

public class NoFallingBlocks extends Module {
    public NoFallingBlocks()
    {
        super("No Falling Blocks", Category.RENDER);
    }

    @Override
    public void assign()
    {
        Kore.noFallingBlocks = this;
    }
}
