package lotr.common.entity.npc.data;

import lotr.common.init.LOTRItems;
import lotr.common.item.VesselType;
import net.minecraft.item.Items;

public class NPCFoodPools {
   public static final NPCFoodPool HOBBIT;
   public static final NPCFoodPool HOBBIT_DRINK;
   public static final NPCFoodPool BREE;
   public static final NPCFoodPool BREE_DRINK;
   public static final NPCFoodPool ELF;
   public static final NPCFoodPool ELF_DRINK;
   public static final NPCFoodPool WOOD_ELF_DRINK;
   public static final NPCFoodPool DWARF;
   public static final NPCFoodPool BLUE_DWARF;
   public static final NPCFoodPool DWARF_DRINK;
   public static final NPCFoodPool DALE;
   public static final NPCFoodPool DALE_DRINK;
   public static final NPCFoodPool DUNLENDING;
   public static final NPCFoodPool DUNLENDING_DRINK;
   public static final NPCFoodPool ROHAN;
   public static final NPCFoodPool ROHAN_DRINK;
   public static final NPCFoodPool GONDOR;
   public static final NPCFoodPool GONDOR_DRINK;
   public static final NPCFoodPool ORC;
   public static final NPCFoodPool ORC_DRINK;
   public static final NPCFoodPool HARNEDHRIM;
   public static final NPCFoodPool HARNEDHRIM_DRINK;
   public static final NPCFoodPool COAST_SOUTHRON;
   public static final NPCFoodPool COAST_SOUTHRON_DRINK;

   static {
      HOBBIT = NPCFoodPool.of(Items.field_151157_am, Items.field_196104_bb, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, LOTRItems.GAMMON, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, Items.field_151025_P, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_151009_A, Items.field_179560_bq, Items.field_185164_cV, Items.field_185165_cW, Items.field_151158_bO, LOTRItems.PEAR, LOTRItems.CHERRY, Items.field_151106_aX);
      HOBBIT_DRINK = NPCFoodPool.of(LOTRItems.ALE, LOTRItems.ALE, LOTRItems.CIDER, LOTRItems.PERRY, LOTRItems.CHERRY_LIQUEUR, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.WOODEN_CUP);
      BREE = NPCFoodPool.of(Items.field_151157_am, Items.field_196104_bb, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, LOTRItems.GAMMON, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, Items.field_151025_P, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_151009_A, Items.field_179560_bq, Items.field_185164_cV, Items.field_185165_cW, Items.field_151158_bO, LOTRItems.PEAR);
      BREE_DRINK = NPCFoodPool.of(LOTRItems.ALE, LOTRItems.ALE, LOTRItems.ALE, LOTRItems.CIDER, LOTRItems.CIDER, LOTRItems.PERRY, LOTRItems.MEAD, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP);
      ELF = NPCFoodPool.of(Items.field_151025_P, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, LOTRItems.LETTUCE, Items.field_151172_bF, Items.field_185164_cV, Items.field_185165_cW, LOTRItems.LEMBAS, LOTRItems.LEMBAS, LOTRItems.LEMBAS, LOTRItems.LEMBAS, LOTRItems.LEMBAS, Items.field_179559_bp, Items.field_196104_bb, Items.field_151077_bg);
      ELF_DRINK = NPCFoodPool.of(LOTRItems.MIRUVOR, LOTRItems.MIRUVOR, LOTRItems.MIRUVOR, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.GOLDEN_GOBLET, VesselType.SILVER_GOBLET, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP);
      WOOD_ELF_DRINK = NPCFoodPool.of(LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.WOODEN_CUP);
      DWARF = NPCFoodPool.of(Items.field_151157_am, Items.field_196104_bb, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, LOTRItems.GAMMON, Items.field_151025_P, LOTRItems.CRAM, LOTRItems.CRAM, LOTRItems.CRAM, LOTRItems.CRAM, Items.field_151009_A);
      BLUE_DWARF = NPCFoodPool.of(Items.field_151157_am, Items.field_196104_bb, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, LOTRItems.GAMMON, Items.field_151025_P, Items.field_151009_A);
      DWARF_DRINK = NPCFoodPool.of(LOTRItems.DWARVEN_ALE, LOTRItems.DWARVEN_ALE, LOTRItems.DWARVEN_ALE, LOTRItems.DWARVEN_ALE, LOTRItems.DWARVEN_TONIC).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.GOLDEN_GOBLET, VesselType.SILVER_GOBLET, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP, VesselType.ALE_HORN);
      DALE = NPCFoodPool.of(Items.field_151157_am, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_185164_cV, Items.field_185165_cW, Items.field_151025_P, LOTRItems.CRAM, LOTRItems.CRAM, LOTRItems.CRAM, LOTRItems.CRAM);
      DALE_DRINK = NPCFoodPool.of(LOTRItems.MEAD, LOTRItems.ALE, LOTRItems.CIDER, LOTRItems.PERRY, LOTRItems.VODKA, LOTRItems.DWARVEN_ALE, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.GOLDEN_GOBLET, VesselType.SILVER_GOBLET, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP, VesselType.ALE_HORN);
      DUNLENDING = NPCFoodPool.of(Items.field_151157_am, Items.field_196104_bb, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, LOTRItems.GAMMON, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_185164_cV, Items.field_185165_cW, Items.field_151025_P);
      DUNLENDING_DRINK = NPCFoodPool.of(LOTRItems.ALE, LOTRItems.ALE, LOTRItems.MEAD, LOTRItems.CIDER, LOTRItems.RUM, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.WOODEN_CUP, VesselType.WATERSKIN, VesselType.ALE_HORN);
      ROHAN = NPCFoodPool.of(Items.field_151157_am, Items.field_151077_bg, Items.field_151083_be, Items.field_179557_bn, Items.field_179559_bp, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_185164_cV, Items.field_185165_cW, Items.field_151025_P);
      ROHAN_DRINK = NPCFoodPool.of(LOTRItems.MEAD, LOTRItems.MEAD, LOTRItems.MEAD, LOTRItems.ALE, LOTRItems.CIDER, LOTRItems.PERRY, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.GOLDEN_GOBLET, VesselType.SILVER_GOBLET, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP, VesselType.ALE_HORN, VesselType.GOLDEN_ALE_HORN);
      GONDOR = NPCFoodPool.of(Items.field_151157_am, Items.field_151077_bg, Items.field_151083_be, Items.field_196102_ba, Items.field_196104_bb, Items.field_179557_bn, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_185164_cV, Items.field_185165_cW, Items.field_151025_P);
      GONDOR_DRINK = NPCFoodPool.of(LOTRItems.ALE, LOTRItems.ALE, LOTRItems.ALE, LOTRItems.MEAD, LOTRItems.CIDER, LOTRItems.PERRY, LOTRItems.APPLE_JUICE).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.GOLDEN_GOBLET, VesselType.SILVER_GOBLET, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP, VesselType.ALE_HORN);
      ORC = NPCFoodPool.of(LOTRItems.MAGGOTY_BREAD, LOTRItems.MAGGOTY_BREAD, Items.field_151025_P, Items.field_179559_bp, Items.field_179557_bn, Items.field_151009_A);
      ORC_DRINK = NPCFoodPool.of(LOTRItems.ORC_DRAUGHT).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.WOODEN_CUP, VesselType.WATERSKIN);
      HARNEDHRIM = NPCFoodPool.of(Items.field_151157_am, Items.field_151077_bg, Items.field_151083_be, Items.field_196102_ba, Items.field_196104_bb, Items.field_179557_bn, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_151025_P);
      HARNEDHRIM_DRINK = NPCFoodPool.of(LOTRItems.WATER_DRINK, LOTRItems.ALE, LOTRItems.CIDER).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP, VesselType.WATERSKIN);
      COAST_SOUTHRON = NPCFoodPool.of(Items.field_151157_am, Items.field_151077_bg, Items.field_151083_be, Items.field_196102_ba, Items.field_196104_bb, Items.field_179557_bn, Items.field_151168_bH, Items.field_151034_e, LOTRItems.GREEN_APPLE, LOTRItems.PEAR, Items.field_151172_bF, LOTRItems.LETTUCE, Items.field_151025_P);
      COAST_SOUTHRON_DRINK = NPCFoodPool.of(LOTRItems.WATER_DRINK, LOTRItems.ALE, LOTRItems.CIDER).setDrinkVessels(VesselType.WOODEN_MUG, VesselType.CERAMIC_MUG, VesselType.GOLDEN_GOBLET, VesselType.COPPER_GOBLET, VesselType.WOODEN_CUP, VesselType.WATERSKIN);
   }
}
