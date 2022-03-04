package lotr.client.speech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import lotr.common.config.LOTRConfig;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.LivingEntity;

public class ImmersiveSpeech {
   private static final Map speeches = new HashMap();

   public static void update() {
      Set removes = new HashSet();
      speeches.forEach((id, speech) -> {
         speech.time--;
         if (speech.time <= 0) {
            removes.add(id);
         }

      });
      Map var10001 = speeches;
      removes.forEach(var10001::remove);
   }

   private static int getDisplayTime() {
      return LOTRUtil.secondsToTicks((Integer)LOTRConfig.CLIENT.immersiveSpeechDuration.get());
   }

   public static void receiveSpeech(LivingEntity entity, String speech) {
      speeches.put(entity.func_145782_y(), new ImmersiveSpeech.TimedSpeech(speech, getDisplayTime()));
   }

   public static void removeSpeech(LivingEntity entity) {
      removeSpeech(entity.func_145782_y());
   }

   public static void removeSpeech(int entityId) {
      speeches.remove(entityId);
   }

   public static ImmersiveSpeech.TimedSpeech getSpeechFor(LivingEntity entity) {
      return (ImmersiveSpeech.TimedSpeech)speeches.get(entity.func_145782_y());
   }

   public static boolean hasSpeech(LivingEntity entity) {
      return speeches.containsKey(entity.func_145782_y());
   }

   public static void forEach(BiConsumer action) {
      speeches.forEach(action);
   }

   public static void clearAll() {
      speeches.clear();
   }

   public static class TimedSpeech {
      private final String speech;
      private int time;
      private final int maxTime;

      private TimedSpeech(String s, int i) {
         this.speech = s;
         this.time = i;
         this.maxTime = i;
      }

      public String getSpeech() {
         return this.speech;
      }

      public float getAge() {
         return (float)this.time / (float)this.maxTime;
      }

      // $FF: synthetic method
      TimedSpeech(String x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
