package lotr.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.util.LOTRUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRConfig {
   public static final LOTRConfig.ClientConfig CLIENT;
   public static final LOTRConfig.CommonConfig COMMON;
   public static final LOTRConfig.ServerConfig SERVER;
   public static final ForgeConfigSpec CLIENT_SPEC;
   public static final ForgeConfigSpec COMMON_SPEC;
   public static final ForgeConfigSpec SERVER_SPEC;

   public static void register(IEventBus fmlBus) {
      fmlBus.register(LOTRConfig.class);
      ModLoadingContext.get().registerConfig(Type.CLIENT, CLIENT_SPEC);
      ModLoadingContext.get().registerConfig(Type.COMMON, COMMON_SPEC);
      ModLoadingContext.get().registerConfig(Type.SERVER, SERVER_SPEC);
   }

   @SubscribeEvent
   public static void onModConfig(ModConfigEvent event) {
      ForgeConfigSpec spec = event.getConfig().getSpec();
      if (spec == CLIENT_SPEC) {
         CLIENT.bakeFields();
      } else if (spec == COMMON_SPEC) {
         COMMON.bakeFields();
      } else if (spec == SERVER_SPEC) {
         SERVER.bakeFields();
      }

   }

   private static final LOTRConfig.IntHolder makeInt(List collection, Builder builder, String key, int def, String comment) {
      return makeIntBounded(collection, builder, key, def, Integer.MIN_VALUE, Integer.MAX_VALUE, comment);
   }

   private static final LOTRConfig.IntHolder makeIntBounded(List collection, Builder builder, String key, int def, int min, int max, String comment) {
      return makeInt(collection, builder, key, def, min, max, false, comment);
   }

   private static final LOTRConfig.IntHolder makeIntBoundedRestart(List collection, Builder builder, String key, int def, int min, int max, String comment) {
      return makeInt(collection, builder, key, def, min, max, true, comment);
   }

   private static final LOTRConfig.IntHolder makeInt(List collection, Builder builder, String key, int def, int min, int max, boolean worldRestart, String comment) {
      builder.comment(comment).translation(String.format("config.%s.%s", "lotr", key));
      if (worldRestart) {
         builder.worldRestart();
      }

      IntValue intVal = builder.defineInRange(key, def, min, max);
      LOTRConfig.IntHolder holder = new LOTRConfig.IntHolder(intVal);
      collection.add(holder);
      return holder;
   }

   private static final LOTRConfig.BoolHolder makeBool(List collection, Builder builder, String key, boolean def, String comment) {
      return makeBool(collection, builder, key, def, false, comment);
   }

   private static final LOTRConfig.BoolHolder makeBoolRestart(List collection, Builder builder, String key, boolean def, String comment) {
      return makeBool(collection, builder, key, def, true, comment);
   }

   private static final LOTRConfig.BoolHolder makeBool(List collection, Builder builder, String key, boolean def, boolean worldRestart, String comment) {
      builder.comment(comment).translation(String.format("config.%s.%s", "lotr", key));
      if (worldRestart) {
         builder.worldRestart();
      }

      BooleanValue boolVal = builder.define(key, def);
      LOTRConfig.BoolHolder holder = new LOTRConfig.BoolHolder(boolVal);
      collection.add(holder);
      return holder;
   }

   static {
      Pair clientPair = (new Builder()).configure(LOTRConfig.ClientConfig::new);
      Pair commonPair = (new Builder()).configure(LOTRConfig.CommonConfig::new);
      Pair serverPair = (new Builder()).configure(LOTRConfig.ServerConfig::new);
      CLIENT = (LOTRConfig.ClientConfig)clientPair.getLeft();
      CLIENT_SPEC = (ForgeConfigSpec)clientPair.getRight();
      COMMON = (LOTRConfig.CommonConfig)commonPair.getLeft();
      COMMON_SPEC = (ForgeConfigSpec)commonPair.getRight();
      SERVER = (LOTRConfig.ServerConfig)serverPair.getLeft();
      SERVER_SPEC = (ForgeConfigSpec)serverPair.getRight();
   }

   public static class BoolHolder extends LOTRConfig.ConfigValueHolder {
      public BoolHolder(ConfigValue cfgVal) {
         super(cfgVal);
      }

      public void toggleAndSave() {
         this.setAndSave(!(Boolean)this.get());
      }
   }

   public static class IntHolder extends LOTRConfig.ConfigValueHolder {
      public IntHolder(ConfigValue cfgVal) {
         super(cfgVal);
      }
   }

   public abstract static class ConfigValueHolder {
      public final ConfigValue configValue;
      private Object value;

      public ConfigValueHolder(ConfigValue cfgVal) {
         this.configValue = cfgVal;
      }

      public void bake() {
         this.value = this.configValue.get();
      }

      public Object get() {
         return this.value;
      }

      public void setAndSave(Object newValue) {
         this.configValue.set(this.value = newValue);
         this.configValue.save();
      }
   }

   public static class ServerConfig {
      private final List serverFields = new ArrayList();
      public final LOTRConfig.IntHolder forceMapLocations;
      public final LOTRConfig.IntHolder forceFogOfWar;
      public final LOTRConfig.IntHolder playerDataClearingInterval;

      public ServerConfig(Builder builder) {
         builder.push("admin");
         this.forceMapLocations = LOTRConfig.makeIntBounded(this.serverFields, builder, "forceMapLocations", 0, 0, 2, "Force hide or show all players' map locations. 0 = per-player (default), 1 = force hide, 2 = force show");
         this.forceFogOfWar = LOTRConfig.makeIntBounded(this.serverFields, builder, "forceFogOfWar", 0, 0, 2, "Force enable or disable the map exploration fog for all players. 0 = set by players in their own configs (default), 1 = force fog, 2 = force no fog");
         this.playerDataClearingInterval = LOTRConfig.makeIntBounded(this.serverFields, builder, "playerDataClearingInterval", LOTRUtil.minutesToTicks(1), LOTRUtil.secondsToTicks(30), LOTRUtil.minutesToTicks(60), "Tick interval between clearing offline LOTR-playerdata from the cache. Offline players' data is typically loaded to serve features like fellowships and their shared custom waypoints. Higher values may reduce server lag, as data will have to be reloaded from disk less often, but will result in higher RAM usage to some extent");
         builder.pop();
      }

      public void bakeFields() {
         this.serverFields.forEach(LOTRConfig.ConfigValueHolder::bake);
      }
   }

   public static class CommonConfig {
      private final List commonFields = new ArrayList();
      public final LOTRConfig.BoolHolder drunkSpeech;
      public final LOTRConfig.IntHolder npcTalkToPlayerMinDuration;
      public final LOTRConfig.IntHolder npcTalkToPlayerMaxDuration;
      public final LOTRConfig.BoolHolder areasOfInfluence;
      public final LOTRConfig.BoolHolder alignmentDraining;
      public final LOTRConfig.BoolHolder smallerBees;
      public final LOTRConfig.BoolHolder generateBiomeJsons;

      public CommonConfig(Builder builder) {
         builder.push("gameplay");
         this.drunkSpeech = LOTRConfig.makeBool(this.commonFields, builder, "drunkSpeech", true, "");
         this.npcTalkToPlayerMinDuration = LOTRConfig.makeIntBounded(this.commonFields, builder, "npcTalkToPlayerMinDuration", 5, 3, 60, "The minimum possible time (in seconds) for which an NPC will stand still and display talking animations when spoken to by a player");
         this.npcTalkToPlayerMaxDuration = LOTRConfig.makeIntBounded(this.commonFields, builder, "npcTalkToPlayerMaxDuration", 10, 3, 60, "The maximum possible time (in seconds) for which an NPC will stand still and display talking animations when spoken to by a player");
         this.areasOfInfluence = LOTRConfig.makeBool(this.commonFields, builder, "areasOfInfluence", true, "Alignment gains depend on factions' areas of influence");
         this.alignmentDraining = LOTRConfig.makeBool(this.commonFields, builder, "alignmentDraining", true, "Factions dislike if a player has + alignment with enemy factions");
         builder.pop();
         builder.push("mobs");
         this.smallerBees = LOTRConfig.makeBool(this.commonFields, builder, "smallerBees", true, "They're simply too big!");
         builder.pop();
         builder.push("datapack-utility");
         this.generateBiomeJsons = LOTRConfig.makeBool(this.commonFields, builder, "generateBiomeJsons", false, "If enabled, the mod will generate up-to-date biome JSON files for all its biomes and output them in the game directory during loading. These biome templates are provided to help datapack creators.");
         builder.pop();
      }

      public void bakeFields() {
         this.commonFields.forEach(LOTRConfig.ConfigValueHolder::bake);
      }

      public int getRandomNPCTalkToPlayerDuration(Random rand) {
         int min = (Integer)this.npcTalkToPlayerMinDuration.get();
         int max = (Integer)this.npcTalkToPlayerMaxDuration.get();
         if (min > max) {
            min = (Integer)this.npcTalkToPlayerMaxDuration.get();
            max = (Integer)this.npcTalkToPlayerMinDuration.get();
         }

         return MathHelper.func_76136_a(rand, min, max);
      }
   }

   public static class ClientConfig {
      private final List clientFields = new ArrayList();
      public final LOTRConfig.BoolHolder modSky;
      public final LOTRConfig.BoolHolder modClouds;
      public final LOTRConfig.IntHolder cloudRange;
      public final LOTRConfig.BoolHolder northernLights;
      public final LOTRConfig.BoolHolder sunGlare;
      public final LOTRConfig.BoolHolder rainMist;
      public final LOTRConfig.BoolHolder mistyMountainsMist;
      public final LOTRConfig.BoolHolder newWeatherRendering;
      public final LOTRConfig.BoolHolder newRainGroundParticles;
      public final LOTRConfig.BoolHolder modMainMenu;
      public final LOTRConfig.BoolHolder sepiaMap;
      public final LOTRConfig.BoolHolder mapLabels;
      public final LOTRConfig.BoolHolder showWorldTypeHelp;
      public final LOTRConfig.BoolHolder fogOfWar;
      public final LOTRConfig.BoolHolder imperialWaypointDistances;
      public final LOTRConfig.BoolHolder compass;
      public final LOTRConfig.BoolHolder compassInfo;
      public final LOTRConfig.BoolHolder showAlignmentEverywhere;
      public final LOTRConfig.IntHolder alignmentXOffset;
      public final LOTRConfig.IntHolder alignmentYOffset;
      public final LOTRConfig.BoolHolder immersiveSpeech;
      public final LOTRConfig.BoolHolder immersiveSpeechChatLog;
      public final LOTRConfig.IntHolder immersiveSpeechDuration;
      public final LOTRConfig.BoolHolder displayAlignmentAboveHead;
      public final LOTRConfig.IntHolder dolAmrothChestplateWings;
      public final LOTRConfig.BoolHolder mannishWomenUseAlexModelStyle;
      public final LOTRConfig.BoolHolder elfWomenUseAlexModelStyle;
      public final LOTRConfig.BoolHolder dwarfWomenUseAlexModelStyle;
      public final LOTRConfig.BoolHolder hobbitWomenUseAlexModelStyle;
      public final LOTRConfig.BoolHolder orcWomenUseAlexModelStyle;
      public final LOTRConfig.BoolHolder windAmbience;
      public final LOTRConfig.BoolHolder newRainSounds;
      public final LOTRConfig.BoolHolder newThunderSounds;

      public ClientConfig(Builder builder) {
         builder.push("environment");
         this.modSky = LOTRConfig.makeBoolRestart(this.clientFields, builder, "modSky", true, "Toggle the Middle-earth sky renderer");
         this.modClouds = LOTRConfig.makeBoolRestart(this.clientFields, builder, "modClouds", true, "Toggle the Middle-earth cloud renderer");
         this.cloudRange = LOTRConfig.makeIntBounded(this.clientFields, builder, "cloudRange", 1024, 0, Integer.MAX_VALUE, "");
         this.northernLights = LOTRConfig.makeBool(this.clientFields, builder, "northernLights", true, "The Aurora, or Northern Lights! May be a slightly performance-intensive feature for some users");
         this.sunGlare = LOTRConfig.makeBool(this.clientFields, builder, "sunGlare", true, "");
         this.rainMist = LOTRConfig.makeBool(this.clientFields, builder, "rainMist", true, "");
         this.mistyMountainsMist = LOTRConfig.makeBool(this.clientFields, builder, "mistyMountainsMist", true, "");
         this.newWeatherRendering = LOTRConfig.makeBoolRestart(this.clientFields, builder, "newWeatherRendering", true, "New rain and snow textures (in Middle-earth), ash, and sandstorms");
         this.newRainGroundParticles = LOTRConfig.makeBoolRestart(this.clientFields, builder, "newRainGroundParticles", true, "Replace rain ground particles in Middle-earth, to match the new rain textures");
         builder.pop();
         builder.push("gui");
         this.modMainMenu = LOTRConfig.makeBool(this.clientFields, builder, "modMainMenu", true, "Display the mod's custom main menu screen");
         this.sepiaMap = LOTRConfig.makeBool(this.clientFields, builder, "sepiaMap", false, "");
         this.mapLabels = LOTRConfig.makeBool(this.clientFields, builder, "mapLabels", true, "");
         this.showWorldTypeHelp = LOTRConfig.makeBool(this.clientFields, builder, "showWorldTypeHelp", true, "Will be automatically set to false after the world type help display has been shown once");
         this.fogOfWar = LOTRConfig.makeBool(this.clientFields, builder, "fogOfWar", true, "Toggle the map exploration fog. If a server has set forceFogOfWar to either force enable or disable the fog, that server setting will override this one.");
         this.imperialWaypointDistances = LOTRConfig.makeBool(this.clientFields, builder, "imperialWaypointDistances", false, "Display waypoint distances in leagues, miles, and yards instead of metric (blocks)");
         builder.pop();
         builder.push("hud");
         this.compass = LOTRConfig.makeBool(this.clientFields, builder, "compass", true, "Middle-earth on-screen compass");
         this.compassInfo = LOTRConfig.makeBool(this.clientFields, builder, "compassInfo", true, "On-screen compass: coordinates and biome name");
         this.showAlignmentEverywhere = LOTRConfig.makeBool(this.clientFields, builder, "showAlignmentEverywhere", false, "Display the alignment meter even in non-mod dimensions");
         this.alignmentXOffset = LOTRConfig.makeInt(this.clientFields, builder, "alignmentXOffset", 0, "Configure the x-position of the alignment meter on-screen. Negative values move it left, positive values right");
         this.alignmentYOffset = LOTRConfig.makeInt(this.clientFields, builder, "alignmentYOffset", 0, "Configure the y-position of the alignment meter on-screen. Negative values move it up, positive values down");
         this.immersiveSpeech = LOTRConfig.makeBool(this.clientFields, builder, "immersiveSpeech", true, "If set to true, NPC speech will appear on-screen with the NPC. If set to false, it will be sent to the chat box");
         this.immersiveSpeechChatLog = LOTRConfig.makeBool(this.clientFields, builder, "immersiveSpeechChatLog", false, "Toggle whether speech still shows in the chat box when Immersive Speech is enabled");
         this.immersiveSpeechDuration = LOTRConfig.makeIntBounded(this.clientFields, builder, "immersiveSpeechDuration", 10, 0, 60, "The duration (in seconds) of Immersive Speech displays");
         this.displayAlignmentAboveHead = LOTRConfig.makeBool(this.clientFields, builder, "displayAlignmentAboveHead", true, "Render other players' alignment values above their heads");
         builder.pop();
         builder.push("model");
         this.dolAmrothChestplateWings = LOTRConfig.makeIntBounded(this.clientFields, builder, "dolAmrothChestplateWings", 12, 0, 24, "The number of wings on the swan knights' chestplates");
         this.mannishWomenUseAlexModelStyle = LOTRConfig.makeBool(this.clientFields, builder, "mannishWomenUseAlexModelStyle", true, "If true, Mannish women NPCs will use the 'Alex' model style, with slimmer arms");
         this.elfWomenUseAlexModelStyle = LOTRConfig.makeBool(this.clientFields, builder, "elfWomenUseAlexModelStyle", true, "If true, Elf-women NPCs will use the 'Alex' model style, with slimmer arms");
         this.dwarfWomenUseAlexModelStyle = LOTRConfig.makeBool(this.clientFields, builder, "dwarfWomenUseAlexModelStyle", false, "If true, Dwarf-women NPCs will use the 'Alex' model style, with slimmer arms");
         this.hobbitWomenUseAlexModelStyle = LOTRConfig.makeBool(this.clientFields, builder, "hobbitWomenUseAlexModelStyle", false, "If true, Hobbit-women NPCs will use the 'Alex' model style, with slimmer arms");
         this.orcWomenUseAlexModelStyle = LOTRConfig.makeBool(this.clientFields, builder, "orcWomenUseAlexModelStyle", false, "If true, Orc-women NPCs will use the 'Alex' model style, with slimmer arms");
         builder.pop();
         builder.push("sound");
         this.windAmbience = LOTRConfig.makeBool(this.clientFields, builder, "windAmbience", true, "");
         this.newRainSounds = LOTRConfig.makeBool(this.clientFields, builder, "newRainSounds", true, "");
         this.newThunderSounds = LOTRConfig.makeBool(this.clientFields, builder, "newThunderSounds", true, "");
         builder.pop();
      }

      public void bakeFields() {
         this.clientFields.forEach(LOTRConfig.ConfigValueHolder::bake);
      }

      public void toggleSepia() {
         this.sepiaMap.toggleAndSave();
      }

      public void toggleMapLabels() {
         this.mapLabels.toggleAndSave();
      }
   }
}
