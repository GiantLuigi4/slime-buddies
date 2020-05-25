package com.ult1team.slimebuddies.SlimeBuddies.Entities;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

public class TamedSlimeRenderer extends RenderLiving<TamedSlimeBase>
{
	private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("textures/entity/slime/slime.png");

	public TamedSlimeRenderer(RenderManager p_i47193_1_)
	{
		super(p_i47193_1_, new ModelSlime(16), 0.25F);
		this.addLayer(new SlimeGelLayer(this));
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(TamedSlimeBase entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		Color col=new Color(entity.getColor());
//		SlimeBuddies.log.log(Level.INFO,col.getRGB());
//		SlimeBuddies.log.log(Level.INFO,entity.serializeNBT());
		GlStateManager.color(col.getRed()/255f,col.getGreen()/255f,col.getBlue()/255f);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	protected void preRenderCallback(TamedSlimeBase entitylivingbaseIn, float partialTickTime)
	{
		float f = 0.999F;
		GlStateManager.scale(0.999F, 0.999F, 0.999F);
		float f1 = (float)entitylivingbaseIn.getSlimeSize();
		float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(TamedSlimeBase entity) {
		return SLIME_TEXTURES;
	}
}