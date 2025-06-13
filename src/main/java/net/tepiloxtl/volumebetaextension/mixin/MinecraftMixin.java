package net.tepiloxtl.volumebetaextension.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.tepiloxtl.volumebetaextension.VolumeBetaExtension;
import net.tepiloxtl.volumebetaextension.mixininterface.ISoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

@Mixin(value = Minecraft.class)
public class MinecraftMixin
{
    @Shadow
    public SoundManager soundManager;

    @Inject(method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/player/PlayerEntity;)V"
            , at = @At("HEAD"))
    private void changeWorld(World world, String loadingTitle, PlayerEntity player, CallbackInfo ci)
    {
        VolumeBetaExtension.LOGGER.info("test");
        ((ISoundManager)soundManager).stopBgMusic();
    }
}