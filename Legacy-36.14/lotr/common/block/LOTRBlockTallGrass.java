package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRDamage;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockTallGrass extends LOTRBlockGrass {
   @SideOnly(Side.CLIENT)
   private IIcon[] grassIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] overlayIcons;
   public static String[] grassNames = new String[]{"short", "flower", "wheat", "thistle", "nettle", "fernsprout"};
   public static boolean[] grassOverlay = new boolean[]{false, true, true, true, false, false};

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      int meta = world.func_72805_g(i, j, k);
      if (meta == 3 && entity.func_70051_ag() || meta == 4 && entity instanceof EntityPlayer) {
         boolean bootsLegs = false;
         if (entity instanceof EntityLivingBase) {
            EntityLivingBase living = (EntityLivingBase)entity;
            if (living.func_71124_b(1) != null && living.func_71124_b(2) != null) {
               bootsLegs = true;
            }
         }

         if (!bootsLegs) {
            entity.func_70097_a(LOTRDamage.plantHurt, 0.25F);
         }
      }

   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      if (meta == 3) {
         ArrayList thistles = new ArrayList();
         thistles.add(new ItemStack(this, 1, 3));
         return thistles;
      } else {
         return super.getDrops(world, i, j, k, meta, fortune);
      }
   }

   @SideOnly(Side.CLIENT)
   public int func_149635_D() {
      return Blocks.field_150329_H.func_149635_D();
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int meta) {
      return this.func_149635_D();
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return world.func_72807_a(i, k).func_150558_b(i, j, k);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= grassNames.length) {
         j = 0;
      }

      return i == -1 ? this.overlayIcons[j] : this.grassIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.grassIcons = new IIcon[grassNames.length];
      this.overlayIcons = new IIcon[grassNames.length];

      for(int i = 0; i < grassNames.length; ++i) {
         this.grassIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + grassNames[i]);
         if (grassOverlay[i]) {
            this.overlayIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + grassNames[i] + "_overlay");
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < grassNames.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
