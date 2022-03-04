package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockGateDwarvenIthildin extends LOTRBlockGateDwarven {
   public boolean hasTileEntity(int meta) {
      return true;
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new LOTRTileEntityDwarvenDoor();
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      LOTRBlockGateDwarvenIthildin.DoorSize[] var2 = LOTRBlockGateDwarvenIthildin.DoorSize.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRBlockGateDwarvenIthildin.DoorSize s = var2[var4];

         for(int i = 0; i < s.width; ++i) {
            for(int j = 0; j < s.height; ++j) {
               IIcon icon = iconregister.func_94245_a(this.func_149641_N() + "_glow_" + s.doorName + "_" + i + "_" + j);
               s.icons[i][j] = icon;
            }
         }
      }

   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      super.func_149689_a(world, i, j, k, entity, itemstack);
      LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)world.func_147438_o(i, j, k);
      if (door != null) {
         int meta = world.func_72805_g(i, j, k);
         int dir = getGateDirection(meta);
         door.setDoorSizeAndPos((LOTRBlockGateDwarvenIthildin.DoorSize)null, 0, 0);
         door.setDoorBasePos(i, j, k);
         if (dir != 0 && dir != 1) {
            int xzFactorX = dir == 2 ? 1 : (dir == 3 ? -1 : 0);
            int xzFactorZ = dir == 4 ? -1 : (dir == 5 ? 1 : 0);
            Iterator var12 = LOTRBlockGateDwarvenIthildin.DoorSize.orderedSizes.iterator();

            while(var12.hasNext()) {
               LOTRBlockGateDwarvenIthildin.DoorSize doorSize = (LOTRBlockGateDwarvenIthildin.DoorSize)var12.next();
               int width = doorSize.width;
               int height = doorSize.height;
               int rangeXZ = width - 1;
               int rangeY = height - 1;

               for(int y = -rangeY; y <= 0; ++y) {
                  for(int xz = -rangeXZ; xz <= 0; ++xz) {
                     int j1 = j + y;
                     int i1 = i + xz * xzFactorX;
                     int k1 = k + xz * xzFactorZ;
                     boolean connected = true;
                     boolean canReplaceSize = true;

                     int y1;
                     int xz1;
                     int j2;
                     int i2;
                     int k2;
                     TileEntity te;
                     LOTRTileEntityDwarvenDoor otherDoor;
                     label112:
                     for(y1 = 0; y1 <= rangeY; ++y1) {
                        for(xz1 = 0; xz1 <= rangeXZ; ++xz1) {
                           j2 = j1 + y1;
                           i2 = i1 + xz1 * xzFactorX;
                           k2 = k1 + xz1 * xzFactorZ;
                           if (i2 != i || j2 != j || k2 != k) {
                              if (!this.areDwarfDoorsMatching(world, i, j, k, i2, j2, k2)) {
                                 connected = false;
                                 break label112;
                              }

                              te = world.func_147438_o(i2, j2, k2);
                              if (te instanceof LOTRTileEntityDwarvenDoor) {
                                 otherDoor = (LOTRTileEntityDwarvenDoor)te;
                                 LOTRBlockGateDwarvenIthildin.DoorSize otherSize = otherDoor.getDoorSize();
                                 if (LOTRBlockGateDwarvenIthildin.DoorSize.compareLarger.compare(otherSize, doorSize) != 1) {
                                    canReplaceSize = false;
                                    break label112;
                                 }
                              }
                           }
                        }
                     }

                     if (connected && canReplaceSize) {
                        door.setDoorSizeAndPos(doorSize, -xz, -y);
                        door.setDoorBasePos(i, j, k);

                        for(y1 = 0; y1 <= rangeY; ++y1) {
                           for(xz1 = 0; xz1 <= rangeXZ; ++xz1) {
                              j2 = j1 + y1;
                              i2 = i1 + xz1 * xzFactorX;
                              k2 = k1 + xz1 * xzFactorZ;
                              if (i2 != i || j2 != j || k2 != k) {
                                 te = world.func_147438_o(i2, j2, k2);
                                 if (te instanceof LOTRTileEntityDwarvenDoor) {
                                    otherDoor = (LOTRTileEntityDwarvenDoor)te;
                                    otherDoor.setDoorSizeAndPos(doorSize, xz1, y1);
                                    otherDoor.setDoorBasePos(i, j, k);
                                 }
                              }
                           }
                        }

                        return;
                     }
                  }
               }
            }
         }
      }

   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)world.func_147438_o(i, j, k);
      if (door != null) {
         LOTRBlockGateDwarvenIthildin.DoorSize doorSize = door.getDoorSize();
         if (doorSize != null) {
            int dir = getGateDirection(meta);
            if (dir != 0 && dir != 1) {
               int xzFactorX = dir == 2 ? 1 : (dir == 3 ? -1 : 0);
               int xzFactorZ = dir == 4 ? -1 : (dir == 5 ? 1 : 0);
               int width = doorSize.width;
               int height = doorSize.height;
               int rangeXZ = width - 1;
               int rangeY = height - 1;

               for(int y = -rangeY; y <= rangeY; ++y) {
                  for(int xz = -rangeXZ; xz <= rangeXZ; ++xz) {
                     int j1 = j + y;
                     int i1 = i + xz * xzFactorX;
                     int k1 = k + xz * xzFactorZ;
                     if (i1 != i || j1 != j || k1 != k) {
                        TileEntity te = world.func_147438_o(i1, j1, k1);
                        if (te instanceof LOTRTileEntityDwarvenDoor) {
                           LOTRTileEntityDwarvenDoor otherDoor = (LOTRTileEntityDwarvenDoor)te;
                           if (otherDoor.isSameDoor(door)) {
                              otherDoor.setDoorSizeAndPos((LOTRBlockGateDwarvenIthildin.DoorSize)null, 0, 0);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public IIcon getGlowIcon(IBlockAccess world, int i, int j, int k, int side) {
      int meta = world.func_72805_g(i, j, k);
      int dir = getGateDirection(meta);
      boolean open = isGateOpen(world, i, j, k);
      if (!open && dir == Facing.field_71588_a[side]) {
         TileEntity te = world.func_147438_o(i, j, k);
         if (te instanceof LOTRTileEntityDwarvenDoor) {
            LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)te;
            return door.getDoorSize().icons[door.getDoorPosX()][door.getDoorPosY()];
         }
      }

      return null;
   }

   protected boolean directionsMatch(int dir1, int dir2) {
      return dir1 == dir2;
   }

   private boolean areDwarfDoorsMatching(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
      Block block = world.func_147439_a(i, j, k);
      int meta = world.func_72805_g(i, j, k);
      Block otherBlock = world.func_147439_a(i1, j1, k1);
      int otherMeta = world.func_72805_g(i1, j1, k1);
      int dir = getGateDirection(meta);
      int otherDir = getGateDirection(otherMeta);
      return block == this && block == otherBlock && ((LOTRBlockGate)block).directionsMatch(dir, otherDir) && ((LOTRBlockGate)otherBlock).directionsMatch(dir, otherDir);
   }

   public static enum DoorSize {
      _1x1("1x1", 1, 1),
      _1x2("1x2", 1, 2),
      _2x2("2x2", 2, 2),
      _2x3("2x3", 2, 3),
      _3x4("3x4", 3, 4);

      public final String doorName;
      public final int width;
      public final int height;
      public final int area;
      public final IIcon[][] icons;
      public static Comparator compareLarger = new Comparator() {
         public int compare(LOTRBlockGateDwarvenIthildin.DoorSize s1, LOTRBlockGateDwarvenIthildin.DoorSize s2) {
            if (s1 == s2) {
               return 0;
            } else if (s1.area != s2.area) {
               return -Integer.valueOf(s1.area).compareTo(s2.area);
            } else {
               return s1.height != s2.height ? -Integer.valueOf(s1.height).compareTo(s2.height) : -Integer.valueOf(s1.width).compareTo(s2.width);
            }
         }
      };
      public static List orderedSizes = new ArrayList();

      private DoorSize(String s, int i, int j) {
         this.doorName = s;
         this.width = i;
         this.height = j;
         this.area = this.width * this.height;
         this.icons = new IIcon[this.width][this.height];
      }

      public static LOTRBlockGateDwarvenIthildin.DoorSize forName(String s) {
         LOTRBlockGateDwarvenIthildin.DoorSize[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRBlockGateDwarvenIthildin.DoorSize size = var1[var3];
            if (size.doorName.equals(s)) {
               return size;
            }
         }

         return null;
      }

      static {
         LOTRBlockGateDwarvenIthildin.DoorSize[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            LOTRBlockGateDwarvenIthildin.DoorSize s = var0[var2];
            orderedSizes.add(s);
         }

         Collections.sort(orderedSizes, compareLarger);
      }
   }
}
