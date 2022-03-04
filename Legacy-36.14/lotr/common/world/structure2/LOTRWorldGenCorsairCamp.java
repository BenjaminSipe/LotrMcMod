package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.entity.npc.LOTREntityCorsairCaptain;
import lotr.common.entity.npc.LOTREntityCorsairSlaver;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairCamp extends LOTRWorldGenCampBase {
   public LOTRWorldGenCorsairCamp(boolean flag) {
      super(flag);
   }

   protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
      return new LOTRWorldGenCorsairTent(false);
   }

   protected LOTREntityNPC getCampCaptain(World world, Random random) {
      return null;
   }

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityCorsair.class);
      respawner.setCheckRanges(24, -12, 12, 10);
      respawner.setSpawnRanges(8, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
      int corsairs = 3 + random.nextInt(5);

      for(int l = 0; l < corsairs; ++l) {
         LOTREntityCorsair corsair = new LOTREntityCorsair(world);
         if (l == 0) {
            corsair = random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world);
         }

         int r = 4;
         float ang = random.nextFloat() * 3.1415927F * 2.0F;
         int i1 = (int)((float)r * MathHelper.func_76134_b(ang));
         int k1 = (int)((float)r * MathHelper.func_76126_a(ang));
         int j1 = this.getTopBlock(world, i1, k1);
         this.spawnNPCAndSetHome((EntityCreature)corsair, world, i1, j1, k1, 16);
      }

   }

   protected boolean generateFarm() {
      return false;
   }

   protected void generateCentrepiece(World world, Random random, int i, int j, int k) {
      this.loadStrScan("corsair_camp_centre");
      this.generateStrScan(world, random, 0, 0, 0);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         int chestPiles;
         int l;
         int r;
         int i1;
         int k1;
         for(chestPiles = 0; chestPiles < 16; ++chestPiles) {
            l = MathHelper.func_76136_a(random, 8, 20);
            float ang = random.nextFloat() * 3.1415927F * 2.0F;
            r = (int)((float)l * MathHelper.func_76134_b(ang));
            int k1 = (int)((float)l * MathHelper.func_76126_a(ang));
            i1 = this.getTopBlock(world, r, k1);
            k1 = random.nextInt(4);
            if (this.generateSubstructureWithRestrictionFlag(new LOTRWorldGenCorsairCampCage(this.notifyChanges), world, random, r, i1, k1, k1, true)) {
               break;
            }
         }

         chestPiles = 1 + random.nextInt(2);

         for(l = 0; l < chestPiles; ++l) {
            for(int att = 0; att < 16; ++att) {
               r = MathHelper.func_76136_a(random, 8, 20);
               float ang = random.nextFloat() * 3.1415927F * 2.0F;
               i1 = (int)((float)r * MathHelper.func_76134_b(ang));
               k1 = (int)((float)r * MathHelper.func_76126_a(ang));
               int j1 = this.getTopBlock(world, i1, k1);
               if (this.isOpaque(world, i1, j1 - 1, k1) && this.isAir(world, i1, j1, k1) && this.isAir(world, i1, j1 + 1, k1)) {
                  this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.wood8, 3);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
                  this.placeChest(world, random, i1, j1 + 1, k1, LOTRMod.chestBasket, 2, LOTRChestContents.CORSAIR, 3 + random.nextInt(3));
                  this.tryPlaceSideChest(world, random, i1 - 1, j1, k1, 5);
                  this.tryPlaceSideChest(world, random, i1 + 1, j1, k1, 4);
                  this.tryPlaceSideChest(world, random, i1, j1, k1 - 1, 2);
                  this.tryPlaceSideChest(world, random, i1, j1, k1 + 1, 3);
                  break;
               }
            }
         }

         return true;
      }
   }

   private void tryPlaceSideChest(World world, Random random, int i, int j, int k, int meta) {
      if (this.isOpaque(world, i, j - 1, k) && this.isAir(world, i, j, k)) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.chestBasket, meta);
         } else {
            this.placeChest(world, random, i, j, k, LOTRMod.chestBasket, meta, LOTRChestContents.CORSAIR, 1);
         }
      }

   }
}
