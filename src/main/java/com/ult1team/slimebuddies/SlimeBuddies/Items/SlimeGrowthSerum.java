package com.ult1team.slimebuddies.SlimeBuddies.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class SlimeGrowthSerum extends ModeledItemBase {
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        int currentsize = target.serializeNBT().getInteger("Size");
    if (target instanceof EntitySlime) {
        if (currentsize < 4) target.serializeNBT().setInteger("Size",currentsize+1);
        return true;
    }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
