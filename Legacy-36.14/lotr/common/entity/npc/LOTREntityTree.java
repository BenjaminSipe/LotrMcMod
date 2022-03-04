package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeHooks;

public abstract class LOTREntityTree extends LOTREntityNPC {
   public static Block[] WOOD_BLOCKS;
   public static Block[] LEAF_BLOCKS;
   public static Block[] SAPLING_BLOCKS;
   public static int[] WOOD_META;
   public static int[] LEAF_META;
   public static int[] SAPLING_META;
   public static String[] TYPES;

   public LOTREntityTree(World world) {
      super(world);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)0);
      if (this.field_70146_Z.nextInt(9) == 0) {
         this.setTreeType(2);
      } else if (this.field_70146_Z.nextInt(3) == 0) {
         this.setTreeType(1);
      } else {
         this.setTreeType(0);
      }

   }

   public int getTreeType() {
      int i = this.field_70180_af.func_75683_a(16);
      if (i < 0 || i >= TYPES.length) {
         i = 0;
      }

      return i;
   }

   public void setTreeType(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("EntType", (byte)this.getTreeType());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setTreeType(nbt.func_74771_c("EntType"));
   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      if (!(target instanceof LOTREntityTree)) {
         super.setAttackTarget(target, speak);
      }
   }

   public void func_70653_a(Entity entity, float f, double d, double d1) {
      super.func_70653_a(entity, f, d, d1);
      this.field_70159_w /= 2.0D;
      this.field_70181_x /= 2.0D;
      this.field_70179_y /= 2.0D;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (this.doTreeDamageCalculation() && !this.isTreeEffectiveDamage(damagesource)) {
         f /= 3.0F;
      }

      return super.func_70097_a(damagesource, f);
   }

   protected boolean doTreeDamageCalculation() {
      return true;
   }

   protected final boolean isTreeEffectiveDamage(DamageSource damagesource) {
      if (damagesource.func_76347_k()) {
         return true;
      } else {
         if (damagesource.func_76346_g() instanceof EntityLivingBase && damagesource.func_76364_f() == damagesource.func_76346_g()) {
            ItemStack itemstack = ((EntityLivingBase)damagesource.func_76346_g()).func_70694_bm();
            if (itemstack != null && ForgeHooks.canToolHarvestBlock(Blocks.field_150364_r, 0, itemstack)) {
               return true;
            }
         }

         return false;
      }
   }

   public void func_70690_d(PotionEffect effect) {
      if (effect.func_76456_a() != Potion.field_76436_u.field_76415_H) {
         super.func_70690_d(effect);
      }
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int logs = MathHelper.func_76136_a(this.field_70146_Z, 3, 10) + this.field_70146_Z.nextInt(4 * (i + 1));

      int sticks;
      int l;
      for(sticks = 0; sticks < logs; ++sticks) {
         l = this.getTreeType();
         this.func_70099_a(new ItemStack(WOOD_BLOCKS[l], 1, WOOD_META[l]), 0.0F);
      }

      sticks = MathHelper.func_76136_a(this.field_70146_Z, 6, 16) + this.field_70146_Z.nextInt(5 * (i + 1));

      for(l = 0; l < sticks; ++l) {
         this.func_145779_a(Items.field_151055_y, 1);
      }

   }

   public boolean canDropRares() {
      return false;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         if (this.liftSpawnRestrictions) {
            return true;
         } else {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            Block block = this.field_70170_p.func_147439_a(i, j - 1, k);
            this.field_70170_p.func_72805_g(i, j - 1, k);
            return j > 62 && (block == Blocks.field_150349_c || block == Blocks.field_150346_d);
         }
      } else {
         return false;
      }
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (this.isTreeHomeBiome(biome)) {
         f += 20.0F;
      }

      return f;
   }

   protected boolean isTreeHomeBiome(BiomeGenBase biome) {
      return biome instanceof LOTRBiomeGenFangorn;
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }

   static {
      WOOD_BLOCKS = new Block[]{Blocks.field_150364_r, LOTRMod.wood2, Blocks.field_150364_r};
      LEAF_BLOCKS = new Block[]{Blocks.field_150362_t, LOTRMod.leaves2, Blocks.field_150362_t};
      SAPLING_BLOCKS = new Block[]{Blocks.field_150345_g, LOTRMod.sapling2, Blocks.field_150345_g};
      WOOD_META = new int[]{0, 1, 2};
      LEAF_META = new int[]{0, 1, 2};
      SAPLING_META = new int[]{0, 1, 2};
      TYPES = new String[]{"oak", "beech", "birch"};
   }
}
