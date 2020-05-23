package com.ult1team.slimebuddies.SlimeBuddies.EventSubscribers;

import com.ult1team.slimebuddies.SlimeBuddies.Items.EntityHolder;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Items;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
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
		if (target instanceof EntitySlime) {
			if (event.getItemStack().getItem() instanceof ItemBucket) {
				if (event.getTarget().isEntityAlive()) {
					if (player.getHeldItem(EnumHand.MAIN_HAND).equals(ItemStack.EMPTY)||event.getHand().equals(EnumHand.MAIN_HAND)) {
						ItemStack stack=((EntityHolder)Items.ENTITY_HOLDER.get()).getStackForEntity(event.getItemStack(),target,((EntityHolder) Items.ENTITY_HOLDER.get()).getRegistryName().toString());
						player.setHeldItem(event.getHand(),stack);
						target.onRemovedFromWorld();
						target.setDead();
					}
				}
			}
		}
	}
}
