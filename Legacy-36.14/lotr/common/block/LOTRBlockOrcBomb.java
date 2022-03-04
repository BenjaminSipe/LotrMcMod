package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockOrcBomb extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] orcBombIcons;

   public LOTRBlockOrcBomb() {
      super(Material.field_151573_f);
      this.func_149647_a(LOTRCreativeTabs.tabCombat);
      this.func_149711_c(3.0F);
      this.func_149752_b(0.0F);
      this.func_149672_a(Block.field_149777_j);
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      int strength = getBombStrengthLevel(i);
      if (strength == 1) {
         return 11974326;
      } else {
         return strength == 2 ? 7829367 : 16777215;
      }
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      int strength = getBombStrengthLevel(meta);
      if (strength == 1) {
         return 11974326;
      } else {
         return strength == 2 ? 7829367 : 16777215;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      boolean isFire = isFireBomb(j);
      if (i == -1) {
         return this.orcBombIcons[2];
      } else if (i == 1) {
         return isFire ? this.orcBombIcons[4] : this.orcBombIcons[1];
      } else {
         return isFire ? this.orcBombIcons[3] : this.orcBombIcons[0];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.orcBombIcons = new IIcon[5];
      this.orcBombIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.orcBombIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.orcBombIcons[2] = iconregister.func_94245_a(this.func_149641_N() + "_handle");
      this.orcBombIcons[3] = iconregister.func_94245_a(this.func_149641_N() + "_fire_side");
      this.orcBombIcons[4] = iconregister.func_94245_a(this.func_149641_N() + "_fire_top");
   }

   public static int getBombStrengthLevel(int meta) {
      return meta & 7;
   }

   public static boolean isFireBomb(int meta) {
      return (meta & 8) != 0;
   }

   public void func_149726_b(World world, int i, int j, int k) {
      super.func_149726_b(world, i, j, k);
      if (world.func_72864_z(i, j, k)) {
         this.func_149664_b(world, i, j, k, -1);
         world.func_147468_f(i, j, k);
      }

   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (block.func_149688_o() != Material.field_151579_a && block.func_149744_f() && world.func_72864_z(i, j, k)) {
         this.func_149664_b(world, i, j, k, -1);
         world.func_147468_f(i, j, k);
      }

   }

   public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
      if (!world.field_72995_K) {
         int meta = world.func_72805_g(i, j, k);
         LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), explosion.func_94613_c());
         bomb.setBombStrengthLevel(meta);
         bomb.setFuseFromExplosion();
         bomb.droppedByPlayer = true;
         world.func_72838_d(bomb);
      }

      super.onBlockExploded(world, i, j, k, explosion);
   }

   public void func_149664_b(World world, int i, int j, int k, int meta) {
      if (!world.field_72995_K && meta == -1) {
         meta = world.func_72805_g(i, j, k);
         LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), (EntityLivingBase)null);
         bomb.setBombStrengthLevel(meta);
         bomb.droppedByPlayer = true;
         world.func_72838_d(bomb);
         world.func_72956_a(bomb, "game.tnt.primed", 1.0F, 1.0F);
      }

   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
      if (entityplayer.func_71045_bC() != null && entityplayer.func_71045_bC().func_77973_b() == LOTRMod.orcTorchItem) {
         this.func_149664_b(world, i, j, k, -1);
         world.func_147468_f(i, j, k);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_149659_a(Explosion explosion) {
      return false;
   }

   public int func_149692_a(int i) {
      return i;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getOrcBombRenderID();
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 1; ++i) {
         for(int j = 0; j <= 2; ++j) {
            list.add(new ItemStack(item, 1, j + i * 8));
         }
      }

   }
}
