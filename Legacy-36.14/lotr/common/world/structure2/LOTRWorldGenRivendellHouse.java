package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityRivendellElf;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenRivendellHouse extends LOTRWorldGenHighElfHouse {
   public LOTRWorldGenRivendellHouse(boolean flag) {
      super(flag);
   }

   protected LOTREntityElf createElf(World world) {
      return new LOTREntityRivendellElf(world);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tableBlock = LOTRMod.rivendellTable;
      this.bannerType = LOTRItemBanner.BannerType.RIVENDELL;
      this.chestContents = LOTRChestContents.RIVENDELL_HALL;
   }

   protected ItemStack getElfFramedItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetRivendell), new ItemStack(LOTRMod.bodyRivendell), new ItemStack(LOTRMod.legsRivendell), new ItemStack(LOTRMod.bootsRivendell), new ItemStack(LOTRMod.daggerRivendell), new ItemStack(LOTRMod.swordRivendell), new ItemStack(LOTRMod.spearRivendell), new ItemStack(LOTRMod.longspearRivendell), new ItemStack(LOTRMod.rivendellBow), new ItemStack(Items.field_151032_g), new ItemStack(Items.field_151008_G), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
