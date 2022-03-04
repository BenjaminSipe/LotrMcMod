package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityFlamingo extends EntityAnimal {
   public boolean field_753_a = false;
   public float field_752_b;
   public float destPos;
   public float field_757_d;
   public float field_756_e;
   public float field_755_h = 5.0F;
   public static final int NECK_TIME = 20;
   public static final int FISHING_TIME = 160;
   public static final int FISHING_TIME_TOTAL = 200;

   public LOTREntityFlamingo(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(false);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.3D));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.2D, Items.field_151115_aP, false));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.2D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, 0);
   }

   private int getFishingTick() {
      int i = this.field_70180_af.func_75679_c(16);
      return i;
   }

   public int getFishingTickPre() {
      return this.getFishingTick() >> 16;
   }

   public int getFishingTickCur() {
      return this.getFishingTick() & '\uffff';
   }

   public void setFishingTick(int pre, int cur) {
      int i = pre << 16 | cur & '\uffff';
      this.field_70180_af.func_75692_b(16, i);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public boolean func_70650_aV() {
      return true;
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.field_756_e = this.field_752_b;
      this.field_757_d = this.destPos;
      this.destPos = (float)((double)this.destPos + (double)(!this.field_70122_E && !this.field_70171_ac ? 4 : -1) * 0.3D);
      if (this.destPos < 0.0F) {
         this.destPos = 0.0F;
      }

      if (this.destPos > 1.0F) {
         this.destPos = 1.0F;
      }

      if (!this.field_70122_E && !this.field_70171_ac && this.field_755_h < 1.0F) {
         this.field_755_h = 1.0F;
      }

      this.field_755_h = (float)((double)this.field_755_h * 0.9D);
      if (!this.field_70122_E && !this.field_70171_ac && this.field_70181_x < 0.0D) {
         this.field_70181_x *= 0.6D;
      }

      this.field_752_b += this.field_755_h * 2.0F;
      if (!this.field_70170_p.field_72995_K && !this.func_70631_g_() && !this.func_70880_s() && this.getFishingTickCur() == 0 && this.field_70146_Z.nextInt(600) == 0) {
         Block block = this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b), MathHelper.func_76128_c(this.field_70161_v));
         if (block == Blocks.field_150355_j) {
            this.setFishingTick(200, 200);
         }
      }

      if (this.getFishingTickCur() > 0) {
         int i;
         if (!this.field_70170_p.field_72995_K) {
            i = this.getFishingTickCur();
            this.setFishingTick(i, i - 1);
         } else {
            for(i = 0; i < 3; ++i) {
               double d = this.field_70165_t + MathHelper.func_82716_a(this.field_70146_Z, -0.3D, 0.3D);
               double d1 = this.field_70121_D.field_72338_b + MathHelper.func_82716_a(this.field_70146_Z, -0.3D, 0.3D);
               double d2 = this.field_70161_v + MathHelper.func_82716_a(this.field_70146_Z, -0.3D, 0.3D);
               this.field_70170_p.func_72869_a("bubble", d, d1, d2, 0.0D, 0.0D, 0.0D);
            }
         }
      }

      if (!this.field_70170_p.field_72995_K && this.func_70880_s() && this.getFishingTickCur() > 20) {
         this.setFishingTick(20, 20);
      }

   }

   public boolean func_70097_a(DamageSource source, float f) {
      boolean flag = super.func_70097_a(source, f);
      if (flag && !this.field_70170_p.field_72995_K && this.getFishingTickCur() > 20) {
         this.setFishingTick(20, 20);
      }

      return flag;
   }

   protected void func_70069_a(float f) {
   }

   protected String func_70639_aQ() {
      return "lotr:flamingo.say";
   }

   protected String func_70621_aR() {
      return "lotr:flamingo.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:flamingo.death";
   }

   protected Item func_146068_u() {
      return Items.field_151008_G;
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151115_aP;
   }

   public EntityAgeable func_90011_a(EntityAgeable entity) {
      return new LOTREntityFlamingo(this.field_70170_p);
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
