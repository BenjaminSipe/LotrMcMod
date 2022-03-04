package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockStalactite extends Block {
   private Block modelBlock;
   private int modelMeta;

   public LOTRBlockStalactite(Block block, int meta) {
      super(block.func_149688_o());
      this.modelBlock = block;
      this.modelMeta = meta;
      this.func_149672_a(this.modelBlock.field_149762_H);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
   }

   public float func_149712_f(World world, int i, int j, int k) {
      return this.modelBlock.func_149712_f(world, i, j, k);
   }

   public float func_149638_a(Entity entity) {
      return this.modelBlock.func_149638_a(entity);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.modelBlock.func_149691_a(i, this.modelMeta);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public int func_149745_a(Random random) {
      return this.modelBlock.func_149745_a(random);
   }

   public int func_149692_a(int i) {
      return i;
   }

   public boolean canSilkHarvest(World world, EntityPlayer entityplayer, int i, int j, int k, int meta) {
      return true;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getStalactiteRenderID();
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      int metadata = world.func_72805_g(i, j, k);
      if (metadata == 0) {
         return world.func_147439_a(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
      } else {
         return metadata == 1 ? world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) : false;
      }
   }

   public boolean func_149705_a(World world, int i, int j, int k, int side, ItemStack itemstack) {
      int metadata = itemstack.func_77960_j();
      if (metadata == 0) {
         return world.func_147439_a(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
      } else {
         return metadata == 1 ? world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) : false;
      }
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j <= 1; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(50) == 0 && world.func_72805_g(i, j, k) == 0) {
         Block above = world.func_147439_a(i, j + 1, k);
         if (above.func_149662_c() && above.func_149688_o() == Material.field_151576_e) {
            world.func_72869_a("dripWater", (double)i + 0.6D, (double)j, (double)k + 0.6D, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void func_149746_a(World world, int i, int j, int k, Entity entity, float fallDistance) {
      if (entity instanceof EntityLivingBase && world.func_72805_g(i, j, k) == 1) {
         int damage = (int)(fallDistance * 2.0F) + 1;
         entity.func_70097_a(DamageSource.field_76379_h, (float)damage);
      }

   }
}
