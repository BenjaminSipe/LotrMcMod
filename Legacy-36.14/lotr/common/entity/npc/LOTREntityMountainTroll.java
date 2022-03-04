package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityMountainTroll extends LOTREntityTroll {
   public static IAttribute thrownRockDamage = (new RangedAttribute("lotr.thrownRockDamage", 5.0D, 0.0D, 100.0D)).func_111117_a("LOTR Thrown Rock Damage");
   private EntityAIBase rangedAttackAI = this.getTrollRangedAttackAI();
   private EntityAIBase meleeAttackAI;
   private boolean canDropTrollTotem = true;

   public LOTREntityMountainTroll(World world) {
      super(world);
   }

   public float getTrollScale() {
      return 1.6F;
   }

   public EntityAIBase getTrollAttackAI() {
      return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.8D, false);
   }

   protected EntityAIBase getTrollRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.2D, 30, 60, 24.0F);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(21, (byte)0);
   }

   public boolean isThrowingRocks() {
      return this.field_70180_af.func_75683_a(21) == 1;
   }

   public void setThrowingRocks(boolean flag) {
      this.field_70180_af.func_75692_b(21, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(70.0D);
      this.func_110148_a(npcAttackDamage).func_111128_a(7.0D);
      this.func_110140_aT().func_111150_b(thrownRockDamage);
   }

   protected boolean hasTrollName() {
      return false;
   }

   protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
      return false;
   }

   public double getMeleeRange() {
      return 12.0D;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.setThrowingRocks(false);
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(3, this.meleeAttackAI);
         this.setThrowingRocks(false);
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(3, this.rangedAttackAI);
         this.setThrowingRocks(true);
      }

   }

   public void func_82196_d(EntityLivingBase target, float f) {
      EntityArrow template = new EntityArrow(this.field_70170_p, this, target, f * 1.5F, 0.5F);
      LOTREntityThrownRock rock = this.getThrownRock();
      rock.func_70012_b(template.field_70165_t, template.field_70163_u, template.field_70161_v, template.field_70177_z, template.field_70125_A);
      rock.field_70159_w = template.field_70159_w;
      rock.field_70181_x = template.field_70181_x + 0.6D;
      rock.field_70179_y = template.field_70179_y;
      this.field_70170_p.func_72838_d(rock);
      this.func_85030_a(this.func_70639_aQ(), this.func_70599_aP(), this.func_70647_i() * 0.75F);
      this.func_71038_i();
   }

   protected LOTREntityThrownRock getThrownRock() {
      LOTREntityThrownRock rock = new LOTREntityThrownRock(this.field_70170_p, this);
      rock.setDamage((float)this.func_110148_a(thrownRockDamage).func_111126_e());
      return rock;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("CanDropTrollTotem", this.canDropTrollTotem);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("CanDropTrollTotem")) {
         this.setCanDropTrollTotem(nbt.func_74767_n("CanDropTrollTotem"));
      }

   }

   public void onTrollDeathBySun() {
      this.field_70170_p.func_72956_a(this, "lotr:troll.transform", this.func_70599_aP(), this.func_70647_i());
      this.field_70170_p.func_72960_a(this, (byte)15);
      this.func_70106_y();
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         super.func_70103_a(b);

         for(int l = 0; l < 64; ++l) {
            LOTRMod.proxy.spawnParticle("largeStone", this.field_70165_t + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      if (this.canDropTrollTotem) {
         this.dropTrollTotemPart(flag, i);
      }

   }

   protected void dropTrollTotemPart(boolean flag, int i) {
      int totemChance = 15 - i * 3;
      totemChance = Math.max(totemChance, 1);
      if (this.field_70146_Z.nextInt(totemChance) == 0) {
         this.func_70099_a(new ItemStack(LOTRMod.trollTotem, 1, this.field_70146_Z.nextInt(3)), 0.0F);
      }

   }

   public LOTREntityMountainTroll setCanDropTrollTotem(boolean flag) {
      this.canDropTrollTotem = flag;
      return this;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killMountainTroll;
   }

   public float getAlignmentBonus() {
      return 4.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 7 + this.field_70146_Z.nextInt(6);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }
}
