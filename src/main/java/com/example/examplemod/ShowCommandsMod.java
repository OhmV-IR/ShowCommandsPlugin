package com.example.examplemod;

import com.mojang.brigadier.ParseResults;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraft.commands.CommandSourceStack;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ShowCommandsMod.MODID)
public class ShowCommandsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "showcommandsmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public ShowCommandsMod()
    {

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Show commands server mod started successfully!");
    }
    @SubscribeEvent
    public void onCommandReceived(CommandEvent cmd){
        try{
        LOGGER.info("received command");
        ParseResults<CommandSourceStack> results = cmd.getParseResults();
        LOGGER.info("Player " + CommandSourceToPlayerName(results.getContext().getSource().source.toString()) + 
        " executed " + ClassNameToCommandName(results.getContext().getCommand().getClass().getSimpleName()) + 
        " with " + "x" + " arguments");
        }
        catch(Exception e){

        }
    }
    private String ClassNameToCommandName(String className){
        String[] dividedStr = className.split("\\$");
        return dividedStr[0];
    }
    private String CommandSourceToPlayerName(String source){
        String[] dividedStr = source.split("['']");
        return dividedStr[1];
    }
}