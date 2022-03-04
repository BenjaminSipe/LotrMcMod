package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockOreStorage extends LOTRBlockOreStorageBase implements LOTRConnectedBlock {
   @SideOnly(Side.CLIENT)
   private IIcon orcSteelSideIcon;
   @SideOnly(Side.CLIENT)
   private IIcon urukSteelSideIcon;
   @SideOnly(Side.CLIENT)
   private IIcon morgulSteelSideIcon;

   public LOTRBlockOreStorage() {
      this.setOreStorageNames(new String[]{"copper", "tin", "bronze", "silver", "mithril", "orcSteel", "quendite", "dwarfSteel", "galvorn", "urukSteel", "naurite", "gulduril", "morgulSteel", "sulfur", "saltpeter", "blueDwarfSteel"});
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.oreStorageIcons = new IIcon[this.oreStorageNames.length];

      for(int i = 0; i < this.oreStorageNames.length; ++i) {
         if (i == 4) {
            LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
         } else {
            this.oreStorageIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.oreStorageNames[i]);
         }
      }

      this.orcSteelSideIcon = iconregister.func_94245_a(this.func_149641_N() + "_orcSteel_side");
      this.urukSteelSideIcon = iconregister.func_94245_a(this.func_149641_N() + "_urukSteel_side");
      this.morgulSteelSideIcon = iconregister.func_94245_a(this.func_149641_N() + "_morgulSteel_side");
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      int meta = world.func_72805_g(i, j, k);
      return meta == 4 ? LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false) : super.func_149673_e(world, i, j, k, side);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int side, int meta) {
      if (meta == 4) {
         return LOTRConnectedTextures.getConnectedIconItem(this, meta);
      } else if (meta == 5 && side > 1) {
         return this.orcSteelSideIcon;
      } else if (meta == 9 && side > 1) {
         return this.urukSteelSideIcon;
      } else {
         return meta == 12 && side > 1 ? this.morgulSteelSideIcon : super.func_149691_a(side, meta);
      }
   }

   public String getConnectedName(int meta) {
      return this.field_149768_d + "_" + this.oreStorageNames[meta];
   }

   public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
   }

   public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
      return world.func_72805_g(i, j, k) == 13 && side == ForgeDirection.UP;
   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      if (world.func_72805_g(i, j, k) == 6) {
         return LOTRMod.oreQuendite.func_149750_m();
      } else if (world.func_72805_g(i, j, k) == 10) {
         return LOTRMod.oreNaurite.func_149750_m();
      } else {
         return world.func_72805_g(i, j, k) == 11 ? LOTRMod.oreGulduril.func_149750_m() : 0;
      }
   }

   public boolean hasTileEntity(int metadata) {
      return metadata == 11;
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return this.hasTileEntity(metadata) ? new LOTRTileEntityGulduril() : null;
   }
}
