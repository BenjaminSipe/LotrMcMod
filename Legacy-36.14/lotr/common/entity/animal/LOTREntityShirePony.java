package lotr.common.entity.animal;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRReflection;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityShirePony extends LOTREntityHorse {
   public static float PONY_SCALE = 0.8F;
   public boolean breedingFlag = false;

   public LOTREntityShirePony(World world) {
      super(world);
      this.func_70105_a(this.field_70130_N * PONY_SCALE, this.field_70131_O * PONY_SCALE);
   }

   public int func_110265_bP() {
      if (this.breedingFlag) {
         return 0;
      } else {
         return this.field_70170_p.field_72995_K ? 0 : 1;
      }
   }

   public boolean func_110259_cr() {
      return false;
   }

   protected void onLOTRHorseSpawn() {
      double maxHealth = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
      maxHealth *= 0.75D;
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
      double jumpStrength = this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e();
      jumpStrength *= 0.5D;
      this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
      double moveSpeed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b();
      moveSpeed *= 0.8D;
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(moveSpeed);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 10.0D, 28.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.2D, 1.0D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.08D, 0.3D);
   }

   public EntityAgeable func_90011_a(EntityAgeable other) {
      LOTREntityShirePony otherPony = (LOTREntityShirePony)other;
      this.breedingFlag = true;
      otherPony.breedingFlag = true;
      EntityAgeable child = super.func_90011_a(otherPony);
      this.breedingFlag = false;
      otherPony.breedingFlag = false;
      return child;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)this.field_70153_n;
         if (this.func_110257_ck() && this.func_110261_ca()) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideShirePony);
         }
      }

   }

   protected String func_70639_aQ() {
      super.func_70639_aQ();
      return "mob.horse.idle";
   }

   protected String func_70621_aR() {
      super.func_70621_aR();
      return "mob.horse.hit";
   }

   protected String func_70673_aS() {
      super.func_70673_aS();
      return "mob.horse.death";
   }

   protected String func_110217_cl() {
      super.func_110217_cl();
      return "mob.horse.angry";
   }
}
