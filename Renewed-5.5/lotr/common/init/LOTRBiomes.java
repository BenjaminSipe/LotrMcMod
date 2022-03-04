package lotr.common.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotr.common.world.biome.AndrastBiome;
import lotr.common.world.biome.AnduinHillsBiome;
import lotr.common.world.biome.AnduinValeBiome;
import lotr.common.world.biome.AnfalasBiome;
import lotr.common.world.biome.AngmarBiome;
import lotr.common.world.biome.AnorienBiome;
import lotr.common.world.biome.BlackrootValeBiome;
import lotr.common.world.biome.BlueMountainsBiome;
import lotr.common.world.biome.BreelandBiome;
import lotr.common.world.biome.BrownLandsBiome;
import lotr.common.world.biome.ChetwoodBiome;
import lotr.common.world.biome.ColdfellsBiome;
import lotr.common.world.biome.DagorladBiome;
import lotr.common.world.biome.DaleBiome;
import lotr.common.world.biome.DeadMarshesBiome;
import lotr.common.world.biome.DorEnErnilBiome;
import lotr.common.world.biome.DorwinionBiome;
import lotr.common.world.biome.DruwaithIaurBiome;
import lotr.common.world.biome.DunlandBiome;
import lotr.common.world.biome.EasternDesolationBiome;
import lotr.common.world.biome.EmynMuilBiome;
import lotr.common.world.biome.EnedwaithBiome;
import lotr.common.world.biome.EregionBiome;
import lotr.common.world.biome.EriadorBiome;
import lotr.common.world.biome.EthirAnduinBiome;
import lotr.common.world.biome.EttenmoorsBiome;
import lotr.common.world.biome.FangornBiome;
import lotr.common.world.biome.ForochelBiome;
import lotr.common.world.biome.ForodwaithBiome;
import lotr.common.world.biome.FurtherGondorBiome;
import lotr.common.world.biome.GladdenFieldsBiome;
import lotr.common.world.biome.GorgorothBiome;
import lotr.common.world.biome.GreyMountainsBiome;
import lotr.common.world.biome.HaradDesertBiome;
import lotr.common.world.biome.HarnennorBiome;
import lotr.common.world.biome.HarondorBiome;
import lotr.common.world.biome.IronHillsBiome;
import lotr.common.world.biome.IthilienBiome;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeWrapper;
import lotr.common.world.biome.LakeBiome;
import lotr.common.world.biome.LamedonBiome;
import lotr.common.world.biome.LebenninBiome;
import lotr.common.world.biome.LindonBiome;
import lotr.common.world.biome.LoneLandsBiome;
import lotr.common.world.biome.LongMarshesBiome;
import lotr.common.world.biome.LossarnachBiome;
import lotr.common.world.biome.LostladenBiome;
import lotr.common.world.biome.LothlorienBiome;
import lotr.common.world.biome.MERiverBiome;
import lotr.common.world.biome.MidgewaterBiome;
import lotr.common.world.biome.MirkwoodBiome;
import lotr.common.world.biome.MistyMountainsBiome;
import lotr.common.world.biome.MordorBiome;
import lotr.common.world.biome.MorgulValeBiome;
import lotr.common.world.biome.MouthsOfEntwashBiome;
import lotr.common.world.biome.NanCurunirBiome;
import lotr.common.world.biome.NindalfBiome;
import lotr.common.world.biome.NorthlandsBiome;
import lotr.common.world.biome.NurnBiome;
import lotr.common.world.biome.OldForestBiome;
import lotr.common.world.biome.PelargirBiome;
import lotr.common.world.biome.PinnathGelinBiome;
import lotr.common.world.biome.RivendellBiome;
import lotr.common.world.biome.RohanBiome;
import lotr.common.world.biome.SeaBiome;
import lotr.common.world.biome.ShireBiome;
import lotr.common.world.biome.ShireWoodlandsBiome;
import lotr.common.world.biome.SouthronCoastsBiome;
import lotr.common.world.biome.SwanfleetBiome;
import lotr.common.world.biome.TolfalasBiome;
import lotr.common.world.biome.TowerHillsBiome;
import lotr.common.world.biome.TrollshawsBiome;
import lotr.common.world.biome.UmbarBiome;
import lotr.common.world.biome.VanillaPlaceholderLOTRBiome;
import lotr.common.world.biome.WhiteMountainsBiome;
import lotr.common.world.biome.WilderlandBiome;
import lotr.common.world.biome.WoodlandRealmBiome;
import lotr.common.world.biome.surface.MiddleEarthSurfaceBuilder;
import lotr.common.world.biome.surface.MiddleEarthSurfaceConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LOTRBiomes {
   public static final DeferredRegister BIOMES;
   public static final DeferredRegister SURFACE_BUILDERS;
   private static final List PRE_REGISTRY;
   private static final List BIOME_WRAPPER_OBJECTS;
   private static final Map BIOME_WRAPPER_OBJECTS_BY_NAME;
   public static final PreRegisteredLOTRBiome SHIRE;
   public static final PreRegisteredLOTRBiome MORDOR;
   public static final PreRegisteredLOTRBiome ANORIEN;
   public static final PreRegisteredLOTRBiome ROHAN;
   public static final PreRegisteredLOTRBiome MISTY_MOUNTAINS;
   public static final PreRegisteredLOTRBiome SHIRE_WOODLANDS;
   public static final PreRegisteredLOTRBiome RIVER;
   public static final PreRegisteredLOTRBiome TROLLSHAWS;
   public static final PreRegisteredLOTRBiome BLUE_MOUNTAINS;
   public static final PreRegisteredLOTRBiome ERIADOR;
   public static final PreRegisteredLOTRBiome LONE_LANDS;
   public static final PreRegisteredLOTRBiome ITHILIEN;
   public static final PreRegisteredLOTRBiome BROWN_LANDS;
   public static final PreRegisteredLOTRBiome LOTHLORIEN;
   public static final PreRegisteredLOTRBiome IRON_HILLS;
   public static final PreRegisteredLOTRBiome DUNLAND;
   public static final PreRegisteredLOTRBiome EMYN_MUIL;
   public static final PreRegisteredLOTRBiome LINDON;
   public static final PreRegisteredLOTRBiome SOUTHRON_COASTS;
   public static final PreRegisteredLOTRBiome NAN_CURUNIR;
   public static final PreRegisteredLOTRBiome FORODWAITH;
   public static final PreRegisteredLOTRBiome EREGION;
   public static final PreRegisteredLOTRBiome MIRKWOOD;
   public static final PreRegisteredLOTRBiome GREY_MOUNTAINS;
   public static final PreRegisteredLOTRBiome WHITE_MOUNTAINS;
   public static final PreRegisteredLOTRBiome FANGORN;
   public static final PreRegisteredLOTRBiome WOODLAND_REALM;
   public static final PreRegisteredLOTRBiome DALE;
   public static final PreRegisteredLOTRBiome ANGMAR;
   public static final PreRegisteredLOTRBiome HARONDOR;
   public static final PreRegisteredLOTRBiome ENEDWAITH;
   public static final PreRegisteredLOTRBiome VALES_OF_ANDUIN;
   public static final PreRegisteredLOTRBiome ANDUIN_HILLS;
   public static final PreRegisteredLOTRBiome WILDERLAND;
   public static final PreRegisteredLOTRBiome OLD_FOREST;
   public static final PreRegisteredLOTRBiome BREELAND;
   public static final PreRegisteredLOTRBiome CHETWOOD;
   public static final PreRegisteredLOTRBiome MISTY_MOUNTAINS_FOOTHILLS;
   public static final PreRegisteredLOTRBiome BLUE_MOUNTAINS_FOOTHILLS;
   public static final PreRegisteredLOTRBiome GREY_MOUNTAINS_FOOTHILLS;
   public static final PreRegisteredLOTRBiome WHITE_MOUNTAINS_FOOTHILLS;
   public static final PreRegisteredLOTRBiome MORDOR_MOUNTAINS;
   public static final PreRegisteredLOTRBiome FORODWAITH_MOUNTAINS;
   public static final PreRegisteredLOTRBiome ANGMAR_MOUNTAINS;
   public static final PreRegisteredLOTRBiome NURN;
   public static final PreRegisteredLOTRBiome UMBAR;
   public static final PreRegisteredLOTRBiome HARAD_DESERT;
   public static final PreRegisteredLOTRBiome LINDON_WOODLANDS;
   public static final PreRegisteredLOTRBiome ERIADOR_DOWNS;
   public static final PreRegisteredLOTRBiome LONE_LANDS_HILLS;
   public static final PreRegisteredLOTRBiome NORTHLANDS;
   public static final PreRegisteredLOTRBiome NORTHLANDS_FOREST;
   public static final PreRegisteredLOTRBiome SEA;
   public static final PreRegisteredLOTRBiome ISLAND;
   public static final PreRegisteredLOTRBiome BEACH;
   public static final PreRegisteredLOTRBiome TOLFALAS;
   public static final PreRegisteredLOTRBiome LAKE;
   public static final PreRegisteredLOTRBiome NURNEN;
   public static final PreRegisteredLOTRBiome DOR_EN_ERNIL;
   public static final PreRegisteredLOTRBiome EMYN_EN_ERNIL;
   public static final PreRegisteredLOTRBiome WESTERN_ISLES;
   public static final PreRegisteredLOTRBiome COLDFELLS;
   public static final PreRegisteredLOTRBiome ETTENMOORS;
   public static final PreRegisteredLOTRBiome HARNENNOR;
   public static final PreRegisteredLOTRBiome DAGORLAD;
   public static final PreRegisteredLOTRBiome WHITE_BEACH;
   public static final PreRegisteredLOTRBiome DORWINION;
   public static final PreRegisteredLOTRBiome EMYN_WINION;
   public static final PreRegisteredLOTRBiome WOLD;
   public static final PreRegisteredLOTRBiome MINHIRIATH;
   public static final PreRegisteredLOTRBiome ERYN_VORN;
   public static final PreRegisteredLOTRBiome DRUWAITH_IAUR;
   public static final PreRegisteredLOTRBiome ANDRAST;
   public static final PreRegisteredLOTRBiome LOSSARNACH;
   public static final PreRegisteredLOTRBiome LEBENNIN;
   public static final PreRegisteredLOTRBiome PELARGIR;
   public static final PreRegisteredLOTRBiome LAMEDON;
   public static final PreRegisteredLOTRBiome LAMEDON_HILLS;
   public static final PreRegisteredLOTRBiome BLACKROOT_VALE;
   public static final PreRegisteredLOTRBiome PINNATH_GELIN;
   public static final PreRegisteredLOTRBiome ANFALAS;
   public static final PreRegisteredLOTRBiome NORTHERN_WILDERLAND;
   public static final PreRegisteredLOTRBiome NORTHERN_DALE;
   public static final PreRegisteredLOTRBiome RIVENDELL;
   public static final PreRegisteredLOTRBiome RIVENDELL_HILLS;
   public static final PreRegisteredLOTRBiome FURTHER_GONDOR;
   public static final PreRegisteredLOTRBiome SHIRE_MOORS;
   public static final PreRegisteredLOTRBiome WHITE_DOWNS;
   public static final PreRegisteredLOTRBiome MIDGEWATER;
   public static final PreRegisteredLOTRBiome SWANFLEET;
   public static final PreRegisteredLOTRBiome GLADDEN_FIELDS;
   public static final PreRegisteredLOTRBiome LONG_MARSHES;
   public static final PreRegisteredLOTRBiome NINDALF;
   public static final PreRegisteredLOTRBiome DEAD_MARSHES;
   public static final PreRegisteredLOTRBiome MOUTHS_OF_ENTWASH;
   public static final PreRegisteredLOTRBiome ETHIR_ANDUIN;
   public static final PreRegisteredLOTRBiome SHIRE_MARSHES;
   public static final PreRegisteredLOTRBiome NURN_MARSHES;
   public static final PreRegisteredLOTRBiome GORGOROTH;
   public static final PreRegisteredLOTRBiome FOROCHEL;
   public static final PreRegisteredLOTRBiome LOTHLORIEN_EAVES;
   public static final PreRegisteredLOTRBiome HARAD_HALF_DESERT;
   public static final PreRegisteredLOTRBiome HARAD_DESERT_HILLS;
   public static final PreRegisteredLOTRBiome LOSTLADEN;
   public static final PreRegisteredLOTRBiome EASTERN_DESOLATION;
   public static final PreRegisteredLOTRBiome NORTHERN_MIRKWOOD;
   public static final PreRegisteredLOTRBiome MIRKWOOD_MOUNTAINS;
   public static final PreRegisteredLOTRBiome MORGUL_VALE;
   public static final PreRegisteredLOTRBiome DENSE_NORTHLANDS_FOREST;
   public static final PreRegisteredLOTRBiome SNOWY_NORTHLANDS;
   public static final PreRegisteredLOTRBiome SNOWY_NORTHLANDS_FOREST;
   public static final PreRegisteredLOTRBiome DENSE_SNOWY_NORTHLANDS_FOREST;
   public static final PreRegisteredLOTRBiome TOWER_HILLS;
   public static final PreRegisteredLOTRBiome SOUTHRON_COASTS_FOREST;
   public static final PreRegisteredLOTRBiome UMBAR_FOREST;
   public static final PreRegisteredLOTRBiome UMBAR_HILLS;
   public static final PreRegisteredLOTRBiome FIELD_OF_CORMALLEN;
   public static final PreRegisteredLOTRBiome HILLS_OF_EVENDIM;
   public static final SurfaceBuilder MIDDLE_EARTH_SURFACE;

   public static void register() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      PRE_REGISTRY.forEach((preparedBiome) -> {
         String name = preparedBiome.getName();
         ResourceLocation fullResName = preparedBiome.getRegistryName();
         LOTRBiomeBase lotrBiome = preparedBiome.initialiseAndReturnBiomeWrapper(fullResName);
         BIOME_WRAPPER_OBJECTS.add(lotrBiome);
         BIOME_WRAPPER_OBJECTS_BY_NAME.put(fullResName, lotrBiome);
         BIOMES.register(name, preparedBiome.supplyBiomeInitialiser());
      });
      BIOMES.register(bus);
      SURFACE_BUILDERS.register(bus);
   }

   private static PreRegisteredLOTRBiome prepare(String name, NonNullSupplier wrapperSupplier) {
      PreRegisteredLOTRBiome preparedBiome = new PreRegisteredLOTRBiome(name, wrapperSupplier);
      PRE_REGISTRY.add(preparedBiome);
      return preparedBiome;
   }

   public static List listAllBiomesForProvider(Registry lookupRegistry) {
      return (List)BIOME_WRAPPER_OBJECTS.stream().map((wrapper) -> {
         return (Biome)lookupRegistry.func_230516_a_(RegistryKey.func_240903_a_(Registry.field_239720_u_, wrapper.getBiomeRegistryName()));
      }).collect(Collectors.toList());
   }

   public static List listBiomeNamesForClassicGen() {
      new ArrayList();
      return (List)BIOME_WRAPPER_OBJECTS.stream().filter(LOTRBiomeBase::isMajorBiome).map(LOTRBiomeBase::getBiomeRegistryName).collect(Collectors.toList());
   }

   public static ResourceLocation getBiomeRegistryName(Biome biome, IWorld world) {
      return biome.getRegistryName() != null ? biome.getRegistryName() : getBiomeRegistry(world).func_177774_c(biome);
   }

   public static Biome getBiomeByRegistryName(ResourceLocation biomeName, IWorld world) {
      return (Biome)getBiomeRegistry(world).func_82594_a(biomeName);
   }

   private static MutableRegistry getBiomeRegistry(IWorld world) {
      return world.func_241828_r().func_243612_b(Registry.field_239720_u_);
   }

   public static ServerWorld getServerBiomeContextWorld() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      return server != null ? server.func_241755_D_() : null;
   }

   public static int getBiomeID(PreRegisteredLOTRBiome preparedBiome, IWorld world) {
      return getBiomeIDByRegistryName(preparedBiome.getRegistryName(), world);
   }

   public static int getBiomeID(LOTRBiomeWrapper biomeWrapper, IWorld world) {
      return getBiomeIDByRegistryName(biomeWrapper.getBiomeRegistryName(), world);
   }

   public static int getBiomeIDByRegistryName(ResourceLocation biomeName, IWorld world) {
      MutableRegistry reg = getBiomeRegistry(world);
      return reg.func_148757_b(reg.func_82594_a(biomeName));
   }

   public static int getBiomeID(Biome biome, IWorld world) {
      return getBiomeRegistry(world).func_148757_b(biome);
   }

   public static Biome getBiomeByID(int biomeID, IWorld world) {
      return (Biome)getBiomeRegistry(world).func_148745_a(biomeID);
   }

   public static boolean areBiomesEqual(Biome biome1, Biome biome2, IWorld world) {
      return getBiomeRegistryName(biome1, world).equals(getBiomeRegistryName(biome2, world));
   }

   public static LOTRBiomeWrapper getWrapperFor(Biome biome, IWorld world) {
      ResourceLocation key = getBiomeRegistryName(biome, world);
      return (LOTRBiomeWrapper)(BIOME_WRAPPER_OBJECTS_BY_NAME.containsKey(key) ? (LOTRBiomeWrapper)BIOME_WRAPPER_OBJECTS_BY_NAME.get(key) : VanillaPlaceholderLOTRBiome.makeWrapperFor(world, biome));
   }

   public static boolean isDefaultLOTRBiome(ResourceLocation biomeName) {
      return BIOME_WRAPPER_OBJECTS_BY_NAME.containsKey(biomeName);
   }

   public static ITextComponent getBiomeDisplayName(Biome biome, IWorld world) {
      ResourceLocation key = getBiomeRegistryName(biome, world);
      return new TranslationTextComponent(String.format("biome.%s.%s", key.func_110624_b(), key.func_110623_a()));
   }

   private static SurfaceBuilder preRegSurfaceBuilder(String name, SurfaceBuilder surfaceBuilder) {
      return (SurfaceBuilder)RegistryOrderHelper.preRegObject(SURFACE_BUILDERS, name, surfaceBuilder);
   }

   static {
      BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "lotr");
      SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, "lotr");
      PRE_REGISTRY = new ArrayList();
      BIOME_WRAPPER_OBJECTS = new ArrayList();
      BIOME_WRAPPER_OBJECTS_BY_NAME = new HashMap();
      SHIRE = prepare("shire", () -> {
         return new ShireBiome(true);
      });
      MORDOR = prepare("mordor", () -> {
         return new MordorBiome(true);
      });
      ANORIEN = prepare("anorien", () -> {
         return new AnorienBiome(true);
      });
      ROHAN = prepare("rohan", () -> {
         return new RohanBiome(true);
      });
      MISTY_MOUNTAINS = prepare("misty_mountains", () -> {
         return new MistyMountainsBiome(true);
      });
      SHIRE_WOODLANDS = prepare("shire_woodlands", () -> {
         return new ShireWoodlandsBiome(false);
      });
      RIVER = prepare("river", () -> {
         return new MERiverBiome(false);
      });
      TROLLSHAWS = prepare("trollshaws", () -> {
         return new TrollshawsBiome(true);
      });
      BLUE_MOUNTAINS = prepare("blue_mountains", () -> {
         return new BlueMountainsBiome(true);
      });
      ERIADOR = prepare("eriador", () -> {
         return new EriadorBiome(true);
      });
      LONE_LANDS = prepare("lone_lands", () -> {
         return new LoneLandsBiome(true);
      });
      ITHILIEN = prepare("ithilien", () -> {
         return new IthilienBiome(true);
      });
      BROWN_LANDS = prepare("brown_lands", () -> {
         return new BrownLandsBiome(true);
      });
      LOTHLORIEN = prepare("lothlorien", () -> {
         return new LothlorienBiome(true);
      });
      IRON_HILLS = prepare("iron_hills", () -> {
         return new IronHillsBiome(true);
      });
      DUNLAND = prepare("dunland", () -> {
         return new DunlandBiome(true);
      });
      EMYN_MUIL = prepare("emyn_muil", () -> {
         return new EmynMuilBiome(true);
      });
      LINDON = prepare("lindon", () -> {
         return new LindonBiome(true);
      });
      SOUTHRON_COASTS = prepare("southron_coasts", () -> {
         return new SouthronCoastsBiome(true);
      });
      NAN_CURUNIR = prepare("nan_curunir", () -> {
         return new NanCurunirBiome(true);
      });
      FORODWAITH = prepare("forodwaith", () -> {
         return new ForodwaithBiome(true);
      });
      EREGION = prepare("eregion", () -> {
         return new EregionBiome(true);
      });
      MIRKWOOD = prepare("mirkwood", () -> {
         return new MirkwoodBiome(true);
      });
      GREY_MOUNTAINS = prepare("grey_mountains", () -> {
         return new GreyMountainsBiome(true);
      });
      WHITE_MOUNTAINS = prepare("white_mountains", () -> {
         return new WhiteMountainsBiome(true);
      });
      FANGORN = prepare("fangorn", () -> {
         return new FangornBiome(true);
      });
      WOODLAND_REALM = prepare("woodland_realm", () -> {
         return new WoodlandRealmBiome(true);
      });
      DALE = prepare("dale", () -> {
         return new DaleBiome(true);
      });
      ANGMAR = prepare("angmar", () -> {
         return new AngmarBiome(true);
      });
      HARONDOR = prepare("harondor", () -> {
         return new HarondorBiome(true);
      });
      ENEDWAITH = prepare("enedwaith", () -> {
         return new EnedwaithBiome(true);
      });
      VALES_OF_ANDUIN = prepare("vales_of_anduin", () -> {
         return new AnduinValeBiome(true);
      });
      ANDUIN_HILLS = prepare("anduin_hills", () -> {
         return new AnduinHillsBiome(true);
      });
      WILDERLAND = prepare("wilderland", () -> {
         return new WilderlandBiome(true);
      });
      OLD_FOREST = prepare("old_forest", () -> {
         return new OldForestBiome(true);
      });
      BREELAND = prepare("breeland", () -> {
         return new BreelandBiome(true);
      });
      CHETWOOD = prepare("chetwood", () -> {
         return new ChetwoodBiome(true);
      });
      MISTY_MOUNTAINS_FOOTHILLS = prepare("misty_mountains_foothills", () -> {
         return new MistyMountainsBiome.Foothills(true);
      });
      BLUE_MOUNTAINS_FOOTHILLS = prepare("blue_mountains_foothills", () -> {
         return new BlueMountainsBiome.Foothills(true);
      });
      GREY_MOUNTAINS_FOOTHILLS = prepare("grey_mountains_foothills", () -> {
         return new GreyMountainsBiome.Foothills(true);
      });
      WHITE_MOUNTAINS_FOOTHILLS = prepare("white_mountains_foothills", () -> {
         return new WhiteMountainsBiome.Foothills(true);
      });
      MORDOR_MOUNTAINS = prepare("mordor_mountains", () -> {
         return new MordorBiome.Mountains(true);
      });
      FORODWAITH_MOUNTAINS = prepare("forodwaith_mountains", () -> {
         return new ForodwaithBiome.Mountains(true);
      });
      ANGMAR_MOUNTAINS = prepare("angmar_mountains", () -> {
         return new AngmarBiome.Mountains(true);
      });
      NURN = prepare("nurn", () -> {
         return new NurnBiome(true);
      });
      UMBAR = prepare("umbar", () -> {
         return new UmbarBiome(true);
      });
      HARAD_DESERT = prepare("harad_desert", () -> {
         return new HaradDesertBiome(true);
      });
      LINDON_WOODLANDS = prepare("lindon_woodlands", () -> {
         return new LindonBiome.Woodlands(true);
      });
      ERIADOR_DOWNS = prepare("eriador_downs", () -> {
         return new EriadorBiome.Downs(true);
      });
      LONE_LANDS_HILLS = prepare("lone_lands_hills", () -> {
         return new LoneLandsBiome.Hills(true);
      });
      NORTHLANDS = prepare("northlands", () -> {
         return new NorthlandsBiome(true);
      });
      NORTHLANDS_FOREST = prepare("northlands_forest", () -> {
         return new NorthlandsBiome.Forest(true);
      });
      SEA = prepare("sea", () -> {
         return new SeaBiome(false);
      });
      ISLAND = prepare("island", () -> {
         return new SeaBiome.Island(false);
      });
      BEACH = prepare("beach", () -> {
         return new SeaBiome.Beach(false);
      });
      TOLFALAS = prepare("tolfalas", () -> {
         return new TolfalasBiome(false);
      });
      LAKE = prepare("lake", () -> {
         return new LakeBiome(false);
      });
      NURNEN = prepare("nurnen", () -> {
         return new NurnBiome.Sea(false);
      });
      DOR_EN_ERNIL = prepare("dor_en_ernil", () -> {
         return new DorEnErnilBiome(true);
      });
      EMYN_EN_ERNIL = prepare("emyn_en_ernil", () -> {
         return new DorEnErnilBiome.Hills(true);
      });
      WESTERN_ISLES = prepare("western_isles", () -> {
         return new SeaBiome.WesternIsles(false);
      });
      COLDFELLS = prepare("coldfells", () -> {
         return new ColdfellsBiome(true);
      });
      ETTENMOORS = prepare("ettenmoors", () -> {
         return new EttenmoorsBiome(true);
      });
      HARNENNOR = prepare("harnennor", () -> {
         return new HarnennorBiome(true);
      });
      DAGORLAD = prepare("dagorlad", () -> {
         return new DagorladBiome(true);
      });
      WHITE_BEACH = prepare("white_beach", () -> {
         return new SeaBiome.WhiteBeach(false);
      });
      DORWINION = prepare("dorwinion", () -> {
         return new DorwinionBiome(true);
      });
      EMYN_WINION = prepare("emyn_winion", () -> {
         return new DorwinionBiome.Hills(true);
      });
      WOLD = prepare("wold", () -> {
         return new RohanBiome.Wold(true);
      });
      MINHIRIATH = prepare("minhiriath", () -> {
         return new EriadorBiome.Minhiriath(true);
      });
      ERYN_VORN = prepare("eryn_vorn", () -> {
         return new EriadorBiome.ErynVorn(true);
      });
      DRUWAITH_IAUR = prepare("druwaith_iaur", () -> {
         return new DruwaithIaurBiome(true);
      });
      ANDRAST = prepare("andrast", () -> {
         return new AndrastBiome(true);
      });
      LOSSARNACH = prepare("lossarnach", () -> {
         return new LossarnachBiome(true);
      });
      LEBENNIN = prepare("lebennin", () -> {
         return new LebenninBiome(true);
      });
      PELARGIR = prepare("pelargir", () -> {
         return new PelargirBiome(true);
      });
      LAMEDON = prepare("lamedon", () -> {
         return new LamedonBiome(true);
      });
      LAMEDON_HILLS = prepare("lamedon_hills", () -> {
         return new LamedonBiome.Hills(true);
      });
      BLACKROOT_VALE = prepare("blackroot_vale", () -> {
         return new BlackrootValeBiome(true);
      });
      PINNATH_GELIN = prepare("pinnath_gelin", () -> {
         return new PinnathGelinBiome(true);
      });
      ANFALAS = prepare("anfalas", () -> {
         return new AnfalasBiome(true);
      });
      NORTHERN_WILDERLAND = prepare("northern_wilderland", () -> {
         return new WilderlandBiome.Northern(true);
      });
      NORTHERN_DALE = prepare("northern_dale", () -> {
         return new DaleBiome.Northern(true);
      });
      RIVENDELL = prepare("rivendell", () -> {
         return new RivendellBiome(true);
      });
      RIVENDELL_HILLS = prepare("rivendell_hills", () -> {
         return new RivendellBiome.Hills(true);
      });
      FURTHER_GONDOR = prepare("further_gondor", () -> {
         return new FurtherGondorBiome(true);
      });
      SHIRE_MOORS = prepare("shire_moors", () -> {
         return new ShireBiome.Moors(true);
      });
      WHITE_DOWNS = prepare("white_downs", () -> {
         return new ShireBiome.WhiteDowns(true);
      });
      MIDGEWATER = prepare("midgewater", () -> {
         return new MidgewaterBiome(true);
      });
      SWANFLEET = prepare("swanfleet", () -> {
         return new SwanfleetBiome(true);
      });
      GLADDEN_FIELDS = prepare("gladden_fields", () -> {
         return new GladdenFieldsBiome(true);
      });
      LONG_MARSHES = prepare("long_marshes", () -> {
         return new LongMarshesBiome(true);
      });
      NINDALF = prepare("nindalf", () -> {
         return new NindalfBiome(true);
      });
      DEAD_MARSHES = prepare("dead_marshes", () -> {
         return new DeadMarshesBiome(true);
      });
      MOUTHS_OF_ENTWASH = prepare("mouths_of_entwash", () -> {
         return new MouthsOfEntwashBiome(true);
      });
      ETHIR_ANDUIN = prepare("ethir_anduin", () -> {
         return new EthirAnduinBiome(true);
      });
      SHIRE_MARSHES = prepare("shire_marshes", () -> {
         return new ShireBiome.Marshes(true);
      });
      NURN_MARSHES = prepare("nurn_marshes", () -> {
         return new NurnBiome.Marshes(true);
      });
      GORGOROTH = prepare("gorgoroth", () -> {
         return new GorgorothBiome(true);
      });
      FOROCHEL = prepare("forochel", () -> {
         return new ForochelBiome(true);
      });
      LOTHLORIEN_EAVES = prepare("lothlorien_eaves", () -> {
         return new LothlorienBiome.Eaves(true);
      });
      HARAD_HALF_DESERT = prepare("harad_half_desert", () -> {
         return new HaradDesertBiome.HalfDesert(true);
      });
      HARAD_DESERT_HILLS = prepare("harad_desert_hills", () -> {
         return new HaradDesertBiome.Hills(true);
      });
      LOSTLADEN = prepare("lostladen", () -> {
         return new LostladenBiome(true);
      });
      EASTERN_DESOLATION = prepare("eastern_desolation", () -> {
         return new EasternDesolationBiome(true);
      });
      NORTHERN_MIRKWOOD = prepare("northern_mirkwood", () -> {
         return new MirkwoodBiome.Northern(true);
      });
      MIRKWOOD_MOUNTAINS = prepare("mirkwood_mountains", () -> {
         return new MirkwoodBiome.Mountains(true);
      });
      MORGUL_VALE = prepare("morgul_vale", () -> {
         return new MorgulValeBiome(true);
      });
      DENSE_NORTHLANDS_FOREST = prepare("dense_northlands_forest", () -> {
         return new NorthlandsBiome.DenseForest(false);
      });
      SNOWY_NORTHLANDS = prepare("snowy_northlands", () -> {
         return new NorthlandsBiome.SnowyNorthlands(true);
      });
      SNOWY_NORTHLANDS_FOREST = prepare("snowy_northlands_forest", () -> {
         return new NorthlandsBiome.SnowyForest(true);
      });
      DENSE_SNOWY_NORTHLANDS_FOREST = prepare("dense_snowy_northlands_forest", () -> {
         return new NorthlandsBiome.DenseSnowyForest(false);
      });
      TOWER_HILLS = prepare("tower_hills", () -> {
         return new TowerHillsBiome(true);
      });
      SOUTHRON_COASTS_FOREST = prepare("southron_coasts_forest", () -> {
         return new SouthronCoastsBiome.Forest(true);
      });
      UMBAR_FOREST = prepare("umbar_forest", () -> {
         return new UmbarBiome.Forest(true);
      });
      UMBAR_HILLS = prepare("umbar_hills", () -> {
         return new UmbarBiome.Hills(true);
      });
      FIELD_OF_CORMALLEN = prepare("field_of_cormallen", () -> {
         return new IthilienBiome.Cormallen(true);
      });
      HILLS_OF_EVENDIM = prepare("hills_of_evendim", () -> {
         return new EriadorBiome.EvendimHills(true);
      });
      MIDDLE_EARTH_SURFACE = preRegSurfaceBuilder("middle_earth", new MiddleEarthSurfaceBuilder(MiddleEarthSurfaceConfig.CODEC));
   }
}
