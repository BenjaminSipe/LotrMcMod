package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockBed extends BlockBed {
   public Item bedItem;
   private Block bedBottomBlock;
   private int bedBottomMetadata;
   @SideOnly(Side.CLIENT)
   private IIcon[] bedIconsEnd;
   @SideOnly(Side.CLIENT)
   private IIcon[] bedIconsSide;
   @SideOnly(Side.CLIENT)
   private IIcon[] bedIconsTop;

   public LOTRBlockBed(Block block, int k) {
      this.bedBottomBlock = block;
      this.bedBottomMetadata = k;
      this.func_149711_c(0.2F);
      this.func_149672_a(Block.field_149766_f);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 0) {
         return this.bedBottomBlock.func_149691_a(0, this.bedBottomMetadata);
      } else {
         int k = func_149895_l(j);
         int l = Direction.field_71584_h[k][i];
         int i1 = func_149975_b(j) ? 1 : 0;
         return i1 == 1 && l == 2 || i1 == 0 && l == 3 ? this.bedIconsEnd[i1] : (l != 5 && l != 4 ? this.bedIconsTop[i1] : this.bedIconsSide[i1]);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.bedIconsTop = new IIcon[]{iconregister.func_94245_a(this.func_149641_N() + "_feet_top"), iconregister.func_94245_a(this.func_149641_N() + "_head_top")};
      this.bedIconsEnd = new IIcon[]{iconregister.func_94245_a(this.func_149641_N() + "_feet_end"), iconregister.func_94245_a(this.func_149641_N() + "_head_end")};
      this.bedIconsSide = new IIcon[]{iconregister.func_94245_a(this.func_149641_N() + "_feet_side"), iconregister.func_94245_a(this.func_149641_N() + "_head_side")};
   }

   public Item func_149650_a(int i, Random random, int j) {
      return func_149975_b(i) ? null : this.bedItem;
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return this.bedItem;
   }

   public boolean isBed(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
      return true;
   }
}
