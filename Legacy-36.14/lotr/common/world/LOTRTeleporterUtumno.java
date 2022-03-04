package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class LOTRTeleporterUtumno extends Teleporter {
   private WorldServer worldObj;
   private int targetX;
   private int targetZ;

   private LOTRTeleporterUtumno(WorldServer world) {
      super(world);
      this.worldObj = world;
   }

   public static LOTRTeleporterUtumno newTeleporter(int dimension) {
      WorldServer world = DimensionManager.getWorld(dimension);
      if (world == null) {
         DimensionManager.initDimension(dimension);
         world = DimensionManager.getWorld(dimension);
      }

      return new LOTRTeleporterUtumno(world);
   }

   public void setTargetCoords(int x, int z) {
      this.targetX = x;
      this.targetZ = z;
   }

   public void func_77185_a(Entity entity, double d, double d1, double d2, float f) {
      int i = 0;
      int j = 256;
      int k = 0;
      if (this.worldObj.field_73011_w.field_76574_g == LOTRDimension.UTUMNO.dimensionID) {
         label31:
         for(int l = 0; l < 10000; ++l) {
            int x = this.targetX;
            int z = this.targetZ;
            int y = LOTRUtumnoLevel.ICE.corridorBaseLevels[LOTRUtumnoLevel.ICE.corridorBaseLevels.length - 1];
            int targetFuzz = 32;
            int x1 = MathHelper.func_76136_a(this.worldObj.field_73012_v, x - targetFuzz, x + targetFuzz);
            int z1 = MathHelper.func_76136_a(this.worldObj.field_73012_v, z - targetFuzz, z + targetFuzz);
            int yFuzz = 3;

            for(int j1 = -yFuzz; j1 <= yFuzz; ++j1) {
               int y1 = y + j1;
               if (this.worldObj.func_147439_a(x1, y1 - 1, z1).func_149662_c() && this.worldObj.func_147437_c(x1, y1, z1) && this.worldObj.func_147437_c(x1, y1, z1)) {
                  i = x1;
                  j = y1;
                  k = z1;
                  break label31;
               }
            }
         }
      } else {
         double randomDistance = MathHelper.func_82716_a(this.worldObj.field_73012_v, 40.0D, 80.0D);
         float angle = this.worldObj.field_73012_v.nextFloat() * 3.1415927F * 2.0F;
         i = LOTRFixedStructures.UTUMNO_ENTRANCE.xCoord + (int)(randomDistance * (double)MathHelper.func_76126_a(angle));
         k = LOTRFixedStructures.UTUMNO_ENTRANCE.zCoord + (int)(randomDistance * (double)MathHelper.func_76134_b(angle));
         j = this.worldObj.func_72825_h(i, k);
      }

      this.setEntityAndMountLocation(entity, (double)i + 0.5D, (double)j + 1.0D, (double)k + 0.5D);
   }

   private void setEntityAndMountLocation(Entity entity, double x, double y, double z) {
      entity.func_70012_b(x, y, z, entity.field_70177_z, 0.0F);
      entity.field_70143_R = 0.0F;
      if (entity.field_70154_o != null) {
         this.setEntityAndMountLocation(entity.field_70154_o, x, y, z);
      }

   }
}
