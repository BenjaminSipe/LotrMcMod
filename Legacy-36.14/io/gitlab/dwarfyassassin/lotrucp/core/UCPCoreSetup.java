package io.gitlab.dwarfyassassin.lotrucp.core;

import cpw.mods.fml.relauncher.IFMLCallHook;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.BotaniaPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.FMLPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.ScreenshotEnhancedPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.ThaumcraftPatcher;
import java.util.Map;
import org.apache.logging.log4j.LogManager;

public class UCPCoreSetup implements IFMLCallHook {
   public Void call() throws Exception {
      UCPCoreMod.log = LogManager.getLogger("LOTR-UCP");
      UCPCoreMod.registerPatcher(new FMLPatcher());
      UCPCoreMod.registerPatcher(new BotaniaPatcher());
      UCPCoreMod.registerPatcher(new ScreenshotEnhancedPatcher());
      UCPCoreMod.registerPatcher(new ThaumcraftPatcher());
      return null;
   }

   public void injectData(Map data) {
   }
}
