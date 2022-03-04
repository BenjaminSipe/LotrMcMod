package lotr.client.render.model.scatter;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class ScatterPositionContext implements IModelData {
   private final long positionHash;

   private ScatterPositionContext(long hash) {
      this.positionHash = hash;
   }

   public static ScatterPositionContext newEmptyContext() {
      return new ScatterPositionContext(0L);
   }

   public static ScatterPositionContext forPosition(IBlockDisplayReader world, BlockPos pos, BlockState state) {
      long hash = MathHelper.func_180186_a(pos);
      return new ScatterPositionContext(hash);
   }

   public long getPositionHash() {
      return this.positionHash;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other != null && other.getClass() == this.getClass()) {
         ScatterPositionContext otherData = (ScatterPositionContext)other;
         return this.positionHash == otherData.positionHash;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Long.hashCode(this.positionHash);
   }

   public String toString() {
      return String.format("ScatterPositionContext[%d]", this.positionHash);
   }

   public boolean hasProperty(ModelProperty prop) {
      return false;
   }

   public Object getData(ModelProperty prop) {
      return null;
   }

   public Object setData(ModelProperty prop, Object data) {
      return null;
   }
}
