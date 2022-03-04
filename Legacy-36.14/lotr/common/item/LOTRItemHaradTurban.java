package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class LOTRItemHaradTurban extends LOTRItemHaradRobes {
   @SideOnly(Side.CLIENT)
   private IIcon ornamentIcon;

   public LOTRItemHaradTurban() {
      super(0);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      super.func_94581_a(iconregister);
      this.ornamentIcon = iconregister.func_94245_a(this.func_111208_A() + "_ornament");
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(ItemStack itemstack, int pass) {
      return pass == 1 && hasOrnament(itemstack) ? this.ornamentIcon : this.field_77791_bV;
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return pass == 1 && hasOrnament(itemstack) ? 16777215 : super.func_82790_a(itemstack, pass);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      super.func_77624_a(itemstack, entityplayer, list, flag);
      if (hasOrnament(itemstack)) {
         list.add(StatCollector.func_74838_a("item.lotr.haradRobes.ornament"));
      }

   }

   public static boolean hasOrnament(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("TurbanOrnament") ? itemstack.func_77978_p().func_74767_n("TurbanOrnament") : false;
   }

   public static void setHasOrnament(ItemStack itemstack, boolean flag) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74757_a("TurbanOrnament", flag);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
      return "lotr:armor/harad_turban.png";
   }
}
