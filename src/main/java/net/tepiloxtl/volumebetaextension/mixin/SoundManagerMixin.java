package net.tepiloxtl.volumebetaextension.mixin;

import net.tepiloxtl.volumebetaextension.VolumeBetaExtension;
import net.tepiloxtl.volumebetaextension.MenuMusicManager;
import net.tepiloxtl.volumebetaextension.mixininterface.ISoundManager;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

import java.io.File;
import java.net.URL;

@Mixin(SoundManager.class)
public class SoundManagerMixin implements ISoundManager {
    @Shadow
    private static SoundSystem soundSystem;

    @Shadow
    private static boolean started;

    @Inject(method = "playSound(Ljava/lang/String;FF)V", at = @At("HEAD"), cancellable = true)
    private void onPlayMusic(String music, float volume, float pitch, CallbackInfo ci) {

        if (!started || soundSystem == null) return;

        // Check if this is one of our custom tracks
        String trackName = music.replace("music.", "");
        if (MenuMusicManager.isCustomTrack(trackName)) {
            URL trackUrl = MenuMusicManager.getCustomTrackUrl(trackName);

            if (trackUrl != null) {
                try {
                    // Stop any current music
                    soundSystem.stop("BgMusic");

                    // Play our custom track
                    soundSystem.newStreamingSource(
                            false,           // priority
                            "BgMusic",       // sourcename
                            trackUrl,        // url
                            trackName + ".ogg", // identifier
                            true,            // toLoop
                            0, 0, 0,        // x, y, z position
                            0,              // attModel
                            0               // rolloff
                    );

                    soundSystem.setVolume("BgMusic", volume);
                    soundSystem.play("BgMusic");

                    VolumeBetaExtension.LOGGER.info("Playing Volume Beta track: {}", trackName);

                    // Cancel the original method
                    ci.cancel();
                } catch (Exception e) {
                    VolumeBetaExtension.LOGGER.error("Failed to play custom track: " + trackName, e);
                }
            }
        }
    }

    public void stopBgMusic()
    {
        soundSystem.fadeOut("BgMusic", null, 2000);
    }
}