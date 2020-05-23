package com.ult1team.slimebuddies.SlimeBuddies;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = SlimeBuddies.MOD_ID,
		name = SlimeBuddies.MOD_NAME,
		version = SlimeBuddies.VERSION
)
public class SlimeBuddies {
	public static final String MOD_ID = "slime_buddies";
	public static final String MOD_NAME = "Slime Buddies";
	public static final String VERSION = "0.1";
	public static Logger log;
	@Mod.Instance(MOD_ID)
	public static SlimeBuddies INSTANCE;
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		log=event.getModLog();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
	}
}
