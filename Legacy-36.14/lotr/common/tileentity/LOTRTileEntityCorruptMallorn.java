package lotr.common.tileentity;

import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRTileEntityCorruptMallorn extends TileEntity {
   public void func_145845_h() {
      super.func_145845_h();
      Random rand = this.field_145850_b.field_73012_v;
      if (!this.field_145850_b.field_72995_K && LOTRMod.canSpawnMobs(this.field_145850_b) && rand.nextInt(40) == 0) {
         BiomeGenBase biome = this.field_145850_b.func_72807_a(this.field_145851_c, this.field_145849_e);
         if (biome instanceof LOTRBiomeGenFangorn) {
            int checkRange = 24;
            int spawnRange = 20;
            List nearbyEnts = this.field_145850_b.func_72872_a(LOTREntityEnt.class, AxisAlignedBB.func_72330_a((double)this.field_145851_c, (double)this.field_145848_d, (double)this.field_145849_e, (double)(this.field_145851_c + 1), (double)(this.field_145848_d + 1), (double)(this.field_145849_e + 1)).func_72314_b((double)checkRange, (double)checkRange, (double)checkRange));
            if (nearbyEnts.isEmpty()) {
               LOTREntityEnt ent = new LOTREntityEnt(this.field_145850_b);

               for(int l = 0; l < 16; ++l) {
                  int i = this.field_145851_c + MathHelper.func_76136_a(rand, -spawnRange, spawnRange);
                  int j = this.field_145848_d + MathHelper.func_76136_a(rand, -spawnRange, spawnRange);
                  int k = this.field_145849_e + MathHelper.func_76136_a(rand, -spawnRange, spawnRange);
                  if (this.field_145850_b.func_147439_a(i, j - 1, k).func_149721_r() && !this.field_145850_b.func_147439_a(i, j, k).func_149721_r() && !this.field_145850_b.func_147439_a(i, j + 1, k).func_149721_r()) {
                     ent.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, rand.nextFloat() * 360.0F, 0.0F);
                     ent.liftSpawnRestrictions = false;
                     if (ent.func_70601_bi()) {
                        ent.func_110161_a((IEntityLivingData)null);
                        ent.isNPCPersistent = false;
                        this.field_145850_b.func_72838_d(ent);
                        break;
                     }
                  }
               }
            }
         }
      }

   }
}
