package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarWargPit extends LOTRWorldGenWargPitBase {
   public LOTRWorldGenAngmarWargPit(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.brickBlock = LOTRMod.brick2;
      this.brickMeta = 0;
      this.brickSlabBlock = LOTRMod.slabSingle3;
      this.brickSlabMeta = 3;
      this.brickStairBlock = LOTRMod.stairsAngmarBrick;
      this.brickWallBlock = LOTRMod.wall2;
      this.brickWallMeta = 0;
      this.pillarBlock = LOTRMod.pillar2;
      this.pillarMeta = 4;
      this.woolBlock = Blocks.field_150325_L;
      this.woolMeta = 15;
      this.carpetBlock = Blocks.field_150404_cg;
      this.carpetMeta = 15;
      this.tableBlock = LOTRMod.angmarTable;
      this.banner = LOTRItemBanner.BannerType.ANGMAR;
      this.chestContents = LOTRChestContents.ANGMAR_TENT;
   }

   protected LOTREntityNPC getOrc(World world) {
      return new LOTREntityAngmarOrc(world);
   }

   protected LOTREntityNPC getWarg(World world) {
      return new LOTREntityAngmarWarg(world);
   }

   protected void setOrcSpawner(LOTREntityNPCRespawner spawner) {
      spawner.setSpawnClass(LOTREntityAngmarOrc.class);
   }

   protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
      spawner.setSpawnClass(LOTREntityAngmarWarg.class);
   }

   protected void associateGroundBlocks() {
      super.associateGroundBlocks();
      this.clearScanAlias("GROUND_COVER");
      this.addBlockMetaAliasOption("GROUND_COVER", 1, Blocks.field_150431_aC, 0);
      this.setBlockAliasChance("GROUND_COVER", 0.25F);
   }
}
