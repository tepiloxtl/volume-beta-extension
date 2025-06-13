package net.tepiloxtl.volumebetaextension.mixin;

import net.tepiloxtl.volumebetaextension.VolumeBetaExtension;
import net.tepiloxtl.volumebetaextension.MenuMusicManager;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    // Track music timing
    private static long musicStartTime = 0;
    private static boolean waitingToPlay = false;
    private static final Random RANDOM = new Random();
    private static final int MIN_WAIT_TIME = 1000; // 1 second
    private static final int MAX_WAIT_TIME = 30000; // 30 seconds
    private static int waitTime = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onUpdateScreen(CallbackInfo ci) {
        // Check if we should play menu music
        if (waitingToPlay) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - musicStartTime >= waitTime) {
                waitingToPlay = false;

                // Get a random track (vanilla or Volume Beta)
                String track = MenuMusicManager.getRandomMenuTrack();

                VolumeBetaExtension.LOGGER.info("Playing menu track: {}", track);

                // Play the track
                if (MenuMusicManager.isCustomTrack(track)) {
                    // Play our custom Volume Beta track
                    // Option 1: If there's a playMusic method
                    this.minecraft.soundManager.playSound("music." + track, 1.0F, 1.0F);

                    // Option 2: If playMusic doesn't exist, try playSound
                    // mc.soundManager.playSound("music." + track, 1.0F, 1.0F);
                } else {
                    // Play vanilla music
                    this.minecraft.soundManager.playSound(track, 1.0F, 1.0F);
                }
            }
        }
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void onInitGui(CallbackInfo ci) {
        // Reset music timer when menu opens
        musicStartTime = System.currentTimeMillis();
        waitingToPlay = true;
        // Random wait time between 1-30 seconds like vanilla
        waitTime = MIN_WAIT_TIME + RANDOM.nextInt(MAX_WAIT_TIME - MIN_WAIT_TIME);

        VolumeBetaExtension.LOGGER.info("Menu opened, waiting {} ms before playing music", waitTime);
    }
}