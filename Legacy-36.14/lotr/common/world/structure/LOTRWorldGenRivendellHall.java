package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.entity.npc.LOTREntityRivendellLord;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTRWorldGenRivendellHall extends LOTRWorldGenHighElvenHall {
   public LOTRWorldGenRivendellHall(boolean flag) {
      super(flag);
      this.tableBlock = LOTRMod.rivendellTable;
      this.bannerType = LOTRItemBanner.BannerType.RIVENDELL;
      this.chestContents = LOTRChestContents.RIVENDELL_HALL;
   }

   protected LOTREntityElf createElf(World world) {
      return new LOTREntityRivendellElf(world);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (super.func_76484_a(world, random, i, j, k)) {
         LOTREntityElf elfLord = new LOTREntityRivendellLord(world);
         elfLord.func_70012_b((double)(i + 6), (double)(j + 6), (double)(k + 6), 0.0F, 0.0F);
         elfLord.spawnRidingHorse = false;
         elfLord.func_110161_a((IEntityLivingData)null);
         elfLord.setPersistentAndTraderShouldRespawn();
         world.func_72838_d(elfLord);
         elfLord.func_110171_b(i + 7, j + 3, k + 7, 16);
      }

      return false;
   }
}
