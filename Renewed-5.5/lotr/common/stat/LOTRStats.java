package lotr.common.stat;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LOTRStats {
   public static ResourceLocation INTERACT_FACTION_CRAFTING_TABLE;
   public static ResourceLocation RING_INTO_FIRE;
   public static ResourceLocation INTERACT_ALLOY_FORGE;
   public static ResourceLocation INTERACT_DWARVEN_FORGE;
   public static ResourceLocation INTERACT_ELVEN_FORGE;
   public static ResourceLocation INTERACT_ORC_FORGE;
   public static ResourceLocation INTERACT_HOBBIT_OVEN;
   public static ResourceLocation INTERACT_KEG;
   public static ResourceLocation FAST_TRAVEL;
   public static ResourceLocation FAST_TRAVEL_ONE_M;
   public static ResourceLocation CREATE_CUSTOM_WAYPOINT;
   public static ResourceLocation ADOPT_CUSTOM_WAYPOINT;
   public static ResourceLocation LIGHT_GONDOR_BEACON;
   public static ResourceLocation EXTINGUISH_GONDOR_BEACON;
   public static ResourceLocation TALK_TO_NPC;
   public static ResourceLocation OPEN_POUCH;
   public static ResourceLocation CLEAN_POUCH;
   public static ResourceLocation CLEAN_SMOKING_PIPE;
   public static final IStatFormatter DISTANCE_IN_M = (value) -> {
      return IStatFormatter.field_223220_d_.format(value * 100);
   };

   public static void setup() {
      INTERACT_FACTION_CRAFTING_TABLE = registerCustom("interact_faction_crafting_table", IStatFormatter.field_223218_b_);
      RING_INTO_FIRE = registerCustom("throw_ring_in_fire", IStatFormatter.field_223218_b_);
      INTERACT_ALLOY_FORGE = registerCustom("interact_alloy_forge", IStatFormatter.field_223218_b_);
      INTERACT_DWARVEN_FORGE = registerCustom("interact_dwarven_forge", IStatFormatter.field_223218_b_);
      INTERACT_ELVEN_FORGE = registerCustom("interact_elven_forge", IStatFormatter.field_223218_b_);
      INTERACT_ORC_FORGE = registerCustom("interact_orc_forge", IStatFormatter.field_223218_b_);
      INTERACT_HOBBIT_OVEN = registerCustom("interact_hobbit_oven", IStatFormatter.field_223218_b_);
      INTERACT_KEG = registerCustom("interact_keg", IStatFormatter.field_223218_b_);
      FAST_TRAVEL = registerCustom("fast_travel", IStatFormatter.field_223218_b_);
      FAST_TRAVEL_ONE_M = registerCustom("fast_travel_one_m", DISTANCE_IN_M);
      CREATE_CUSTOM_WAYPOINT = registerCustom("create_custom_waypoint", IStatFormatter.field_223218_b_);
      ADOPT_CUSTOM_WAYPOINT = registerCustom("adopt_custom_waypoint", IStatFormatter.field_223218_b_);
      LIGHT_GONDOR_BEACON = registerCustom("light_gondor_beacon", IStatFormatter.field_223218_b_);
      EXTINGUISH_GONDOR_BEACON = registerCustom("extinguish_gondor_beacon", IStatFormatter.field_223218_b_);
      TALK_TO_NPC = registerCustom("talk_to_npc", IStatFormatter.field_223218_b_);
      OPEN_POUCH = registerCustom("open_pouch", IStatFormatter.field_223218_b_);
      CLEAN_POUCH = registerCustom("clean_pouch", IStatFormatter.field_223218_b_);
      CLEAN_SMOKING_PIPE = registerCustom("clean_smoking_pipe", IStatFormatter.field_223218_b_);
   }

   private static ResourceLocation registerCustom(String key, IStatFormatter formatter) {
      ResourceLocation res = new ResourceLocation("lotr", key);
      Registry.func_218325_a(Registry.field_212623_l, res.toString(), res);
      Stats.field_199092_j.func_199077_a(res, formatter);
      return res;
   }
}
