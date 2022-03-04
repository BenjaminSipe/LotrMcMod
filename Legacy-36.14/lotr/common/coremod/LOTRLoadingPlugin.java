package lotr.common.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@TransformerExclusions({"lotr.common.coremod", "io.gitlab.dwarfyassassin.lotrucp.core"})
@SortingIndex(1001)
@MCVersion("1.7.10")
public class LOTRLoadingPlugin implements IFMLLoadingPlugin {
   private final UCPCoreMod dwarfyAssassinCompatibilityCoremod = new UCPCoreMod();

   public String[] getASMTransformerClass() {
      List classes = new ArrayList();
      classes.add(LOTRClassTransformer.class.getName());
      classes.addAll(Arrays.asList(this.dwarfyAssassinCompatibilityCoremod.getASMTransformerClass()));
      return (String[])classes.toArray(new String[0]);
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return this.dwarfyAssassinCompatibilityCoremod.getSetupClass();
   }

   public void injectData(Map data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }
}
