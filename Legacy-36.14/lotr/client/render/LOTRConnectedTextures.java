package lotr.client.render;

import cpw.mods.fml.common.FMLLog;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import lotr.common.block.LOTRConnectedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class LOTRConnectedTextures {
   private static Map blockIconsMap = new HashMap();

   public static IIcon getConnectedIconBlock(LOTRConnectedBlock block, IBlockAccess world, int i, int j, int k, int side, boolean noBase) {
      int meta = world.func_72805_g(i, j, k);
      String blockName = block.getConnectedName(meta);
      boolean[][] flags = new boolean[3][3];

      for(int x = -1; x <= 1; ++x) {
         for(int y = -1; y <= 1; ++y) {
            boolean match = false;
            if (x == 0 && y == 0) {
               match = true;
            } else {
               int i1 = i;
               int j1 = j;
               int k1 = k;
               if (side == 0) {
                  i1 = i + x;
                  k1 = k + y;
               } else if (side == 1) {
                  i1 = i + x;
                  k1 = k + y;
               } else if (side == 2) {
                  i1 = i - x;
                  j1 = j - y;
               } else if (side == 3) {
                  i1 = i + x;
                  j1 = j - y;
               } else if (side == 4) {
                  k1 = k + x;
                  j1 = j - y;
               } else if (side == 5) {
                  k1 = k - x;
                  j1 = j - y;
               }

               match = block.areBlocksConnected(world, i, j, k, i1, j1, k1);
            }

            flags[y + 1][x + 1] = match;
         }
      }

      return getConnectedIcon(blockName, flags, noBase);
   }

   public static IIcon getConnectedIconItem(LOTRConnectedBlock block, int meta) {
      boolean[][] adjacentFlags = new boolean[][]{{false, false, false}, {false, true, false}, {false, false, false}};
      return getConnectedIconItem(block, meta, adjacentFlags);
   }

   public static IIcon getConnectedIconItem(LOTRConnectedBlock block, int meta, boolean[][] adjacentFlags) {
      String blockName = block.getConnectedName(meta);
      return getConnectedIcon(blockName, adjacentFlags, false);
   }

   private static IIcon getConnectedIcon(String blockName, boolean[][] adjacentFlags, boolean noBase) {
      if (blockIconsMap.containsKey(blockName) && !((Map)blockIconsMap.get(blockName)).isEmpty()) {
         Set set = new HashSet();
         if (!noBase) {
            set.add(LOTRConnectedTextures.IconElement.BASE);
         }

         if (adjacentFlags != null) {
            boolean topLeft = adjacentFlags[0][0];
            boolean top = adjacentFlags[0][1];
            boolean topRight = adjacentFlags[0][2];
            boolean left = adjacentFlags[1][0];
            boolean mid = adjacentFlags[1][1];
            boolean right = adjacentFlags[1][2];
            boolean bottomLeft = adjacentFlags[2][0];
            boolean bottom = adjacentFlags[2][1];
            boolean bottomRight = adjacentFlags[2][2];
            if (!left) {
               set.add(LOTRConnectedTextures.IconElement.SIDE_LEFT);
            }

            if (!right) {
               set.add(LOTRConnectedTextures.IconElement.SIDE_RIGHT);
            }

            if (!top) {
               set.add(LOTRConnectedTextures.IconElement.SIDE_TOP);
            }

            if (!bottom) {
               set.add(LOTRConnectedTextures.IconElement.SIDE_BOTTOM);
            }

            if (!left && !top) {
               set.add(LOTRConnectedTextures.IconElement.CORNER_TOPLEFT);
            }

            if (!right && !top) {
               set.add(LOTRConnectedTextures.IconElement.CORNER_TOPRIGHT);
            }

            if (!left && !bottom) {
               set.add(LOTRConnectedTextures.IconElement.CORNER_BOTTOMLEFT);
            }

            if (!right && !bottom) {
               set.add(LOTRConnectedTextures.IconElement.CORNER_BOTTOMRIGHT);
            }

            if (left && top && !topLeft) {
               set.add(LOTRConnectedTextures.IconElement.INVCORNER_TOPLEFT);
            }

            if (right && top && !topRight) {
               set.add(LOTRConnectedTextures.IconElement.INVCORNER_TOPRIGHT);
            }

            if (left && bottom && !bottomLeft) {
               set.add(LOTRConnectedTextures.IconElement.INVCORNER_BOTTOMLEFT);
            }

            if (right && bottom && !bottomRight) {
               set.add(LOTRConnectedTextures.IconElement.INVCORNER_BOTTOMRIGHT);
            }
         }

         int key = LOTRConnectedTextures.IconElement.getIconSetKey(set);
         return (IIcon)((Map)blockIconsMap.get(blockName)).get(key);
      } else {
         return Minecraft.func_71410_x().func_147117_R().func_110572_b("");
      }
   }

   public static void registerConnectedIcons(IIconRegister iconregister, LOTRConnectedBlock block, int meta, boolean includeNoBase) {
      String iconName = block.getConnectedName(meta);
      Map iconElementMap = getConnectedIconElements(iconName);
      createConnectedIcons(iconregister, block, meta, includeNoBase, iconElementMap);
   }

   private static String getBaseIconName(String blockName) {
      String s = blockName;
      int pathIndex = blockName.indexOf(":");
      if (pathIndex >= 0) {
         s = blockName.substring(pathIndex + 1);
      }

      return s;
   }

   private static String getModID(String blockName) {
      int pathIndex = blockName.indexOf(":");
      return pathIndex >= 0 ? blockName.substring(0, pathIndex) : "";
   }

   private static Map getConnectedIconElements(String iconName) {
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resourceManager = mc.func_110442_L();
      String baseIconName = getBaseIconName(iconName);
      String modID = getModID(iconName);
      HashMap iconElementMap = new HashMap();

      try {
         LOTRConnectedTextures.IconElement[] var6 = LOTRConnectedTextures.IconElement.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            LOTRConnectedTextures.IconElement e = var6[var8];
            ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + "_" + e.iconName + ".png");
            BufferedImage image = ImageIO.read(resourceManager.func_110536_a(res).func_110527_b());
            iconElementMap.put(e, image);
         }
      } catch (IOException var12) {
         FMLLog.severe("Failed to load connected textures for %s", new Object[]{modID + ":" + baseIconName});
         var12.printStackTrace();
      }

      return iconElementMap;
   }

   private static void createConnectedIcons(IIconRegister iconregister, LOTRConnectedBlock block, int meta, boolean includeNoBase, Map iconElementMap) {
      String blockName = block.getConnectedName(meta);
      blockIconsMap.remove(blockName);
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resourceManager = mc.func_110442_L();
      TextureMap textureMap = (TextureMap)iconregister;
      String baseIconName = getBaseIconName(blockName);
      String modID = getModID(blockName);
      BufferedImage iconElementBase = (BufferedImage)iconElementMap.get(LOTRConnectedTextures.IconElement.BASE);
      int iconWidth = iconElementBase.getWidth();
      int iconHeight = iconElementBase.getHeight();
      Iterator var14 = iconElementMap.entrySet().iterator();

      while(true) {
         Entry entry;
         LOTRConnectedTextures.IconElement elemt;
         BufferedImage img;
         do {
            do {
               if (!var14.hasNext()) {
                  Map iconsMap = new HashMap();
                  Iterator var30 = LOTRConnectedTextures.IconElement.allCombos.entrySet().iterator();

                  while(true) {
                     while(true) {
                        int key;
                        List list;
                        do {
                           if (!var30.hasNext()) {
                              blockIconsMap.put(blockName, iconsMap);
                              return;
                           }

                           Entry entry = (Entry)var30.next();
                           key = (Integer)entry.getKey();
                           Set set = (Set)entry.getValue();
                           list = LOTRConnectedTextures.IconElement.sortIconSet(set);
                        } while(!includeNoBase && !list.contains(LOTRConnectedTextures.IconElement.BASE));

                        String iconName = modID + ":textures/blocks/" + baseIconName + "_" + key;
                        if (textureMap.getTextureExtry(iconName) != null) {
                           FMLLog.severe("Icon is already registered for %s", new Object[]{iconName});
                        } else {
                           BufferedImage iconImage = new BufferedImage(iconWidth, iconHeight, 2);
                           Iterator var22 = list.iterator();

                           while(var22.hasNext()) {
                              LOTRConnectedTextures.IconElement e = (LOTRConnectedTextures.IconElement)var22.next();
                              BufferedImage baseIconImage = (BufferedImage)iconElementMap.get(e);

                              for(int i = 0; i < iconImage.getWidth(); ++i) {
                                 for(int j = 0; j < iconImage.getHeight(); ++j) {
                                    int rgb = baseIconImage.getRGB(i, j);
                                    int alpha = rgb & -16777216;
                                    if (alpha != 0) {
                                       iconImage.setRGB(i, j, rgb);
                                    }
                                 }
                              }
                           }

                           LOTRBufferedImageIcon icon = new LOTRBufferedImageIcon(iconName, iconImage);
                           icon.func_110966_b(iconImage.getWidth());
                           icon.func_110969_c(iconImage.getHeight());
                           textureMap.setTextureEntry(iconName, icon);
                           iconsMap.put(key, icon);
                        }
                     }
                  }
               }

               entry = (Entry)var14.next();
               elemt = (LOTRConnectedTextures.IconElement)entry.getKey();
               img = (BufferedImage)entry.getValue();
            } while(elemt == LOTRConnectedTextures.IconElement.BASE);
         } while(img.getWidth() == iconWidth && img.getHeight() == iconHeight);

         FMLLog.severe("LOTR: All connected texture icons for %s must have the same dimensions!", new Object[]{baseIconName});
         FMLLog.severe("%s: base icon is %dx%d, but %s icon is %dx%d", new Object[]{baseIconName, iconWidth, iconHeight, elemt.iconName, img.getWidth(), img.getHeight()});
         BufferedImage errored = new BufferedImage(iconWidth, iconHeight, 2);

         for(int i = 0; i < errored.getWidth(); ++i) {
            for(int j = 0; j < errored.getHeight(); ++j) {
               int rgb = false;
               int rgb;
               if ((i + j) % 2 == 0) {
                  rgb = 16711680;
               } else {
                  rgb = 0;
               }

               errored.setRGB(i, j, -16777216 | rgb);
            }
         }

         entry.setValue(errored);
      }
   }

   public static void registerNonConnectedGateIcons(IIconRegister iconregister, LOTRConnectedBlock block, int meta) {
      registerNonConnectedGateIcons(iconregister, block, meta, block.getConnectedName(meta));
   }

   public static void registerNonConnectedGateIcons(IIconRegister iconregister, LOTRConnectedBlock block, int meta, String iconName) {
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resourceManager = mc.func_110442_L();
      String baseIconName = getBaseIconName(iconName);
      String modID = getModID(iconName);
      HashMap iconElementMap = new HashMap();

      try {
         ResourceLocation res = new ResourceLocation(modID, "textures/blocks/" + baseIconName + ".png");
         BufferedImage blockIconImage = ImageIO.read(resourceManager.func_110536_a(res).func_110527_b());
         int iconWidth = blockIconImage.getWidth();
         int iconHeight = blockIconImage.getHeight();
         int sideWidth = Math.max(Math.round((float)iconWidth / 16.0F * 3.0F), 1);
         int sideHeight = Math.max(Math.round((float)iconHeight / 16.0F * 3.0F), 1);
         BufferedImage emptyBase = new BufferedImage(iconWidth, iconHeight, 2);
         iconElementMap.put(LOTRConnectedTextures.IconElement.BASE, emptyBase);
         iconElementMap.put(LOTRConnectedTextures.IconElement.SIDE_LEFT, getSubImageIcon(blockIconImage, 0, 0, sideWidth, iconHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.SIDE_RIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, iconHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.SIDE_TOP, getSubImageIcon(blockIconImage, 0, 0, iconWidth, sideHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.SIDE_BOTTOM, getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, iconWidth, sideHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.CORNER_TOPLEFT, getSubImageIcon(blockIconImage, 0, 0, sideWidth, sideHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.CORNER_TOPRIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, 0, sideWidth, sideHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.CORNER_BOTTOMLEFT, getSubImageIcon(blockIconImage, 0, iconHeight - sideHeight, sideWidth, sideHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.CORNER_BOTTOMRIGHT, getSubImageIcon(blockIconImage, iconWidth - sideWidth, iconHeight - sideHeight, sideWidth, sideHeight));
         iconElementMap.put(LOTRConnectedTextures.IconElement.INVCORNER_TOPLEFT, iconElementMap.get(LOTRConnectedTextures.IconElement.CORNER_TOPLEFT));
         iconElementMap.put(LOTRConnectedTextures.IconElement.INVCORNER_TOPRIGHT, iconElementMap.get(LOTRConnectedTextures.IconElement.CORNER_TOPRIGHT));
         iconElementMap.put(LOTRConnectedTextures.IconElement.INVCORNER_BOTTOMLEFT, iconElementMap.get(LOTRConnectedTextures.IconElement.CORNER_BOTTOMLEFT));
         iconElementMap.put(LOTRConnectedTextures.IconElement.INVCORNER_BOTTOMRIGHT, iconElementMap.get(LOTRConnectedTextures.IconElement.CORNER_BOTTOMRIGHT));
      } catch (IOException var16) {
         FMLLog.severe("Failed to load connected textures for %s", new Object[]{modID + ":" + baseIconName});
         var16.printStackTrace();
      }

      createConnectedIcons(iconregister, block, meta, false, iconElementMap);
   }

   private static BufferedImage getSubImageIcon(BufferedImage base, int x, int y, int width, int height) {
      BufferedImage subpart = base.getSubimage(x, y, width, height);
      BufferedImage img = new BufferedImage(base.getWidth(), base.getHeight(), 2);

      for(int subX = 0; subX < width; ++subX) {
         for(int subY = 0; subY < height; ++subY) {
            img.setRGB(x + subX, y + subY, subpart.getRGB(subX, subY));
         }
      }

      return img;
   }

   private static enum IconElement {
      BASE("base", 0),
      SIDE_LEFT("left", 1),
      SIDE_RIGHT("right", 1),
      SIDE_TOP("top", 1),
      SIDE_BOTTOM("bottom", 1),
      CORNER_TOPLEFT("topLeft", 2),
      CORNER_TOPRIGHT("topRight", 2),
      CORNER_BOTTOMLEFT("bottomLeft", 2),
      CORNER_BOTTOMRIGHT("bottomRight", 2),
      INVCORNER_TOPLEFT("topLeftInv", 2),
      INVCORNER_TOPRIGHT("topRightInv", 2),
      INVCORNER_BOTTOMLEFT("bottomLeftInv", 2),
      INVCORNER_BOTTOMRIGHT("bottomRightInv", 2);

      public final String iconName;
      private final int bitFlag;
      private final int priority;
      private static EnumSet allSides = EnumSet.of(SIDE_LEFT, SIDE_RIGHT, SIDE_TOP, SIDE_BOTTOM);
      private static EnumSet allCorners = EnumSet.of(CORNER_TOPLEFT, CORNER_TOPRIGHT, CORNER_BOTTOMLEFT, CORNER_BOTTOMRIGHT);
      private static EnumSet allInvCorners = EnumSet.of(INVCORNER_TOPLEFT, INVCORNER_TOPRIGHT, INVCORNER_BOTTOMLEFT, INVCORNER_BOTTOMRIGHT);
      public static Map allCombos = new HashMap();
      private static Comparator comparator;

      private IconElement(String s, int i) {
         this.iconName = s;
         this.bitFlag = 1 << this.ordinal();
         this.priority = i;
      }

      public static int getIconSetKey(Set set) {
         int i = 0;
         LOTRConnectedTextures.IconElement[] var2 = values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            LOTRConnectedTextures.IconElement e = var2[var4];
            if (set.contains(e)) {
               i |= e.bitFlag;
            }
         }

         return i;
      }

      public static List sortIconSet(Set set) {
         List list = new ArrayList();
         list.addAll(set);
         Collections.sort(list, comparator);
         return list;
      }

      static {
         List permutations = new ArrayList();
         boolean[] trueOrFalse = new boolean[]{false, true};
         boolean[] var2 = trueOrFalse;
         int var3 = trueOrFalse.length;

         int key;
         for(key = 0; key < var3; ++key) {
            boolean base = var2[key];
            boolean[] var6 = trueOrFalse;
            int var7 = trueOrFalse.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               boolean left = var6[var8];
               boolean[] var10 = trueOrFalse;
               int var11 = trueOrFalse.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  boolean right = var10[var12];
                  boolean[] var14 = trueOrFalse;
                  int var15 = trueOrFalse.length;

                  for(int var16 = 0; var16 < var15; ++var16) {
                     boolean top = var14[var16];
                     boolean[] var18 = trueOrFalse;
                     int var19 = trueOrFalse.length;

                     for(int var20 = 0; var20 < var19; ++var20) {
                        boolean bottom = var18[var20];
                        boolean[] var22 = trueOrFalse;
                        int var23 = trueOrFalse.length;

                        for(int var24 = 0; var24 < var23; ++var24) {
                           boolean topLeft = var22[var24];
                           boolean[] var26 = trueOrFalse;
                           int var27 = trueOrFalse.length;

                           for(int var28 = 0; var28 < var27; ++var28) {
                              boolean topRight = var26[var28];
                              boolean[] var30 = trueOrFalse;
                              int var31 = trueOrFalse.length;

                              for(int var32 = 0; var32 < var31; ++var32) {
                                 boolean bottomLeft = var30[var32];
                                 boolean[] var34 = trueOrFalse;
                                 int var35 = trueOrFalse.length;

                                 for(int var36 = 0; var36 < var35; ++var36) {
                                    boolean bottomRight = var34[var36];
                                    boolean[] var38 = trueOrFalse;
                                    int var39 = trueOrFalse.length;

                                    for(int var40 = 0; var40 < var39; ++var40) {
                                       boolean topLeftInv = var38[var40];
                                       boolean[] var42 = trueOrFalse;
                                       int var43 = trueOrFalse.length;

                                       for(int var44 = 0; var44 < var43; ++var44) {
                                          boolean topRightInv = var42[var44];
                                          boolean[] var46 = trueOrFalse;
                                          int var47 = trueOrFalse.length;

                                          for(int var48 = 0; var48 < var47; ++var48) {
                                             boolean bottomLeftInv = var46[var48];
                                             boolean[] var50 = trueOrFalse;
                                             int var51 = trueOrFalse.length;

                                             for(int var52 = 0; var52 < var51; ++var52) {
                                                boolean bottomRightInv = var50[var52];
                                                Set set = new HashSet();
                                                if (base) {
                                                   set.add(BASE);
                                                }

                                                boolean addLeft = left && (!top || topLeft) && (!bottom || bottomLeft);
                                                boolean addRight = right && (!top || topRight) && (!bottom || bottomRight);
                                                boolean addTop = top && (!left || topLeft) && (!right || topRight);
                                                boolean addBottom = bottom && (!left || bottomLeft) && (!right || bottomRight);
                                                if (addLeft) {
                                                   set.add(SIDE_LEFT);
                                                }

                                                if (addRight) {
                                                   set.add(SIDE_RIGHT);
                                                }

                                                if (addTop) {
                                                   set.add(SIDE_TOP);
                                                }

                                                if (addBottom) {
                                                   set.add(SIDE_BOTTOM);
                                                }

                                                if (topLeft && addTop && addLeft) {
                                                   set.add(CORNER_TOPLEFT);
                                                }

                                                if (topRight && addTop && addRight) {
                                                   set.add(CORNER_TOPRIGHT);
                                                }

                                                if (bottomLeft && addBottom && addLeft) {
                                                   set.add(CORNER_BOTTOMLEFT);
                                                }

                                                if (bottomRight && addBottom && addRight) {
                                                   set.add(CORNER_BOTTOMRIGHT);
                                                }

                                                if (topLeftInv && !topLeft && !addTop && !addLeft) {
                                                   set.add(INVCORNER_TOPLEFT);
                                                }

                                                if (topRightInv && !topRight && !addTop && !addRight) {
                                                   set.add(INVCORNER_TOPRIGHT);
                                                }

                                                if (bottomLeftInv && !bottomLeft && !addBottom && !addLeft) {
                                                   set.add(INVCORNER_BOTTOMLEFT);
                                                }

                                                if (bottomRightInv && !bottomRight && !addBottom && !addRight) {
                                                   set.add(INVCORNER_BOTTOMRIGHT);
                                                }

                                                permutations.add(set);
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         Iterator var59 = permutations.iterator();

         while(var59.hasNext()) {
            Set iconSet = (Set)var59.next();
            key = getIconSetKey(iconSet);
            if (!allCombos.containsKey(key)) {
               allCombos.put(key, iconSet);
            }
         }

         comparator = new Comparator() {
            public int compare(LOTRConnectedTextures.IconElement e1, LOTRConnectedTextures.IconElement e2) {
               return e1.priority == e2.priority ? e1.compareTo(e2) : Integer.valueOf(e1.priority).compareTo(e2.priority);
            }
         };
      }
   }
}
