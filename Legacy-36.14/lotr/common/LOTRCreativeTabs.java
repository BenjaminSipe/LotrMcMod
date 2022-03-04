package lotr.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRCreativeTabs extends CreativeTabs {
   public static LOTRCreativeTabs tabBlock = new LOTRCreativeTabs("blocks");
   public static LOTRCreativeTabs tabUtil = new LOTRCreativeTabs("util");
   public static LOTRCreativeTabs tabDeco = new LOTRCreativeTabs("decorations");
   public static LOTRCreativeTabs tabFood = new LOTRCreativeTabs("food");
   public static LOTRCreativeTabs tabMaterials = new LOTRCreativeTabs("materials");
   public static LOTRCreativeTabs tabMisc = new LOTRCreativeTabs("misc");
   public static LOTRCreativeTabs tabTools = new LOTRCreativeTabs("tools");
   public static LOTRCreativeTabs tabCombat = new LOTRCreativeTabs("combat");
   public static LOTRCreativeTabs tabStory = new LOTRCreativeTabs("story");
   public static LOTRCreativeTabs tabSpawn = new LOTRCreativeTabs("spawning");
   public ItemStack theIcon;

   public LOTRCreativeTabs(String label) {
      super(label);
   }

   public static void setupIcons() {
      tabBlock.theIcon = new ItemStack(LOTRMod.brick, 1, 11);
      tabUtil.theIcon = new ItemStack(LOTRMod.dwarvenForge);
      tabDeco.theIcon = new ItemStack(LOTRMod.simbelmyne);
      tabFood.theIcon = new ItemStack(LOTRMod.lembas);
      tabMaterials.theIcon = new ItemStack(LOTRMod.mithril);
      tabMisc.theIcon = new ItemStack(LOTRMod.hobbitPipe);
      tabTools.theIcon = new ItemStack(LOTRMod.pickaxeOrc);
      tabCombat.theIcon = new ItemStack(LOTRMod.helmetGondor);
      tabStory.theIcon = new ItemStack(LOTRMod.anduril);
      tabSpawn.theIcon = new ItemStack(LOTRMod.spawnEgg, 1, 55);
   }

   @SideOnly(Side.CLIENT)
   public String func_78024_c() {
      return StatCollector.func_74838_a("lotr.creativetab." + this.func_78013_b());
   }

   @SideOnly(Side.CLIENT)
   public Item func_78016_d() {
      return this.theIcon.func_77973_b();
   }

   public ItemStack func_151244_d() {
      return this.theIcon;
   }
}
