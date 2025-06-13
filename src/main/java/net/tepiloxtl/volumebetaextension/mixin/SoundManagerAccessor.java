package net.tepiloxtl.volumebetaextension.mixin;

import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import paulscode.sound.SoundSystem;

@Mixin(SoundManager.class)
public interface SoundManagerAccessor {
    @Accessor("soundSystem")
    static SoundSystem getSoundSystem() {
        throw new AssertionError();
    }

    @Accessor("started")
    static boolean isStarted() {
        throw new AssertionError();
    }
}