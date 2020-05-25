package com.ult1team.slimebuddies.SlimeBuddies.EventSubscribers;

import com.ult1team.slimebuddies.SlimeBuddies.Entities.TamedSlimeBase;
import com.ult1team.slimebuddies.SlimeBuddies.Items.EntityHolder;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemUsed {
	@SubscribeEvent
	public static void onItemUsed(PlayerInteractEvent.EntityInteract event) {
		Entity target=event.getTarget();
		EntityPlayer player=event.getEntityPlayer();
		if (canItemHoldEntity(event.getItemStack(),target,event.getEntityPlayer())) {
			if (event.getTarget().isEntityAlive()) {
				if (player.getHeldItem(EnumHand.MAIN_HAND).equals(ItemStack.EMPTY)||event.getHand().equals(EnumHand.MAIN_HAND)) {
					ItemStack stack=(EntityHolder.getStackForEntity(event.getItemStack(),target,(Items.ENTITY_HOLDER.get()).getRegistryName().toString()));
					player.setHeldItem(event.getHand(),stack);
					target.onRemovedFromWorld();
					target.setDead();
				}
			}
		}
	}
	
	public static boolean canItemHoldEntity(ItemStack stack,Entity entity,EntityPlayer player) {
		boolean canRemotelyBottle=true;
		if (entity instanceof TamedSlimeBase) {
			UUID owner=null;
			NBTTagCompound compound=entity.serializeNBT();
			if (compound.hasKey("ownerLUUID")&&compound.hasKey("ownerMUUID")) {
				owner=new UUID(compound.getLong("ownerLUUID"),compound.getLong("ownerMUUID"));
			}
			if (owner!=null) {
				if (!owner.equals(player.getUniqueID())) {
					canRemotelyBottle=false;
				}
			}
		}
		if (player.isCreative()) {
			canRemotelyBottle=true;
		}
		if (canRemotelyBottle) {
			if (entity instanceof EntitySlime) {
				if (stack.getItem().getRegistryName().equals(Blocks.CAULDRON.getRegistryName())) {
					return true;
				}
				if (stack.getItem() instanceof ItemBucket||stack.getItem().getRegistryName().equals(new ResourceLocation("forge","bucketfilled"))) {
					if (entity.serializeNBT().getInteger("Size")<=1) {
						return true;
					}
				}
				if (stack.getItem() instanceof ItemGlassBottle||stack.getItem().getRegistryName().equals(net.minecraft.init.Items.LEATHER_CHESTPLATE.getRegistryName())) {
					if (entity.serializeNBT().getInteger("Size")<=0) {
						return true;
					}
				}
				if (stack.getItem() instanceof ItemPotion||stack.getItem() instanceof ItemLingeringPotion) {
					if (entity.serializeNBT().getInteger("Size")<=0) {
						if (entity instanceof TamedSlimeBase) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
