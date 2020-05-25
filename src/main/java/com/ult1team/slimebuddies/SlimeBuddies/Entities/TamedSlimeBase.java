package com.ult1team.slimebuddies.SlimeBuddies.Entities;

import com.ult1team.slimebuddies.SlimeBuddies.SlimeBuddies;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TamedSlimeBase extends EntitySlime {
	private static final DataParameter<Integer> POTION_COLOR = EntityDataManager.<Integer>createKey(TamedSlimeBase.class, DataSerializers.VARINT);
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
		compound.setInteger("Color",color);
	}
	
	int color=new Color(255,255,255).getRGB();
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("ownerLUUID")&&compound.hasKey("ownerMUUID")) {
			owner=new UUID(compound.getLong("ownerLUUID"),compound.getLong("ownerMUUID"));
		}
		if (compound.hasKey("Color")) {
			color=compound.getInteger("Color");
		}
	}
	
	public int getColor() {
		return this.dataManager.get(POTION_COLOR);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (!world.isRemote) {
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
			Color color = new Color(255,255,255);
			for (PotionEffect potionEffect:this.getActivePotionEffects()) {
				for (int i=0;i<potionEffect.getAmplifier()+1;i++) {
					color=new Color(potionEffect.getPotion().getLiquidColor()).average(color);
				}
			}
//			SlimeBuddies.log.log(Level.INFO,color.getRGB());
			NBTTagCompound compound=this.serializeNBT();
			compound.setInteger("Color",color.getRGB());
			this.readFromNBT(compound);
			this.setColor(color.getRGB());
		}
 	}
	
	@Override
	protected void updatePotionEffects() {
//		super.updatePotionEffects();
	}
	
	@Override
	protected void onFinishedPotionEffect(PotionEffect effect) {
//		super.onFinishedPotionEffect(effect);
	}
	
	@Nullable
	@Override
	public PotionEffect removeActivePotionEffect(@Nullable Potion potioneffectin) {
		return this.getActivePotionMap().remove(potioneffectin);
	}
	
	@Override
	public void removePotionEffect(Potion potionIn) {
		super.removePotionEffect(potionIn);
	}
	
	@Override
	protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
//		super.onChangedPotionEffect(id, p_70695_2_);
	}
	
	@Override
	public boolean canBeHitWithPotion() {
		return true;
	}
	
	@Override
	public void addPotionEffect(PotionEffect potioneffectIn) {
		super.addPotionEffect(potioneffectIn);
	}
	
	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		return true;
	}
	
	@Override
	public boolean isPotionActive(Potion potionIn) {
		return false;
	}
	
	@Override
	public void travel(float strafe, float vertical, float forward) {
		super.travel(strafe,vertical,forward);
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if (!player.world.isRemote) {
			if (player.isSneaking()) {
				for (PotionEffect effect:this.getActivePotionEffects()) {
					player.addPotionEffect(effect);
				}
				this.clearActivePotions();
				this.serializeNBT();
				this.markPotionsDirty();
			}
		}
		return super.applyPlayerInteraction(player, vec, hand);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(POTION_COLOR, Integer.valueOf(new Color(255,255,255).getRGB()));
	}
	
	@Override
	public void setDead() {
		super.setDead();
	}
	
	@Override
	protected void setSlimeSize(int size, boolean resetHealth) {
		super.setSlimeSize(size, resetHealth);
	}
	
	public void setSlimeSizeS(int size, boolean resetHealth) {
		this.setSlimeSize(size,resetHealth);
	}
	
	public void setColor(int color)
	{
		this.dataManager.set(POTION_COLOR, Integer.valueOf(color));
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
