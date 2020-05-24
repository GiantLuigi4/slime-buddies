package com.ult1team.slimebuddies.SlimeBuddies.Utils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryObject<T> {
	private T value;
	protected RegistryObject(String modid, String registryName,T object) {
		try {
			value=(T)((IForgeRegistryEntry.Impl)object).setRegistryName(new ResourceLocation(modid,registryName));
		} catch (Exception err) {
			value=(T)object;
		}
	}
	public T get() {
		return (T)(((IForgeRegistryEntry.Impl)value).delegate.get());
	}
}
