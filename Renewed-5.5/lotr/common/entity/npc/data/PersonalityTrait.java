package lotr.common.entity.npc.data;

public enum PersonalityTrait {
   PROUD("proud", "modest"),
   BRAVE("brave", "nervous"),
   CALM("calm", "anxious"),
   POLITE("polite", "rough"),
   FRIENDLY("friendly", "rude"),
   STRANGE("strange", "normal"),
   LOUD("loud", "quiet"),
   CLEVER("clever", "stupid"),
   WISE("wise", "insane"),
   EXCITABLE("excitable", "dull");

   private final String mainName;
   private final String oppositeName;
   private final int networkID;

   private PersonalityTrait(String s, String s1) {
      this.mainName = s;
      this.oppositeName = s1;
      this.networkID = this.ordinal();
   }

   public String getMainName() {
      return this.mainName;
   }

   public String getOppositeName() {
      return this.oppositeName;
   }

   public int getNetworkID() {
      return this.networkID;
   }

   public static PersonalityTrait fromMainName(String name) {
      PersonalityTrait[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PersonalityTrait trait = var1[var3];
         if (trait.mainName.equalsIgnoreCase(name)) {
            return trait;
         }
      }

      return null;
   }

   public static PersonalityTrait fromOppositeName(String name) {
      PersonalityTrait[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PersonalityTrait trait = var1[var3];
         if (trait.oppositeName.equalsIgnoreCase(name)) {
            return trait;
         }
      }

      return null;
   }

   public static PersonalityTrait fromNetworkID(int id) {
      PersonalityTrait[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         PersonalityTrait trait = var1[var3];
         if (trait.networkID == id) {
            return trait;
         }
      }

      return null;
   }
}
