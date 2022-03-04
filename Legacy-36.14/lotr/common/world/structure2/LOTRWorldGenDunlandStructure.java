package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenDunlandStructure extends LOTRWorldGenStructureBase2 {
   protected Block floorBlock;
   protected int floorMeta;
   protected Block woodBlock;
   protected int woodMeta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block fenceGateBlock;
   protected Block doorBlock;
   protected Block roofBlock;
   protected int roofMeta;
   protected Block roofSlabBlock;
   protected int roofSlabMeta;
   protected Block roofStairBlock;
   protected Block barsBlock;
   protected int barsMeta;
   protected Block bedBlock;

   public LOTRWorldGenDunlandStructure(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      int randomFloor = random.nextInt(5);
      if (randomFloor == 0) {
         this.floorBlock = Blocks.field_150347_e;
         this.floorMeta = 0;
      } else if (randomFloor == 1) {
         this.floorBlock = Blocks.field_150405_ch;
         this.floorMeta = 0;
      } else if (randomFloor == 2) {
         this.floorBlock = Blocks.field_150406_ce;
         this.floorMeta = 7;
      } else if (randomFloor == 3) {
         this.floorBlock = Blocks.field_150406_ce;
         this.floorMeta = 12;
      } else if (randomFloor == 4) {
         this.floorBlock = Blocks.field_150406_ce;
         this.floorMeta = 15;
      }

      if (random.nextBoolean()) {
         this.woodBlock = Blocks.field_150364_r;
         this.woodMeta = 1;
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 1;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 1;
         this.plankStairBlock = Blocks.field_150485_bF;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 1;
         this.fenceGateBlock = LOTRMod.fenceGateSpruce;
         this.doorBlock = LOTRMod.doorSpruce;
      } else {
         int randomWood = random.nextInt(2);
         if (randomWood == 0) {
            this.woodBlock = Blocks.field_150364_r;
            this.woodMeta = 0;
            this.plankBlock = Blocks.field_150344_f;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.field_150376_bx;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.field_150476_ad;
            this.fenceBlock = Blocks.field_150422_aJ;
            this.fenceMeta = 0;
            this.fenceGateBlock = Blocks.field_150396_be;
            this.doorBlock = Blocks.field_150466_ao;
         } else if (randomWood == 1) {
            this.woodBlock = LOTRMod.wood5;
            this.woodMeta = 0;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 4;
            this.plankStairBlock = LOTRMod.stairsPine;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 4;
            this.fenceGateBlock = LOTRMod.fenceGatePine;
            this.doorBlock = LOTRMod.doorPine;
         }
      }

      this.roofBlock = LOTRMod.thatch;
      this.roofMeta = 0;
      this.roofSlabBlock = LOTRMod.slabSingleThatch;
      this.roofSlabMeta = 0;
      this.roofStairBlock = LOTRMod.stairsThatch;
      if (random.nextBoolean()) {
         this.barsBlock = Blocks.field_150411_aY;
         this.barsMeta = 0;
      } else {
         this.barsBlock = LOTRMod.bronzeBars;
         this.barsMeta = 0;
      }

      if (random.nextBoolean()) {
         this.bedBlock = LOTRMod.furBed;
      } else {
         this.bedBlock = LOTRMod.strawBed;
      }

   }

   protected ItemStack getRandomDunlandWeapon(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(Items.field_151040_l), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.daggerIron), new ItemStack(Items.field_151052_q), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.dunlendingClub), new ItemStack(LOTRMod.dunlendingTrident)};
      return items[random.nextInt(items.length)].func_77946_l();
   }

   protected void placeDunlandItemFrame(World world, Random random, int i, int j, int k, int direction) {
      ItemStack[] items = new ItemStack[]{new ItemStack(Items.field_151103_aS), new ItemStack(LOTRMod.fur), new ItemStack(Items.field_151145_ak), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151052_q), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearStone), new ItemStack(Items.field_151031_f), new ItemStack(Items.field_151032_g), new ItemStack(LOTRMod.mug), new ItemStack(LOTRMod.skullCup)};
      ItemStack item = items[random.nextInt(items.length)].func_77946_l();
      this.spawnItemFrame(world, i, j, k, direction, item);
   }
}
