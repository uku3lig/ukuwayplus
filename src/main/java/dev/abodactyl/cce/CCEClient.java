package dev.abodactyl.cce;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class CCEClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

    }

    public static boolean connected() {
        return MinecraftClient.getInstance().getServer().getServerIp().contains("clubchunk.com");
    }


}
