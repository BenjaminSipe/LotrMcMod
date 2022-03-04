package lotr.common.item;

import lotr.common.LOTRCreativeTabs;

public class LOTRItemRingil extends LOTRItemSword implements LOTRStoryItem {
   public LOTRItemRingil() {
      super(LOTRMaterial.HIGH_ELVEN);
      this.func_77656_e(1500);
      this.lotrWeaponDamage = 9.0F;
      this.setIsElvenBlade();
      this.func_77637_a(LOTRCreativeTabs.tabStory);
   }
}
