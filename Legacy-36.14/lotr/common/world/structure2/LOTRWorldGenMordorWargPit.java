package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorWarg;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMordorWargPit extends LOTRWorldGenWargPitBase {
   public LOTRWorldGenMordorWargPit(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.brickBlock = LOTRMod.brick;
      this.brickMeta = 0;
      this.brickSlabBlock = LOTRMod.slabSingle;
      this.brickSlabMeta = 1;
      this.brickStairBlock = LOTRMod.stairsMordorBrick;
      this.brickWallBlock = LOTRMod.wall;
      this.brickWallMeta = 1;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 7;
      this.woolBlock = Blocks.field_150325_L;
      this.woolMeta = 12;
      this.carpetBlock = Blocks.field_150404_cg;
      this.carpetMeta = 12;
      this.gateMetalBlock = LOTRMod.gateIronBars;
      this.tableBlock = LOTRMod.morgulTable;
      this.banner = LOTRItemBanner.BannerType.MORDOR;
      this.chestContents = LOTRChestContents.ORC_TENT;
   }

   protected LOTREntityNPC getOrc(World world) {
      return new LOTREntityMordorOrc(world);
   }

   protected LOTREntityNPC getWarg(World world) {
      return new LOTREntityMordorWarg(world);
   }

   protected void setOrcSpawner(LOTREntityNPCRespawner spawner) {
      spawner.setSpawnClass(LOTREntityMordorOrc.class);
   }

   protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
      spawner.setSpawnClass(LOTREntityMordorWarg.class);
   }

   protected void associateGroundBlocks() {
      this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.rock, 0);
      this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.mordorDirt, 0);
      this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.mordorGravel, 0);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingle10, 7);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 3);
      this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 1);
      this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.mordorMoss, 0);
      this.setBlockAliasChance("GROUND_COVER", 0.25F);
   }
}
