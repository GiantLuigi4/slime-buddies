package com.ult1team.slimebuddies.SlimeBuddies.Registry;

import com.ult1team.slimebuddies.SlimeBuddies.Entities.TamedSlime;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.DeferredRegistryClone;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.RegistryObject;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.awt.*;

//TODO: remove references to java.awt
public class Entities {
	public static final DeferredRegistryClone<EntityEntry> ENTITIES = new DeferredRegistryClone<EntityEntry>(SlimeBuddies.MOD_ID);
	
	public static final RegistryObject<EntityEntry> SLIME = ENTITIES.register("tamedslime",()->EntityEntryBuilder.create().entity(TamedSlime.class).egg(new Color(0,148,0).getRGB(),new Color(42,123,19).getRGB()).name("tamedslime").id("tamedslime",0).tracker(80,3,true).build());
	
	public static void registerAll() {}
}
