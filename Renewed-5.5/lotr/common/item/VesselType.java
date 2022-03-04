package lotr.common.item;

import java.util.Map;
import java.util.function.Supplier;
import lotr.common.init.LOTRItems;
import lotr.common.util.LOTRUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public enum VesselType {
   WOODEN_MUG(() -> {
      return (Item)LOTRItems.WOODEN_MUG.get();
   }, "wooden_mug", "wooden_mug_drink", true),
   CERAMIC_MUG(() -> {
      return (Item)LOTRItems.CERAMIC_MUG.get();
   }, "ceramic_mug", "ceramic_mug_drink", true),
   GOLDEN_GOBLET(() -> {
      return (Item)LOTRItems.GOLDEN_GOBLET.get();
   }, "golden_goblet", "golden_goblet_drink", true),
   SILVER_GOBLET(() -> {
      return (Item)LOTRItems.SILVER_GOBLET.get();
   }, "silver_goblet", "silver_goblet_drink", true),
   COPPER_GOBLET(() -> {
      return (Item)LOTRItems.COPPER_GOBLET.get();
   }, "copper_goblet", "copper_goblet_drink", true),
   WOODEN_CUP(() -> {
      return (Item)LOTRItems.WOODEN_CUP.get();
   }, "wooden_cup", "wooden_cup_drink", true),
   WATERSKIN(() -> {
      return (Item)LOTRItems.WATERSKIN.get();
   }, "waterskin", "waterskin_drink", false),
   ALE_HORN(() -> {
      return (Item)LOTRItems.ALE_HORN.get();
   }, "ale_horn", "ale_horn_drink", true),
   GOLDEN_ALE_HORN(() -> {
      return (Item)LOTRItems.GOLDEN_ALE_HORN.get();
   }, "golden_ale_horn", "golden_ale_horn_drink", true);

   private static final Map NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), VesselType::getCodeName);
   private final Supplier itemSup;
   private final String vesselName;
   private final String emptyIconName;
   private final boolean isPlaceable;

   private VesselType(Supplier item, String name, String iconName, boolean place) {
      this.itemSup = item;
      this.vesselName = name;
      this.emptyIconName = iconName;
      this.isPlaceable = place;
   }

   public ItemStack createEmpty() {
      return new ItemStack((IItemProvider)this.itemSup.get());
   }

   public String getCodeName() {
      return this.vesselName;
   }

   public static VesselType forName(String name) {
      return (VesselType)NAME_LOOKUP.getOrDefault(name, WOODEN_MUG);
   }

   public String getEmptyIconName() {
      return this.emptyIconName;
   }

   public ResourceLocation getEmptySpritePath() {
      return new ResourceLocation("lotr", "item/" + this.emptyIconName);
   }

   public boolean isPlaceable() {
      return this.isPlaceable;
   }
}
