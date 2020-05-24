package com.ult1team.slimebuddies.SlimeBuddies.EventSubscribers;

import com.ult1team.slimebuddies.SlimeBuddies.Items.EntityHolder;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Items;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.List;

@Mod.EventBusSubscriber
public class ItemUsed {
	@SubscribeEvent
	public static void onItemUsed(PlayerInteractEvent.EntityInteract event) {
		Entity target=event.getTarget();
		EntityPlayer player=event.getEntityPlayer();
		if (canItemHoldEntity(event.getItemStack(),target)) {
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
	
	public static boolean canItemHoldEntity(ItemStack stack,Entity entity) {
		if (entity instanceof EntitySlime) {
			if (stack.getItem().getRegistryName().equals(Blocks.CAULDRON.getRegistryName())) {
				return true;
			}
			if (stack.getItem() instanceof ItemBucket) {
				if (entity.serializeNBT().getInteger("size")<=1) {
					return true;
				}
			}
			if (stack.getItem() instanceof ItemGlassBottle||stack.getItem().getRegistryName().equals(net.minecraft.init.Items.LEATHER_CHESTPLATE.getRegistryName())) {
				if (entity.serializeNBT().getInteger("size")<=0) {
					return true;
				}
			}
		}
		return false;
	}
}
