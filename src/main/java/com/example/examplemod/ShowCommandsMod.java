package com.example.examplemod;

import com.mojang.brigadier.ParseResults;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ShowCommandsMod.MODID)
public class ShowCommandsMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "showcommandsmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static MinecraftServer SERVER;
    // Create a Deferred Register to hold Blocks which will all be registered under
    // the "examplemod" namespace

    public ShowCommandsMod() {

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab

        // Register our mod's ForgeConfigSpec so that Forge can create and load the
        // config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartedEvent event) {
        // Do something when the server starts
        LOGGER.info("Show commands server mod started successfully!");
        SERVER = event.getServer();
    }

    @SubscribeEvent
    public void onCommandReceived(CommandEvent cmd) {
        try {
            LOGGER.info("received command");
            ParseResults<CommandSourceStack> results = cmd.getParseResults();
            String playerName = CommandSourceToPlayerName(results.getContext().getSource().source.toString());
            String commandName = ClassNameToCommandName(results.getContext().getCommand().getClass().getSimpleName());
            LOGGER.info("Player " + playerName + " executed " + commandName+ " with " + "x" + " arguments");
            PlayerList plist = SERVER.getPlayerList();
            for (ServerPlayer player : plist.getPlayers()) {
                player.sendSystemMessage(Component.literal("Player " + playerName + " executed " + commandName+ " with " + "x" + " arguments"));
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

    }

    private String ClassNameToCommandName(String className) {
        String[] dividedStr = className.split("\\$");
        return dividedStr[0];
    }

    private String CommandSourceToPlayerName(String source) {
        String[] dividedStr = source.split("['']");
        try{
        return dividedStr[1];
        }
        catch(Exception e){
            return dividedStr[0];
        }
    }
}
