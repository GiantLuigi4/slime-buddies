package com.ult1team.slimebuddies.SlimeBuddies.Registry;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;

public class Fluids {
	public static ArrayList<BlockFluidClassic> fluids = new ArrayList<>();
	
	public static Block registerFluid(String modid, String name,int luminosity,int density,int viscosity,boolean floats) {
		Fluid f=new Fluid(name, new ResourceLocation(modid+":blocks/"+name+"_still"), new ResourceLocation(modid+":blocks/"+name+"_flowing")).setLuminosity(luminosity).setDensity(density).setViscosity(viscosity).setGaseous(floats);
		FluidRegistry.addBucketForFluid(f);
		fluids.add((BlockFluidClassic)new BlockFluidClassic(f, Material.WATER) {}.setTranslationKey(name));
		return fluids.get(fluids.size()-1);
	}
}
