package lotr.common;

import net.minecraft.util.StatCollector;

public enum LOTRGuiMessageTypes {
   FRIENDLY_FIRE("friendlyFire"),
   UTUMNO_WARN("utumnoWarn"),
   ENCHANTING("enchanting"),
   ALIGN_DRAIN("alignDrain");

   public final String messageName;

   private LOTRGuiMessageTypes(String s) {
      this.messageName = s;
   }

   public String getMessage() {
      return StatCollector.func_74838_a("lotr.gui.message." + this.messageName);
   }

   public String getSaveName() {
      return this.messageName;
   }

   public static LOTRGuiMessageTypes forSaveName(String name) {
      LOTRGuiMessageTypes[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRGuiMessageTypes message = var1[var3];
         if (message.getSaveName().equals(name)) {
            return message;
         }
      }

      return null;
   }
}
