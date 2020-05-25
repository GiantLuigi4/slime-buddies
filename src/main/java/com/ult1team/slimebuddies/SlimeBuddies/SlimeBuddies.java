package com.ult1team.slimebuddies.SlimeBuddies;
import com.ult1team.slimebuddies.SlimeBuddies.Entities.TamedSlimeBase;
import com.ult1team.slimebuddies.SlimeBuddies.Entities.TamedSlimeRenderer;
import com.ult1team.slimebuddies.SlimeBuddies.EventSubscribers.ItemUsed;
import com.ult1team.slimebuddies.SlimeBuddies.Items.ModeledItemBase;
import com.ult1team.slimebuddies.SlimeBuddies.Items.OverlayColors;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Blocks;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Entities;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Items;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.DeferredRegistryClone;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.RegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
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
		Items.registerAll();
		Entities.registerAll();
		Blocks.registerAll();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(ItemUsed.class);
		if (event.getSide().isClient()) {
			setupClient();
		}
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new OverlayColors(),
				Items.SLIME_OVERLAY_BOTTLE.get(),
				Items.SLIME_OVERLAY_BUCKET.get(),
				Items.SLIME_OVERLAY_CAULDRON.get()
		);
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
		for (RegistryObject<Object> item:DeferredRegistryClone.registryObjects) {
			if (item.get() instanceof ModeledItemBase) {
				((ModeledItemBase)item.get()).registerModels(event);
			}
		}
		ModelLoader.setCustomStateMapper(Blocks.SUGAR_WATER.get(), new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(MOD_ID+":sugar_water", "sugar_water");
			}
		});
	}
	
	@SideOnly(Side.CLIENT)
	public static void setupClient() {
		RenderingRegistry.registerEntityRenderingHandler(TamedSlimeBase.class, TamedSlimeRenderer::new);
	}
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
}
