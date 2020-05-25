package com.ult1team.slimebuddies.SlimeBuddies.Utils;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class DeferredRegistryClone<T extends IForgeRegistryEntry.Impl> {
	public static ArrayList<RegistryObject> registryObjects=new ArrayList<>();
	public static HashMap<BlockFluidClassic, Block> fluids=new HashMap<>();
	
	private String modid;
	
	public DeferredRegistryClone(String modid) {
		this.modid = modid;
	}
	
	public RegistryObject<T> register(String registryName, Supplier<T> object) {
		RegistryObject<T> object1=new RegistryObject<T>(modid,registryName,object.get());
		registryObjects.add(object1);
		if (object instanceof BlockFluidClassic) {
			fluids.put((BlockFluidClassic)object,(Block)object1.get());
		}
		return object1;
	}
	
	public static Block getBlockForFluid(BlockFluidClassic fluidClassic) {
		return fluids.get(fluidClassic);
	}
}
