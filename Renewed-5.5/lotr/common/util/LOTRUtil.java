package lotr.common.util;

import com.google.common.math.LongMath;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap.Type;

public class LOTRUtil {
   public static Object randInArray(Object[] array, Random rand) {
      return array[rand.nextInt(array.length)];
   }

   public static Object[] combineVarargs(Object[] array1, Object... array2) {
      List combined = new ArrayList();
      combined.addAll(Arrays.asList(array1));
      combined.addAll(Arrays.asList(array2));
      return (Object[])combined.toArray();
   }

   public static int[] combineArrays(int[] array1, int[] array2) {
      int[] result = new int[array1.length + array2.length];

      int i;
      for(i = 0; i < array1.length; ++i) {
         result[i] = array1[i];
      }

      for(i = 0; i < array2.length; ++i) {
         result[i + array1.length] = array2[i];
      }

      return result;
   }

   public static int[] combineArrays(int[] array1, int[] array2, int[] array3) {
      return combineArrays(combineArrays(array1, array2), array3);
   }

   public static Map createKeyedEnumMap(Enum[] values, Function keyGetter) {
      return (Map)Arrays.stream(values).collect(Collectors.toMap(keyGetter, (type) -> {
         return type;
      }));
   }

   public static String toPaddedHexString(int rgb) {
      return String.format("%1$06X", rgb);
   }

   public static float normalisedTriangleWave(float t, float period, float min, float max) {
      float relativeT = Math.abs(t / period % 1.0F);
      return min + (max - min) * -(Math.abs(relativeT - 0.5F) - 0.5F) * 2.0F;
   }

   public static float normalisedSin(float t) {
      return (MathHelper.func_76126_a(t) + 1.0F) / 2.0F;
   }

   public static float normalisedCos(float t) {
      return (MathHelper.func_76134_b(t) + 1.0F) / 2.0F;
   }

   public static float trapezoidalIntensitySinglePulse(float t, float fullDuration, float fadeInAndOutFraction, float min, float max) {
      float fadeOutStart = 1.0F - fadeInAndOutFraction;
      float frac = t / fullDuration;
      frac = MathHelper.func_76131_a(frac, 0.0F, 1.0F);
      float intensity = 1.0F;
      if (frac < fadeInAndOutFraction) {
         intensity = frac / fadeInAndOutFraction;
      } else if (frac > fadeOutStart) {
         intensity = (1.0F - frac) / fadeInAndOutFraction;
      }

      return min + (max - min) * intensity;
   }

   public static int getCoordinateRandomModulo(int x, int y, int z, int mod) {
      long rand = MathHelper.func_180187_c(x, y, z);
      return LongMath.mod(rand, mod);
   }

   public static int secondsToTicks(int s) {
      return s * 20;
   }

   public static int minutesToTicks(int m) {
      return secondsToTicks(m * 60);
   }

   public static ITextComponent getHMSTime_Seconds(int s) {
      return getHMSTime_Ticks(secondsToTicks(s));
   }

   public static ITextComponent getHMSTime_Ticks(int ticks) {
      int hours = ticks / 72000;
      int minutes = ticks % 72000 / 1200;
      int seconds = ticks % 72000 % 1200 / 20;
      ITextComponent sHours = new TranslationTextComponent("gui.lotr.time.hours", new Object[]{hours});
      ITextComponent sMinutes = new TranslationTextComponent("gui.lotr.time.minutes", new Object[]{minutes});
      ITextComponent sSeconds = new TranslationTextComponent("gui.lotr.time.seconds", new Object[]{seconds});
      if (hours > 0) {
         return new TranslationTextComponent("gui.lotr.time.format.hms", new Object[]{sHours, sMinutes, sSeconds});
      } else {
         return minutes > 0 ? new TranslationTextComponent("gui.lotr.time.format.ms", new Object[]{sMinutes, sSeconds}) : new TranslationTextComponent("gui.lotr.time.format.s", new Object[]{sSeconds});
      }
   }

   public static void sendMessage(PlayerEntity player, ITextComponent message) {
      player.func_146105_b(message, false);
   }

   public static Direction getRandomPerpendicular(Direction dir, Random rand) {
      Direction[] perpendiculars = (Direction[])Arrays.stream(Direction.values()).filter((d) -> {
         return d.func_176740_k() != dir.func_176740_k();
      }).toArray((a) -> {
         return new Direction[a];
      });
      return (Direction)randInArray(perpendiculars, rand);
   }

   public static boolean hasSolidSide(IBlockReader world, BlockPos pos, Direction side) {
      BlockState state = world.func_180495_p(pos);
      return Block.func_208061_a(state.func_196951_e(world, pos), side);
   }

   public static void spawnXPOrbs(PlayerEntity player, int count, float xp) {
      if (xp == 0.0F) {
         count = 0;
      } else if (xp < 1.0F) {
         float totalXp = (float)count * xp;
         int floorTotal = MathHelper.func_76141_d(totalXp);
         if (floorTotal < MathHelper.func_76123_f(totalXp) && Math.random() < (double)(totalXp - (float)floorTotal)) {
            ++floorTotal;
         }

         count = floorTotal;
      }

      while(count > 0) {
         int orbXp = ExperienceOrbEntity.func_70527_a(count);
         count -= orbXp;
         player.field_70170_p.func_217376_c(new ExperienceOrbEntity(player.field_70170_p, player.func_226277_ct_(), player.func_226278_cu_() + 0.5D, player.func_226281_cx_() + 0.5D, orbXp));
      }

   }

   public static int calculatePlayersUsingSingleContainer(World world, int x, int y, int z, Class containerClass, Predicate tester) {
      int count = 0;
      float range = 5.0F;
      AxisAlignedBB checkBox = new AxisAlignedBB((double)((float)x - 5.0F), (double)((float)y - 5.0F), (double)((float)z - 5.0F), (double)((float)(x + 1) + 5.0F), (double)((float)(y + 1) + 5.0F), (double)((float)(z + 1) + 5.0F));
      Iterator var9 = world.func_217357_a(PlayerEntity.class, checkBox).iterator();

      while(var9.hasNext()) {
         PlayerEntity player = (PlayerEntity)var9.next();
         Container container = player.field_71070_bA;
         if (container != null && containerClass.isInstance(container) && tester.test(container)) {
            ++count;
         }
      }

      return count;
   }

   public static ItemStack findHeldOrInventoryItem(PlayerEntity player, Predicate test) {
      ItemStack offhandItem = player.func_184586_b(Hand.OFF_HAND);
      if (test.test(offhandItem)) {
         return offhandItem;
      } else {
         ItemStack mainhandItem = player.func_184586_b(Hand.MAIN_HAND);
         if (test.test(mainhandItem)) {
            return mainhandItem;
         } else {
            IInventory playerInv = player.field_71071_by;

            for(int i = 0; i < playerInv.func_70302_i_(); ++i) {
               ItemStack invItem = playerInv.func_70301_a(i);
               if (test.test(invItem)) {
                  return invItem;
               }
            }

            return ItemStack.field_190927_a;
         }
      }
   }

   public static void consumeOneInventoryItem(PlayerEntity player, ItemStack stack) {
      if (!player.field_71075_bZ.field_75098_d) {
         stack.func_190918_g(1);
         if (stack.func_190926_b()) {
            player.field_71071_by.func_184437_d(stack);
         }
      }

   }

   public static int forceLoadChunkAndGetTopBlock(World world, int x, int z) {
      Chunk chunk = world.func_175726_f(new BlockPos(x, 0, z));
      int y = chunk.func_201576_a(Type.MOTION_BLOCKING_NO_LEAVES, x, z) + 1;
      return y;
   }

   public static void unlockFinalField(Field f) {
      try {
         f.setAccessible(true);
         Field modifiersField = Field.class.getDeclaredField("modifiers");
         modifiersField.setAccessible(true);
         modifiersField.setInt(f, (f.getModifiers() & -3 & -5 | 1) & -17);
      } catch (SecurityException | IllegalAccessException | NoSuchFieldException var2) {
         LOTRLog.error("Error unlocking final field " + f.toString());
         var2.printStackTrace();
      }

   }

   public static void unlockMethod(Method m) {
      try {
         m.setAccessible(true);
         Field modifiersField = Method.class.getDeclaredField("modifiers");
         modifiersField.setAccessible(true);
         modifiersField.setInt(m, (m.getModifiers() & -3 & -5 | 1) & -17);
      } catch (SecurityException | IllegalAccessException | NoSuchFieldException var2) {
         LOTRLog.error("Error unlocking final method " + m.toString());
         var2.printStackTrace();
      }

   }

   public static void unlockConstructor(Constructor constr) {
      try {
         Field modifiersField = Constructor.class.getDeclaredField("modifiers");
         modifiersField.setAccessible(true);
         modifiersField.setInt(constr, (constr.getModifiers() & -3 & -5 | 1) & -17);
      } catch (SecurityException | IllegalAccessException | NoSuchFieldException var2) {
         LOTRLog.error("Error unlocking final field " + constr.toString());
         var2.printStackTrace();
      }

   }
}
