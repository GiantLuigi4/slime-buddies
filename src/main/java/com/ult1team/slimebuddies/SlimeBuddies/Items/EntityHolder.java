package com.ult1team.slimebuddies.SlimeBuddies.Items;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.apache.logging.log4j.Level;

import java.util.UUID;

public class EntityHolder extends Item {
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack stack=player.getHeldItem(hand);
			NBTTagCompound compound=stack.getTagCompound();
			try {
				Entity entity=AnvilChunkLoader.readWorldEntityPos((NBTTagCompound)compound.getTag("entity"), worldIn, pos.getX()+hitX, pos.getY()+hitY, pos.getZ()+hitZ, false);
				entity.setUniqueId(UUID.randomUUID());
				worldIn.spawnEntity(entity);
				player.setHeldItem(hand,new ItemStack(Item.getByNameOrId(compound.getString("item"))));
			} catch (Exception err) {}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	public ItemStack getStackForEntity(ItemStack sourceItem, Entity entity, String id) {
		ItemStack stack=new ItemStack(Item.getByNameOrId(id));
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("entity",entity.serializeNBT());
		stack.getTagCompound().setString("item",sourceItem.getItem().getRegistryName().toString());
		return stack;
	}
}
