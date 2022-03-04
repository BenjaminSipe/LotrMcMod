package lotr.common.world.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.fac.LOTRFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTRConquestZone {
   public final int gridX;
   public final int gridZ;
   public boolean isDummyZone = false;
   private float[] conquestStrengths;
   public static List allPlayableFacs = null;
   private long lastChangeTime;
   private long isEmptyKey = 0L;
   private boolean isLoaded = true;
   private boolean clientSide = false;

   public LOTRConquestZone(int i, int k) {
      this.gridX = i;
      this.gridZ = k;
      if (allPlayableFacs == null) {
         allPlayableFacs = LOTRFaction.getPlayableAlignmentFactions();
         if (allPlayableFacs.size() >= 62) {
            throw new RuntimeException("Too many factions! Need to upgrade LOTRConquestZone data format.");
         }
      }

      this.conquestStrengths = new float[allPlayableFacs.size()];
   }

   public LOTRConquestZone setClientSide() {
      this.clientSide = true;
      return this;
   }

   public LOTRConquestZone setDummyZone() {
      this.isDummyZone = true;
      return this;
   }

   public float getConquestStrength(LOTRFaction fac, World world) {
      return this.getConquestStrength(fac, world.func_82737_E());
   }

   public float getConquestStrength(LOTRFaction fac, long worldTime) {
      float str = this.getConquestStrengthRaw(fac);
      str -= this.calcTimeStrReduction(worldTime);
      str = Math.max(str, 0.0F);
      return str;
   }

   public float getConquestStrengthRaw(LOTRFaction fac) {
      if (!fac.isPlayableAlignmentFaction()) {
         return 0.0F;
      } else {
         int index = allPlayableFacs.indexOf(fac);
         float str = this.conquestStrengths[index];
         return str;
      }
   }

   public void setConquestStrengthRaw(LOTRFaction fac, float str) {
      if (fac.isPlayableAlignmentFaction()) {
         if (str < 0.0F) {
            str = 0.0F;
         }

         int index = allPlayableFacs.indexOf(fac);
         this.conquestStrengths[index] = str;
         if (str == 0.0F) {
            this.isEmptyKey &= ~(1L << index);
         } else {
            this.isEmptyKey |= 1L << index;
         }

         this.markDirty();
      }
   }

   public void setConquestStrength(LOTRFaction fac, float str, World world) {
      this.setConquestStrengthRaw(fac, str);
      this.updateAllOtherFactions(fac, world);
      this.lastChangeTime = world.func_82737_E();
      this.markDirty();
   }

   public void addConquestStrength(LOTRFaction fac, float add, World world) {
      float str = this.getConquestStrength(fac, world);
      str += add;
      this.setConquestStrength(fac, str, world);
   }

   private void updateAllOtherFactions(LOTRFaction fac, World world) {
      for(int i = 0; i < this.conquestStrengths.length; ++i) {
         LOTRFaction otherFac = (LOTRFaction)allPlayableFacs.get(i);
         if (otherFac != fac && this.conquestStrengths[i] > 0.0F) {
            float otherStr = this.getConquestStrength(otherFac, world);
            this.setConquestStrengthRaw(otherFac, otherStr);
         }
      }

   }

   public void checkForEmptiness(World world) {
      boolean emptyCheck = true;
      Iterator var3 = allPlayableFacs.iterator();

      while(var3.hasNext()) {
         LOTRFaction fac = (LOTRFaction)var3.next();
         float str = this.getConquestStrength(fac, world);
         if (str != 0.0F) {
            emptyCheck = false;
            break;
         }
      }

      if (emptyCheck) {
         this.conquestStrengths = new float[allPlayableFacs.size()];
         this.isEmptyKey = 0L;
         this.markDirty();
      }

   }

   public void clearAllFactions(World world) {
      Iterator var2 = allPlayableFacs.iterator();

      while(var2.hasNext()) {
         LOTRFaction fac = (LOTRFaction)var2.next();
         this.setConquestStrengthRaw(fac, 0.0F);
      }

      this.lastChangeTime = world.func_82737_E();
      this.markDirty();
   }

   public long getLastChangeTime() {
      return this.lastChangeTime;
   }

   public void setLastChangeTime(long l) {
      this.lastChangeTime = l;
      this.markDirty();
   }

   private float calcTimeStrReduction(long worldTime) {
      int dl = (int)(worldTime - this.lastChangeTime);
      float s = (float)dl / 20.0F;
      float graceCap = 3600.0F;
      if (s > graceCap) {
         s -= graceCap;
         float decayRate = 3600.0F;
         return s / decayRate;
      } else {
         return 0.0F;
      }
   }

   public boolean isEmpty() {
      return this.isEmptyKey == 0L;
   }

   public void markDirty() {
      if (this.isLoaded && !this.clientSide) {
         LOTRConquestGrid.markZoneDirty(this);
      }

   }

   public String toString() {
      return "LOTRConquestZone: " + this.gridX + ", " + this.gridZ;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      nbt.func_74777_a("X", (short)this.gridX);
      nbt.func_74777_a("Z", (short)this.gridZ);
      nbt.func_74772_a("Time", this.lastChangeTime);

      for(int i = 0; i < this.conquestStrengths.length; ++i) {
         LOTRFaction fac = (LOTRFaction)allPlayableFacs.get(i);
         String facKey = fac.codeName() + "_str";
         float str = this.conquestStrengths[i];
         if (str != 0.0F) {
            nbt.func_74776_a(facKey, str);
         }
      }

   }

   public static LOTRConquestZone readFromNBT(NBTTagCompound nbt) {
      int x = nbt.func_74765_d("X");
      int z = nbt.func_74765_d("Z");
      long time = nbt.func_74763_f("Time");
      LOTRConquestZone zone = new LOTRConquestZone(x, z);
      zone.isLoaded = false;
      zone.lastChangeTime = time;

      for(int i = 0; i < allPlayableFacs.size(); ++i) {
         LOTRFaction fac = (LOTRFaction)allPlayableFacs.get(i);
         List nameAndAliases = new ArrayList();
         nameAndAliases.add(fac.codeName());
         nameAndAliases.addAll(fac.listAliases());
         Iterator var9 = nameAndAliases.iterator();

         while(var9.hasNext()) {
            String alias = (String)var9.next();
            String facKey = alias + "_str";
            if (nbt.func_74764_b(facKey)) {
               float str = nbt.func_74760_g(facKey);
               zone.setConquestStrengthRaw(fac, str);
               break;
            }
         }
      }

      zone.isLoaded = true;
      return zone;
   }
}
