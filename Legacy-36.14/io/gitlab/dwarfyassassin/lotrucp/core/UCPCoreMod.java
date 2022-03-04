package io.gitlab.dwarfyassassin.lotrucp.core;

import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class UCPCoreMod {
   public static Logger log;
   public static List activePatches = new ArrayList();
   private static List modPatches = new ArrayList();

   public String[] getASMTransformerClass() {
      return new String[]{UCPClassTransformer.class.getName()};
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return UCPCoreSetup.class.getName();
   }

   public void injectData(Map data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }

   public static void registerPatcher(Patcher patcher) {
      if (patcher.getLoadPhase() == Patcher.LoadingPhase.CORE_MOD_LOADING && patcher.shouldInit()) {
         activePatches.add(patcher);
      } else if (patcher.getLoadPhase() == Patcher.LoadingPhase.FORGE_MOD_LOADING) {
         modPatches.add(patcher);
      }

   }

   public static void loadModPatches() {
      int i = 0;
      Iterator var1 = modPatches.iterator();

      while(var1.hasNext()) {
         Patcher patcher = (Patcher)var1.next();
         if (patcher.shouldInit()) {
            activePatches.add(patcher);
            ++i;
         }
      }

      log.info("Loaded " + i + " mod patches.");
      modPatches.clear();
   }

   static {
      System.out.println("LOTR-UCP: Found core mod.");
   }
}
