package com.ult1team.slimebuddies.SlimeBuddies;
import com.ult1team.slimebuddies.SlimeBuddies.EventSubscribers.ItemUsed;
import com.ult1team.slimebuddies.SlimeBuddies.Items.ModeledItemBase;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Entities;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Items;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.DeferredRegistryClone;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.RegistryObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = SlimeBuddies.MOD_ID,
		name = SlimeBuddies.MOD_NAME,
		version = SlimeBuddies.VERSION
)
@Mod.EventBusSubscriber
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
		Items.registerAll();
		Entities.registerAll();
		MinecraftForge.EVENT_BUS.register(ItemUsed.class);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (RegistryObject<Object> item:DeferredRegistryClone.registryObjects) {
			if (item.get() instanceof Item) {
				event.getRegistry().register((Item)item.get());
			}
		}
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (RegistryObject<Object> item:DeferredRegistryClone.registryObjects) {
			if (item.get() instanceof Block) {
				event.getRegistry().register((Block)item.get());
			}
		}
	}
	
	@SubscribeEvent
	public void registerEntity(RegistryEvent.Register<EntityEntry> event) {
		for (RegistryObject<Object> item:DeferredRegistryClone.registryObjects) {
			if (item.get() instanceof EntityEntry) {
				event.getRegistry().register((EntityEntry)item.get());
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		for (RegistryObject<? extends Object> item:DeferredRegistryClone.registryObjects) {
			if (item.get() instanceof ModeledItemBase) {
				((ModeledItemBase)item.get()).registerModels(event);
			}
		}
	}
}
