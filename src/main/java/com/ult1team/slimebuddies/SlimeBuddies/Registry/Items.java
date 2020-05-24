package com.ult1team.slimebuddies.SlimeBuddies.Registry;

import com.ult1team.slimebuddies.SlimeBuddies.Items.EntityHolder;
import com.ult1team.slimebuddies.SlimeBuddies.Items.SlimeOverlay;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.DeferredRegistryClone;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.RegistryObject;
import net.minecraft.item.Item;

public class Items {
	public static final DeferredRegistryClone<Item> ITEMS = new DeferredRegistryClone<Item>(SlimeBuddies.MOD_ID);
	
	public static final RegistryObject<Item> ENTITY_HOLDER = ITEMS.register("entity_holder",EntityHolder::new);
	public static final RegistryObject<Item> SLIME_OVERLAY_BUCKET = ITEMS.register("slime_overlay_bucket",SlimeOverlay::new);
	public static final RegistryObject<Item> MAGMA_OVERLAY_BUCKET = ITEMS.register("magma_slime_overlay_bucket",SlimeOverlay::new);
	public static final RegistryObject<Item> SLIME_OVERLAY_BOTTLE = ITEMS.register("slime_overlay_bottle",SlimeOverlay::new);
	public static final RegistryObject<Item> MAGMA_OVERLAY_BOTTLE = ITEMS.register("magma_slime_overlay_bottle",SlimeOverlay::new);
	public static final RegistryObject<Item> SLIME_OVERLAY_CAULDRON = ITEMS.register("slime_overlay_cauldron",SlimeOverlay::new);
	public static final RegistryObject<Item> MAGMA_OVERLAY_CAULDRON = ITEMS.register("magma_slime_overlay_cauldron",SlimeOverlay::new);
	
	public static void registerAll() {}
}
