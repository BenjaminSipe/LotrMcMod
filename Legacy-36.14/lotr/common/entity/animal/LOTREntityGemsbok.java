package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityGemsbok extends EntityAnimal {
   public LOTREntityGemsbok(World world) {
      super(world);
      this.func_70105_a(0.9F, 1.4F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.3D));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.2D, Items.field_151015_O, false));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.1D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(22.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public boolean func_70650_aV() {
      return true;
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151015_O;
   }

   protected void func_70628_a(boolean flag, int i) {
      int j = 1 + this.field_70146_Z.nextInt(4) + this.field_70146_Z.nextInt(1 + i);

      for(int k = 0; k < j; ++k) {
         this.func_145779_a(LOTRMod.gemsbokHide, 1);
      }

      if (this.field_70146_Z.nextBoolean()) {
         this.func_145779_a(LOTRMod.gemsbokHorn, 1);
      }

   }

   public EntityAgeable func_90011_a(EntityAgeable entity) {
      return new LOTREntityGemsbok(this.field_70170_p);
   }

   protected String func_70639_aQ() {
      return "lotr:deer.say";
   }

   protected String func_70621_aR() {
      return "lotr:deer.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:deer.death";
   }

   protected float func_70647_i() {
      return super.func_70647_i() * this.getGemsbokSoundPitch();
   }

   protected float getGemsbokSoundPitch() {
      return 0.8F;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
