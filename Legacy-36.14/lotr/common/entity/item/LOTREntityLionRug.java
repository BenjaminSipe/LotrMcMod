package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemLionRug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityLionRug extends LOTREntityRugBase {
   public LOTREntityLionRug(World world) {
      super(world);
      this.func_70105_a(1.8F, 0.3F);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(18, (byte)0);
   }

   public LOTRItemLionRug.LionRugType getRugType() {
      int i = this.field_70180_af.func_75683_a(18);
      return LOTRItemLionRug.LionRugType.forID(i);
   }

   public void setRugType(LOTRItemLionRug.LionRugType t) {
      this.field_70180_af.func_75692_b(18, (byte)t.lionID);
   }

   protected String getRugNoise() {
      return "lotr:lion.say";
   }

   protected ItemStack getRugItem() {
      return new ItemStack(LOTRMod.lionRug, 1, this.getRugType().lionID);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("RugType", (byte)this.getRugType().lionID);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setRugType(LOTRItemLionRug.LionRugType.forID(nbt.func_74771_c("RugType")));
   }
}
