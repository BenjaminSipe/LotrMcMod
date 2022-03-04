package lotr.common.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTRAmbientCreature;
import lotr.common.entity.animal.LOTREntityAurochs;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityFish;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityWickedDwarf;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.variant.LOTRBiomeVariantList;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeInvasionSpawns;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.BiomeGenBase.FlowerEntry;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.EnumHelper;

public abstract class LOTRBiome extends BiomeGenBase {
   private static Class[][] correctCreatureTypeParams;
   public static EnumCreatureType creatureType_LOTRAmbient;
   public static LOTRBiome river;
   public static LOTRBiome rohan;
   public static LOTRBiome mistyMountains;
   public static LOTRBiome shire;
   public static LOTRBiome shireWoodlands;
   public static LOTRBiome mordor;
   public static LOTRBiome mordorMountains;
   public static LOTRBiome gondor;
   public static LOTRBiome whiteMountains;
   public static LOTRBiome lothlorien;
   public static LOTRBiome celebrant;
   public static LOTRBiome ironHills;
   public static LOTRBiome deadMarshes;
   public static LOTRBiome trollshaws;
   public static LOTRBiome woodlandRealm;
   public static LOTRBiome mirkwoodCorrupted;
   public static LOTRBiome rohanUrukHighlands;
   public static LOTRBiome emynMuil;
   public static LOTRBiome ithilien;
   public static LOTRBiome pelargir;
   public static LOTRBiome loneLands;
   public static LOTRBiome loneLandsHills;
   public static LOTRBiome dunland;
   public static LOTRBiome fangorn;
   public static LOTRBiome angle;
   public static LOTRBiome ettenmoors;
   public static LOTRBiome oldForest;
   public static LOTRBiome harondor;
   public static LOTRBiome eriador;
   public static LOTRBiome eriadorDowns;
   public static LOTRBiome erynVorn;
   public static LOTRBiome greyMountains;
   public static LOTRBiome midgewater;
   public static LOTRBiome brownLands;
   public static LOTRBiome ocean;
   public static LOTRBiome anduinHills;
   public static LOTRBiome meneltarma;
   public static LOTRBiome gladdenFields;
   public static LOTRBiome lothlorienEdge;
   public static LOTRBiome forodwaith;
   public static LOTRBiome enedwaith;
   public static LOTRBiome angmar;
   public static LOTRBiome eregion;
   public static LOTRBiome lindon;
   public static LOTRBiome lindonWoodlands;
   public static LOTRBiome eastBight;
   public static LOTRBiome blueMountains;
   public static LOTRBiome mirkwoodMountains;
   public static LOTRBiome wilderland;
   public static LOTRBiome dagorlad;
   public static LOTRBiome nurn;
   public static LOTRBiome nurnen;
   public static LOTRBiome nurnMarshes;
   public static LOTRBiome angmarMountains;
   public static LOTRBiome anduinMouth;
   public static LOTRBiome entwashMouth;
   public static LOTRBiome dorEnErnil;
   public static LOTRBiome dorEnErnilHills;
   public static LOTRBiome fangornWasteland;
   public static LOTRBiome rohanWoodlands;
   public static LOTRBiome gondorWoodlands;
   public static LOTRBiome lake;
   public static LOTRBiome lindonCoast;
   public static LOTRBiome barrowDowns;
   public static LOTRBiome longMarshes;
   public static LOTRBiome fangornClearing;
   public static LOTRBiome ithilienHills;
   public static LOTRBiome ithilienWasteland;
   public static LOTRBiome nindalf;
   public static LOTRBiome coldfells;
   public static LOTRBiome nanCurunir;
   public static LOTRBiome adornland;
   public static LOTRBiome whiteDowns;
   public static LOTRBiome swanfleet;
   public static LOTRBiome pelennor;
   public static LOTRBiome minhiriath;
   public static LOTRBiome erebor;
   public static LOTRBiome mirkwoodNorth;
   public static LOTRBiome woodlandRealmHills;
   public static LOTRBiome nanUngol;
   public static LOTRBiome pinnathGelin;
   public static LOTRBiome island;
   public static LOTRBiome forodwaithMountains;
   public static LOTRBiome mistyMountainsFoothills;
   public static LOTRBiome greyMountainsFoothills;
   public static LOTRBiome blueMountainsFoothills;
   public static LOTRBiome tundra;
   public static LOTRBiome taiga;
   public static LOTRBiome breeland;
   public static LOTRBiome chetwood;
   public static LOTRBiome forodwaithGlacier;
   public static LOTRBiome whiteMountainsFoothills;
   public static LOTRBiome beach;
   public static LOTRBiome beachGravel;
   public static LOTRBiome nearHarad;
   public static LOTRBiome farHarad;
   public static LOTRBiome haradMountains;
   public static LOTRBiome umbar;
   public static LOTRBiome farHaradJungle;
   public static LOTRBiome umbarHills;
   public static LOTRBiome nearHaradHills;
   public static LOTRBiome farHaradJungleLake;
   public static LOTRBiome lostladen;
   public static LOTRBiome farHaradForest;
   public static LOTRBiome nearHaradFertile;
   public static LOTRBiome pertorogwaith;
   public static LOTRBiome umbarForest;
   public static LOTRBiome farHaradJungleEdge;
   public static LOTRBiome tauredainClearing;
   public static LOTRBiome gulfHarad;
   public static LOTRBiome dorwinionHills;
   public static LOTRBiome tolfalas;
   public static LOTRBiome lebennin;
   public static LOTRBiome rhun;
   public static LOTRBiome rhunForest;
   public static LOTRBiome redMountains;
   public static LOTRBiome redMountainsFoothills;
   public static LOTRBiome dolGuldur;
   public static LOTRBiome nearHaradSemiDesert;
   public static LOTRBiome farHaradArid;
   public static LOTRBiome farHaradAridHills;
   public static LOTRBiome farHaradSwamp;
   public static LOTRBiome farHaradCloudForest;
   public static LOTRBiome farHaradBushland;
   public static LOTRBiome farHaradBushlandHills;
   public static LOTRBiome farHaradMangrove;
   public static LOTRBiome nearHaradFertileForest;
   public static LOTRBiome anduinVale;
   public static LOTRBiome wold;
   public static LOTRBiome shireMoors;
   public static LOTRBiome shireMarshes;
   public static LOTRBiome nearHaradRedDesert;
   public static LOTRBiome farHaradVolcano;
   public static LOTRBiome udun;
   public static LOTRBiome gorgoroth;
   public static LOTRBiome morgulVale;
   public static LOTRBiome easternDesolation;
   public static LOTRBiome dale;
   public static LOTRBiome dorwinion;
   public static LOTRBiome towerHills;
   public static LOTRBiome gulfHaradForest;
   public static LOTRBiome wilderlandNorth;
   public static LOTRBiome forodwaithCoast;
   public static LOTRBiome farHaradCoast;
   public static LOTRBiome nearHaradRiverbank;
   public static LOTRBiome lossarnach;
   public static LOTRBiome imlothMelui;
   public static LOTRBiome nearHaradOasis;
   public static LOTRBiome beachWhite;
   public static LOTRBiome harnedor;
   public static LOTRBiome lamedon;
   public static LOTRBiome lamedonHills;
   public static LOTRBiome blackrootVale;
   public static LOTRBiome andrast;
   public static LOTRBiome pukel;
   public static LOTRBiome rhunLand;
   public static LOTRBiome rhunLandSteppe;
   public static LOTRBiome rhunLandHills;
   public static LOTRBiome rhunRedForest;
   public static LOTRBiome rhunIsland;
   public static LOTRBiome rhunIslandForest;
   public static LOTRBiome lastDesert;
   public static LOTRBiome windMountains;
   public static LOTRBiome windMountainsFoothills;
   public static LOTRBiome rivendell;
   public static LOTRBiome rivendellHills;
   public static LOTRBiome farHaradJungleMountains;
   public static LOTRBiome halfTrollForest;
   public static LOTRBiome farHaradKanuka;
   public static LOTRBiome utumno;
   public LOTRDimension biomeDimension;
   public LOTRBiomeDecorator decorator;
   public int topBlockMeta;
   public int fillerBlockMeta;
   public float heightBaseParameter;
   public static NoiseGeneratorPerlin biomeTerrainNoise;
   protected static Random terrainRand;
   protected boolean enablePodzol;
   protected boolean enableRocky;
   private LOTRBiomeVariantList biomeVariantsSmall;
   private LOTRBiomeVariantList biomeVariantsLarge;
   private static final float defaultVariantChance = 0.4F;
   public float variantChance;
   public LOTRBiomeSpawnList npcSpawnList;
   protected List spawnableLOTRAmbientList;
   private List spawnableTraders;
   private LOTREventSpawner.EventChance banditChance;
   private Class banditEntityClass;
   public final LOTRBiomeInvasionSpawns invasionSpawns;
   public LOTRBiome.BiomeColors biomeColors;
   public LOTRBiome.BiomeTerrain biomeTerrain;
   private boolean initDwarven;
   private boolean isDwarven;
   private static Color waterColorNorth;
   private static Color waterColorSouth;
   private static int waterLimitNorth;
   private static int waterLimitSouth;

   public static void initBiomes() {
      river = (new LOTRBiomeGenRiver(0, false)).setMinMaxHeight(-0.5F, 0.0F).setColor(3570869).setBiomeName("river");
      rohan = (new LOTRBiomeGenRohan(1, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.2F, 0.15F).setColor(7384389).setBiomeName("rohan");
      mistyMountains = (new LOTRBiomeGenMistyMountains(2, true)).setTemperatureRainfall(0.2F, 0.5F).setMinMaxHeight(2.0F, 2.0F).setColor(15263713).setBiomeName("mistyMountains");
      shire = (new LOTRBiomeGenShire(3, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.15F, 0.3F).setColor(6794549).setBiomeName("shire");
      shireWoodlands = (new LOTRBiomeGenShireWoodlands(4, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.3F, 0.5F).setColor(4486966).setBiomeName("shireWoodlands");
      mordor = (new LOTRBiomeGenMordor(5, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.3F, 0.5F).setColor(1118222).setBiomeName("mordor");
      mordorMountains = (new LOTRBiomeGenMordorMountains(6, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(2.0F, 3.0F).setColor(5328200).setBiomeName("mordorMountains");
      gondor = (new LOTRBiomeGenGondor(7, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.1F, 0.15F).setColor(8959045).setBiomeName("gondor");
      whiteMountains = (new LOTRBiomeGenWhiteMountains(8, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(1.5F, 2.0F).setColor(15066600).setBiomeName("whiteMountains");
      lothlorien = (new LOTRBiomeGenLothlorien(9, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.3F).setColor(16504895).setBiomeName("lothlorien");
      celebrant = (new LOTRBiomeGenCelebrant(10, true)).setTemperatureRainfall(1.1F, 1.1F).setMinMaxHeight(0.1F, 0.05F).setColor(7647046).setBiomeName("celebrant");
      ironHills = (new LOTRBiomeGenIronHills(11, true)).setTemperatureRainfall(0.27F, 0.4F).setMinMaxHeight(0.3F, 1.4F).setColor(9142093).setBiomeName("ironHills");
      deadMarshes = (new LOTRBiomeGenDeadMarshes(12, true)).setTemperatureRainfall(0.4F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(7303999).setBiomeName("deadMarshes");
      trollshaws = (new LOTRBiomeGenTrollshaws(13, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.15F, 1.0F).setColor(5798959).setBiomeName("trollshaws");
      woodlandRealm = (new LOTRBiomeGenWoodlandRealm(14, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.3F).setColor(4089126).setBiomeName("woodlandRealm");
      mirkwoodCorrupted = (new LOTRBiomeGenMirkwoodCorrupted(15, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.2F, 0.4F).setColor(3032091).setBiomeName("mirkwoodCorrupted");
      rohanUrukHighlands = (new LOTRBiomeGenRohanUruk(16, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.8F, 0.3F).setColor(8295258).setBiomeName("rohanUrukHighlands");
      emynMuil = (new LOTRBiomeGenEmynMuil(17, true)).setTemperatureRainfall(0.5F, 0.9F).setMinMaxHeight(0.2F, 0.8F).setColor(9866354).setBiomeName("emynMuil");
      ithilien = (new LOTRBiomeGenIthilien(18, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.15F, 0.5F).setColor(7710516).setBiomeName("ithilien");
      pelargir = (new LOTRBiomeGenPelargir(19, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.08F, 0.2F).setColor(11256145).setBiomeName("pelargir");
      loneLands = (new LOTRBiomeGenLoneLands(21, true)).setTemperatureRainfall(0.6F, 0.5F).setMinMaxHeight(0.15F, 0.4F).setColor(8562762).setBiomeName("loneLands");
      loneLandsHills = (new LOTRBiomeGenLoneLandsHills(22, false)).setTemperatureRainfall(0.6F, 0.5F).setMinMaxHeight(0.6F, 0.8F).setColor(8687182).setBiomeName("loneLandsHills");
      dunland = (new LOTRBiomeGenDunland(23, true)).setTemperatureRainfall(0.4F, 0.7F).setMinMaxHeight(0.3F, 0.5F).setColor(6920524).setBiomeName("dunland");
      fangorn = (new LOTRBiomeGenFangorn(24, true)).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 0.4F).setColor(4355353).setBiomeName("fangorn");
      angle = (new LOTRBiomeGenAngle(25, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.15F, 0.3F).setColor(9416527).setBiomeName("angle");
      ettenmoors = (new LOTRBiomeGenEttenmoors(26, true)).setTemperatureRainfall(0.2F, 0.6F).setMinMaxHeight(0.5F, 0.6F).setColor(8161626).setBiomeName("ettenmoors");
      oldForest = (new LOTRBiomeGenOldForest(27, true)).setTemperatureRainfall(0.5F, 1.0F).setMinMaxHeight(0.2F, 0.3F).setColor(4551995).setBiomeName("oldForest");
      harondor = (new LOTRBiomeGenHarondor(28, true)).setTemperatureRainfall(1.0F, 0.6F).setMinMaxHeight(0.2F, 0.3F).setColor(10663238).setBiomeName("harondor");
      eriador = (new LOTRBiomeGenEriador(29, true)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.1F, 0.4F).setColor(7054916).setBiomeName("eriador");
      eriadorDowns = (new LOTRBiomeGenEriadorDowns(30, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.5F, 0.5F).setColor(7638087).setBiomeName("eriadorDowns");
      erynVorn = (new LOTRBiomeGenErynVorn(31, false)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.1F, 0.4F).setColor(4357965).setBiomeName("erynVorn");
      greyMountains = (new LOTRBiomeGenGreyMountains(32, true)).setTemperatureRainfall(0.28F, 0.2F).setMinMaxHeight(1.8F, 2.0F).setColor(13290689).setBiomeName("greyMountains");
      midgewater = (new LOTRBiomeGenMidgewater(33, true)).setTemperatureRainfall(0.6F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(6001495).setBiomeName("midgewater");
      brownLands = (new LOTRBiomeGenBrownLands(34, true)).setTemperatureRainfall(1.0F, 0.2F).setMinMaxHeight(0.2F, 0.2F).setColor(8552016).setBiomeName("brownLands");
      ocean = (new LOTRBiomeGenOcean(35, false)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(-1.0F, 0.3F).setColor(153997).setBiomeName("ocean");
      anduinHills = (new LOTRBiomeGenAnduin(36, true)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.6F, 0.4F).setColor(7058012).setBiomeName("anduinHills");
      meneltarma = (new LOTRBiomeGenMeneltarma(37, false)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.1F, 0.2F).setColor(9549658).setBiomeName("meneltarma");
      gladdenFields = (new LOTRBiomeGenGladdenFields(38, true)).setTemperatureRainfall(0.6F, 1.2F).setMinMaxHeight(0.0F, 0.1F).setColor(5020505).setBiomeName("gladdenFields");
      lothlorienEdge = (new LOTRBiomeGenLothlorienEdge(39, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.2F).setColor(13944387).setBiomeName("lothlorienEdge");
      forodwaith = (new LOTRBiomeGenForodwaith(40, true)).setTemperatureRainfall(0.0F, 0.2F).setMinMaxHeight(0.1F, 0.1F).setColor(14211282).setBiomeName("forodwaith");
      enedwaith = (new LOTRBiomeGenEnedwaith(41, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.2F, 0.3F).setColor(8038479).setBiomeName("enedwaith");
      angmar = (new LOTRBiomeGenAngmar(42, true)).setTemperatureRainfall(0.2F, 0.2F).setMinMaxHeight(0.2F, 0.6F).setColor(5523247).setBiomeName("angmar");
      eregion = (new LOTRBiomeGenEregion(43, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.2F, 0.3F).setColor(6656072).setBiomeName("eregion");
      lindon = (new LOTRBiomeGenLindon(44, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.15F, 0.2F).setColor(7646533).setBiomeName("lindon");
      lindonWoodlands = (new LOTRBiomeGenLindonWoodlands(45, false)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.2F, 0.5F).setColor(1996591).setBiomeName("lindonWoodlands");
      eastBight = (new LOTRBiomeGenEastBight(46, true)).setTemperatureRainfall(0.8F, 0.3F).setMinMaxHeight(0.15F, 0.05F).setColor(9082205).setBiomeName("eastBight");
      blueMountains = (new LOTRBiomeGenBlueMountains(47, true)).setTemperatureRainfall(0.22F, 0.8F).setMinMaxHeight(1.0F, 2.5F).setColor(13228770).setBiomeName("blueMountains");
      mirkwoodMountains = (new LOTRBiomeGenMirkwoodMountains(48, true)).setTemperatureRainfall(0.28F, 0.9F).setMinMaxHeight(1.2F, 1.5F).setColor(2632989).setBiomeName("mirkwoodMountains");
      wilderland = (new LOTRBiomeGenWilderland(49, true)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(0.2F, 0.4F).setColor(9612368).setBiomeName("wilderland");
      dagorlad = (new LOTRBiomeGenDagorlad(50, true)).setTemperatureRainfall(1.0F, 0.2F).setMinMaxHeight(0.1F, 0.05F).setColor(7036741).setBiomeName("dagorlad");
      nurn = (new LOTRBiomeGenNurn(51, true)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(0.1F, 0.2F).setColor(2630683).setBiomeName("nurn");
      nurnen = (new LOTRBiomeGenNurnen(52, false)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(-1.0F, 0.3F).setColor(931414).setBiomeName("nurnen");
      nurnMarshes = (new LOTRBiomeGenNurnMarshes(53, true)).setTemperatureRainfall(0.9F, 0.4F).setMinMaxHeight(0.0F, 0.1F).setColor(4012843).setBiomeName("nurnMarshes");
      adornland = (new LOTRBiomeGenAdornland(54, true)).setTemperatureRainfall(0.7F, 0.6F).setMinMaxHeight(0.2F, 0.2F).setColor(7838543).setBiomeName("adornland");
      angmarMountains = (new LOTRBiomeGenAngmarMountains(55, true)).setTemperatureRainfall(0.25F, 0.1F).setMinMaxHeight(1.6F, 1.5F).setColor(13619147).setBiomeName("angmarMountains");
      anduinMouth = (new LOTRBiomeGenAnduinMouth(56, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(5089363).setBiomeName("anduinMouth");
      entwashMouth = (new LOTRBiomeGenEntwashMouth(57, true)).setTemperatureRainfall(0.5F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(5612358).setBiomeName("entwashMouth");
      dorEnErnil = (new LOTRBiomeGenDorEnErnil(58, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.07F, 0.2F).setColor(9355077).setBiomeName("dorEnErnil");
      dorEnErnilHills = (new LOTRBiomeGenDorEnErnilHills(59, false)).setTemperatureRainfall(0.8F, 0.7F).setMinMaxHeight(0.5F, 0.5F).setColor(8560707).setBiomeName("dorEnErnilHills");
      fangornWasteland = (new LOTRBiomeGenFangornWasteland(60, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.2F, 0.4F).setColor(6782028).setBiomeName("fangornWasteland");
      rohanWoodlands = (new LOTRBiomeGenRohanWoodlands(61, false)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.2F, 0.4F).setColor(5736246).setBiomeName("rohanWoodlands");
      gondorWoodlands = (new LOTRBiomeGenGondorWoodlands(62, false)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.2F).setColor(5867307).setBiomeName("gondorWoodlands");
      lake = (new LOTRBiomeGenLake(63, false)).setColor(3433630).setBiomeName("lake");
      lindonCoast = (new LOTRBiomeGenLindonCoast(64, false)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.0F, 0.5F).setColor(9278870).setBiomeName("lindonCoast");
      barrowDowns = (new LOTRBiomeGenBarrowDowns(65, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.3F, 0.4F).setColor(8097362).setBiomeName("barrowDowns");
      longMarshes = (new LOTRBiomeGenLongMarshes(66, true)).setTemperatureRainfall(0.6F, 0.9F).setMinMaxHeight(0.0F, 0.1F).setColor(7178054).setBiomeName("longMarshes");
      fangornClearing = (new LOTRBiomeGenFangornClearing(67, false)).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(0.2F, 0.1F).setColor(5877050).setBiomeName("fangornClearing");
      ithilienHills = (new LOTRBiomeGenIthilienHills(68, false)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.6F, 0.6F).setColor(6985792).setBiomeName("ithilienHills");
      ithilienWasteland = (new LOTRBiomeGenIthilienWasteland(69, true)).setTemperatureRainfall(0.6F, 0.6F).setMinMaxHeight(0.15F, 0.2F).setColor(8030031).setBiomeName("ithilienWasteland");
      nindalf = (new LOTRBiomeGenNindalf(70, true)).setTemperatureRainfall(0.4F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(7111750).setBiomeName("nindalf");
      coldfells = (new LOTRBiomeGenColdfells(71, true)).setTemperatureRainfall(0.25F, 0.8F).setMinMaxHeight(0.4F, 0.8F).setColor(8296018).setBiomeName("coldfells");
      nanCurunir = (new LOTRBiomeGenNanCurunir(72, true)).setTemperatureRainfall(0.6F, 0.4F).setMinMaxHeight(0.2F, 0.1F).setColor(7109714).setBiomeName("nanCurunir");
      whiteDowns = (new LOTRBiomeGenWhiteDowns(74, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.6F, 0.6F).setColor(10210937).setBiomeName("whiteDowns");
      swanfleet = (new LOTRBiomeGenSwanfleet(75, true)).setTemperatureRainfall(0.8F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(6265945).setBiomeName("swanfleet");
      pelennor = (new LOTRBiomeGenPelennor(76, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.1F, 0.02F).setColor(11258955).setBiomeName("pelennor");
      minhiriath = (new LOTRBiomeGenMinhiriath(77, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.1F, 0.2F).setColor(7380550).setBiomeName("minhiriath");
      erebor = (new LOTRBiomeGenErebor(78, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.4F, 0.6F).setColor(7499093).setBiomeName("erebor");
      mirkwoodNorth = (new LOTRBiomeGenMirkwoodNorth(79, true)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.2F, 0.4F).setColor(3822115).setBiomeName("mirkwoodNorth");
      woodlandRealmHills = (new LOTRBiomeGenWoodlandRealmHills(80, false)).setTemperatureRainfall(0.8F, 0.6F).setMinMaxHeight(0.9F, 0.7F).setColor(3624991).setBiomeName("woodlandRealmHills");
      nanUngol = (new LOTRBiomeGenNanUngol(81, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.1F, 0.4F).setColor(656641).setBiomeName("nanUngol");
      pinnathGelin = (new LOTRBiomeGenPinnathGelin(82, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.5F, 0.5F).setColor(9946693).setBiomeName("pinnathGelin");
      island = (new LOTRBiomeGenOcean(83, false)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.0F, 0.3F).setColor(10138963).setBiomeName("island");
      forodwaithMountains = (new LOTRBiomeGenForodwaithMountains(84, true)).setTemperatureRainfall(0.0F, 0.2F).setMinMaxHeight(2.0F, 2.0F).setColor(15592942).setBiomeName("forodwaithMountains");
      mistyMountainsFoothills = (new LOTRBiomeGenMistyMountainsFoothills(85, true)).setTemperatureRainfall(0.25F, 0.6F).setMinMaxHeight(0.7F, 0.9F).setColor(12501430).setBiomeName("mistyMountainsFoothills");
      greyMountainsFoothills = (new LOTRBiomeGenGreyMountainsFoothills(86, true)).setTemperatureRainfall(0.5F, 0.7F).setMinMaxHeight(0.5F, 0.9F).setColor(9148000).setBiomeName("greyMountainsFoothills");
      blueMountainsFoothills = (new LOTRBiomeGenBlueMountainsFoothills(87, true)).setTemperatureRainfall(0.5F, 0.8F).setMinMaxHeight(0.5F, 0.9F).setColor(11253170).setBiomeName("blueMountainsFoothills");
      tundra = (new LOTRBiomeGenTundra(88, true)).setTemperatureRainfall(0.1F, 0.3F).setMinMaxHeight(0.1F, 0.2F).setColor(12366486).setBiomeName("tundra");
      taiga = (new LOTRBiomeGenTaiga(89, true)).setTemperatureRainfall(0.1F, 0.7F).setMinMaxHeight(0.1F, 0.5F).setColor(6526543).setBiomeName("taiga");
      breeland = (new LOTRBiomeGenBreeland(90, true)).setTemperatureRainfall(0.8F, 0.7F).setMinMaxHeight(0.1F, 0.2F).setColor(6861625).setBiomeName("breeland");
      chetwood = (new LOTRBiomeGenChetwood(91, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.4F).setColor(4424477).setBiomeName("chetwood");
      forodwaithGlacier = (new LOTRBiomeGenForodwaithGlacier(92, true)).setTemperatureRainfall(0.0F, 0.1F).setMinMaxHeight(1.0F, 0.1F).setColor(9424096).setBiomeName("forodwaithGlacier");
      whiteMountainsFoothills = (new LOTRBiomeGenWhiteMountainsFoothills(93, true)).setTemperatureRainfall(0.6F, 0.7F).setMinMaxHeight(0.5F, 0.9F).setColor(12635575).setBiomeName("whiteMountainsFoothills");
      beach = (new LOTRBiomeGenBeach(94, false)).setBeachBlock(Blocks.field_150354_m, 0).setColor(14404247).setBiomeName("beach");
      beachGravel = (new LOTRBiomeGenBeach(95, false)).setBeachBlock(Blocks.field_150351_n, 0).setColor(9868704).setBiomeName("beachGravel");
      nearHarad = (new LOTRBiomeGenNearHarad(96, true)).setTemperatureRainfall(1.5F, 0.1F).setMinMaxHeight(0.2F, 0.1F).setColor(14205815).setBiomeName("nearHarad");
      farHarad = (new LOTRBiomeGenFarHaradSavannah(97, true)).setTemperatureRainfall(1.2F, 0.2F).setMinMaxHeight(0.1F, 0.1F).setColor(9740353).setBiomeName("farHarad");
      haradMountains = (new LOTRBiomeGenHaradMountains(98, true)).setTemperatureRainfall(0.9F, 0.5F).setMinMaxHeight(1.8F, 2.0F).setColor(9867381).setBiomeName("haradMountains");
      umbar = (new LOTRBiomeGenUmbar(99, true)).setTemperatureRainfall(0.9F, 0.6F).setMinMaxHeight(0.1F, 0.2F).setColor(9542740).setBiomeName("umbar");
      farHaradJungle = (new LOTRBiomeGenFarHaradJungle(100, true)).setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(0.2F, 0.4F).setColor(4944931).setBiomeName("farHaradJungle");
      umbarHills = (new LOTRBiomeGenUmbar(101, false)).setTemperatureRainfall(0.8F, 0.5F).setMinMaxHeight(1.2F, 0.8F).setColor(8226378).setBiomeName("umbarHills");
      nearHaradHills = (new LOTRBiomeGenNearHaradHills(102, false)).setTemperatureRainfall(1.2F, 0.3F).setMinMaxHeight(0.5F, 0.8F).setColor(12167010).setBiomeName("nearHaradHills");
      farHaradJungleLake = (new LOTRBiomeGenFarHaradJungleLake(103, false)).setTemperatureRainfall(1.2F, 0.9F).setMinMaxHeight(-0.5F, 0.2F).setColor(2271948).setBiomeName("farHaradJungleLake");
      lostladen = (new LOTRBiomeGenLostladen(104, true)).setTemperatureRainfall(1.2F, 0.2F).setMinMaxHeight(0.2F, 0.1F).setColor(10658661).setBiomeName("lostladen");
      farHaradForest = (new LOTRBiomeGenFarHaradForest(105, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.3F, 0.4F).setColor(3703325).setBiomeName("farHaradForest");
      nearHaradFertile = (new LOTRBiomeGenNearHaradFertile(106, true)).setTemperatureRainfall(1.2F, 0.7F).setMinMaxHeight(0.2F, 0.1F).setColor(10398286).setBiomeName("nearHaradFertile");
      pertorogwaith = (new LOTRBiomeGenPertorogwaith(107, true)).setTemperatureRainfall(0.7F, 0.1F).setMinMaxHeight(0.2F, 0.5F).setColor(8879706).setBiomeName("pertorogwaith");
      umbarForest = (new LOTRBiomeGenUmbarForest(108, false)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.2F, 0.3F).setColor(7178042).setBiomeName("umbarForest");
      farHaradJungleEdge = (new LOTRBiomeGenFarHaradJungleEdge(109, true)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.2F, 0.2F).setColor(7440430).setBiomeName("farHaradJungleEdge");
      tauredainClearing = (new LOTRBiomeGenTauredainClearing(110, true)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.2F, 0.2F).setColor(10796101).setBiomeName("tauredainClearing");
      gulfHarad = (new LOTRBiomeGenGulfHarad(111, true)).setTemperatureRainfall(1.0F, 0.5F).setMinMaxHeight(0.15F, 0.1F).setColor(9152592).setBiomeName("gulfHarad");
      dorwinionHills = (new LOTRBiomeGenDorwinionHills(112, true)).setTemperatureRainfall(0.9F, 0.8F).setMinMaxHeight(0.8F, 0.8F).setColor(13357993).setBiomeName("dorwinionHills");
      tolfalas = (new LOTRBiomeGenTolfalas(113, true)).setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.3F, 1.0F).setColor(10199149).setBiomeName("tolfalas");
      lebennin = (new LOTRBiomeGenLebennin(114, true)).setTemperatureRainfall(1.0F, 0.9F).setMinMaxHeight(0.1F, 0.3F).setColor(7845418).setBiomeName("lebennin");
      rhun = (new LOTRBiomeGenRhun(115, true)).setTemperatureRainfall(0.9F, 0.3F).setMinMaxHeight(0.3F, 0.0F).setColor(10465880).setBiomeName("rhun");
      rhunForest = (new LOTRBiomeGenRhunForest(116, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.3F, 0.5F).setColor(7505723).setBiomeName("rhunForest");
      redMountains = (new LOTRBiomeGenRedMountains(117, true)).setTemperatureRainfall(0.3F, 0.4F).setMinMaxHeight(1.5F, 2.0F).setColor(9662796).setBiomeName("redMountains");
      redMountainsFoothills = (new LOTRBiomeGenRedMountainsFoothills(118, true)).setTemperatureRainfall(0.7F, 0.4F).setMinMaxHeight(0.5F, 0.9F).setColor(10064978).setBiomeName("redMountainsFoothills");
      dolGuldur = (new LOTRBiomeGenDolGuldur(119, true)).setTemperatureRainfall(0.6F, 0.8F).setMinMaxHeight(0.2F, 0.5F).setColor(2371343).setBiomeName("dolGuldur");
      nearHaradSemiDesert = (new LOTRBiomeGenNearHaradSemiDesert(120, true)).setTemperatureRainfall(1.5F, 0.2F).setMinMaxHeight(0.2F, 0.1F).setColor(12434282).setBiomeName("nearHaradSemiDesert");
      farHaradArid = (new LOTRBiomeGenFarHaradArid(121, true)).setTemperatureRainfall(1.5F, 0.3F).setMinMaxHeight(0.2F, 0.15F).setColor(11185749).setBiomeName("farHaradArid");
      farHaradAridHills = (new LOTRBiomeGenFarHaradArid(122, false)).setTemperatureRainfall(1.5F, 0.3F).setMinMaxHeight(1.0F, 0.6F).setColor(10063195).setBiomeName("farHaradAridHills");
      farHaradSwamp = (new LOTRBiomeGenFarHaradSwamp(123, true)).setTemperatureRainfall(0.8F, 1.0F).setMinMaxHeight(0.0F, 0.1F).setColor(5608267).setBiomeName("farHaradSwamp");
      farHaradCloudForest = (new LOTRBiomeGenFarHaradCloudForest(124, true)).setTemperatureRainfall(1.2F, 1.2F).setMinMaxHeight(0.7F, 0.4F).setColor(3046208).setBiomeName("farHaradCloudForest");
      farHaradBushland = (new LOTRBiomeGenFarHaradBushland(125, true)).setTemperatureRainfall(1.0F, 0.4F).setMinMaxHeight(0.2F, 0.1F).setColor(10064190).setBiomeName("farHaradBushland");
      farHaradBushlandHills = (new LOTRBiomeGenFarHaradBushland(126, false)).setTemperatureRainfall(0.8F, 0.4F).setMinMaxHeight(0.8F, 0.8F).setColor(8354100).setBiomeName("farHaradBushlandHills");
      farHaradMangrove = (new LOTRBiomeGenFarHaradMangrove(127, true)).setTemperatureRainfall(1.0F, 0.9F).setMinMaxHeight(-0.05F, 0.05F).setColor(8883789).setBiomeName("farHaradMangrove");
      nearHaradFertileForest = (new LOTRBiomeGenNearHaradFertileForest(128, false)).setTemperatureRainfall(1.2F, 1.0F).setMinMaxHeight(0.2F, 0.4F).setColor(6915122).setBiomeName("nearHaradFertileForest");
      anduinVale = (new LOTRBiomeGenAnduinVale(129, true)).setTemperatureRainfall(0.9F, 1.1F).setMinMaxHeight(0.05F, 0.05F).setColor(7447880).setBiomeName("anduinVale");
      wold = (new LOTRBiomeGenWold(130, true)).setTemperatureRainfall(0.9F, 0.1F).setMinMaxHeight(0.4F, 0.3F).setColor(9483599).setBiomeName("wold");
      shireMoors = (new LOTRBiomeGenShireMoors(131, true)).setTemperatureRainfall(0.6F, 1.6F).setMinMaxHeight(0.4F, 0.6F).setColor(6921036).setBiomeName("shireMoors");
      shireMarshes = (new LOTRBiomeGenShireMarshes(132, true)).setTemperatureRainfall(0.8F, 1.2F).setMinMaxHeight(0.0F, 0.1F).setColor(4038751).setBiomeName("shireMarshes");
      nearHaradRedDesert = (new LOTRBiomeGenNearHaradRed(133, true)).setTemperatureRainfall(1.5F, 0.1F).setMinMaxHeight(0.2F, 0.0F).setColor(13210447).setBiomeName("nearHaradRedDesert");
      farHaradVolcano = (new LOTRBiomeGenFarHaradVolcano(134, true)).setTemperatureRainfall(1.5F, 0.0F).setMinMaxHeight(0.6F, 1.2F).setColor(6838068).setBiomeName("farHaradVolcano");
      udun = (new LOTRBiomeGenUdun(135, true)).setTemperatureRainfall(1.5F, 0.0F).setMinMaxHeight(0.2F, 0.7F).setColor(65536).setBiomeName("udun");
      gorgoroth = (new LOTRBiomeGenGorgoroth(136, true)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.6F, 0.2F).setColor(2170141).setBiomeName("gorgoroth");
      morgulVale = (new LOTRBiomeGenMorgulVale(137, true)).setTemperatureRainfall(1.0F, 0.0F).setMinMaxHeight(0.2F, 0.1F).setColor(1387801).setBiomeName("morgulVale");
      easternDesolation = (new LOTRBiomeGenEasternDesolation(138, true)).setTemperatureRainfall(1.0F, 0.3F).setMinMaxHeight(0.2F, 0.2F).setColor(6052935).setBiomeName("easternDesolation");
      dale = (new LOTRBiomeGenDale(139, true)).setTemperatureRainfall(0.8F, 0.7F).setMinMaxHeight(0.1F, 0.2F).setColor(8233807).setBiomeName("dale");
      dorwinion = (new LOTRBiomeGenDorwinion(140, true)).setTemperatureRainfall(0.9F, 0.9F).setMinMaxHeight(0.1F, 0.3F).setColor(7120197).setBiomeName("dorwinion");
      towerHills = (new LOTRBiomeGenTowerHills(141, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.5F, 0.5F).setColor(6854209).setBiomeName("towerHills");
      gulfHaradForest = (new LOTRBiomeGenGulfHaradForest(142, false)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.2F, 0.4F).setColor(5868590).setBiomeName("gulfHaradForest");
      wilderlandNorth = (new LOTRBiomeGenWilderlandNorth(143, true)).setTemperatureRainfall(0.6F, 0.6F).setMinMaxHeight(0.2F, 0.5F).setColor(9676396).setBiomeName("wilderlandNorth");
      forodwaithCoast = (new LOTRBiomeGenForodwaithCoast(144, false)).setTemperatureRainfall(0.0F, 0.4F).setMinMaxHeight(0.0F, 0.5F).setColor(9214637).setBiomeName("forodwaithCoast");
      farHaradCoast = (new LOTRBiomeGenFarHaradCoast(145, false)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.0F, 0.5F).setColor(8356472).setBiomeName("farHaradCoast");
      nearHaradRiverbank = (new LOTRBiomeGenNearHaradRiverbank(146, false)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.1F, 0.1F).setColor(7183952).setBiomeName("nearHaradRiverbank");
      lossarnach = (new LOTRBiomeGenLossarnach(147, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.1F, 0.2F).setColor(8439086).setBiomeName("lossarnach");
      imlothMelui = (new LOTRBiomeGenImlothMelui(148, true)).setTemperatureRainfall(1.0F, 1.2F).setMinMaxHeight(0.1F, 0.2F).setColor(14517608).setBiomeName("imlothMelui");
      nearHaradOasis = (new LOTRBiomeGenNearHaradOasis(149, false)).setTemperatureRainfall(1.2F, 0.8F).setMinMaxHeight(0.1F, 0.1F).setColor(832768).setBiomeName("nearHaradOasis");
      beachWhite = (new LOTRBiomeGenBeach(150, false)).setBeachBlock(LOTRMod.whiteSand, 0).setColor(15592941).setBiomeName("beachWhite");
      harnedor = (new LOTRBiomeGenHarnedor(151, true)).setTemperatureRainfall(1.0F, 0.3F).setMinMaxHeight(0.1F, 0.3F).setColor(11449173).setBiomeName("harnedor");
      lamedon = (new LOTRBiomeGenLamedon(152, true)).setTemperatureRainfall(0.9F, 0.5F).setMinMaxHeight(0.2F, 0.2F).setColor(10927460).setBiomeName("lamedon");
      lamedonHills = (new LOTRBiomeGenLamedonHills(153, true)).setTemperatureRainfall(0.6F, 0.4F).setMinMaxHeight(0.6F, 0.9F).setColor(13555369).setBiomeName("lamedonHills");
      blackrootVale = (new LOTRBiomeGenBlackrootVale(154, true)).setTemperatureRainfall(0.8F, 0.9F).setMinMaxHeight(0.2F, 0.12F).setColor(7183921).setBiomeName("blackrootVale");
      andrast = (new LOTRBiomeGenAndrast(155, true)).setTemperatureRainfall(0.8F, 0.8F).setMinMaxHeight(0.2F, 0.2F).setColor(8885856).setBiomeName("andrast");
      pukel = (new LOTRBiomeGenPukel(156, true)).setTemperatureRainfall(0.7F, 0.7F).setMinMaxHeight(0.2F, 0.4F).setColor(5667394).setBiomeName("pukel");
      rhunLand = (new LOTRBiomeGenRhunLand(157, true)).setTemperatureRainfall(1.0F, 0.8F).setMinMaxHeight(0.1F, 0.3F).setColor(11381583).setBiomeName("rhunLand");
      rhunLandSteppe = (new LOTRBiomeGenRhunLandSteppe(158, true)).setTemperatureRainfall(1.0F, 0.3F).setMinMaxHeight(0.2F, 0.05F).setColor(11712354).setBiomeName("rhunLandSteppe");
      rhunLandHills = (new LOTRBiomeGenRhunLandHills(159, true)).setTemperatureRainfall(1.0F, 0.5F).setMinMaxHeight(0.6F, 0.8F).setColor(9342286).setBiomeName("rhunLandHills");
      rhunRedForest = (new LOTRBiomeGenRhunRedForest(160, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.3F).setColor(9530430).setBiomeName("rhunRedForest");
      rhunIsland = (new LOTRBiomeGenRhunIsland(161, false)).setTemperatureRainfall(1.0F, 0.8F).setMinMaxHeight(0.1F, 0.4F).setColor(10858839).setBiomeName("rhunIsland");
      rhunIslandForest = (new LOTRBiomeGenRhunIslandForest(162, false)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.1F, 0.4F).setColor(9533758).setBiomeName("rhunIslandForest");
      lastDesert = (new LOTRBiomeGenLastDesert(163, true)).setTemperatureRainfall(0.7F, 0.0F).setMinMaxHeight(0.2F, 0.05F).setColor(13878151).setBiomeName("lastDesert");
      windMountains = (new LOTRBiomeGenWindMountains(164, true)).setTemperatureRainfall(0.28F, 0.2F).setMinMaxHeight(2.0F, 2.0F).setColor(13882323).setBiomeName("windMountains");
      windMountainsFoothills = (new LOTRBiomeGenWindMountainsFoothills(165, true)).setTemperatureRainfall(0.4F, 0.6F).setMinMaxHeight(0.5F, 0.6F).setColor(10133354).setBiomeName("windMountainsFoothills");
      rivendell = (new LOTRBiomeGenRivendell(166, true)).setTemperatureRainfall(0.9F, 1.0F).setMinMaxHeight(0.15F, 0.3F).setColor(8828714).setBiomeName("rivendell");
      rivendellHills = (new LOTRBiomeGenRivendellHills(167, true)).setTemperatureRainfall(0.7F, 0.8F).setMinMaxHeight(2.0F, 0.5F).setColor(14210481).setBiomeName("rivendellHills");
      farHaradJungleMountains = (new LOTRBiomeGenFarHaradJungleMountains(168, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(1.8F, 1.5F).setColor(6511174).setBiomeName("farHaradJungleMountains");
      halfTrollForest = (new LOTRBiomeGenHalfTrollForest(169, true)).setTemperatureRainfall(0.8F, 0.2F).setMinMaxHeight(0.3F, 0.4F).setColor(5992500).setBiomeName("halfTrollForest");
      farHaradKanuka = (new LOTRBiomeGenKanuka(170, true)).setTemperatureRainfall(1.0F, 1.0F).setMinMaxHeight(0.3F, 0.5F).setColor(5142552).setBiomeName("farHaradKanuka");
      utumno = (new LOTRBiomeGenUtumno(0)).setTemperatureRainfall(2.0F, 0.0F).setMinMaxHeight(0.0F, 0.0F).setColor(0).setBiomeName("utumno");
   }

   public LOTRBiome(int i, boolean major) {
      this(i, major, LOTRDimension.MIDDLE_EARTH);
   }

   public LOTRBiome(int i, boolean major, LOTRDimension dim) {
      super(i, false);
      this.topBlockMeta = 0;
      this.fillerBlockMeta = 0;
      this.enablePodzol = true;
      this.enableRocky = true;
      this.biomeVariantsSmall = new LOTRBiomeVariantList();
      this.biomeVariantsLarge = new LOTRBiomeVariantList();
      this.variantChance = 0.4F;
      this.npcSpawnList = new LOTRBiomeSpawnList(this);
      this.spawnableLOTRAmbientList = new ArrayList();
      this.spawnableTraders = new ArrayList();
      this.biomeColors = new LOTRBiome.BiomeColors(this);
      this.biomeTerrain = new LOTRBiome.BiomeTerrain(this);
      this.initDwarven = false;
      this.biomeDimension = dim;
      if (this.biomeDimension.biomeList[i] != null) {
         throw new IllegalArgumentException("LOTR biome already exists at index " + i + " for dimension " + this.biomeDimension.dimensionName + "!");
      } else {
         this.biomeDimension.biomeList[i] = this;
         if (major) {
            this.biomeDimension.majorBiomes.add(this);
         }

         this.field_76759_H = LOTRBiome.BiomeColors.DEFAULT_WATER;
         this.decorator = new LOTRBiomeDecorator(this);
         this.field_76762_K.clear();
         this.field_76755_L.clear();
         this.field_76761_J.clear();
         this.field_82914_M.clear();
         if (this.hasDomesticAnimals()) {
            this.field_76762_K.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(EntityCow.class, 8, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 5, 4, 4));
         } else {
            this.field_76762_K.add(new SpawnListEntry(EntitySheep.class, 12, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(LOTREntityWildBoar.class, 10, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(EntityChicken.class, 8, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(LOTREntityDeer.class, 10, 4, 4));
            this.field_76762_K.add(new SpawnListEntry(LOTREntityAurochs.class, 6, 4, 4));
         }

         this.field_76755_L.add(new SpawnListEntry(LOTREntityFish.class, 10, 4, 4));
         this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 8, 4, 4));
         this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityRabbit.class, 8, 4, 4));
         this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
         this.field_82914_M.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
         this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
         this.invasionSpawns = new LOTRBiomeInvasionSpawns(this);
      }
   }

   protected void addBiomeVariant(LOTRBiomeVariant v) {
      this.addBiomeVariant(v, 1.0F);
   }

   protected void addBiomeVariant(LOTRBiomeVariant v, float f) {
      if (v.variantScale == LOTRBiomeVariant.VariantScale.ALL) {
         this.biomeVariantsLarge.add(v, f);
         this.biomeVariantsSmall.add(v, f);
      } else if (v.variantScale == LOTRBiomeVariant.VariantScale.LARGE) {
         this.biomeVariantsLarge.add(v, f);
      } else if (v.variantScale == LOTRBiomeVariant.VariantScale.SMALL) {
         this.biomeVariantsSmall.add(v, f);
      }

   }

   protected void addBiomeVariantSet(LOTRBiomeVariant[] set) {
      LOTRBiomeVariant[] var2 = set;
      int var3 = set.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRBiomeVariant v = var2[var4];
         this.addBiomeVariant(v);
      }

   }

   protected void clearBiomeVariants() {
      this.biomeVariantsLarge.clear();
      this.biomeVariantsSmall.clear();
      this.variantChance = 0.4F;
   }

   public LOTRBiomeVariantList getBiomeVariantsLarge() {
      return this.biomeVariantsLarge;
   }

   public LOTRBiomeVariantList getBiomeVariantsSmall() {
      return this.biomeVariantsSmall;
   }

   public LOTRBiome setTemperatureRainfall(float f, float f1) {
      super.func_76732_a(f, f1);
      return this;
   }

   public boolean hasSeasonalGrass() {
      return this.field_76750_F > 0.3F && this.field_76750_F < 1.0F;
   }

   public LOTRBiome setMinMaxHeight(float f, float f1) {
      this.heightBaseParameter = f;
      f -= 2.0F;
      f += 0.2F;
      this.field_76748_D = f;
      this.field_76749_E = f1 / 2.0F;
      return this;
   }

   public boolean isWateryBiome() {
      return this.heightBaseParameter < 0.0F;
   }

   public LOTRBiome setColor(int color) {
      color |= -16777216;
      Integer existingBiomeID = (Integer)this.biomeDimension.colorsToBiomeIDs.get(color);
      if (existingBiomeID != null) {
         throw new RuntimeException("LOTR biome (ID " + this.field_76756_M + ") is duplicating the color of another LOTR biome (ID " + existingBiomeID + ")");
      } else {
         this.biomeDimension.colorsToBiomeIDs.put(color, this.field_76756_M);
         return (LOTRBiome)super.func_76739_b(color);
      }
   }

   public LOTRBiome setBiomeName(String s) {
      return (LOTRBiome)super.func_76735_a(s);
   }

   public final String getBiomeDisplayName() {
      return StatCollector.func_74838_a("lotr.biome." + this.field_76791_y + ".name");
   }

   public final void plantFlower(World world, Random rand, int x, int y, int z) {
      FlowerEntry flower = this.getRandomFlower(world, rand, x, y, z);
      if (flower != null && flower.block != null && flower.block.func_149718_j(world, x, y, z)) {
         world.func_147465_d(x, y, z, flower.block, flower.metadata, 3);
      }
   }

   public FlowerEntry getRandomFlower(World world, Random rand, int i, int j, int k) {
      return (FlowerEntry)WeightedRandom.func_76271_a(rand, this.flowers);
   }

   protected void registerPlainsFlowers() {
      this.flowers.clear();
      this.addFlower(Blocks.field_150328_O, 4, 3);
      this.addFlower(Blocks.field_150328_O, 5, 3);
      this.addFlower(Blocks.field_150328_O, 6, 3);
      this.addFlower(Blocks.field_150328_O, 7, 3);
      this.addFlower(Blocks.field_150328_O, 0, 20);
      this.addFlower(Blocks.field_150328_O, 3, 20);
      this.addFlower(Blocks.field_150328_O, 8, 20);
      this.addFlower(Blocks.field_150327_N, 0, 30);
      this.addFlower(LOTRMod.bluebell, 0, 5);
      this.addFlower(LOTRMod.marigold, 0, 10);
      this.addFlower(LOTRMod.lavender, 0, 5);
   }

   protected void registerRhunPlainsFlowers() {
      this.registerPlainsFlowers();
      this.addFlower(LOTRMod.marigold, 0, 10);
      this.addFlower(LOTRMod.rhunFlower, 0, 10);
      this.addFlower(LOTRMod.rhunFlower, 1, 10);
      this.addFlower(LOTRMod.rhunFlower, 2, 10);
      this.addFlower(LOTRMod.rhunFlower, 3, 10);
      this.addFlower(LOTRMod.rhunFlower, 4, 10);
   }

   protected void registerForestFlowers() {
      this.flowers.clear();
      this.addDefaultFlowers();
      this.addFlower(LOTRMod.bluebell, 0, 5);
      this.addFlower(LOTRMod.marigold, 0, 10);
   }

   protected void registerRhunForestFlowers() {
      this.registerForestFlowers();
      this.addFlower(LOTRMod.marigold, 0, 10);
      this.addFlower(LOTRMod.rhunFlower, 0, 10);
      this.addFlower(LOTRMod.rhunFlower, 1, 10);
      this.addFlower(LOTRMod.rhunFlower, 2, 10);
      this.addFlower(LOTRMod.rhunFlower, 3, 10);
      this.addFlower(LOTRMod.rhunFlower, 4, 10);
   }

   protected void registerJungleFlowers() {
      this.flowers.clear();
      this.addDefaultFlowers();
      this.addFlower(LOTRMod.haradFlower, 2, 20);
      this.addFlower(LOTRMod.haradFlower, 3, 20);
   }

   protected void registerMountainsFlowers() {
      this.flowers.clear();
      this.addDefaultFlowers();
      this.addFlower(Blocks.field_150328_O, 1, 10);
      this.addFlower(LOTRMod.bluebell, 0, 5);
   }

   protected void registerTaigaFlowers() {
      this.flowers.clear();
      this.addDefaultFlowers();
      this.addFlower(Blocks.field_150328_O, 1, 10);
      this.addFlower(LOTRMod.bluebell, 0, 5);
   }

   protected void registerSavannaFlowers() {
      this.flowers.clear();
      this.addDefaultFlowers();
   }

   protected void registerSwampFlowers() {
      this.flowers.clear();
      this.addDefaultFlowers();
   }

   protected void registerHaradFlowers() {
      this.flowers.clear();
      this.addFlower(LOTRMod.haradFlower, 0, 10);
      this.addFlower(LOTRMod.haradFlower, 1, 10);
      this.addFlower(LOTRMod.haradFlower, 2, 5);
      this.addFlower(LOTRMod.haradFlower, 3, 5);
   }

   protected void registerTravellingTrader(Class entityClass) {
      this.spawnableTraders.add(entityClass);
      LOTREventSpawner.createTraderSpawner(entityClass);
   }

   protected final void clearTravellingTraders() {
      this.spawnableTraders.clear();
   }

   public final boolean canSpawnTravellingTrader(Class entityClass) {
      return this.spawnableTraders.contains(entityClass);
   }

   protected final void setBanditChance(LOTREventSpawner.EventChance c) {
      this.banditChance = c;
   }

   public final LOTREventSpawner.EventChance getBanditChance() {
      return this.banditChance;
   }

   protected final void setBanditEntityClass(Class c) {
      this.banditEntityClass = c;
   }

   public final Class getBanditEntityClass() {
      return this.banditEntityClass == null ? LOTREntityBandit.class : this.banditEntityClass;
   }

   public void addBiomeF3Info(List info, World world, LOTRBiomeVariant variant, int i, int j, int k) {
      int colorRGB = this.field_76790_z & 16777215;

      String colorString;
      for(colorString = Integer.toHexString(colorRGB); colorString.length() < 6; colorString = "0" + colorString) {
      }

      info.add("Middle-earth biome: " + this.getBiomeDisplayName() + ", ID: " + this.field_76756_M + ", c: #" + colorString);
      info.add("Variant: " + variant.variantName + ", loaded: " + LOTRBiomeVariantStorage.getSize(world));
   }

   protected boolean hasDomesticAnimals() {
      return false;
   }

   public boolean hasSky() {
      return true;
   }

   public LOTRAchievement getBiomeAchievement() {
      return null;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return null;
   }

   public abstract LOTRMusicRegion.Sub getBiomeMusic();

   public boolean isHiddenBiome() {
      return false;
   }

   public boolean isRiver() {
      return false;
   }

   public boolean getEnableRiver() {
      return true;
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.PATH;
   }

   public LOTRRoadType.BridgeType getBridgeBlock() {
      return LOTRRoadType.BridgeType.DEFAULT;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      int seaLevel = 63;
      double stoneNoiseFiller = this.modifyStoneNoiseForFiller(stoneNoise);
      int fillerDepthBase = (int)(stoneNoiseFiller / 4.0D + 5.0D + random.nextDouble() * 0.25D);
      int fillerDepth = -1;
      Block top = this.field_76752_A;
      byte topMeta = (byte)this.topBlockMeta;
      Block filler = this.field_76753_B;
      byte fillerMeta = (byte)this.fillerBlockMeta;
      float pdzRand;
      if (this.enableRocky && height >= 90) {
         float hFactor = (float)(height - 90) / 10.0F;
         pdzRand = 1.2F - hFactor * 0.2F;
         pdzRand = Math.max(pdzRand, 0.0F);
         double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.03D, (double)k * 0.03D);
         double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.3D, (double)k * 0.3D);
         if (d1 + d2 > (double)pdzRand) {
            if (random.nextInt(5) == 0) {
               top = Blocks.field_150351_n;
               topMeta = 0;
            } else {
               top = Blocks.field_150348_b;
               topMeta = 0;
            }

            filler = Blocks.field_150348_b;
            fillerMeta = 0;
            int prevHeight = height;
            if (random.nextInt(20) == 0) {
               ++height;
            }

            for(int j = height; j >= prevHeight; --j) {
               int index = xzIndex * ySize + j;
               blocks[index] = top;
               meta[index] = topMeta;
            }
         }
      }

      boolean podzol;
      if (this.enablePodzol) {
         podzol = false;
         if (this.field_76752_A == Blocks.field_150349_c) {
            pdzRand = (float)this.decorator.treesPerChunk + this.getTreeIncreaseChance();
            pdzRand = Math.max(pdzRand, variant.treeFactor * 0.5F);
            if (pdzRand >= 1.0F) {
               float thresh = 0.8F;
               thresh -= pdzRand * 0.15F;
               thresh = Math.max(thresh, 0.0F);
               double d = 0.06D;
               double randNoise = biomeTerrainNoise.func_151601_a((double)i * d, (double)k * d);
               if (randNoise > (double)thresh) {
                  podzol = true;
               }
            }
         }

         if (podzol) {
            terrainRand.setSeed(world.func_72905_C());
            terrainRand.setSeed(terrainRand.nextLong() + (long)i * 4668095025L + (long)k * 1387590552L ^ world.func_72905_C());
            pdzRand = terrainRand.nextFloat();
            if (pdzRand < 0.35F) {
               top = Blocks.field_150346_d;
               topMeta = 2;
            } else if (pdzRand < 0.5F) {
               top = Blocks.field_150346_d;
               topMeta = 1;
            } else if (pdzRand < 0.51F) {
               top = Blocks.field_150351_n;
               topMeta = 0;
            }
         }
      }

      podzol = variant.hasMarsh;
      if (podzol) {
         double d1 = LOTRBiomeVariant.marshNoise.func_151601_a((double)i * 0.1D, (double)k * 0.1D);
         if (d1 > -0.1D) {
            for(int j = ySize - 1; j >= 0; --j) {
               int index = xzIndex * ySize + j;
               if (blocks[index] == null || blocks[index].func_149688_o() != Material.field_151579_a) {
                  if (j == seaLevel - 1 && blocks[index] != Blocks.field_150355_j) {
                     blocks[index] = Blocks.field_150355_j;
                  }
                  break;
               }
            }
         }
      }

      int j;
      for(j = ySize - 1; j >= 0; --j) {
         int index = xzIndex * ySize + j;
         if (j <= 0 + random.nextInt(5)) {
            blocks[index] = Blocks.field_150357_h;
         } else {
            Block block = blocks[index];
            if (block == Blocks.field_150350_a) {
               fillerDepth = -1;
            } else if (block == Blocks.field_150348_b) {
               if (fillerDepth == -1) {
                  if (fillerDepthBase <= 0) {
                     top = Blocks.field_150350_a;
                     topMeta = 0;
                     filler = Blocks.field_150348_b;
                     fillerMeta = 0;
                  } else if (j >= seaLevel - 4 && j <= seaLevel + 1) {
                     top = this.field_76752_A;
                     topMeta = (byte)this.topBlockMeta;
                     filler = this.field_76753_B;
                     fillerMeta = (byte)this.fillerBlockMeta;
                  }

                  if (j < seaLevel && top == Blocks.field_150350_a) {
                     top = Blocks.field_150355_j;
                     topMeta = 0;
                  }

                  fillerDepth = fillerDepthBase;
                  if (j >= seaLevel - 1) {
                     blocks[index] = top;
                     meta[index] = topMeta;
                  } else {
                     blocks[index] = filler;
                     meta[index] = fillerMeta;
                  }
               } else if (fillerDepth > 0) {
                  --fillerDepth;
                  blocks[index] = filler;
                  meta[index] = fillerMeta;
                  if (fillerDepth == 0) {
                     boolean sand = false;
                     if (filler == Blocks.field_150354_m) {
                        if (fillerMeta == 1) {
                           filler = LOTRMod.redSandstone;
                           fillerMeta = 0;
                        } else {
                           filler = Blocks.field_150322_A;
                           fillerMeta = 0;
                        }

                        sand = true;
                     }

                     if (filler == LOTRMod.whiteSand) {
                        filler = LOTRMod.whiteSandstone;
                        fillerMeta = 0;
                        sand = true;
                     }

                     if (sand) {
                        fillerDepth = 10 + random.nextInt(4);
                     }
                  }

                  if ((this instanceof LOTRBiomeGenGondor || this instanceof LOTRBiomeGenIthilien || this instanceof LOTRBiomeGenDorEnErnil) && fillerDepth == 0 && filler == this.field_76753_B) {
                     fillerDepth = 8 + random.nextInt(3);
                     filler = LOTRMod.rock;
                     fillerMeta = 1;
                  } else if ((this instanceof LOTRBiomeGenRohan || this instanceof LOTRBiomeGenAdornland) && fillerDepth == 0 && filler == this.field_76753_B) {
                     fillerDepth = 8 + random.nextInt(3);
                     filler = LOTRMod.rock;
                     fillerMeta = 2;
                  } else if (this instanceof LOTRBiomeGenDorwinion && fillerDepth == 0 && this.field_76753_B != LOTRMod.rock && filler == this.field_76753_B) {
                     fillerDepth = 6 + random.nextInt(3);
                     filler = LOTRMod.rock;
                     fillerMeta = 5;
                  }
               }
            }
         }
      }

      j = (int)(stoneNoise * 6.0D + 2.0D + random.nextDouble() * 0.25D);
      this.generateMountainTerrain(world, random, blocks, meta, i, k, xzIndex, ySize, height, j, variant);
      variant.generateVariantTerrain(world, random, blocks, meta, i, k, height, this);
   }

   protected double modifyStoneNoiseForFiller(double stoneNoise) {
      return stoneNoise;
   }

   protected void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, LOTRBiomeVariant variant) {
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      this.decorator.decorate(world, random, i, k);
   }

   public LOTRBiomeSpawnList getNPCSpawnList(World world, Random random, int i, int j, int k, LOTRBiomeVariant variant) {
      return this.npcSpawnList;
   }

   public final boolean isDwarvenBiome(World world) {
      if (this.initDwarven) {
         return this.isDwarven;
      } else {
         this.initDwarven = true;
         this.isDwarven = this.npcSpawnList.containsEntityClassByDefault(LOTREntityDwarf.class, world) && !this.npcSpawnList.containsEntityClassByDefault(LOTREntityWickedDwarf.class, world);
         return this.isDwarven;
      }
   }

   public List func_76747_a(EnumCreatureType creatureType) {
      return creatureType == creatureType_LOTRAmbient ? this.spawnableLOTRAmbientList : super.func_76747_a(creatureType);
   }

   public float getChanceToSpawnAnimals() {
      return 1.0F;
   }

   public boolean canSpawnHostilesInDay() {
      return false;
   }

   public final WorldGenAbstractTree func_150567_a(Random random) {
      LOTRTreeType tree = this.decorator.getRandomTree(random);
      return tree.create(false, random);
   }

   public final WorldGenAbstractTree getTreeGen(World world, Random random, int i, int j, int k) {
      LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)world.func_72959_q();
      LOTRBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
      LOTRTreeType tree = this.decorator.getRandomTreeForVariant(random, variant);
      return tree.create(false, random);
   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public final WorldGenerator func_76730_b(Random random) {
      LOTRBiome.GrassBlockAndMeta obj = this.getRandomGrass(random);
      return new WorldGenTallGrass(obj.block, obj.meta);
   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      boolean fern = this.decorator.enableFern;
      boolean special = this.decorator.enableSpecialGrasses;
      if (fern && random.nextInt(3) == 0) {
         return new LOTRBiome.GrassBlockAndMeta(Blocks.field_150329_H, 2);
      } else if (special && random.nextInt(500) == 0) {
         return new LOTRBiome.GrassBlockAndMeta(LOTRMod.flaxPlant, 0);
      } else if (random.nextInt(4) > 0) {
         if (special) {
            if (random.nextInt(200) == 0) {
               return new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 3);
            }

            if (random.nextInt(16) == 0) {
               return new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 1);
            }

            if (random.nextInt(10) == 0) {
               return new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 2);
            }
         }

         return random.nextInt(80) == 0 ? new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 4) : new LOTRBiome.GrassBlockAndMeta(LOTRMod.tallGrass, 0);
      } else {
         return random.nextInt(3) == 0 ? new LOTRBiome.GrassBlockAndMeta(LOTRMod.clover, 0) : new LOTRBiome.GrassBlockAndMeta(Blocks.field_150329_H, 1);
      }
   }

   public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
      WorldGenDoublePlant generator = new WorldGenDoublePlant();
      if (this.decorator.enableFern && random.nextInt(4) == 0) {
         generator.func_150548_a(3);
      } else {
         generator.func_150548_a(2);
      }

      return generator;
   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
      int i = random.nextInt(3);
      switch(i) {
      case 0:
         doubleFlowerGen.func_150548_a(1);
         break;
      case 1:
         doubleFlowerGen.func_150548_a(4);
         break;
      case 2:
         doubleFlowerGen.func_150548_a(5);
      }

      return doubleFlowerGen;
   }

   public int spawnCountMultiplier() {
      return 1;
   }

   public BiomeGenBase func_150566_k() {
      return this;
   }

   public boolean func_76738_d() {
      return !this.func_76746_c() && super.func_76738_d();
   }

   public boolean getEnableRain() {
      return this.field_76765_S;
   }

   public boolean func_76746_c() {
      return LOTRMod.isChristmas() && LOTRMod.proxy.isClient() ? true : super.func_76746_c();
   }

   public int getSnowHeight() {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public int func_150558_b(int i, int j, int k) {
      int grassColor;
      if (this.biomeColors.grass != null) {
         grassColor = this.biomeColors.grass.getRGB();
      } else {
         grassColor = this.getBaseGrassColor(i, j, k);
      }

      return grassColor;
   }

   @SideOnly(Side.CLIENT)
   public final int getBaseGrassColor(int i, int j, int k) {
      float temp = this.func_150564_a(i, j, k);
      float rain = this.field_76751_G;
      WorldChunkManager wcMgr = LOTRMod.proxy.getClientWorld().func_72959_q();
      if (wcMgr instanceof LOTRWorldChunkManager) {
         LOTRBiomeVariant variant = ((LOTRWorldChunkManager)wcMgr).getBiomeVariantAt(i, k);
         temp += variant.tempBoost;
         rain += variant.rainBoost;
      }

      temp = MathHelper.func_76131_a(temp, 0.0F, 1.0F);
      rain = MathHelper.func_76131_a(rain, 0.0F, 1.0F);
      return ColorizerGrass.func_77480_a((double)temp, (double)rain);
   }

   @SideOnly(Side.CLIENT)
   public int func_150571_c(int i, int j, int k) {
      int folgColor;
      if (this.biomeColors.foliage != null) {
         folgColor = this.biomeColors.foliage.getRGB();
      } else {
         folgColor = this.getBaseFoliageColor(i, j, k);
      }

      return folgColor;
   }

   @SideOnly(Side.CLIENT)
   public final int getBaseFoliageColor(int i, int j, int k) {
      LOTRBiomeVariant variant = ((LOTRWorldChunkManager)LOTRMod.proxy.getClientWorld().func_72959_q()).getBiomeVariantAt(i, k);
      float temp = this.func_150564_a(i, j, k) + variant.tempBoost;
      float rain = this.field_76751_G + variant.rainBoost;
      temp = MathHelper.func_76131_a(temp, 0.0F, 1.0F);
      rain = MathHelper.func_76131_a(rain, 0.0F, 1.0F);
      return ColorizerFoliage.func_77470_a((double)temp, (double)rain);
   }

   @SideOnly(Side.CLIENT)
   public final int func_76731_a(float f) {
      if (LOTRTickHandlerClient.scrapTraderMisbehaveTick > 0) {
         return 0;
      } else {
         return this.biomeColors.sky != null ? this.biomeColors.sky.getRGB() : super.func_76731_a(f);
      }
   }

   @SideOnly(Side.CLIENT)
   public final int getBaseSkyColorByTemp(int i, int j, int k) {
      return super.func_76731_a(this.func_150564_a(i, j, k));
   }

   public final Vec3 getCloudColor(Vec3 clouds) {
      if (this.biomeColors.clouds != null) {
         float[] colors = this.biomeColors.clouds.getColorComponents((float[])null);
         clouds.field_72450_a *= (double)colors[0];
         clouds.field_72448_b *= (double)colors[1];
         clouds.field_72449_c *= (double)colors[2];
      }

      return clouds;
   }

   public final Vec3 getFogColor(Vec3 fog) {
      if (this.biomeColors.fog != null) {
         float[] colors = this.biomeColors.fog.getColorComponents((float[])null);
         fog.field_72450_a *= (double)colors[0];
         fog.field_72448_b *= (double)colors[1];
         fog.field_72449_c *= (double)colors[2];
      }

      return fog;
   }

   public final boolean hasFog() {
      return this.biomeColors.foggy;
   }

   public static void updateWaterColor(int i, int j, int k) {
      int min = 0;
      int max = waterLimitSouth - waterLimitNorth;
      float latitude = (float)MathHelper.func_76125_a(k - waterLimitNorth, min, max) / (float)max;
      float[] northColors = waterColorNorth.getColorComponents((float[])null);
      float[] southColors = waterColorSouth.getColorComponents((float[])null);
      float dR = southColors[0] - northColors[0];
      float dG = southColors[1] - northColors[1];
      float dB = southColors[2] - northColors[2];
      float r = dR * latitude;
      float g = dG * latitude;
      float b = dB * latitude;
      r += northColors[0];
      g += northColors[1];
      b += northColors[2];
      Color water = new Color(r, g, b);
      int waterRGB = water.getRGB();
      LOTRDimension[] var16 = LOTRDimension.values();
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         LOTRDimension dimension = var16[var18];
         LOTRBiome[] var20 = dimension.biomeList;
         int var21 = var20.length;

         for(int var22 = 0; var22 < var21; ++var22) {
            LOTRBiome biome = var20[var22];
            if (biome != null && !biome.biomeColors.hasCustomWater()) {
               biome.biomeColors.updateWater(waterRGB);
            }
         }
      }

   }

   static {
      correctCreatureTypeParams = new Class[][]{{EnumCreatureType.class, Class.class, Integer.TYPE, Material.class, Boolean.TYPE, Boolean.TYPE}};
      creatureType_LOTRAmbient = (EnumCreatureType)EnumHelper.addEnum(correctCreatureTypeParams, EnumCreatureType.class, "LOTRAmbient", new Object[]{LOTRAmbientCreature.class, 45, Material.field_151579_a, true, false});
      biomeTerrainNoise = new NoiseGeneratorPerlin(new Random(1955L), 1);
      terrainRand = new Random();
      waterColorNorth = new Color(602979);
      waterColorSouth = new Color(4973293);
      waterLimitNorth = -40000;
      waterLimitSouth = 160000;
   }

   public static class GrassBlockAndMeta {
      public final Block block;
      public final int meta;

      public GrassBlockAndMeta(Block b, int i) {
         this.block = b;
         this.meta = i;
      }
   }

   public static class BiomeTerrain {
      private LOTRBiome theBiome;
      private double xzScale = -1.0D;
      private double heightStretchFactor = -1.0D;

      public BiomeTerrain(LOTRBiome biome) {
         this.theBiome = biome;
      }

      public void setXZScale(double d) {
         this.xzScale = d;
      }

      public void resetXZScale() {
         this.setXZScale(-1.0D);
      }

      public boolean hasXZScale() {
         return this.xzScale != -1.0D;
      }

      public double getXZScale() {
         return this.xzScale;
      }

      public void setHeightStretchFactor(double d) {
         this.heightStretchFactor = d;
      }

      public void resetHeightStretchFactor() {
         this.setHeightStretchFactor(-1.0D);
      }

      public boolean hasHeightStretchFactor() {
         return this.heightStretchFactor != -1.0D;
      }

      public double getHeightStretchFactor() {
         return this.heightStretchFactor;
      }
   }

   public static class BiomeColors {
      private LOTRBiome theBiome;
      private Color grass;
      private Color foliage;
      private Color sky;
      private Color clouds;
      private Color fog;
      private boolean foggy;
      private boolean hasCustomWater = false;
      private static int DEFAULT_WATER = 7186907;

      public BiomeColors(LOTRBiome biome) {
         this.theBiome = biome;
      }

      public void setGrass(int rgb) {
         this.grass = new Color(rgb);
      }

      public void resetGrass() {
         this.grass = null;
      }

      public void setFoliage(int rgb) {
         this.foliage = new Color(rgb);
      }

      public void resetFoliage() {
         this.foliage = null;
      }

      public void setSky(int rgb) {
         this.sky = new Color(rgb);
      }

      public void resetSky() {
         this.sky = null;
      }

      public void setClouds(int rgb) {
         this.clouds = new Color(rgb);
      }

      public void resetClouds() {
         this.clouds = null;
      }

      public void setFog(int rgb) {
         this.fog = new Color(rgb);
      }

      public void resetFog() {
         this.fog = null;
      }

      public void setFoggy(boolean flag) {
         this.foggy = flag;
      }

      public void setWater(int rgb) {
         this.theBiome.field_76759_H = rgb;
         if (rgb != DEFAULT_WATER) {
            this.hasCustomWater = true;
         }

      }

      public void resetWater() {
         this.setWater(DEFAULT_WATER);
      }

      public boolean hasCustomWater() {
         return this.hasCustomWater;
      }

      public void updateWater(int rgb) {
         this.theBiome.field_76759_H = rgb;
      }
   }
}
