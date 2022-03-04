package lotr.common.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityFallingFireJar extends EntityFallingBlock implements IEntityAdditionalSpawnData {
   public LOTREntityFallingFireJar(World world) {
      super(world);
   }

   public LOTREntityFallingFireJar(World world, double d, double d1, double d2, Block block) {
      super(world, d, d1, d2, block);
   }

   public LOTREntityFallingFireJar(World world, double d, double d1, double d2, Block block, int meta) {
      super(world, d, d1, d2, block, meta);
   }

   public void writeSpawnData(ByteBuf data) {
      data.writeDouble(this.field_70169_q);
      data.writeDouble(this.field_70167_r);
      data.writeDouble(this.field_70166_s);
      data.writeInt(Block.func_149682_b(this.func_145805_f()));
      data.writeByte(this.field_145814_a);
   }

   public void readSpawnData(ByteBuf data) {
      double x = data.readDouble();
      double y = data.readDouble();
      double z = data.readDouble();
      Block block = Block.func_149729_e(data.readInt());
      int meta = data.readByte();
      Entity proxy = new EntityFallingBlock(this.field_70170_p, x, y, z, block, meta);
      NBTTagCompound nbt = new NBTTagCompound();
      proxy.func_70109_d(nbt);
      this.func_70020_e(nbt);
   }
}
