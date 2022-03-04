package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRItemKebabStand extends ItemBlock {
   public LOTRItemKebabStand(Block block) {
      super(block);
   }

   public static void setKebabData(ItemStack itemstack, NBTTagCompound kebabData) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74782_a("LOTRKebabData", kebabData);
   }

   public static void setKebabData(ItemStack itemstack, LOTRTileEntityKebabStand kebabStand) {
      if (kebabStand.shouldSaveBlockData()) {
         NBTTagCompound kebabData = new NBTTagCompound();
         kebabStand.writeKebabStandToNBT(kebabData);
         setKebabData(itemstack, kebabData);
      }

   }

   public static NBTTagCompound getKebabData(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("LOTRKebabData")) {
         NBTTagCompound kebabData = itemstack.func_77978_p().func_74775_l("LOTRKebabData");
         return kebabData;
      } else {
         return null;
      }
   }

   public static void loadKebabData(ItemStack itemstack, LOTRTileEntityKebabStand kebabStand) {
      NBTTagCompound kebabData = getKebabData(itemstack);
      if (kebabData != null) {
         kebabStand.readKebabStandFromNBT(kebabData);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      NBTTagCompound kebabData = getKebabData(itemstack);
      if (kebabData != null) {
         LOTRTileEntityKebabStand kebabStand = new LOTRTileEntityKebabStand();
         kebabStand.readKebabStandFromNBT(kebabData);
         int meats = kebabStand.getMeatAmount();
         list.add(StatCollector.func_74837_a("tile.lotr.kebabStand.meats", new Object[]{meats}));
         if (kebabStand.isCooked()) {
            list.add(StatCollector.func_74838_a("tile.lotr.kebabStand.cooked"));
         }
      }

   }
}
