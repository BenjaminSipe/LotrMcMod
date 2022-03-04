package lotr.client;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.lang.reflect.Method;
import lotr.common.LOTRReflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRReflectionClient {
   private static int[] colorCodes;

   public static void testAll(World world, Minecraft mc) {
      setCameraRoll(mc.field_71460_t, getCameraRoll(mc.field_71460_t));
      setHandFOV(mc.field_71460_t, getHandFOV(mc.field_71460_t));
      getColorCodes(mc.field_71466_p);
      setHighlightedItemTicks(mc.field_71456_v, getHighlightedItemTicks(mc.field_71456_v));
      getHighlightedItemStack(mc.field_71456_v);
   }

   public static void setCameraRoll(EntityRenderer renderer, float roll) {
      try {
         ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, roll, new String[]{"camRoll", "field_78495_O"});
      } catch (Exception var3) {
         LOTRReflection.logFailure(var3);
      }

   }

   public static float getCameraRoll(EntityRenderer renderer) {
      try {
         return (Float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"camRoll", "field_78495_O"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0.0F;
      }
   }

   public static void setHandFOV(EntityRenderer renderer, float fov) {
      try {
         ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, fov, new String[]{"fovModifierHand", "field_78507_R"});
      } catch (Exception var3) {
         LOTRReflection.logFailure(var3);
      }

   }

   public static float getHandFOV(EntityRenderer renderer) {
      try {
         return (Float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, new String[]{"fovModifierHand", "field_78507_R"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0.0F;
      }
   }

   public static float getFOVModifier(EntityRenderer renderer, float tick, boolean flag) {
      try {
         Method method = LOTRReflection.getPrivateMethod(EntityRenderer.class, renderer, new Class[]{Float.TYPE, Boolean.TYPE}, "getFOVModifier", "func_78481_a");
         return (Float)method.invoke(renderer, tick, flag);
      } catch (Exception var4) {
         LOTRReflection.logFailure(var4);
         return 0.0F;
      }
   }

   private static int[] getColorCodes(FontRenderer fontRenderer) {
      if (colorCodes == null) {
         try {
            colorCodes = (int[])ObfuscationReflectionHelper.getPrivateValue(FontRenderer.class, fontRenderer, new String[]{"colorCode", "field_78285_g"});
         } catch (Exception var2) {
            LOTRReflection.logFailure(var2);
         }
      }

      return colorCodes;
   }

   public static int getFormattingColor(EnumChatFormatting ecf) {
      FontRenderer fr = Minecraft.func_71410_x().field_71466_p;
      int colorIndex = ecf.ordinal();
      return getColorCodes(fr)[colorIndex];
   }

   public static void setHighlightedItemTicks(GuiIngame gui, int ticks) {
      try {
         ObfuscationReflectionHelper.setPrivateValue(GuiIngame.class, gui, ticks, new String[]{"remainingHighlightTicks", "field_92017_k"});
      } catch (Exception var3) {
         LOTRReflection.logFailure(var3);
      }

   }

   public static int getHighlightedItemTicks(GuiIngame gui) {
      try {
         return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, gui, new String[]{"remainingHighlightTicks", "field_92017_k"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0;
      }
   }

   public static ItemStack getHighlightedItemStack(GuiIngame gui) {
      try {
         return (ItemStack)ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, gui, new String[]{"highlightingItemStack", "field_92016_l"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return null;
      }
   }

   public static int getGuiLeft(GuiContainer gui) {
      try {
         return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, new String[]{"guiLeft", "field_147003_i"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0;
      }
   }

   public static int getGuiTop(GuiContainer gui) {
      try {
         return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, new String[]{"guiTop", "field_147009_r"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0;
      }
   }

   public static int getGuiXSize(GuiContainer gui) {
      try {
         return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, new String[]{"xSize", "field_146999_f"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0;
      }
   }

   public static boolean hasGuiPotionEffects(InventoryEffectRenderer gui) {
      try {
         return (Boolean)ObfuscationReflectionHelper.getPrivateValue(InventoryEffectRenderer.class, gui, new String[]{"field_147045_u"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return false;
      }
   }

   public static int getCreativeTabIndex(GuiContainerCreative gui) {
      try {
         return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainerCreative.class, gui, new String[]{"selectedTabIndex", "field_147058_w"});
      } catch (Exception var2) {
         LOTRReflection.logFailure(var2);
         return 0;
      }
   }
}
