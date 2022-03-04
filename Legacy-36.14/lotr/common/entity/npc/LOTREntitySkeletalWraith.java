package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntitySkeletalWraith extends LOTREntityNPC {
   public LOTREntitySkeletalWraith(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.field_70178_ae = true;
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIRestrictSun(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIFleeSun(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIAttackOnCollide(this, 1.2D, false));
      this.field_70714_bg.func_75776_a(4, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HOSTILE;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public void func_70636_d() {
      if (this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K) {
         float f = this.func_70013_c(1.0F);
         if (f > 0.5F && this.field_70146_Z.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.field_70170_p.func_72937_j(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v))) {
            boolean flag = true;
            ItemStack itemstack = this.func_71124_b(4);
            if (itemstack != null) {
               if (itemstack.func_77984_f()) {
                  itemstack.func_77964_b(itemstack.func_77952_i() + this.field_70146_Z.nextInt(2));
                  if (itemstack.func_77952_i() >= itemstack.func_77958_k()) {
                     this.func_70669_a(itemstack);
                     this.func_70062_b(4, (ItemStack)null);
                  }
               }

               flag = false;
            }

            if (flag) {
               this.func_70015_d(8);
            }
         }
      }

      super.func_70636_d();
      if (this.field_70146_Z.nextBoolean()) {
         this.field_70170_p.func_72869_a("smoke", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

   }

   public boolean canDropRares() {
      return false;
   }

   protected String func_70639_aQ() {
      return "mob.skeleton.say";
   }

   protected String func_70621_aR() {
      return "mob.skeleton.hurt";
   }

   protected String func_70673_aS() {
      return "mob.skeleton.death";
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEAD;
   }
}
