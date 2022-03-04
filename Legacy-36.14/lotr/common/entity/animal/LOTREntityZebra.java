package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityZebra extends LOTREntityHorse {
   public LOTREntityZebra(World world) {
      super(world);
   }

   public int func_110265_bP() {
      return 0;
   }

   public boolean func_110259_cr() {
      return false;
   }

   public String func_70005_c_() {
      if (this.func_94056_bM()) {
         return this.func_94057_bL();
      } else {
         String s = EntityList.func_75621_b(this);
         return StatCollector.func_74838_a("entity." + s + ".name");
      }
   }

   public EntityAgeable func_90011_a(EntityAgeable entityageable) {
      LOTREntityZebra zebra = (LOTREntityZebra)super.func_90011_a(entityageable);
      return zebra;
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      if (itemstack != null && itemstack.func_77973_b() == Items.field_151133_ar && !entityplayer.field_71075_bZ.field_75098_d) {
         --itemstack.field_77994_a;
         if (itemstack.field_77994_a <= 0) {
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, new ItemStack(Items.field_151117_aB));
         } else if (!entityplayer.field_71071_by.func_70441_a(new ItemStack(Items.field_151117_aB))) {
            entityplayer.func_71019_a(new ItemStack(Items.field_151117_aB, 1, 0), false);
         }

         return true;
      } else {
         return super.func_70085_c(entityplayer);
      }
   }

   protected void func_70628_a(boolean flag, int i) {
      int j = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(1 + i);

      int l;
      for(l = 0; l < j; ++l) {
         this.func_145779_a(Items.field_151116_aA, 1);
      }

      j = this.field_70146_Z.nextInt(2) + 1 + this.field_70146_Z.nextInt(1 + i);

      for(l = 0; l < j; ++l) {
         if (this.func_70027_ad()) {
            this.func_145779_a(LOTRMod.zebraCooked, 1);
         } else {
            this.func_145779_a(LOTRMod.zebraRaw, 1);
         }
      }

   }

   protected String func_70639_aQ() {
      super.func_70639_aQ();
      return "lotr:zebra.say";
   }

   protected String func_70621_aR() {
      super.func_70621_aR();
      return "lotr:zebra.hurt";
   }

   protected String func_70673_aS() {
      super.func_70673_aS();
      return "lotr:zebra.death";
   }

   protected String func_110217_cl() {
      super.func_110217_cl();
      return "lotr:zebra.hurt";
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }
}
