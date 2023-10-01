package net.uku3lig.ukuway.jukebox;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.uku3lig.ukuway.HideawayPlus;
import net.uku3lig.ukuway.Prompt;
import net.uku3lig.ukuway.lifecycle.Task;

public class Jukebox {
    public long trackPointer = -1;
    public int partPointer = -1;
    public JukeboxTrack currentTrack = null;
    public JukeboxTrack.Part currentPart = null;

    public boolean looping = false;

    public Jukebox() {
        HideawayPlus.lifecycle().add(
                Task.of(() -> {
                    if (!looping && HideawayPlus.connected() & HideawayPlus.jukebox() != null
                            && currentTrack != null && currentPart != null) {
                        if (trackPointer >= currentPart.length) loop();
                        else trackPointer++;
                    }
                }, 0)
        );
    }

    public void play(JukeboxTrack track) {
        MinecraftClient.getInstance().getSoundManager().stopAll();
        currentTrack = track;
        currentPart = track.parts.get(0);
        trackPointer = 0;
        partPointer = 0;
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.playSound(
                    SoundEvent.of(currentTrack.parts.get(partPointer).id), SoundCategory.RECORDS, 1, 1
            );
        }
    }

    private void loop() {
        looping = true;
        JukeboxTrack temp = currentTrack;
        stop();
        currentTrack = temp;

        partPointer++;
        trackPointer = 0;

        try {
            currentPart = currentTrack.parts.get(partPointer);
        } catch (Exception e) {
            partPointer = 0;
            currentPart = currentTrack.parts.get(partPointer);
        }
        try {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.playSound(
                        SoundEvent.of(currentTrack.parts.get(partPointer).id), SoundCategory.RECORDS, 1, 1
                );
            }
        } catch (Exception e) {
            Prompt.error("An issue occurred when looping music with the Jukebox. Please send your latest.log file to the developers of Hideaway+.");
        }
        looping = false;
    }

    public void stop() {
        MinecraftClient.getInstance().getSoundManager().stopAll();
        currentTrack = null;
        currentPart = null;
        trackPointer = -1;
        partPointer = -1;
    }
}
