package lotr.common.entity.projectile;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRReflection;
import lotr.common.item.LOTRItemRing;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityFishHook extends EntityFishHook {
   public LOTREntityFishHook(World world) {
      super(world);
   }

   public LOTREntityFishHook(World world, EntityPlayer entityplayer) {
      super(world, entityplayer);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, 0);
   }

   public int getPlayerID() {
      return this.field_70180_af.func_75679_c(16);
   }

   public void setPlayerID(int id) {
      this.field_70180_af.func_75692_b(16, id);
   }

   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_146042_b == null) {
            this.func_70106_y();
            return;
         }
      } else if (this.field_146042_b == null) {
         int playerID = this.getPlayerID();
         Entity player = this.field_70170_p.func_73045_a(playerID);
         if (!(player instanceof EntityPlayer)) {
            this.func_70106_y();
            return;
         }

         EntityPlayer entityplayer = (EntityPlayer)player;
         entityplayer.field_71104_cf = this;
         this.field_146042_b = entityplayer;
      }

      super.func_70071_h_();
   }

   public int func_146034_e() {
      if (this.field_70170_p.field_72995_K) {
         return 0;
      } else {
         int damage = 0;
         double d0;
         double d1;
         if (this.field_146043_c != null) {
            double d0 = this.field_146042_b.field_70165_t - this.field_70165_t;
            double d1 = this.field_146042_b.field_70163_u - this.field_70163_u;
            double d2 = this.field_146042_b.field_70161_v - this.field_70161_v;
            d0 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
            d1 = 0.1D;
            Entity var10000 = this.field_146043_c;
            var10000.field_70159_w += d0 * d1;
            var10000 = this.field_146043_c;
            var10000.field_70181_x += d1 * d1 + (double)MathHelper.func_76133_a(d0) * 0.08D;
            var10000 = this.field_146043_c;
            var10000.field_70179_y += d2 * d1;
            damage = 3;
         } else if (LOTRReflection.getFishHookBobTime(this) > 0) {
            float chance = this.field_70170_p.field_73012_v.nextFloat();
            int luck = EnchantmentHelper.func_151386_g(this.field_146042_b);
            int speed = EnchantmentHelper.func_151387_h(this.field_146042_b);
            LOTRFishing.FishResult result = LOTRFishing.getFishResult(this.field_70146_Z, chance, luck, speed, true);
            ItemStack item = result.fishedItem;
            EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, item);
            d0 = this.field_146042_b.field_70165_t - this.field_70165_t;
            d1 = this.field_146042_b.field_70163_u - this.field_70163_u;
            double d2 = this.field_146042_b.field_70161_v - this.field_70161_v;
            double dist = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
            double motion = 0.1D;
            entityitem.field_70159_w = d0 * motion;
            entityitem.field_70181_x = d1 * motion + (double)MathHelper.func_76133_a(dist) * 0.08D;
            entityitem.field_70179_y = d2 * motion;
            this.field_70170_p.func_72838_d(entityitem);
            this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_146042_b.field_70170_p, this.field_146042_b.field_70165_t, this.field_146042_b.field_70163_u + 0.5D, this.field_146042_b.field_70161_v + 0.5D, this.field_70146_Z.nextInt(6) + 1));
            this.field_146042_b.func_71064_a(result.category.stat, 1);
            if (item.func_77973_b() instanceof LOTRItemRing) {
               LOTRLevelData.getData(this.field_146042_b).addAchievement(LOTRAchievement.fishRing);
            }

            damage = 1;
         }

         if (LOTRReflection.isFishHookInGround(this)) {
            damage = 2;
         }

         this.func_70106_y();
         this.field_146042_b.field_71104_cf = null;
         return damage;
      }
   }
}
