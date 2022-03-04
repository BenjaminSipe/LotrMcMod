package lotr.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTRSpeechClient {
   private static Map npcSpeeches = new HashMap();
   private static int DISPLAY_TIME = 200;

   public static void update() {
      Map newMap = new HashMap();
      Iterator var1 = npcSpeeches.entrySet().iterator();

      while(var1.hasNext()) {
         Entry e = (Entry)var1.next();
         UUID key = (UUID)e.getKey();
         LOTRSpeechClient.TimedSpeech speech = (LOTRSpeechClient.TimedSpeech)e.getValue();
         speech.time--;
         if (speech.time > 0) {
            newMap.put(key, speech);
         }
      }

      npcSpeeches = newMap;
   }

   public static void receiveSpeech(LOTREntityNPC npc, String speech) {
      npcSpeeches.put(npc.func_110124_au(), new LOTRSpeechClient.TimedSpeech(speech, DISPLAY_TIME));
   }

   public static void removeSpeech(LOTREntityNPC npc) {
      npcSpeeches.remove(npc.func_110124_au());
   }

   public static LOTRSpeechClient.TimedSpeech getSpeechFor(LOTREntityNPC npc) {
      UUID key = npc.func_110124_au();
      return npcSpeeches.containsKey(key) ? (LOTRSpeechClient.TimedSpeech)npcSpeeches.get(key) : null;
   }

   public static boolean hasSpeech(LOTREntityNPC npc) {
      return getSpeechFor(npc) != null;
   }

   public static void clearAll() {
      npcSpeeches.clear();
   }

   public static class TimedSpeech {
      private String speech;
      private int time;

      private TimedSpeech(String s, int i) {
         this.speech = s;
         this.time = i;
      }

      public String getSpeech() {
         return this.speech;
      }

      public float getAge() {
         return (float)this.time / (float)LOTRSpeechClient.DISPLAY_TIME;
      }

      // $FF: synthetic method
      TimedSpeech(String x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
