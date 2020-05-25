package com.ult1team.slimebuddies.SlimeBuddies.Registry;

import com.ult1team.slimebuddies.SlimeBuddies.Items.SlimeGrowthSerum;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.DeferredRegistryClone;
import com.ult1team.slimebuddies.SlimeBuddies.Utils.RegistryObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class Blocks {
	public static final DeferredRegistryClone<Block> BLOCKS = new DeferredRegistryClone<Block>(SlimeBuddies.MOD_ID);
	
	public static final RegistryObject<Block> SUGAR_WATER = BLOCKS.register("sugar_water", ()->Fluids.registerFluid(SlimeBuddies.MOD_ID,"sugar_water",0,1020,3000,false));
	
	public static void registerAll() {}
}
