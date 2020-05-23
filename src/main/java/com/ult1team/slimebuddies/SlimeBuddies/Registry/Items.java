package com.ult1team.slimebuddies.SlimeBuddies.Registry;

import com.ult1team.slimebuddies.SlimeBuddies.Items.EntityHolder;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.DeferredRegistryClone;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.RegistryObject;
import net.minecraft.item.Item;

public class Items {
	public static final DeferredRegistryClone<Item> ITEMS = new DeferredRegistryClone<Item>(SlimeBuddies.MOD_ID);
	
	public static final RegistryObject<Item> ENTITY_HOLDER = ITEMS.register("entity_holder",EntityHolder::new);
	
	public static void registerAll() {
		ENTITY_HOLDER.get();
	}
}
