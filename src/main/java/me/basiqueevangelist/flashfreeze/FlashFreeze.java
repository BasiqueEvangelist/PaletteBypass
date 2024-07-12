package me.basiqueevangelist.flashfreeze;

import eu.pb4.polymer.networking.api.server.PolymerServerNetworking;
import me.basiqueevangelist.flashfreeze.block.UnknownBlockBlock;
import me.basiqueevangelist.flashfreeze.command.LookupCommand;
import me.basiqueevangelist.flashfreeze.item.FlashFreezeDataComponents;
import me.basiqueevangelist.flashfreeze.item.UnknownItemItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.nbt.NbtInt;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class FlashFreeze implements ModInitializer {
    public static final String MODID = "flashfreeze";

    public static WeakReference<MinecraftServer> SERVER;

    public static final UnknownBlockBlock UNKNOWN_BLOCK = new UnknownBlockBlock();
    public static final UnknownItemItem UNKNOWN_ITEM = new UnknownItemItem();

    public static final int NETWORK_VERISON = 1;
    public static final Identifier NETWORK_VERSION_ID = FlashFreeze.id("network_version");

    public void onInitialize() {
        LoggerFactory.getLogger("FlashFreeze").info("Flash freezing content since 2021");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FlashFreeze.SERVER = new WeakReference<>(server);
        });

        CommandRegistrationCallback.EVENT.register(LookupCommand::register);

        FlashFreezeDataComponents.init();

        Registry.register(Registries.BLOCK, id("unknown_block"), UNKNOWN_BLOCK);
        Registry.register(Registries.ITEM, id("unknown_item"), UNKNOWN_ITEM);
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }

    public static boolean hasAtLeast(ServerPlayerEntity player, int networkVersion) {
        NbtInt v = PolymerServerNetworking.getMetadata(player.networkHandler, NETWORK_VERSION_ID, NbtInt.TYPE);

        if (v == null) return false;

        return v.intValue() >= networkVersion;
    }
}
