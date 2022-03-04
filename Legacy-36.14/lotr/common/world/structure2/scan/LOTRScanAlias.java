package lotr.common.world.structure2.scan;

public class LOTRScanAlias {
   public final String name;
   public final LOTRScanAlias.Type type;

   public LOTRScanAlias(String s, LOTRScanAlias.Type t) {
      this.name = s;
      this.type = t;
   }

   public String getFullCode() {
      char c = this.type.typeCode;
      return c + this.name + c;
   }

   public static enum Type {
      BLOCK('#'),
      BLOCK_META('~');

      public final char typeCode;

      private Type(char c) {
         this.typeCode = c;
      }
   }
}
