package net.kore.mixins.forge;

import net.kore.Kore;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FMLCommonHandler.class)
public class MixinFMLCommonHandler {
    @Inject(method = "getModName", at = @At("HEAD"), cancellable = true, remap = false)
    public void getModName(CallbackInfoReturnable<String> cir)
    {
        if (Kore.customBrand.isToggled())
        {
            cir.setReturnValue(Kore.customBrand.brand.getValue());
        }
    }
}
