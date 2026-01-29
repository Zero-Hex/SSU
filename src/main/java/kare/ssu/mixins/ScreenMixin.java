package kare.ssu.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import kare.ssu.client.RecipeQueryClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class ScreenMixin {
    @Shadow @Nullable protected Slot hoveredSlot;

    @Shadow public abstract void onClose();

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void keyPressed(KeyInput keyInput, CallbackInfoReturnable<Boolean> cir) {
        InputConstants.Key pressedKey = InputConstants.Type.KEYSYM.getOrCreate(keyInput.key());
        if (KeyBindingHelper.getBoundKeyOf(RecipeQueryClient.queryKey).equals(pressedKey) && hoveredSlot != null) {
            this.onClose();
            RecipeQueryClient.onRecipeQueryKeyPressed(hoveredSlot);
        } else if (KeyBindingHelper.getBoundKeyOf(RecipeQueryClient.enchantedKey).equals(pressedKey) && hoveredSlot != null) {
            this.onClose();
            RecipeQueryClient.onViewEnchanted(hoveredSlot);
        }
    }


}