package lotr.common.fac;

public class FactionPair {
   private final Faction fac1;
   private final Faction fac2;

   private FactionPair(Faction f1, Faction f2) {
      this.fac1 = f1;
      this.fac2 = f2;
   }

   public static FactionPair of(Faction f1, Faction f2) {
      return new FactionPair(f1, f2);
   }

   public Faction getFirst() {
      return this.fac1;
   }

   public Faction getSecond() {
      return this.fac2;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         if (obj instanceof FactionPair) {
            FactionPair otherPair = (FactionPair)obj;
            if (this.fac1 == otherPair.fac1 && this.fac2 == otherPair.fac2) {
               return true;
            }

            if (this.fac1 == otherPair.fac2 && this.fac2 == otherPair.fac1) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      int f1 = this.fac1.getAssignedId();
      int f2 = this.fac2.getAssignedId();
      int lower = Math.min(f1, f2);
      int upper = Math.max(f1, f2);
      return upper << 16 | lower;
   }
}
