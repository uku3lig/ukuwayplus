package net.uku3lig.ukuway.jukebox;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.uku3lig.ukuway.UkuwayPlus;

public class Jukebox implements ClientPlayerTickable {
    private long ticksElapsed = -1;
    private int partPointer = -1;
    @Getter
    private JukeboxTrack currentTrack = null;
    private JukeboxTrack.Part currentPart = null;

    public void play(JukeboxTrack track) {
        this.stop();
        this.currentTrack = track;
        this.currentPart = track.getParts().get(0);

        this.playCurrentPart();
    }

    private void loop() {
        this.ticksElapsed = 0;
        this.partPointer++;

        this.currentPart = currentTrack.getParts().get(partPointer % currentTrack.getParts().size());

        this.playCurrentPart();
    }

    public void stop() {
        MinecraftClient.getInstance().getSoundManager().stopAll();
        this.currentTrack = null;
        this.currentPart = null;
        this.ticksElapsed = 0;
        this.partPointer = 0;
    }

    private void playCurrentPart() {
        try {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.playSound(SoundEvent.of(this.currentPart.id()), SoundCategory.RECORDS, 1, 1);
            }
        } catch (Exception e) {
            UkuwayPlus.getLogger().error("An issue occurred when looping music with the Jukebox.", e);
        }
    }

    @Override
    public void tick() {
        if (UkuwayPlus.isConnected() && this.currentTrack != null && this.currentPart != null) {
            if (this.ticksElapsed >= this.currentPart.length()) loop();
            else this.ticksElapsed++;
        }
    }
}
