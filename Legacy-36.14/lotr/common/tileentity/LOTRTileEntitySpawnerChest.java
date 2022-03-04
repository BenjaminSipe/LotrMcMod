package lotr.common.tileentity;

import lotr.common.entity.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;

public class LOTRTileEntitySpawnerChest extends TileEntityChest {
   private String entityClassName = "";

   public void setMobID(Class entityClass) {
      this.entityClassName = LOTREntities.getStringFromClass(entityClass);
   }

   public Entity createMob() {
      return EntityList.func_75620_a(this.entityClassName, this.field_145850_b);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.entityClassName = nbt.func_74779_i("MobID");
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74778_a("MobID", this.entityClassName);
   }
}
