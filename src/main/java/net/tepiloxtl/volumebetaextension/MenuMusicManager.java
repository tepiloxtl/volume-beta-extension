package net.tepiloxtl.volumebetaextension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundManager;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class MenuMusicManager {
    private static final List<String> VOLUME_BETA_TRACKS = Arrays.asList(
            "moog_city_2",
            "beginning_2",
            "floating_trees",
            "mutation"
    );

    private static final Random RANDOM = new Random();
    private static boolean initialized = false;
    private static final Map<String, URL> customMusicUrls = new HashMap<>();

    public static void init() {
        if (initialized) return;
        initialized = true;

        // Load our custom music files
        loadCustomMusic();

        VolumeBetaExtension.LOGGER.info("Loaded {} Volume Beta tracks", customMusicUrls.size());
    }

    private static void loadCustomMusic() {
        for (String track : VOLUME_BETA_TRACKS) {
            try {
                // Try to load from mod resources
                URL musicUrl = MenuMusicManager.class.getResource(
                        "/assets/volumebetaextension/music/" + track + ".ogg"
                );

                if (musicUrl != null) {
                    customMusicUrls.put(track, musicUrl);
                    VolumeBetaExtension.LOGGER.info("Loaded track: {}", track);
                } else {
                    VolumeBetaExtension.LOGGER.warn("Could not find track: {}", track);
                }
            } catch (Exception e) {
                VolumeBetaExtension.LOGGER.error("Error loading track " + track, e);
            }
        }
    }

    public static String getRandomMenuTrack() {
        // Mix vanilla and Volume Beta tracks
        List<String> allTracks = new ArrayList<>();

        // Add vanilla menu tracks (hal1-4)

        // Add our Volume Beta tracks
        allTracks.addAll(VOLUME_BETA_TRACKS);

        return allTracks.get(RANDOM.nextInt(allTracks.size()));
    }

    public static boolean isCustomTrack(String trackName) {
        return VOLUME_BETA_TRACKS.contains(trackName);
    }

    public static URL getCustomTrackUrl(String trackName) {
        return customMusicUrls.get(trackName);
    }
}