package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRWorldGenNomadStructure extends LOTRWorldGenStructureBase2 {
   protected Block tentBlock;
   protected int tentMeta;
   protected Block tent2Block;
   protected int tent2Meta;
   protected Block carpetBlock;
   protected int carpetMeta;
   protected Block carpet2Block;
   protected int carpet2Meta;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block fenceGateBlock;
   protected Block trapdoorBlock;
   protected Block beamBlock;
   protected int beamMeta;
   protected Block bedBlock;

   public LOTRWorldGenNomadStructure(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tentBlock = Blocks.field_150325_L;
      this.tentMeta = 0;
      this.tent2Block = Blocks.field_150325_L;
      this.tent2Meta = 12;
      this.carpetBlock = Blocks.field_150404_cg;
      this.carpetMeta = 0;
      this.carpet2Block = Blocks.field_150404_cg;
      this.carpet2Meta = 12;
      int randomWood = random.nextInt(3);
      if (randomWood == 0) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 0;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 0;
         this.plankStairBlock = Blocks.field_150476_ad;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 0;
         this.fenceGateBlock = Blocks.field_150396_be;
         this.trapdoorBlock = Blocks.field_150415_aT;
         this.beamBlock = LOTRMod.woodBeamV1;
         this.beamMeta = 0;
      } else if (randomWood == 1) {
         this.plankBlock = LOTRMod.planks2;
         this.plankMeta = 2;
         this.plankSlabBlock = LOTRMod.woodSlabSingle3;
         this.plankSlabMeta = 2;
         this.plankStairBlock = LOTRMod.stairsCedar;
         this.fenceBlock = LOTRMod.fence2;
         this.fenceMeta = 2;
         this.fenceGateBlock = LOTRMod.fenceGateCedar;
         this.trapdoorBlock = LOTRMod.trapdoorCedar;
         this.beamBlock = LOTRMod.woodBeam4;
         this.beamMeta = 2;
      } else if (randomWood == 2) {
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 14;
         this.plankSlabBlock = LOTRMod.woodSlabSingle2;
         this.plankSlabMeta = 6;
         this.plankStairBlock = LOTRMod.stairsDatePalm;
         this.fenceBlock = LOTRMod.fence;
         this.fenceMeta = 14;
         this.fenceGateBlock = LOTRMod.fenceGateDatePalm;
         this.trapdoorBlock = LOTRMod.trapdoorDatePalm;
         this.beamBlock = LOTRMod.woodBeam3;
         this.beamMeta = 2;
      }

      this.bedBlock = LOTRMod.strawBed;
   }

   protected void laySandBase(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i, j, k, Blocks.field_150354_m, 0);

      for(int j1 = j - 1; this.getY(j1) >= 0 && !this.isOpaque(world, i, j1, k); --j1) {
         if (this.isOpaque(world, i, j1 - 1, k)) {
            this.setBlockAndMetadata(world, i, j1, k, Blocks.field_150322_A, 0);
         } else {
            this.setBlockAndMetadata(world, i, j1, k, Blocks.field_150354_m, 0);
         }

         this.setGrassToDirt(world, i, j1 - 1, k);
      }

   }

   protected ItemStack getRandomNomadWeapon(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad), new ItemStack(LOTRMod.pikeHarad)};
      return items[random.nextInt(items.length)].func_77946_l();
   }

   protected ItemStack getRandomUmbarWeapon(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.spearNearHarad), new ItemStack(LOTRMod.pikeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
