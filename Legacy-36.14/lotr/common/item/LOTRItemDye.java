package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class LOTRItemDye extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] dyeIcons;
   private String[] dyeNames = new String[]{"elanor", "niphredil", "bluebell", "green", "charcoal", "brown"};

   public LOTRItemDye() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }

   public boolean func_111207_a(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entityliving) {
      if (entityliving instanceof EntitySheep) {
         EntitySheep sheep = (EntitySheep)entityliving;
         int dye = isItemDye(itemstack);
         if (dye == -1) {
            return false;
         } else {
            int blockDye = BlockColored.func_150031_c(dye);
            if (!sheep.func_70892_o() && sheep.func_70896_n() != blockDye) {
               sheep.func_70891_b(blockDye);
               --itemstack.field_77994_a;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.dyeIcons.length) {
         i = 0;
      }

      return this.dyeIcons[i];
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.dyeIcons = new IIcon[this.dyeNames.length];

      for(int i = 0; i < this.dyeNames.length; ++i) {
         this.dyeIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + this.dyeNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.dyeNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public static int isItemDye(ItemStack itemstack) {
      if (itemstack.func_77973_b() == Items.field_151100_aR) {
         return itemstack.func_77960_j();
      } else {
         int[] oreIDs = OreDictionary.getOreIDs(itemstack);
         int[] var2 = oreIDs;
         int var3 = oreIDs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int id = var2[var4];
            String oreName = OreDictionary.getOreName(id);

            for(int j = 0; j <= 15; ++j) {
               ItemStack dye = new ItemStack(Items.field_151100_aR, 1, j);
               if (LOTRMod.isOreNameEqual(dye, oreName)) {
                  return j;
               }
            }
         }

         return -1;
      }
   }
}
