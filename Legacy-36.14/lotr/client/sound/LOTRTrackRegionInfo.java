package lotr.client.sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRTrackRegionInfo {
   private LOTRMusicRegion region;
   private List subregions = new ArrayList();
   private static final double defaultWeight = 1.0D;
   private double weight;
   private List categories = new ArrayList();

   public LOTRTrackRegionInfo(LOTRMusicRegion r) {
      this.region = r;
      this.weight = 1.0D;
   }

   public List getSubregions() {
      return this.subregions;
   }

   public void addSubregion(String sub) {
      if (!this.subregions.contains(sub)) {
         this.subregions.add(sub);
      }

   }

   public void addAllSubregions() {
      List allSubs = this.region.getAllSubregions();
      if (!allSubs.isEmpty()) {
         Iterator var2 = allSubs.iterator();

         while(var2.hasNext()) {
            String sub = (String)var2.next();
            this.addSubregion(sub);
         }
      }

   }

   public double getWeight() {
      return this.weight;
   }

   public void setWeight(double d) {
      this.weight = d;
   }

   public List getCategories() {
      return this.categories;
   }

   public void addCategory(LOTRMusicCategory cat) {
      if (!this.categories.contains(cat)) {
         this.categories.add(cat);
      }

   }

   public void addAllCategories() {
      LOTRMusicCategory[] var1 = LOTRMusicCategory.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRMusicCategory cat = var1[var3];
         this.addCategory(cat);
      }

   }
}
