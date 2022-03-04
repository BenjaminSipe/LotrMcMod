package lotr.client.sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRRegionTrackPool {
   private final LOTRMusicRegion region;
   private final String subregion;
   private List trackList = new ArrayList();

   public LOTRRegionTrackPool(LOTRMusicRegion r, String s) {
      this.region = r;
      this.subregion = s;
   }

   public void addTrack(LOTRMusicTrack track) {
      this.trackList.add(track);
   }

   public boolean isEmpty() {
      return this.trackList.isEmpty();
   }

   public LOTRMusicTrack getRandomTrack(Random rand, LOTRTrackSorter.Filter filter) {
      List sortedTracks = LOTRTrackSorter.sortTracks(this.trackList, filter);
      double totalWeight = 0.0D;

      double weight;
      for(Iterator var6 = sortedTracks.iterator(); var6.hasNext(); totalWeight += weight) {
         LOTRMusicTrack track = (LOTRMusicTrack)var6.next();
         weight = track.getRegionInfo(this.region).getWeight();
      }

      double randWeight = rand.nextDouble();
      randWeight *= totalWeight;
      Iterator it = sortedTracks.iterator();
      LOTRMusicTrack track = null;

      while(it.hasNext()) {
         track = (LOTRMusicTrack)it.next();
         double weight = track.getRegionInfo(this.region).getWeight();
         randWeight -= weight;
         if (randWeight < 0.0D) {
            return track;
         }
      }

      return track;
   }
}
