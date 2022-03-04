package lotr.common.init;

import java.util.Comparator;
import java.util.function.Supplier;
import lotr.common.item.LOTRSpawnEggItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LOTRItemGroups {
   public static final ItemGroup BLOCKS = new LOTRItemGroups.LOTRItemGroup("blocks", () -> {
      return (Block)LOTRBlocks.GONDOR_BRICK.get();
   });
   public static final ItemGroup UTIL = new LOTRItemGroups.LOTRItemGroup("util", () -> {
      return (Block)LOTRBlocks.DWARVEN_CRAFTING_TABLE.get();
   });
   public static final ItemGroup DECO = new LOTRItemGroups.LOTRItemGroup("decorations", () -> {
      return (Block)LOTRBlocks.SIMBELMYNE.get();
   });
   public static final ItemGroup MATERIALS = new LOTRItemGroups.LOTRItemGroup("materials", () -> {
      return (Item)LOTRItems.MITHRIL_INGOT.get();
   });
   public static final ItemGroup MISC = new LOTRItemGroups.LOTRItemGroup("misc", () -> {
      return (Item)LOTRItems.GOLD_RING.get();
   });
   public static final ItemGroup FOOD = new LOTRItemGroups.LOTRItemGroup("food", () -> {
      return (Item)LOTRItems.LEMBAS.get();
   });
   public static final ItemGroup TOOLS = new LOTRItemGroups.LOTRItemGroup("tools", () -> {
      return (Item)LOTRItems.DWARVEN_PICKAXE.get();
   });
   public static final ItemGroup COMBAT = new LOTRItemGroups.LOTRItemGroup("combat", () -> {
      return (Item)LOTRItems.GONDOR_SWORD.get();
   });
   public static final ItemGroup STORY = new LOTRItemGroups.LOTRItemGroup("story", () -> {
      return (Item)LOTRItems.RED_BOOK.get();
   });
   public static final ItemGroup SPAWNERS = new LOTRItemGroups.LOTRItemGroup("spawners", () -> {
      return LOTRSpawnEggItem.getModSpawnEgg((EntityType)LOTREntities.HOBBIT.get());
   });

   public static class LOTRItemGroup extends ItemGroup {
      private final Supplier iconSup;

      public LOTRItemGroup(String s, Supplier itemSup) {
         super("lotr." + s);
         this.iconSup = itemSup;
      }

      @OnlyIn(Dist.CLIENT)
      public ItemStack func_78016_d() {
         return new ItemStack((IItemProvider)this.iconSup.get());
      }

      @OnlyIn(Dist.CLIENT)
      public void func_78018_a(NonNullList items) {
         super.func_78018_a(items);
         items.sort(Comparator.comparing(LOTRItems::getCreativeTabOrderForItem));
      }
   }
}
