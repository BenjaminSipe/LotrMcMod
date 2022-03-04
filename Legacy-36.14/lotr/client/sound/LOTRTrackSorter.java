package lotr.client.sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.world.biome.LOTRMusicRegion;

public class LOTRTrackSorter {
   public static List sortTracks(List tracks, LOTRTrackSorter.Filter filter) {
      List sorted = new ArrayList();
      Iterator var3 = tracks.iterator();

      while(var3.hasNext()) {
         LOTRMusicTrack track = (LOTRMusicTrack)var3.next();
         if (filter.accept(track)) {
            sorted.add(track);
         }
      }

      return sorted;
   }

   public static LOTRTrackSorter.Filter forRegionAndCategory(final LOTRMusicRegion reg, final LOTRMusicCategory cat) {
      return new LOTRTrackSorter.Filter() {
         public boolean accept(LOTRMusicTrack track) {
            return track.getRegionInfo(reg).getCategories().contains(cat);
         }
      };
   }

   public static LOTRTrackSorter.Filter forAny() {
      return new LOTRTrackSorter.Filter() {
         public boolean accept(LOTRMusicTrack track) {
            return true;
         }
      };
   }

   public interface Filter {
      boolean accept(LOTRMusicTrack var1);
   }
}
