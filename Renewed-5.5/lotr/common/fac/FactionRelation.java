package lotr.common.fac;

import java.util.Map;
import lotr.common.util.LOTRUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum FactionRelation {
   ALLY("ally"),
   FRIEND("friend"),
   NEUTRAL("neutral"),
   ENEMY("enemy"),
   MORTAL_ENEMY("mortal_enemy");

   private static final Map NAME_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), (relation) -> {
      return relation.codeName;
   });
   private static final Map ID_LOOKUP = LOTRUtil.createKeyedEnumMap(values(), (relation) -> {
      return relation.networkID;
   });
   public final String codeName;
   public final int networkID;

   private FactionRelation(String name) {
      this.codeName = name;
      this.networkID = this.ordinal();
   }

   public ITextComponent getDisplayName() {
      return new TranslationTextComponent(String.format("factionrelation.%s.%s", "lotr", this.codeName));
   }

   public static FactionRelation forName(String name) {
      return (FactionRelation)NAME_LOOKUP.get(name);
   }

   public static FactionRelation forNetworkID(int id) {
      return (FactionRelation)ID_LOOKUP.get(id);
   }
}
