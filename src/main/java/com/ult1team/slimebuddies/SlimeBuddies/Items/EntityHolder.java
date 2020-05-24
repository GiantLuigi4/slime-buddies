package com.ult1team.slimebuddies.SlimeBuddies.Items;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.apache.logging.log4j.Level;

import java.util.UUID;

public class EntityHolder extends ModeledItemBase {
	
	public EntityHolder() {
		super();
		this.setTileEntityItemStackRenderer(new EntityHolderRenderer());
		SlimeBuddies.log.log(Level.INFO,"Set TEISR for entity holder");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		try {
			NBTTagCompound compound=stack.getTagCompound();
			ItemStack container=new ItemStack(Item.getByNameOrId(compound.getString("item")));
			container.setTagCompound(compound.getCompoundTag("item_tags"));
			NBTTagCompound compound1=compound.getCompoundTag("entity");
			String name=compound1.getString("id");
			name=EntityList.getTranslationName(new ResourceLocation(name));
			if (compound1.hasKey("CustomName")) {
				name="\""+compound1.getString("CustomName")+"\"";
			}
			return container.getDisplayName()+" of "+name;
		} catch (Exception err) {
			return super.getItemStackDisplayName(stack);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			ItemStack stack=player.getHeldItem(hand);
			NBTTagCompound compound=stack.getTagCompound();
			try {
				Entity entity=AnvilChunkLoader.readWorldEntityPos((NBTTagCompound)compound.getTag("entity"), worldIn, pos.getX()+hitX, pos.getY()+hitY, pos.getZ()+hitZ, false);
				entity.setUniqueId(UUID.randomUUID());
				worldIn.spawnEntity(entity);
				ItemStack newStack=new ItemStack(Item.getByNameOrId(compound.getString("item")));
				newStack.setTagCompound(stack.getTagCompound().getCompoundTag("item_tags"));
				player.setHeldItem(hand,newStack);
			} catch (Exception err) {}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	public static ItemStack getStackForEntity(ItemStack sourceItem, Entity entity, String id) {
		ItemStack stack=new ItemStack(Item.getByNameOrId(id));
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("entity",entity.serializeNBT());
		stack.getTagCompound().setString("item",sourceItem.getItem().getRegistryName().toString());
		if (sourceItem.hasTagCompound()) {
			stack.getTagCompound().setTag("item_tags",sourceItem.getTagCompound());
		}
		return stack;
	}
}

