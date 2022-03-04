package lotr.common.entity.animal;

import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityDeer extends LOTREntityAnimalMF implements LOTRRandomSkinEntity {
   public LOTREntityDeer(World world) {
      super(world);
      this.func_70105_a(0.8F, 1.0F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.8D));
      this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.2D, Items.field_151015_O, false));
      this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.4D));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.4D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
   }

   public Class getAnimalMFBaseClass() {
      return this.getClass();
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(20, (byte)0);
      this.setMale(this.field_70146_Z.nextBoolean());
   }

   public boolean isMale() {
      return this.field_70180_af.func_75683_a(20) == 1;
   }

   public void setMale(boolean flag) {
      this.field_70180_af.func_75692_b(20, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
   }

   public boolean func_70650_aV() {
      return true;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("DeerMale", this.isMale());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setMale(nbt.func_74767_n("DeerMale"));
   }

   public boolean func_70877_b(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151015_O;
   }

   public EntityAgeable func_90011_a(EntityAgeable entity) {
      LOTREntityDeer deer = new LOTREntityDeer(this.field_70170_p);
      deer.setMale(this.field_70146_Z.nextBoolean());
      return deer;
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

   public int func_70627_aG() {
      return 300;
   }

   protected float func_70599_aP() {
      return 0.5F;
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
}
