package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockBirdCage extends LOTRBlockAnimalJar {
   @SideOnly(Side.CLIENT)
   private IIcon[] sideIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] topIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] baseIcons;
   private String[] cageTypes;

   public LOTRBlockBirdCage() {
      super(Material.field_151592_s);
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149777_j);
      this.setCageTypes("bronze", "iron", "silver", "gold");
   }

   protected void setCageTypes(String... s) {
      this.cageTypes = s;
   }

   public boolean canCapture(Entity entity) {
      return entity instanceof LOTREntityBird;
   }

   public float getJarEntityHeight() {
      return 0.5F;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return true;
   }

   public static boolean isSameBirdCage(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      Block block = world.func_147439_a(i, j, k);
      int meta = world.func_72805_g(i, j, k);
      Block block1 = world.func_147439_a(i1, j1, k1);
      int meta1 = world.func_72805_g(i1, j1, k1);
      return block instanceof LOTRBlockBirdCage && block == block1 && meta == meta1;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.cageTypes.length) {
         j = 0;
      }

      if (i != 0 && i != 1) {
         return i == -1 ? this.baseIcons[j] : this.sideIcons[j];
      } else {
         return this.topIcons[j];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.sideIcons = new IIcon[this.cageTypes.length];
      this.topIcons = new IIcon[this.cageTypes.length];
      this.baseIcons = new IIcon[this.cageTypes.length];

      for(int i = 0; i < this.cageTypes.length; ++i) {
         this.sideIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.cageTypes[i] + "_side");
         this.topIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.cageTypes[i] + "_top");
         this.baseIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.cageTypes[i] + "_base");
      }

   }

   public int func_149645_b() {
      return LOTRMod.proxy.getBirdCageRenderID();
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.cageTypes.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
