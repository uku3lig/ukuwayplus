package plus.hideaway.mod.feat.jukebox;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.Prompt;
import plus.hideaway.mod.feat.lifecycle.Task;

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
        HideawayPlus.client().getSoundManager().stop();
        currentTrack = track;
        currentPart = track.parts.get(0);
        trackPointer = 0;
        partPointer = 0;
        assert HideawayPlus.client().player != null;
        HideawayPlus.client().player.playNotifySound(
                SoundEvent.createVariableRangeEvent(currentTrack.parts.get(partPointer).id), SoundSource.MASTER, 1, 1
        );
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
            assert HideawayPlus.client().player != null;
            HideawayPlus.client().player.playNotifySound(
                    SoundEvent.createVariableRangeEvent(currentTrack.parts.get(partPointer).id), SoundSource.MASTER, 1, 1
            );
        } catch (Exception e) {
            Prompt.error("An issue occurred when looping music with the Jukebox. Please send your latest.log file to the developers of Hideaway+.");
        }
        looping = false;
    }

    public void stop() {
        HideawayPlus.client().getSoundManager().stop();
        currentTrack = null;
        currentPart = null;
        trackPointer = -1;
        partPointer = -1;
    }
}
