package com.ult1team.slimebuddies.SlimeBuddies.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.UUID;

public class TamedSlimeBase extends EntitySlime {
	public TamedSlimeBase(World worldIn) {
		super(worldIn);
	}
	
	public UUID owner=null;
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if (owner!=null) {
			compound.setLong("ownerLUUID", owner.getLeastSignificantBits());
			compound.setLong("ownerMUUID", owner.getMostSignificantBits());
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("ownerLUUID")&&compound.hasKey("ownerMUUID")) {
			owner=new UUID(compound.getLong("ownerLUUID"),compound.getLong("ownerMUUID"));
		}
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (this.owner==null) {
			Entity player=world.getNearestAttackablePlayer(this,20,20);
			if (player!=null) {
				owner=player.getUniqueID();
			}
		}
		if (this.owner!=null) {
			EntityLivingBase ownerPlayer=this.world.getPlayerEntityByUUID(this.owner);
			if (ownerPlayer!=null) {
				if (ownerPlayer.getDistanceSq(this)>=30*(this.getSlimeSize()+1)) {
					if (this.onGround) {
						this.jumpHelper.doJump();
						this.setMoveForward(1);
						this.setAttackTarget(ownerPlayer);
						this.faceEntity(ownerPlayer,20,0);
						if (ownerPlayer.getDistanceSq(this)>=120*(this.getSlimeSize()+1)) {
							this.setPositionAndUpdate(ownerPlayer.posX,ownerPlayer.posY,ownerPlayer.posZ);
						}
					}
				}
				if (ownerPlayer.getDistanceSq(this)<=30*(this.getSlimeSize()+1)) {
					this.setAttackTarget(null);
				}
			}
		}
	}
	
	@Override
	public void setDead() {
		super.setDead();
//		int i = this.getSlimeSize();
//
//		if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0F)
//		{
//			int j = 2 + this.rand.nextInt(3);
//
//			for (int k = 0; k < j; ++k)
//			{
//				float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
//				float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
//				TamedSlime entityslime = this.createInstance();
//
//				if (this.hasCustomName())
//				{
//					entityslime.setCustomNameTag(this.getCustomNameTag());
//				}
//
//				if (this.isNoDespawnRequired())
//				{
//					entityslime.enablePersistence();
//				}
//
//				entityslime.setSlimeSizeS(i / 2, true);
//				entityslime.setLocationAndAngles(this.posX + (double)f, this.posY + 0.5D, this.posZ + (double)f1, this.rand.nextFloat() * 360.0F, 0.0F);
//				this.world.spawnEntity(entityslime);
//			}
//		}
//
//		this.isDead = true;
	}
	
	@Override
	protected void setSlimeSize(int size, boolean resetHealth) {
		super.setSlimeSize(size, resetHealth);
	}
	
	public void setSlimeSizeS(int size, boolean resetHealth) {
		this.setSlimeSize(size,resetHealth);
	}
	
	@Override
	protected void dealDamage(EntityLivingBase entityIn) {
		int i = this.getSlimeSize();
		
		boolean isOwner=false;
		if (this.owner!=null) {
			isOwner=entityIn.getUniqueID().equals(owner);
		}
		if ((this.canEntityBeSeen(entityIn) && this.getDistanceSq(entityIn) < 0.6D * (double)i * 0.6D * (double)i && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength()))&&!isOwner)
		{
			this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.applyEnchantments(this, entityIn);
		}
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn)
	{
		boolean isOwner=false;
		if (this.owner!=null) {
			isOwner=entityIn.getUniqueID().equals(owner);
		}
		if (!isOwner&&this.canDamagePlayer())
		{
			this.dealDamage(entityIn);
		}
	}
	
	@Nullable
	@Override
	public EntityLivingBase getAttackTarget() {
		if (this.owner!=null) {
			EntityLivingBase ownerPlayer = this.world.getPlayerEntityByUUID(this.owner);
			if (ownerPlayer != null) {
				if (ownerPlayer.getDistanceSq(this) <= 30 * (this.getSlimeSize() + 1)) {
					return null;
				}
			}
		}
		return super.getAttackTarget();
	}
	
	@Override
	protected void outOfWorld() {
		super.outOfWorld();
		if (this.owner!=null) {
			EntityLivingBase ownerPlayer=this.world.getPlayerEntityByUUID(this.owner);
			if (ownerPlayer!=null) {
				this.setPositionAndUpdate(ownerPlayer.posX,ownerPlayer.posY,ownerPlayer.posZ);
			}
		}
	}
}
