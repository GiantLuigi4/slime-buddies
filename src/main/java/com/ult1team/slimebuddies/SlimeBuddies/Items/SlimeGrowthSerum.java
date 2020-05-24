package com.ult1team.slimebuddies.SlimeBuddies.Items;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import org.apache.logging.log4j.Level;

public class SlimeGrowthSerum extends ModeledItemBase {
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        int currentsize = target.serializeNBT().getInteger("Size");
        if (target instanceof EntitySlime) {
            if (!playerIn.world.isRemote) {
                if (currentsize < 4) {
                    NBTTagCompound compound=target.serializeNBT();
                    compound.setInteger("Size",currentsize+1);
                    target.deserializeNBT(compound);
                }
            }
            return true;
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
