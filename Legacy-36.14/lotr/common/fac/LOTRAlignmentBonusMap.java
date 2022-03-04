package lotr.common.fac;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LOTRAlignmentBonusMap extends HashMap {
   public Set getChangedFactions() {
      Set changed = new HashSet();
      Iterator var2 = this.keySet().iterator();

      while(var2.hasNext()) {
         LOTRFaction fac = (LOTRFaction)var2.next();
         float bonus = (Float)this.get(fac);
         if (bonus != 0.0F) {
            changed.add(fac);
         }
      }

      return changed;
   }
}
