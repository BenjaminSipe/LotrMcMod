package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityBearRug extends LOTREntityRugBase {
   public LOTREntityBearRug(World world) {
      super(world);
      this.func_70105_a(1.8F, 0.3F);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(18, (byte)0);
   }

   public LOTREntityBear.BearType getRugType() {
      int i = this.field_70180_af.func_75683_a(18);
      return LOTREntityBear.BearType.forID(i);
   }

   public void setRugType(LOTREntityBear.BearType t) {
      this.field_70180_af.func_75692_b(18, (byte)t.bearID);
   }

   protected String getRugNoise() {
      return "lotr:bear.say";
   }

   protected ItemStack getRugItem() {
      return new ItemStack(LOTRMod.bearRug, 1, this.getRugType().bearID);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("RugType", (byte)this.getRugType().bearID);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setRugType(LOTREntityBear.BearType.forID(nbt.func_74771_c("RugType")));
   }
}
