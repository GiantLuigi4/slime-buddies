package com.ult1team.slimebuddies.SlimeBuddies.Items;

import com.ult1team.slimebuddies.SlimeBuddies.Entities.TamedSlimeBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.EnumHand;

public class SlimeRetamer extends ModeledItemBase {
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		if (!playerIn.getEntityWorld().isRemote) {
			if (target instanceof TamedSlimeBase) {
				NBTTagCompound compound=target.serializeNBT();
				compound.setLong("ownerLUUID",playerIn.getUniqueID().getLeastSignificantBits());
				compound.setLong("ownerMUUID",playerIn.getUniqueID().getMostSignificantBits());
				target.readEntityFromNBT(compound);
				target.deserializeNBT(compound);
				((TamedSlimeBase)target).owner=playerIn.getUniqueID();
			}
		}
		return true;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return "slime retamer";
	}
}
