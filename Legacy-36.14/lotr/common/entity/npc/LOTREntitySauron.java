package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAISauronUseMace;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntitySauron extends LOTREntityNPC {
   public LOTREntitySauron(World world) {
      super(world);
      this.func_70105_a(0.8F, 2.2F);
      this.field_70178_ae = true;
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAISauronUseMace(this));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIAttackOnCollide(this, 2.0D, false));
      this.field_70714_bg.func_75776_a(3, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F, 0.02F));
      this.field_70714_bg.func_75776_a(5, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(500.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.18D);
      this.func_110148_a(npcAttackDamage).func_111128_a(8.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.func_70062_b(0, new ItemStack(LOTRMod.sauronMace));
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   public int func_70658_aO() {
      return 20;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_110143_aJ() < this.func_110138_aP() && this.field_70173_aa % 10 == 0) {
         this.func_70691_i(2.0F);
      }

      if (this.getIsUsingMace() && this.field_70170_p.field_72995_K) {
         for(int i = 0; i < 6; ++i) {
            double d = this.field_70165_t - 2.0D + (double)(this.field_70146_Z.nextFloat() * 4.0F);
            double d1 = this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * 3.0F);
            double d2 = this.field_70161_v - 2.0D + (double)(this.field_70146_Z.nextFloat() * 4.0F);
            double d3 = (this.field_70165_t - d) / 8.0D;
            double d4 = (this.field_70163_u + 0.5D - d1) / 8.0D;
            double d5 = (this.field_70161_v - d2) / 8.0D;
            double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            double d7 = 1.0D - d6;
            double d8 = 0.0D;
            double d9 = 0.0D;
            double d10 = 0.0D;
            if (d7 > 0.0D) {
               d7 *= d7;
               d8 += d3 / d6 * d7 * 0.2D;
               d9 += d4 / d6 * d7 * 0.2D;
               d10 += d5 / d6 * d7 * 0.2D;
            }

            this.field_70170_p.func_72869_a("smoke", d, d1, d2, d8, d9, d10);
         }
      }

   }

   protected void func_70069_a(float f) {
   }

   public void func_70690_d(PotionEffect effect) {
   }

   protected int func_70682_h(int i) {
      return i;
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 3.0F, false);
         this.func_70106_y();
      }

   }

   public boolean getIsUsingMace() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setIsUsingMace(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
   }
}
