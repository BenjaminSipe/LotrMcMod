package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class LOTRTeleporter extends Teleporter {
   private WorldServer world;
   private boolean makeRingPortal;

   public LOTRTeleporter(WorldServer worldserver, boolean flag) {
      super(worldserver);
      this.world = worldserver;
      this.makeRingPortal = flag;
   }

   public void func_77185_a(Entity entity, double d, double d1, double d2, float f) {
      int i;
      int j;
      int k;
      if (this.world.field_73011_w.field_76574_g == LOTRDimension.MIDDLE_EARTH.dimensionID) {
         i = 0;
         k = 0;
         j = LOTRMod.getTrueTopBlock(this.world, i, k);
      } else {
         i = LOTRLevelData.overworldPortalX;
         k = LOTRLevelData.overworldPortalZ;
         j = LOTRLevelData.overworldPortalY;
      }

      entity.func_70012_b((double)i + 0.5D, (double)j + 1.0D, (double)k + 0.5D, entity.field_70177_z, 0.0F);
      if (this.world.field_73011_w.field_76574_g == LOTRDimension.MIDDLE_EARTH.dimensionID && LOTRLevelData.madeMiddleEarthPortal == 0) {
         LOTRLevelData.setMadeMiddleEarthPortal(1);
         if (this.makeRingPortal) {
            if (this.world.field_73011_w instanceof LOTRWorldProviderMiddleEarth) {
               ((LOTRWorldProviderMiddleEarth)this.world.field_73011_w).setRingPortalLocation(i, j, k);
            }

            Entity portal = new LOTREntityPortal(this.world);
            portal.func_70012_b((double)i + 0.5D, (double)j + 3.5D, (double)k + 0.5D, 0.0F, 0.0F);
            this.world.func_72838_d(portal);
         }
      }

   }
}
