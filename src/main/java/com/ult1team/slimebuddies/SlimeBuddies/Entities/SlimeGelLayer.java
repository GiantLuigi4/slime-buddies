package com.ult1team.slimebuddies.SlimeBuddies.Entities;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.apache.logging.log4j.Level;

public class SlimeGelLayer implements LayerRenderer<TamedSlimeBase>
{
	private final TamedSlimeRenderer slimeRenderer;
	private final ModelBase slimeModel = new ModelSlime(0);
	
	public SlimeGelLayer(TamedSlimeRenderer slimeRendererIn)
	{
		this.slimeRenderer = slimeRendererIn;
	}
	
	public void doRenderLayer(TamedSlimeBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (!entitylivingbaseIn.isInvisible())
		{
			Color col=new Color(entitylivingbaseIn.getColor());
			GlStateManager.color(col.getRed()/255f,col.getGreen()/255f,col.getBlue()/255f);
			GlStateManager.enableNormalize();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
			this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlend();
			GlStateManager.disableNormalize();
		}
	}
	
	public boolean shouldCombineTextures()
	{
		return true;
	}
}