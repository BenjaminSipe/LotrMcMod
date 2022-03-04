package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRCreativeTabs;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockGate extends Block implements LOTRConnectedBlock {
   protected static final int MAX_GATE_RANGE = 16;
   private boolean hasConnectedTextures;
   public boolean fullBlockGate = false;

   protected LOTRBlockGate(Material material, boolean ct) {
      super(material);
      this.hasConnectedTextures = ct;
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
   }

   public static LOTRBlockGate createWooden(boolean ct) {
      LOTRBlockGate block = new LOTRBlockGate(Material.field_151575_d, ct);
      block.func_149711_c(4.0F);
      block.func_149752_b(5.0F);
      block.func_149672_a(Block.field_149766_f);
      return block;
   }

   public static LOTRBlockGate createStone(boolean ct) {
      LOTRBlockGate block = new LOTRBlockGate(Material.field_151576_e, ct);
      block.func_149711_c(4.0F);
      block.func_149752_b(10.0F);
      block.func_149672_a(Block.field_149769_e);
      return block;
   }

   public static LOTRBlockGate createMetal(boolean ct) {
      LOTRBlockGate block = new LOTRBlockGate(Material.field_151573_f, ct);
      block.func_149711_c(4.0F);
      block.func_149752_b(10.0F);
      block.func_149672_a(Block.field_149777_j);
      return block;
   }

   public LOTRBlockGate setFullBlock() {
      this.fullBlockGate = true;
      this.field_149786_r = 255;
      this.field_149783_u = true;
      return this;
   }

   public static boolean isGateOpen(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return isGateOpen(meta);
   }

   protected static boolean isGateOpen(int meta) {
      return (meta & 8) != 0;
   }

   protected static void setGateOpen(World world, int i, int j, int k, boolean flag) {
      int meta = world.func_72805_g(i, j, k);
      if (flag) {
         meta |= 8;
      } else {
         meta &= 7;
      }

      world.func_72921_c(i, j, k, meta, 3);
   }

   protected static int getGateDirection(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return getGateDirection(meta);
   }

   protected static int getGateDirection(int meta) {
      return meta & 7;
   }

   protected boolean directionsMatch(int dir1, int dir2) {
      if (dir1 != 0 && dir1 != 1) {
         if (dir1 != 2 && dir1 != 3) {
            if (dir1 != 4 && dir1 != 5) {
               return false;
            } else {
               return dir2 == 4 || dir2 == 5;
            }
         } else {
            return dir2 == 2 || dir2 == 3;
         }
      } else {
         return dir1 == dir2;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      if (this.hasConnectedTextures) {
         LOTRConnectedTextures.registerConnectedIcons(iconregister, this, 0, true);
      } else {
         super.func_149651_a(iconregister);
         LOTRConnectedTextures.registerNonConnectedGateIcons(iconregister, this, 0);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      boolean open = isGateOpen(world, i, j, k);
      if (this.hasConnectedTextures) {
         return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, open);
      } else {
         return open ? LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false) : this.field_149761_L;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.hasConnectedTextures ? LOTRConnectedTextures.getConnectedIconItem(this, j) : this.field_149761_L;
   }

   public String getConnectedName(int meta) {
      return this.field_149768_d;
   }

   public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      int meta = world.func_72805_g(i, j, k);
      Block otherBlock = world.func_147439_a(i1, j1, k1);
      int otherMeta = world.func_72805_g(i1, j1, k1);
      int dir = getGateDirection(meta);
      boolean open = isGateOpen(meta);
      int otherDir = getGateDirection(otherMeta);
      boolean otherOpen = isGateOpen(otherMeta);
      if ((dir == 0 || dir == 1) && j1 != j) {
         return false;
      } else if ((dir == 2 || dir == 3) && k1 != k) {
         return false;
      } else if ((dir == 4 || dir == 5) && i1 != i) {
         return false;
      } else if (open && j1 == j - 1 && dir != 0 && dir != 1 && !(otherBlock instanceof LOTRBlockGate)) {
         return true;
      } else {
         boolean connected = open ? otherBlock instanceof LOTRBlockGate : otherBlock == this;
         return connected && this.directionsMatch(dir, otherDir) && ((LOTRBlockGate)otherBlock).directionsMatch(dir, otherDir) && open == otherOpen;
      }
   }

   @SideOnly(Side.CLIENT)
   public boolean func_149646_a(IBlockAccess world, int i, int j, int k, int side) {
      int i1 = i - Facing.field_71586_b[side];
      int j1 = j - Facing.field_71587_c[side];
      int k1 = k - Facing.field_71585_d[side];
      Block otherBlock = world.func_147439_a(i, j, k);
      if (otherBlock instanceof LOTRBlockGate) {
         int metaThis = world.func_72805_g(i1, j1, k1);
         int metaOther = world.func_72805_g(i, j, k);
         int dirThis = getGateDirection(metaThis);
         boolean openThis = isGateOpen(metaThis);
         int dirOther = getGateDirection(metaOther);
         boolean openOther = isGateOpen(metaOther);
         if (!this.fullBlockGate || openThis) {
            boolean connect = !this.directionsMatch(dirThis, side);
            if (connect) {
               return openThis != openOther || !this.directionsMatch(dirThis, dirOther) || !((LOTRBlockGate)otherBlock).directionsMatch(dirThis, dirOther);
            }
         }
      }

      return super.func_149646_a(world, i, j, k, side);
   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149662_c() {
      return false;
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      int dir = getGateDirection(world, i, j, k);
      this.setBlockBoundsForDirection(dir);
   }

   private void setBlockBoundsForDirection(int dir) {
      if (this.fullBlockGate) {
         this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         float width = 0.25F;
         float halfWidth = width / 2.0F;
         if (dir == 0) {
            this.func_149676_a(0.0F, 1.0F - width, 0.0F, 1.0F, 1.0F, 1.0F);
         } else if (dir == 1) {
            this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, width, 1.0F);
         } else if (dir != 2 && dir != 3) {
            if (dir == 4 || dir == 5) {
               this.func_149676_a(0.5F - halfWidth, 0.0F, 0.0F, 0.5F + halfWidth, 1.0F, 1.0F);
            }
         } else {
            this.func_149676_a(0.0F, 0.0F, 0.5F - halfWidth, 1.0F, 1.0F, 0.5F + halfWidth);
         }
      }

   }

   public void func_149683_g() {
      this.setBlockBoundsForDirection(4);
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      if (isGateOpen(world, i, j, k)) {
         return null;
      } else {
         this.func_149719_a(world, i, j, k);
         return super.func_149668_a(world, i, j, k);
      }
   }

   public boolean func_149655_b(IBlockAccess world, int i, int j, int k) {
      return isGateOpen(world, i, j, k);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_70694_bm();
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (Block.func_149634_a(item) instanceof LOTRBlockGate) {
            return false;
         }

         if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
            return false;
         }
      }

      if (!world.field_72995_K) {
         this.activateGate(world, i, j, k);
      }

      return true;
   }

   private void activateGate(World world, int i, int j, int k) {
      boolean wasOpen = isGateOpen(world, i, j, k);
      boolean isOpen = !wasOpen;
      List gates = this.getConnectedGates(world, i, j, k);
      Iterator var8 = gates.iterator();

      while(var8.hasNext()) {
         ChunkCoordinates coords = (ChunkCoordinates)var8.next();
         setGateOpen(world, coords.field_71574_a, coords.field_71572_b, coords.field_71573_c, isOpen);
      }

      String soundEffect = "";
      boolean stone = this.func_149688_o() == Material.field_151576_e;
      if (stone) {
         soundEffect = isOpen ? "lotr:block.gate.stone_open" : "lotr:block.gate.stone_close";
      } else {
         soundEffect = isOpen ? "lotr:block.gate.open" : "lotr:block.gate.close";
      }

      world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, soundEffect, 1.0F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
   }

   private List getConnectedGates(World world, int i, int j, int k) {
      boolean open = isGateOpen(world, i, j, k);
      int dir = getGateDirection(world, i, j, k);
      Set allCoords = new HashSet();
      Set lastDepthCoords = new HashSet();
      Set currentDepthCoords = new HashSet();

      for(int depth = 0; depth <= 16; ++depth) {
         if (depth == 0) {
            allCoords.add(new ChunkCoordinates(i, j, k));
            currentDepthCoords.add(new ChunkCoordinates(i, j, k));
         } else {
            Iterator var11 = lastDepthCoords.iterator();

            while(var11.hasNext()) {
               ChunkCoordinates coords = (ChunkCoordinates)var11.next();
               this.gatherAdjacentGates(world, coords.field_71574_a, coords.field_71572_b, coords.field_71573_c, dir, open, allCoords, currentDepthCoords);
            }
         }

         lastDepthCoords.clear();
         lastDepthCoords.addAll(currentDepthCoords);
         currentDepthCoords.clear();
      }

      return new ArrayList(allCoords);
   }

   private void gatherAdjacentGates(World world, int i, int j, int k, int dir, boolean open, Set allCoords, Set currentDepthCoords) {
      if (dir != 0 && dir != 1) {
         this.gatherAdjacentGate(world, i, j - 1, k, dir, open, allCoords, currentDepthCoords);
         this.gatherAdjacentGate(world, i, j + 1, k, dir, open, allCoords, currentDepthCoords);
      }

      if (dir != 2 && dir != 3) {
         this.gatherAdjacentGate(world, i, j, k - 1, dir, open, allCoords, currentDepthCoords);
         this.gatherAdjacentGate(world, i, j, k + 1, dir, open, allCoords, currentDepthCoords);
      }

      if (dir != 4 && dir != 5) {
         this.gatherAdjacentGate(world, i - 1, j, k, dir, open, allCoords, currentDepthCoords);
         this.gatherAdjacentGate(world, i + 1, j, k, dir, open, allCoords, currentDepthCoords);
      }

   }

   private void gatherAdjacentGate(World world, int i, int j, int k, int dir, boolean open, Set allCoords, Set currentDepthCoords) {
      ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
      if (!allCoords.contains(coords)) {
         Block otherBlock = world.func_147439_a(i, j, k);
         if (otherBlock instanceof LOTRBlockGate) {
            boolean otherOpen = isGateOpen(world, i, j, k);
            int otherDir = getGateDirection(world, i, j, k);
            if (otherOpen == open && this.directionsMatch(dir, otherDir) && ((LOTRBlockGate)otherBlock).directionsMatch(dir, otherDir)) {
               allCoords.add(coords);
               currentDepthCoords.add(coords);
            }
         }

      }
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!world.field_72995_K && !(block instanceof LOTRBlockGate)) {
         boolean open = isGateOpen(world, i, j, k);
         boolean powered = false;
         List gates = this.getConnectedGates(world, i, j, k);
         Iterator var9 = gates.iterator();

         while(var9.hasNext()) {
            ChunkCoordinates coords = (ChunkCoordinates)var9.next();
            int i1 = coords.field_71574_a;
            int j1 = coords.field_71572_b;
            int k1 = coords.field_71573_c;
            if (world.func_72864_z(i1, j1, k1)) {
               powered = true;
               break;
            }
         }

         if (powered || block.func_149744_f()) {
            if (powered && !open) {
               this.activateGate(world, i, j, k);
            } else if (!powered && open) {
               this.activateGate(world, i, j, k);
            }
         }
      }

   }
}
