package lotr.common.item;

import lotr.common.LOTRCreativeTabs;

public class LOTRItemGlamdring extends LOTRItemSword implements LOTRStoryItem {
   public LOTRItemGlamdring() {
      super(LOTRMaterial.GONDOLIN);
      this.func_77637_a(LOTRCreativeTabs.tabStory);
      this.setIsElvenBlade();
   }
}
