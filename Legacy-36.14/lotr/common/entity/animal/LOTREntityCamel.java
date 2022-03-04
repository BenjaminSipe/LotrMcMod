package lotr.common.entity.animal;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCamel extends LOTREntityHorse implements LOTRBiomeGenNearHarad.ImmuneToHeat {
   public LOTREntityCamel(World world) {
      super(world);
      this.func_70105_a(1.6F, 1.8F);
   }

   public int func_110265_bP() {
      return this.field_70170_p.field_72995_K ? 0 : 1;
   }

   protected void onLOTRHorseSpawn() {
      double jumpStrength = this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e();
      jumpStrength *= 0.5D;
      this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 12.0D, 36.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.1D, 0.6D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.1D, 0.35D);
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() == Items.field_151015_O;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.field_70153_n instanceof EntityPlayer && this.isMountSaddled()) {
         LOTRLevelData.getData((EntityPlayer)this.field_70153_n).addAchievement(LOTRAchievement.rideCamel);
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      int hides = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      int meat;
      for(meat = 0; meat < hides; ++meat) {
         this.func_145779_a(Items.field_151116_aA, 1);
      }

      meat = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + i);

      for(int l = 0; l < meat; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(LOTRMod.camelCooked, 1);
         } else {
            this.func_145779_a(LOTRMod.camelRaw, 1);
         }
      }

   }

   public boolean func_110259_cr() {
      return true;
   }

   public boolean isMountArmorValid(ItemStack itemstack) {
      return itemstack != null && itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150404_cg) ? true : super.isMountArmorValid(itemstack);
   }

   public boolean isCamelWearingCarpet() {
      ItemStack itemstack = this.getMountArmor();
      return itemstack != null && itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150404_cg);
   }

   public int getCamelCarpetColor() {
      ItemStack itemstack = this.getMountArmor();
      if (itemstack != null && itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150404_cg)) {
         int meta = itemstack.func_77960_j();
         int dyeMeta = BlockColored.func_150031_c(meta);
         int[] colors = ItemDye.field_150922_c;
         dyeMeta = MathHelper.func_76125_a(dyeMeta, 0, colors.length);
         return colors[dyeMeta];
      } else {
         return -1;
      }
   }

   public void setNomadChestAndCarpet() {
      this.setChestedForWorldGen();
      ItemStack carpet = new ItemStack(Blocks.field_150404_cg, 1, this.field_70146_Z.nextInt(16));
      this.setMountArmor(carpet);
   }
}
