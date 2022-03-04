package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRDate;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockLeavesBase extends BlockLeaves {
   public static List allLeafBlocks = new ArrayList();
   @SideOnly(Side.CLIENT)
   private IIcon[][] leafIcons;
   private String[] leafNames;
   private boolean[] seasonal;
   private String vanillaTextureName;

   public LOTRBlockLeavesBase() {
      this(false, (String)null);
   }

   public LOTRBlockLeavesBase(boolean vanilla, String vname) {
      if (vanilla) {
         this.func_149647_a(CreativeTabs.field_78031_c);
         this.vanillaTextureName = vname;
      } else {
         this.func_149647_a(LOTRCreativeTabs.tabDeco);
      }

      allLeafBlocks.add(this);
   }

   protected void setLeafNames(String... s) {
      this.leafNames = s;
      this.setSeasonal();
   }

   public String[] func_150125_e() {
      return this.leafNames;
   }

   public String[] getAllLeafNames() {
      return this.leafNames;
   }

   protected void setSeasonal(boolean... b) {
      if (b.length != this.leafNames.length) {
         throw new IllegalArgumentException("Leaf seasons length must match number of types");
      } else {
         this.seasonal = b;
      }
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return 16777215;
   }

   protected static int getBiomeLeafColor(IBlockAccess world, int i, int j, int k) {
      int totalR = 0;
      int totalG = 0;
      int totalB = 0;
      int count = 0;
      int range = 1;

      int i1;
      int k1;
      int biomeColor;
      for(i1 = -range; i1 <= range; ++i1) {
         for(k1 = -range; k1 <= range; ++k1) {
            biomeColor = world.func_72807_a(i + i1, k + k1).func_150571_c(i + i1, j, k + k1);
            totalR += (biomeColor & 16711680) >> 16;
            totalG += (biomeColor & '\uff00') >> 8;
            totalB += biomeColor & 255;
            ++count;
         }
      }

      i1 = totalR / count & 255;
      k1 = totalG / count & 255;
      biomeColor = totalB / count & 255;
      return i1 << 16 | k1 << 8 | biomeColor;
   }

   protected boolean shouldOakUseBiomeColor() {
      LOTRDate.Season season = LOTRDate.ShireReckoning.getSeason();
      return season == LOTRDate.Season.SPRING || season == LOTRDate.Season.SUMMER || !(LOTRMod.proxy.getClientWorld().field_73011_w instanceof LOTRWorldProvider);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      int saplingChanceBase = this.getSaplingChance(meta & 3);
      int saplingChance = this.calcFortuneModifiedDropChance(saplingChanceBase, fortune);
      if (world.field_73012_v.nextInt(saplingChance) == 0) {
         drops.add(new ItemStack(this.func_149650_a(meta, world.field_73012_v, fortune), 1, this.func_149692_a(meta)));
      }

      this.addSpecialLeafDrops(drops, world, i, j, k, meta, fortune);
      return drops;
   }

   protected int getSaplingChance(int meta) {
      return 20;
   }

   protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
   }

   protected int calcFortuneModifiedDropChance(int baseChance, int fortune) {
      int chance = baseChance;
      if (fortune > 0) {
         chance = baseChance - (2 << fortune);
         chance = Math.max(chance, baseChance / 2);
         chance = Math.max(chance, 1);
      }

      return chance;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      int meta = j & 3;
      if (meta >= this.leafNames.length) {
         meta = 0;
      }

      return this.leafIcons[meta][this.field_150121_P ? 0 : 1];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.leafIcons = new IIcon[this.leafNames.length][2];

      for(int i = 0; i < this.leafNames.length; ++i) {
         IIcon fancy = iconregister.func_94245_a(this.func_149641_N() + "_" + this.leafNames[i] + "_fancy");
         IIcon fast = iconregister.func_94245_a(this.func_149641_N() + "_" + this.leafNames[i] + "_fast");
         this.leafIcons[i][0] = fancy;
         this.leafIcons[i][1] = fast;
      }

   }

   public String func_149641_N() {
      return this.vanillaTextureName != null ? this.vanillaTextureName : super.func_149641_N();
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < this.leafNames.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }

   public static void setAllGraphicsLevels(boolean flag) {
      for(int i = 0; i < allLeafBlocks.size(); ++i) {
         ((LOTRBlockLeavesBase)allLeafBlocks.get(i)).func_150122_b(flag);
      }

   }
}
