package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBlueMountainsHouse extends LOTRWorldGenDwarfHouse {
   public LOTRWorldGenBlueMountainsHouse(boolean flag) {
      super(flag);
   }

   protected LOTREntityDwarf createDwarf(World world) {
      return new LOTREntityBlueDwarf(world);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.stoneBlock = Blocks.field_150348_b;
      this.stoneMeta = 0;
      this.fillerBlock = LOTRMod.rock;
      this.fillerMeta = 3;
      this.topBlock = LOTRMod.rock;
      this.topMeta = 3;
      this.brick2Block = LOTRMod.brick;
      this.brick2Meta = 14;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 3;
      this.chandelierBlock = LOTRMod.chandelier;
      this.chandelierMeta = 11;
      this.tableBlock = LOTRMod.blueDwarvenTable;
      this.barsBlock = LOTRMod.blueDwarfBars;
      this.larderContents = LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
      this.personalContents = LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
      this.plateFoods = LOTRFoods.BLUE_DWARF;
      this.drinkFoods = LOTRFoods.DWARF_DRINK;
   }

   protected ItemStack getRandomWeaponItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.swordBlueDwarven), new ItemStack(LOTRMod.daggerBlueDwarven), new ItemStack(LOTRMod.hammerBlueDwarven), new ItemStack(LOTRMod.battleaxeBlueDwarven), new ItemStack(LOTRMod.pickaxeBlueDwarven), new ItemStack(LOTRMod.mattockBlueDwarven), new ItemStack(LOTRMod.throwingAxeBlueDwarven), new ItemStack(LOTRMod.pikeBlueDwarven)};
      return items[random.nextInt(items.length)].func_77946_l();
   }

   protected ItemStack getRandomOtherItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetBlueDwarven), new ItemStack(LOTRMod.bodyBlueDwarven), new ItemStack(LOTRMod.legsBlueDwarven), new ItemStack(LOTRMod.bootsBlueDwarven), new ItemStack(LOTRMod.blueDwarfSteel), new ItemStack(LOTRMod.bronze), new ItemStack(Items.field_151042_j), new ItemStack(LOTRMod.silver), new ItemStack(LOTRMod.silverNugget), new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151074_bl)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
