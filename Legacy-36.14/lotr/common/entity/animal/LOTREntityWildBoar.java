package lotr.common.entity.animal;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWildBoar extends LOTREntityHorse {
   public LOTREntityWildBoar(World world) {
      super(world);
      this.func_70105_a(0.9F, 0.8F);
   }

   protected boolean isMountHostile() {
      return true;
   }

   protected EntityAIBase createMountAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.2D, true);
   }

   public int func_110265_bP() {
      return 0;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
   }

   protected void onLOTRHorseSpawn() {
      double maxHealth = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
      maxHealth = Math.min(maxHealth, 25.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
      double speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
      speed *= 1.0D;
      speed = this.clampChildSpeed(speed);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(speed);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 10.0D, 30.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.3D, 1.0D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.08D, 0.29D);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      boolean doBoarNerf = true;
      if (doBoarNerf) {
         double speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
         double clampedSpeed = this.clampChildSpeed(speed);
         if (clampedSpeed < speed) {
            System.out.println("Reducing boar movement speed from " + speed);
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(clampedSpeed);
            System.out.println("Movement speed now " + this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
         }
      }

   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() == Items.field_151172_bF;
   }

   protected void func_70628_a(boolean flag, int i) {
      int meat = this.field_70146_Z.nextInt(3) + 1 + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meat; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(Items.field_151157_am, 1);
         } else {
            this.func_145779_a(Items.field_151147_al, 1);
         }
      }

   }

   protected String func_70639_aQ() {
      super.func_70639_aQ();
      return "mob.pig.say";
   }

   protected String func_70621_aR() {
      super.func_70621_aR();
      return "mob.pig.say";
   }

   protected String func_70673_aS() {
      super.func_70673_aS();
      return "mob.pig.death";
   }

   protected String func_110217_cl() {
      super.func_110217_cl();
      return "mob.pig.say";
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      this.func_85030_a("mob.pig.step", 0.15F, 1.0F);
   }
}
