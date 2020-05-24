package com.ult1team.slimebuddies.SlimeBuddies.Utils;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.function.Supplier;

public class DeferredRegistryClone<T extends IForgeRegistryEntry.Impl> {
	public static ArrayList<RegistryObject> registryObjects=new ArrayList<>();
	
	private String modid;
	
	public DeferredRegistryClone(String modid) {
		this.modid = modid;
	}
	
	public RegistryObject<T> register(String registryName, Supplier<T> object) {
		RegistryObject<T> object1=new RegistryObject<T>(modid,registryName,object.get());
		registryObjects.add(object1);
		return object1;
	}
}
