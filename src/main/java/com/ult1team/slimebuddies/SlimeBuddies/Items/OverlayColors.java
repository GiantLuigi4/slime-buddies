package com.ult1team.slimebuddies.SlimeBuddies.Items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class OverlayColors implements IItemColor {
	public static int tint=0;
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return tint;
	}
}
