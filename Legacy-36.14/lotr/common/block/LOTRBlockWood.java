package lotr.common.block;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityGaladhrimWarrior;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockWood extends LOTRBlockWoodBase {
   public LOTRBlockWood() {
      this.setWoodNames(new String[]{"shirePine", "mallorn", "mirkOak", "charred"});
   }

   public boolean removedByPlayer(World world, EntityPlayer entityplayer, int i, int j, int k, boolean willHarvest) {
      if (!world.field_72995_K && (world.func_72805_g(i, j, k) & 3) == 1 && world.field_73012_v.nextInt(3) == 0 && world.func_72807_a(i, k) instanceof LOTRBiomeGenLothlorien && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.LOTHLORIEN) < 0.0F && !entityplayer.field_71075_bZ.field_75098_d) {
         int elves = 4 + world.field_73012_v.nextInt(3);
         boolean sentMessage = false;

         for(int l = 0; l < elves; ++l) {
            LOTREntityGaladhrimWarrior elfWarrior = new LOTREntityGaladhrimWarrior(world);
            int i1 = MathHelper.func_76128_c(entityplayer.field_70165_t) - 6 + world.field_73012_v.nextInt(12);
            int k1 = MathHelper.func_76128_c(entityplayer.field_70161_v) - 6 + world.field_73012_v.nextInt(12);
            int j1 = world.func_72825_h(i1, k1);
            if (world.func_147439_a(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP) && !world.func_147439_a(i1, j1, k1).func_149721_r() && !world.func_147439_a(i1, j1 + 1, k1).func_149721_r()) {
               elfWarrior.func_70012_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D, 0.0F, 0.0F);
               if (elfWarrior.func_70601_bi()) {
                  elfWarrior.spawnRidingHorse = false;
                  elfWarrior.func_110161_a((IEntityLivingData)null);
                  world.func_72838_d(elfWarrior);
                  elfWarrior.isDefendingTree = true;
                  elfWarrior.func_70624_b(entityplayer);
                  if (!sentMessage) {
                     elfWarrior.sendSpeechBank(entityplayer, "galadhrim/warrior/defendTrees");
                     sentMessage = true;
                  }
               }
            }
         }
      }

      return super.removedByPlayer(world, entityplayer, i, j, k, willHarvest);
   }
}
