package lotr.common.entity.animal;

import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityElk extends LOTREntityHorse implements LOTRRandomSkinEntity {
   public LOTREntityElk(World world) {
      super(world);
      this.func_70105_a(1.6F, 1.8F);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   protected boolean isMountHostile() {
      return true;
   }

   protected EntityAIBase createMountAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.25D, true);
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
      maxHealth *= (double)(1.0F + this.field_70146_Z.nextFloat() * 0.5F);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 16.0D, 50.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.3D, 1.0D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.08D, 0.34D);
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() == Items.field_151015_O;
   }

   protected void func_70628_a(boolean flag, int i) {
      int hide = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      int meat;
      for(meat = 0; meat < hide; ++meat) {
         this.func_145779_a(Items.field_151116_aA, 1);
      }

      meat = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meat; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(LOTRMod.deerCooked, 1);
         } else {
            this.func_145779_a(LOTRMod.deerRaw, 1);
         }
      }

   }

   protected String func_70639_aQ() {
      super.func_70639_aQ();
      return "lotr:elk.say";
   }

   protected String func_70621_aR() {
      super.func_70621_aR();
      return "lotr:elk.hurt";
   }

   protected String func_70673_aS() {
      super.func_70673_aS();
      return "lotr:elk.death";
   }
}
