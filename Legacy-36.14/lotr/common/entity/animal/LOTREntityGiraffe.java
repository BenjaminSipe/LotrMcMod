package lotr.common.entity.animal;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityGiraffe extends LOTREntityHorse {
   public LOTREntityGiraffe(World world) {
      super(world);
      this.func_70105_a(1.7F, 4.0F);
   }

   public int func_110265_bP() {
      return 0;
   }

   protected void onLOTRHorseSpawn() {
      double jumpStrength = this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111126_e();
      jumpStrength *= 0.8D;
      this.func_110148_a(LOTRReflection.getHorseJumpStrength()).func_111128_a(jumpStrength);
   }

   protected double clampChildHealth(double health) {
      return MathHelper.func_151237_a(health, 12.0D, 34.0D);
   }

   protected double clampChildJump(double jump) {
      return MathHelper.func_151237_a(jump, 0.2D, 1.0D);
   }

   protected double clampChildSpeed(double speed) {
      return MathHelper.func_151237_a(speed, 0.08D, 0.35D);
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack != null && Block.func_149634_a(itemstack.func_77973_b()) instanceof BlockLeavesBase;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.field_70153_n instanceof EntityPlayer && this.isMountSaddled()) {
         EntityPlayer entityplayer = (EntityPlayer)this.field_70153_n;
         BiomeGenBase biome = this.field_70170_p.func_72807_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70161_v));
         if (biome instanceof LOTRBiomeGenShire) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideGiraffeShire);
         }
      }

   }

   protected Item func_146068_u() {
      return Items.field_151116_aA;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      if (flag) {
         int rugChance = 30 - i * 5;
         rugChance = Math.max(rugChance, 1);
         if (this.field_70146_Z.nextInt(rugChance) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.giraffeRug), 0.0F);
         }
      }

   }
}
