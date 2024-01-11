package baby.creed.mixins.forge;

import baby.creed.Creed;
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
        if (Creed.customBrand.isToggled())
        {
            cir.setReturnValue(Creed.customBrand.brand.getValue());
        }
    }
}
