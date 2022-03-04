package lotr.common.init;

import java.util.Random;
import java.util.function.ToIntFunction;
import lotr.common.block.AlloyForgeBlock;
import lotr.common.block.AridGrassBlock;
import lotr.common.block.AxialSlabBlock;
import lotr.common.block.BranchBlock;
import lotr.common.block.BrickWithAboveBlock;
import lotr.common.block.BronzeLanternBlock;
import lotr.common.block.BundleBlock;
import lotr.common.block.CakelikeBlock;
import lotr.common.block.CandleBlock;
import lotr.common.block.ChalkBlock;
import lotr.common.block.ChalkPathBlock;
import lotr.common.block.ChandelierBlock;
import lotr.common.block.ClayTilingBlock;
import lotr.common.block.CrystalBlock;
import lotr.common.block.CustomWaypointMarkerBlock;
import lotr.common.block.DirectionalBaleBlock;
import lotr.common.block.DirectionalMineralBlock;
import lotr.common.block.DirectionalStoneBlock;
import lotr.common.block.DirtyChalkBlock;
import lotr.common.block.DoubleAridGrassBlock;
import lotr.common.block.DoubleTorchBlock;
import lotr.common.block.DoubleableLOTRGrassBlock;
import lotr.common.block.DriedReedsBlock;
import lotr.common.block.DripstoneBlock;
import lotr.common.block.DwarvenForgeBlock;
import lotr.common.block.ElvenForgeBlock;
import lotr.common.block.FactionCraftingTableBlock;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.FlowerLikeBlock;
import lotr.common.block.FourLeafCloverBlock;
import lotr.common.block.GateBlock;
import lotr.common.block.GondorBeaconBlock;
import lotr.common.block.HangingWebBlock;
import lotr.common.block.HearthBlock;
import lotr.common.block.HobbitOvenBlock;
import lotr.common.block.IceBrickBlock;
import lotr.common.block.KegBlock;
import lotr.common.block.LOTRBarsBlock;
import lotr.common.block.LOTRCropBlock;
import lotr.common.block.LOTRDoorBlock;
import lotr.common.block.LOTRDoubleGrassBlock;
import lotr.common.block.LOTRFenceBlock;
import lotr.common.block.LOTRFenceGateBlock;
import lotr.common.block.LOTRFlowerBlock;
import lotr.common.block.LOTRGlassBlock;
import lotr.common.block.LOTRGlassPaneBlock;
import lotr.common.block.LOTRGrassBlock;
import lotr.common.block.LOTRLadderBlock;
import lotr.common.block.LOTRLeavesBlock;
import lotr.common.block.LOTRMushroomBlock;
import lotr.common.block.LOTROreBlock;
import lotr.common.block.LOTRPillarBlock;
import lotr.common.block.LOTRPlanksBlock;
import lotr.common.block.LOTRPottedPlantBlock;
import lotr.common.block.LOTRSandBlock;
import lotr.common.block.LOTRSandstoneBlock;
import lotr.common.block.LOTRSaplingBlock;
import lotr.common.block.LOTRSignTypes;
import lotr.common.block.LOTRStainedGlassBlock;
import lotr.common.block.LOTRStainedGlassPaneBlock;
import lotr.common.block.LOTRStairsBlock;
import lotr.common.block.LOTRStandingSignBlock;
import lotr.common.block.LOTRStoneBlock;
import lotr.common.block.LOTRStoneButtonBlock;
import lotr.common.block.LOTRStonePressurePlateBlock;
import lotr.common.block.LOTRStrippedLogBlock;
import lotr.common.block.LOTRStrippedWoodBlock;
import lotr.common.block.LOTRTallFlowerBlock;
import lotr.common.block.LOTRTorchBlock;
import lotr.common.block.LOTRTrapdoorBlock;
import lotr.common.block.LOTRWallBlock;
import lotr.common.block.LOTRWallSignBlock;
import lotr.common.block.LOTRWallTorchBlock;
import lotr.common.block.LOTRWoodBarsBlock;
import lotr.common.block.LOTRWoodButtonBlock;
import lotr.common.block.LOTRWoodPressurePlateBlock;
import lotr.common.block.LogSlabBlock;
import lotr.common.block.LogStairsBlock;
import lotr.common.block.MineralBlock;
import lotr.common.block.MirkOakLeavesBlock;
import lotr.common.block.MithrilBlock;
import lotr.common.block.MithrilOreBlock;
import lotr.common.block.MordorDirtBlock;
import lotr.common.block.MordorDirtPathBlock;
import lotr.common.block.MordorGrassBlock;
import lotr.common.block.MordorGravelBlock;
import lotr.common.block.MordorMossBlock;
import lotr.common.block.MordorRockBlock;
import lotr.common.block.MordorThornBlock;
import lotr.common.block.MorgulFlowerBlock;
import lotr.common.block.MorgulShroomBlock;
import lotr.common.block.NettleBlock;
import lotr.common.block.OrcForgeBlock;
import lotr.common.block.OrcPlatingBlock;
import lotr.common.block.PalantirBlock;
import lotr.common.block.PipeweedCropBlock;
import lotr.common.block.PlateBlock;
import lotr.common.block.QuagmireBlock;
import lotr.common.block.ReedsBlock;
import lotr.common.block.RottenLogSlabBlock;
import lotr.common.block.RottenWoodBeamBlock;
import lotr.common.block.RottenWoodBeamSlabBlock;
import lotr.common.block.RushesBlock;
import lotr.common.block.SignSetupHelper;
import lotr.common.block.SnowBrickBlock;
import lotr.common.block.SnowPathBlock;
import lotr.common.block.SoulFireHearthBlock;
import lotr.common.block.StrippableBranchBlock;
import lotr.common.block.StrippableLogBlock;
import lotr.common.block.StrippableLogSlabBlock;
import lotr.common.block.StrippableLogStairsBlock;
import lotr.common.block.StrippableRottenLogBlock;
import lotr.common.block.StrippableRottenLogSlabBlock;
import lotr.common.block.StrippableWoodBlock;
import lotr.common.block.StrippedRottenLogBlock;
import lotr.common.block.ThatchBlock;
import lotr.common.block.ThatchFloorBlock;
import lotr.common.block.ThatchSlabBlock;
import lotr.common.block.ThatchStairsBlock;
import lotr.common.block.ThistleBlock;
import lotr.common.block.ThreeLeafCloverBlock;
import lotr.common.block.TranslucentMineralBlock;
import lotr.common.block.TreasurePileBlock;
import lotr.common.block.VerticalOnlySlabBlock;
import lotr.common.block.VesselDrinkBlock;
import lotr.common.block.WateryTallFlowerBlock;
import lotr.common.block.WattleAndDaubBlock;
import lotr.common.block.WattleAndDaubPillarBlock;
import lotr.common.block.WickerBarsBlock;
import lotr.common.block.WickerFenceBlock;
import lotr.common.block.WickerFenceGateBlock;
import lotr.common.block.WildPipeweedBlock;
import lotr.common.block.WoodBeamBlock;
import lotr.common.block.trees.AppleTree;
import lotr.common.block.trees.AspenTree;
import lotr.common.block.trees.BeechTree;
import lotr.common.block.trees.CedarTree;
import lotr.common.block.trees.CherryTree;
import lotr.common.block.trees.CulumaldaTree;
import lotr.common.block.trees.CypressTree;
import lotr.common.block.trees.FirTree;
import lotr.common.block.trees.GreenOakTree;
import lotr.common.block.trees.HollyTree;
import lotr.common.block.trees.LairelosseTree;
import lotr.common.block.trees.LarchTree;
import lotr.common.block.trees.LebethronTree;
import lotr.common.block.trees.MallornTree;
import lotr.common.block.trees.MapleTree;
import lotr.common.block.trees.MirkOakTree;
import lotr.common.block.trees.PearTree;
import lotr.common.block.trees.PineTree;
import lotr.common.block.trees.RedOakTree;
import lotr.common.world.biome.LOTRBiomeFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRBlocks {
   public static final DeferredRegister BLOCKS;
   public static final int MISC_STEEL_HARVEST_LVL = 1;
   public static final int COPPER_HARVEST_LVL = 1;
   public static final int TIN_HARVEST_LVL = 1;
   public static final int BRONZE_HARVEST_LVL = 1;
   public static final int SILVER_HARVEST_LVL = 2;
   public static final int MITHRIL_HARVEST_LVL = 2;
   public static final int MORGUL_IRON_HARVEST_LVL = 1;
   public static final int DURNOR_HARVEST_LVL = 0;
   public static final int EDHELVIR_HARVEST_LVL = 2;
   public static final int GULDURIL_HARVEST_LVL = 2;
   public static final int GLOWSTONE_HARVEST_LVL = 1;
   public static final int GEM_HARVEST_LVL = 2;
   public static final int SALT_HARVEST_LVL = 0;
   public static final int SULFUR_HARVEST_LVL = 0;
   public static final int NITER_HARVEST_LVL = 0;
   public static final int PALANTIR_HARVEST_LVL = 2;
   public static final SoundType SOUND_CERAMIC;
   public static final SoundType SOUND_TREASURE;
   public static final RegistryObject GONDOR_ROCK;
   public static final RegistryObject GONDOR_BRICK;
   public static final RegistryObject MORDOR_ROCK;
   public static final RegistryObject MORDOR_BRICK;
   public static final RegistryObject ROHAN_ROCK;
   public static final RegistryObject ROHAN_BRICK;
   public static final RegistryObject BLUE_ROCK;
   public static final RegistryObject BLUE_BRICK;
   public static final RegistryObject RED_ROCK;
   public static final RegistryObject RED_BRICK;
   public static final RegistryObject SIMBELMYNE;
   public static final RegistryObject SHIRE_HEATHER;
   public static final RegistryObject COPPER_ORE;
   public static final RegistryObject TIN_ORE;
   public static final RegistryObject SILVER_ORE;
   public static final RegistryObject MITHRIL_ORE;
   public static final RegistryObject COPPER_BLOCK;
   public static final RegistryObject TIN_BLOCK;
   public static final RegistryObject SILVER_BLOCK;
   public static final RegistryObject MITHRIL_BLOCK;
   public static final RegistryObject BRONZE_BLOCK;
   public static final RegistryObject POTTED_SIMBELMYNE;
   public static final RegistryObject POTTED_SHIRE_HEATHER;
   public static final RegistryObject GONDOR_ROCK_SLAB;
   public static final RegistryObject GONDOR_ROCK_STAIRS;
   public static final RegistryObject GONDOR_ROCK_WALL;
   public static final RegistryObject GONDOR_CRAFTING_TABLE;
   public static final RegistryObject MORDOR_CRAFTING_TABLE;
   public static final RegistryObject ROHAN_CRAFTING_TABLE;
   public static final RegistryObject PINE_LOG;
   public static final RegistryObject PINE_WOOD;
   public static final RegistryObject PINE_PLANKS;
   public static final RegistryObject PINE_LEAVES;
   public static final RegistryObject PINE_SAPLING;
   public static final RegistryObject POTTED_PINE_SAPLING;
   public static final RegistryObject PINE_SLAB;
   public static final RegistryObject PINE_STAIRS;
   public static final RegistryObject PINE_FENCE;
   public static final RegistryObject PINE_FENCE_GATE;
   public static final RegistryObject PINE_DOOR;
   public static final RegistryObject PINE_TRAPDOOR;
   public static final RegistryObject PINE_PRESSURE_PLATE;
   public static final RegistryObject PINE_BUTTON;
   public static final RegistryObject STRIPPED_PINE_LOG;
   public static final RegistryObject STRIPPED_PINE_WOOD;
   public static final RegistryObject PINE_BEAM;
   public static final RegistryObject PINE_SIGN;
   public static final RegistryObject PINE_WALL_SIGN;
   public static final RegistryObject MALLORN_LOG;
   public static final RegistryObject MALLORN_WOOD;
   public static final RegistryObject MALLORN_PLANKS;
   public static final RegistryObject MALLORN_LEAVES;
   public static final RegistryObject MALLORN_SAPLING;
   public static final RegistryObject POTTED_MALLORN_SAPLING;
   public static final RegistryObject MALLORN_SLAB;
   public static final RegistryObject MALLORN_STAIRS;
   public static final RegistryObject MALLORN_FENCE;
   public static final RegistryObject MALLORN_FENCE_GATE;
   public static final RegistryObject MALLORN_DOOR;
   public static final RegistryObject MALLORN_TRAPDOOR;
   public static final RegistryObject MALLORN_PRESSURE_PLATE;
   public static final RegistryObject MALLORN_BUTTON;
   public static final RegistryObject STRIPPED_MALLORN_LOG;
   public static final RegistryObject STRIPPED_MALLORN_WOOD;
   public static final RegistryObject MALLORN_BEAM;
   public static final RegistryObject MALLORN_SIGN;
   public static final RegistryObject MALLORN_WALL_SIGN;
   public static final RegistryObject DWARVEN_BRICK;
   public static final RegistryObject HIGH_ELVEN_BRICK;
   public static final RegistryObject GALADHRIM_BRICK;
   public static final RegistryObject WOOD_ELVEN_BRICK;
   public static final RegistryObject DWARVEN_CRAFTING_TABLE;
   public static final RegistryObject LINDON_CRAFTING_TABLE;
   public static final RegistryObject RIVENDELL_CRAFTING_TABLE;
   public static final RegistryObject GALADHRIM_CRAFTING_TABLE;
   public static final RegistryObject WOOD_ELVEN_CRAFTING_TABLE;
   public static final RegistryObject APPLE_CRUMBLE;
   public static final RegistryObject SALT_ORE;
   public static final RegistryObject SULFUR_ORE;
   public static final RegistryObject NITER_ORE;
   public static final RegistryObject SALT_BLOCK;
   public static final RegistryObject SULFUR_BLOCK;
   public static final RegistryObject NITER_BLOCK;
   public static final RegistryObject MORGUL_IRON_ORE_MORDOR;
   public static final RegistryObject MORGUL_IRON_ORE_STONE;
   public static final RegistryObject DURNOR_ORE;
   public static final RegistryObject ORC_STEEL_BLOCK;
   public static final RegistryObject DURNOR_BLOCK;
   public static final RegistryObject HARAD_BRICK;
   public static final RegistryObject RED_HARAD_BRICK;
   public static final RegistryObject HARAD_CRAFTING_TABLE;
   public static final RegistryObject GONDOR_BRICK_SLAB;
   public static final RegistryObject GONDOR_BRICK_STAIRS;
   public static final RegistryObject GONDOR_BRICK_WALL;
   public static final RegistryObject MORDOR_ROCK_SLAB;
   public static final RegistryObject MORDOR_ROCK_STAIRS;
   public static final RegistryObject MORDOR_ROCK_WALL;
   public static final RegistryObject MORDOR_BRICK_SLAB;
   public static final RegistryObject MORDOR_BRICK_STAIRS;
   public static final RegistryObject MORDOR_BRICK_WALL;
   public static final RegistryObject ROHAN_ROCK_SLAB;
   public static final RegistryObject ROHAN_ROCK_STAIRS;
   public static final RegistryObject ROHAN_ROCK_WALL;
   public static final RegistryObject ROHAN_BRICK_SLAB;
   public static final RegistryObject ROHAN_BRICK_STAIRS;
   public static final RegistryObject ROHAN_BRICK_WALL;
   public static final RegistryObject BLUE_ROCK_SLAB;
   public static final RegistryObject BLUE_ROCK_STAIRS;
   public static final RegistryObject BLUE_ROCK_WALL;
   public static final RegistryObject BLUE_BRICK_SLAB;
   public static final RegistryObject BLUE_BRICK_STAIRS;
   public static final RegistryObject BLUE_BRICK_WALL;
   public static final RegistryObject RED_ROCK_SLAB;
   public static final RegistryObject RED_ROCK_STAIRS;
   public static final RegistryObject RED_ROCK_WALL;
   public static final RegistryObject RED_BRICK_SLAB;
   public static final RegistryObject RED_BRICK_STAIRS;
   public static final RegistryObject RED_BRICK_WALL;
   public static final RegistryObject DWARVEN_BRICK_SLAB;
   public static final RegistryObject DWARVEN_BRICK_STAIRS;
   public static final RegistryObject DWARVEN_BRICK_WALL;
   public static final RegistryObject HIGH_ELVEN_BRICK_SLAB;
   public static final RegistryObject HIGH_ELVEN_BRICK_STAIRS;
   public static final RegistryObject HIGH_ELVEN_BRICK_WALL;
   public static final RegistryObject GALADHRIM_BRICK_SLAB;
   public static final RegistryObject GALADHRIM_BRICK_STAIRS;
   public static final RegistryObject GALADHRIM_BRICK_WALL;
   public static final RegistryObject WOOD_ELVEN_BRICK_SLAB;
   public static final RegistryObject WOOD_ELVEN_BRICK_STAIRS;
   public static final RegistryObject WOOD_ELVEN_BRICK_WALL;
   public static final RegistryObject HARAD_BRICK_SLAB;
   public static final RegistryObject HARAD_BRICK_STAIRS;
   public static final RegistryObject HARAD_BRICK_WALL;
   public static final RegistryObject RED_HARAD_BRICK_SLAB;
   public static final RegistryObject RED_HARAD_BRICK_STAIRS;
   public static final RegistryObject RED_HARAD_BRICK_WALL;
   public static final RegistryObject DWARVEN_STEEL_BLOCK;
   public static final RegistryObject ELVEN_STEEL_BLOCK;
   public static final RegistryObject MIRK_OAK_LOG;
   public static final RegistryObject MIRK_OAK_WOOD;
   public static final RegistryObject MIRK_OAK_PLANKS;
   public static final RegistryObject MIRK_OAK_LEAVES;
   public static final RegistryObject MIRK_OAK_SAPLING;
   public static final RegistryObject POTTED_MIRK_OAK_SAPLING;
   public static final RegistryObject MIRK_OAK_SLAB;
   public static final RegistryObject MIRK_OAK_STAIRS;
   public static final RegistryObject MIRK_OAK_FENCE;
   public static final RegistryObject MIRK_OAK_FENCE_GATE;
   public static final RegistryObject MIRK_OAK_DOOR;
   public static final RegistryObject MIRK_OAK_TRAPDOOR;
   public static final RegistryObject MIRK_OAK_PRESSURE_PLATE;
   public static final RegistryObject MIRK_OAK_BUTTON;
   public static final RegistryObject STRIPPED_MIRK_OAK_LOG;
   public static final RegistryObject STRIPPED_MIRK_OAK_WOOD;
   public static final RegistryObject MIRK_OAK_BEAM;
   public static final RegistryObject MIRK_OAK_SIGN;
   public static final RegistryObject MIRK_OAK_WALL_SIGN;
   public static final RegistryObject CHARRED_LOG;
   public static final RegistryObject CHARRED_WOOD;
   public static final RegistryObject CHARRED_PLANKS;
   public static final RegistryObject CHARRED_SLAB;
   public static final RegistryObject CHARRED_STAIRS;
   public static final RegistryObject CHARRED_FENCE;
   public static final RegistryObject CHARRED_FENCE_GATE;
   public static final RegistryObject CHARRED_DOOR;
   public static final RegistryObject CHARRED_TRAPDOOR;
   public static final RegistryObject CHARRED_PRESSURE_PLATE;
   public static final RegistryObject CHARRED_BUTTON;
   public static final RegistryObject STRIPPED_CHARRED_LOG;
   public static final RegistryObject STRIPPED_CHARRED_WOOD;
   public static final RegistryObject CHARRED_BEAM;
   public static final RegistryObject CHARRED_SIGN;
   public static final RegistryObject CHARRED_WALL_SIGN;
   public static final RegistryObject APPLE_LOG;
   public static final RegistryObject APPLE_WOOD;
   public static final RegistryObject APPLE_PLANKS;
   public static final RegistryObject APPLE_LEAVES;
   public static final RegistryObject APPLE_LEAVES_RED;
   public static final RegistryObject APPLE_LEAVES_GREEN;
   public static final RegistryObject APPLE_SAPLING;
   public static final RegistryObject POTTED_APPLE_SAPLING;
   public static final RegistryObject APPLE_SLAB;
   public static final RegistryObject APPLE_STAIRS;
   public static final RegistryObject APPLE_FENCE;
   public static final RegistryObject APPLE_FENCE_GATE;
   public static final RegistryObject APPLE_DOOR;
   public static final RegistryObject APPLE_TRAPDOOR;
   public static final RegistryObject APPLE_PRESSURE_PLATE;
   public static final RegistryObject APPLE_BUTTON;
   public static final RegistryObject STRIPPED_APPLE_LOG;
   public static final RegistryObject STRIPPED_APPLE_WOOD;
   public static final RegistryObject APPLE_BEAM;
   public static final RegistryObject APPLE_SIGN;
   public static final RegistryObject APPLE_WALL_SIGN;
   public static final RegistryObject PEAR_LOG;
   public static final RegistryObject PEAR_WOOD;
   public static final RegistryObject PEAR_PLANKS;
   public static final RegistryObject PEAR_LEAVES;
   public static final RegistryObject PEAR_LEAVES_FRUIT;
   public static final RegistryObject PEAR_SAPLING;
   public static final RegistryObject POTTED_PEAR_SAPLING;
   public static final RegistryObject PEAR_SLAB;
   public static final RegistryObject PEAR_STAIRS;
   public static final RegistryObject PEAR_FENCE;
   public static final RegistryObject PEAR_FENCE_GATE;
   public static final RegistryObject PEAR_DOOR;
   public static final RegistryObject PEAR_TRAPDOOR;
   public static final RegistryObject PEAR_PRESSURE_PLATE;
   public static final RegistryObject PEAR_BUTTON;
   public static final RegistryObject STRIPPED_PEAR_LOG;
   public static final RegistryObject STRIPPED_PEAR_WOOD;
   public static final RegistryObject PEAR_BEAM;
   public static final RegistryObject PEAR_SIGN;
   public static final RegistryObject PEAR_WALL_SIGN;
   public static final RegistryObject CHERRY_LOG;
   public static final RegistryObject CHERRY_WOOD;
   public static final RegistryObject CHERRY_PLANKS;
   public static final RegistryObject CHERRY_LEAVES;
   public static final RegistryObject CHERRY_LEAVES_FRUIT;
   public static final RegistryObject CHERRY_SAPLING;
   public static final RegistryObject POTTED_CHERRY_SAPLING;
   public static final RegistryObject CHERRY_SLAB;
   public static final RegistryObject CHERRY_STAIRS;
   public static final RegistryObject CHERRY_FENCE;
   public static final RegistryObject CHERRY_FENCE_GATE;
   public static final RegistryObject CHERRY_DOOR;
   public static final RegistryObject CHERRY_TRAPDOOR;
   public static final RegistryObject CHERRY_PRESSURE_PLATE;
   public static final RegistryObject CHERRY_BUTTON;
   public static final RegistryObject STRIPPED_CHERRY_LOG;
   public static final RegistryObject STRIPPED_CHERRY_WOOD;
   public static final RegistryObject CHERRY_BEAM;
   public static final RegistryObject CHERRY_SIGN;
   public static final RegistryObject CHERRY_WALL_SIGN;
   public static final RegistryObject LEBETHRON_LOG;
   public static final RegistryObject LEBETHRON_WOOD;
   public static final RegistryObject LEBETHRON_PLANKS;
   public static final RegistryObject LEBETHRON_LEAVES;
   public static final RegistryObject LEBETHRON_SAPLING;
   public static final RegistryObject POTTED_LEBETHRON_SAPLING;
   public static final RegistryObject LEBETHRON_SLAB;
   public static final RegistryObject LEBETHRON_STAIRS;
   public static final RegistryObject LEBETHRON_FENCE;
   public static final RegistryObject LEBETHRON_FENCE_GATE;
   public static final RegistryObject LEBETHRON_DOOR;
   public static final RegistryObject LEBETHRON_TRAPDOOR;
   public static final RegistryObject LEBETHRON_PRESSURE_PLATE;
   public static final RegistryObject LEBETHRON_BUTTON;
   public static final RegistryObject STRIPPED_LEBETHRON_LOG;
   public static final RegistryObject STRIPPED_LEBETHRON_WOOD;
   public static final RegistryObject LEBETHRON_BEAM;
   public static final RegistryObject LEBETHRON_SIGN;
   public static final RegistryObject LEBETHRON_WALL_SIGN;
   public static final RegistryObject BEECH_LOG;
   public static final RegistryObject BEECH_WOOD;
   public static final RegistryObject BEECH_PLANKS;
   public static final RegistryObject BEECH_LEAVES;
   public static final RegistryObject BEECH_SAPLING;
   public static final RegistryObject POTTED_BEECH_SAPLING;
   public static final RegistryObject BEECH_SLAB;
   public static final RegistryObject BEECH_STAIRS;
   public static final RegistryObject BEECH_FENCE;
   public static final RegistryObject BEECH_FENCE_GATE;
   public static final RegistryObject BEECH_DOOR;
   public static final RegistryObject BEECH_TRAPDOOR;
   public static final RegistryObject BEECH_PRESSURE_PLATE;
   public static final RegistryObject BEECH_BUTTON;
   public static final RegistryObject STRIPPED_BEECH_LOG;
   public static final RegistryObject STRIPPED_BEECH_WOOD;
   public static final RegistryObject BEECH_BEAM;
   public static final RegistryObject BEECH_SIGN;
   public static final RegistryObject BEECH_WALL_SIGN;
   public static final RegistryObject CHERRY_PIE;
   public static final RegistryObject HEARTH_BLOCK;
   public static final RegistryObject MALLORN_LADDER;
   public static final RegistryObject ELANOR;
   public static final RegistryObject NIPHREDIL;
   public static final RegistryObject POTTED_ELANOR;
   public static final RegistryObject POTTED_NIPHREDIL;
   public static final RegistryObject BLUEBELL;
   public static final RegistryObject MARIGOLD;
   public static final RegistryObject ASPHODEL;
   public static final RegistryObject LAVENDER;
   public static final RegistryObject POTTED_BLUEBELL;
   public static final RegistryObject POTTED_MARIGOLD;
   public static final RegistryObject POTTED_ASPHODEL;
   public static final RegistryObject POTTED_LAVENDER;
   public static final RegistryObject MORDOR_GRAVEL;
   public static final RegistryObject MORDOR_DIRT;
   public static final RegistryObject MORDOR_MOSS;
   public static final RegistryObject MORDOR_GRASS;
   public static final RegistryObject MORDOR_THORN;
   public static final RegistryObject POTTED_MORDOR_THORN;
   public static final RegistryObject DWARVEN_PILLAR;
   public static final RegistryObject DWARVEN_PILLAR_SLAB;
   public static final RegistryObject STONE_PILLAR;
   public static final RegistryObject STONE_PILLAR_SLAB;
   public static final RegistryObject GONDOR_PILLAR;
   public static final RegistryObject GONDOR_PILLAR_SLAB;
   public static final RegistryObject GLOWSTONE_ORE;
   public static final RegistryObject GLOWING_DWARVEN_BRICK;
   public static final RegistryObject MORDOR_PILLAR;
   public static final RegistryObject MORDOR_PILLAR_SLAB;
   public static final RegistryObject ROHAN_PILLAR;
   public static final RegistryObject ROHAN_PILLAR_SLAB;
   public static final RegistryObject BLUE_PILLAR;
   public static final RegistryObject BLUE_PILLAR_SLAB;
   public static final RegistryObject RED_PILLAR;
   public static final RegistryObject RED_PILLAR_SLAB;
   public static final RegistryObject HIGH_ELVEN_PILLAR;
   public static final RegistryObject HIGH_ELVEN_PILLAR_SLAB;
   public static final RegistryObject GALADHRIM_PILLAR;
   public static final RegistryObject GALADHRIM_PILLAR_SLAB;
   public static final RegistryObject WOOD_ELVEN_PILLAR;
   public static final RegistryObject WOOD_ELVEN_PILLAR_SLAB;
   public static final RegistryObject HARAD_PILLAR;
   public static final RegistryObject HARAD_PILLAR_SLAB;
   public static final RegistryObject RED_HARAD_PILLAR;
   public static final RegistryObject RED_HARAD_PILLAR_SLAB;
   public static final RegistryObject UMBAR_BRICK;
   public static final RegistryObject UMBAR_BRICK_SLAB;
   public static final RegistryObject UMBAR_BRICK_STAIRS;
   public static final RegistryObject UMBAR_BRICK_WALL;
   public static final RegistryObject UMBAR_CRAFTING_TABLE;
   public static final RegistryObject UMBAR_PILLAR;
   public static final RegistryObject UMBAR_PILLAR_SLAB;
   public static final RegistryObject DRIPSTONE;
   public static final RegistryObject MORDOR_DRIPSTONE;
   public static final RegistryObject OBSIDIAN_DRIPSTONE;
   public static final RegistryObject ICE_DRIPSTONE;
   public static final RegistryObject GONDOR_DRIPSTONE;
   public static final RegistryObject ROHAN_DRIPSTONE;
   public static final RegistryObject BLUE_DRIPSTONE;
   public static final RegistryObject RED_DRIPSTONE;
   public static final RegistryObject ANDESITE_DRIPSTONE;
   public static final RegistryObject DIORITE_DRIPSTONE;
   public static final RegistryObject GRANITE_DRIPSTONE;
   public static final RegistryObject OAK_BEAM;
   public static final RegistryObject SPRUCE_BEAM;
   public static final RegistryObject BIRCH_BEAM;
   public static final RegistryObject JUNGLE_BEAM;
   public static final RegistryObject ACACIA_BEAM;
   public static final RegistryObject DARK_OAK_BEAM;
   public static final RegistryObject ORC_TORCH;
   public static final RegistryObject ORC_BARS;
   public static final RegistryObject DWARVEN_BARS;
   public static final RegistryObject BRONZE_BARS;
   public static final RegistryObject SILVER_BARS;
   public static final RegistryObject GOLD_BARS;
   public static final RegistryObject MITHRIL_BARS;
   public static final RegistryObject HIGH_ELVEN_BARS;
   public static final RegistryObject GALADHRIM_BARS;
   public static final RegistryObject WOOD_ELVEN_BARS;
   public static final RegistryObject HIGH_ELVEN_WOOD_BARS;
   public static final RegistryObject GALADHRIM_WOOD_BARS;
   public static final RegistryObject WOOD_ELVEN_WOOD_BARS;
   public static final RegistryObject CRACKED_STONE_BRICK_SLAB;
   public static final RegistryObject CRACKED_STONE_BRICK_STAIRS;
   public static final RegistryObject CRACKED_STONE_BRICK_WALL;
   public static final RegistryObject CLOVER;
   public static final RegistryObject FOUR_LEAF_CLOVER;
   public static final RegistryObject POTTED_CLOVER;
   public static final RegistryObject POTTED_FOUR_LEAF_CLOVER;
   public static final RegistryObject SHORT_GRASS;
   public static final RegistryObject WHEATGRASS;
   public static final RegistryObject FLOWERY_GRASS;
   public static final RegistryObject MAPLE_LOG;
   public static final RegistryObject MAPLE_WOOD;
   public static final RegistryObject MAPLE_PLANKS;
   public static final RegistryObject MAPLE_LEAVES;
   public static final RegistryObject MAPLE_SAPLING;
   public static final RegistryObject POTTED_MAPLE_SAPLING;
   public static final RegistryObject MAPLE_SLAB;
   public static final RegistryObject MAPLE_STAIRS;
   public static final RegistryObject MAPLE_FENCE;
   public static final RegistryObject MAPLE_FENCE_GATE;
   public static final RegistryObject MAPLE_DOOR;
   public static final RegistryObject MAPLE_TRAPDOOR;
   public static final RegistryObject MAPLE_PRESSURE_PLATE;
   public static final RegistryObject MAPLE_BUTTON;
   public static final RegistryObject STRIPPED_MAPLE_LOG;
   public static final RegistryObject STRIPPED_MAPLE_WOOD;
   public static final RegistryObject MAPLE_BEAM;
   public static final RegistryObject MAPLE_SIGN;
   public static final RegistryObject MAPLE_WALL_SIGN;
   public static final RegistryObject THISTLE;
   public static final RegistryObject NETTLES;
   public static final RegistryObject FERNSPROUT;
   public static final RegistryObject CANDLE;
   public static final RegistryObject GOLD_CHANDELIER;
   public static final RegistryObject IRON_CHANDELIER;
   public static final RegistryObject BRONZE_CHANDELIER;
   public static final RegistryObject SILVER_CHANDELIER;
   public static final RegistryObject MITHRIL_CHANDELIER;
   public static final RegistryObject ATHELAS;
   public static final RegistryObject DWARFWORT;
   public static final RegistryObject WILD_PIPEWEED;
   public static final RegistryObject WILD_FLAX;
   public static final RegistryObject POTTED_ATHELAS;
   public static final RegistryObject POTTED_DWARFWORT;
   public static final RegistryObject POTTED_WILD_PIPEWEED;
   public static final RegistryObject POTTED_WILD_FLAX;
   public static final RegistryObject POTTED_THISTLE;
   public static final RegistryObject POTTED_NETTLES;
   public static final RegistryObject POTTED_FERNSPROUT;
   public static final RegistryObject ASPEN_LOG;
   public static final RegistryObject ASPEN_WOOD;
   public static final RegistryObject ASPEN_PLANKS;
   public static final RegistryObject ASPEN_LEAVES;
   public static final RegistryObject ASPEN_SAPLING;
   public static final RegistryObject POTTED_ASPEN_SAPLING;
   public static final RegistryObject ASPEN_SLAB;
   public static final RegistryObject ASPEN_STAIRS;
   public static final RegistryObject ASPEN_FENCE;
   public static final RegistryObject ASPEN_FENCE_GATE;
   public static final RegistryObject ASPEN_DOOR;
   public static final RegistryObject ASPEN_TRAPDOOR;
   public static final RegistryObject ASPEN_PRESSURE_PLATE;
   public static final RegistryObject ASPEN_BUTTON;
   public static final RegistryObject STRIPPED_ASPEN_LOG;
   public static final RegistryObject STRIPPED_ASPEN_WOOD;
   public static final RegistryObject ASPEN_BEAM;
   public static final RegistryObject ASPEN_SIGN;
   public static final RegistryObject ASPEN_WALL_SIGN;
   public static final RegistryObject LAIRELOSSE_LOG;
   public static final RegistryObject LAIRELOSSE_WOOD;
   public static final RegistryObject LAIRELOSSE_PLANKS;
   public static final RegistryObject LAIRELOSSE_LEAVES;
   public static final RegistryObject LAIRELOSSE_SAPLING;
   public static final RegistryObject POTTED_LAIRELOSSE_SAPLING;
   public static final RegistryObject LAIRELOSSE_SLAB;
   public static final RegistryObject LAIRELOSSE_STAIRS;
   public static final RegistryObject LAIRELOSSE_FENCE;
   public static final RegistryObject LAIRELOSSE_FENCE_GATE;
   public static final RegistryObject LAIRELOSSE_DOOR;
   public static final RegistryObject LAIRELOSSE_TRAPDOOR;
   public static final RegistryObject LAIRELOSSE_PRESSURE_PLATE;
   public static final RegistryObject LAIRELOSSE_BUTTON;
   public static final RegistryObject STRIPPED_LAIRELOSSE_LOG;
   public static final RegistryObject STRIPPED_LAIRELOSSE_WOOD;
   public static final RegistryObject LAIRELOSSE_BEAM;
   public static final RegistryObject LAIRELOSSE_SIGN;
   public static final RegistryObject LAIRELOSSE_WALL_SIGN;
   public static final RegistryObject CEDAR_LOG;
   public static final RegistryObject CEDAR_WOOD;
   public static final RegistryObject CEDAR_PLANKS;
   public static final RegistryObject CEDAR_LEAVES;
   public static final RegistryObject CEDAR_SAPLING;
   public static final RegistryObject POTTED_CEDAR_SAPLING;
   public static final RegistryObject CEDAR_SLAB;
   public static final RegistryObject CEDAR_STAIRS;
   public static final RegistryObject CEDAR_FENCE;
   public static final RegistryObject CEDAR_FENCE_GATE;
   public static final RegistryObject CEDAR_DOOR;
   public static final RegistryObject CEDAR_TRAPDOOR;
   public static final RegistryObject CEDAR_PRESSURE_PLATE;
   public static final RegistryObject CEDAR_BUTTON;
   public static final RegistryObject STRIPPED_CEDAR_LOG;
   public static final RegistryObject STRIPPED_CEDAR_WOOD;
   public static final RegistryObject CEDAR_BEAM;
   public static final RegistryObject CEDAR_SIGN;
   public static final RegistryObject CEDAR_WALL_SIGN;
   public static final RegistryObject PIPEWEED_CROP;
   public static final RegistryObject FLAX_CROP;
   public static final RegistryObject LETTUCE;
   public static final RegistryObject BLUE_MALLORN_TORCH;
   public static final RegistryObject BLUE_MALLORN_WALL_TORCH;
   public static final RegistryObject GREEN_MALLORN_TORCH;
   public static final RegistryObject GREEN_MALLORN_WALL_TORCH;
   public static final RegistryObject GOLD_MALLORN_TORCH;
   public static final RegistryObject GOLD_MALLORN_WALL_TORCH;
   public static final RegistryObject SILVER_MALLORN_TORCH;
   public static final RegistryObject SILVER_MALLORN_WALL_TORCH;
   public static final RegistryObject SANDSTONE_DRIPSTONE;
   public static final RegistryObject RED_SANDSTONE_DRIPSTONE;
   public static final RegistryObject EDHELVIR_ORE;
   public static final RegistryObject EDHELVIR_BLOCK;
   public static final RegistryObject HIGH_ELVEN_TORCH;
   public static final RegistryObject HIGH_ELVEN_WALL_TORCH;
   public static final RegistryObject STONE_WALL;
   public static final RegistryObject RED_SAND_GEM;
   public static final RegistryObject YELLOW_SAND_GEM;
   public static final RegistryObject HARAD_DAISY;
   public static final RegistryObject SOUTHBELL;
   public static final RegistryObject POTTED_RED_SAND_GEM;
   public static final RegistryObject POTTED_YELLOW_SAND_GEM;
   public static final RegistryObject POTTED_HARAD_DAISY;
   public static final RegistryObject POTTED_SOUTHBELL;
   public static final RegistryObject HIBISCUS;
   public static final RegistryObject FLAME_OF_HARAD;
   public static final RegistryObject GULDURIL_ORE_MORDOR;
   public static final RegistryObject GULDURIL_ORE_STONE;
   public static final RegistryObject GULDURIL_BLOCK;
   public static final RegistryObject EDHELVIR_CRYSTAL;
   public static final RegistryObject GULDURIL_CRYSTAL;
   public static final RegistryObject GLOWSTONE_CRYSTAL;
   public static final RegistryObject NUMENOREAN_BRICK;
   public static final RegistryObject NUMENOREAN_BRICK_SLAB;
   public static final RegistryObject NUMENOREAN_BRICK_STAIRS;
   public static final RegistryObject NUMENOREAN_BRICK_WALL;
   public static final RegistryObject NUMENOREAN_PILLAR;
   public static final RegistryObject NUMENOREAN_PILLAR_SLAB;
   public static final RegistryObject ALLOY_FORGE;
   public static final RegistryObject DWARVEN_FORGE;
   public static final RegistryObject ELVEN_FORGE;
   public static final RegistryObject ORC_FORGE;
   public static final RegistryObject URUK_BRICK;
   public static final RegistryObject URUK_BRICK_SLAB;
   public static final RegistryObject URUK_BRICK_STAIRS;
   public static final RegistryObject URUK_BRICK_WALL;
   public static final RegistryObject URUK_PILLAR;
   public static final RegistryObject URUK_PILLAR_SLAB;
   public static final RegistryObject URUK_CRAFTING_TABLE;
   public static final RegistryObject URUK_STEEL_BLOCK;
   public static final RegistryObject URUK_BARS;
   public static final RegistryObject HOBBIT_CRAFTING_TABLE;
   public static final RegistryObject BRICK_PILLAR;
   public static final RegistryObject BRICK_PILLAR_SLAB;
   public static final RegistryObject SHIRE_PINE_DOOR;
   public static final RegistryObject SHIRE_PINE_TRAPDOOR;
   public static final RegistryObject FIR_LOG;
   public static final RegistryObject FIR_WOOD;
   public static final RegistryObject FIR_PLANKS;
   public static final RegistryObject FIR_LEAVES;
   public static final RegistryObject FIR_SAPLING;
   public static final RegistryObject POTTED_FIR_SAPLING;
   public static final RegistryObject FIR_SLAB;
   public static final RegistryObject FIR_STAIRS;
   public static final RegistryObject FIR_FENCE;
   public static final RegistryObject FIR_FENCE_GATE;
   public static final RegistryObject FIR_DOOR;
   public static final RegistryObject FIR_TRAPDOOR;
   public static final RegistryObject FIR_PRESSURE_PLATE;
   public static final RegistryObject FIR_BUTTON;
   public static final RegistryObject STRIPPED_FIR_LOG;
   public static final RegistryObject STRIPPED_FIR_WOOD;
   public static final RegistryObject FIR_BEAM;
   public static final RegistryObject FIR_SIGN;
   public static final RegistryObject FIR_WALL_SIGN;
   public static final RegistryObject LARCH_LOG;
   public static final RegistryObject LARCH_WOOD;
   public static final RegistryObject LARCH_PLANKS;
   public static final RegistryObject LARCH_LEAVES;
   public static final RegistryObject LARCH_SAPLING;
   public static final RegistryObject POTTED_LARCH_SAPLING;
   public static final RegistryObject LARCH_SLAB;
   public static final RegistryObject LARCH_STAIRS;
   public static final RegistryObject LARCH_FENCE;
   public static final RegistryObject LARCH_FENCE_GATE;
   public static final RegistryObject LARCH_DOOR;
   public static final RegistryObject LARCH_TRAPDOOR;
   public static final RegistryObject LARCH_PRESSURE_PLATE;
   public static final RegistryObject LARCH_BUTTON;
   public static final RegistryObject STRIPPED_LARCH_LOG;
   public static final RegistryObject STRIPPED_LARCH_WOOD;
   public static final RegistryObject LARCH_BEAM;
   public static final RegistryObject LARCH_SIGN;
   public static final RegistryObject LARCH_WALL_SIGN;
   public static final RegistryObject HOBBIT_OVEN;
   public static final RegistryObject FINE_PLATE;
   public static final RegistryObject STONEWARE_PLATE;
   public static final RegistryObject WOODEN_PLATE;
   public static final RegistryObject MOSSY_GONDOR_BRICK;
   public static final RegistryObject MOSSY_GONDOR_BRICK_SLAB;
   public static final RegistryObject MOSSY_GONDOR_BRICK_STAIRS;
   public static final RegistryObject MOSSY_GONDOR_BRICK_WALL;
   public static final RegistryObject CRACKED_GONDOR_BRICK;
   public static final RegistryObject CRACKED_GONDOR_BRICK_SLAB;
   public static final RegistryObject CRACKED_GONDOR_BRICK_STAIRS;
   public static final RegistryObject CRACKED_GONDOR_BRICK_WALL;
   public static final RegistryObject CARVED_GONDOR_BRICK;
   public static final RegistryObject MOSSY_HIGH_ELVEN_BRICK;
   public static final RegistryObject MOSSY_HIGH_ELVEN_BRICK_SLAB;
   public static final RegistryObject MOSSY_HIGH_ELVEN_BRICK_STAIRS;
   public static final RegistryObject MOSSY_HIGH_ELVEN_BRICK_WALL;
   public static final RegistryObject CRACKED_HIGH_ELVEN_BRICK;
   public static final RegistryObject CRACKED_HIGH_ELVEN_BRICK_SLAB;
   public static final RegistryObject CRACKED_HIGH_ELVEN_BRICK_STAIRS;
   public static final RegistryObject CRACKED_HIGH_ELVEN_BRICK_WALL;
   public static final RegistryObject CARVED_HIGH_ELVEN_BRICK;
   public static final RegistryObject THATCH;
   public static final RegistryObject THATCH_SLAB;
   public static final RegistryObject THATCH_STAIRS;
   public static final RegistryObject DRYSTONE;
   public static final RegistryObject WATTLE_AND_DAUB;
   public static final RegistryObject WATTLE_AND_DAUB_PILLAR;
   public static final RegistryObject MOSSY_MORDOR_BRICK;
   public static final RegistryObject MOSSY_MORDOR_BRICK_SLAB;
   public static final RegistryObject MOSSY_MORDOR_BRICK_STAIRS;
   public static final RegistryObject MOSSY_MORDOR_BRICK_WALL;
   public static final RegistryObject CRACKED_MORDOR_BRICK;
   public static final RegistryObject CRACKED_MORDOR_BRICK_SLAB;
   public static final RegistryObject CRACKED_MORDOR_BRICK_STAIRS;
   public static final RegistryObject CRACKED_MORDOR_BRICK_WALL;
   public static final RegistryObject CARVED_MORDOR_BRICK;
   public static final RegistryObject MOSSY_ROHAN_BRICK;
   public static final RegistryObject MOSSY_ROHAN_BRICK_SLAB;
   public static final RegistryObject MOSSY_ROHAN_BRICK_STAIRS;
   public static final RegistryObject MOSSY_ROHAN_BRICK_WALL;
   public static final RegistryObject CRACKED_ROHAN_BRICK;
   public static final RegistryObject CRACKED_ROHAN_BRICK_SLAB;
   public static final RegistryObject CRACKED_ROHAN_BRICK_STAIRS;
   public static final RegistryObject CRACKED_ROHAN_BRICK_WALL;
   public static final RegistryObject CARVED_ROHAN_BRICK;
   public static final RegistryObject GOLD_TRIMMED_DWARVEN_BRICK;
   public static final RegistryObject SILVER_TRIMMED_DWARVEN_BRICK;
   public static final RegistryObject MITHRIL_TRIMMED_DWARVEN_BRICK;
   public static final RegistryObject MOSSY_NUMENOREAN_BRICK;
   public static final RegistryObject MOSSY_NUMENOREAN_BRICK_SLAB;
   public static final RegistryObject MOSSY_NUMENOREAN_BRICK_STAIRS;
   public static final RegistryObject MOSSY_NUMENOREAN_BRICK_WALL;
   public static final RegistryObject CRACKED_NUMENOREAN_BRICK;
   public static final RegistryObject CRACKED_NUMENOREAN_BRICK_SLAB;
   public static final RegistryObject CRACKED_NUMENOREAN_BRICK_STAIRS;
   public static final RegistryObject CRACKED_NUMENOREAN_BRICK_WALL;
   public static final RegistryObject KEG;
   public static final RegistryObject MORGUL_SHROOM;
   public static final RegistryObject POTTED_MORGUL_SHROOM;
   public static final RegistryObject MOSSY_BLUE_BRICK;
   public static final RegistryObject MOSSY_BLUE_BRICK_SLAB;
   public static final RegistryObject MOSSY_BLUE_BRICK_STAIRS;
   public static final RegistryObject MOSSY_BLUE_BRICK_WALL;
   public static final RegistryObject CRACKED_BLUE_BRICK;
   public static final RegistryObject CRACKED_BLUE_BRICK_SLAB;
   public static final RegistryObject CRACKED_BLUE_BRICK_STAIRS;
   public static final RegistryObject CRACKED_BLUE_BRICK_WALL;
   public static final RegistryObject CARVED_BLUE_BRICK;
   public static final RegistryObject MOSSY_RED_BRICK;
   public static final RegistryObject MOSSY_RED_BRICK_SLAB;
   public static final RegistryObject MOSSY_RED_BRICK_STAIRS;
   public static final RegistryObject MOSSY_RED_BRICK_WALL;
   public static final RegistryObject CRACKED_RED_BRICK;
   public static final RegistryObject CRACKED_RED_BRICK_SLAB;
   public static final RegistryObject CRACKED_RED_BRICK_STAIRS;
   public static final RegistryObject CRACKED_RED_BRICK_WALL;
   public static final RegistryObject CARVED_RED_BRICK;
   public static final RegistryObject SMOOTH_BLUE_ROCK;
   public static final RegistryObject SMOOTH_BLUE_ROCK_SLAB;
   public static final RegistryObject SMOOTH_RED_ROCK;
   public static final RegistryObject SMOOTH_RED_ROCK_SLAB;
   public static final RegistryObject SMOOTH_MORDOR_ROCK;
   public static final RegistryObject SMOOTH_MORDOR_ROCK_SLAB;
   public static final RegistryObject SMOOTH_GONDOR_ROCK;
   public static final RegistryObject SMOOTH_GONDOR_ROCK_SLAB;
   public static final RegistryObject SMOOTH_ROHAN_ROCK;
   public static final RegistryObject SMOOTH_ROHAN_ROCK_SLAB;
   public static final RegistryObject BLUE_MOUNTAINS_CRAFTING_TABLE;
   public static final RegistryObject BLUE_DWARVEN_BARS;
   public static final RegistryObject GOLD_TREASURE_PILE;
   public static final RegistryObject SILVER_TREASURE_PILE;
   public static final RegistryObject COPPER_TREASURE_PILE;
   public static final RegistryObject HOLLY_LOG;
   public static final RegistryObject HOLLY_WOOD;
   public static final RegistryObject HOLLY_PLANKS;
   public static final RegistryObject HOLLY_LEAVES;
   public static final RegistryObject HOLLY_SAPLING;
   public static final RegistryObject POTTED_HOLLY_SAPLING;
   public static final RegistryObject HOLLY_SLAB;
   public static final RegistryObject HOLLY_STAIRS;
   public static final RegistryObject HOLLY_FENCE;
   public static final RegistryObject HOLLY_FENCE_GATE;
   public static final RegistryObject HOLLY_DOOR;
   public static final RegistryObject HOLLY_TRAPDOOR;
   public static final RegistryObject HOLLY_PRESSURE_PLATE;
   public static final RegistryObject HOLLY_BUTTON;
   public static final RegistryObject STRIPPED_HOLLY_LOG;
   public static final RegistryObject STRIPPED_HOLLY_WOOD;
   public static final RegistryObject HOLLY_BEAM;
   public static final RegistryObject HOLLY_SIGN;
   public static final RegistryObject HOLLY_WALL_SIGN;
   public static final RegistryObject MIRK_SHROOM;
   public static final RegistryObject POTTED_MIRK_SHROOM;
   public static final RegistryObject GREEN_OAK_LOG;
   public static final RegistryObject GREEN_OAK_WOOD;
   public static final RegistryObject GREEN_OAK_PLANKS;
   public static final RegistryObject GREEN_OAK_LEAVES;
   public static final RegistryObject GREEN_OAK_SAPLING;
   public static final RegistryObject POTTED_GREEN_OAK_SAPLING;
   public static final RegistryObject GREEN_OAK_SLAB;
   public static final RegistryObject GREEN_OAK_STAIRS;
   public static final RegistryObject GREEN_OAK_FENCE;
   public static final RegistryObject GREEN_OAK_FENCE_GATE;
   public static final RegistryObject GREEN_OAK_DOOR;
   public static final RegistryObject GREEN_OAK_TRAPDOOR;
   public static final RegistryObject GREEN_OAK_PRESSURE_PLATE;
   public static final RegistryObject GREEN_OAK_BUTTON;
   public static final RegistryObject STRIPPED_GREEN_OAK_LOG;
   public static final RegistryObject STRIPPED_GREEN_OAK_WOOD;
   public static final RegistryObject GREEN_OAK_BEAM;
   public static final RegistryObject GREEN_OAK_SIGN;
   public static final RegistryObject GREEN_OAK_WALL_SIGN;
   public static final RegistryObject RED_OAK_LEAVES;
   public static final RegistryObject RED_OAK_SAPLING;
   public static final RegistryObject POTTED_RED_OAK_SAPLING;
   public static final RegistryObject MOSSY_GALADHRIM_BRICK;
   public static final RegistryObject MOSSY_GALADHRIM_BRICK_SLAB;
   public static final RegistryObject MOSSY_GALADHRIM_BRICK_STAIRS;
   public static final RegistryObject MOSSY_GALADHRIM_BRICK_WALL;
   public static final RegistryObject CRACKED_GALADHRIM_BRICK;
   public static final RegistryObject CRACKED_GALADHRIM_BRICK_SLAB;
   public static final RegistryObject CRACKED_GALADHRIM_BRICK_STAIRS;
   public static final RegistryObject CRACKED_GALADHRIM_BRICK_WALL;
   public static final RegistryObject CARVED_GALADHRIM_BRICK;
   public static final RegistryObject HANGING_WEB;
   public static final RegistryObject ARID_GRASS;
   public static final RegistryObject WHITE_CHRYSANTHEMUM;
   public static final RegistryObject YELLOW_CHRYSANTHEMUM;
   public static final RegistryObject PINK_CHRYSANTHEMUM;
   public static final RegistryObject RED_CHRYSANTHEMUM;
   public static final RegistryObject ORANGE_CHRYSANTHEMUM;
   public static final RegistryObject POTTED_WHITE_CHRYSANTHEMUM;
   public static final RegistryObject POTTED_YELLOW_CHRYSANTHEMUM;
   public static final RegistryObject POTTED_PINK_CHRYSANTHEMUM;
   public static final RegistryObject POTTED_RED_CHRYSANTHEMUM;
   public static final RegistryObject POTTED_ORANGE_CHRYSANTHEMUM;
   public static final RegistryObject BRONZE_LANTERN;
   public static final RegistryObject MOSSY_DWARVEN_BRICK;
   public static final RegistryObject MOSSY_DWARVEN_BRICK_SLAB;
   public static final RegistryObject MOSSY_DWARVEN_BRICK_STAIRS;
   public static final RegistryObject MOSSY_DWARVEN_BRICK_WALL;
   public static final RegistryObject CRACKED_DWARVEN_BRICK;
   public static final RegistryObject CRACKED_DWARVEN_BRICK_SLAB;
   public static final RegistryObject CRACKED_DWARVEN_BRICK_STAIRS;
   public static final RegistryObject CRACKED_DWARVEN_BRICK_WALL;
   public static final RegistryObject SNOWY_DWARVEN_BRICK;
   public static final RegistryObject SNOWY_DWARVEN_BRICK_SLAB;
   public static final RegistryObject SNOWY_DWARVEN_BRICK_STAIRS;
   public static final RegistryObject SNOWY_DWARVEN_BRICK_WALL;
   public static final RegistryObject CARVED_DWARVEN_BRICK;
   public static final RegistryObject MOSSY_WOOD_ELVEN_BRICK;
   public static final RegistryObject MOSSY_WOOD_ELVEN_BRICK_SLAB;
   public static final RegistryObject MOSSY_WOOD_ELVEN_BRICK_STAIRS;
   public static final RegistryObject MOSSY_WOOD_ELVEN_BRICK_WALL;
   public static final RegistryObject CRACKED_WOOD_ELVEN_BRICK;
   public static final RegistryObject CRACKED_WOOD_ELVEN_BRICK_SLAB;
   public static final RegistryObject CRACKED_WOOD_ELVEN_BRICK_STAIRS;
   public static final RegistryObject CRACKED_WOOD_ELVEN_BRICK_WALL;
   public static final RegistryObject CARVED_WOOD_ELVEN_BRICK;
   public static final RegistryObject ARNOR_BRICK;
   public static final RegistryObject ARNOR_BRICK_SLAB;
   public static final RegistryObject ARNOR_BRICK_STAIRS;
   public static final RegistryObject ARNOR_BRICK_WALL;
   public static final RegistryObject MOSSY_ARNOR_BRICK;
   public static final RegistryObject MOSSY_ARNOR_BRICK_SLAB;
   public static final RegistryObject MOSSY_ARNOR_BRICK_STAIRS;
   public static final RegistryObject MOSSY_ARNOR_BRICK_WALL;
   public static final RegistryObject CRACKED_ARNOR_BRICK;
   public static final RegistryObject CRACKED_ARNOR_BRICK_SLAB;
   public static final RegistryObject CRACKED_ARNOR_BRICK_STAIRS;
   public static final RegistryObject CRACKED_ARNOR_BRICK_WALL;
   public static final RegistryObject CARVED_ARNOR_BRICK;
   public static final RegistryObject ARNOR_PILLAR;
   public static final RegistryObject ARNOR_PILLAR_SLAB;
   public static final RegistryObject RANGER_CRAFTING_TABLE;
   public static final RegistryObject GONDOR_COBBLEBRICK;
   public static final RegistryObject GONDOR_COBBLEBRICK_SLAB;
   public static final RegistryObject GONDOR_COBBLEBRICK_STAIRS;
   public static final RegistryObject GONDOR_COBBLEBRICK_WALL;
   public static final RegistryObject MOSSY_GONDOR_COBBLEBRICK;
   public static final RegistryObject MOSSY_GONDOR_COBBLEBRICK_SLAB;
   public static final RegistryObject MOSSY_GONDOR_COBBLEBRICK_STAIRS;
   public static final RegistryObject MOSSY_GONDOR_COBBLEBRICK_WALL;
   public static final RegistryObject CRACKED_GONDOR_COBBLEBRICK;
   public static final RegistryObject CRACKED_GONDOR_COBBLEBRICK_SLAB;
   public static final RegistryObject CRACKED_GONDOR_COBBLEBRICK_STAIRS;
   public static final RegistryObject CRACKED_GONDOR_COBBLEBRICK_WALL;
   public static final RegistryObject DOL_AMROTH_BRICK;
   public static final RegistryObject DOL_AMROTH_BRICK_SLAB;
   public static final RegistryObject DOL_AMROTH_BRICK_STAIRS;
   public static final RegistryObject DOL_AMROTH_BRICK_WALL;
   public static final RegistryObject MOSSY_DOL_AMROTH_BRICK;
   public static final RegistryObject MOSSY_DOL_AMROTH_BRICK_SLAB;
   public static final RegistryObject MOSSY_DOL_AMROTH_BRICK_STAIRS;
   public static final RegistryObject MOSSY_DOL_AMROTH_BRICK_WALL;
   public static final RegistryObject CRACKED_DOL_AMROTH_BRICK;
   public static final RegistryObject CRACKED_DOL_AMROTH_BRICK_SLAB;
   public static final RegistryObject CRACKED_DOL_AMROTH_BRICK_STAIRS;
   public static final RegistryObject CRACKED_DOL_AMROTH_BRICK_WALL;
   public static final RegistryObject DOL_AMROTH_CRAFTING_TABLE;
   public static final RegistryObject CLAY_TILING;
   public static final RegistryObject WHITE_CLAY_TILING;
   public static final RegistryObject ORANGE_CLAY_TILING;
   public static final RegistryObject MAGENTA_CLAY_TILING;
   public static final RegistryObject LIGHT_BLUE_CLAY_TILING;
   public static final RegistryObject YELLOW_CLAY_TILING;
   public static final RegistryObject LIME_CLAY_TILING;
   public static final RegistryObject PINK_CLAY_TILING;
   public static final RegistryObject GRAY_CLAY_TILING;
   public static final RegistryObject LIGHT_GRAY_CLAY_TILING;
   public static final RegistryObject CYAN_CLAY_TILING;
   public static final RegistryObject PURPLE_CLAY_TILING;
   public static final RegistryObject BLUE_CLAY_TILING;
   public static final RegistryObject BROWN_CLAY_TILING;
   public static final RegistryObject GREEN_CLAY_TILING;
   public static final RegistryObject RED_CLAY_TILING;
   public static final RegistryObject BLACK_CLAY_TILING;
   public static final RegistryObject CLAY_TILING_SLAB;
   public static final RegistryObject WHITE_CLAY_TILING_SLAB;
   public static final RegistryObject ORANGE_CLAY_TILING_SLAB;
   public static final RegistryObject MAGENTA_CLAY_TILING_SLAB;
   public static final RegistryObject LIGHT_BLUE_CLAY_TILING_SLAB;
   public static final RegistryObject YELLOW_CLAY_TILING_SLAB;
   public static final RegistryObject LIME_CLAY_TILING_SLAB;
   public static final RegistryObject PINK_CLAY_TILING_SLAB;
   public static final RegistryObject GRAY_CLAY_TILING_SLAB;
   public static final RegistryObject LIGHT_GRAY_CLAY_TILING_SLAB;
   public static final RegistryObject CYAN_CLAY_TILING_SLAB;
   public static final RegistryObject PURPLE_CLAY_TILING_SLAB;
   public static final RegistryObject BLUE_CLAY_TILING_SLAB;
   public static final RegistryObject BROWN_CLAY_TILING_SLAB;
   public static final RegistryObject GREEN_CLAY_TILING_SLAB;
   public static final RegistryObject RED_CLAY_TILING_SLAB;
   public static final RegistryObject BLACK_CLAY_TILING_SLAB;
   public static final RegistryObject CLAY_TILING_STAIRS;
   public static final RegistryObject WHITE_CLAY_TILING_STAIRS;
   public static final RegistryObject ORANGE_CLAY_TILING_STAIRS;
   public static final RegistryObject MAGENTA_CLAY_TILING_STAIRS;
   public static final RegistryObject LIGHT_BLUE_CLAY_TILING_STAIRS;
   public static final RegistryObject YELLOW_CLAY_TILING_STAIRS;
   public static final RegistryObject LIME_CLAY_TILING_STAIRS;
   public static final RegistryObject PINK_CLAY_TILING_STAIRS;
   public static final RegistryObject GRAY_CLAY_TILING_STAIRS;
   public static final RegistryObject LIGHT_GRAY_CLAY_TILING_STAIRS;
   public static final RegistryObject CYAN_CLAY_TILING_STAIRS;
   public static final RegistryObject PURPLE_CLAY_TILING_STAIRS;
   public static final RegistryObject BLUE_CLAY_TILING_STAIRS;
   public static final RegistryObject BROWN_CLAY_TILING_STAIRS;
   public static final RegistryObject GREEN_CLAY_TILING_STAIRS;
   public static final RegistryObject RED_CLAY_TILING_STAIRS;
   public static final RegistryObject BLACK_CLAY_TILING_STAIRS;
   public static final RegistryObject CLAY_TILING_WALL;
   public static final RegistryObject WHITE_CLAY_TILING_WALL;
   public static final RegistryObject ORANGE_CLAY_TILING_WALL;
   public static final RegistryObject MAGENTA_CLAY_TILING_WALL;
   public static final RegistryObject LIGHT_BLUE_CLAY_TILING_WALL;
   public static final RegistryObject YELLOW_CLAY_TILING_WALL;
   public static final RegistryObject LIME_CLAY_TILING_WALL;
   public static final RegistryObject PINK_CLAY_TILING_WALL;
   public static final RegistryObject GRAY_CLAY_TILING_WALL;
   public static final RegistryObject LIGHT_GRAY_CLAY_TILING_WALL;
   public static final RegistryObject CYAN_CLAY_TILING_WALL;
   public static final RegistryObject PURPLE_CLAY_TILING_WALL;
   public static final RegistryObject BLUE_CLAY_TILING_WALL;
   public static final RegistryObject BROWN_CLAY_TILING_WALL;
   public static final RegistryObject GREEN_CLAY_TILING_WALL;
   public static final RegistryObject RED_CLAY_TILING_WALL;
   public static final RegistryObject BLACK_CLAY_TILING_WALL;
   public static final RegistryObject FINE_GLASS;
   public static final RegistryObject WHITE_FINE_GLASS;
   public static final RegistryObject ORANGE_FINE_GLASS;
   public static final RegistryObject MAGENTA_FINE_GLASS;
   public static final RegistryObject LIGHT_BLUE_FINE_GLASS;
   public static final RegistryObject YELLOW_FINE_GLASS;
   public static final RegistryObject LIME_FINE_GLASS;
   public static final RegistryObject PINK_FINE_GLASS;
   public static final RegistryObject GRAY_FINE_GLASS;
   public static final RegistryObject LIGHT_GRAY_FINE_GLASS;
   public static final RegistryObject CYAN_FINE_GLASS;
   public static final RegistryObject PURPLE_FINE_GLASS;
   public static final RegistryObject BLUE_FINE_GLASS;
   public static final RegistryObject BROWN_FINE_GLASS;
   public static final RegistryObject GREEN_FINE_GLASS;
   public static final RegistryObject RED_FINE_GLASS;
   public static final RegistryObject BLACK_FINE_GLASS;
   public static final RegistryObject FINE_GLASS_PANE;
   public static final RegistryObject WHITE_FINE_GLASS_PANE;
   public static final RegistryObject ORANGE_FINE_GLASS_PANE;
   public static final RegistryObject MAGENTA_FINE_GLASS_PANE;
   public static final RegistryObject LIGHT_BLUE_FINE_GLASS_PANE;
   public static final RegistryObject YELLOW_FINE_GLASS_PANE;
   public static final RegistryObject LIME_FINE_GLASS_PANE;
   public static final RegistryObject PINK_FINE_GLASS_PANE;
   public static final RegistryObject GRAY_FINE_GLASS_PANE;
   public static final RegistryObject LIGHT_GRAY_FINE_GLASS_PANE;
   public static final RegistryObject CYAN_FINE_GLASS_PANE;
   public static final RegistryObject PURPLE_FINE_GLASS_PANE;
   public static final RegistryObject BLUE_FINE_GLASS_PANE;
   public static final RegistryObject BROWN_FINE_GLASS_PANE;
   public static final RegistryObject GREEN_FINE_GLASS_PANE;
   public static final RegistryObject RED_FINE_GLASS_PANE;
   public static final RegistryObject BLACK_FINE_GLASS_PANE;
   public static final RegistryObject ORC_PLATING;
   public static final RegistryObject RUSTED_ORC_PLATING;
   public static final RegistryObject CARVED_NUMENOREAN_BRICK;
   public static final RegistryObject MOSSY_UMBAR_BRICK;
   public static final RegistryObject MOSSY_UMBAR_BRICK_SLAB;
   public static final RegistryObject MOSSY_UMBAR_BRICK_STAIRS;
   public static final RegistryObject MOSSY_UMBAR_BRICK_WALL;
   public static final RegistryObject CRACKED_UMBAR_BRICK;
   public static final RegistryObject CRACKED_UMBAR_BRICK_SLAB;
   public static final RegistryObject CRACKED_UMBAR_BRICK_STAIRS;
   public static final RegistryObject CRACKED_UMBAR_BRICK_WALL;
   public static final RegistryObject CARVED_BRICK;
   public static final RegistryObject ORC_PLATING_SLAB;
   public static final RegistryObject ORC_PLATING_STAIRS;
   public static final RegistryObject RUSTED_ORC_PLATING_SLAB;
   public static final RegistryObject RUSTED_ORC_PLATING_STAIRS;
   public static final RegistryObject WHITE_WOOL_SLAB;
   public static final RegistryObject ORANGE_WOOL_SLAB;
   public static final RegistryObject MAGENTA_WOOL_SLAB;
   public static final RegistryObject LIGHT_BLUE_WOOL_SLAB;
   public static final RegistryObject YELLOW_WOOL_SLAB;
   public static final RegistryObject LIME_WOOL_SLAB;
   public static final RegistryObject PINK_WOOL_SLAB;
   public static final RegistryObject GRAY_WOOL_SLAB;
   public static final RegistryObject LIGHT_GRAY_WOOL_SLAB;
   public static final RegistryObject CYAN_WOOL_SLAB;
   public static final RegistryObject PURPLE_WOOL_SLAB;
   public static final RegistryObject BLUE_WOOL_SLAB;
   public static final RegistryObject BROWN_WOOL_SLAB;
   public static final RegistryObject GREEN_WOOL_SLAB;
   public static final RegistryObject RED_WOOL_SLAB;
   public static final RegistryObject BLACK_WOOL_SLAB;
   public static final RegistryObject WHITE_WOOL_STAIRS;
   public static final RegistryObject ORANGE_WOOL_STAIRS;
   public static final RegistryObject MAGENTA_WOOL_STAIRS;
   public static final RegistryObject LIGHT_BLUE_WOOL_STAIRS;
   public static final RegistryObject YELLOW_WOOL_STAIRS;
   public static final RegistryObject LIME_WOOL_STAIRS;
   public static final RegistryObject PINK_WOOL_STAIRS;
   public static final RegistryObject GRAY_WOOL_STAIRS;
   public static final RegistryObject LIGHT_GRAY_WOOL_STAIRS;
   public static final RegistryObject CYAN_WOOL_STAIRS;
   public static final RegistryObject PURPLE_WOOL_STAIRS;
   public static final RegistryObject BLUE_WOOL_STAIRS;
   public static final RegistryObject BROWN_WOOL_STAIRS;
   public static final RegistryObject GREEN_WOOL_STAIRS;
   public static final RegistryObject RED_WOOL_STAIRS;
   public static final RegistryObject BLACK_WOOL_STAIRS;
   public static final RegistryObject ANGMAR_BRICK;
   public static final RegistryObject ANGMAR_BRICK_SLAB;
   public static final RegistryObject ANGMAR_BRICK_STAIRS;
   public static final RegistryObject ANGMAR_BRICK_WALL;
   public static final RegistryObject ANGMAR_CRAFTING_TABLE;
   public static final RegistryObject MOSSY_ANGMAR_BRICK;
   public static final RegistryObject MOSSY_ANGMAR_BRICK_SLAB;
   public static final RegistryObject MOSSY_ANGMAR_BRICK_STAIRS;
   public static final RegistryObject MOSSY_ANGMAR_BRICK_WALL;
   public static final RegistryObject CRACKED_ANGMAR_BRICK;
   public static final RegistryObject CRACKED_ANGMAR_BRICK_SLAB;
   public static final RegistryObject CRACKED_ANGMAR_BRICK_STAIRS;
   public static final RegistryObject CRACKED_ANGMAR_BRICK_WALL;
   public static final RegistryObject SNOWY_ANGMAR_BRICK;
   public static final RegistryObject SNOWY_ANGMAR_BRICK_SLAB;
   public static final RegistryObject SNOWY_ANGMAR_BRICK_STAIRS;
   public static final RegistryObject SNOWY_ANGMAR_BRICK_WALL;
   public static final RegistryObject PURPLE_MOOR_GRASS;
   public static final RegistryObject RED_MOOR_GRASS;
   public static final RegistryObject TALL_WHEATGRASS;
   public static final RegistryObject TALL_ARID_GRASS;
   public static final RegistryObject CARVED_ANGMAR_BRICK;
   public static final RegistryObject ANGMAR_PILLAR;
   public static final RegistryObject ANGMAR_PILLAR_SLAB;
   public static final RegistryObject CHALK;
   public static final RegistryObject CHALK_SLAB;
   public static final RegistryObject CHALK_STAIRS;
   public static final RegistryObject CHALK_WALL;
   public static final RegistryObject CHALK_BRICK;
   public static final RegistryObject CHALK_BRICK_SLAB;
   public static final RegistryObject CHALK_BRICK_STAIRS;
   public static final RegistryObject CHALK_BRICK_WALL;
   public static final RegistryObject CHALK_PILLAR;
   public static final RegistryObject CHALK_PILLAR_SLAB;
   public static final RegistryObject CHALK_DRIPSTONE;
   public static final RegistryObject GONDOR_ROCK_PRESSURE_PLATE;
   public static final RegistryObject MORDOR_ROCK_PRESSURE_PLATE;
   public static final RegistryObject ROHAN_ROCK_PRESSURE_PLATE;
   public static final RegistryObject BLUE_ROCK_PRESSURE_PLATE;
   public static final RegistryObject RED_ROCK_PRESSURE_PLATE;
   public static final RegistryObject CHALK_PRESSURE_PLATE;
   public static final RegistryObject GONDOR_ROCK_BUTTON;
   public static final RegistryObject MORDOR_ROCK_BUTTON;
   public static final RegistryObject ROHAN_ROCK_BUTTON;
   public static final RegistryObject BLUE_ROCK_BUTTON;
   public static final RegistryObject RED_ROCK_BUTTON;
   public static final RegistryObject CHALK_BUTTON;
   public static final RegistryObject POLISHED_CHALK;
   public static final RegistryObject POLISHED_CHALK_SLAB;
   public static final RegistryObject POLISHED_CHALK_STAIRS;
   public static final RegistryObject POLISHED_CHALK_WALL;
   public static final RegistryObject DIRTY_CHALK;
   public static final RegistryObject DORWINION_BRICK;
   public static final RegistryObject DORWINION_BRICK_SLAB;
   public static final RegistryObject DORWINION_BRICK_STAIRS;
   public static final RegistryObject DORWINION_BRICK_WALL;
   public static final RegistryObject DORWINION_CRAFTING_TABLE;
   public static final RegistryObject MOSSY_DORWINION_BRICK;
   public static final RegistryObject MOSSY_DORWINION_BRICK_SLAB;
   public static final RegistryObject MOSSY_DORWINION_BRICK_STAIRS;
   public static final RegistryObject MOSSY_DORWINION_BRICK_WALL;
   public static final RegistryObject CRACKED_DORWINION_BRICK;
   public static final RegistryObject CRACKED_DORWINION_BRICK_SLAB;
   public static final RegistryObject CRACKED_DORWINION_BRICK_STAIRS;
   public static final RegistryObject CRACKED_DORWINION_BRICK_WALL;
   public static final RegistryObject RED_DORWINION_BRICK;
   public static final RegistryObject RED_DORWINION_BRICK_SLAB;
   public static final RegistryObject RED_DORWINION_BRICK_STAIRS;
   public static final RegistryObject RED_DORWINION_BRICK_WALL;
   public static final RegistryObject MOSSY_RED_DORWINION_BRICK;
   public static final RegistryObject MOSSY_RED_DORWINION_BRICK_SLAB;
   public static final RegistryObject MOSSY_RED_DORWINION_BRICK_STAIRS;
   public static final RegistryObject MOSSY_RED_DORWINION_BRICK_WALL;
   public static final RegistryObject CRACKED_RED_DORWINION_BRICK;
   public static final RegistryObject CRACKED_RED_DORWINION_BRICK_SLAB;
   public static final RegistryObject CRACKED_RED_DORWINION_BRICK_STAIRS;
   public static final RegistryObject CRACKED_RED_DORWINION_BRICK_WALL;
   public static final RegistryObject DORWINION_PILLAR;
   public static final RegistryObject DORWINION_PILLAR_SLAB;
   public static final RegistryObject WHITE_SAND;
   public static final RegistryObject WHITE_SANDSTONE;
   public static final RegistryObject WHITE_SANDSTONE_SLAB;
   public static final RegistryObject WHITE_SANDSTONE_STAIRS;
   public static final RegistryObject WHITE_SANDSTONE_WALL;
   public static final RegistryObject WHITE_SANDSTONE_DRIPSTONE;
   public static final RegistryObject CUT_WHITE_SANDSTONE;
   public static final RegistryObject CUT_WHITE_SANDSTONE_SLAB;
   public static final RegistryObject CHISELED_WHITE_SANDSTONE;
   public static final RegistryObject SMOOTH_WHITE_SANDSTONE;
   public static final RegistryObject SMOOTH_WHITE_SANDSTONE_SLAB;
   public static final RegistryObject SMOOTH_WHITE_SANDSTONE_STAIRS;
   public static final RegistryObject CYPRESS_LOG;
   public static final RegistryObject CYPRESS_WOOD;
   public static final RegistryObject CYPRESS_PLANKS;
   public static final RegistryObject CYPRESS_LEAVES;
   public static final RegistryObject CYPRESS_SAPLING;
   public static final RegistryObject POTTED_CYPRESS_SAPLING;
   public static final RegistryObject CYPRESS_SLAB;
   public static final RegistryObject CYPRESS_STAIRS;
   public static final RegistryObject CYPRESS_FENCE;
   public static final RegistryObject CYPRESS_FENCE_GATE;
   public static final RegistryObject CYPRESS_DOOR;
   public static final RegistryObject CYPRESS_TRAPDOOR;
   public static final RegistryObject CYPRESS_PRESSURE_PLATE;
   public static final RegistryObject CYPRESS_BUTTON;
   public static final RegistryObject STRIPPED_CYPRESS_LOG;
   public static final RegistryObject STRIPPED_CYPRESS_WOOD;
   public static final RegistryObject CYPRESS_BEAM;
   public static final RegistryObject CYPRESS_SIGN;
   public static final RegistryObject CYPRESS_WALL_SIGN;
   public static final RegistryObject DALE_BRICK;
   public static final RegistryObject DALE_BRICK_SLAB;
   public static final RegistryObject DALE_BRICK_STAIRS;
   public static final RegistryObject DALE_BRICK_WALL;
   public static final RegistryObject MOSSY_DALE_BRICK;
   public static final RegistryObject MOSSY_DALE_BRICK_SLAB;
   public static final RegistryObject MOSSY_DALE_BRICK_STAIRS;
   public static final RegistryObject MOSSY_DALE_BRICK_WALL;
   public static final RegistryObject CRACKED_DALE_BRICK;
   public static final RegistryObject CRACKED_DALE_BRICK_SLAB;
   public static final RegistryObject CRACKED_DALE_BRICK_STAIRS;
   public static final RegistryObject CRACKED_DALE_BRICK_WALL;
   public static final RegistryObject DALE_CRAFTING_TABLE;
   public static final RegistryObject DALE_PILLAR;
   public static final RegistryObject DALE_PILLAR_SLAB;
   public static final RegistryObject CARVED_DALE_BRICK;
   public static final RegistryObject BLACKROOT;
   public static final RegistryObject POTTED_BLACKROOT;
   public static final RegistryObject DALE_PAVING;
   public static final RegistryObject DALE_PAVING_SLAB;
   public static final RegistryObject DALE_PAVING_STAIRS;
   public static final RegistryObject MOSSY_DALE_PAVING;
   public static final RegistryObject MOSSY_DALE_PAVING_SLAB;
   public static final RegistryObject MOSSY_DALE_PAVING_STAIRS;
   public static final RegistryObject YELLOW_IRIS;
   public static final RegistryObject QUAGMIRE;
   public static final RegistryObject MALLOS;
   public static final RegistryObject POTTED_MALLOS;
   public static final RegistryObject ROTTEN_LOG;
   public static final RegistryObject ROTTEN_WOOD;
   public static final RegistryObject ROTTEN_PLANKS;
   public static final RegistryObject ROTTEN_SLAB;
   public static final RegistryObject ROTTEN_STAIRS;
   public static final RegistryObject ROTTEN_FENCE;
   public static final RegistryObject ROTTEN_FENCE_GATE;
   public static final RegistryObject ROTTEN_DOOR;
   public static final RegistryObject ROTTEN_TRAPDOOR;
   public static final RegistryObject ROTTEN_PRESSURE_PLATE;
   public static final RegistryObject ROTTEN_BUTTON;
   public static final RegistryObject STRIPPED_ROTTEN_LOG;
   public static final RegistryObject STRIPPED_ROTTEN_WOOD;
   public static final RegistryObject ROTTEN_BEAM;
   public static final RegistryObject ROTTEN_SIGN;
   public static final RegistryObject ROTTEN_WALL_SIGN;
   public static final RegistryObject REEDS;
   public static final RegistryObject PAPYRUS;
   public static final RegistryObject RUSHES;
   public static final RegistryObject DRIED_REEDS;
   public static final RegistryObject REED_THATCH;
   public static final RegistryObject REED_THATCH_SLAB;
   public static final RegistryObject REED_THATCH_STAIRS;
   public static final RegistryObject WHITE_WATER_LILY;
   public static final RegistryObject YELLOW_WATER_LILY;
   public static final RegistryObject PURPLE_WATER_LILY;
   public static final RegistryObject PINK_WATER_LILY;
   public static final RegistryObject OAK_LOG_SLAB;
   public static final RegistryObject OAK_WOOD_SLAB;
   public static final RegistryObject OAK_WOOD_STAIRS;
   public static final RegistryObject OAK_BRANCH;
   public static final RegistryObject STRIPPED_OAK_LOG_SLAB;
   public static final RegistryObject STRIPPED_OAK_WOOD_SLAB;
   public static final RegistryObject STRIPPED_OAK_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_OAK_BRANCH;
   public static final RegistryObject OAK_BEAM_SLAB;
   public static final RegistryObject SPRUCE_LOG_SLAB;
   public static final RegistryObject SPRUCE_WOOD_SLAB;
   public static final RegistryObject SPRUCE_WOOD_STAIRS;
   public static final RegistryObject SPRUCE_BRANCH;
   public static final RegistryObject STRIPPED_SPRUCE_LOG_SLAB;
   public static final RegistryObject STRIPPED_SPRUCE_WOOD_SLAB;
   public static final RegistryObject STRIPPED_SPRUCE_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_SPRUCE_BRANCH;
   public static final RegistryObject SPRUCE_BEAM_SLAB;
   public static final RegistryObject BIRCH_LOG_SLAB;
   public static final RegistryObject BIRCH_WOOD_SLAB;
   public static final RegistryObject BIRCH_WOOD_STAIRS;
   public static final RegistryObject BIRCH_BRANCH;
   public static final RegistryObject STRIPPED_BIRCH_LOG_SLAB;
   public static final RegistryObject STRIPPED_BIRCH_WOOD_SLAB;
   public static final RegistryObject STRIPPED_BIRCH_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_BIRCH_BRANCH;
   public static final RegistryObject BIRCH_BEAM_SLAB;
   public static final RegistryObject JUNGLE_LOG_SLAB;
   public static final RegistryObject JUNGLE_WOOD_SLAB;
   public static final RegistryObject JUNGLE_WOOD_STAIRS;
   public static final RegistryObject JUNGLE_BRANCH;
   public static final RegistryObject STRIPPED_JUNGLE_LOG_SLAB;
   public static final RegistryObject STRIPPED_JUNGLE_WOOD_SLAB;
   public static final RegistryObject STRIPPED_JUNGLE_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_JUNGLE_BRANCH;
   public static final RegistryObject JUNGLE_BEAM_SLAB;
   public static final RegistryObject ACACIA_LOG_SLAB;
   public static final RegistryObject ACACIA_WOOD_SLAB;
   public static final RegistryObject ACACIA_WOOD_STAIRS;
   public static final RegistryObject ACACIA_BRANCH;
   public static final RegistryObject STRIPPED_ACACIA_LOG_SLAB;
   public static final RegistryObject STRIPPED_ACACIA_WOOD_SLAB;
   public static final RegistryObject STRIPPED_ACACIA_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_ACACIA_BRANCH;
   public static final RegistryObject ACACIA_BEAM_SLAB;
   public static final RegistryObject DARK_OAK_LOG_SLAB;
   public static final RegistryObject DARK_OAK_WOOD_SLAB;
   public static final RegistryObject DARK_OAK_WOOD_STAIRS;
   public static final RegistryObject DARK_OAK_BRANCH;
   public static final RegistryObject STRIPPED_DARK_OAK_LOG_SLAB;
   public static final RegistryObject STRIPPED_DARK_OAK_WOOD_SLAB;
   public static final RegistryObject STRIPPED_DARK_OAK_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_DARK_OAK_BRANCH;
   public static final RegistryObject DARK_OAK_BEAM_SLAB;
   public static final RegistryObject PINE_LOG_SLAB;
   public static final RegistryObject PINE_WOOD_SLAB;
   public static final RegistryObject PINE_WOOD_STAIRS;
   public static final RegistryObject PINE_BRANCH;
   public static final RegistryObject STRIPPED_PINE_LOG_SLAB;
   public static final RegistryObject STRIPPED_PINE_WOOD_SLAB;
   public static final RegistryObject STRIPPED_PINE_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_PINE_BRANCH;
   public static final RegistryObject PINE_BEAM_SLAB;
   public static final RegistryObject MALLORN_LOG_SLAB;
   public static final RegistryObject MALLORN_WOOD_SLAB;
   public static final RegistryObject MALLORN_WOOD_STAIRS;
   public static final RegistryObject MALLORN_BRANCH;
   public static final RegistryObject STRIPPED_MALLORN_LOG_SLAB;
   public static final RegistryObject STRIPPED_MALLORN_WOOD_SLAB;
   public static final RegistryObject STRIPPED_MALLORN_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_MALLORN_BRANCH;
   public static final RegistryObject MALLORN_BEAM_SLAB;
   public static final RegistryObject MIRK_OAK_LOG_SLAB;
   public static final RegistryObject MIRK_OAK_WOOD_SLAB;
   public static final RegistryObject MIRK_OAK_WOOD_STAIRS;
   public static final RegistryObject MIRK_OAK_BRANCH;
   public static final RegistryObject STRIPPED_MIRK_OAK_LOG_SLAB;
   public static final RegistryObject STRIPPED_MIRK_OAK_WOOD_SLAB;
   public static final RegistryObject STRIPPED_MIRK_OAK_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_MIRK_OAK_BRANCH;
   public static final RegistryObject MIRK_OAK_BEAM_SLAB;
   public static final RegistryObject CHARRED_LOG_SLAB;
   public static final RegistryObject CHARRED_WOOD_SLAB;
   public static final RegistryObject CHARRED_WOOD_STAIRS;
   public static final RegistryObject CHARRED_BRANCH;
   public static final RegistryObject STRIPPED_CHARRED_LOG_SLAB;
   public static final RegistryObject STRIPPED_CHARRED_WOOD_SLAB;
   public static final RegistryObject STRIPPED_CHARRED_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_CHARRED_BRANCH;
   public static final RegistryObject CHARRED_BEAM_SLAB;
   public static final RegistryObject APPLE_LOG_SLAB;
   public static final RegistryObject APPLE_WOOD_SLAB;
   public static final RegistryObject APPLE_WOOD_STAIRS;
   public static final RegistryObject APPLE_BRANCH;
   public static final RegistryObject STRIPPED_APPLE_LOG_SLAB;
   public static final RegistryObject STRIPPED_APPLE_WOOD_SLAB;
   public static final RegistryObject STRIPPED_APPLE_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_APPLE_BRANCH;
   public static final RegistryObject APPLE_BEAM_SLAB;
   public static final RegistryObject PEAR_LOG_SLAB;
   public static final RegistryObject PEAR_WOOD_SLAB;
   public static final RegistryObject PEAR_WOOD_STAIRS;
   public static final RegistryObject PEAR_BRANCH;
   public static final RegistryObject STRIPPED_PEAR_LOG_SLAB;
   public static final RegistryObject STRIPPED_PEAR_WOOD_SLAB;
   public static final RegistryObject STRIPPED_PEAR_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_PEAR_BRANCH;
   public static final RegistryObject PEAR_BEAM_SLAB;
   public static final RegistryObject CHERRY_LOG_SLAB;
   public static final RegistryObject CHERRY_WOOD_SLAB;
   public static final RegistryObject CHERRY_WOOD_STAIRS;
   public static final RegistryObject CHERRY_BRANCH;
   public static final RegistryObject STRIPPED_CHERRY_LOG_SLAB;
   public static final RegistryObject STRIPPED_CHERRY_WOOD_SLAB;
   public static final RegistryObject STRIPPED_CHERRY_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_CHERRY_BRANCH;
   public static final RegistryObject CHERRY_BEAM_SLAB;
   public static final RegistryObject LEBETHRON_LOG_SLAB;
   public static final RegistryObject LEBETHRON_WOOD_SLAB;
   public static final RegistryObject LEBETHRON_WOOD_STAIRS;
   public static final RegistryObject LEBETHRON_BRANCH;
   public static final RegistryObject STRIPPED_LEBETHRON_LOG_SLAB;
   public static final RegistryObject STRIPPED_LEBETHRON_WOOD_SLAB;
   public static final RegistryObject STRIPPED_LEBETHRON_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_LEBETHRON_BRANCH;
   public static final RegistryObject LEBETHRON_BEAM_SLAB;
   public static final RegistryObject BEECH_LOG_SLAB;
   public static final RegistryObject BEECH_WOOD_SLAB;
   public static final RegistryObject BEECH_WOOD_STAIRS;
   public static final RegistryObject BEECH_BRANCH;
   public static final RegistryObject STRIPPED_BEECH_LOG_SLAB;
   public static final RegistryObject STRIPPED_BEECH_WOOD_SLAB;
   public static final RegistryObject STRIPPED_BEECH_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_BEECH_BRANCH;
   public static final RegistryObject BEECH_BEAM_SLAB;
   public static final RegistryObject MAPLE_LOG_SLAB;
   public static final RegistryObject MAPLE_WOOD_SLAB;
   public static final RegistryObject MAPLE_WOOD_STAIRS;
   public static final RegistryObject MAPLE_BRANCH;
   public static final RegistryObject STRIPPED_MAPLE_LOG_SLAB;
   public static final RegistryObject STRIPPED_MAPLE_WOOD_SLAB;
   public static final RegistryObject STRIPPED_MAPLE_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_MAPLE_BRANCH;
   public static final RegistryObject MAPLE_BEAM_SLAB;
   public static final RegistryObject ASPEN_LOG_SLAB;
   public static final RegistryObject ASPEN_WOOD_SLAB;
   public static final RegistryObject ASPEN_WOOD_STAIRS;
   public static final RegistryObject ASPEN_BRANCH;
   public static final RegistryObject STRIPPED_ASPEN_LOG_SLAB;
   public static final RegistryObject STRIPPED_ASPEN_WOOD_SLAB;
   public static final RegistryObject STRIPPED_ASPEN_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_ASPEN_BRANCH;
   public static final RegistryObject ASPEN_BEAM_SLAB;
   public static final RegistryObject LAIRELOSSE_LOG_SLAB;
   public static final RegistryObject LAIRELOSSE_WOOD_SLAB;
   public static final RegistryObject LAIRELOSSE_WOOD_STAIRS;
   public static final RegistryObject LAIRELOSSE_BRANCH;
   public static final RegistryObject STRIPPED_LAIRELOSSE_LOG_SLAB;
   public static final RegistryObject STRIPPED_LAIRELOSSE_WOOD_SLAB;
   public static final RegistryObject STRIPPED_LAIRELOSSE_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_LAIRELOSSE_BRANCH;
   public static final RegistryObject LAIRELOSSE_BEAM_SLAB;
   public static final RegistryObject CEDAR_LOG_SLAB;
   public static final RegistryObject CEDAR_WOOD_SLAB;
   public static final RegistryObject CEDAR_WOOD_STAIRS;
   public static final RegistryObject CEDAR_BRANCH;
   public static final RegistryObject STRIPPED_CEDAR_LOG_SLAB;
   public static final RegistryObject STRIPPED_CEDAR_WOOD_SLAB;
   public static final RegistryObject STRIPPED_CEDAR_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_CEDAR_BRANCH;
   public static final RegistryObject CEDAR_BEAM_SLAB;
   public static final RegistryObject FIR_LOG_SLAB;
   public static final RegistryObject FIR_WOOD_SLAB;
   public static final RegistryObject FIR_WOOD_STAIRS;
   public static final RegistryObject FIR_BRANCH;
   public static final RegistryObject STRIPPED_FIR_LOG_SLAB;
   public static final RegistryObject STRIPPED_FIR_WOOD_SLAB;
   public static final RegistryObject STRIPPED_FIR_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_FIR_BRANCH;
   public static final RegistryObject FIR_BEAM_SLAB;
   public static final RegistryObject LARCH_LOG_SLAB;
   public static final RegistryObject LARCH_WOOD_SLAB;
   public static final RegistryObject LARCH_WOOD_STAIRS;
   public static final RegistryObject LARCH_BRANCH;
   public static final RegistryObject STRIPPED_LARCH_LOG_SLAB;
   public static final RegistryObject STRIPPED_LARCH_WOOD_SLAB;
   public static final RegistryObject STRIPPED_LARCH_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_LARCH_BRANCH;
   public static final RegistryObject LARCH_BEAM_SLAB;
   public static final RegistryObject HOLLY_LOG_SLAB;
   public static final RegistryObject HOLLY_WOOD_SLAB;
   public static final RegistryObject HOLLY_WOOD_STAIRS;
   public static final RegistryObject HOLLY_BRANCH;
   public static final RegistryObject STRIPPED_HOLLY_LOG_SLAB;
   public static final RegistryObject STRIPPED_HOLLY_WOOD_SLAB;
   public static final RegistryObject STRIPPED_HOLLY_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_HOLLY_BRANCH;
   public static final RegistryObject HOLLY_BEAM_SLAB;
   public static final RegistryObject GREEN_OAK_LOG_SLAB;
   public static final RegistryObject GREEN_OAK_WOOD_SLAB;
   public static final RegistryObject GREEN_OAK_WOOD_STAIRS;
   public static final RegistryObject GREEN_OAK_BRANCH;
   public static final RegistryObject STRIPPED_GREEN_OAK_LOG_SLAB;
   public static final RegistryObject STRIPPED_GREEN_OAK_WOOD_SLAB;
   public static final RegistryObject STRIPPED_GREEN_OAK_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_GREEN_OAK_BRANCH;
   public static final RegistryObject GREEN_OAK_BEAM_SLAB;
   public static final RegistryObject CYPRESS_LOG_SLAB;
   public static final RegistryObject CYPRESS_WOOD_SLAB;
   public static final RegistryObject CYPRESS_WOOD_STAIRS;
   public static final RegistryObject CYPRESS_BRANCH;
   public static final RegistryObject STRIPPED_CYPRESS_LOG_SLAB;
   public static final RegistryObject STRIPPED_CYPRESS_WOOD_SLAB;
   public static final RegistryObject STRIPPED_CYPRESS_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_CYPRESS_BRANCH;
   public static final RegistryObject CYPRESS_BEAM_SLAB;
   public static final RegistryObject ROTTEN_LOG_SLAB;
   public static final RegistryObject ROTTEN_WOOD_SLAB;
   public static final RegistryObject ROTTEN_WOOD_STAIRS;
   public static final RegistryObject ROTTEN_BRANCH;
   public static final RegistryObject STRIPPED_ROTTEN_LOG_SLAB;
   public static final RegistryObject STRIPPED_ROTTEN_WOOD_SLAB;
   public static final RegistryObject STRIPPED_ROTTEN_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_ROTTEN_BRANCH;
   public static final RegistryObject ROTTEN_BEAM_SLAB;
   public static final RegistryObject LAPIS_TRIMMED_BLUE_BRICK;
   public static final RegistryObject DWARVEN_TORCH;
   public static final RegistryObject DWARVEN_WALL_TORCH;
   public static final RegistryObject SNOW_BRICK;
   public static final RegistryObject SNOW_BRICK_SLAB;
   public static final RegistryObject SNOW_BRICK_STAIRS;
   public static final RegistryObject SNOW_BRICK_WALL;
   public static final RegistryObject LOSSOTH_CRAFTING_TABLE;
   public static final RegistryObject ICE_BRICK;
   public static final RegistryObject ICE_BRICK_SLAB;
   public static final RegistryObject ICE_BRICK_STAIRS;
   public static final RegistryObject ICE_BRICK_WALL;
   public static final RegistryObject SNOW_PATH;
   public static final RegistryObject FUR_BUNDLE;
   public static final RegistryObject LEATHER_BUNDLE;
   public static final RegistryObject BLUBBER_TORCH;
   public static final RegistryObject BLUBBER_WALL_TORCH;
   public static final RegistryObject CUSTOM_WAYPOINT_MARKER;
   public static final RegistryObject DUNLENDING_CRAFTING_TABLE;
   public static final RegistryObject CARVED_CHALK_BRICK;
   public static final RegistryObject GONDOR_BEACON;
   public static final RegistryObject FALLEN_OAK_LEAVES;
   public static final RegistryObject FALLEN_SPRUCE_LEAVES;
   public static final RegistryObject FALLEN_BIRCH_LEAVES;
   public static final RegistryObject FALLEN_JUNGLE_LEAVES;
   public static final RegistryObject FALLEN_ACACIA_LEAVES;
   public static final RegistryObject FALLEN_DARK_OAK_LEAVES;
   public static final RegistryObject FALLEN_PINE_LEAVES;
   public static final RegistryObject FALLEN_MALLORN_LEAVES;
   public static final RegistryObject FALLEN_MIRK_OAK_LEAVES;
   public static final RegistryObject FALLEN_APPLE_LEAVES;
   public static final RegistryObject FALLEN_PEAR_LEAVES;
   public static final RegistryObject FALLEN_CHERRY_LEAVES;
   public static final RegistryObject FALLEN_LEBETHRON_LEAVES;
   public static final RegistryObject FALLEN_BEECH_LEAVES;
   public static final RegistryObject FALLEN_MAPLE_LEAVES;
   public static final RegistryObject FALLEN_ASPEN_LEAVES;
   public static final RegistryObject FALLEN_LAIRELOSSE_LEAVES;
   public static final RegistryObject FALLEN_CEDAR_LEAVES;
   public static final RegistryObject FALLEN_FIR_LEAVES;
   public static final RegistryObject FALLEN_LARCH_LEAVES;
   public static final RegistryObject FALLEN_HOLLY_LEAVES;
   public static final RegistryObject FALLEN_GREEN_OAK_LEAVES;
   public static final RegistryObject FALLEN_RED_OAK_LEAVES;
   public static final RegistryObject FALLEN_CYPRESS_LEAVES;
   public static final RegistryObject THATCH_FLOORING;
   public static final RegistryObject WOODEN_MUG;
   public static final RegistryObject CERAMIC_MUG;
   public static final RegistryObject GOLDEN_GOBLET;
   public static final RegistryObject SILVER_GOBLET;
   public static final RegistryObject COPPER_GOBLET;
   public static final RegistryObject WOODEN_CUP;
   public static final RegistryObject ALE_HORN;
   public static final RegistryObject GOLDEN_ALE_HORN;
   public static final RegistryObject MORDOR_BASALT_BRICK;
   public static final RegistryObject MORDOR_BASALT_BRICK_SLAB;
   public static final RegistryObject MORDOR_BASALT_BRICK_STAIRS;
   public static final RegistryObject MORDOR_BASALT_BRICK_WALL;
   public static final RegistryObject CARVED_MORDOR_BASALT_BRICK;
   public static final RegistryObject SOUL_FIRE_HEARTH_BLOCK;
   public static final RegistryObject WOODEN_GATE;
   public static final RegistryObject WOODEN_PORTCULLIS;
   public static final RegistryObject IRON_PORTCULLIS;
   public static final RegistryObject BRONZE_PORTCULLIS;
   public static final RegistryObject SILVER_GATE;
   public static final RegistryObject GOLDEN_GATE;
   public static final RegistryObject MITHRIL_GATE;
   public static final RegistryObject DWARVEN_GATE;
   public static final RegistryObject ORC_GATE;
   public static final RegistryObject GONDOR_GATE;
   public static final RegistryObject DOL_AMROTH_GATE;
   public static final RegistryObject ROHAN_GATE;
   public static final RegistryObject HIGH_ELVEN_GATE;
   public static final RegistryObject GALADHRIM_GATE;
   public static final RegistryObject WOOD_ELVEN_GATE;
   public static final RegistryObject HARAD_GATE;
   public static final RegistryObject URUK_GATE;
   public static final RegistryObject WICKER_FENCE;
   public static final RegistryObject RED_HOBBIT_GATE;
   public static final RegistryObject YELLOW_HOBBIT_GATE;
   public static final RegistryObject GREEN_HOBBIT_GATE;
   public static final RegistryObject BLUE_HOBBIT_GATE;
   public static final RegistryObject MORGUL_FLOWER;
   public static final RegistryObject POTTED_MORGUL_FLOWER;
   public static final RegistryObject REED_BALE;
   public static final RegistryObject FLAX_BALE;
   public static final RegistryObject WICKER_BARS;
   public static final RegistryObject WICKER_FENCE_GATE;
   public static final RegistryObject CULUMALDA_LOG;
   public static final RegistryObject CULUMALDA_WOOD;
   public static final RegistryObject CULUMALDA_PLANKS;
   public static final RegistryObject CULUMALDA_LEAVES;
   public static final RegistryObject CULUMALDA_SAPLING;
   public static final RegistryObject POTTED_CULUMALDA_SAPLING;
   public static final RegistryObject CULUMALDA_SLAB;
   public static final RegistryObject CULUMALDA_STAIRS;
   public static final RegistryObject CULUMALDA_FENCE;
   public static final RegistryObject CULUMALDA_FENCE_GATE;
   public static final RegistryObject CULUMALDA_DOOR;
   public static final RegistryObject CULUMALDA_TRAPDOOR;
   public static final RegistryObject CULUMALDA_PRESSURE_PLATE;
   public static final RegistryObject CULUMALDA_BUTTON;
   public static final RegistryObject STRIPPED_CULUMALDA_LOG;
   public static final RegistryObject STRIPPED_CULUMALDA_WOOD;
   public static final RegistryObject CULUMALDA_BEAM;
   public static final RegistryObject CULUMALDA_SIGN;
   public static final RegistryObject CULUMALDA_WALL_SIGN;
   public static final RegistryObject CULUMALDA_LOG_SLAB;
   public static final RegistryObject CULUMALDA_WOOD_SLAB;
   public static final RegistryObject CULUMALDA_WOOD_STAIRS;
   public static final RegistryObject CULUMALDA_BRANCH;
   public static final RegistryObject STRIPPED_CULUMALDA_LOG_SLAB;
   public static final RegistryObject STRIPPED_CULUMALDA_WOOD_SLAB;
   public static final RegistryObject STRIPPED_CULUMALDA_WOOD_STAIRS;
   public static final RegistryObject STRIPPED_CULUMALDA_BRANCH;
   public static final RegistryObject CULUMALDA_BEAM_SLAB;
   public static final RegistryObject FALLEN_CULUMALDA_LEAVES;
   public static final RegistryObject BREE_CRAFTING_TABLE;
   public static final RegistryObject PALANTIR;
   public static final RegistryObject MORDOR_DIRT_PATH;
   public static final RegistryObject CHALK_PATH;
   public static final RegistryObject OAK_VERTICAL_SLAB;
   public static final RegistryObject SPRUCE_VERTICAL_SLAB;
   public static final RegistryObject BIRCH_VERTICAL_SLAB;
   public static final RegistryObject JUNGLE_VERTICAL_SLAB;
   public static final RegistryObject ACACIA_VERTICAL_SLAB;
   public static final RegistryObject DARK_OAK_VERTICAL_SLAB;
   public static final RegistryObject STONE_VERTICAL_SLAB;
   public static final RegistryObject SMOOTH_STONE_VERTICAL_SLAB;
   public static final RegistryObject SANDSTONE_VERTICAL_SLAB;
   public static final RegistryObject CUT_SANDSTONE_VERTICAL_SLAB;
   public static final RegistryObject PETRIFIED_OAK_VERTICAL_SLAB;
   public static final RegistryObject COBBLESTONE_VERTICAL_SLAB;
   public static final RegistryObject BRICK_VERTICAL_SLAB;
   public static final RegistryObject STONE_BRICK_VERTICAL_SLAB;
   public static final RegistryObject NETHER_BRICK_VERTICAL_SLAB;
   public static final RegistryObject QUARTZ_VERTICAL_SLAB;
   public static final RegistryObject RED_SANDSTONE_VERTICAL_SLAB;
   public static final RegistryObject CUT_RED_SANDSTONE_SLAB;
   public static final RegistryObject PRISMARINE_VERTICAL_SLAB;
   public static final RegistryObject PRISMARINE_BRICK_VERTICAL_SLAB;
   public static final RegistryObject DARK_PRISMARINE_VERTICAL_SLAB;
   public static final RegistryObject PURPUR_VERTICAL_SLAB;
   public static final RegistryObject POLISHED_GRANITE_VERTICAL_SLAB;
   public static final RegistryObject SMOOTH_RED_SANDSTONE_VERTICAL_SLAB;
   public static final RegistryObject MOSSY_STONE_BRICK_VERTICAL_SLAB;
   public static final RegistryObject POLISHED_DIORITE_VERTICAL_SLAB;
   public static final RegistryObject MOSSY_COBBLESTONE_VERTICAL_SLAB;
   public static final RegistryObject END_STONE_BRICK_VERTICAL_SLAB;
   public static final RegistryObject SMOOTH_SANDSTONE_VERTICAL_SLAB;
   public static final RegistryObject SMOOTH_QUARTZ_VERTICAL_SLAB;
   public static final RegistryObject GRANITE_VERTICAL_SLAB;
   public static final RegistryObject ANDESITE_VERTICAL_SLAB;
   public static final RegistryObject RED_NETHER_BRICK_VERTICAL_SLAB;
   public static final RegistryObject POLISHED_ANDESITE_VERTICAL_SLAB;
   public static final RegistryObject DIORITE_VERTICAL_SLAB;
   public static final RegistryObject CRIMSON_VERTICAL_SLAB;
   public static final RegistryObject WARPED_VERTICAL_SLAB;
   public static final RegistryObject BLACKSTONE_VERTICAL_SLAB;
   public static final RegistryObject POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB;
   public static final RegistryObject POLISHED_BLACKSTONE_VERTICAL_SLAB;

   public static void register() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      BLOCKS.register(bus);
      bus.addGenericListener(Block.class, SignSetupHelper::register);
      bus.addGenericListener(Block.class, LOTRBiomeFeatures::setup);
   }

   private static RegistryObject makeVerticalVanillaSlab(String name, Block slabBlock) {
      String fullName = String.format("%s_vertical_slab", name);
      return BLOCKS.register(fullName, () -> {
         return new VerticalOnlySlabBlock(slabBlock);
      });
   }

   public static ToIntFunction constantLight(int light) {
      return (state) -> {
         return light;
      };
   }

   public static boolean posPredicateTrue(BlockState state, IBlockReader reader, BlockPos pos) {
      return true;
   }

   public static boolean posPredicateFalse(BlockState state, IBlockReader reader, BlockPos pos) {
      return false;
   }

   public static boolean allowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType entityType) {
      return true;
   }

   public static boolean notAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType entityType) {
      return false;
   }

   public static boolean allowSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType entityType) {
      return entityType == EntityType.field_200781_U || entityType == EntityType.field_200783_W;
   }

   static {
      BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "lotr");
      SOUND_CERAMIC = new SoundType(1.0F, 1.0F, LOTRSoundEvents.CERAMIC_BREAK, SoundEvents.field_187902_gb, SoundEvents.field_187845_fY, SoundEvents.field_187843_fX, SoundEvents.field_187841_fW);
      SOUND_TREASURE = new SoundType(1.0F, 1.0F, LOTRSoundEvents.TREASURE_BREAK, LOTRSoundEvents.TREASURE_STEP, LOTRSoundEvents.TREASURE_PLACE, LOTRSoundEvents.TREASURE_HIT, LOTRSoundEvents.TREASURE_FALL) {
         private Random treasureRand = new Random();

         public float func_185847_b() {
            return super.func_185847_b() * (0.85F + this.treasureRand.nextFloat() * 0.3F);
         }
      };
      GONDOR_ROCK = BLOCKS.register("gondor_rock", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151659_e);
      });
      GONDOR_BRICK = BLOCKS.register("gondor_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151659_e);
      });
      MORDOR_ROCK = BLOCKS.register("mordor_rock", () -> {
         return new MordorRockBlock(MaterialColor.field_151646_E);
      });
      MORDOR_BRICK = BLOCKS.register("mordor_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151646_E);
      });
      ROHAN_ROCK = BLOCKS.register("rohan_rock", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151664_l);
      });
      ROHAN_BRICK = BLOCKS.register("rohan_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151664_l);
      });
      BLUE_ROCK = BLOCKS.register("blue_rock", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151674_s);
      });
      BLUE_BRICK = BLOCKS.register("blue_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151674_s);
      });
      RED_ROCK = BLOCKS.register("red_rock", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193562_N);
      });
      RED_BRICK = BLOCKS.register("red_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193562_N);
      });
      SIMBELMYNE = BLOCKS.register("simbelmyne", () -> {
         return (new LOTRFlowerBlock(Effects.field_76428_l, 10)).setWideFlat();
      });
      SHIRE_HEATHER = BLOCKS.register("shire_heather", () -> {
         return (new LOTRFlowerBlock(Effects.field_76443_y, 7)).setWide();
      });
      COPPER_ORE = BLOCKS.register("copper_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(1);
      });
      TIN_ORE = BLOCKS.register("tin_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(1);
      });
      SILVER_ORE = BLOCKS.register("silver_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(2);
      });
      MITHRIL_ORE = BLOCKS.register("mithril_ore", () -> {
         return (new MithrilOreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(4.0F, 6.0F))).setOreLevel(2);
      });
      COPPER_BLOCK = BLOCKS.register("copper_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151676_q).func_200948_a(4.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      TIN_BLOCK = BLOCKS.register("tin_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151665_m).func_200948_a(2.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      SILVER_BLOCK = BLOCKS.register("silver_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_197656_x).func_200948_a(3.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 2);
      });
      MITHRIL_BLOCK = BLOCKS.register("mithril_block", () -> {
         return new MithrilBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_197656_x).func_200948_a(5.0F, 12.0F).func_200947_a(SoundType.field_185852_e), 2, true);
      });
      BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151676_q).func_200948_a(4.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      POTTED_SIMBELMYNE = BLOCKS.register("potted_simbelmyne", () -> {
         return new LOTRPottedPlantBlock(SIMBELMYNE);
      });
      POTTED_SHIRE_HEATHER = BLOCKS.register("potted_shire_heather", () -> {
         return new LOTRPottedPlantBlock(SHIRE_HEATHER);
      });
      GONDOR_ROCK_SLAB = BLOCKS.register("gondor_rock_slab", () -> {
         return new AxialSlabBlock(GONDOR_ROCK);
      });
      GONDOR_ROCK_STAIRS = BLOCKS.register("gondor_rock_stairs", () -> {
         return new LOTRStairsBlock(GONDOR_ROCK);
      });
      GONDOR_ROCK_WALL = BLOCKS.register("gondor_rock_wall", () -> {
         return new LOTRWallBlock(GONDOR_ROCK);
      });
      GONDOR_CRAFTING_TABLE = BLOCKS.register("gondor_crafting_table", () -> {
         return new FactionCraftingTableBlock.Gondor(GONDOR_BRICK);
      });
      MORDOR_CRAFTING_TABLE = BLOCKS.register("mordor_crafting_table", () -> {
         return new FactionCraftingTableBlock.Mordor(MORDOR_BRICK);
      });
      ROHAN_CRAFTING_TABLE = BLOCKS.register("rohan_crafting_table", () -> {
         return new FactionCraftingTableBlock.Rohan(() -> {
            return Blocks.field_196662_n;
         });
      });
      PINE_LOG = BLOCKS.register("pine_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151658_d, MaterialColor.field_151654_J, () -> {
            return (Block)STRIPPED_PINE_LOG.get();
         });
      });
      PINE_WOOD = BLOCKS.register("pine_wood", () -> {
         return new StrippableWoodBlock(PINE_LOG, () -> {
            return (Block)STRIPPED_PINE_WOOD.get();
         });
      });
      PINE_PLANKS = BLOCKS.register("pine_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151658_d);
      });
      PINE_LEAVES = BLOCKS.register("pine_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      PINE_SAPLING = BLOCKS.register("pine_sapling", () -> {
         return new LOTRSaplingBlock(new PineTree());
      });
      POTTED_PINE_SAPLING = BLOCKS.register("potted_pine_sapling", () -> {
         return new LOTRPottedPlantBlock(PINE_SAPLING);
      });
      PINE_SLAB = BLOCKS.register("pine_slab", () -> {
         return new AxialSlabBlock(PINE_PLANKS);
      });
      PINE_STAIRS = BLOCKS.register("pine_stairs", () -> {
         return new LOTRStairsBlock(PINE_PLANKS);
      });
      PINE_FENCE = BLOCKS.register("pine_fence", () -> {
         return new LOTRFenceBlock(PINE_PLANKS);
      });
      PINE_FENCE_GATE = BLOCKS.register("pine_fence_gate", () -> {
         return new LOTRFenceGateBlock(PINE_PLANKS);
      });
      PINE_DOOR = BLOCKS.register("pine_door", () -> {
         return new LOTRDoorBlock(PINE_PLANKS);
      });
      PINE_TRAPDOOR = BLOCKS.register("pine_trapdoor", () -> {
         return new LOTRTrapdoorBlock(PINE_PLANKS);
      });
      PINE_PRESSURE_PLATE = BLOCKS.register("pine_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(PINE_PLANKS);
      });
      PINE_BUTTON = BLOCKS.register("pine_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_PINE_LOG = BLOCKS.register("stripped_pine_log", () -> {
         return new LOTRStrippedLogBlock(PINE_LOG);
      });
      STRIPPED_PINE_WOOD = BLOCKS.register("stripped_pine_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_PINE_LOG);
      });
      PINE_BEAM = BLOCKS.register("pine_beam", () -> {
         return new WoodBeamBlock(PINE_LOG);
      });
      PINE_SIGN = BLOCKS.register("pine_sign", () -> {
         return new LOTRStandingSignBlock(PINE_PLANKS, LOTRSignTypes.PINE);
      });
      PINE_WALL_SIGN = BLOCKS.register("pine_wall_sign", () -> {
         return new LOTRWallSignBlock(PINE_SIGN);
      });
      MALLORN_LOG = BLOCKS.register("mallorn_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151677_p, MaterialColor.field_151659_e, () -> {
            return (Block)STRIPPED_MALLORN_LOG.get();
         });
      });
      MALLORN_WOOD = BLOCKS.register("mallorn_wood", () -> {
         return new StrippableWoodBlock(MALLORN_LOG, () -> {
            return (Block)STRIPPED_MALLORN_WOOD.get();
         });
      });
      MALLORN_PLANKS = BLOCKS.register("mallorn_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151677_p);
      });
      MALLORN_LEAVES = BLOCKS.register("mallorn_leaves", () -> {
         return (new LOTRLeavesBlock(MaterialColor.field_151647_F)).setFallingParticle(LOTRParticles.MALLORN_LEAF, 75);
      });
      MALLORN_SAPLING = BLOCKS.register("mallorn_sapling", () -> {
         return new LOTRSaplingBlock(new MallornTree());
      });
      POTTED_MALLORN_SAPLING = BLOCKS.register("potted_mallorn_sapling", () -> {
         return new LOTRPottedPlantBlock(MALLORN_SAPLING);
      });
      MALLORN_SLAB = BLOCKS.register("mallorn_slab", () -> {
         return new AxialSlabBlock(MALLORN_PLANKS);
      });
      MALLORN_STAIRS = BLOCKS.register("mallorn_stairs", () -> {
         return new LOTRStairsBlock(MALLORN_PLANKS);
      });
      MALLORN_FENCE = BLOCKS.register("mallorn_fence", () -> {
         return new LOTRFenceBlock(MALLORN_PLANKS);
      });
      MALLORN_FENCE_GATE = BLOCKS.register("mallorn_fence_gate", () -> {
         return new LOTRFenceGateBlock(MALLORN_PLANKS);
      });
      MALLORN_DOOR = BLOCKS.register("mallorn_door", () -> {
         return new LOTRDoorBlock(MALLORN_PLANKS);
      });
      MALLORN_TRAPDOOR = BLOCKS.register("mallorn_trapdoor", () -> {
         return new LOTRTrapdoorBlock(MALLORN_PLANKS);
      });
      MALLORN_PRESSURE_PLATE = BLOCKS.register("mallorn_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(MALLORN_PLANKS);
      });
      MALLORN_BUTTON = BLOCKS.register("mallorn_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_MALLORN_LOG = BLOCKS.register("stripped_mallorn_log", () -> {
         return new LOTRStrippedLogBlock(MALLORN_LOG);
      });
      STRIPPED_MALLORN_WOOD = BLOCKS.register("stripped_mallorn_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_MALLORN_LOG);
      });
      MALLORN_BEAM = BLOCKS.register("mallorn_beam", () -> {
         return new WoodBeamBlock(MALLORN_LOG);
      });
      MALLORN_SIGN = BLOCKS.register("mallorn_sign", () -> {
         return new LOTRStandingSignBlock(MALLORN_PLANKS, LOTRSignTypes.MALLORN);
      });
      MALLORN_WALL_SIGN = BLOCKS.register("mallorn_wall_sign", () -> {
         return new LOTRWallSignBlock(MALLORN_SIGN);
      });
      DWARVEN_BRICK = BLOCKS.register("dwarven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151665_m);
      });
      HIGH_ELVEN_BRICK = BLOCKS.register("high_elven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151668_h);
      });
      GALADHRIM_BRICK = BLOCKS.register("galadhrim_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151668_h);
      });
      WOOD_ELVEN_BRICK = BLOCKS.register("wood_elven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193561_M);
      });
      DWARVEN_CRAFTING_TABLE = BLOCKS.register("dwarven_crafting_table", () -> {
         return new FactionCraftingTableBlock.Dwarven(DWARVEN_BRICK);
      });
      LINDON_CRAFTING_TABLE = BLOCKS.register("lindon_crafting_table", () -> {
         return new FactionCraftingTableBlock.Lindon(() -> {
            return Blocks.field_196662_n;
         });
      });
      RIVENDELL_CRAFTING_TABLE = BLOCKS.register("rivendell_crafting_table", () -> {
         return new FactionCraftingTableBlock.Rivendell(() -> {
            return Blocks.field_196662_n;
         });
      });
      GALADHRIM_CRAFTING_TABLE = BLOCKS.register("galadhrim_crafting_table", () -> {
         return new FactionCraftingTableBlock.Galadhrim(MALLORN_PLANKS);
      });
      WOOD_ELVEN_CRAFTING_TABLE = BLOCKS.register("wood_elven_crafting_table", () -> {
         return new FactionCraftingTableBlock.WoodElven(() -> {
            return Blocks.field_196662_n;
         });
      });
      APPLE_CRUMBLE = BLOCKS.register("apple_crumble", () -> {
         return new CakelikeBlock();
      });
      SALT_ORE = BLOCKS.register("salt_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(0);
      });
      SULFUR_ORE = BLOCKS.register("sulfur_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(0);
      });
      NITER_ORE = BLOCKS.register("niter_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(0);
      });
      SALT_BLOCK = BLOCKS.register("salt_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151666_j).func_200948_a(3.0F, 3.0F).func_200947_a(SoundType.field_185852_e), 0);
      });
      SULFUR_BLOCK = BLOCKS.register("sulfur_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151673_t).func_200948_a(3.0F, 3.0F).func_200947_a(SoundType.field_185852_e), 0);
      });
      NITER_BLOCK = BLOCKS.register("niter_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_193561_M).func_200948_a(3.0F, 3.0F).func_200947_a(SoundType.field_185852_e), 0);
      });
      MORGUL_IRON_ORE_MORDOR = BLOCKS.register("morgul_iron_ore_mordor", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(1);
      });
      MORGUL_IRON_ORE_STONE = BLOCKS.register("morgul_iron_ore_stone", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F))).setOreLevel(1);
      });
      DURNOR_ORE = BLOCKS.register("durnor_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F).func_235838_a_(constantLight(8)))).setOreLevel(0);
      });
      ORC_STEEL_BLOCK = BLOCKS.register("orc_steel_block", () -> {
         return new DirectionalMineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151670_w).func_200948_a(5.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      DURNOR_BLOCK = BLOCKS.register("durnor_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151645_D).func_200948_a(5.0F, 6.0F).func_235838_a_(constantLight(8)).func_200947_a(SoundType.field_185852_e), 0);
      });
      HARAD_BRICK = BLOCKS.register("harad_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151658_d);
      });
      RED_HARAD_BRICK = BLOCKS.register("red_harad_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193562_N);
      });
      HARAD_CRAFTING_TABLE = BLOCKS.register("harad_crafting_table", () -> {
         return new FactionCraftingTableBlock.Harad(HARAD_BRICK);
      });
      GONDOR_BRICK_SLAB = BLOCKS.register("gondor_brick_slab", () -> {
         return new AxialSlabBlock(GONDOR_BRICK);
      });
      GONDOR_BRICK_STAIRS = BLOCKS.register("gondor_brick_stairs", () -> {
         return new LOTRStairsBlock(GONDOR_BRICK);
      });
      GONDOR_BRICK_WALL = BLOCKS.register("gondor_brick_wall", () -> {
         return new LOTRWallBlock(GONDOR_BRICK);
      });
      MORDOR_ROCK_SLAB = BLOCKS.register("mordor_rock_slab", () -> {
         return new AxialSlabBlock(MORDOR_ROCK);
      });
      MORDOR_ROCK_STAIRS = BLOCKS.register("mordor_rock_stairs", () -> {
         return new LOTRStairsBlock(MORDOR_ROCK);
      });
      MORDOR_ROCK_WALL = BLOCKS.register("mordor_rock_wall", () -> {
         return new LOTRWallBlock(MORDOR_ROCK);
      });
      MORDOR_BRICK_SLAB = BLOCKS.register("mordor_brick_slab", () -> {
         return new AxialSlabBlock(MORDOR_BRICK);
      });
      MORDOR_BRICK_STAIRS = BLOCKS.register("mordor_brick_stairs", () -> {
         return new LOTRStairsBlock(MORDOR_BRICK);
      });
      MORDOR_BRICK_WALL = BLOCKS.register("mordor_brick_wall", () -> {
         return new LOTRWallBlock(MORDOR_BRICK);
      });
      ROHAN_ROCK_SLAB = BLOCKS.register("rohan_rock_slab", () -> {
         return new AxialSlabBlock(ROHAN_ROCK);
      });
      ROHAN_ROCK_STAIRS = BLOCKS.register("rohan_rock_stairs", () -> {
         return new LOTRStairsBlock(ROHAN_ROCK);
      });
      ROHAN_ROCK_WALL = BLOCKS.register("rohan_rock_wall", () -> {
         return new LOTRWallBlock(ROHAN_ROCK);
      });
      ROHAN_BRICK_SLAB = BLOCKS.register("rohan_brick_slab", () -> {
         return new AxialSlabBlock(ROHAN_BRICK);
      });
      ROHAN_BRICK_STAIRS = BLOCKS.register("rohan_brick_stairs", () -> {
         return new LOTRStairsBlock(ROHAN_BRICK);
      });
      ROHAN_BRICK_WALL = BLOCKS.register("rohan_brick_wall", () -> {
         return new LOTRWallBlock(ROHAN_BRICK);
      });
      BLUE_ROCK_SLAB = BLOCKS.register("blue_rock_slab", () -> {
         return new AxialSlabBlock(BLUE_ROCK);
      });
      BLUE_ROCK_STAIRS = BLOCKS.register("blue_rock_stairs", () -> {
         return new LOTRStairsBlock(BLUE_ROCK);
      });
      BLUE_ROCK_WALL = BLOCKS.register("blue_rock_wall", () -> {
         return new LOTRWallBlock(BLUE_ROCK);
      });
      BLUE_BRICK_SLAB = BLOCKS.register("blue_brick_slab", () -> {
         return new AxialSlabBlock(BLUE_BRICK);
      });
      BLUE_BRICK_STAIRS = BLOCKS.register("blue_brick_stairs", () -> {
         return new LOTRStairsBlock(BLUE_BRICK);
      });
      BLUE_BRICK_WALL = BLOCKS.register("blue_brick_wall", () -> {
         return new LOTRWallBlock(BLUE_BRICK);
      });
      RED_ROCK_SLAB = BLOCKS.register("red_rock_slab", () -> {
         return new AxialSlabBlock(RED_ROCK);
      });
      RED_ROCK_STAIRS = BLOCKS.register("red_rock_stairs", () -> {
         return new LOTRStairsBlock(RED_ROCK);
      });
      RED_ROCK_WALL = BLOCKS.register("red_rock_wall", () -> {
         return new LOTRWallBlock(RED_ROCK);
      });
      RED_BRICK_SLAB = BLOCKS.register("red_brick_slab", () -> {
         return new AxialSlabBlock(RED_BRICK);
      });
      RED_BRICK_STAIRS = BLOCKS.register("red_brick_stairs", () -> {
         return new LOTRStairsBlock(RED_BRICK);
      });
      RED_BRICK_WALL = BLOCKS.register("red_brick_wall", () -> {
         return new LOTRWallBlock(RED_BRICK);
      });
      DWARVEN_BRICK_SLAB = BLOCKS.register("dwarven_brick_slab", () -> {
         return new AxialSlabBlock(DWARVEN_BRICK);
      });
      DWARVEN_BRICK_STAIRS = BLOCKS.register("dwarven_brick_stairs", () -> {
         return new LOTRStairsBlock(DWARVEN_BRICK);
      });
      DWARVEN_BRICK_WALL = BLOCKS.register("dwarven_brick_wall", () -> {
         return new LOTRWallBlock(DWARVEN_BRICK);
      });
      HIGH_ELVEN_BRICK_SLAB = BLOCKS.register("high_elven_brick_slab", () -> {
         return new AxialSlabBlock(HIGH_ELVEN_BRICK);
      });
      HIGH_ELVEN_BRICK_STAIRS = BLOCKS.register("high_elven_brick_stairs", () -> {
         return new LOTRStairsBlock(HIGH_ELVEN_BRICK);
      });
      HIGH_ELVEN_BRICK_WALL = BLOCKS.register("high_elven_brick_wall", () -> {
         return new LOTRWallBlock(HIGH_ELVEN_BRICK);
      });
      GALADHRIM_BRICK_SLAB = BLOCKS.register("galadhrim_brick_slab", () -> {
         return new AxialSlabBlock(GALADHRIM_BRICK);
      });
      GALADHRIM_BRICK_STAIRS = BLOCKS.register("galadhrim_brick_stairs", () -> {
         return new LOTRStairsBlock(GALADHRIM_BRICK);
      });
      GALADHRIM_BRICK_WALL = BLOCKS.register("galadhrim_brick_wall", () -> {
         return new LOTRWallBlock(GALADHRIM_BRICK);
      });
      WOOD_ELVEN_BRICK_SLAB = BLOCKS.register("wood_elven_brick_slab", () -> {
         return new AxialSlabBlock(WOOD_ELVEN_BRICK);
      });
      WOOD_ELVEN_BRICK_STAIRS = BLOCKS.register("wood_elven_brick_stairs", () -> {
         return new LOTRStairsBlock(WOOD_ELVEN_BRICK);
      });
      WOOD_ELVEN_BRICK_WALL = BLOCKS.register("wood_elven_brick_wall", () -> {
         return new LOTRWallBlock(WOOD_ELVEN_BRICK);
      });
      HARAD_BRICK_SLAB = BLOCKS.register("harad_brick_slab", () -> {
         return new AxialSlabBlock(HARAD_BRICK);
      });
      HARAD_BRICK_STAIRS = BLOCKS.register("harad_brick_stairs", () -> {
         return new LOTRStairsBlock(HARAD_BRICK);
      });
      HARAD_BRICK_WALL = BLOCKS.register("harad_brick_wall", () -> {
         return new LOTRWallBlock(HARAD_BRICK);
      });
      RED_HARAD_BRICK_SLAB = BLOCKS.register("red_harad_brick_slab", () -> {
         return new AxialSlabBlock(RED_HARAD_BRICK);
      });
      RED_HARAD_BRICK_STAIRS = BLOCKS.register("red_harad_brick_stairs", () -> {
         return new LOTRStairsBlock(RED_HARAD_BRICK);
      });
      RED_HARAD_BRICK_WALL = BLOCKS.register("red_harad_brick_wall", () -> {
         return new LOTRWallBlock(RED_HARAD_BRICK);
      });
      DWARVEN_STEEL_BLOCK = BLOCKS.register("dwarven_steel_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151670_w).func_200948_a(5.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      ELVEN_STEEL_BLOCK = BLOCKS.register("elven_steel_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151668_h).func_200948_a(5.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      MIRK_OAK_LOG = BLOCKS.register("mirk_oak_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151650_B, MaterialColor.field_151650_B, () -> {
            return (Block)STRIPPED_MIRK_OAK_LOG.get();
         });
      });
      MIRK_OAK_WOOD = BLOCKS.register("mirk_oak_wood", () -> {
         return new StrippableWoodBlock(MIRK_OAK_LOG, () -> {
            return (Block)STRIPPED_MIRK_OAK_WOOD.get();
         });
      });
      MIRK_OAK_PLANKS = BLOCKS.register("mirk_oak_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151650_B);
      });
      MIRK_OAK_LEAVES = BLOCKS.register("mirk_oak_leaves", () -> {
         return (new MirkOakLeavesBlock()).setFallingParticle(LOTRParticles.MIRK_OAK_LEAF, 250);
      });
      MIRK_OAK_SAPLING = BLOCKS.register("mirk_oak_sapling", () -> {
         return new LOTRSaplingBlock(new MirkOakTree());
      });
      POTTED_MIRK_OAK_SAPLING = BLOCKS.register("potted_mirk_oak_sapling", () -> {
         return new LOTRPottedPlantBlock(MIRK_OAK_SAPLING);
      });
      MIRK_OAK_SLAB = BLOCKS.register("mirk_oak_slab", () -> {
         return new AxialSlabBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_STAIRS = BLOCKS.register("mirk_oak_stairs", () -> {
         return new LOTRStairsBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_FENCE = BLOCKS.register("mirk_oak_fence", () -> {
         return new LOTRFenceBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_FENCE_GATE = BLOCKS.register("mirk_oak_fence_gate", () -> {
         return new LOTRFenceGateBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_DOOR = BLOCKS.register("mirk_oak_door", () -> {
         return new LOTRDoorBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_TRAPDOOR = BLOCKS.register("mirk_oak_trapdoor", () -> {
         return new LOTRTrapdoorBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_PRESSURE_PLATE = BLOCKS.register("mirk_oak_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(MIRK_OAK_PLANKS);
      });
      MIRK_OAK_BUTTON = BLOCKS.register("mirk_oak_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_MIRK_OAK_LOG = BLOCKS.register("stripped_mirk_oak_log", () -> {
         return new LOTRStrippedLogBlock(MIRK_OAK_LOG);
      });
      STRIPPED_MIRK_OAK_WOOD = BLOCKS.register("stripped_mirk_oak_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_MIRK_OAK_LOG);
      });
      MIRK_OAK_BEAM = BLOCKS.register("mirk_oak_beam", () -> {
         return new WoodBeamBlock(MIRK_OAK_LOG);
      });
      MIRK_OAK_SIGN = BLOCKS.register("mirk_oak_sign", () -> {
         return new LOTRStandingSignBlock(MIRK_OAK_PLANKS, LOTRSignTypes.MIRK_OAK);
      });
      MIRK_OAK_WALL_SIGN = BLOCKS.register("mirk_oak_wall_sign", () -> {
         return new LOTRWallSignBlock(MIRK_OAK_SIGN);
      });
      CHARRED_LOG = BLOCKS.register("charred_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151670_w, MaterialColor.field_151646_E, () -> {
            return (Block)STRIPPED_CHARRED_LOG.get();
         });
      });
      CHARRED_WOOD = BLOCKS.register("charred_wood", () -> {
         return new StrippableWoodBlock(CHARRED_LOG, () -> {
            return (Block)STRIPPED_CHARRED_WOOD.get();
         });
      });
      CHARRED_PLANKS = BLOCKS.register("charred_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151646_E);
      });
      CHARRED_SLAB = BLOCKS.register("charred_slab", () -> {
         return new AxialSlabBlock(CHARRED_PLANKS);
      });
      CHARRED_STAIRS = BLOCKS.register("charred_stairs", () -> {
         return new LOTRStairsBlock(CHARRED_PLANKS);
      });
      CHARRED_FENCE = BLOCKS.register("charred_fence", () -> {
         return new LOTRFenceBlock(CHARRED_PLANKS);
      });
      CHARRED_FENCE_GATE = BLOCKS.register("charred_fence_gate", () -> {
         return new LOTRFenceGateBlock(CHARRED_PLANKS);
      });
      CHARRED_DOOR = BLOCKS.register("charred_door", () -> {
         return new LOTRDoorBlock(CHARRED_PLANKS);
      });
      CHARRED_TRAPDOOR = BLOCKS.register("charred_trapdoor", () -> {
         return new LOTRTrapdoorBlock(CHARRED_PLANKS);
      });
      CHARRED_PRESSURE_PLATE = BLOCKS.register("charred_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(CHARRED_PLANKS);
      });
      CHARRED_BUTTON = BLOCKS.register("charred_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_CHARRED_LOG = BLOCKS.register("stripped_charred_log", () -> {
         return new LOTRStrippedLogBlock(CHARRED_LOG);
      });
      STRIPPED_CHARRED_WOOD = BLOCKS.register("stripped_charred_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_CHARRED_LOG);
      });
      CHARRED_BEAM = BLOCKS.register("charred_beam", () -> {
         return new WoodBeamBlock(CHARRED_LOG);
      });
      CHARRED_SIGN = BLOCKS.register("charred_sign", () -> {
         return new LOTRStandingSignBlock(CHARRED_PLANKS, LOTRSignTypes.CHARRED);
      });
      CHARRED_WALL_SIGN = BLOCKS.register("charred_wall_sign", () -> {
         return new LOTRWallSignBlock(CHARRED_SIGN);
      });
      APPLE_LOG = BLOCKS.register("apple_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151663_o, MaterialColor.field_151654_J, () -> {
            return (Block)STRIPPED_APPLE_LOG.get();
         });
      });
      APPLE_WOOD = BLOCKS.register("apple_wood", () -> {
         return new StrippableWoodBlock(APPLE_LOG, () -> {
            return (Block)STRIPPED_APPLE_WOOD.get();
         });
      });
      APPLE_PLANKS = BLOCKS.register("apple_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151663_o);
      });
      APPLE_LEAVES = BLOCKS.register("apple_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      APPLE_LEAVES_RED = BLOCKS.register("apple_leaves_red", () -> {
         return new LOTRLeavesBlock();
      });
      APPLE_LEAVES_GREEN = BLOCKS.register("apple_leaves_green", () -> {
         return new LOTRLeavesBlock();
      });
      APPLE_SAPLING = BLOCKS.register("apple_sapling", () -> {
         return new LOTRSaplingBlock(new AppleTree());
      });
      POTTED_APPLE_SAPLING = BLOCKS.register("potted_apple_sapling", () -> {
         return new LOTRPottedPlantBlock(APPLE_SAPLING);
      });
      APPLE_SLAB = BLOCKS.register("apple_slab", () -> {
         return new AxialSlabBlock(APPLE_PLANKS);
      });
      APPLE_STAIRS = BLOCKS.register("apple_stairs", () -> {
         return new LOTRStairsBlock(APPLE_PLANKS);
      });
      APPLE_FENCE = BLOCKS.register("apple_fence", () -> {
         return new LOTRFenceBlock(APPLE_PLANKS);
      });
      APPLE_FENCE_GATE = BLOCKS.register("apple_fence_gate", () -> {
         return new LOTRFenceGateBlock(APPLE_PLANKS);
      });
      APPLE_DOOR = BLOCKS.register("apple_door", () -> {
         return new LOTRDoorBlock(APPLE_PLANKS);
      });
      APPLE_TRAPDOOR = BLOCKS.register("apple_trapdoor", () -> {
         return new LOTRTrapdoorBlock(APPLE_PLANKS);
      });
      APPLE_PRESSURE_PLATE = BLOCKS.register("apple_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(APPLE_PLANKS);
      });
      APPLE_BUTTON = BLOCKS.register("apple_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_APPLE_LOG = BLOCKS.register("stripped_apple_log", () -> {
         return new LOTRStrippedLogBlock(APPLE_LOG);
      });
      STRIPPED_APPLE_WOOD = BLOCKS.register("stripped_apple_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_APPLE_LOG);
      });
      APPLE_BEAM = BLOCKS.register("apple_beam", () -> {
         return new WoodBeamBlock(APPLE_LOG);
      });
      APPLE_SIGN = BLOCKS.register("apple_sign", () -> {
         return new LOTRStandingSignBlock(APPLE_PLANKS, LOTRSignTypes.APPLE);
      });
      APPLE_WALL_SIGN = BLOCKS.register("apple_wall_sign", () -> {
         return new LOTRWallSignBlock(APPLE_SIGN);
      });
      PEAR_LOG = BLOCKS.register("pear_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151663_o, MaterialColor.field_151654_J, () -> {
            return (Block)STRIPPED_PEAR_LOG.get();
         });
      });
      PEAR_WOOD = BLOCKS.register("pear_wood", () -> {
         return new StrippableWoodBlock(PEAR_LOG, () -> {
            return (Block)STRIPPED_PEAR_WOOD.get();
         });
      });
      PEAR_PLANKS = BLOCKS.register("pear_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151663_o);
      });
      PEAR_LEAVES = BLOCKS.register("pear_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      PEAR_LEAVES_FRUIT = BLOCKS.register("pear_leaves_fruit", () -> {
         return new LOTRLeavesBlock();
      });
      PEAR_SAPLING = BLOCKS.register("pear_sapling", () -> {
         return new LOTRSaplingBlock(new PearTree());
      });
      POTTED_PEAR_SAPLING = BLOCKS.register("potted_pear_sapling", () -> {
         return new LOTRPottedPlantBlock(PEAR_SAPLING);
      });
      PEAR_SLAB = BLOCKS.register("pear_slab", () -> {
         return new AxialSlabBlock(PEAR_PLANKS);
      });
      PEAR_STAIRS = BLOCKS.register("pear_stairs", () -> {
         return new LOTRStairsBlock(PEAR_PLANKS);
      });
      PEAR_FENCE = BLOCKS.register("pear_fence", () -> {
         return new LOTRFenceBlock(PEAR_PLANKS);
      });
      PEAR_FENCE_GATE = BLOCKS.register("pear_fence_gate", () -> {
         return new LOTRFenceGateBlock(PEAR_PLANKS);
      });
      PEAR_DOOR = BLOCKS.register("pear_door", () -> {
         return new LOTRDoorBlock(PEAR_PLANKS);
      });
      PEAR_TRAPDOOR = BLOCKS.register("pear_trapdoor", () -> {
         return new LOTRTrapdoorBlock(PEAR_PLANKS);
      });
      PEAR_PRESSURE_PLATE = BLOCKS.register("pear_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(PEAR_PLANKS);
      });
      PEAR_BUTTON = BLOCKS.register("pear_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_PEAR_LOG = BLOCKS.register("stripped_pear_log", () -> {
         return new LOTRStrippedLogBlock(PEAR_LOG);
      });
      STRIPPED_PEAR_WOOD = BLOCKS.register("stripped_pear_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_PEAR_LOG);
      });
      PEAR_BEAM = BLOCKS.register("pear_beam", () -> {
         return new WoodBeamBlock(PEAR_LOG);
      });
      PEAR_SIGN = BLOCKS.register("pear_sign", () -> {
         return new LOTRStandingSignBlock(PEAR_PLANKS, LOTRSignTypes.PEAR);
      });
      PEAR_WALL_SIGN = BLOCKS.register("pear_wall_sign", () -> {
         return new LOTRWallSignBlock(PEAR_SIGN);
      });
      CHERRY_LOG = BLOCKS.register("cherry_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_193559_aa, MaterialColor.field_193559_aa, () -> {
            return (Block)STRIPPED_CHERRY_LOG.get();
         });
      });
      CHERRY_WOOD = BLOCKS.register("cherry_wood", () -> {
         return new StrippableWoodBlock(CHERRY_LOG, () -> {
            return (Block)STRIPPED_CHERRY_WOOD.get();
         });
      });
      CHERRY_PLANKS = BLOCKS.register("cherry_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_193559_aa);
      });
      CHERRY_LEAVES = BLOCKS.register("cherry_leaves", () -> {
         return new LOTRLeavesBlock(MaterialColor.field_151671_v);
      });
      CHERRY_LEAVES_FRUIT = BLOCKS.register("cherry_leaves_fruit", () -> {
         return new LOTRLeavesBlock(MaterialColor.field_151671_v);
      });
      CHERRY_SAPLING = BLOCKS.register("cherry_sapling", () -> {
         return new LOTRSaplingBlock(new CherryTree());
      });
      POTTED_CHERRY_SAPLING = BLOCKS.register("potted_cherry_sapling", () -> {
         return new LOTRPottedPlantBlock(CHERRY_SAPLING);
      });
      CHERRY_SLAB = BLOCKS.register("cherry_slab", () -> {
         return new AxialSlabBlock(CHERRY_PLANKS);
      });
      CHERRY_STAIRS = BLOCKS.register("cherry_stairs", () -> {
         return new LOTRStairsBlock(CHERRY_PLANKS);
      });
      CHERRY_FENCE = BLOCKS.register("cherry_fence", () -> {
         return new LOTRFenceBlock(CHERRY_PLANKS);
      });
      CHERRY_FENCE_GATE = BLOCKS.register("cherry_fence_gate", () -> {
         return new LOTRFenceGateBlock(CHERRY_PLANKS);
      });
      CHERRY_DOOR = BLOCKS.register("cherry_door", () -> {
         return new LOTRDoorBlock(CHERRY_PLANKS);
      });
      CHERRY_TRAPDOOR = BLOCKS.register("cherry_trapdoor", () -> {
         return new LOTRTrapdoorBlock(CHERRY_PLANKS);
      });
      CHERRY_PRESSURE_PLATE = BLOCKS.register("cherry_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(CHERRY_PLANKS);
      });
      CHERRY_BUTTON = BLOCKS.register("cherry_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_CHERRY_LOG = BLOCKS.register("stripped_cherry_log", () -> {
         return new LOTRStrippedLogBlock(CHERRY_LOG);
      });
      STRIPPED_CHERRY_WOOD = BLOCKS.register("stripped_cherry_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_CHERRY_LOG);
      });
      CHERRY_BEAM = BLOCKS.register("cherry_beam", () -> {
         return new WoodBeamBlock(CHERRY_LOG);
      });
      CHERRY_SIGN = BLOCKS.register("cherry_sign", () -> {
         return new LOTRStandingSignBlock(CHERRY_PLANKS, LOTRSignTypes.CHERRY);
      });
      CHERRY_WALL_SIGN = BLOCKS.register("cherry_wall_sign", () -> {
         return new LOTRWallSignBlock(CHERRY_SIGN);
      });
      LEBETHRON_LOG = BLOCKS.register("lebethron_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151650_B, MaterialColor.field_151650_B, () -> {
            return (Block)STRIPPED_LEBETHRON_LOG.get();
         });
      });
      LEBETHRON_WOOD = BLOCKS.register("lebethron_wood", () -> {
         return new StrippableWoodBlock(LEBETHRON_LOG, () -> {
            return (Block)STRIPPED_LEBETHRON_WOOD.get();
         });
      });
      LEBETHRON_PLANKS = BLOCKS.register("lebethron_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151650_B);
      });
      LEBETHRON_LEAVES = BLOCKS.register("lebethron_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      LEBETHRON_SAPLING = BLOCKS.register("lebethron_sapling", () -> {
         return new LOTRSaplingBlock(new LebethronTree());
      });
      POTTED_LEBETHRON_SAPLING = BLOCKS.register("potted_lebethron_sapling", () -> {
         return new LOTRPottedPlantBlock(LEBETHRON_SAPLING);
      });
      LEBETHRON_SLAB = BLOCKS.register("lebethron_slab", () -> {
         return new AxialSlabBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_STAIRS = BLOCKS.register("lebethron_stairs", () -> {
         return new LOTRStairsBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_FENCE = BLOCKS.register("lebethron_fence", () -> {
         return new LOTRFenceBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_FENCE_GATE = BLOCKS.register("lebethron_fence_gate", () -> {
         return new LOTRFenceGateBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_DOOR = BLOCKS.register("lebethron_door", () -> {
         return new LOTRDoorBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_TRAPDOOR = BLOCKS.register("lebethron_trapdoor", () -> {
         return new LOTRTrapdoorBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_PRESSURE_PLATE = BLOCKS.register("lebethron_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(LEBETHRON_PLANKS);
      });
      LEBETHRON_BUTTON = BLOCKS.register("lebethron_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_LEBETHRON_LOG = BLOCKS.register("stripped_lebethron_log", () -> {
         return new LOTRStrippedLogBlock(LEBETHRON_LOG);
      });
      STRIPPED_LEBETHRON_WOOD = BLOCKS.register("stripped_lebethron_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_LEBETHRON_LOG);
      });
      LEBETHRON_BEAM = BLOCKS.register("lebethron_beam", () -> {
         return new WoodBeamBlock(LEBETHRON_LOG);
      });
      LEBETHRON_SIGN = BLOCKS.register("lebethron_sign", () -> {
         return new LOTRStandingSignBlock(LEBETHRON_PLANKS, LOTRSignTypes.LEBETHRON);
      });
      LEBETHRON_WALL_SIGN = BLOCKS.register("lebethron_wall_sign", () -> {
         return new LOTRWallSignBlock(LEBETHRON_SIGN);
      });
      BEECH_LOG = BLOCKS.register("beech_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151663_o, MaterialColor.field_151654_J, () -> {
            return (Block)STRIPPED_BEECH_LOG.get();
         });
      });
      BEECH_WOOD = BLOCKS.register("beech_wood", () -> {
         return new StrippableWoodBlock(BEECH_LOG, () -> {
            return (Block)STRIPPED_BEECH_WOOD.get();
         });
      });
      BEECH_PLANKS = BLOCKS.register("beech_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151663_o);
      });
      BEECH_LEAVES = BLOCKS.register("beech_leaves", () -> {
         return new LOTRLeavesBlock(MaterialColor.field_151676_q);
      });
      BEECH_SAPLING = BLOCKS.register("beech_sapling", () -> {
         return new LOTRSaplingBlock(new BeechTree());
      });
      POTTED_BEECH_SAPLING = BLOCKS.register("potted_beech_sapling", () -> {
         return new LOTRPottedPlantBlock(BEECH_SAPLING);
      });
      BEECH_SLAB = BLOCKS.register("beech_slab", () -> {
         return new AxialSlabBlock(BEECH_PLANKS);
      });
      BEECH_STAIRS = BLOCKS.register("beech_stairs", () -> {
         return new LOTRStairsBlock(BEECH_PLANKS);
      });
      BEECH_FENCE = BLOCKS.register("beech_fence", () -> {
         return new LOTRFenceBlock(BEECH_PLANKS);
      });
      BEECH_FENCE_GATE = BLOCKS.register("beech_fence_gate", () -> {
         return new LOTRFenceGateBlock(BEECH_PLANKS);
      });
      BEECH_DOOR = BLOCKS.register("beech_door", () -> {
         return new LOTRDoorBlock(BEECH_PLANKS);
      });
      BEECH_TRAPDOOR = BLOCKS.register("beech_trapdoor", () -> {
         return new LOTRTrapdoorBlock(BEECH_PLANKS);
      });
      BEECH_PRESSURE_PLATE = BLOCKS.register("beech_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(BEECH_PLANKS);
      });
      BEECH_BUTTON = BLOCKS.register("beech_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_BEECH_LOG = BLOCKS.register("stripped_beech_log", () -> {
         return new LOTRStrippedLogBlock(BEECH_LOG);
      });
      STRIPPED_BEECH_WOOD = BLOCKS.register("stripped_beech_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_BEECH_LOG);
      });
      BEECH_BEAM = BLOCKS.register("beech_beam", () -> {
         return new WoodBeamBlock(BEECH_LOG);
      });
      BEECH_SIGN = BLOCKS.register("beech_sign", () -> {
         return new LOTRStandingSignBlock(BEECH_PLANKS, LOTRSignTypes.BEECH);
      });
      BEECH_WALL_SIGN = BLOCKS.register("beech_wall_sign", () -> {
         return new LOTRWallSignBlock(BEECH_SIGN);
      });
      CHERRY_PIE = BLOCKS.register("cherry_pie", () -> {
         return new CakelikeBlock();
      });
      HEARTH_BLOCK = BLOCKS.register("hearth_block", () -> {
         return new HearthBlock();
      });
      MALLORN_LADDER = BLOCKS.register("mallorn_ladder", () -> {
         return new LOTRLadderBlock();
      });
      ELANOR = BLOCKS.register("elanor", () -> {
         return new LOTRFlowerBlock(Effects.field_76426_n, 6);
      });
      NIPHREDIL = BLOCKS.register("niphredil", () -> {
         return new LOTRFlowerBlock(Effects.field_76428_l, 9);
      });
      POTTED_ELANOR = BLOCKS.register("potted_elanor", () -> {
         return new LOTRPottedPlantBlock(ELANOR);
      });
      POTTED_NIPHREDIL = BLOCKS.register("potted_niphredil", () -> {
         return new LOTRPottedPlantBlock(NIPHREDIL);
      });
      BLUEBELL = BLOCKS.register("bluebell", () -> {
         return new LOTRFlowerBlock(Effects.field_76420_g, 5);
      });
      MARIGOLD = BLOCKS.register("marigold", () -> {
         return new LOTRFlowerBlock(Effects.field_76437_t, 9);
      });
      ASPHODEL = BLOCKS.register("asphodel", () -> {
         return new LOTRFlowerBlock(Effects.field_76440_q, 8);
      });
      LAVENDER = BLOCKS.register("lavender", () -> {
         return (new LOTRFlowerBlock(Effects.field_76443_y, 7)).setWide();
      });
      POTTED_BLUEBELL = BLOCKS.register("potted_bluebell", () -> {
         return new LOTRPottedPlantBlock(BLUEBELL);
      });
      POTTED_MARIGOLD = BLOCKS.register("potted_marigold", () -> {
         return new LOTRPottedPlantBlock(MARIGOLD);
      });
      POTTED_ASPHODEL = BLOCKS.register("potted_asphodel", () -> {
         return new LOTRPottedPlantBlock(ASPHODEL);
      });
      POTTED_LAVENDER = BLOCKS.register("potted_lavender", () -> {
         return new LOTRPottedPlantBlock(LAVENDER);
      });
      MORDOR_GRAVEL = BLOCKS.register("mordor_gravel", () -> {
         return new MordorGravelBlock(MaterialColor.field_151670_w, 4737096);
      });
      MORDOR_DIRT = BLOCKS.register("mordor_dirt", () -> {
         return new MordorDirtBlock(MaterialColor.field_193573_Y);
      });
      MORDOR_MOSS = BLOCKS.register("mordor_moss", () -> {
         return new MordorMossBlock();
      });
      MORDOR_GRASS = BLOCKS.register("mordor_grass", () -> {
         return new MordorGrassBlock();
      });
      MORDOR_THORN = BLOCKS.register("mordor_thorn", () -> {
         return new MordorThornBlock();
      });
      POTTED_MORDOR_THORN = BLOCKS.register("potted_mordor_thorn", () -> {
         return new LOTRPottedPlantBlock(MORDOR_THORN);
      });
      DWARVEN_PILLAR = BLOCKS.register("dwarven_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151665_m);
      });
      DWARVEN_PILLAR_SLAB = BLOCKS.register("dwarven_pillar_slab", () -> {
         return new AxialSlabBlock(DWARVEN_PILLAR);
      });
      STONE_PILLAR = BLOCKS.register("stone_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151665_m);
      });
      STONE_PILLAR_SLAB = BLOCKS.register("stone_pillar_slab", () -> {
         return new AxialSlabBlock(STONE_PILLAR);
      });
      GONDOR_PILLAR = BLOCKS.register("gondor_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151659_e);
      });
      GONDOR_PILLAR_SLAB = BLOCKS.register("gondor_pillar_slab", () -> {
         return new AxialSlabBlock(GONDOR_PILLAR);
      });
      GLOWSTONE_ORE = BLOCKS.register("glowstone_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F).func_235838_a_(constantLight(12)))).setOreLevel(1);
      });
      GLOWING_DWARVEN_BRICK = BLOCKS.register("glowing_dwarven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151658_d, 12);
      });
      MORDOR_PILLAR = BLOCKS.register("mordor_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151646_E);
      });
      MORDOR_PILLAR_SLAB = BLOCKS.register("mordor_pillar_slab", () -> {
         return new AxialSlabBlock(MORDOR_PILLAR);
      });
      ROHAN_PILLAR = BLOCKS.register("rohan_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151664_l);
      });
      ROHAN_PILLAR_SLAB = BLOCKS.register("rohan_pillar_slab", () -> {
         return new AxialSlabBlock(ROHAN_PILLAR);
      });
      BLUE_PILLAR = BLOCKS.register("blue_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151674_s);
      });
      BLUE_PILLAR_SLAB = BLOCKS.register("blue_pillar_slab", () -> {
         return new AxialSlabBlock(BLUE_PILLAR);
      });
      RED_PILLAR = BLOCKS.register("red_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_193562_N);
      });
      RED_PILLAR_SLAB = BLOCKS.register("red_pillar_slab", () -> {
         return new AxialSlabBlock(RED_PILLAR);
      });
      HIGH_ELVEN_PILLAR = BLOCKS.register("high_elven_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151668_h);
      });
      HIGH_ELVEN_PILLAR_SLAB = BLOCKS.register("high_elven_pillar_slab", () -> {
         return new AxialSlabBlock(HIGH_ELVEN_PILLAR);
      });
      GALADHRIM_PILLAR = BLOCKS.register("galadhrim_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151668_h);
      });
      GALADHRIM_PILLAR_SLAB = BLOCKS.register("galadhrim_pillar_slab", () -> {
         return new AxialSlabBlock(GALADHRIM_PILLAR);
      });
      WOOD_ELVEN_PILLAR = BLOCKS.register("wood_elven_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_193561_M);
      });
      WOOD_ELVEN_PILLAR_SLAB = BLOCKS.register("wood_elven_pillar_slab", () -> {
         return new AxialSlabBlock(WOOD_ELVEN_PILLAR);
      });
      HARAD_PILLAR = BLOCKS.register("harad_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151658_d);
      });
      HARAD_PILLAR_SLAB = BLOCKS.register("harad_pillar_slab", () -> {
         return new AxialSlabBlock(HARAD_PILLAR);
      });
      RED_HARAD_PILLAR = BLOCKS.register("red_harad_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_193562_N);
      });
      RED_HARAD_PILLAR_SLAB = BLOCKS.register("red_harad_pillar_slab", () -> {
         return new AxialSlabBlock(RED_HARAD_PILLAR);
      });
      UMBAR_BRICK = BLOCKS.register("umbar_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151658_d);
      });
      UMBAR_BRICK_SLAB = BLOCKS.register("umbar_brick_slab", () -> {
         return new AxialSlabBlock(UMBAR_BRICK);
      });
      UMBAR_BRICK_STAIRS = BLOCKS.register("umbar_brick_stairs", () -> {
         return new LOTRStairsBlock(UMBAR_BRICK);
      });
      UMBAR_BRICK_WALL = BLOCKS.register("umbar_brick_wall", () -> {
         return new LOTRWallBlock(UMBAR_BRICK);
      });
      UMBAR_CRAFTING_TABLE = BLOCKS.register("umbar_crafting_table", () -> {
         return new FactionCraftingTableBlock.Umbar(UMBAR_BRICK);
      });
      UMBAR_PILLAR = BLOCKS.register("umbar_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151658_d);
      });
      UMBAR_PILLAR_SLAB = BLOCKS.register("umbar_pillar_slab", () -> {
         return new AxialSlabBlock(UMBAR_PILLAR);
      });
      DRIPSTONE = BLOCKS.register("dripstone", () -> {
         return new DripstoneBlock(Blocks.field_150348_b);
      });
      MORDOR_DRIPSTONE = BLOCKS.register("mordor_dripstone", () -> {
         return new DripstoneBlock(MORDOR_ROCK, ParticleTypes.field_197617_j);
      });
      OBSIDIAN_DRIPSTONE = BLOCKS.register("obsidian_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_150343_Z, ParticleTypes.field_197617_j);
      });
      ICE_DRIPSTONE = BLOCKS.register("ice_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_150403_cj, false);
      });
      GONDOR_DRIPSTONE = BLOCKS.register("gondor_dripstone", () -> {
         return new DripstoneBlock(GONDOR_ROCK);
      });
      ROHAN_DRIPSTONE = BLOCKS.register("rohan_dripstone", () -> {
         return new DripstoneBlock(ROHAN_ROCK);
      });
      BLUE_DRIPSTONE = BLOCKS.register("blue_dripstone", () -> {
         return new DripstoneBlock(BLUE_ROCK);
      });
      RED_DRIPSTONE = BLOCKS.register("red_dripstone", () -> {
         return new DripstoneBlock(RED_ROCK);
      });
      ANDESITE_DRIPSTONE = BLOCKS.register("andesite_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_196656_g);
      });
      DIORITE_DRIPSTONE = BLOCKS.register("diorite_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_196654_e);
      });
      GRANITE_DRIPSTONE = BLOCKS.register("granite_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_196650_c);
      });
      OAK_BEAM = BLOCKS.register("oak_beam", () -> {
         return new WoodBeamBlock(MaterialColor.field_151663_o, MaterialColor.field_151654_J);
      });
      SPRUCE_BEAM = BLOCKS.register("spruce_beam", () -> {
         return new WoodBeamBlock(MaterialColor.field_151654_J, MaterialColor.field_151650_B);
      });
      BIRCH_BEAM = BLOCKS.register("birch_beam", () -> {
         return new WoodBeamBlock(MaterialColor.field_151658_d, MaterialColor.field_151677_p);
      });
      JUNGLE_BEAM = BLOCKS.register("jungle_beam", () -> {
         return new WoodBeamBlock(MaterialColor.field_151664_l, MaterialColor.field_151654_J);
      });
      ACACIA_BEAM = BLOCKS.register("acacia_beam", () -> {
         return new WoodBeamBlock(MaterialColor.field_151676_q, MaterialColor.field_151665_m);
      });
      DARK_OAK_BEAM = BLOCKS.register("dark_oak_beam", () -> {
         return new WoodBeamBlock(MaterialColor.field_151650_B, MaterialColor.field_151650_B);
      });
      ORC_TORCH = BLOCKS.register("orc_torch", () -> {
         return new DoubleTorchBlock(14);
      });
      ORC_BARS = BLOCKS.register("orc_bars", () -> {
         return new LOTRBarsBlock();
      });
      DWARVEN_BARS = BLOCKS.register("dwarven_bars", () -> {
         return new LOTRBarsBlock();
      });
      BRONZE_BARS = BLOCKS.register("bronze_bars", () -> {
         return new LOTRBarsBlock();
      });
      SILVER_BARS = BLOCKS.register("silver_bars", () -> {
         return new LOTRBarsBlock();
      });
      GOLD_BARS = BLOCKS.register("gold_bars", () -> {
         return new LOTRBarsBlock();
      });
      MITHRIL_BARS = BLOCKS.register("mithril_bars", () -> {
         return new LOTRBarsBlock();
      });
      HIGH_ELVEN_BARS = BLOCKS.register("high_elven_bars", () -> {
         return new LOTRBarsBlock();
      });
      GALADHRIM_BARS = BLOCKS.register("galadhrim_bars", () -> {
         return new LOTRBarsBlock();
      });
      WOOD_ELVEN_BARS = BLOCKS.register("wood_elven_bars", () -> {
         return new LOTRBarsBlock();
      });
      HIGH_ELVEN_WOOD_BARS = BLOCKS.register("high_elven_wood_bars", () -> {
         return new LOTRWoodBarsBlock();
      });
      GALADHRIM_WOOD_BARS = BLOCKS.register("galadhrim_wood_bars", () -> {
         return new LOTRWoodBarsBlock();
      });
      WOOD_ELVEN_WOOD_BARS = BLOCKS.register("wood_elven_wood_bars", () -> {
         return new LOTRWoodBarsBlock();
      });
      CRACKED_STONE_BRICK_SLAB = BLOCKS.register("cracked_stone_brick_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196700_dk);
      });
      CRACKED_STONE_BRICK_STAIRS = BLOCKS.register("cracked_stone_brick_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196700_dk);
      });
      CRACKED_STONE_BRICK_WALL = BLOCKS.register("cracked_stone_brick_wall", () -> {
         return new LOTRWallBlock(Blocks.field_196700_dk);
      });
      CLOVER = BLOCKS.register("clover", () -> {
         return new ThreeLeafCloverBlock();
      });
      FOUR_LEAF_CLOVER = BLOCKS.register("four_leaf_clover", () -> {
         return new FourLeafCloverBlock();
      });
      POTTED_CLOVER = BLOCKS.register("potted_clover", () -> {
         return new LOTRPottedPlantBlock(CLOVER);
      });
      POTTED_FOUR_LEAF_CLOVER = BLOCKS.register("potted_four_leaf_clover", () -> {
         return new LOTRPottedPlantBlock(FOUR_LEAF_CLOVER);
      });
      SHORT_GRASS = BLOCKS.register("short_grass", () -> {
         return new LOTRGrassBlock();
      });
      WHEATGRASS = BLOCKS.register("wheatgrass", () -> {
         return new DoubleableLOTRGrassBlock(() -> {
            return (Block)TALL_WHEATGRASS.get();
         });
      });
      FLOWERY_GRASS = BLOCKS.register("flowery_grass", () -> {
         return new LOTRGrassBlock();
      });
      MAPLE_LOG = BLOCKS.register("maple_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151663_o, MaterialColor.field_151654_J, () -> {
            return (Block)STRIPPED_MAPLE_LOG.get();
         });
      });
      MAPLE_WOOD = BLOCKS.register("maple_wood", () -> {
         return new StrippableWoodBlock(MAPLE_LOG, () -> {
            return (Block)STRIPPED_MAPLE_WOOD.get();
         });
      });
      MAPLE_PLANKS = BLOCKS.register("maple_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151663_o);
      });
      MAPLE_LEAVES = BLOCKS.register("maple_leaves", () -> {
         return new LOTRLeavesBlock(MaterialColor.field_193559_aa);
      });
      MAPLE_SAPLING = BLOCKS.register("maple_sapling", () -> {
         return new LOTRSaplingBlock(new MapleTree());
      });
      POTTED_MAPLE_SAPLING = BLOCKS.register("potted_maple_sapling", () -> {
         return new LOTRPottedPlantBlock(MAPLE_SAPLING);
      });
      MAPLE_SLAB = BLOCKS.register("maple_slab", () -> {
         return new AxialSlabBlock(MAPLE_PLANKS);
      });
      MAPLE_STAIRS = BLOCKS.register("maple_stairs", () -> {
         return new LOTRStairsBlock(MAPLE_PLANKS);
      });
      MAPLE_FENCE = BLOCKS.register("maple_fence", () -> {
         return new LOTRFenceBlock(MAPLE_PLANKS);
      });
      MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", () -> {
         return new LOTRFenceGateBlock(MAPLE_PLANKS);
      });
      MAPLE_DOOR = BLOCKS.register("maple_door", () -> {
         return new LOTRDoorBlock(MAPLE_PLANKS);
      });
      MAPLE_TRAPDOOR = BLOCKS.register("maple_trapdoor", () -> {
         return new LOTRTrapdoorBlock(MAPLE_PLANKS);
      });
      MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(MAPLE_PLANKS);
      });
      MAPLE_BUTTON = BLOCKS.register("maple_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", () -> {
         return new LOTRStrippedLogBlock(MAPLE_LOG);
      });
      STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_MAPLE_LOG);
      });
      MAPLE_BEAM = BLOCKS.register("maple_beam", () -> {
         return new WoodBeamBlock(MAPLE_LOG);
      });
      MAPLE_SIGN = BLOCKS.register("maple_sign", () -> {
         return new LOTRStandingSignBlock(MAPLE_PLANKS, LOTRSignTypes.MAPLE);
      });
      MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", () -> {
         return new LOTRWallSignBlock(MAPLE_SIGN);
      });
      THISTLE = BLOCKS.register("thistle", () -> {
         return new ThistleBlock(Effects.field_76436_u, 4);
      });
      NETTLES = BLOCKS.register("nettles", () -> {
         return new NettleBlock();
      });
      FERNSPROUT = BLOCKS.register("fernsprout", () -> {
         return new LOTRGrassBlock();
      });
      CANDLE = BLOCKS.register("candle", () -> {
         return new CandleBlock(8, 2);
      });
      GOLD_CHANDELIER = BLOCKS.register("gold_chandelier", () -> {
         return new ChandelierBlock();
      });
      IRON_CHANDELIER = BLOCKS.register("iron_chandelier", () -> {
         return new ChandelierBlock();
      });
      BRONZE_CHANDELIER = BLOCKS.register("bronze_chandelier", () -> {
         return new ChandelierBlock();
      });
      SILVER_CHANDELIER = BLOCKS.register("silver_chandelier", () -> {
         return new ChandelierBlock();
      });
      MITHRIL_CHANDELIER = BLOCKS.register("mithril_chandelier", () -> {
         return new ChandelierBlock();
      });
      ATHELAS = BLOCKS.register("athelas", () -> {
         return (new FlowerLikeBlock()).setPlantShape(Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D));
      });
      DWARFWORT = BLOCKS.register("dwarfwort", () -> {
         return new FlowerLikeBlock();
      });
      WILD_PIPEWEED = BLOCKS.register("wild_pipeweed", () -> {
         return (new WildPipeweedBlock()).setPlantShape(Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D));
      });
      WILD_FLAX = BLOCKS.register("wild_flax", () -> {
         return (new FlowerLikeBlock()).setPlantShape(Block.func_208617_a(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D));
      });
      POTTED_ATHELAS = BLOCKS.register("potted_athelas", () -> {
         return new LOTRPottedPlantBlock(ATHELAS);
      });
      POTTED_DWARFWORT = BLOCKS.register("potted_dwarfwort", () -> {
         return new LOTRPottedPlantBlock(DWARFWORT);
      });
      POTTED_WILD_PIPEWEED = BLOCKS.register("potted_wild_pipeweed", () -> {
         return new LOTRPottedPlantBlock(WILD_PIPEWEED);
      });
      POTTED_WILD_FLAX = BLOCKS.register("potted_wild_flax", () -> {
         return new LOTRPottedPlantBlock(WILD_FLAX);
      });
      POTTED_THISTLE = BLOCKS.register("potted_thistle", () -> {
         return new LOTRPottedPlantBlock(THISTLE);
      });
      POTTED_NETTLES = BLOCKS.register("potted_nettles", () -> {
         return new LOTRPottedPlantBlock(NETTLES);
      });
      POTTED_FERNSPROUT = BLOCKS.register("potted_fernsprout", () -> {
         return new LOTRPottedPlantBlock(FERNSPROUT);
      });
      ASPEN_LOG = BLOCKS.register("aspen_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151654_J, MaterialColor.field_151668_h, () -> {
            return (Block)STRIPPED_ASPEN_LOG.get();
         });
      });
      ASPEN_WOOD = BLOCKS.register("aspen_wood", () -> {
         return new StrippableWoodBlock(ASPEN_LOG, () -> {
            return (Block)STRIPPED_ASPEN_WOOD.get();
         });
      });
      ASPEN_PLANKS = BLOCKS.register("aspen_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151654_J);
      });
      ASPEN_LEAVES = BLOCKS.register("aspen_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      ASPEN_SAPLING = BLOCKS.register("aspen_sapling", () -> {
         return new LOTRSaplingBlock(new AspenTree());
      });
      POTTED_ASPEN_SAPLING = BLOCKS.register("potted_aspen_sapling", () -> {
         return new LOTRPottedPlantBlock(ASPEN_SAPLING);
      });
      ASPEN_SLAB = BLOCKS.register("aspen_slab", () -> {
         return new AxialSlabBlock(ASPEN_PLANKS);
      });
      ASPEN_STAIRS = BLOCKS.register("aspen_stairs", () -> {
         return new LOTRStairsBlock(ASPEN_PLANKS);
      });
      ASPEN_FENCE = BLOCKS.register("aspen_fence", () -> {
         return new LOTRFenceBlock(ASPEN_PLANKS);
      });
      ASPEN_FENCE_GATE = BLOCKS.register("aspen_fence_gate", () -> {
         return new LOTRFenceGateBlock(ASPEN_PLANKS);
      });
      ASPEN_DOOR = BLOCKS.register("aspen_door", () -> {
         return new LOTRDoorBlock(ASPEN_PLANKS);
      });
      ASPEN_TRAPDOOR = BLOCKS.register("aspen_trapdoor", () -> {
         return new LOTRTrapdoorBlock(ASPEN_PLANKS);
      });
      ASPEN_PRESSURE_PLATE = BLOCKS.register("aspen_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(ASPEN_PLANKS);
      });
      ASPEN_BUTTON = BLOCKS.register("aspen_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_ASPEN_LOG = BLOCKS.register("stripped_aspen_log", () -> {
         return new LOTRStrippedLogBlock(ASPEN_LOG);
      });
      STRIPPED_ASPEN_WOOD = BLOCKS.register("stripped_aspen_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_ASPEN_LOG);
      });
      ASPEN_BEAM = BLOCKS.register("aspen_beam", () -> {
         return new WoodBeamBlock(ASPEN_LOG);
      });
      ASPEN_SIGN = BLOCKS.register("aspen_sign", () -> {
         return new LOTRStandingSignBlock(ASPEN_PLANKS, LOTRSignTypes.ASPEN);
      });
      ASPEN_WALL_SIGN = BLOCKS.register("aspen_wall_sign", () -> {
         return new LOTRWallSignBlock(ASPEN_SIGN);
      });
      LAIRELOSSE_LOG = BLOCKS.register("lairelosse_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151666_j, MaterialColor.field_151659_e, () -> {
            return (Block)STRIPPED_LAIRELOSSE_LOG.get();
         });
      });
      LAIRELOSSE_WOOD = BLOCKS.register("lairelosse_wood", () -> {
         return new StrippableWoodBlock(LAIRELOSSE_LOG, () -> {
            return (Block)STRIPPED_LAIRELOSSE_WOOD.get();
         });
      });
      LAIRELOSSE_PLANKS = BLOCKS.register("lairelosse_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151666_j);
      });
      LAIRELOSSE_LEAVES = BLOCKS.register("lairelosse_leaves", () -> {
         return new LOTRLeavesBlock(MaterialColor.field_151666_j);
      });
      LAIRELOSSE_SAPLING = BLOCKS.register("lairelosse_sapling", () -> {
         return new LOTRSaplingBlock(new LairelosseTree());
      });
      POTTED_LAIRELOSSE_SAPLING = BLOCKS.register("potted_lairelosse_sapling", () -> {
         return new LOTRPottedPlantBlock(LAIRELOSSE_SAPLING);
      });
      LAIRELOSSE_SLAB = BLOCKS.register("lairelosse_slab", () -> {
         return new AxialSlabBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_STAIRS = BLOCKS.register("lairelosse_stairs", () -> {
         return new LOTRStairsBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_FENCE = BLOCKS.register("lairelosse_fence", () -> {
         return new LOTRFenceBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_FENCE_GATE = BLOCKS.register("lairelosse_fence_gate", () -> {
         return new LOTRFenceGateBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_DOOR = BLOCKS.register("lairelosse_door", () -> {
         return new LOTRDoorBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_TRAPDOOR = BLOCKS.register("lairelosse_trapdoor", () -> {
         return new LOTRTrapdoorBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_PRESSURE_PLATE = BLOCKS.register("lairelosse_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(LAIRELOSSE_PLANKS);
      });
      LAIRELOSSE_BUTTON = BLOCKS.register("lairelosse_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_LAIRELOSSE_LOG = BLOCKS.register("stripped_lairelosse_log", () -> {
         return new LOTRStrippedLogBlock(LAIRELOSSE_LOG);
      });
      STRIPPED_LAIRELOSSE_WOOD = BLOCKS.register("stripped_lairelosse_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_LAIRELOSSE_LOG);
      });
      LAIRELOSSE_BEAM = BLOCKS.register("lairelosse_beam", () -> {
         return new WoodBeamBlock(LAIRELOSSE_LOG);
      });
      LAIRELOSSE_SIGN = BLOCKS.register("lairelosse_sign", () -> {
         return new LOTRStandingSignBlock(LAIRELOSSE_PLANKS, LOTRSignTypes.LAIRELOSSE);
      });
      LAIRELOSSE_WALL_SIGN = BLOCKS.register("lairelosse_wall_sign", () -> {
         return new LOTRWallSignBlock(LAIRELOSSE_SIGN);
      });
      CEDAR_LOG = BLOCKS.register("cedar_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151654_J, MaterialColor.field_151650_B, () -> {
            return (Block)STRIPPED_CEDAR_LOG.get();
         });
      });
      CEDAR_WOOD = BLOCKS.register("cedar_wood", () -> {
         return new StrippableWoodBlock(CEDAR_LOG, () -> {
            return (Block)STRIPPED_CEDAR_WOOD.get();
         });
      });
      CEDAR_PLANKS = BLOCKS.register("cedar_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151654_J);
      });
      CEDAR_LEAVES = BLOCKS.register("cedar_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      CEDAR_SAPLING = BLOCKS.register("cedar_sapling", () -> {
         return new LOTRSaplingBlock(new CedarTree());
      });
      POTTED_CEDAR_SAPLING = BLOCKS.register("potted_cedar_sapling", () -> {
         return new LOTRPottedPlantBlock(CEDAR_SAPLING);
      });
      CEDAR_SLAB = BLOCKS.register("cedar_slab", () -> {
         return new AxialSlabBlock(CEDAR_PLANKS);
      });
      CEDAR_STAIRS = BLOCKS.register("cedar_stairs", () -> {
         return new LOTRStairsBlock(CEDAR_PLANKS);
      });
      CEDAR_FENCE = BLOCKS.register("cedar_fence", () -> {
         return new LOTRFenceBlock(CEDAR_PLANKS);
      });
      CEDAR_FENCE_GATE = BLOCKS.register("cedar_fence_gate", () -> {
         return new LOTRFenceGateBlock(CEDAR_PLANKS);
      });
      CEDAR_DOOR = BLOCKS.register("cedar_door", () -> {
         return new LOTRDoorBlock(CEDAR_PLANKS);
      });
      CEDAR_TRAPDOOR = BLOCKS.register("cedar_trapdoor", () -> {
         return new LOTRTrapdoorBlock(CEDAR_PLANKS);
      });
      CEDAR_PRESSURE_PLATE = BLOCKS.register("cedar_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(CEDAR_PLANKS);
      });
      CEDAR_BUTTON = BLOCKS.register("cedar_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_CEDAR_LOG = BLOCKS.register("stripped_cedar_log", () -> {
         return new LOTRStrippedLogBlock(CEDAR_LOG);
      });
      STRIPPED_CEDAR_WOOD = BLOCKS.register("stripped_cedar_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_CEDAR_LOG);
      });
      CEDAR_BEAM = BLOCKS.register("cedar_beam", () -> {
         return new WoodBeamBlock(CEDAR_LOG);
      });
      CEDAR_SIGN = BLOCKS.register("cedar_sign", () -> {
         return new LOTRStandingSignBlock(CEDAR_PLANKS, LOTRSignTypes.CEDAR);
      });
      CEDAR_WALL_SIGN = BLOCKS.register("cedar_wall_sign", () -> {
         return new LOTRWallSignBlock(CEDAR_SIGN);
      });
      PIPEWEED_CROP = BLOCKS.register("pipeweed_crop", () -> {
         return new PipeweedCropBlock(() -> {
            return (Item)LOTRItems.PIPEWEED_SEEDS.get();
         });
      });
      FLAX_CROP = BLOCKS.register("flax_crop", () -> {
         return new LOTRCropBlock(() -> {
            return (Item)LOTRItems.FLAX_SEEDS.get();
         });
      });
      LETTUCE = BLOCKS.register("lettuce", () -> {
         return new LOTRCropBlock(() -> {
            return (Item)LOTRItems.LETTUCE.get();
         });
      });
      BLUE_MALLORN_TORCH = BLOCKS.register("blue_mallorn_torch", () -> {
         return (new LOTRTorchBlock(14)).setParticles(LOTRParticles.BLUE_ELVEN_GLOW);
      });
      BLUE_MALLORN_WALL_TORCH = BLOCKS.register("blue_mallorn_wall_torch", () -> {
         return new LOTRWallTorchBlock(BLUE_MALLORN_TORCH);
      });
      GREEN_MALLORN_TORCH = BLOCKS.register("green_mallorn_torch", () -> {
         return (new LOTRTorchBlock(14)).setParticles(LOTRParticles.GREEN_ELVEN_GLOW);
      });
      GREEN_MALLORN_WALL_TORCH = BLOCKS.register("green_mallorn_wall_torch", () -> {
         return new LOTRWallTorchBlock(GREEN_MALLORN_TORCH);
      });
      GOLD_MALLORN_TORCH = BLOCKS.register("gold_mallorn_torch", () -> {
         return (new LOTRTorchBlock(14)).setParticles(LOTRParticles.GOLD_ELVEN_GLOW);
      });
      GOLD_MALLORN_WALL_TORCH = BLOCKS.register("gold_mallorn_wall_torch", () -> {
         return new LOTRWallTorchBlock(GOLD_MALLORN_TORCH);
      });
      SILVER_MALLORN_TORCH = BLOCKS.register("silver_mallorn_torch", () -> {
         return (new LOTRTorchBlock(14)).setParticles(LOTRParticles.SILVER_ELVEN_GLOW);
      });
      SILVER_MALLORN_WALL_TORCH = BLOCKS.register("silver_mallorn_wall_torch", () -> {
         return new LOTRWallTorchBlock(SILVER_MALLORN_TORCH);
      });
      SANDSTONE_DRIPSTONE = BLOCKS.register("sandstone_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_150322_A);
      });
      RED_SANDSTONE_DRIPSTONE = BLOCKS.register("red_sandstone_dripstone", () -> {
         return new DripstoneBlock(Blocks.field_180395_cM);
      });
      EDHELVIR_ORE = BLOCKS.register("edhelvir_ore", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F).func_235838_a_(constantLight(12)))).setOreLevel(2);
      });
      EDHELVIR_BLOCK = BLOCKS.register("edhelvir_block", () -> {
         return new TranslucentMineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151674_s).func_226896_b_().func_200948_a(5.0F, 6.0F).func_235838_a_(constantLight(12)).func_200947_a(SoundType.field_185852_e), 2, DyeColor.LIGHT_BLUE);
      });
      HIGH_ELVEN_TORCH = BLOCKS.register("high_elven_torch", () -> {
         return (new LOTRTorchBlock(14)).setParticles(LOTRParticles.BLUE_ELVEN_GLOW);
      });
      HIGH_ELVEN_WALL_TORCH = BLOCKS.register("high_elven_wall_torch", () -> {
         return new LOTRWallTorchBlock(HIGH_ELVEN_TORCH);
      });
      STONE_WALL = BLOCKS.register("stone_wall", () -> {
         return new LOTRWallBlock(Blocks.field_150348_b);
      });
      RED_SAND_GEM = BLOCKS.register("red_sand_gem", () -> {
         return new LOTRFlowerBlock(Effects.field_76426_n, 4);
      });
      YELLOW_SAND_GEM = BLOCKS.register("yellow_sand_gem", () -> {
         return new LOTRFlowerBlock(Effects.field_76443_y, 7);
      });
      HARAD_DAISY = BLOCKS.register("harad_daisy", () -> {
         return new LOTRFlowerBlock(Effects.field_76428_l, 8);
      });
      SOUTHBELL = BLOCKS.register("southbell", () -> {
         return new LOTRFlowerBlock(Effects.field_76437_t, 9);
      });
      POTTED_RED_SAND_GEM = BLOCKS.register("potted_red_sand_gem", () -> {
         return new LOTRPottedPlantBlock(RED_SAND_GEM);
      });
      POTTED_YELLOW_SAND_GEM = BLOCKS.register("potted_yellow_sand_gem", () -> {
         return new LOTRPottedPlantBlock(YELLOW_SAND_GEM);
      });
      POTTED_HARAD_DAISY = BLOCKS.register("potted_harad_daisy", () -> {
         return new LOTRPottedPlantBlock(HARAD_DAISY);
      });
      POTTED_SOUTHBELL = BLOCKS.register("potted_southbell", () -> {
         return new LOTRPottedPlantBlock(SOUTHBELL);
      });
      HIBISCUS = BLOCKS.register("hibiscus", () -> {
         return new LOTRTallFlowerBlock();
      });
      FLAME_OF_HARAD = BLOCKS.register("flame_of_harad", () -> {
         return new LOTRTallFlowerBlock();
      });
      GULDURIL_ORE_MORDOR = BLOCKS.register("gulduril_ore_mordor", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F).func_235838_a_(constantLight(12)))).setOreLevel(2);
      });
      GULDURIL_ORE_STONE = BLOCKS.register("gulduril_ore_stone", () -> {
         return (new LOTROreBlock(Properties.func_200945_a(Material.field_151576_e).func_200948_a(3.0F, 3.0F).func_235838_a_(constantLight(12)))).setOreLevel(2);
      });
      GULDURIL_BLOCK = BLOCKS.register("gulduril_block", () -> {
         return new MineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_151653_I).func_200948_a(5.0F, 6.0F).func_235838_a_(constantLight(12)).func_200947_a(SoundType.field_185852_e), 2);
      });
      EDHELVIR_CRYSTAL = BLOCKS.register("edhelvir_crystal", () -> {
         return new CrystalBlock(12, 2, DyeColor.LIGHT_BLUE);
      });
      GULDURIL_CRYSTAL = BLOCKS.register("gulduril_crystal", () -> {
         return new CrystalBlock(12, 2, DyeColor.LIME);
      });
      GLOWSTONE_CRYSTAL = BLOCKS.register("glowstone_crystal", () -> {
         return new CrystalBlock(12, 1, DyeColor.YELLOW);
      });
      NUMENOREAN_BRICK = BLOCKS.register("numenorean_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151646_E);
      });
      NUMENOREAN_BRICK_SLAB = BLOCKS.register("numenorean_brick_slab", () -> {
         return new AxialSlabBlock(NUMENOREAN_BRICK);
      });
      NUMENOREAN_BRICK_STAIRS = BLOCKS.register("numenorean_brick_stairs", () -> {
         return new LOTRStairsBlock(NUMENOREAN_BRICK);
      });
      NUMENOREAN_BRICK_WALL = BLOCKS.register("numenorean_brick_wall", () -> {
         return new LOTRWallBlock(NUMENOREAN_BRICK);
      });
      NUMENOREAN_PILLAR = BLOCKS.register("numenorean_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151646_E);
      });
      NUMENOREAN_PILLAR_SLAB = BLOCKS.register("numenorean_pillar_slab", () -> {
         return new AxialSlabBlock(NUMENOREAN_PILLAR);
      });
      ALLOY_FORGE = BLOCKS.register("alloy_forge", () -> {
         return new AlloyForgeBlock(MaterialColor.field_151665_m);
      });
      DWARVEN_FORGE = BLOCKS.register("dwarven_forge", () -> {
         return new DwarvenForgeBlock(MaterialColor.field_151665_m);
      });
      ELVEN_FORGE = BLOCKS.register("elven_forge", () -> {
         return new ElvenForgeBlock(MaterialColor.field_151668_h);
      });
      ORC_FORGE = BLOCKS.register("orc_forge", () -> {
         return new OrcForgeBlock(MaterialColor.field_151646_E);
      });
      URUK_BRICK = BLOCKS.register("uruk_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193560_ab);
      });
      URUK_BRICK_SLAB = BLOCKS.register("uruk_brick_slab", () -> {
         return new AxialSlabBlock(URUK_BRICK);
      });
      URUK_BRICK_STAIRS = BLOCKS.register("uruk_brick_stairs", () -> {
         return new LOTRStairsBlock(URUK_BRICK);
      });
      URUK_BRICK_WALL = BLOCKS.register("uruk_brick_wall", () -> {
         return new LOTRWallBlock(URUK_BRICK);
      });
      URUK_PILLAR = BLOCKS.register("uruk_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_193560_ab);
      });
      URUK_PILLAR_SLAB = BLOCKS.register("uruk_pillar_slab", () -> {
         return new AxialSlabBlock(URUK_PILLAR);
      });
      URUK_CRAFTING_TABLE = BLOCKS.register("uruk_crafting_table", () -> {
         return new FactionCraftingTableBlock.Uruk(URUK_BRICK);
      });
      URUK_STEEL_BLOCK = BLOCKS.register("uruk_steel_block", () -> {
         return new DirectionalMineralBlock(Properties.func_200949_a(Material.field_151573_f, MaterialColor.field_193574_Z).func_200948_a(5.0F, 6.0F).func_200947_a(SoundType.field_185852_e), 1);
      });
      URUK_BARS = BLOCKS.register("uruk_bars", () -> {
         return new LOTRBarsBlock();
      });
      HOBBIT_CRAFTING_TABLE = BLOCKS.register("hobbit_crafting_table", () -> {
         return new FactionCraftingTableBlock.Hobbit(() -> {
            return Blocks.field_196662_n;
         });
      });
      BRICK_PILLAR = BLOCKS.register("brick_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151645_D, 2.0F, 6.0F);
      });
      BRICK_PILLAR_SLAB = BLOCKS.register("brick_pillar_slab", () -> {
         return new AxialSlabBlock(BRICK_PILLAR);
      });
      SHIRE_PINE_DOOR = BLOCKS.register("shire_pine_door", () -> {
         return new LOTRDoorBlock(PINE_PLANKS);
      });
      SHIRE_PINE_TRAPDOOR = BLOCKS.register("shire_pine_trapdoor", () -> {
         return new LOTRTrapdoorBlock(PINE_PLANKS);
      });
      FIR_LOG = BLOCKS.register("fir_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151663_o, MaterialColor.field_197655_T, () -> {
            return (Block)STRIPPED_FIR_LOG.get();
         });
      });
      FIR_WOOD = BLOCKS.register("fir_wood", () -> {
         return new StrippableWoodBlock(FIR_LOG, () -> {
            return (Block)STRIPPED_FIR_WOOD.get();
         });
      });
      FIR_PLANKS = BLOCKS.register("fir_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151663_o);
      });
      FIR_LEAVES = BLOCKS.register("fir_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      FIR_SAPLING = BLOCKS.register("fir_sapling", () -> {
         return new LOTRSaplingBlock(new FirTree());
      });
      POTTED_FIR_SAPLING = BLOCKS.register("potted_fir_sapling", () -> {
         return new LOTRPottedPlantBlock(FIR_SAPLING);
      });
      FIR_SLAB = BLOCKS.register("fir_slab", () -> {
         return new AxialSlabBlock(FIR_PLANKS);
      });
      FIR_STAIRS = BLOCKS.register("fir_stairs", () -> {
         return new LOTRStairsBlock(FIR_PLANKS);
      });
      FIR_FENCE = BLOCKS.register("fir_fence", () -> {
         return new LOTRFenceBlock(FIR_PLANKS);
      });
      FIR_FENCE_GATE = BLOCKS.register("fir_fence_gate", () -> {
         return new LOTRFenceGateBlock(FIR_PLANKS);
      });
      FIR_DOOR = BLOCKS.register("fir_door", () -> {
         return new LOTRDoorBlock(FIR_PLANKS);
      });
      FIR_TRAPDOOR = BLOCKS.register("fir_trapdoor", () -> {
         return new LOTRTrapdoorBlock(FIR_PLANKS);
      });
      FIR_PRESSURE_PLATE = BLOCKS.register("fir_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(FIR_PLANKS);
      });
      FIR_BUTTON = BLOCKS.register("fir_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_FIR_LOG = BLOCKS.register("stripped_fir_log", () -> {
         return new LOTRStrippedLogBlock(FIR_LOG);
      });
      STRIPPED_FIR_WOOD = BLOCKS.register("stripped_fir_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_FIR_LOG);
      });
      FIR_BEAM = BLOCKS.register("fir_beam", () -> {
         return new WoodBeamBlock(FIR_LOG);
      });
      FIR_SIGN = BLOCKS.register("fir_sign", () -> {
         return new LOTRStandingSignBlock(FIR_PLANKS, LOTRSignTypes.FIR);
      });
      FIR_WALL_SIGN = BLOCKS.register("fir_wall_sign", () -> {
         return new LOTRWallSignBlock(FIR_SIGN);
      });
      LARCH_LOG = BLOCKS.register("larch_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151654_J, MaterialColor.field_197655_T, () -> {
            return (Block)STRIPPED_LARCH_LOG.get();
         });
      });
      LARCH_WOOD = BLOCKS.register("larch_wood", () -> {
         return new StrippableWoodBlock(LARCH_LOG, () -> {
            return (Block)STRIPPED_LARCH_WOOD.get();
         });
      });
      LARCH_PLANKS = BLOCKS.register("larch_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151654_J);
      });
      LARCH_LEAVES = BLOCKS.register("larch_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      LARCH_SAPLING = BLOCKS.register("larch_sapling", () -> {
         return new LOTRSaplingBlock(new LarchTree());
      });
      POTTED_LARCH_SAPLING = BLOCKS.register("potted_larch_sapling", () -> {
         return new LOTRPottedPlantBlock(LARCH_SAPLING);
      });
      LARCH_SLAB = BLOCKS.register("larch_slab", () -> {
         return new AxialSlabBlock(LARCH_PLANKS);
      });
      LARCH_STAIRS = BLOCKS.register("larch_stairs", () -> {
         return new LOTRStairsBlock(LARCH_PLANKS);
      });
      LARCH_FENCE = BLOCKS.register("larch_fence", () -> {
         return new LOTRFenceBlock(LARCH_PLANKS);
      });
      LARCH_FENCE_GATE = BLOCKS.register("larch_fence_gate", () -> {
         return new LOTRFenceGateBlock(LARCH_PLANKS);
      });
      LARCH_DOOR = BLOCKS.register("larch_door", () -> {
         return new LOTRDoorBlock(LARCH_PLANKS);
      });
      LARCH_TRAPDOOR = BLOCKS.register("larch_trapdoor", () -> {
         return new LOTRTrapdoorBlock(LARCH_PLANKS);
      });
      LARCH_PRESSURE_PLATE = BLOCKS.register("larch_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(LARCH_PLANKS);
      });
      LARCH_BUTTON = BLOCKS.register("larch_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_LARCH_LOG = BLOCKS.register("stripped_larch_log", () -> {
         return new LOTRStrippedLogBlock(LARCH_LOG);
      });
      STRIPPED_LARCH_WOOD = BLOCKS.register("stripped_larch_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_LARCH_LOG);
      });
      LARCH_BEAM = BLOCKS.register("larch_beam", () -> {
         return new WoodBeamBlock(LARCH_LOG);
      });
      LARCH_SIGN = BLOCKS.register("larch_sign", () -> {
         return new LOTRStandingSignBlock(LARCH_PLANKS, LOTRSignTypes.LARCH);
      });
      LARCH_WALL_SIGN = BLOCKS.register("larch_wall_sign", () -> {
         return new LOTRWallSignBlock(LARCH_SIGN);
      });
      HOBBIT_OVEN = BLOCKS.register("hobbit_oven", () -> {
         return new HobbitOvenBlock(MaterialColor.field_151645_D);
      });
      FINE_PLATE = BLOCKS.register("fine_plate", () -> {
         return new PlateBlock(Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.5F).func_200947_a(SOUND_CERAMIC));
      });
      STONEWARE_PLATE = BLOCKS.register("stoneware_plate", () -> {
         return new PlateBlock(Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.5F).func_200947_a(SOUND_CERAMIC));
      });
      WOODEN_PLATE = BLOCKS.register("wooden_plate", () -> {
         return new PlateBlock(Properties.func_200945_a(Material.field_151575_d).func_200943_b(0.5F).func_200947_a(SoundType.field_185848_a));
      });
      MOSSY_GONDOR_BRICK = BLOCKS.register("mossy_gondor_brick", () -> {
         return new LOTRStoneBlock(GONDOR_BRICK);
      });
      MOSSY_GONDOR_BRICK_SLAB = BLOCKS.register("mossy_gondor_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_GONDOR_BRICK);
      });
      MOSSY_GONDOR_BRICK_STAIRS = BLOCKS.register("mossy_gondor_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_GONDOR_BRICK);
      });
      MOSSY_GONDOR_BRICK_WALL = BLOCKS.register("mossy_gondor_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_GONDOR_BRICK);
      });
      CRACKED_GONDOR_BRICK = BLOCKS.register("cracked_gondor_brick", () -> {
         return new LOTRStoneBlock(GONDOR_BRICK);
      });
      CRACKED_GONDOR_BRICK_SLAB = BLOCKS.register("cracked_gondor_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_GONDOR_BRICK);
      });
      CRACKED_GONDOR_BRICK_STAIRS = BLOCKS.register("cracked_gondor_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_GONDOR_BRICK);
      });
      CRACKED_GONDOR_BRICK_WALL = BLOCKS.register("cracked_gondor_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_GONDOR_BRICK);
      });
      CARVED_GONDOR_BRICK = BLOCKS.register("carved_gondor_brick", () -> {
         return new LOTRStoneBlock(GONDOR_BRICK);
      });
      MOSSY_HIGH_ELVEN_BRICK = BLOCKS.register("mossy_high_elven_brick", () -> {
         return new LOTRStoneBlock(HIGH_ELVEN_BRICK);
      });
      MOSSY_HIGH_ELVEN_BRICK_SLAB = BLOCKS.register("mossy_high_elven_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_HIGH_ELVEN_BRICK);
      });
      MOSSY_HIGH_ELVEN_BRICK_STAIRS = BLOCKS.register("mossy_high_elven_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_HIGH_ELVEN_BRICK);
      });
      MOSSY_HIGH_ELVEN_BRICK_WALL = BLOCKS.register("mossy_high_elven_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_HIGH_ELVEN_BRICK);
      });
      CRACKED_HIGH_ELVEN_BRICK = BLOCKS.register("cracked_high_elven_brick", () -> {
         return new LOTRStoneBlock(HIGH_ELVEN_BRICK);
      });
      CRACKED_HIGH_ELVEN_BRICK_SLAB = BLOCKS.register("cracked_high_elven_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_HIGH_ELVEN_BRICK);
      });
      CRACKED_HIGH_ELVEN_BRICK_STAIRS = BLOCKS.register("cracked_high_elven_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_HIGH_ELVEN_BRICK);
      });
      CRACKED_HIGH_ELVEN_BRICK_WALL = BLOCKS.register("cracked_high_elven_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_HIGH_ELVEN_BRICK);
      });
      CARVED_HIGH_ELVEN_BRICK = BLOCKS.register("carved_high_elven_brick", () -> {
         return new LOTRStoneBlock(HIGH_ELVEN_BRICK);
      });
      THATCH = BLOCKS.register("thatch", () -> {
         return new ThatchBlock();
      });
      THATCH_SLAB = BLOCKS.register("thatch_slab", () -> {
         return new ThatchSlabBlock(THATCH);
      });
      THATCH_STAIRS = BLOCKS.register("thatch_stairs", () -> {
         return new ThatchStairsBlock(THATCH);
      });
      DRYSTONE = BLOCKS.register("drystone", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151665_m);
      });
      WATTLE_AND_DAUB = BLOCKS.register("wattle_and_daub", () -> {
         return new WattleAndDaubBlock();
      });
      WATTLE_AND_DAUB_PILLAR = BLOCKS.register("wattle_and_daub_pillar", () -> {
         return new WattleAndDaubPillarBlock(WATTLE_AND_DAUB);
      });
      MOSSY_MORDOR_BRICK = BLOCKS.register("mossy_mordor_brick", () -> {
         return new LOTRStoneBlock(MORDOR_BRICK);
      });
      MOSSY_MORDOR_BRICK_SLAB = BLOCKS.register("mossy_mordor_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_MORDOR_BRICK);
      });
      MOSSY_MORDOR_BRICK_STAIRS = BLOCKS.register("mossy_mordor_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_MORDOR_BRICK);
      });
      MOSSY_MORDOR_BRICK_WALL = BLOCKS.register("mossy_mordor_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_MORDOR_BRICK);
      });
      CRACKED_MORDOR_BRICK = BLOCKS.register("cracked_mordor_brick", () -> {
         return new LOTRStoneBlock(MORDOR_BRICK);
      });
      CRACKED_MORDOR_BRICK_SLAB = BLOCKS.register("cracked_mordor_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_MORDOR_BRICK);
      });
      CRACKED_MORDOR_BRICK_STAIRS = BLOCKS.register("cracked_mordor_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_MORDOR_BRICK);
      });
      CRACKED_MORDOR_BRICK_WALL = BLOCKS.register("cracked_mordor_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_MORDOR_BRICK);
      });
      CARVED_MORDOR_BRICK = BLOCKS.register("carved_mordor_brick", () -> {
         return new LOTRStoneBlock(MORDOR_BRICK);
      });
      MOSSY_ROHAN_BRICK = BLOCKS.register("mossy_rohan_brick", () -> {
         return new LOTRStoneBlock(ROHAN_BRICK);
      });
      MOSSY_ROHAN_BRICK_SLAB = BLOCKS.register("mossy_rohan_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_ROHAN_BRICK);
      });
      MOSSY_ROHAN_BRICK_STAIRS = BLOCKS.register("mossy_rohan_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_ROHAN_BRICK);
      });
      MOSSY_ROHAN_BRICK_WALL = BLOCKS.register("mossy_rohan_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_ROHAN_BRICK);
      });
      CRACKED_ROHAN_BRICK = BLOCKS.register("cracked_rohan_brick", () -> {
         return new LOTRStoneBlock(ROHAN_BRICK);
      });
      CRACKED_ROHAN_BRICK_SLAB = BLOCKS.register("cracked_rohan_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_ROHAN_BRICK);
      });
      CRACKED_ROHAN_BRICK_STAIRS = BLOCKS.register("cracked_rohan_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_ROHAN_BRICK);
      });
      CRACKED_ROHAN_BRICK_WALL = BLOCKS.register("cracked_rohan_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_ROHAN_BRICK);
      });
      CARVED_ROHAN_BRICK = BLOCKS.register("carved_rohan_brick", () -> {
         return new LOTRStoneBlock(ROHAN_BRICK);
      });
      GOLD_TRIMMED_DWARVEN_BRICK = BLOCKS.register("gold_trimmed_dwarven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151665_m);
      });
      SILVER_TRIMMED_DWARVEN_BRICK = BLOCKS.register("silver_trimmed_dwarven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151665_m);
      });
      MITHRIL_TRIMMED_DWARVEN_BRICK = BLOCKS.register("mithril_trimmed_dwarven_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151665_m);
      });
      MOSSY_NUMENOREAN_BRICK = BLOCKS.register("mossy_numenorean_brick", () -> {
         return new LOTRStoneBlock(NUMENOREAN_BRICK);
      });
      MOSSY_NUMENOREAN_BRICK_SLAB = BLOCKS.register("mossy_numenorean_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_NUMENOREAN_BRICK);
      });
      MOSSY_NUMENOREAN_BRICK_STAIRS = BLOCKS.register("mossy_numenorean_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_NUMENOREAN_BRICK);
      });
      MOSSY_NUMENOREAN_BRICK_WALL = BLOCKS.register("mossy_numenorean_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_NUMENOREAN_BRICK);
      });
      CRACKED_NUMENOREAN_BRICK = BLOCKS.register("cracked_numenorean_brick", () -> {
         return new LOTRStoneBlock(NUMENOREAN_BRICK);
      });
      CRACKED_NUMENOREAN_BRICK_SLAB = BLOCKS.register("cracked_numenorean_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_NUMENOREAN_BRICK);
      });
      CRACKED_NUMENOREAN_BRICK_STAIRS = BLOCKS.register("cracked_numenorean_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_NUMENOREAN_BRICK);
      });
      CRACKED_NUMENOREAN_BRICK_WALL = BLOCKS.register("cracked_numenorean_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_NUMENOREAN_BRICK);
      });
      KEG = BLOCKS.register("keg", () -> {
         return new KegBlock();
      });
      MORGUL_SHROOM = BLOCKS.register("morgul_shroom", () -> {
         return new MorgulShroomBlock();
      });
      POTTED_MORGUL_SHROOM = BLOCKS.register("potted_morgul_shroom", () -> {
         return new LOTRPottedPlantBlock(MORGUL_SHROOM);
      });
      MOSSY_BLUE_BRICK = BLOCKS.register("mossy_blue_brick", () -> {
         return new LOTRStoneBlock(BLUE_BRICK);
      });
      MOSSY_BLUE_BRICK_SLAB = BLOCKS.register("mossy_blue_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_BLUE_BRICK);
      });
      MOSSY_BLUE_BRICK_STAIRS = BLOCKS.register("mossy_blue_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_BLUE_BRICK);
      });
      MOSSY_BLUE_BRICK_WALL = BLOCKS.register("mossy_blue_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_BLUE_BRICK);
      });
      CRACKED_BLUE_BRICK = BLOCKS.register("cracked_blue_brick", () -> {
         return new LOTRStoneBlock(BLUE_BRICK);
      });
      CRACKED_BLUE_BRICK_SLAB = BLOCKS.register("cracked_blue_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_BLUE_BRICK);
      });
      CRACKED_BLUE_BRICK_STAIRS = BLOCKS.register("cracked_blue_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_BLUE_BRICK);
      });
      CRACKED_BLUE_BRICK_WALL = BLOCKS.register("cracked_blue_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_BLUE_BRICK);
      });
      CARVED_BLUE_BRICK = BLOCKS.register("carved_blue_brick", () -> {
         return new LOTRStoneBlock(BLUE_BRICK);
      });
      MOSSY_RED_BRICK = BLOCKS.register("mossy_red_brick", () -> {
         return new LOTRStoneBlock(RED_BRICK);
      });
      MOSSY_RED_BRICK_SLAB = BLOCKS.register("mossy_red_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_RED_BRICK);
      });
      MOSSY_RED_BRICK_STAIRS = BLOCKS.register("mossy_red_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_RED_BRICK);
      });
      MOSSY_RED_BRICK_WALL = BLOCKS.register("mossy_red_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_RED_BRICK);
      });
      CRACKED_RED_BRICK = BLOCKS.register("cracked_red_brick", () -> {
         return new LOTRStoneBlock(RED_BRICK);
      });
      CRACKED_RED_BRICK_SLAB = BLOCKS.register("cracked_red_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_RED_BRICK);
      });
      CRACKED_RED_BRICK_STAIRS = BLOCKS.register("cracked_red_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_RED_BRICK);
      });
      CRACKED_RED_BRICK_WALL = BLOCKS.register("cracked_red_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_RED_BRICK);
      });
      CARVED_RED_BRICK = BLOCKS.register("carved_red_brick", () -> {
         return new LOTRStoneBlock(RED_BRICK);
      });
      SMOOTH_BLUE_ROCK = BLOCKS.register("smooth_blue_rock", () -> {
         return new LOTRStoneBlock(BLUE_ROCK);
      });
      SMOOTH_BLUE_ROCK_SLAB = BLOCKS.register("smooth_blue_rock_slab", () -> {
         return new AxialSlabBlock(SMOOTH_BLUE_ROCK);
      });
      SMOOTH_RED_ROCK = BLOCKS.register("smooth_red_rock", () -> {
         return new LOTRStoneBlock(RED_ROCK);
      });
      SMOOTH_RED_ROCK_SLAB = BLOCKS.register("smooth_red_rock_slab", () -> {
         return new AxialSlabBlock(SMOOTH_RED_ROCK);
      });
      SMOOTH_MORDOR_ROCK = BLOCKS.register("smooth_mordor_rock", () -> {
         return new LOTRStoneBlock(MORDOR_ROCK);
      });
      SMOOTH_MORDOR_ROCK_SLAB = BLOCKS.register("smooth_mordor_rock_slab", () -> {
         return new AxialSlabBlock(SMOOTH_MORDOR_ROCK);
      });
      SMOOTH_GONDOR_ROCK = BLOCKS.register("smooth_gondor_rock", () -> {
         return new LOTRStoneBlock(GONDOR_ROCK);
      });
      SMOOTH_GONDOR_ROCK_SLAB = BLOCKS.register("smooth_gondor_rock_slab", () -> {
         return new AxialSlabBlock(SMOOTH_GONDOR_ROCK);
      });
      SMOOTH_ROHAN_ROCK = BLOCKS.register("smooth_rohan_rock", () -> {
         return new LOTRStoneBlock(ROHAN_ROCK);
      });
      SMOOTH_ROHAN_ROCK_SLAB = BLOCKS.register("smooth_rohan_rock_slab", () -> {
         return new AxialSlabBlock(SMOOTH_ROHAN_ROCK);
      });
      BLUE_MOUNTAINS_CRAFTING_TABLE = BLOCKS.register("blue_mountains_crafting_table", () -> {
         return new FactionCraftingTableBlock.BlueMountains(BLUE_BRICK);
      });
      BLUE_DWARVEN_BARS = BLOCKS.register("blue_dwarven_bars", () -> {
         return new LOTRBarsBlock();
      });
      GOLD_TREASURE_PILE = BLOCKS.register("gold_treasure_pile", () -> {
         return new TreasurePileBlock(MaterialColor.field_151647_F);
      });
      SILVER_TREASURE_PILE = BLOCKS.register("silver_treasure_pile", () -> {
         return new TreasurePileBlock(MaterialColor.field_197656_x);
      });
      COPPER_TREASURE_PILE = BLOCKS.register("copper_treasure_pile", () -> {
         return new TreasurePileBlock(MaterialColor.field_151676_q);
      });
      HOLLY_LOG = BLOCKS.register("holly_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151658_d, MaterialColor.field_151670_w, () -> {
            return (Block)STRIPPED_HOLLY_LOG.get();
         });
      });
      HOLLY_WOOD = BLOCKS.register("holly_wood", () -> {
         return new StrippableWoodBlock(HOLLY_LOG, () -> {
            return (Block)STRIPPED_HOLLY_WOOD.get();
         });
      });
      HOLLY_PLANKS = BLOCKS.register("holly_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151658_d);
      });
      HOLLY_LEAVES = BLOCKS.register("holly_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      HOLLY_SAPLING = BLOCKS.register("holly_sapling", () -> {
         return new LOTRSaplingBlock(new HollyTree());
      });
      POTTED_HOLLY_SAPLING = BLOCKS.register("potted_holly_sapling", () -> {
         return new LOTRPottedPlantBlock(HOLLY_SAPLING);
      });
      HOLLY_SLAB = BLOCKS.register("holly_slab", () -> {
         return new AxialSlabBlock(HOLLY_PLANKS);
      });
      HOLLY_STAIRS = BLOCKS.register("holly_stairs", () -> {
         return new LOTRStairsBlock(HOLLY_PLANKS);
      });
      HOLLY_FENCE = BLOCKS.register("holly_fence", () -> {
         return new LOTRFenceBlock(HOLLY_PLANKS);
      });
      HOLLY_FENCE_GATE = BLOCKS.register("holly_fence_gate", () -> {
         return new LOTRFenceGateBlock(HOLLY_PLANKS);
      });
      HOLLY_DOOR = BLOCKS.register("holly_door", () -> {
         return new LOTRDoorBlock(HOLLY_PLANKS);
      });
      HOLLY_TRAPDOOR = BLOCKS.register("holly_trapdoor", () -> {
         return new LOTRTrapdoorBlock(HOLLY_PLANKS);
      });
      HOLLY_PRESSURE_PLATE = BLOCKS.register("holly_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(HOLLY_PLANKS);
      });
      HOLLY_BUTTON = BLOCKS.register("holly_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_HOLLY_LOG = BLOCKS.register("stripped_holly_log", () -> {
         return new LOTRStrippedLogBlock(HOLLY_LOG);
      });
      STRIPPED_HOLLY_WOOD = BLOCKS.register("stripped_holly_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_HOLLY_LOG);
      });
      HOLLY_BEAM = BLOCKS.register("holly_beam", () -> {
         return new WoodBeamBlock(HOLLY_LOG);
      });
      HOLLY_SIGN = BLOCKS.register("holly_sign", () -> {
         return new LOTRStandingSignBlock(HOLLY_PLANKS, LOTRSignTypes.HOLLY);
      });
      HOLLY_WALL_SIGN = BLOCKS.register("holly_wall_sign", () -> {
         return new LOTRWallSignBlock(HOLLY_SIGN);
      });
      MIRK_SHROOM = BLOCKS.register("mirk_shroom", () -> {
         return new LOTRMushroomBlock();
      });
      POTTED_MIRK_SHROOM = BLOCKS.register("potted_mirk_shroom", () -> {
         return new LOTRPottedPlantBlock(MIRK_SHROOM);
      });
      GREEN_OAK_LOG = BLOCKS.register("green_oak_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151650_B, MaterialColor.field_151650_B, () -> {
            return (Block)STRIPPED_GREEN_OAK_LOG.get();
         });
      });
      GREEN_OAK_WOOD = BLOCKS.register("green_oak_wood", () -> {
         return new StrippableWoodBlock(GREEN_OAK_LOG, () -> {
            return (Block)STRIPPED_GREEN_OAK_WOOD.get();
         });
      });
      GREEN_OAK_PLANKS = BLOCKS.register("green_oak_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151650_B);
      });
      GREEN_OAK_LEAVES = BLOCKS.register("green_oak_leaves", () -> {
         return (new LOTRLeavesBlock()).setFallingParticle(LOTRParticles.GREEN_OAK_LEAF, 150);
      });
      GREEN_OAK_SAPLING = BLOCKS.register("green_oak_sapling", () -> {
         return new LOTRSaplingBlock(new GreenOakTree());
      });
      POTTED_GREEN_OAK_SAPLING = BLOCKS.register("potted_green_oak_sapling", () -> {
         return new LOTRPottedPlantBlock(GREEN_OAK_SAPLING);
      });
      GREEN_OAK_SLAB = BLOCKS.register("green_oak_slab", () -> {
         return new AxialSlabBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_STAIRS = BLOCKS.register("green_oak_stairs", () -> {
         return new LOTRStairsBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_FENCE = BLOCKS.register("green_oak_fence", () -> {
         return new LOTRFenceBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_FENCE_GATE = BLOCKS.register("green_oak_fence_gate", () -> {
         return new LOTRFenceGateBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_DOOR = BLOCKS.register("green_oak_door", () -> {
         return new LOTRDoorBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_TRAPDOOR = BLOCKS.register("green_oak_trapdoor", () -> {
         return new LOTRTrapdoorBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_PRESSURE_PLATE = BLOCKS.register("green_oak_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(GREEN_OAK_PLANKS);
      });
      GREEN_OAK_BUTTON = BLOCKS.register("green_oak_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_GREEN_OAK_LOG = BLOCKS.register("stripped_green_oak_log", () -> {
         return new LOTRStrippedLogBlock(GREEN_OAK_LOG);
      });
      STRIPPED_GREEN_OAK_WOOD = BLOCKS.register("stripped_green_oak_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_GREEN_OAK_LOG);
      });
      GREEN_OAK_BEAM = BLOCKS.register("green_oak_beam", () -> {
         return new WoodBeamBlock(GREEN_OAK_LOG);
      });
      GREEN_OAK_SIGN = BLOCKS.register("green_oak_sign", () -> {
         return new LOTRStandingSignBlock(GREEN_OAK_PLANKS, LOTRSignTypes.GREEN_OAK);
      });
      GREEN_OAK_WALL_SIGN = BLOCKS.register("green_oak_wall_sign", () -> {
         return new LOTRWallSignBlock(GREEN_OAK_SIGN);
      });
      RED_OAK_LEAVES = BLOCKS.register("red_oak_leaves", () -> {
         return (new LOTRLeavesBlock(MaterialColor.field_193559_aa)).setFallingParticle(LOTRParticles.RED_OAK_LEAF, 40);
      });
      RED_OAK_SAPLING = BLOCKS.register("red_oak_sapling", () -> {
         return new LOTRSaplingBlock(new RedOakTree());
      });
      POTTED_RED_OAK_SAPLING = BLOCKS.register("potted_red_oak_sapling", () -> {
         return new LOTRPottedPlantBlock(RED_OAK_SAPLING);
      });
      MOSSY_GALADHRIM_BRICK = BLOCKS.register("mossy_galadhrim_brick", () -> {
         return new LOTRStoneBlock(GALADHRIM_BRICK);
      });
      MOSSY_GALADHRIM_BRICK_SLAB = BLOCKS.register("mossy_galadhrim_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_GALADHRIM_BRICK);
      });
      MOSSY_GALADHRIM_BRICK_STAIRS = BLOCKS.register("mossy_galadhrim_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_GALADHRIM_BRICK);
      });
      MOSSY_GALADHRIM_BRICK_WALL = BLOCKS.register("mossy_galadhrim_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_GALADHRIM_BRICK);
      });
      CRACKED_GALADHRIM_BRICK = BLOCKS.register("cracked_galadhrim_brick", () -> {
         return new LOTRStoneBlock(GALADHRIM_BRICK);
      });
      CRACKED_GALADHRIM_BRICK_SLAB = BLOCKS.register("cracked_galadhrim_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_GALADHRIM_BRICK);
      });
      CRACKED_GALADHRIM_BRICK_STAIRS = BLOCKS.register("cracked_galadhrim_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_GALADHRIM_BRICK);
      });
      CRACKED_GALADHRIM_BRICK_WALL = BLOCKS.register("cracked_galadhrim_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_GALADHRIM_BRICK);
      });
      CARVED_GALADHRIM_BRICK = BLOCKS.register("carved_galadhrim_brick", () -> {
         return new LOTRStoneBlock(GALADHRIM_BRICK);
      });
      HANGING_WEB = BLOCKS.register("hanging_web", () -> {
         return new HangingWebBlock();
      });
      ARID_GRASS = BLOCKS.register("arid_grass", () -> {
         return new AridGrassBlock(() -> {
            return (Block)TALL_ARID_GRASS.get();
         });
      });
      WHITE_CHRYSANTHEMUM = BLOCKS.register("white_chrysanthemum", () -> {
         return new LOTRFlowerBlock(Effects.field_76421_d, 9);
      });
      YELLOW_CHRYSANTHEMUM = BLOCKS.register("yellow_chrysanthemum", () -> {
         return new LOTRFlowerBlock(Effects.field_76437_t, 9);
      });
      PINK_CHRYSANTHEMUM = BLOCKS.register("pink_chrysanthemum", () -> {
         return new LOTRFlowerBlock(Effects.field_76428_l, 10);
      });
      RED_CHRYSANTHEMUM = BLOCKS.register("red_chrysanthemum", () -> {
         return new LOTRFlowerBlock(Effects.field_180152_w, 10);
      });
      ORANGE_CHRYSANTHEMUM = BLOCKS.register("orange_chrysanthemum", () -> {
         return new LOTRFlowerBlock(Effects.field_76426_n, 4);
      });
      POTTED_WHITE_CHRYSANTHEMUM = BLOCKS.register("potted_white_chrysanthemum", () -> {
         return new LOTRPottedPlantBlock(WHITE_CHRYSANTHEMUM);
      });
      POTTED_YELLOW_CHRYSANTHEMUM = BLOCKS.register("potted_yellow_chrysanthemum", () -> {
         return new LOTRPottedPlantBlock(YELLOW_CHRYSANTHEMUM);
      });
      POTTED_PINK_CHRYSANTHEMUM = BLOCKS.register("potted_pink_chrysanthemum", () -> {
         return new LOTRPottedPlantBlock(PINK_CHRYSANTHEMUM);
      });
      POTTED_RED_CHRYSANTHEMUM = BLOCKS.register("potted_red_chrysanthemum", () -> {
         return new LOTRPottedPlantBlock(RED_CHRYSANTHEMUM);
      });
      POTTED_ORANGE_CHRYSANTHEMUM = BLOCKS.register("potted_orange_chrysanthemum", () -> {
         return new LOTRPottedPlantBlock(ORANGE_CHRYSANTHEMUM);
      });
      BRONZE_LANTERN = BLOCKS.register("bronze_lantern", () -> {
         return new BronzeLanternBlock();
      });
      MOSSY_DWARVEN_BRICK = BLOCKS.register("mossy_dwarven_brick", () -> {
         return new LOTRStoneBlock(DWARVEN_BRICK);
      });
      MOSSY_DWARVEN_BRICK_SLAB = BLOCKS.register("mossy_dwarven_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_DWARVEN_BRICK);
      });
      MOSSY_DWARVEN_BRICK_STAIRS = BLOCKS.register("mossy_dwarven_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_DWARVEN_BRICK);
      });
      MOSSY_DWARVEN_BRICK_WALL = BLOCKS.register("mossy_dwarven_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_DWARVEN_BRICK);
      });
      CRACKED_DWARVEN_BRICK = BLOCKS.register("cracked_dwarven_brick", () -> {
         return new LOTRStoneBlock(DWARVEN_BRICK);
      });
      CRACKED_DWARVEN_BRICK_SLAB = BLOCKS.register("cracked_dwarven_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_DWARVEN_BRICK);
      });
      CRACKED_DWARVEN_BRICK_STAIRS = BLOCKS.register("cracked_dwarven_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_DWARVEN_BRICK);
      });
      CRACKED_DWARVEN_BRICK_WALL = BLOCKS.register("cracked_dwarven_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_DWARVEN_BRICK);
      });
      SNOWY_DWARVEN_BRICK = BLOCKS.register("snowy_dwarven_brick", () -> {
         return new LOTRStoneBlock(DWARVEN_BRICK);
      });
      SNOWY_DWARVEN_BRICK_SLAB = BLOCKS.register("snowy_dwarven_brick_slab", () -> {
         return new AxialSlabBlock(SNOWY_DWARVEN_BRICK);
      });
      SNOWY_DWARVEN_BRICK_STAIRS = BLOCKS.register("snowy_dwarven_brick_stairs", () -> {
         return new LOTRStairsBlock(SNOWY_DWARVEN_BRICK);
      });
      SNOWY_DWARVEN_BRICK_WALL = BLOCKS.register("snowy_dwarven_brick_wall", () -> {
         return new LOTRWallBlock(SNOWY_DWARVEN_BRICK);
      });
      CARVED_DWARVEN_BRICK = BLOCKS.register("carved_dwarven_brick", () -> {
         return new LOTRStoneBlock(DWARVEN_BRICK);
      });
      MOSSY_WOOD_ELVEN_BRICK = BLOCKS.register("mossy_wood_elven_brick", () -> {
         return new LOTRStoneBlock(WOOD_ELVEN_BRICK);
      });
      MOSSY_WOOD_ELVEN_BRICK_SLAB = BLOCKS.register("mossy_wood_elven_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_WOOD_ELVEN_BRICK);
      });
      MOSSY_WOOD_ELVEN_BRICK_STAIRS = BLOCKS.register("mossy_wood_elven_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_WOOD_ELVEN_BRICK);
      });
      MOSSY_WOOD_ELVEN_BRICK_WALL = BLOCKS.register("mossy_wood_elven_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_WOOD_ELVEN_BRICK);
      });
      CRACKED_WOOD_ELVEN_BRICK = BLOCKS.register("cracked_wood_elven_brick", () -> {
         return new LOTRStoneBlock(WOOD_ELVEN_BRICK);
      });
      CRACKED_WOOD_ELVEN_BRICK_SLAB = BLOCKS.register("cracked_wood_elven_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_WOOD_ELVEN_BRICK);
      });
      CRACKED_WOOD_ELVEN_BRICK_STAIRS = BLOCKS.register("cracked_wood_elven_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_WOOD_ELVEN_BRICK);
      });
      CRACKED_WOOD_ELVEN_BRICK_WALL = BLOCKS.register("cracked_wood_elven_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_WOOD_ELVEN_BRICK);
      });
      CARVED_WOOD_ELVEN_BRICK = BLOCKS.register("carved_wood_elven_brick", () -> {
         return new LOTRStoneBlock(WOOD_ELVEN_BRICK);
      });
      ARNOR_BRICK = BLOCKS.register("arnor_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151665_m);
      });
      ARNOR_BRICK_SLAB = BLOCKS.register("arnor_brick_slab", () -> {
         return new AxialSlabBlock(ARNOR_BRICK);
      });
      ARNOR_BRICK_STAIRS = BLOCKS.register("arnor_brick_stairs", () -> {
         return new LOTRStairsBlock(ARNOR_BRICK);
      });
      ARNOR_BRICK_WALL = BLOCKS.register("arnor_brick_wall", () -> {
         return new LOTRWallBlock(ARNOR_BRICK);
      });
      MOSSY_ARNOR_BRICK = BLOCKS.register("mossy_arnor_brick", () -> {
         return new LOTRStoneBlock(ARNOR_BRICK);
      });
      MOSSY_ARNOR_BRICK_SLAB = BLOCKS.register("mossy_arnor_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_ARNOR_BRICK);
      });
      MOSSY_ARNOR_BRICK_STAIRS = BLOCKS.register("mossy_arnor_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_ARNOR_BRICK);
      });
      MOSSY_ARNOR_BRICK_WALL = BLOCKS.register("mossy_arnor_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_ARNOR_BRICK);
      });
      CRACKED_ARNOR_BRICK = BLOCKS.register("cracked_arnor_brick", () -> {
         return new LOTRStoneBlock(ARNOR_BRICK);
      });
      CRACKED_ARNOR_BRICK_SLAB = BLOCKS.register("cracked_arnor_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_ARNOR_BRICK);
      });
      CRACKED_ARNOR_BRICK_STAIRS = BLOCKS.register("cracked_arnor_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_ARNOR_BRICK);
      });
      CRACKED_ARNOR_BRICK_WALL = BLOCKS.register("cracked_arnor_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_ARNOR_BRICK);
      });
      CARVED_ARNOR_BRICK = BLOCKS.register("carved_arnor_brick", () -> {
         return new LOTRStoneBlock(ARNOR_BRICK);
      });
      ARNOR_PILLAR = BLOCKS.register("arnor_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151665_m);
      });
      ARNOR_PILLAR_SLAB = BLOCKS.register("arnor_pillar_slab", () -> {
         return new AxialSlabBlock(ARNOR_PILLAR);
      });
      RANGER_CRAFTING_TABLE = BLOCKS.register("ranger_crafting_table", () -> {
         return new FactionCraftingTableBlock.Ranger(() -> {
            return Blocks.field_196662_n;
         });
      });
      GONDOR_COBBLEBRICK = BLOCKS.register("gondor_cobblebrick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151659_e);
      });
      GONDOR_COBBLEBRICK_SLAB = BLOCKS.register("gondor_cobblebrick_slab", () -> {
         return new AxialSlabBlock(GONDOR_COBBLEBRICK);
      });
      GONDOR_COBBLEBRICK_STAIRS = BLOCKS.register("gondor_cobblebrick_stairs", () -> {
         return new LOTRStairsBlock(GONDOR_COBBLEBRICK);
      });
      GONDOR_COBBLEBRICK_WALL = BLOCKS.register("gondor_cobblebrick_wall", () -> {
         return new LOTRWallBlock(GONDOR_COBBLEBRICK);
      });
      MOSSY_GONDOR_COBBLEBRICK = BLOCKS.register("mossy_gondor_cobblebrick", () -> {
         return new LOTRStoneBlock(GONDOR_COBBLEBRICK);
      });
      MOSSY_GONDOR_COBBLEBRICK_SLAB = BLOCKS.register("mossy_gondor_cobblebrick_slab", () -> {
         return new AxialSlabBlock(MOSSY_GONDOR_COBBLEBRICK);
      });
      MOSSY_GONDOR_COBBLEBRICK_STAIRS = BLOCKS.register("mossy_gondor_cobblebrick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_GONDOR_COBBLEBRICK);
      });
      MOSSY_GONDOR_COBBLEBRICK_WALL = BLOCKS.register("mossy_gondor_cobblebrick_wall", () -> {
         return new LOTRWallBlock(MOSSY_GONDOR_COBBLEBRICK);
      });
      CRACKED_GONDOR_COBBLEBRICK = BLOCKS.register("cracked_gondor_cobblebrick", () -> {
         return new LOTRStoneBlock(GONDOR_COBBLEBRICK);
      });
      CRACKED_GONDOR_COBBLEBRICK_SLAB = BLOCKS.register("cracked_gondor_cobblebrick_slab", () -> {
         return new AxialSlabBlock(CRACKED_GONDOR_COBBLEBRICK);
      });
      CRACKED_GONDOR_COBBLEBRICK_STAIRS = BLOCKS.register("cracked_gondor_cobblebrick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_GONDOR_COBBLEBRICK);
      });
      CRACKED_GONDOR_COBBLEBRICK_WALL = BLOCKS.register("cracked_gondor_cobblebrick_wall", () -> {
         return new LOTRWallBlock(CRACKED_GONDOR_COBBLEBRICK);
      });
      DOL_AMROTH_BRICK = BLOCKS.register("dol_amroth_brick", () -> {
         return new BrickWithAboveBlock(MaterialColor.field_151659_e, LOTRTags.Blocks.DOL_AMROTH_BRICKS);
      });
      DOL_AMROTH_BRICK_SLAB = BLOCKS.register("dol_amroth_brick_slab", () -> {
         return new AxialSlabBlock(DOL_AMROTH_BRICK);
      });
      DOL_AMROTH_BRICK_STAIRS = BLOCKS.register("dol_amroth_brick_stairs", () -> {
         return new LOTRStairsBlock(DOL_AMROTH_BRICK);
      });
      DOL_AMROTH_BRICK_WALL = BLOCKS.register("dol_amroth_brick_wall", () -> {
         return new LOTRWallBlock(DOL_AMROTH_BRICK);
      });
      MOSSY_DOL_AMROTH_BRICK = BLOCKS.register("mossy_dol_amroth_brick", () -> {
         return new BrickWithAboveBlock(DOL_AMROTH_BRICK, LOTRTags.Blocks.DOL_AMROTH_BRICKS);
      });
      MOSSY_DOL_AMROTH_BRICK_SLAB = BLOCKS.register("mossy_dol_amroth_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_DOL_AMROTH_BRICK);
      });
      MOSSY_DOL_AMROTH_BRICK_STAIRS = BLOCKS.register("mossy_dol_amroth_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_DOL_AMROTH_BRICK);
      });
      MOSSY_DOL_AMROTH_BRICK_WALL = BLOCKS.register("mossy_dol_amroth_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_DOL_AMROTH_BRICK);
      });
      CRACKED_DOL_AMROTH_BRICK = BLOCKS.register("cracked_dol_amroth_brick", () -> {
         return new BrickWithAboveBlock(DOL_AMROTH_BRICK, LOTRTags.Blocks.DOL_AMROTH_BRICKS);
      });
      CRACKED_DOL_AMROTH_BRICK_SLAB = BLOCKS.register("cracked_dol_amroth_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_DOL_AMROTH_BRICK);
      });
      CRACKED_DOL_AMROTH_BRICK_STAIRS = BLOCKS.register("cracked_dol_amroth_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_DOL_AMROTH_BRICK);
      });
      CRACKED_DOL_AMROTH_BRICK_WALL = BLOCKS.register("cracked_dol_amroth_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_DOL_AMROTH_BRICK);
      });
      DOL_AMROTH_CRAFTING_TABLE = BLOCKS.register("dol_amroth_crafting_table", () -> {
         return new FactionCraftingTableBlock.DolAmroth(DOL_AMROTH_BRICK);
      });
      CLAY_TILING = BLOCKS.register("clay_tiling", () -> {
         return new ClayTilingBlock(MaterialColor.field_151676_q);
      });
      WHITE_CLAY_TILING = BLOCKS.register("white_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.WHITE);
      });
      ORANGE_CLAY_TILING = BLOCKS.register("orange_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.ORANGE);
      });
      MAGENTA_CLAY_TILING = BLOCKS.register("magenta_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.MAGENTA);
      });
      LIGHT_BLUE_CLAY_TILING = BLOCKS.register("light_blue_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.LIGHT_BLUE);
      });
      YELLOW_CLAY_TILING = BLOCKS.register("yellow_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.YELLOW);
      });
      LIME_CLAY_TILING = BLOCKS.register("lime_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.LIME);
      });
      PINK_CLAY_TILING = BLOCKS.register("pink_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.PINK);
      });
      GRAY_CLAY_TILING = BLOCKS.register("gray_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.GRAY);
      });
      LIGHT_GRAY_CLAY_TILING = BLOCKS.register("light_gray_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.LIGHT_GRAY);
      });
      CYAN_CLAY_TILING = BLOCKS.register("cyan_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.CYAN);
      });
      PURPLE_CLAY_TILING = BLOCKS.register("purple_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.PURPLE);
      });
      BLUE_CLAY_TILING = BLOCKS.register("blue_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.BLUE);
      });
      BROWN_CLAY_TILING = BLOCKS.register("brown_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.BROWN);
      });
      GREEN_CLAY_TILING = BLOCKS.register("green_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.GREEN);
      });
      RED_CLAY_TILING = BLOCKS.register("red_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.RED);
      });
      BLACK_CLAY_TILING = BLOCKS.register("black_clay_tiling", () -> {
         return new ClayTilingBlock(DyeColor.BLACK);
      });
      CLAY_TILING_SLAB = BLOCKS.register("clay_tiling_slab", () -> {
         return new AxialSlabBlock(CLAY_TILING);
      });
      WHITE_CLAY_TILING_SLAB = BLOCKS.register("white_clay_tiling_slab", () -> {
         return new AxialSlabBlock(WHITE_CLAY_TILING);
      });
      ORANGE_CLAY_TILING_SLAB = BLOCKS.register("orange_clay_tiling_slab", () -> {
         return new AxialSlabBlock(ORANGE_CLAY_TILING);
      });
      MAGENTA_CLAY_TILING_SLAB = BLOCKS.register("magenta_clay_tiling_slab", () -> {
         return new AxialSlabBlock(MAGENTA_CLAY_TILING);
      });
      LIGHT_BLUE_CLAY_TILING_SLAB = BLOCKS.register("light_blue_clay_tiling_slab", () -> {
         return new AxialSlabBlock(LIGHT_BLUE_CLAY_TILING);
      });
      YELLOW_CLAY_TILING_SLAB = BLOCKS.register("yellow_clay_tiling_slab", () -> {
         return new AxialSlabBlock(YELLOW_CLAY_TILING);
      });
      LIME_CLAY_TILING_SLAB = BLOCKS.register("lime_clay_tiling_slab", () -> {
         return new AxialSlabBlock(LIME_CLAY_TILING);
      });
      PINK_CLAY_TILING_SLAB = BLOCKS.register("pink_clay_tiling_slab", () -> {
         return new AxialSlabBlock(PINK_CLAY_TILING);
      });
      GRAY_CLAY_TILING_SLAB = BLOCKS.register("gray_clay_tiling_slab", () -> {
         return new AxialSlabBlock(GRAY_CLAY_TILING);
      });
      LIGHT_GRAY_CLAY_TILING_SLAB = BLOCKS.register("light_gray_clay_tiling_slab", () -> {
         return new AxialSlabBlock(LIGHT_GRAY_CLAY_TILING);
      });
      CYAN_CLAY_TILING_SLAB = BLOCKS.register("cyan_clay_tiling_slab", () -> {
         return new AxialSlabBlock(CYAN_CLAY_TILING);
      });
      PURPLE_CLAY_TILING_SLAB = BLOCKS.register("purple_clay_tiling_slab", () -> {
         return new AxialSlabBlock(PURPLE_CLAY_TILING);
      });
      BLUE_CLAY_TILING_SLAB = BLOCKS.register("blue_clay_tiling_slab", () -> {
         return new AxialSlabBlock(BLUE_CLAY_TILING);
      });
      BROWN_CLAY_TILING_SLAB = BLOCKS.register("brown_clay_tiling_slab", () -> {
         return new AxialSlabBlock(BROWN_CLAY_TILING);
      });
      GREEN_CLAY_TILING_SLAB = BLOCKS.register("green_clay_tiling_slab", () -> {
         return new AxialSlabBlock(GREEN_CLAY_TILING);
      });
      RED_CLAY_TILING_SLAB = BLOCKS.register("red_clay_tiling_slab", () -> {
         return new AxialSlabBlock(RED_CLAY_TILING);
      });
      BLACK_CLAY_TILING_SLAB = BLOCKS.register("black_clay_tiling_slab", () -> {
         return new AxialSlabBlock(BLACK_CLAY_TILING);
      });
      CLAY_TILING_STAIRS = BLOCKS.register("clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(CLAY_TILING);
      });
      WHITE_CLAY_TILING_STAIRS = BLOCKS.register("white_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(WHITE_CLAY_TILING);
      });
      ORANGE_CLAY_TILING_STAIRS = BLOCKS.register("orange_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(ORANGE_CLAY_TILING);
      });
      MAGENTA_CLAY_TILING_STAIRS = BLOCKS.register("magenta_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(MAGENTA_CLAY_TILING);
      });
      LIGHT_BLUE_CLAY_TILING_STAIRS = BLOCKS.register("light_blue_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(LIGHT_BLUE_CLAY_TILING);
      });
      YELLOW_CLAY_TILING_STAIRS = BLOCKS.register("yellow_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(YELLOW_CLAY_TILING);
      });
      LIME_CLAY_TILING_STAIRS = BLOCKS.register("lime_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(LIME_CLAY_TILING);
      });
      PINK_CLAY_TILING_STAIRS = BLOCKS.register("pink_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(PINK_CLAY_TILING);
      });
      GRAY_CLAY_TILING_STAIRS = BLOCKS.register("gray_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(GRAY_CLAY_TILING);
      });
      LIGHT_GRAY_CLAY_TILING_STAIRS = BLOCKS.register("light_gray_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(LIGHT_GRAY_CLAY_TILING);
      });
      CYAN_CLAY_TILING_STAIRS = BLOCKS.register("cyan_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(CYAN_CLAY_TILING);
      });
      PURPLE_CLAY_TILING_STAIRS = BLOCKS.register("purple_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(PURPLE_CLAY_TILING);
      });
      BLUE_CLAY_TILING_STAIRS = BLOCKS.register("blue_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(BLUE_CLAY_TILING);
      });
      BROWN_CLAY_TILING_STAIRS = BLOCKS.register("brown_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(BROWN_CLAY_TILING);
      });
      GREEN_CLAY_TILING_STAIRS = BLOCKS.register("green_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(GREEN_CLAY_TILING);
      });
      RED_CLAY_TILING_STAIRS = BLOCKS.register("red_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(RED_CLAY_TILING);
      });
      BLACK_CLAY_TILING_STAIRS = BLOCKS.register("black_clay_tiling_stairs", () -> {
         return new LOTRStairsBlock(BLACK_CLAY_TILING);
      });
      CLAY_TILING_WALL = BLOCKS.register("clay_tiling_wall", () -> {
         return new LOTRWallBlock(CLAY_TILING);
      });
      WHITE_CLAY_TILING_WALL = BLOCKS.register("white_clay_tiling_wall", () -> {
         return new LOTRWallBlock(WHITE_CLAY_TILING);
      });
      ORANGE_CLAY_TILING_WALL = BLOCKS.register("orange_clay_tiling_wall", () -> {
         return new LOTRWallBlock(ORANGE_CLAY_TILING);
      });
      MAGENTA_CLAY_TILING_WALL = BLOCKS.register("magenta_clay_tiling_wall", () -> {
         return new LOTRWallBlock(MAGENTA_CLAY_TILING);
      });
      LIGHT_BLUE_CLAY_TILING_WALL = BLOCKS.register("light_blue_clay_tiling_wall", () -> {
         return new LOTRWallBlock(LIGHT_BLUE_CLAY_TILING);
      });
      YELLOW_CLAY_TILING_WALL = BLOCKS.register("yellow_clay_tiling_wall", () -> {
         return new LOTRWallBlock(YELLOW_CLAY_TILING);
      });
      LIME_CLAY_TILING_WALL = BLOCKS.register("lime_clay_tiling_wall", () -> {
         return new LOTRWallBlock(LIME_CLAY_TILING);
      });
      PINK_CLAY_TILING_WALL = BLOCKS.register("pink_clay_tiling_wall", () -> {
         return new LOTRWallBlock(PINK_CLAY_TILING);
      });
      GRAY_CLAY_TILING_WALL = BLOCKS.register("gray_clay_tiling_wall", () -> {
         return new LOTRWallBlock(GRAY_CLAY_TILING);
      });
      LIGHT_GRAY_CLAY_TILING_WALL = BLOCKS.register("light_gray_clay_tiling_wall", () -> {
         return new LOTRWallBlock(LIGHT_GRAY_CLAY_TILING);
      });
      CYAN_CLAY_TILING_WALL = BLOCKS.register("cyan_clay_tiling_wall", () -> {
         return new LOTRWallBlock(CYAN_CLAY_TILING);
      });
      PURPLE_CLAY_TILING_WALL = BLOCKS.register("purple_clay_tiling_wall", () -> {
         return new LOTRWallBlock(PURPLE_CLAY_TILING);
      });
      BLUE_CLAY_TILING_WALL = BLOCKS.register("blue_clay_tiling_wall", () -> {
         return new LOTRWallBlock(BLUE_CLAY_TILING);
      });
      BROWN_CLAY_TILING_WALL = BLOCKS.register("brown_clay_tiling_wall", () -> {
         return new LOTRWallBlock(BROWN_CLAY_TILING);
      });
      GREEN_CLAY_TILING_WALL = BLOCKS.register("green_clay_tiling_wall", () -> {
         return new LOTRWallBlock(GREEN_CLAY_TILING);
      });
      RED_CLAY_TILING_WALL = BLOCKS.register("red_clay_tiling_wall", () -> {
         return new LOTRWallBlock(RED_CLAY_TILING);
      });
      BLACK_CLAY_TILING_WALL = BLOCKS.register("black_clay_tiling_wall", () -> {
         return new LOTRWallBlock(BLACK_CLAY_TILING);
      });
      FINE_GLASS = BLOCKS.register("fine_glass", () -> {
         return new LOTRGlassBlock();
      });
      WHITE_FINE_GLASS = BLOCKS.register("white_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.WHITE);
      });
      ORANGE_FINE_GLASS = BLOCKS.register("orange_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.ORANGE);
      });
      MAGENTA_FINE_GLASS = BLOCKS.register("magenta_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.MAGENTA);
      });
      LIGHT_BLUE_FINE_GLASS = BLOCKS.register("light_blue_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.LIGHT_BLUE);
      });
      YELLOW_FINE_GLASS = BLOCKS.register("yellow_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.YELLOW);
      });
      LIME_FINE_GLASS = BLOCKS.register("lime_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.LIME);
      });
      PINK_FINE_GLASS = BLOCKS.register("pink_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.PINK);
      });
      GRAY_FINE_GLASS = BLOCKS.register("gray_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.GRAY);
      });
      LIGHT_GRAY_FINE_GLASS = BLOCKS.register("light_gray_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.LIGHT_GRAY);
      });
      CYAN_FINE_GLASS = BLOCKS.register("cyan_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.CYAN);
      });
      PURPLE_FINE_GLASS = BLOCKS.register("purple_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.PURPLE);
      });
      BLUE_FINE_GLASS = BLOCKS.register("blue_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.BLUE);
      });
      BROWN_FINE_GLASS = BLOCKS.register("brown_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.BROWN);
      });
      GREEN_FINE_GLASS = BLOCKS.register("green_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.GREEN);
      });
      RED_FINE_GLASS = BLOCKS.register("red_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.RED);
      });
      BLACK_FINE_GLASS = BLOCKS.register("black_fine_glass", () -> {
         return new LOTRStainedGlassBlock(DyeColor.BLACK);
      });
      FINE_GLASS_PANE = BLOCKS.register("fine_glass_pane", () -> {
         return new LOTRGlassPaneBlock();
      });
      WHITE_FINE_GLASS_PANE = BLOCKS.register("white_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.WHITE);
      });
      ORANGE_FINE_GLASS_PANE = BLOCKS.register("orange_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.ORANGE);
      });
      MAGENTA_FINE_GLASS_PANE = BLOCKS.register("magenta_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.MAGENTA);
      });
      LIGHT_BLUE_FINE_GLASS_PANE = BLOCKS.register("light_blue_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.LIGHT_BLUE);
      });
      YELLOW_FINE_GLASS_PANE = BLOCKS.register("yellow_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.YELLOW);
      });
      LIME_FINE_GLASS_PANE = BLOCKS.register("lime_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.LIME);
      });
      PINK_FINE_GLASS_PANE = BLOCKS.register("pink_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.PINK);
      });
      GRAY_FINE_GLASS_PANE = BLOCKS.register("gray_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.GRAY);
      });
      LIGHT_GRAY_FINE_GLASS_PANE = BLOCKS.register("light_gray_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.LIGHT_GRAY);
      });
      CYAN_FINE_GLASS_PANE = BLOCKS.register("cyan_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.CYAN);
      });
      PURPLE_FINE_GLASS_PANE = BLOCKS.register("purple_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.PURPLE);
      });
      BLUE_FINE_GLASS_PANE = BLOCKS.register("blue_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.BLUE);
      });
      BROWN_FINE_GLASS_PANE = BLOCKS.register("brown_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.BROWN);
      });
      GREEN_FINE_GLASS_PANE = BLOCKS.register("green_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.GREEN);
      });
      RED_FINE_GLASS_PANE = BLOCKS.register("red_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.RED);
      });
      BLACK_FINE_GLASS_PANE = BLOCKS.register("black_fine_glass_pane", () -> {
         return new LOTRStainedGlassPaneBlock(DyeColor.BLACK);
      });
      ORC_PLATING = BLOCKS.register("orc_plating", () -> {
         return new OrcPlatingBlock();
      });
      RUSTED_ORC_PLATING = BLOCKS.register("rusted_orc_plating", () -> {
         return new OrcPlatingBlock();
      });
      CARVED_NUMENOREAN_BRICK = BLOCKS.register("carved_numenorean_brick", () -> {
         return new LOTRStoneBlock(NUMENOREAN_BRICK);
      });
      MOSSY_UMBAR_BRICK = BLOCKS.register("mossy_umbar_brick", () -> {
         return new LOTRStoneBlock(UMBAR_BRICK);
      });
      MOSSY_UMBAR_BRICK_SLAB = BLOCKS.register("mossy_umbar_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_UMBAR_BRICK);
      });
      MOSSY_UMBAR_BRICK_STAIRS = BLOCKS.register("mossy_umbar_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_UMBAR_BRICK);
      });
      MOSSY_UMBAR_BRICK_WALL = BLOCKS.register("mossy_umbar_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_UMBAR_BRICK);
      });
      CRACKED_UMBAR_BRICK = BLOCKS.register("cracked_umbar_brick", () -> {
         return new LOTRStoneBlock(UMBAR_BRICK);
      });
      CRACKED_UMBAR_BRICK_SLAB = BLOCKS.register("cracked_umbar_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_UMBAR_BRICK);
      });
      CRACKED_UMBAR_BRICK_STAIRS = BLOCKS.register("cracked_umbar_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_UMBAR_BRICK);
      });
      CRACKED_UMBAR_BRICK_WALL = BLOCKS.register("cracked_umbar_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_UMBAR_BRICK);
      });
      CARVED_BRICK = BLOCKS.register("carved_brick", () -> {
         return new LOTRStoneBlock(() -> {
            return Blocks.field_196584_bK;
         });
      });
      ORC_PLATING_SLAB = BLOCKS.register("orc_plating_slab", () -> {
         return new AxialSlabBlock(ORC_PLATING);
      });
      ORC_PLATING_STAIRS = BLOCKS.register("orc_plating_stairs", () -> {
         return new LOTRStairsBlock(ORC_PLATING);
      });
      RUSTED_ORC_PLATING_SLAB = BLOCKS.register("rusted_orc_plating_slab", () -> {
         return new AxialSlabBlock(RUSTED_ORC_PLATING);
      });
      RUSTED_ORC_PLATING_STAIRS = BLOCKS.register("rusted_orc_plating_stairs", () -> {
         return new LOTRStairsBlock(RUSTED_ORC_PLATING);
      });
      WHITE_WOOL_SLAB = BLOCKS.register("white_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196556_aL);
      });
      ORANGE_WOOL_SLAB = BLOCKS.register("orange_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196557_aM);
      });
      MAGENTA_WOOL_SLAB = BLOCKS.register("magenta_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196558_aN);
      });
      LIGHT_BLUE_WOOL_SLAB = BLOCKS.register("light_blue_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196559_aO);
      });
      YELLOW_WOOL_SLAB = BLOCKS.register("yellow_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196560_aP);
      });
      LIME_WOOL_SLAB = BLOCKS.register("lime_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196561_aQ);
      });
      PINK_WOOL_SLAB = BLOCKS.register("pink_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196562_aR);
      });
      GRAY_WOOL_SLAB = BLOCKS.register("gray_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196563_aS);
      });
      LIGHT_GRAY_WOOL_SLAB = BLOCKS.register("light_gray_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196564_aT);
      });
      CYAN_WOOL_SLAB = BLOCKS.register("cyan_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196565_aU);
      });
      PURPLE_WOOL_SLAB = BLOCKS.register("purple_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196566_aV);
      });
      BLUE_WOOL_SLAB = BLOCKS.register("blue_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196567_aW);
      });
      BROWN_WOOL_SLAB = BLOCKS.register("brown_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196568_aX);
      });
      GREEN_WOOL_SLAB = BLOCKS.register("green_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196569_aY);
      });
      RED_WOOL_SLAB = BLOCKS.register("red_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196570_aZ);
      });
      BLACK_WOOL_SLAB = BLOCKS.register("black_wool_slab", () -> {
         return new AxialSlabBlock(Blocks.field_196602_ba);
      });
      WHITE_WOOL_STAIRS = BLOCKS.register("white_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196556_aL);
      });
      ORANGE_WOOL_STAIRS = BLOCKS.register("orange_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196557_aM);
      });
      MAGENTA_WOOL_STAIRS = BLOCKS.register("magenta_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196558_aN);
      });
      LIGHT_BLUE_WOOL_STAIRS = BLOCKS.register("light_blue_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196559_aO);
      });
      YELLOW_WOOL_STAIRS = BLOCKS.register("yellow_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196560_aP);
      });
      LIME_WOOL_STAIRS = BLOCKS.register("lime_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196561_aQ);
      });
      PINK_WOOL_STAIRS = BLOCKS.register("pink_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196562_aR);
      });
      GRAY_WOOL_STAIRS = BLOCKS.register("gray_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196563_aS);
      });
      LIGHT_GRAY_WOOL_STAIRS = BLOCKS.register("light_gray_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196564_aT);
      });
      CYAN_WOOL_STAIRS = BLOCKS.register("cyan_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196565_aU);
      });
      PURPLE_WOOL_STAIRS = BLOCKS.register("purple_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196566_aV);
      });
      BLUE_WOOL_STAIRS = BLOCKS.register("blue_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196567_aW);
      });
      BROWN_WOOL_STAIRS = BLOCKS.register("brown_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196568_aX);
      });
      GREEN_WOOL_STAIRS = BLOCKS.register("green_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196569_aY);
      });
      RED_WOOL_STAIRS = BLOCKS.register("red_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196570_aZ);
      });
      BLACK_WOOL_STAIRS = BLOCKS.register("black_wool_stairs", () -> {
         return new LOTRStairsBlock(Blocks.field_196602_ba);
      });
      ANGMAR_BRICK = BLOCKS.register("angmar_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151670_w);
      });
      ANGMAR_BRICK_SLAB = BLOCKS.register("angmar_brick_slab", () -> {
         return new AxialSlabBlock(ANGMAR_BRICK);
      });
      ANGMAR_BRICK_STAIRS = BLOCKS.register("angmar_brick_stairs", () -> {
         return new LOTRStairsBlock(ANGMAR_BRICK);
      });
      ANGMAR_BRICK_WALL = BLOCKS.register("angmar_brick_wall", () -> {
         return new LOTRWallBlock(ANGMAR_BRICK);
      });
      ANGMAR_CRAFTING_TABLE = BLOCKS.register("angmar_crafting_table", () -> {
         return new FactionCraftingTableBlock.Angmar(ANGMAR_BRICK);
      });
      MOSSY_ANGMAR_BRICK = BLOCKS.register("mossy_angmar_brick", () -> {
         return new LOTRStoneBlock(ANGMAR_BRICK);
      });
      MOSSY_ANGMAR_BRICK_SLAB = BLOCKS.register("mossy_angmar_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_ANGMAR_BRICK);
      });
      MOSSY_ANGMAR_BRICK_STAIRS = BLOCKS.register("mossy_angmar_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_ANGMAR_BRICK);
      });
      MOSSY_ANGMAR_BRICK_WALL = BLOCKS.register("mossy_angmar_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_ANGMAR_BRICK);
      });
      CRACKED_ANGMAR_BRICK = BLOCKS.register("cracked_angmar_brick", () -> {
         return new LOTRStoneBlock(ANGMAR_BRICK);
      });
      CRACKED_ANGMAR_BRICK_SLAB = BLOCKS.register("cracked_angmar_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_ANGMAR_BRICK);
      });
      CRACKED_ANGMAR_BRICK_STAIRS = BLOCKS.register("cracked_angmar_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_ANGMAR_BRICK);
      });
      CRACKED_ANGMAR_BRICK_WALL = BLOCKS.register("cracked_angmar_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_ANGMAR_BRICK);
      });
      SNOWY_ANGMAR_BRICK = BLOCKS.register("snowy_angmar_brick", () -> {
         return new LOTRStoneBlock(ANGMAR_BRICK);
      });
      SNOWY_ANGMAR_BRICK_SLAB = BLOCKS.register("snowy_angmar_brick_slab", () -> {
         return new AxialSlabBlock(SNOWY_ANGMAR_BRICK);
      });
      SNOWY_ANGMAR_BRICK_STAIRS = BLOCKS.register("snowy_angmar_brick_stairs", () -> {
         return new LOTRStairsBlock(SNOWY_ANGMAR_BRICK);
      });
      SNOWY_ANGMAR_BRICK_WALL = BLOCKS.register("snowy_angmar_brick_wall", () -> {
         return new LOTRWallBlock(SNOWY_ANGMAR_BRICK);
      });
      PURPLE_MOOR_GRASS = BLOCKS.register("purple_moor_grass", () -> {
         return new LOTRGrassBlock();
      });
      RED_MOOR_GRASS = BLOCKS.register("red_moor_grass", () -> {
         return new LOTRGrassBlock();
      });
      TALL_WHEATGRASS = BLOCKS.register("tall_wheatgrass", () -> {
         return new LOTRDoubleGrassBlock();
      });
      TALL_ARID_GRASS = BLOCKS.register("tall_arid_grass", () -> {
         return new DoubleAridGrassBlock();
      });
      CARVED_ANGMAR_BRICK = BLOCKS.register("carved_angmar_brick", () -> {
         return new LOTRStoneBlock(ANGMAR_BRICK);
      });
      ANGMAR_PILLAR = BLOCKS.register("angmar_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151670_w);
      });
      ANGMAR_PILLAR_SLAB = BLOCKS.register("angmar_pillar_slab", () -> {
         return new AxialSlabBlock(ANGMAR_PILLAR);
      });
      CHALK = BLOCKS.register("chalk", () -> {
         return new ChalkBlock(MaterialColor.field_151677_p, 0.5F, 0.5F);
      });
      CHALK_SLAB = BLOCKS.register("chalk_slab", () -> {
         return new AxialSlabBlock(CHALK);
      });
      CHALK_STAIRS = BLOCKS.register("chalk_stairs", () -> {
         return new LOTRStairsBlock(CHALK);
      });
      CHALK_WALL = BLOCKS.register("chalk_wall", () -> {
         return new LOTRWallBlock(CHALK);
      });
      CHALK_BRICK = BLOCKS.register("chalk_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_151677_p, 0.8F, 0.8F);
      });
      CHALK_BRICK_SLAB = BLOCKS.register("chalk_brick_slab", () -> {
         return new AxialSlabBlock(CHALK_BRICK);
      });
      CHALK_BRICK_STAIRS = BLOCKS.register("chalk_brick_stairs", () -> {
         return new LOTRStairsBlock(CHALK_BRICK);
      });
      CHALK_BRICK_WALL = BLOCKS.register("chalk_brick_wall", () -> {
         return new LOTRWallBlock(CHALK_BRICK);
      });
      CHALK_PILLAR = BLOCKS.register("chalk_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_151677_p);
      });
      CHALK_PILLAR_SLAB = BLOCKS.register("chalk_pillar_slab", () -> {
         return new AxialSlabBlock(CHALK_PILLAR);
      });
      CHALK_DRIPSTONE = BLOCKS.register("chalk_dripstone", () -> {
         return new DripstoneBlock(CHALK);
      });
      GONDOR_ROCK_PRESSURE_PLATE = BLOCKS.register("gondor_rock_pressure_plate", () -> {
         return new LOTRStonePressurePlateBlock(GONDOR_ROCK);
      });
      MORDOR_ROCK_PRESSURE_PLATE = BLOCKS.register("mordor_rock_pressure_plate", () -> {
         return new LOTRStonePressurePlateBlock(MORDOR_ROCK);
      });
      ROHAN_ROCK_PRESSURE_PLATE = BLOCKS.register("rohan_rock_pressure_plate", () -> {
         return new LOTRStonePressurePlateBlock(ROHAN_ROCK);
      });
      BLUE_ROCK_PRESSURE_PLATE = BLOCKS.register("blue_rock_pressure_plate", () -> {
         return new LOTRStonePressurePlateBlock(BLUE_ROCK);
      });
      RED_ROCK_PRESSURE_PLATE = BLOCKS.register("red_rock_pressure_plate", () -> {
         return new LOTRStonePressurePlateBlock(RED_ROCK);
      });
      CHALK_PRESSURE_PLATE = BLOCKS.register("chalk_pressure_plate", () -> {
         return new LOTRStonePressurePlateBlock(CHALK);
      });
      GONDOR_ROCK_BUTTON = BLOCKS.register("gondor_rock_button", () -> {
         return new LOTRStoneButtonBlock();
      });
      MORDOR_ROCK_BUTTON = BLOCKS.register("mordor_rock_button", () -> {
         return new LOTRStoneButtonBlock();
      });
      ROHAN_ROCK_BUTTON = BLOCKS.register("rohan_rock_button", () -> {
         return new LOTRStoneButtonBlock();
      });
      BLUE_ROCK_BUTTON = BLOCKS.register("blue_rock_button", () -> {
         return new LOTRStoneButtonBlock();
      });
      RED_ROCK_BUTTON = BLOCKS.register("red_rock_button", () -> {
         return new LOTRStoneButtonBlock();
      });
      CHALK_BUTTON = BLOCKS.register("chalk_button", () -> {
         return new LOTRStoneButtonBlock();
      });
      POLISHED_CHALK = BLOCKS.register("polished_chalk", () -> {
         return new LOTRStoneBlock(CHALK);
      });
      POLISHED_CHALK_SLAB = BLOCKS.register("polished_chalk_slab", () -> {
         return new AxialSlabBlock(POLISHED_CHALK);
      });
      POLISHED_CHALK_STAIRS = BLOCKS.register("polished_chalk_stairs", () -> {
         return new LOTRStairsBlock(POLISHED_CHALK);
      });
      POLISHED_CHALK_WALL = BLOCKS.register("polished_chalk_wall", () -> {
         return new LOTRWallBlock(POLISHED_CHALK);
      });
      DIRTY_CHALK = BLOCKS.register("dirty_chalk", () -> {
         return new DirtyChalkBlock(CHALK);
      });
      DORWINION_BRICK = BLOCKS.register("dorwinion_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193561_M);
      });
      DORWINION_BRICK_SLAB = BLOCKS.register("dorwinion_brick_slab", () -> {
         return new AxialSlabBlock(DORWINION_BRICK);
      });
      DORWINION_BRICK_STAIRS = BLOCKS.register("dorwinion_brick_stairs", () -> {
         return new LOTRStairsBlock(DORWINION_BRICK);
      });
      DORWINION_BRICK_WALL = BLOCKS.register("dorwinion_brick_wall", () -> {
         return new LOTRWallBlock(DORWINION_BRICK);
      });
      DORWINION_CRAFTING_TABLE = BLOCKS.register("dorwinion_crafting_table", () -> {
         return new FactionCraftingTableBlock.Dorwinion(() -> {
            return Blocks.field_196662_n;
         });
      });
      MOSSY_DORWINION_BRICK = BLOCKS.register("mossy_dorwinion_brick", () -> {
         return new LOTRStoneBlock(DORWINION_BRICK);
      });
      MOSSY_DORWINION_BRICK_SLAB = BLOCKS.register("mossy_dorwinion_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_DORWINION_BRICK);
      });
      MOSSY_DORWINION_BRICK_STAIRS = BLOCKS.register("mossy_dorwinion_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_DORWINION_BRICK);
      });
      MOSSY_DORWINION_BRICK_WALL = BLOCKS.register("mossy_dorwinion_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_DORWINION_BRICK);
      });
      CRACKED_DORWINION_BRICK = BLOCKS.register("cracked_dorwinion_brick", () -> {
         return new LOTRStoneBlock(DORWINION_BRICK);
      });
      CRACKED_DORWINION_BRICK_SLAB = BLOCKS.register("cracked_dorwinion_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_DORWINION_BRICK);
      });
      CRACKED_DORWINION_BRICK_STAIRS = BLOCKS.register("cracked_dorwinion_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_DORWINION_BRICK);
      });
      CRACKED_DORWINION_BRICK_WALL = BLOCKS.register("cracked_dorwinion_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_DORWINION_BRICK);
      });
      RED_DORWINION_BRICK = BLOCKS.register("red_dorwinion_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193559_aa);
      });
      RED_DORWINION_BRICK_SLAB = BLOCKS.register("red_dorwinion_brick_slab", () -> {
         return new AxialSlabBlock(RED_DORWINION_BRICK);
      });
      RED_DORWINION_BRICK_STAIRS = BLOCKS.register("red_dorwinion_brick_stairs", () -> {
         return new LOTRStairsBlock(RED_DORWINION_BRICK);
      });
      RED_DORWINION_BRICK_WALL = BLOCKS.register("red_dorwinion_brick_wall", () -> {
         return new LOTRWallBlock(RED_DORWINION_BRICK);
      });
      MOSSY_RED_DORWINION_BRICK = BLOCKS.register("mossy_red_dorwinion_brick", () -> {
         return new LOTRStoneBlock(RED_DORWINION_BRICK);
      });
      MOSSY_RED_DORWINION_BRICK_SLAB = BLOCKS.register("mossy_red_dorwinion_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_RED_DORWINION_BRICK);
      });
      MOSSY_RED_DORWINION_BRICK_STAIRS = BLOCKS.register("mossy_red_dorwinion_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_RED_DORWINION_BRICK);
      });
      MOSSY_RED_DORWINION_BRICK_WALL = BLOCKS.register("mossy_red_dorwinion_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_RED_DORWINION_BRICK);
      });
      CRACKED_RED_DORWINION_BRICK = BLOCKS.register("cracked_red_dorwinion_brick", () -> {
         return new LOTRStoneBlock(RED_DORWINION_BRICK);
      });
      CRACKED_RED_DORWINION_BRICK_SLAB = BLOCKS.register("cracked_red_dorwinion_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_RED_DORWINION_BRICK);
      });
      CRACKED_RED_DORWINION_BRICK_STAIRS = BLOCKS.register("cracked_red_dorwinion_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_RED_DORWINION_BRICK);
      });
      CRACKED_RED_DORWINION_BRICK_WALL = BLOCKS.register("cracked_red_dorwinion_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_RED_DORWINION_BRICK);
      });
      DORWINION_PILLAR = BLOCKS.register("dorwinion_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_193561_M);
      });
      DORWINION_PILLAR_SLAB = BLOCKS.register("dorwinion_pillar_slab", () -> {
         return new AxialSlabBlock(DORWINION_PILLAR);
      });
      WHITE_SAND = BLOCKS.register("white_sand", () -> {
         return new LOTRSandBlock(15856113, MaterialColor.field_151659_e);
      });
      WHITE_SANDSTONE = BLOCKS.register("white_sandstone", () -> {
         return new LOTRSandstoneBlock(MaterialColor.field_151659_e);
      });
      WHITE_SANDSTONE_SLAB = BLOCKS.register("white_sandstone_slab", () -> {
         return new AxialSlabBlock(WHITE_SANDSTONE);
      });
      WHITE_SANDSTONE_STAIRS = BLOCKS.register("white_sandstone_stairs", () -> {
         return new LOTRStairsBlock(WHITE_SANDSTONE);
      });
      WHITE_SANDSTONE_WALL = BLOCKS.register("white_sandstone_wall", () -> {
         return new LOTRWallBlock(WHITE_SANDSTONE);
      });
      WHITE_SANDSTONE_DRIPSTONE = BLOCKS.register("white_sandstone_dripstone", () -> {
         return new DripstoneBlock(WHITE_SANDSTONE);
      });
      CUT_WHITE_SANDSTONE = BLOCKS.register("cut_white_sandstone", () -> {
         return new LOTRSandstoneBlock(MaterialColor.field_151659_e);
      });
      CUT_WHITE_SANDSTONE_SLAB = BLOCKS.register("cut_white_sandstone_slab", () -> {
         return new AxialSlabBlock(CUT_WHITE_SANDSTONE);
      });
      CHISELED_WHITE_SANDSTONE = BLOCKS.register("chiseled_white_sandstone", () -> {
         return new LOTRSandstoneBlock(MaterialColor.field_151659_e);
      });
      SMOOTH_WHITE_SANDSTONE = BLOCKS.register("smooth_white_sandstone", () -> {
         return new LOTRSandstoneBlock(MaterialColor.field_151659_e);
      });
      SMOOTH_WHITE_SANDSTONE_SLAB = BLOCKS.register("smooth_white_sandstone_slab", () -> {
         return new AxialSlabBlock(SMOOTH_WHITE_SANDSTONE);
      });
      SMOOTH_WHITE_SANDSTONE_STAIRS = BLOCKS.register("smooth_white_sandstone_stairs", () -> {
         return new LOTRStairsBlock(SMOOTH_WHITE_SANDSTONE);
      });
      CYPRESS_LOG = BLOCKS.register("cypress_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151658_d, MaterialColor.field_151664_l, () -> {
            return (Block)STRIPPED_CYPRESS_LOG.get();
         });
      });
      CYPRESS_WOOD = BLOCKS.register("cypress_wood", () -> {
         return new StrippableWoodBlock(CYPRESS_LOG, () -> {
            return (Block)STRIPPED_CYPRESS_WOOD.get();
         });
      });
      CYPRESS_PLANKS = BLOCKS.register("cypress_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151658_d);
      });
      CYPRESS_LEAVES = BLOCKS.register("cypress_leaves", () -> {
         return new LOTRLeavesBlock();
      });
      CYPRESS_SAPLING = BLOCKS.register("cypress_sapling", () -> {
         return new LOTRSaplingBlock(new CypressTree());
      });
      POTTED_CYPRESS_SAPLING = BLOCKS.register("potted_cypress_sapling", () -> {
         return new LOTRPottedPlantBlock(CYPRESS_SAPLING);
      });
      CYPRESS_SLAB = BLOCKS.register("cypress_slab", () -> {
         return new AxialSlabBlock(CYPRESS_PLANKS);
      });
      CYPRESS_STAIRS = BLOCKS.register("cypress_stairs", () -> {
         return new LOTRStairsBlock(CYPRESS_PLANKS);
      });
      CYPRESS_FENCE = BLOCKS.register("cypress_fence", () -> {
         return new LOTRFenceBlock(CYPRESS_PLANKS);
      });
      CYPRESS_FENCE_GATE = BLOCKS.register("cypress_fence_gate", () -> {
         return new LOTRFenceGateBlock(CYPRESS_PLANKS);
      });
      CYPRESS_DOOR = BLOCKS.register("cypress_door", () -> {
         return new LOTRDoorBlock(CYPRESS_PLANKS);
      });
      CYPRESS_TRAPDOOR = BLOCKS.register("cypress_trapdoor", () -> {
         return new LOTRTrapdoorBlock(CYPRESS_PLANKS);
      });
      CYPRESS_PRESSURE_PLATE = BLOCKS.register("cypress_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(CYPRESS_PLANKS);
      });
      CYPRESS_BUTTON = BLOCKS.register("cypress_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_CYPRESS_LOG = BLOCKS.register("stripped_cypress_log", () -> {
         return new LOTRStrippedLogBlock(CYPRESS_LOG);
      });
      STRIPPED_CYPRESS_WOOD = BLOCKS.register("stripped_cypress_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_CYPRESS_LOG);
      });
      CYPRESS_BEAM = BLOCKS.register("cypress_beam", () -> {
         return new WoodBeamBlock(CYPRESS_LOG);
      });
      CYPRESS_SIGN = BLOCKS.register("cypress_sign", () -> {
         return new LOTRStandingSignBlock(CYPRESS_PLANKS, LOTRSignTypes.CYPRESS);
      });
      CYPRESS_WALL_SIGN = BLOCKS.register("cypress_wall_sign", () -> {
         return new LOTRWallSignBlock(CYPRESS_SIGN);
      });
      DALE_BRICK = BLOCKS.register("dale_brick", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193565_Q);
      });
      DALE_BRICK_SLAB = BLOCKS.register("dale_brick_slab", () -> {
         return new AxialSlabBlock(DALE_BRICK);
      });
      DALE_BRICK_STAIRS = BLOCKS.register("dale_brick_stairs", () -> {
         return new LOTRStairsBlock(DALE_BRICK);
      });
      DALE_BRICK_WALL = BLOCKS.register("dale_brick_wall", () -> {
         return new LOTRWallBlock(DALE_BRICK);
      });
      MOSSY_DALE_BRICK = BLOCKS.register("mossy_dale_brick", () -> {
         return new LOTRStoneBlock(DALE_BRICK);
      });
      MOSSY_DALE_BRICK_SLAB = BLOCKS.register("mossy_dale_brick_slab", () -> {
         return new AxialSlabBlock(MOSSY_DALE_BRICK);
      });
      MOSSY_DALE_BRICK_STAIRS = BLOCKS.register("mossy_dale_brick_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_DALE_BRICK);
      });
      MOSSY_DALE_BRICK_WALL = BLOCKS.register("mossy_dale_brick_wall", () -> {
         return new LOTRWallBlock(MOSSY_DALE_BRICK);
      });
      CRACKED_DALE_BRICK = BLOCKS.register("cracked_dale_brick", () -> {
         return new LOTRStoneBlock(DALE_BRICK);
      });
      CRACKED_DALE_BRICK_SLAB = BLOCKS.register("cracked_dale_brick_slab", () -> {
         return new AxialSlabBlock(CRACKED_DALE_BRICK);
      });
      CRACKED_DALE_BRICK_STAIRS = BLOCKS.register("cracked_dale_brick_stairs", () -> {
         return new LOTRStairsBlock(CRACKED_DALE_BRICK);
      });
      CRACKED_DALE_BRICK_WALL = BLOCKS.register("cracked_dale_brick_wall", () -> {
         return new LOTRWallBlock(CRACKED_DALE_BRICK);
      });
      DALE_CRAFTING_TABLE = BLOCKS.register("dale_crafting_table", () -> {
         return new FactionCraftingTableBlock.Dale(() -> {
            return Blocks.field_196662_n;
         });
      });
      DALE_PILLAR = BLOCKS.register("dale_pillar", () -> {
         return new LOTRPillarBlock(MaterialColor.field_193565_Q);
      });
      DALE_PILLAR_SLAB = BLOCKS.register("dale_pillar_slab", () -> {
         return new AxialSlabBlock(DALE_PILLAR);
      });
      CARVED_DALE_BRICK = BLOCKS.register("carved_dale_brick", () -> {
         return new DirectionalStoneBlock(DALE_BRICK);
      });
      BLACKROOT = BLOCKS.register("blackroot", () -> {
         return (new FlowerLikeBlock()).setPlantShape(Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D));
      });
      POTTED_BLACKROOT = BLOCKS.register("potted_blackroot", () -> {
         return new LOTRPottedPlantBlock(BLACKROOT);
      });
      DALE_PAVING = BLOCKS.register("dale_paving", () -> {
         return new LOTRStoneBlock(MaterialColor.field_193565_Q);
      });
      DALE_PAVING_SLAB = BLOCKS.register("dale_paving_slab", () -> {
         return new AxialSlabBlock(DALE_PAVING);
      });
      DALE_PAVING_STAIRS = BLOCKS.register("dale_paving_stairs", () -> {
         return new LOTRStairsBlock(DALE_PAVING);
      });
      MOSSY_DALE_PAVING = BLOCKS.register("mossy_dale_paving", () -> {
         return new LOTRStoneBlock(DALE_PAVING);
      });
      MOSSY_DALE_PAVING_SLAB = BLOCKS.register("mossy_dale_paving_slab", () -> {
         return new AxialSlabBlock(MOSSY_DALE_PAVING);
      });
      MOSSY_DALE_PAVING_STAIRS = BLOCKS.register("mossy_dale_paving_stairs", () -> {
         return new LOTRStairsBlock(MOSSY_DALE_PAVING);
      });
      YELLOW_IRIS = BLOCKS.register("yellow_iris", () -> {
         return new WateryTallFlowerBlock();
      });
      QUAGMIRE = BLOCKS.register("quagmire", () -> {
         return new QuagmireBlock();
      });
      MALLOS = BLOCKS.register("mallos", () -> {
         return new LOTRFlowerBlock(Effects.field_76422_e, 8);
      });
      POTTED_MALLOS = BLOCKS.register("potted_mallos", () -> {
         return new LOTRPottedPlantBlock(MALLOS);
      });
      ROTTEN_LOG = BLOCKS.register("rotten_log", () -> {
         return new StrippableRottenLogBlock(MaterialColor.field_151654_J, MaterialColor.field_151670_w, () -> {
            return (Block)STRIPPED_ROTTEN_LOG.get();
         });
      });
      ROTTEN_WOOD = BLOCKS.register("rotten_wood", () -> {
         return new StrippableWoodBlock(ROTTEN_LOG, () -> {
            return (Block)STRIPPED_ROTTEN_WOOD.get();
         });
      });
      ROTTEN_PLANKS = BLOCKS.register("rotten_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151654_J);
      });
      ROTTEN_SLAB = BLOCKS.register("rotten_slab", () -> {
         return new AxialSlabBlock(ROTTEN_PLANKS);
      });
      ROTTEN_STAIRS = BLOCKS.register("rotten_stairs", () -> {
         return new LOTRStairsBlock(ROTTEN_PLANKS);
      });
      ROTTEN_FENCE = BLOCKS.register("rotten_fence", () -> {
         return new LOTRFenceBlock(ROTTEN_PLANKS);
      });
      ROTTEN_FENCE_GATE = BLOCKS.register("rotten_fence_gate", () -> {
         return new LOTRFenceGateBlock(ROTTEN_PLANKS);
      });
      ROTTEN_DOOR = BLOCKS.register("rotten_door", () -> {
         return new LOTRDoorBlock(ROTTEN_PLANKS);
      });
      ROTTEN_TRAPDOOR = BLOCKS.register("rotten_trapdoor", () -> {
         return new LOTRTrapdoorBlock(ROTTEN_PLANKS);
      });
      ROTTEN_PRESSURE_PLATE = BLOCKS.register("rotten_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(ROTTEN_PLANKS);
      });
      ROTTEN_BUTTON = BLOCKS.register("rotten_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_ROTTEN_LOG = BLOCKS.register("stripped_rotten_log", () -> {
         return new StrippedRottenLogBlock(ROTTEN_LOG);
      });
      STRIPPED_ROTTEN_WOOD = BLOCKS.register("stripped_rotten_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_ROTTEN_LOG);
      });
      ROTTEN_BEAM = BLOCKS.register("rotten_beam", () -> {
         return new RottenWoodBeamBlock(ROTTEN_LOG);
      });
      ROTTEN_SIGN = BLOCKS.register("rotten_sign", () -> {
         return new LOTRStandingSignBlock(ROTTEN_PLANKS, LOTRSignTypes.ROTTEN);
      });
      ROTTEN_WALL_SIGN = BLOCKS.register("rotten_wall_sign", () -> {
         return new LOTRWallSignBlock(ROTTEN_SIGN);
      });
      REEDS = BLOCKS.register("reeds", () -> {
         return new ReedsBlock();
      });
      PAPYRUS = BLOCKS.register("papyrus", () -> {
         return new ReedsBlock();
      });
      RUSHES = BLOCKS.register("rushes", () -> {
         return new RushesBlock();
      });
      DRIED_REEDS = BLOCKS.register("dried_reeds", () -> {
         return new DriedReedsBlock();
      });
      REED_THATCH = BLOCKS.register("reed_thatch", () -> {
         return new ThatchBlock(MaterialColor.field_193574_Z);
      });
      REED_THATCH_SLAB = BLOCKS.register("reed_thatch_slab", () -> {
         return new ThatchSlabBlock(REED_THATCH);
      });
      REED_THATCH_STAIRS = BLOCKS.register("reed_thatch_stairs", () -> {
         return new ThatchStairsBlock(REED_THATCH);
      });
      WHITE_WATER_LILY = BLOCKS.register("white_water_lily", () -> {
         return new LOTRWaterLilyBlock();
      });
      YELLOW_WATER_LILY = BLOCKS.register("yellow_water_lily", () -> {
         return new LOTRWaterLilyBlock();
      });
      PURPLE_WATER_LILY = BLOCKS.register("purple_water_lily", () -> {
         return new LOTRWaterLilyBlock();
      });
      PINK_WATER_LILY = BLOCKS.register("pink_water_lily", () -> {
         return new LOTRWaterLilyBlock();
      });
      OAK_LOG_SLAB = BLOCKS.register("oak_log_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196617_K, () -> {
            return (Block)STRIPPED_OAK_LOG_SLAB.get();
         });
      });
      OAK_WOOD_SLAB = BLOCKS.register("oak_wood_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196626_Q, () -> {
            return (Block)STRIPPED_OAK_WOOD_SLAB.get();
         });
      });
      OAK_WOOD_STAIRS = BLOCKS.register("oak_wood_stairs", () -> {
         return new StrippableLogStairsBlock(Blocks.field_196626_Q, () -> {
            return (Block)STRIPPED_OAK_WOOD_STAIRS.get();
         });
      });
      OAK_BRANCH = BLOCKS.register("oak_branch", () -> {
         return new StrippableBranchBlock(Blocks.field_196626_Q, () -> {
            return (Block)STRIPPED_OAK_BRANCH.get();
         });
      });
      STRIPPED_OAK_LOG_SLAB = BLOCKS.register("stripped_oak_log_slab", () -> {
         return new LogSlabBlock(Blocks.field_203204_R);
      });
      STRIPPED_OAK_WOOD_SLAB = BLOCKS.register("stripped_oak_wood_slab", () -> {
         return new LogSlabBlock(Blocks.field_209389_ab);
      });
      STRIPPED_OAK_WOOD_STAIRS = BLOCKS.register("stripped_oak_wood_stairs", () -> {
         return new LogStairsBlock(Blocks.field_209389_ab);
      });
      STRIPPED_OAK_BRANCH = BLOCKS.register("stripped_oak_branch", () -> {
         return new BranchBlock(Blocks.field_209389_ab);
      });
      OAK_BEAM_SLAB = BLOCKS.register("oak_beam_slab", () -> {
         return new LogSlabBlock(OAK_BEAM);
      });
      SPRUCE_LOG_SLAB = BLOCKS.register("spruce_log_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196618_L, () -> {
            return (Block)STRIPPED_SPRUCE_LOG_SLAB.get();
         });
      });
      SPRUCE_WOOD_SLAB = BLOCKS.register("spruce_wood_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196629_R, () -> {
            return (Block)STRIPPED_SPRUCE_WOOD_SLAB.get();
         });
      });
      SPRUCE_WOOD_STAIRS = BLOCKS.register("spruce_wood_stairs", () -> {
         return new StrippableLogStairsBlock(Blocks.field_196629_R, () -> {
            return (Block)STRIPPED_SPRUCE_WOOD_STAIRS.get();
         });
      });
      SPRUCE_BRANCH = BLOCKS.register("spruce_branch", () -> {
         return new StrippableBranchBlock(Blocks.field_196629_R, () -> {
            return (Block)STRIPPED_SPRUCE_BRANCH.get();
         });
      });
      STRIPPED_SPRUCE_LOG_SLAB = BLOCKS.register("stripped_spruce_log_slab", () -> {
         return new LogSlabBlock(Blocks.field_203205_S);
      });
      STRIPPED_SPRUCE_WOOD_SLAB = BLOCKS.register("stripped_spruce_wood_slab", () -> {
         return new LogSlabBlock(Blocks.field_209390_ac);
      });
      STRIPPED_SPRUCE_WOOD_STAIRS = BLOCKS.register("stripped_spruce_wood_stairs", () -> {
         return new LogStairsBlock(Blocks.field_209390_ac);
      });
      STRIPPED_SPRUCE_BRANCH = BLOCKS.register("stripped_spruce_branch", () -> {
         return new BranchBlock(Blocks.field_209390_ac);
      });
      SPRUCE_BEAM_SLAB = BLOCKS.register("spruce_beam_slab", () -> {
         return new LogSlabBlock(SPRUCE_BEAM);
      });
      BIRCH_LOG_SLAB = BLOCKS.register("birch_log_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196619_M, () -> {
            return (Block)STRIPPED_BIRCH_LOG_SLAB.get();
         });
      });
      BIRCH_WOOD_SLAB = BLOCKS.register("birch_wood_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196631_S, () -> {
            return (Block)STRIPPED_BIRCH_WOOD_SLAB.get();
         });
      });
      BIRCH_WOOD_STAIRS = BLOCKS.register("birch_wood_stairs", () -> {
         return new StrippableLogStairsBlock(Blocks.field_196631_S, () -> {
            return (Block)STRIPPED_BIRCH_WOOD_STAIRS.get();
         });
      });
      BIRCH_BRANCH = BLOCKS.register("birch_branch", () -> {
         return new StrippableBranchBlock(Blocks.field_196631_S, () -> {
            return (Block)STRIPPED_BIRCH_BRANCH.get();
         });
      });
      STRIPPED_BIRCH_LOG_SLAB = BLOCKS.register("stripped_birch_log_slab", () -> {
         return new LogSlabBlock(Blocks.field_203206_T);
      });
      STRIPPED_BIRCH_WOOD_SLAB = BLOCKS.register("stripped_birch_wood_slab", () -> {
         return new LogSlabBlock(Blocks.field_209391_ad);
      });
      STRIPPED_BIRCH_WOOD_STAIRS = BLOCKS.register("stripped_birch_wood_stairs", () -> {
         return new LogStairsBlock(Blocks.field_209391_ad);
      });
      STRIPPED_BIRCH_BRANCH = BLOCKS.register("stripped_birch_branch", () -> {
         return new BranchBlock(Blocks.field_209391_ad);
      });
      BIRCH_BEAM_SLAB = BLOCKS.register("birch_beam_slab", () -> {
         return new LogSlabBlock(BIRCH_BEAM);
      });
      JUNGLE_LOG_SLAB = BLOCKS.register("jungle_log_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196620_N, () -> {
            return (Block)STRIPPED_JUNGLE_LOG_SLAB.get();
         });
      });
      JUNGLE_WOOD_SLAB = BLOCKS.register("jungle_wood_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196634_T, () -> {
            return (Block)STRIPPED_JUNGLE_WOOD_SLAB.get();
         });
      });
      JUNGLE_WOOD_STAIRS = BLOCKS.register("jungle_wood_stairs", () -> {
         return new StrippableLogStairsBlock(Blocks.field_196634_T, () -> {
            return (Block)STRIPPED_JUNGLE_WOOD_STAIRS.get();
         });
      });
      JUNGLE_BRANCH = BLOCKS.register("jungle_branch", () -> {
         return new StrippableBranchBlock(Blocks.field_196634_T, () -> {
            return (Block)STRIPPED_JUNGLE_BRANCH.get();
         });
      });
      STRIPPED_JUNGLE_LOG_SLAB = BLOCKS.register("stripped_jungle_log_slab", () -> {
         return new LogSlabBlock(Blocks.field_203207_U);
      });
      STRIPPED_JUNGLE_WOOD_SLAB = BLOCKS.register("stripped_jungle_wood_slab", () -> {
         return new LogSlabBlock(Blocks.field_209392_ae);
      });
      STRIPPED_JUNGLE_WOOD_STAIRS = BLOCKS.register("stripped_jungle_wood_stairs", () -> {
         return new LogStairsBlock(Blocks.field_209392_ae);
      });
      STRIPPED_JUNGLE_BRANCH = BLOCKS.register("stripped_jungle_branch", () -> {
         return new BranchBlock(Blocks.field_209392_ae);
      });
      JUNGLE_BEAM_SLAB = BLOCKS.register("jungle_beam_slab", () -> {
         return new LogSlabBlock(JUNGLE_BEAM);
      });
      ACACIA_LOG_SLAB = BLOCKS.register("acacia_log_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196621_O, () -> {
            return (Block)STRIPPED_ACACIA_LOG_SLAB.get();
         });
      });
      ACACIA_WOOD_SLAB = BLOCKS.register("acacia_wood_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196637_U, () -> {
            return (Block)STRIPPED_ACACIA_WOOD_SLAB.get();
         });
      });
      ACACIA_WOOD_STAIRS = BLOCKS.register("acacia_wood_stairs", () -> {
         return new StrippableLogStairsBlock(Blocks.field_196637_U, () -> {
            return (Block)STRIPPED_ACACIA_WOOD_STAIRS.get();
         });
      });
      ACACIA_BRANCH = BLOCKS.register("acacia_branch", () -> {
         return new StrippableBranchBlock(Blocks.field_196637_U, () -> {
            return (Block)STRIPPED_ACACIA_BRANCH.get();
         });
      });
      STRIPPED_ACACIA_LOG_SLAB = BLOCKS.register("stripped_acacia_log_slab", () -> {
         return new LogSlabBlock(Blocks.field_203208_V);
      });
      STRIPPED_ACACIA_WOOD_SLAB = BLOCKS.register("stripped_acacia_wood_slab", () -> {
         return new LogSlabBlock(Blocks.field_209393_af);
      });
      STRIPPED_ACACIA_WOOD_STAIRS = BLOCKS.register("stripped_acacia_wood_stairs", () -> {
         return new LogStairsBlock(Blocks.field_209393_af);
      });
      STRIPPED_ACACIA_BRANCH = BLOCKS.register("stripped_acacia_branch", () -> {
         return new BranchBlock(Blocks.field_209393_af);
      });
      ACACIA_BEAM_SLAB = BLOCKS.register("acacia_beam_slab", () -> {
         return new LogSlabBlock(ACACIA_BEAM);
      });
      DARK_OAK_LOG_SLAB = BLOCKS.register("dark_oak_log_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196623_P, () -> {
            return (Block)STRIPPED_DARK_OAK_LOG_SLAB.get();
         });
      });
      DARK_OAK_WOOD_SLAB = BLOCKS.register("dark_oak_wood_slab", () -> {
         return new StrippableLogSlabBlock(Blocks.field_196639_V, () -> {
            return (Block)STRIPPED_DARK_OAK_WOOD_SLAB.get();
         });
      });
      DARK_OAK_WOOD_STAIRS = BLOCKS.register("dark_oak_wood_stairs", () -> {
         return new StrippableLogStairsBlock(Blocks.field_196639_V, () -> {
            return (Block)STRIPPED_DARK_OAK_WOOD_STAIRS.get();
         });
      });
      DARK_OAK_BRANCH = BLOCKS.register("dark_oak_branch", () -> {
         return new StrippableBranchBlock(Blocks.field_196639_V, () -> {
            return (Block)STRIPPED_DARK_OAK_BRANCH.get();
         });
      });
      STRIPPED_DARK_OAK_LOG_SLAB = BLOCKS.register("stripped_dark_oak_log_slab", () -> {
         return new LogSlabBlock(Blocks.field_203209_W);
      });
      STRIPPED_DARK_OAK_WOOD_SLAB = BLOCKS.register("stripped_dark_oak_wood_slab", () -> {
         return new LogSlabBlock(Blocks.field_209394_ag);
      });
      STRIPPED_DARK_OAK_WOOD_STAIRS = BLOCKS.register("stripped_dark_oak_wood_stairs", () -> {
         return new LogStairsBlock(Blocks.field_209394_ag);
      });
      STRIPPED_DARK_OAK_BRANCH = BLOCKS.register("stripped_dark_oak_branch", () -> {
         return new BranchBlock(Blocks.field_209394_ag);
      });
      DARK_OAK_BEAM_SLAB = BLOCKS.register("dark_oak_beam_slab", () -> {
         return new LogSlabBlock(DARK_OAK_BEAM);
      });
      PINE_LOG_SLAB = BLOCKS.register("pine_log_slab", () -> {
         return new StrippableLogSlabBlock(PINE_LOG, () -> {
            return (Block)STRIPPED_PINE_LOG_SLAB.get();
         });
      });
      PINE_WOOD_SLAB = BLOCKS.register("pine_wood_slab", () -> {
         return new StrippableLogSlabBlock(PINE_WOOD, () -> {
            return (Block)STRIPPED_PINE_WOOD_SLAB.get();
         });
      });
      PINE_WOOD_STAIRS = BLOCKS.register("pine_wood_stairs", () -> {
         return new StrippableLogStairsBlock(PINE_WOOD, () -> {
            return (Block)STRIPPED_PINE_WOOD_STAIRS.get();
         });
      });
      PINE_BRANCH = BLOCKS.register("pine_branch", () -> {
         return new StrippableBranchBlock(PINE_WOOD, () -> {
            return (Block)STRIPPED_PINE_BRANCH.get();
         });
      });
      STRIPPED_PINE_LOG_SLAB = BLOCKS.register("stripped_pine_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_PINE_LOG);
      });
      STRIPPED_PINE_WOOD_SLAB = BLOCKS.register("stripped_pine_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_PINE_WOOD);
      });
      STRIPPED_PINE_WOOD_STAIRS = BLOCKS.register("stripped_pine_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_PINE_WOOD);
      });
      STRIPPED_PINE_BRANCH = BLOCKS.register("stripped_pine_branch", () -> {
         return new BranchBlock(STRIPPED_PINE_WOOD);
      });
      PINE_BEAM_SLAB = BLOCKS.register("pine_beam_slab", () -> {
         return new LogSlabBlock(PINE_BEAM);
      });
      MALLORN_LOG_SLAB = BLOCKS.register("mallorn_log_slab", () -> {
         return new StrippableLogSlabBlock(MALLORN_LOG, () -> {
            return (Block)STRIPPED_MALLORN_LOG_SLAB.get();
         });
      });
      MALLORN_WOOD_SLAB = BLOCKS.register("mallorn_wood_slab", () -> {
         return new StrippableLogSlabBlock(MALLORN_WOOD, () -> {
            return (Block)STRIPPED_MALLORN_WOOD_SLAB.get();
         });
      });
      MALLORN_WOOD_STAIRS = BLOCKS.register("mallorn_wood_stairs", () -> {
         return new StrippableLogStairsBlock(MALLORN_WOOD, () -> {
            return (Block)STRIPPED_MALLORN_WOOD_STAIRS.get();
         });
      });
      MALLORN_BRANCH = BLOCKS.register("mallorn_branch", () -> {
         return new StrippableBranchBlock(MALLORN_WOOD, () -> {
            return (Block)STRIPPED_MALLORN_BRANCH.get();
         });
      });
      STRIPPED_MALLORN_LOG_SLAB = BLOCKS.register("stripped_mallorn_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_MALLORN_LOG);
      });
      STRIPPED_MALLORN_WOOD_SLAB = BLOCKS.register("stripped_mallorn_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_MALLORN_WOOD);
      });
      STRIPPED_MALLORN_WOOD_STAIRS = BLOCKS.register("stripped_mallorn_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_MALLORN_WOOD);
      });
      STRIPPED_MALLORN_BRANCH = BLOCKS.register("stripped_mallorn_branch", () -> {
         return new BranchBlock(STRIPPED_MALLORN_WOOD);
      });
      MALLORN_BEAM_SLAB = BLOCKS.register("mallorn_beam_slab", () -> {
         return new LogSlabBlock(MALLORN_BEAM);
      });
      MIRK_OAK_LOG_SLAB = BLOCKS.register("mirk_oak_log_slab", () -> {
         return new StrippableLogSlabBlock(MIRK_OAK_LOG, () -> {
            return (Block)STRIPPED_MIRK_OAK_LOG_SLAB.get();
         });
      });
      MIRK_OAK_WOOD_SLAB = BLOCKS.register("mirk_oak_wood_slab", () -> {
         return new StrippableLogSlabBlock(MIRK_OAK_WOOD, () -> {
            return (Block)STRIPPED_MIRK_OAK_WOOD_SLAB.get();
         });
      });
      MIRK_OAK_WOOD_STAIRS = BLOCKS.register("mirk_oak_wood_stairs", () -> {
         return new StrippableLogStairsBlock(MIRK_OAK_WOOD, () -> {
            return (Block)STRIPPED_MIRK_OAK_WOOD_STAIRS.get();
         });
      });
      MIRK_OAK_BRANCH = BLOCKS.register("mirk_oak_branch", () -> {
         return new StrippableBranchBlock(MIRK_OAK_WOOD, () -> {
            return (Block)STRIPPED_MIRK_OAK_BRANCH.get();
         });
      });
      STRIPPED_MIRK_OAK_LOG_SLAB = BLOCKS.register("stripped_mirk_oak_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_MIRK_OAK_LOG);
      });
      STRIPPED_MIRK_OAK_WOOD_SLAB = BLOCKS.register("stripped_mirk_oak_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_MIRK_OAK_WOOD);
      });
      STRIPPED_MIRK_OAK_WOOD_STAIRS = BLOCKS.register("stripped_mirk_oak_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_MIRK_OAK_WOOD);
      });
      STRIPPED_MIRK_OAK_BRANCH = BLOCKS.register("stripped_mirk_oak_branch", () -> {
         return new BranchBlock(STRIPPED_MIRK_OAK_WOOD);
      });
      MIRK_OAK_BEAM_SLAB = BLOCKS.register("mirk_oak_beam_slab", () -> {
         return new LogSlabBlock(MIRK_OAK_BEAM);
      });
      CHARRED_LOG_SLAB = BLOCKS.register("charred_log_slab", () -> {
         return new StrippableLogSlabBlock(CHARRED_LOG, () -> {
            return (Block)STRIPPED_CHARRED_LOG_SLAB.get();
         });
      });
      CHARRED_WOOD_SLAB = BLOCKS.register("charred_wood_slab", () -> {
         return new StrippableLogSlabBlock(CHARRED_WOOD, () -> {
            return (Block)STRIPPED_CHARRED_WOOD_SLAB.get();
         });
      });
      CHARRED_WOOD_STAIRS = BLOCKS.register("charred_wood_stairs", () -> {
         return new StrippableLogStairsBlock(CHARRED_WOOD, () -> {
            return (Block)STRIPPED_CHARRED_WOOD_STAIRS.get();
         });
      });
      CHARRED_BRANCH = BLOCKS.register("charred_branch", () -> {
         return new StrippableBranchBlock(CHARRED_WOOD, () -> {
            return (Block)STRIPPED_CHARRED_BRANCH.get();
         });
      });
      STRIPPED_CHARRED_LOG_SLAB = BLOCKS.register("stripped_charred_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_CHARRED_LOG);
      });
      STRIPPED_CHARRED_WOOD_SLAB = BLOCKS.register("stripped_charred_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_CHARRED_WOOD);
      });
      STRIPPED_CHARRED_WOOD_STAIRS = BLOCKS.register("stripped_charred_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_CHARRED_WOOD);
      });
      STRIPPED_CHARRED_BRANCH = BLOCKS.register("stripped_charred_branch", () -> {
         return new BranchBlock(STRIPPED_CHARRED_WOOD);
      });
      CHARRED_BEAM_SLAB = BLOCKS.register("charred_beam_slab", () -> {
         return new LogSlabBlock(CHARRED_BEAM);
      });
      APPLE_LOG_SLAB = BLOCKS.register("apple_log_slab", () -> {
         return new StrippableLogSlabBlock(APPLE_LOG, () -> {
            return (Block)STRIPPED_APPLE_LOG_SLAB.get();
         });
      });
      APPLE_WOOD_SLAB = BLOCKS.register("apple_wood_slab", () -> {
         return new StrippableLogSlabBlock(APPLE_WOOD, () -> {
            return (Block)STRIPPED_APPLE_WOOD_SLAB.get();
         });
      });
      APPLE_WOOD_STAIRS = BLOCKS.register("apple_wood_stairs", () -> {
         return new StrippableLogStairsBlock(APPLE_WOOD, () -> {
            return (Block)STRIPPED_APPLE_WOOD_STAIRS.get();
         });
      });
      APPLE_BRANCH = BLOCKS.register("apple_branch", () -> {
         return new StrippableBranchBlock(APPLE_WOOD, () -> {
            return (Block)STRIPPED_APPLE_BRANCH.get();
         });
      });
      STRIPPED_APPLE_LOG_SLAB = BLOCKS.register("stripped_apple_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_APPLE_LOG);
      });
      STRIPPED_APPLE_WOOD_SLAB = BLOCKS.register("stripped_apple_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_APPLE_WOOD);
      });
      STRIPPED_APPLE_WOOD_STAIRS = BLOCKS.register("stripped_apple_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_APPLE_WOOD);
      });
      STRIPPED_APPLE_BRANCH = BLOCKS.register("stripped_apple_branch", () -> {
         return new BranchBlock(STRIPPED_APPLE_WOOD);
      });
      APPLE_BEAM_SLAB = BLOCKS.register("apple_beam_slab", () -> {
         return new LogSlabBlock(APPLE_BEAM);
      });
      PEAR_LOG_SLAB = BLOCKS.register("pear_log_slab", () -> {
         return new StrippableLogSlabBlock(PEAR_LOG, () -> {
            return (Block)STRIPPED_PEAR_LOG_SLAB.get();
         });
      });
      PEAR_WOOD_SLAB = BLOCKS.register("pear_wood_slab", () -> {
         return new StrippableLogSlabBlock(PEAR_WOOD, () -> {
            return (Block)STRIPPED_PEAR_WOOD_SLAB.get();
         });
      });
      PEAR_WOOD_STAIRS = BLOCKS.register("pear_wood_stairs", () -> {
         return new StrippableLogStairsBlock(PEAR_WOOD, () -> {
            return (Block)STRIPPED_PEAR_WOOD_STAIRS.get();
         });
      });
      PEAR_BRANCH = BLOCKS.register("pear_branch", () -> {
         return new StrippableBranchBlock(PEAR_WOOD, () -> {
            return (Block)STRIPPED_PEAR_BRANCH.get();
         });
      });
      STRIPPED_PEAR_LOG_SLAB = BLOCKS.register("stripped_pear_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_PEAR_LOG);
      });
      STRIPPED_PEAR_WOOD_SLAB = BLOCKS.register("stripped_pear_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_PEAR_WOOD);
      });
      STRIPPED_PEAR_WOOD_STAIRS = BLOCKS.register("stripped_pear_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_PEAR_WOOD);
      });
      STRIPPED_PEAR_BRANCH = BLOCKS.register("stripped_pear_branch", () -> {
         return new BranchBlock(STRIPPED_PEAR_WOOD);
      });
      PEAR_BEAM_SLAB = BLOCKS.register("pear_beam_slab", () -> {
         return new LogSlabBlock(PEAR_BEAM);
      });
      CHERRY_LOG_SLAB = BLOCKS.register("cherry_log_slab", () -> {
         return new StrippableLogSlabBlock(CHERRY_LOG, () -> {
            return (Block)STRIPPED_CHERRY_LOG_SLAB.get();
         });
      });
      CHERRY_WOOD_SLAB = BLOCKS.register("cherry_wood_slab", () -> {
         return new StrippableLogSlabBlock(CHERRY_WOOD, () -> {
            return (Block)STRIPPED_CHERRY_WOOD_SLAB.get();
         });
      });
      CHERRY_WOOD_STAIRS = BLOCKS.register("cherry_wood_stairs", () -> {
         return new StrippableLogStairsBlock(CHERRY_WOOD, () -> {
            return (Block)STRIPPED_CHERRY_WOOD_STAIRS.get();
         });
      });
      CHERRY_BRANCH = BLOCKS.register("cherry_branch", () -> {
         return new StrippableBranchBlock(CHERRY_WOOD, () -> {
            return (Block)STRIPPED_CHERRY_BRANCH.get();
         });
      });
      STRIPPED_CHERRY_LOG_SLAB = BLOCKS.register("stripped_cherry_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_CHERRY_LOG);
      });
      STRIPPED_CHERRY_WOOD_SLAB = BLOCKS.register("stripped_cherry_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_CHERRY_WOOD);
      });
      STRIPPED_CHERRY_WOOD_STAIRS = BLOCKS.register("stripped_cherry_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_CHERRY_WOOD);
      });
      STRIPPED_CHERRY_BRANCH = BLOCKS.register("stripped_cherry_branch", () -> {
         return new BranchBlock(STRIPPED_CHERRY_WOOD);
      });
      CHERRY_BEAM_SLAB = BLOCKS.register("cherry_beam_slab", () -> {
         return new LogSlabBlock(CHERRY_BEAM);
      });
      LEBETHRON_LOG_SLAB = BLOCKS.register("lebethron_log_slab", () -> {
         return new StrippableLogSlabBlock(LEBETHRON_LOG, () -> {
            return (Block)STRIPPED_LEBETHRON_LOG_SLAB.get();
         });
      });
      LEBETHRON_WOOD_SLAB = BLOCKS.register("lebethron_wood_slab", () -> {
         return new StrippableLogSlabBlock(LEBETHRON_WOOD, () -> {
            return (Block)STRIPPED_LEBETHRON_WOOD_SLAB.get();
         });
      });
      LEBETHRON_WOOD_STAIRS = BLOCKS.register("lebethron_wood_stairs", () -> {
         return new StrippableLogStairsBlock(LEBETHRON_WOOD, () -> {
            return (Block)STRIPPED_LEBETHRON_WOOD_STAIRS.get();
         });
      });
      LEBETHRON_BRANCH = BLOCKS.register("lebethron_branch", () -> {
         return new StrippableBranchBlock(LEBETHRON_WOOD, () -> {
            return (Block)STRIPPED_LEBETHRON_BRANCH.get();
         });
      });
      STRIPPED_LEBETHRON_LOG_SLAB = BLOCKS.register("stripped_lebethron_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_LEBETHRON_LOG);
      });
      STRIPPED_LEBETHRON_WOOD_SLAB = BLOCKS.register("stripped_lebethron_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_LEBETHRON_WOOD);
      });
      STRIPPED_LEBETHRON_WOOD_STAIRS = BLOCKS.register("stripped_lebethron_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_LEBETHRON_WOOD);
      });
      STRIPPED_LEBETHRON_BRANCH = BLOCKS.register("stripped_lebethron_branch", () -> {
         return new BranchBlock(STRIPPED_LEBETHRON_WOOD);
      });
      LEBETHRON_BEAM_SLAB = BLOCKS.register("lebethron_beam_slab", () -> {
         return new LogSlabBlock(LEBETHRON_BEAM);
      });
      BEECH_LOG_SLAB = BLOCKS.register("beech_log_slab", () -> {
         return new StrippableLogSlabBlock(BEECH_LOG, () -> {
            return (Block)STRIPPED_BEECH_LOG_SLAB.get();
         });
      });
      BEECH_WOOD_SLAB = BLOCKS.register("beech_wood_slab", () -> {
         return new StrippableLogSlabBlock(BEECH_WOOD, () -> {
            return (Block)STRIPPED_BEECH_WOOD_SLAB.get();
         });
      });
      BEECH_WOOD_STAIRS = BLOCKS.register("beech_wood_stairs", () -> {
         return new StrippableLogStairsBlock(BEECH_WOOD, () -> {
            return (Block)STRIPPED_BEECH_WOOD_STAIRS.get();
         });
      });
      BEECH_BRANCH = BLOCKS.register("beech_branch", () -> {
         return new StrippableBranchBlock(BEECH_WOOD, () -> {
            return (Block)STRIPPED_BEECH_BRANCH.get();
         });
      });
      STRIPPED_BEECH_LOG_SLAB = BLOCKS.register("stripped_beech_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_BEECH_LOG);
      });
      STRIPPED_BEECH_WOOD_SLAB = BLOCKS.register("stripped_beech_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_BEECH_WOOD);
      });
      STRIPPED_BEECH_WOOD_STAIRS = BLOCKS.register("stripped_beech_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_BEECH_WOOD);
      });
      STRIPPED_BEECH_BRANCH = BLOCKS.register("stripped_beech_branch", () -> {
         return new BranchBlock(STRIPPED_BEECH_WOOD);
      });
      BEECH_BEAM_SLAB = BLOCKS.register("beech_beam_slab", () -> {
         return new LogSlabBlock(BEECH_BEAM);
      });
      MAPLE_LOG_SLAB = BLOCKS.register("maple_log_slab", () -> {
         return new StrippableLogSlabBlock(MAPLE_LOG, () -> {
            return (Block)STRIPPED_MAPLE_LOG_SLAB.get();
         });
      });
      MAPLE_WOOD_SLAB = BLOCKS.register("maple_wood_slab", () -> {
         return new StrippableLogSlabBlock(MAPLE_WOOD, () -> {
            return (Block)STRIPPED_MAPLE_WOOD_SLAB.get();
         });
      });
      MAPLE_WOOD_STAIRS = BLOCKS.register("maple_wood_stairs", () -> {
         return new StrippableLogStairsBlock(MAPLE_WOOD, () -> {
            return (Block)STRIPPED_MAPLE_WOOD_STAIRS.get();
         });
      });
      MAPLE_BRANCH = BLOCKS.register("maple_branch", () -> {
         return new StrippableBranchBlock(MAPLE_WOOD, () -> {
            return (Block)STRIPPED_MAPLE_BRANCH.get();
         });
      });
      STRIPPED_MAPLE_LOG_SLAB = BLOCKS.register("stripped_maple_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_MAPLE_LOG);
      });
      STRIPPED_MAPLE_WOOD_SLAB = BLOCKS.register("stripped_maple_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_MAPLE_WOOD);
      });
      STRIPPED_MAPLE_WOOD_STAIRS = BLOCKS.register("stripped_maple_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_MAPLE_WOOD);
      });
      STRIPPED_MAPLE_BRANCH = BLOCKS.register("stripped_maple_branch", () -> {
         return new BranchBlock(STRIPPED_MAPLE_WOOD);
      });
      MAPLE_BEAM_SLAB = BLOCKS.register("maple_beam_slab", () -> {
         return new LogSlabBlock(MAPLE_BEAM);
      });
      ASPEN_LOG_SLAB = BLOCKS.register("aspen_log_slab", () -> {
         return new StrippableLogSlabBlock(ASPEN_LOG, () -> {
            return (Block)STRIPPED_ASPEN_LOG_SLAB.get();
         });
      });
      ASPEN_WOOD_SLAB = BLOCKS.register("aspen_wood_slab", () -> {
         return new StrippableLogSlabBlock(ASPEN_WOOD, () -> {
            return (Block)STRIPPED_ASPEN_WOOD_SLAB.get();
         });
      });
      ASPEN_WOOD_STAIRS = BLOCKS.register("aspen_wood_stairs", () -> {
         return new StrippableLogStairsBlock(ASPEN_WOOD, () -> {
            return (Block)STRIPPED_ASPEN_WOOD_STAIRS.get();
         });
      });
      ASPEN_BRANCH = BLOCKS.register("aspen_branch", () -> {
         return new StrippableBranchBlock(ASPEN_WOOD, () -> {
            return (Block)STRIPPED_ASPEN_BRANCH.get();
         });
      });
      STRIPPED_ASPEN_LOG_SLAB = BLOCKS.register("stripped_aspen_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_ASPEN_LOG);
      });
      STRIPPED_ASPEN_WOOD_SLAB = BLOCKS.register("stripped_aspen_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_ASPEN_WOOD);
      });
      STRIPPED_ASPEN_WOOD_STAIRS = BLOCKS.register("stripped_aspen_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_ASPEN_WOOD);
      });
      STRIPPED_ASPEN_BRANCH = BLOCKS.register("stripped_aspen_branch", () -> {
         return new BranchBlock(STRIPPED_ASPEN_WOOD);
      });
      ASPEN_BEAM_SLAB = BLOCKS.register("aspen_beam_slab", () -> {
         return new LogSlabBlock(ASPEN_BEAM);
      });
      LAIRELOSSE_LOG_SLAB = BLOCKS.register("lairelosse_log_slab", () -> {
         return new StrippableLogSlabBlock(LAIRELOSSE_LOG, () -> {
            return (Block)STRIPPED_LAIRELOSSE_LOG_SLAB.get();
         });
      });
      LAIRELOSSE_WOOD_SLAB = BLOCKS.register("lairelosse_wood_slab", () -> {
         return new StrippableLogSlabBlock(LAIRELOSSE_WOOD, () -> {
            return (Block)STRIPPED_LAIRELOSSE_WOOD_SLAB.get();
         });
      });
      LAIRELOSSE_WOOD_STAIRS = BLOCKS.register("lairelosse_wood_stairs", () -> {
         return new StrippableLogStairsBlock(LAIRELOSSE_WOOD, () -> {
            return (Block)STRIPPED_LAIRELOSSE_WOOD_STAIRS.get();
         });
      });
      LAIRELOSSE_BRANCH = BLOCKS.register("lairelosse_branch", () -> {
         return new StrippableBranchBlock(LAIRELOSSE_WOOD, () -> {
            return (Block)STRIPPED_LAIRELOSSE_BRANCH.get();
         });
      });
      STRIPPED_LAIRELOSSE_LOG_SLAB = BLOCKS.register("stripped_lairelosse_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_LAIRELOSSE_LOG);
      });
      STRIPPED_LAIRELOSSE_WOOD_SLAB = BLOCKS.register("stripped_lairelosse_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_LAIRELOSSE_WOOD);
      });
      STRIPPED_LAIRELOSSE_WOOD_STAIRS = BLOCKS.register("stripped_lairelosse_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_LAIRELOSSE_WOOD);
      });
      STRIPPED_LAIRELOSSE_BRANCH = BLOCKS.register("stripped_lairelosse_branch", () -> {
         return new BranchBlock(STRIPPED_LAIRELOSSE_WOOD);
      });
      LAIRELOSSE_BEAM_SLAB = BLOCKS.register("lairelosse_beam_slab", () -> {
         return new LogSlabBlock(LAIRELOSSE_BEAM);
      });
      CEDAR_LOG_SLAB = BLOCKS.register("cedar_log_slab", () -> {
         return new StrippableLogSlabBlock(CEDAR_LOG, () -> {
            return (Block)STRIPPED_CEDAR_LOG_SLAB.get();
         });
      });
      CEDAR_WOOD_SLAB = BLOCKS.register("cedar_wood_slab", () -> {
         return new StrippableLogSlabBlock(CEDAR_WOOD, () -> {
            return (Block)STRIPPED_CEDAR_WOOD_SLAB.get();
         });
      });
      CEDAR_WOOD_STAIRS = BLOCKS.register("cedar_wood_stairs", () -> {
         return new StrippableLogStairsBlock(CEDAR_WOOD, () -> {
            return (Block)STRIPPED_CEDAR_WOOD_STAIRS.get();
         });
      });
      CEDAR_BRANCH = BLOCKS.register("cedar_branch", () -> {
         return new StrippableBranchBlock(CEDAR_WOOD, () -> {
            return (Block)STRIPPED_CEDAR_BRANCH.get();
         });
      });
      STRIPPED_CEDAR_LOG_SLAB = BLOCKS.register("stripped_cedar_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_CEDAR_LOG);
      });
      STRIPPED_CEDAR_WOOD_SLAB = BLOCKS.register("stripped_cedar_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_CEDAR_WOOD);
      });
      STRIPPED_CEDAR_WOOD_STAIRS = BLOCKS.register("stripped_cedar_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_CEDAR_WOOD);
      });
      STRIPPED_CEDAR_BRANCH = BLOCKS.register("stripped_cedar_branch", () -> {
         return new BranchBlock(STRIPPED_CEDAR_WOOD);
      });
      CEDAR_BEAM_SLAB = BLOCKS.register("cedar_beam_slab", () -> {
         return new LogSlabBlock(CEDAR_BEAM);
      });
      FIR_LOG_SLAB = BLOCKS.register("fir_log_slab", () -> {
         return new StrippableLogSlabBlock(FIR_LOG, () -> {
            return (Block)STRIPPED_FIR_LOG_SLAB.get();
         });
      });
      FIR_WOOD_SLAB = BLOCKS.register("fir_wood_slab", () -> {
         return new StrippableLogSlabBlock(FIR_WOOD, () -> {
            return (Block)STRIPPED_FIR_WOOD_SLAB.get();
         });
      });
      FIR_WOOD_STAIRS = BLOCKS.register("fir_wood_stairs", () -> {
         return new StrippableLogStairsBlock(FIR_WOOD, () -> {
            return (Block)STRIPPED_FIR_WOOD_STAIRS.get();
         });
      });
      FIR_BRANCH = BLOCKS.register("fir_branch", () -> {
         return new StrippableBranchBlock(FIR_WOOD, () -> {
            return (Block)STRIPPED_FIR_BRANCH.get();
         });
      });
      STRIPPED_FIR_LOG_SLAB = BLOCKS.register("stripped_fir_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_FIR_LOG);
      });
      STRIPPED_FIR_WOOD_SLAB = BLOCKS.register("stripped_fir_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_FIR_WOOD);
      });
      STRIPPED_FIR_WOOD_STAIRS = BLOCKS.register("stripped_fir_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_FIR_WOOD);
      });
      STRIPPED_FIR_BRANCH = BLOCKS.register("stripped_fir_branch", () -> {
         return new BranchBlock(STRIPPED_FIR_WOOD);
      });
      FIR_BEAM_SLAB = BLOCKS.register("fir_beam_slab", () -> {
         return new LogSlabBlock(FIR_BEAM);
      });
      LARCH_LOG_SLAB = BLOCKS.register("larch_log_slab", () -> {
         return new StrippableLogSlabBlock(LARCH_LOG, () -> {
            return (Block)STRIPPED_LARCH_LOG_SLAB.get();
         });
      });
      LARCH_WOOD_SLAB = BLOCKS.register("larch_wood_slab", () -> {
         return new StrippableLogSlabBlock(LARCH_WOOD, () -> {
            return (Block)STRIPPED_LARCH_WOOD_SLAB.get();
         });
      });
      LARCH_WOOD_STAIRS = BLOCKS.register("larch_wood_stairs", () -> {
         return new StrippableLogStairsBlock(LARCH_WOOD, () -> {
            return (Block)STRIPPED_LARCH_WOOD_STAIRS.get();
         });
      });
      LARCH_BRANCH = BLOCKS.register("larch_branch", () -> {
         return new StrippableBranchBlock(LARCH_WOOD, () -> {
            return (Block)STRIPPED_LARCH_BRANCH.get();
         });
      });
      STRIPPED_LARCH_LOG_SLAB = BLOCKS.register("stripped_larch_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_LARCH_LOG);
      });
      STRIPPED_LARCH_WOOD_SLAB = BLOCKS.register("stripped_larch_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_LARCH_WOOD);
      });
      STRIPPED_LARCH_WOOD_STAIRS = BLOCKS.register("stripped_larch_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_LARCH_WOOD);
      });
      STRIPPED_LARCH_BRANCH = BLOCKS.register("stripped_larch_branch", () -> {
         return new BranchBlock(STRIPPED_LARCH_WOOD);
      });
      LARCH_BEAM_SLAB = BLOCKS.register("larch_beam_slab", () -> {
         return new LogSlabBlock(LARCH_BEAM);
      });
      HOLLY_LOG_SLAB = BLOCKS.register("holly_log_slab", () -> {
         return new StrippableLogSlabBlock(HOLLY_LOG, () -> {
            return (Block)STRIPPED_HOLLY_LOG_SLAB.get();
         });
      });
      HOLLY_WOOD_SLAB = BLOCKS.register("holly_wood_slab", () -> {
         return new StrippableLogSlabBlock(HOLLY_WOOD, () -> {
            return (Block)STRIPPED_HOLLY_WOOD_SLAB.get();
         });
      });
      HOLLY_WOOD_STAIRS = BLOCKS.register("holly_wood_stairs", () -> {
         return new StrippableLogStairsBlock(HOLLY_WOOD, () -> {
            return (Block)STRIPPED_HOLLY_WOOD_STAIRS.get();
         });
      });
      HOLLY_BRANCH = BLOCKS.register("holly_branch", () -> {
         return new StrippableBranchBlock(HOLLY_WOOD, () -> {
            return (Block)STRIPPED_HOLLY_BRANCH.get();
         });
      });
      STRIPPED_HOLLY_LOG_SLAB = BLOCKS.register("stripped_holly_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_HOLLY_LOG);
      });
      STRIPPED_HOLLY_WOOD_SLAB = BLOCKS.register("stripped_holly_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_HOLLY_WOOD);
      });
      STRIPPED_HOLLY_WOOD_STAIRS = BLOCKS.register("stripped_holly_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_HOLLY_WOOD);
      });
      STRIPPED_HOLLY_BRANCH = BLOCKS.register("stripped_holly_branch", () -> {
         return new BranchBlock(STRIPPED_HOLLY_WOOD);
      });
      HOLLY_BEAM_SLAB = BLOCKS.register("holly_beam_slab", () -> {
         return new LogSlabBlock(HOLLY_BEAM);
      });
      GREEN_OAK_LOG_SLAB = BLOCKS.register("green_oak_log_slab", () -> {
         return new StrippableLogSlabBlock(GREEN_OAK_LOG, () -> {
            return (Block)STRIPPED_GREEN_OAK_LOG_SLAB.get();
         });
      });
      GREEN_OAK_WOOD_SLAB = BLOCKS.register("green_oak_wood_slab", () -> {
         return new StrippableLogSlabBlock(GREEN_OAK_WOOD, () -> {
            return (Block)STRIPPED_GREEN_OAK_WOOD_SLAB.get();
         });
      });
      GREEN_OAK_WOOD_STAIRS = BLOCKS.register("green_oak_wood_stairs", () -> {
         return new StrippableLogStairsBlock(GREEN_OAK_WOOD, () -> {
            return (Block)STRIPPED_GREEN_OAK_WOOD_STAIRS.get();
         });
      });
      GREEN_OAK_BRANCH = BLOCKS.register("green_oak_branch", () -> {
         return new StrippableBranchBlock(GREEN_OAK_WOOD, () -> {
            return (Block)STRIPPED_GREEN_OAK_BRANCH.get();
         });
      });
      STRIPPED_GREEN_OAK_LOG_SLAB = BLOCKS.register("stripped_green_oak_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_GREEN_OAK_LOG);
      });
      STRIPPED_GREEN_OAK_WOOD_SLAB = BLOCKS.register("stripped_green_oak_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_GREEN_OAK_WOOD);
      });
      STRIPPED_GREEN_OAK_WOOD_STAIRS = BLOCKS.register("stripped_green_oak_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_GREEN_OAK_WOOD);
      });
      STRIPPED_GREEN_OAK_BRANCH = BLOCKS.register("stripped_green_oak_branch", () -> {
         return new BranchBlock(STRIPPED_GREEN_OAK_WOOD);
      });
      GREEN_OAK_BEAM_SLAB = BLOCKS.register("green_oak_beam_slab", () -> {
         return new LogSlabBlock(GREEN_OAK_BEAM);
      });
      CYPRESS_LOG_SLAB = BLOCKS.register("cypress_log_slab", () -> {
         return new StrippableLogSlabBlock(CYPRESS_LOG, () -> {
            return (Block)STRIPPED_CYPRESS_LOG_SLAB.get();
         });
      });
      CYPRESS_WOOD_SLAB = BLOCKS.register("cypress_wood_slab", () -> {
         return new StrippableLogSlabBlock(CYPRESS_WOOD, () -> {
            return (Block)STRIPPED_CYPRESS_WOOD_SLAB.get();
         });
      });
      CYPRESS_WOOD_STAIRS = BLOCKS.register("cypress_wood_stairs", () -> {
         return new StrippableLogStairsBlock(CYPRESS_WOOD, () -> {
            return (Block)STRIPPED_CYPRESS_WOOD_STAIRS.get();
         });
      });
      CYPRESS_BRANCH = BLOCKS.register("cypress_branch", () -> {
         return new StrippableBranchBlock(CYPRESS_WOOD, () -> {
            return (Block)STRIPPED_CYPRESS_BRANCH.get();
         });
      });
      STRIPPED_CYPRESS_LOG_SLAB = BLOCKS.register("stripped_cypress_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_CYPRESS_LOG);
      });
      STRIPPED_CYPRESS_WOOD_SLAB = BLOCKS.register("stripped_cypress_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_CYPRESS_WOOD);
      });
      STRIPPED_CYPRESS_WOOD_STAIRS = BLOCKS.register("stripped_cypress_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_CYPRESS_WOOD);
      });
      STRIPPED_CYPRESS_BRANCH = BLOCKS.register("stripped_cypress_branch", () -> {
         return new BranchBlock(STRIPPED_CYPRESS_WOOD);
      });
      CYPRESS_BEAM_SLAB = BLOCKS.register("cypress_beam_slab", () -> {
         return new LogSlabBlock(CYPRESS_BEAM);
      });
      ROTTEN_LOG_SLAB = BLOCKS.register("rotten_log_slab", () -> {
         return new StrippableRottenLogSlabBlock(ROTTEN_LOG, () -> {
            return (Block)STRIPPED_ROTTEN_LOG_SLAB.get();
         });
      });
      ROTTEN_WOOD_SLAB = BLOCKS.register("rotten_wood_slab", () -> {
         return new StrippableLogSlabBlock(ROTTEN_WOOD, () -> {
            return (Block)STRIPPED_ROTTEN_WOOD_SLAB.get();
         });
      });
      ROTTEN_WOOD_STAIRS = BLOCKS.register("rotten_wood_stairs", () -> {
         return new StrippableLogStairsBlock(ROTTEN_WOOD, () -> {
            return (Block)STRIPPED_ROTTEN_WOOD_STAIRS.get();
         });
      });
      ROTTEN_BRANCH = BLOCKS.register("rotten_branch", () -> {
         return new StrippableBranchBlock(ROTTEN_WOOD, () -> {
            return (Block)STRIPPED_ROTTEN_BRANCH.get();
         });
      });
      STRIPPED_ROTTEN_LOG_SLAB = BLOCKS.register("stripped_rotten_log_slab", () -> {
         return new RottenLogSlabBlock(STRIPPED_ROTTEN_LOG);
      });
      STRIPPED_ROTTEN_WOOD_SLAB = BLOCKS.register("stripped_rotten_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_ROTTEN_WOOD);
      });
      STRIPPED_ROTTEN_WOOD_STAIRS = BLOCKS.register("stripped_rotten_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_ROTTEN_WOOD);
      });
      STRIPPED_ROTTEN_BRANCH = BLOCKS.register("stripped_rotten_branch", () -> {
         return new BranchBlock(STRIPPED_ROTTEN_WOOD);
      });
      ROTTEN_BEAM_SLAB = BLOCKS.register("rotten_beam_slab", () -> {
         return new RottenWoodBeamSlabBlock(ROTTEN_BEAM);
      });
      LAPIS_TRIMMED_BLUE_BRICK = BLOCKS.register("lapis_trimmed_blue_brick", () -> {
         return new LOTRStoneBlock(BLUE_BRICK);
      });
      DWARVEN_TORCH = BLOCKS.register("dwarven_torch", () -> {
         return new LOTRTorchBlock(14);
      });
      DWARVEN_WALL_TORCH = BLOCKS.register("dwarven_wall_torch", () -> {
         return new LOTRWallTorchBlock(DWARVEN_TORCH);
      });
      SNOW_BRICK = BLOCKS.register("snow_brick", () -> {
         return new SnowBrickBlock();
      });
      SNOW_BRICK_SLAB = BLOCKS.register("snow_brick_slab", () -> {
         return new AxialSlabBlock(SNOW_BRICK);
      });
      SNOW_BRICK_STAIRS = BLOCKS.register("snow_brick_stairs", () -> {
         return new LOTRStairsBlock(SNOW_BRICK);
      });
      SNOW_BRICK_WALL = BLOCKS.register("snow_brick_wall", () -> {
         return new LOTRWallBlock(SNOW_BRICK);
      });
      LOSSOTH_CRAFTING_TABLE = BLOCKS.register("lossoth_crafting_table", () -> {
         return new FactionCraftingTableBlock.Lossoth(FIR_PLANKS);
      });
      ICE_BRICK = BLOCKS.register("ice_brick", () -> {
         return new IceBrickBlock();
      });
      ICE_BRICK_SLAB = BLOCKS.register("ice_brick_slab", () -> {
         return new AxialSlabBlock(ICE_BRICK);
      });
      ICE_BRICK_STAIRS = BLOCKS.register("ice_brick_stairs", () -> {
         return new LOTRStairsBlock(ICE_BRICK);
      });
      ICE_BRICK_WALL = BLOCKS.register("ice_brick_wall", () -> {
         return new LOTRWallBlock(ICE_BRICK);
      });
      SNOW_PATH = BLOCKS.register("snow_path", () -> {
         return new SnowPathBlock();
      });
      FUR_BUNDLE = BLOCKS.register("fur_bundle", () -> {
         return new BundleBlock(MaterialColor.field_151650_B);
      });
      LEATHER_BUNDLE = BLOCKS.register("leather_bundle", () -> {
         return new BundleBlock(MaterialColor.field_151676_q);
      });
      BLUBBER_TORCH = BLOCKS.register("blubber_torch", () -> {
         return new LOTRTorchBlock(12);
      });
      BLUBBER_WALL_TORCH = BLOCKS.register("blubber_wall_torch", () -> {
         return new LOTRWallTorchBlock(BLUBBER_TORCH);
      });
      CUSTOM_WAYPOINT_MARKER = BLOCKS.register("custom_waypoint_marker", () -> {
         return new CustomWaypointMarkerBlock();
      });
      DUNLENDING_CRAFTING_TABLE = BLOCKS.register("dunlending_crafting_table", () -> {
         return new FactionCraftingTableBlock.Dunlending(() -> {
            return Blocks.field_150347_e;
         });
      });
      CARVED_CHALK_BRICK = BLOCKS.register("carved_chalk_brick", () -> {
         return new LOTRStoneBlock(CHALK_BRICK);
      });
      GONDOR_BEACON = BLOCKS.register("gondor_beacon", () -> {
         return new GondorBeaconBlock();
      });
      FALLEN_OAK_LEAVES = BLOCKS.register("fallen_oak_leaves", () -> {
         return new FallenLeavesBlock(Blocks.field_196642_W);
      });
      FALLEN_SPRUCE_LEAVES = BLOCKS.register("fallen_spruce_leaves", () -> {
         return new FallenLeavesBlock(Blocks.field_196645_X);
      });
      FALLEN_BIRCH_LEAVES = BLOCKS.register("fallen_birch_leaves", () -> {
         return new FallenLeavesBlock(Blocks.field_196647_Y);
      });
      FALLEN_JUNGLE_LEAVES = BLOCKS.register("fallen_jungle_leaves", () -> {
         return new FallenLeavesBlock(Blocks.field_196648_Z);
      });
      FALLEN_ACACIA_LEAVES = BLOCKS.register("fallen_acacia_leaves", () -> {
         return new FallenLeavesBlock(Blocks.field_196572_aa);
      });
      FALLEN_DARK_OAK_LEAVES = BLOCKS.register("fallen_dark_oak_leaves", () -> {
         return new FallenLeavesBlock(Blocks.field_196574_ab);
      });
      FALLEN_PINE_LEAVES = BLOCKS.register("fallen_pine_leaves", () -> {
         return new FallenLeavesBlock(PINE_LEAVES);
      });
      FALLEN_MALLORN_LEAVES = BLOCKS.register("fallen_mallorn_leaves", () -> {
         return new FallenLeavesBlock(MALLORN_LEAVES);
      });
      FALLEN_MIRK_OAK_LEAVES = BLOCKS.register("fallen_mirk_oak_leaves", () -> {
         return new FallenLeavesBlock(MIRK_OAK_LEAVES);
      });
      FALLEN_APPLE_LEAVES = BLOCKS.register("fallen_apple_leaves", () -> {
         return new FallenLeavesBlock(APPLE_LEAVES);
      });
      FALLEN_PEAR_LEAVES = BLOCKS.register("fallen_pear_leaves", () -> {
         return new FallenLeavesBlock(PEAR_LEAVES);
      });
      FALLEN_CHERRY_LEAVES = BLOCKS.register("fallen_cherry_leaves", () -> {
         return new FallenLeavesBlock(CHERRY_LEAVES);
      });
      FALLEN_LEBETHRON_LEAVES = BLOCKS.register("fallen_lebethron_leaves", () -> {
         return new FallenLeavesBlock(LEBETHRON_LEAVES);
      });
      FALLEN_BEECH_LEAVES = BLOCKS.register("fallen_beech_leaves", () -> {
         return new FallenLeavesBlock(BEECH_LEAVES);
      });
      FALLEN_MAPLE_LEAVES = BLOCKS.register("fallen_maple_leaves", () -> {
         return new FallenLeavesBlock(MAPLE_LEAVES);
      });
      FALLEN_ASPEN_LEAVES = BLOCKS.register("fallen_aspen_leaves", () -> {
         return new FallenLeavesBlock(ASPEN_LEAVES);
      });
      FALLEN_LAIRELOSSE_LEAVES = BLOCKS.register("fallen_lairelosse_leaves", () -> {
         return new FallenLeavesBlock(LAIRELOSSE_LEAVES);
      });
      FALLEN_CEDAR_LEAVES = BLOCKS.register("fallen_cedar_leaves", () -> {
         return new FallenLeavesBlock(CEDAR_LEAVES);
      });
      FALLEN_FIR_LEAVES = BLOCKS.register("fallen_fir_leaves", () -> {
         return new FallenLeavesBlock(FIR_LEAVES);
      });
      FALLEN_LARCH_LEAVES = BLOCKS.register("fallen_larch_leaves", () -> {
         return new FallenLeavesBlock(LARCH_LEAVES);
      });
      FALLEN_HOLLY_LEAVES = BLOCKS.register("fallen_holly_leaves", () -> {
         return new FallenLeavesBlock(HOLLY_LEAVES);
      });
      FALLEN_GREEN_OAK_LEAVES = BLOCKS.register("fallen_green_oak_leaves", () -> {
         return new FallenLeavesBlock(GREEN_OAK_LEAVES);
      });
      FALLEN_RED_OAK_LEAVES = BLOCKS.register("fallen_red_oak_leaves", () -> {
         return new FallenLeavesBlock(RED_OAK_LEAVES);
      });
      FALLEN_CYPRESS_LEAVES = BLOCKS.register("fallen_cypress_leaves", () -> {
         return new FallenLeavesBlock(CYPRESS_LEAVES);
      });
      THATCH_FLOORING = BLOCKS.register("thatch_flooring", () -> {
         return new ThatchFloorBlock();
      });
      WOODEN_MUG = BLOCKS.register("wooden_mug", () -> {
         return VesselDrinkBlock.makeWoodenMug();
      });
      CERAMIC_MUG = BLOCKS.register("ceramic_mug", () -> {
         return VesselDrinkBlock.makeCeramicMug();
      });
      GOLDEN_GOBLET = BLOCKS.register("golden_goblet", () -> {
         return VesselDrinkBlock.makeMetalGoblet();
      });
      SILVER_GOBLET = BLOCKS.register("silver_goblet", () -> {
         return VesselDrinkBlock.makeMetalGoblet();
      });
      COPPER_GOBLET = BLOCKS.register("copper_goblet", () -> {
         return VesselDrinkBlock.makeMetalGoblet();
      });
      WOODEN_CUP = BLOCKS.register("wooden_cup", () -> {
         return VesselDrinkBlock.makeWoodenGoblet();
      });
      ALE_HORN = BLOCKS.register("ale_horn", () -> {
         return VesselDrinkBlock.makeAleHorn();
      });
      GOLDEN_ALE_HORN = BLOCKS.register("golden_ale_horn", () -> {
         return VesselDrinkBlock.makeAleHorn();
      });
      MORDOR_BASALT_BRICK = BLOCKS.register("mordor_basalt_brick", () -> {
         return new LOTRStoneBlock(() -> {
            return Blocks.field_235337_cO_;
         });
      });
      MORDOR_BASALT_BRICK_SLAB = BLOCKS.register("mordor_basalt_brick_slab", () -> {
         return new AxialSlabBlock(MORDOR_BASALT_BRICK);
      });
      MORDOR_BASALT_BRICK_STAIRS = BLOCKS.register("mordor_basalt_brick_stairs", () -> {
         return new LOTRStairsBlock(MORDOR_BASALT_BRICK);
      });
      MORDOR_BASALT_BRICK_WALL = BLOCKS.register("mordor_basalt_brick_wall", () -> {
         return new LOTRWallBlock(MORDOR_BASALT_BRICK);
      });
      CARVED_MORDOR_BASALT_BRICK = BLOCKS.register("carved_mordor_basalt_brick", () -> {
         return new LOTRStoneBlock(MORDOR_BASALT_BRICK);
      });
      SOUL_FIRE_HEARTH_BLOCK = BLOCKS.register("soul_fire_hearth_block", () -> {
         return new SoulFireHearthBlock();
      });
      WOODEN_GATE = BLOCKS.register("wooden_gate", () -> {
         return GateBlock.makeWooden();
      });
      WOODEN_PORTCULLIS = BLOCKS.register("wooden_portcullis", () -> {
         return GateBlock.makeWoodenCutout();
      });
      IRON_PORTCULLIS = BLOCKS.register("iron_portcullis", () -> {
         return GateBlock.makeMetalCutout();
      });
      BRONZE_PORTCULLIS = BLOCKS.register("bronze_portcullis", () -> {
         return GateBlock.makeMetalCutout();
      });
      SILVER_GATE = BLOCKS.register("silver_gate", () -> {
         return GateBlock.makeMetal();
      });
      GOLDEN_GATE = BLOCKS.register("golden_gate", () -> {
         return GateBlock.makeMetal();
      });
      MITHRIL_GATE = BLOCKS.register("mithril_gate", () -> {
         return GateBlock.makeMetal();
      });
      DWARVEN_GATE = BLOCKS.register("dwarven_gate", () -> {
         return GateBlock.makeStone();
      });
      ORC_GATE = BLOCKS.register("orc_gate", () -> {
         return GateBlock.makeMetal();
      });
      GONDOR_GATE = BLOCKS.register("gondor_gate", () -> {
         return GateBlock.makeWooden();
      });
      DOL_AMROTH_GATE = BLOCKS.register("dol_amroth_gate", () -> {
         return GateBlock.makeWooden();
      });
      ROHAN_GATE = BLOCKS.register("rohan_gate", () -> {
         return GateBlock.makeWooden();
      });
      HIGH_ELVEN_GATE = BLOCKS.register("high_elven_gate", () -> {
         return GateBlock.makeWooden();
      });
      GALADHRIM_GATE = BLOCKS.register("galadhrim_gate", () -> {
         return GateBlock.makeWooden();
      });
      WOOD_ELVEN_GATE = BLOCKS.register("wood_elven_gate", () -> {
         return GateBlock.makeWooden();
      });
      HARAD_GATE = BLOCKS.register("harad_gate", () -> {
         return GateBlock.makeWooden();
      });
      URUK_GATE = BLOCKS.register("uruk_gate", () -> {
         return GateBlock.makeMetal();
      });
      WICKER_FENCE = BLOCKS.register("wicker_fence", () -> {
         return new WickerFenceBlock();
      });
      RED_HOBBIT_GATE = BLOCKS.register("red_hobbit_gate", () -> {
         return GateBlock.makeWooden();
      });
      YELLOW_HOBBIT_GATE = BLOCKS.register("yellow_hobbit_gate", () -> {
         return GateBlock.makeWooden();
      });
      GREEN_HOBBIT_GATE = BLOCKS.register("green_hobbit_gate", () -> {
         return GateBlock.makeWooden();
      });
      BLUE_HOBBIT_GATE = BLOCKS.register("blue_hobbit_gate", () -> {
         return GateBlock.makeWooden();
      });
      MORGUL_FLOWER = BLOCKS.register("morgul_flower", () -> {
         return new MorgulFlowerBlock();
      });
      POTTED_MORGUL_FLOWER = BLOCKS.register("potted_morgul_flower", () -> {
         return new LOTRPottedPlantBlock(MORGUL_FLOWER);
      });
      REED_BALE = BLOCKS.register("reed_bale", () -> {
         return new DirectionalBaleBlock(MaterialColor.field_193574_Z);
      });
      FLAX_BALE = BLOCKS.register("flax_bale", () -> {
         return new DirectionalBaleBlock(MaterialColor.field_193566_R);
      });
      WICKER_BARS = BLOCKS.register("wicker_bars", () -> {
         return new WickerBarsBlock();
      });
      WICKER_FENCE_GATE = BLOCKS.register("wicker_fence_gate", () -> {
         return new WickerFenceGateBlock();
      });
      CULUMALDA_LOG = BLOCKS.register("culumalda_log", () -> {
         return new StrippableLogBlock(MaterialColor.field_151654_J, MaterialColor.field_151650_B, () -> {
            return (Block)STRIPPED_CULUMALDA_LOG.get();
         });
      });
      CULUMALDA_WOOD = BLOCKS.register("culumalda_wood", () -> {
         return new StrippableWoodBlock(CULUMALDA_LOG, () -> {
            return (Block)STRIPPED_CULUMALDA_WOOD.get();
         });
      });
      CULUMALDA_PLANKS = BLOCKS.register("culumalda_planks", () -> {
         return new LOTRPlanksBlock(MaterialColor.field_151654_J);
      });
      CULUMALDA_LEAVES = BLOCKS.register("culumalda_leaves", () -> {
         return (new LOTRLeavesBlock()).setFallingParticle(LOTRParticles.CULUMALDA_POLLEN, 60);
      });
      CULUMALDA_SAPLING = BLOCKS.register("culumalda_sapling", () -> {
         return new LOTRSaplingBlock(new CulumaldaTree());
      });
      POTTED_CULUMALDA_SAPLING = BLOCKS.register("potted_culumalda_sapling", () -> {
         return new LOTRPottedPlantBlock(CULUMALDA_SAPLING);
      });
      CULUMALDA_SLAB = BLOCKS.register("culumalda_slab", () -> {
         return new AxialSlabBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_STAIRS = BLOCKS.register("culumalda_stairs", () -> {
         return new LOTRStairsBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_FENCE = BLOCKS.register("culumalda_fence", () -> {
         return new LOTRFenceBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_FENCE_GATE = BLOCKS.register("culumalda_fence_gate", () -> {
         return new LOTRFenceGateBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_DOOR = BLOCKS.register("culumalda_door", () -> {
         return new LOTRDoorBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_TRAPDOOR = BLOCKS.register("culumalda_trapdoor", () -> {
         return new LOTRTrapdoorBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_PRESSURE_PLATE = BLOCKS.register("culumalda_pressure_plate", () -> {
         return new LOTRWoodPressurePlateBlock(CULUMALDA_PLANKS);
      });
      CULUMALDA_BUTTON = BLOCKS.register("culumalda_button", () -> {
         return new LOTRWoodButtonBlock();
      });
      STRIPPED_CULUMALDA_LOG = BLOCKS.register("stripped_culumalda_log", () -> {
         return new LOTRStrippedLogBlock(CULUMALDA_LOG);
      });
      STRIPPED_CULUMALDA_WOOD = BLOCKS.register("stripped_culumalda_wood", () -> {
         return new LOTRStrippedWoodBlock(STRIPPED_CULUMALDA_LOG);
      });
      CULUMALDA_BEAM = BLOCKS.register("culumalda_beam", () -> {
         return new WoodBeamBlock(CULUMALDA_LOG);
      });
      CULUMALDA_SIGN = BLOCKS.register("culumalda_sign", () -> {
         return new LOTRStandingSignBlock(CULUMALDA_PLANKS, LOTRSignTypes.CULUMALDA);
      });
      CULUMALDA_WALL_SIGN = BLOCKS.register("culumalda_wall_sign", () -> {
         return new LOTRWallSignBlock(CULUMALDA_SIGN);
      });
      CULUMALDA_LOG_SLAB = BLOCKS.register("culumalda_log_slab", () -> {
         return new StrippableLogSlabBlock(CULUMALDA_LOG, () -> {
            return (Block)STRIPPED_CULUMALDA_LOG_SLAB.get();
         });
      });
      CULUMALDA_WOOD_SLAB = BLOCKS.register("culumalda_wood_slab", () -> {
         return new StrippableLogSlabBlock(CULUMALDA_WOOD, () -> {
            return (Block)STRIPPED_CULUMALDA_WOOD_SLAB.get();
         });
      });
      CULUMALDA_WOOD_STAIRS = BLOCKS.register("culumalda_wood_stairs", () -> {
         return new StrippableLogStairsBlock(CULUMALDA_WOOD, () -> {
            return (Block)STRIPPED_CULUMALDA_WOOD_STAIRS.get();
         });
      });
      CULUMALDA_BRANCH = BLOCKS.register("culumalda_branch", () -> {
         return new StrippableBranchBlock(CULUMALDA_WOOD, () -> {
            return (Block)STRIPPED_CULUMALDA_BRANCH.get();
         });
      });
      STRIPPED_CULUMALDA_LOG_SLAB = BLOCKS.register("stripped_culumalda_log_slab", () -> {
         return new LogSlabBlock(STRIPPED_CULUMALDA_LOG);
      });
      STRIPPED_CULUMALDA_WOOD_SLAB = BLOCKS.register("stripped_culumalda_wood_slab", () -> {
         return new LogSlabBlock(STRIPPED_CULUMALDA_WOOD);
      });
      STRIPPED_CULUMALDA_WOOD_STAIRS = BLOCKS.register("stripped_culumalda_wood_stairs", () -> {
         return new LogStairsBlock(STRIPPED_CULUMALDA_WOOD);
      });
      STRIPPED_CULUMALDA_BRANCH = BLOCKS.register("stripped_culumalda_branch", () -> {
         return new BranchBlock(STRIPPED_CULUMALDA_WOOD);
      });
      CULUMALDA_BEAM_SLAB = BLOCKS.register("culumalda_beam_slab", () -> {
         return new LogSlabBlock(CULUMALDA_BEAM);
      });
      FALLEN_CULUMALDA_LEAVES = BLOCKS.register("fallen_culumalda_leaves", () -> {
         return new FallenLeavesBlock(CULUMALDA_LEAVES);
      });
      BREE_CRAFTING_TABLE = BLOCKS.register("bree_crafting_table", () -> {
         return new FactionCraftingTableBlock.Bree(BEECH_PLANKS);
      });
      PALANTIR = BLOCKS.register("palantir", () -> {
         return new PalantirBlock();
      });
      MORDOR_DIRT_PATH = BLOCKS.register("mordor_dirt_path", () -> {
         return new MordorDirtPathBlock(MaterialColor.field_193573_Y);
      });
      CHALK_PATH = BLOCKS.register("chalk_path", () -> {
         return new ChalkPathBlock(MaterialColor.field_151677_p, 0.5F, 0.5F);
      });
      OAK_VERTICAL_SLAB = makeVerticalVanillaSlab("oak", Blocks.field_196622_bq);
      SPRUCE_VERTICAL_SLAB = makeVerticalVanillaSlab("spruce", Blocks.field_196624_br);
      BIRCH_VERTICAL_SLAB = makeVerticalVanillaSlab("birch", Blocks.field_196627_bs);
      JUNGLE_VERTICAL_SLAB = makeVerticalVanillaSlab("jungle", Blocks.field_196630_bt);
      ACACIA_VERTICAL_SLAB = makeVerticalVanillaSlab("acacia", Blocks.field_196632_bu);
      DARK_OAK_VERTICAL_SLAB = makeVerticalVanillaSlab("dark_oak", Blocks.field_196635_bv);
      STONE_VERTICAL_SLAB = makeVerticalVanillaSlab("stone", Blocks.field_150333_U);
      SMOOTH_STONE_VERTICAL_SLAB = makeVerticalVanillaSlab("smooth_stone", Blocks.field_222401_hJ);
      SANDSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("sandstone", Blocks.field_196640_bx);
      CUT_SANDSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("cut_sandstone", Blocks.field_222402_hL);
      PETRIFIED_OAK_VERTICAL_SLAB = makeVerticalVanillaSlab("petrified_oak", Blocks.field_196643_by);
      COBBLESTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("cobblestone", Blocks.field_196646_bz);
      BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("brick", Blocks.field_196571_bA);
      STONE_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("stone_brick", Blocks.field_196573_bB);
      NETHER_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("nether_brick", Blocks.field_196575_bC);
      QUARTZ_VERTICAL_SLAB = makeVerticalVanillaSlab("quartz", Blocks.field_196576_bD);
      RED_SANDSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("red_sandstone", Blocks.field_196578_bE);
      CUT_RED_SANDSTONE_SLAB = makeVerticalVanillaSlab("cut_red_sandstone", Blocks.field_222403_hT);
      PRISMARINE_VERTICAL_SLAB = makeVerticalVanillaSlab("prismarine", Blocks.field_203200_bP);
      PRISMARINE_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("prismarine_brick", Blocks.field_203201_bQ);
      DARK_PRISMARINE_VERTICAL_SLAB = makeVerticalVanillaSlab("dark_prismarine", Blocks.field_203202_bR);
      PURPUR_VERTICAL_SLAB = makeVerticalVanillaSlab("purpur", Blocks.field_185771_cX);
      POLISHED_GRANITE_VERTICAL_SLAB = makeVerticalVanillaSlab("polished_granite", Blocks.field_222446_lj);
      SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("smooth_red_sandstone", Blocks.field_222447_lk);
      MOSSY_STONE_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("mossy_stone_brick", Blocks.field_222448_ll);
      POLISHED_DIORITE_VERTICAL_SLAB = makeVerticalVanillaSlab("polished_diorite", Blocks.field_222449_lm);
      MOSSY_COBBLESTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("mossy_cobblestone", Blocks.field_222450_ln);
      END_STONE_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("end_stone_brick", Blocks.field_222451_lo);
      SMOOTH_SANDSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("smooth_sandstone", Blocks.field_222452_lp);
      SMOOTH_QUARTZ_VERTICAL_SLAB = makeVerticalVanillaSlab("smooth_quartz", Blocks.field_222453_lq);
      GRANITE_VERTICAL_SLAB = makeVerticalVanillaSlab("granite", Blocks.field_222454_lr);
      ANDESITE_VERTICAL_SLAB = makeVerticalVanillaSlab("andesite", Blocks.field_222455_ls);
      RED_NETHER_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("red_nether_brick", Blocks.field_222456_lt);
      POLISHED_ANDESITE_VERTICAL_SLAB = makeVerticalVanillaSlab("polished_andesite", Blocks.field_222457_lu);
      DIORITE_VERTICAL_SLAB = makeVerticalVanillaSlab("diorite", Blocks.field_222458_lv);
      CRIMSON_VERTICAL_SLAB = makeVerticalVanillaSlab("crimson", Blocks.field_235346_mE_);
      WARPED_VERTICAL_SLAB = makeVerticalVanillaSlab("warped", Blocks.field_235347_mF_);
      BLACKSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("blackstone", Blocks.field_235409_ns_);
      POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = makeVerticalVanillaSlab("polished_blackstone_brick", Blocks.field_235414_nx_);
      POLISHED_BLACKSTONE_VERTICAL_SLAB = makeVerticalVanillaSlab("polished_blackstone", Blocks.field_235389_nC_);
   }
}
