package com.ult1team.slimebuddies.SlimeBuddies.Items;

import com.ult1team.slimebuddies.SlimeBuddies.Entities.Color;
import com.ult1team.slimebuddies.SlimeBuddies.Registry.Items;
import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

public class EntityHolderRenderer extends TileEntityItemStackRenderer {
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5f,0.5f,0.5f);
		String item=itemStackIn.getTagCompound().getString("item");
		ItemStack renderStack=new ItemStack(Item.getByNameOrId(item));
		NBTTagCompound compound=itemStackIn.getTagCompound();
		if (itemStackIn.getTagCompound().hasKey("item_tags")) {
			renderStack.setTagCompound(itemStackIn.getTagCompound().getCompoundTag("item_tags"));
		}
		if (renderStack.getItem() instanceof ItemGlassBottle) {
			renderStack=new ItemStack(net.minecraft.init.Items.POTIONITEM);
			NBTTagCompound compound2=new NBTTagCompound();
			compound2.setInteger("CustomPotionColor",new Color(255,255,255).getRGB());
			renderStack.setTagCompound(compound2);
			OverlayColors.tint=16777215;
			Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.NONE);
		} else {
			try {
				if ((compound.getCompoundTag("entity")).hasKey("Color")) {
					Color col=new Color(compound.getCompoundTag("entity").getInteger("Color"));
					if (renderStack.getItem() instanceof ItemPotion) {
						col=new Color(renderStack.getTagCompound().getInteger("CustomPotionColor")).average(col);
						renderStack=new ItemStack(net.minecraft.init.Items.POTIONITEM);
						NBTTagCompound compound2=new NBTTagCompound();
						compound2.setInteger("CustomPotionColor",new Color(255,255,255).getRGB());
						renderStack.setTagCompound(compound2);
					}
					col=new Color(
							col.getRed(),
							col.getGreen(),
							col.getBlue()
							);
					OverlayColors.tint=col.getRGB();
				} else {
					OverlayColors.tint=new Color(255,255,255).getRGB();
				}
			} catch (Exception err) {
			}
			Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.NONE);
		}
		if (renderStack.getItem() instanceof ItemGlassBottle||renderStack.getItem() instanceof ItemPotion) {
			ItemStack renderStack2=new ItemStack(Items.SLIME_OVERLAY_BOTTLE.get());
			if (compound.getCompoundTag("entity").getString("id").equals("minecraft:magma_cube")) {
				renderStack2 = new ItemStack(Items.MAGMA_OVERLAY_BOTTLE.get());
			}
			Minecraft.getMinecraft().getRenderItem().renderItem(renderStack2, ItemCameraTransforms.TransformType.NONE);
		} else if (renderStack.getItem().getRegistryName().equals(Blocks.CAULDRON.getRegistryName())) {
			GlStateManager.scale(1.001f,1.001f,1.001f);
			ItemStack renderStack2=new ItemStack(Items.SLIME_OVERLAY_CAULDRON.get());
			if (compound.getCompoundTag("entity").getString("id").equals("minecraft:magma_cube")) {
				renderStack2 = new ItemStack(Items.MAGMA_OVERLAY_CAULDRON.get());
			}
			Minecraft.getMinecraft().getRenderItem().renderItem(renderStack2, ItemCameraTransforms.TransformType.NONE);
		} else {
			GlStateManager.scale(1.001f,1.001f,1.001f);
			ItemStack renderStack2=new ItemStack(Items.SLIME_OVERLAY_BUCKET.get());
			if (renderStack.getItem().getRegistryName().equals(new ResourceLocation("forge","bucketfilled"))) {
				GlStateManager.scale(1f,1f,1.008f);
			}
			if (compound.getCompoundTag("entity").getString("id").equals("minecraft:magma_cube")) {
				renderStack2 = new ItemStack(Items.MAGMA_OVERLAY_BUCKET.get());
			}
			Minecraft.getMinecraft().getRenderItem().renderItem(renderStack2, ItemCameraTransforms.TransformType.NONE);
		}
		GlStateManager.popMatrix();
	}
	
	public EntityHolderRenderer() {
		super();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn, float partialTicks) {
		renderByItem(itemStackIn);
	}
}
