package me.basiqueevangelist.palettebypass.mixin;

import me.basiqueevangelist.palettebypass.PaletteBypass;
import me.basiqueevangelist.palettebypass.UnknownBlockState;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.UpgradeData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(UpgradeData.class)
public class UpgradeDataMixin {
    @Redirect(method = "upgradeCenter", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/PalettedContainer;get(I)Ljava/lang/Object;"))
    private Object transformUnknownIfNeeded(PalettedContainer<Object> palettedContainer, int index) {
        Object o = ((PalettedContainerAccessor) palettedContainer).callGet(index);
        if (o instanceof UnknownBlockState)
            return PaletteBypass.getForUnknown((UnknownBlockState) o);
        return o;
    }
}
