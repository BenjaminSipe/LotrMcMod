package lotr.common;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindFieldException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotr.common.util.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockStem;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.Level;

public class LOTRReflection {
   public static void logFailure(Exception e) {
      LOTRLog.logger.log(Level.ERROR, "LOTRReflection failed");
      throw new RuntimeException(e);
   }

   public static String[] remapMethodNames(String className, String... methodNames) {
      String internalClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap(className.replace('.', '/'));
      String[] mappedNames = new String[methodNames.length];
      int i = 0;
      String[] var5 = methodNames;
      int var6 = methodNames.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String mName = var5[var7];
         mappedNames[i++] = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(internalClassName, mName, (String)null);
      }

      return mappedNames;
   }

   public static Method getPrivateMethod(Class classToAccess, Object instance, Class[] methodClasses, String... methodNames) {
      try {
         return ReflectionHelper.findMethod(classToAccess, instance, remapMethodNames(classToAccess.getName(), methodNames), methodClasses);
      } catch (UnableToFindFieldException var5) {
         LOTRLog.logger.log(Level.ERROR, "Unable to locate any method %s on type %s", new Object[]{Arrays.toString(methodNames), classToAccess.getName()});
         throw var5;
      } catch (UnableToAccessFieldException var6) {
         LOTRLog.logger.log(Level.ERROR, "Unable to access any method %s on type %s", new Object[]{Arrays.toString(methodNames), classToAccess.getName()});
         throw var6;
      }
   }

   public static void setFinalField(Class classToAccess, Object instance, Object value, String... fieldNames) throws Exception {
      try {
         fieldNames = ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames);
         Field f = ReflectionHelper.findField(classToAccess, fieldNames);
         setFinalField(classToAccess, instance, value, f);
      } catch (Exception var5) {
         LOTRLog.logger.log(Level.ERROR, "Unable to access static final field");
         throw var5;
      }
   }

   public static void setFinalField(Class classToAccess, Object instance, Object value, Field f) throws Exception {
      try {
         unlockFinalField(f);
         f.set(instance, value);
      } catch (Exception var5) {
         LOTRLog.logger.log(Level.ERROR, "Unable to access static final field");
         throw var5;
      }
   }

   public static void unlockFinalField(Field f) throws Exception {
      f.setAccessible(true);
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(f, f.getModifiers() & -17);
   }

   public static void testAll(World world) {
      getHorseJumpStrength();
      getHorseArmorTextures();
      getHorseInv(new EntityHorse(world));
      setupHorseInv(new EntityHorse(world));
      getStemFruitBlock((BlockStem)Blocks.field_150394_bc);
      getCropItem((BlockCrops)Blocks.field_150469_bN);
      isBadEffect(Potion.field_76436_u);
      getHoverEventMappings();
      isFishHookInGround(new EntityFishHook(world));
      getFishHookBobTime(new EntityFishHook(world));
      canPistonPushBlock(Blocks.field_150359_w, world, 0, 0, 0, false);
   }

   public static void setWorldInfo(World world, WorldInfo newWorldInfo) {
      try {
         ObfuscationReflectionHelper.setPrivateValue(World.class, world, newWorldInfo, new String[]{"worldInfo", "field_72986_A"});
      } catch (Exception var3) {
         logFailure(var3);
      }

   }

   public static IAttribute getHorseJumpStrength() {
      try {
         return (IAttribute)ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, (Object)null, new String[]{"horseJumpStrength", "field_110271_bv"});
      } catch (Exception var1) {
         logFailure(var1);
         return null;
      }
   }

   public static String[] getHorseArmorTextures() {
      try {
         return (String[])ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, (Object)null, new String[]{"horseArmorTextures", "field_110270_bw"});
      } catch (Exception var1) {
         logFailure(var1);
         return null;
      }
   }

   public static AnimalChest getHorseInv(EntityHorse horse) {
      try {
         return (AnimalChest)ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, horse, new String[]{"horseChest", "field_110296_bG"});
      } catch (Exception var2) {
         logFailure(var2);
         return null;
      }
   }

   public static void setupHorseInv(EntityHorse horse) {
      try {
         Method method = getPrivateMethod(EntityHorse.class, horse, new Class[0], "func_110226_cD");
         method.invoke(horse);
      } catch (Exception var2) {
         logFailure(var2);
      }

   }

   public static Block getStemFruitBlock(BlockStem block) {
      try {
         return (Block)ObfuscationReflectionHelper.getPrivateValue(BlockStem.class, block, new String[]{"field_149877_a"});
      } catch (Exception var2) {
         logFailure(var2);
         return null;
      }
   }

   public static Item getCropItem(BlockCrops block) {
      try {
         Method method = getPrivateMethod(BlockCrops.class, block, new Class[0], "func_149865_P");
         return (Item)method.invoke(block);
      } catch (Exception var2) {
         logFailure(var2);
         return null;
      }
   }

   public static boolean isBadEffect(Potion potion) {
      try {
         return (Boolean)ObfuscationReflectionHelper.getPrivateValue(Potion.class, potion, new String[]{"isBadEffect", "field_76418_K"});
      } catch (Exception var2) {
         logFailure(var2);
         return false;
      }
   }

   public static Map getHoverEventMappings() {
      try {
         return (Map)ObfuscationReflectionHelper.getPrivateValue(Action.class, (Object)null, new String[]{"nameMapping", "field_150690_d"});
      } catch (Exception var1) {
         logFailure(var1);
         return null;
      }
   }

   public static void removeCommand(Class commandClass) {
      try {
         CommandHandler handler = (CommandHandler)MinecraftServer.func_71276_C().func_71187_D();
         Map commandMap = handler.func_71555_a();
         Set commandSet = (Set)ObfuscationReflectionHelper.getPrivateValue(CommandHandler.class, handler, new String[]{"commandSet", "field_71561_b"});
         List mapremoves = new ArrayList();
         Iterator var5 = commandMap.values().iterator();

         while(var5.hasNext()) {
            Object obj = var5.next();
            ICommand command = (ICommand)obj;
            if (command.getClass() == commandClass) {
               mapremoves.add(command);
            }
         }

         commandMap.values().removeAll(mapremoves);
         List setremoves = new ArrayList();
         Iterator var10 = commandSet.iterator();

         while(var10.hasNext()) {
            Object obj = var10.next();
            if (obj.getClass() == commandClass) {
               setremoves.add(obj);
            }
         }

         commandSet.removeAll(setremoves);
      } catch (Exception var8) {
         logFailure(var8);
      }

   }

   public static boolean isFishHookInGround(EntityFishHook fishHook) {
      try {
         return (Boolean)ObfuscationReflectionHelper.getPrivateValue(EntityFishHook.class, fishHook, new String[]{"field_146051_au"});
      } catch (Exception var2) {
         logFailure(var2);
         return false;
      }
   }

   public static int getFishHookBobTime(EntityFishHook fishHook) {
      try {
         return (Integer)ObfuscationReflectionHelper.getPrivateValue(EntityFishHook.class, fishHook, new String[]{"field_146045_ax"});
      } catch (Exception var2) {
         logFailure(var2);
         return 0;
      }
   }

   public static boolean canPistonPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
      try {
         Method method = getPrivateMethod(BlockPistonBase.class, (Object)null, new Class[]{Block.class, World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE}, "canPushBlock", "func_150080_a");
         return (Boolean)method.invoke((Object)null, block, world, i, j, k, flag);
      } catch (Exception var7) {
         logFailure(var7);
         return false;
      }
   }
}
