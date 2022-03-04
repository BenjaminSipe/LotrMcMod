package lotr.client.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotr.common.world.biome.LOTRMusicRegion;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.client.audio.SoundList.SoundEntry;
import net.minecraft.client.audio.SoundList.SoundEntry.Type;
import net.minecraft.util.ResourceLocation;

public class LOTRMusicTrack extends PositionedSound {
   private final String filename;
   private String title;
   private Map regions = new HashMap();
   private List authors = new ArrayList();

   public LOTRMusicTrack(String s) {
      super(getMusicResource(s));
      this.field_147662_b = 1.0F;
      this.field_147663_c = 1.0F;
      this.field_147660_d = 0.0F;
      this.field_147661_e = 0.0F;
      this.field_147658_f = 0.0F;
      this.field_147659_g = false;
      this.field_147665_h = 0;
      this.field_147666_i = AttenuationType.NONE;
      this.filename = s;
   }

   private static ResourceLocation getMusicResource(String s) {
      ResourceLocation res = new ResourceLocation("lotrmusic", s);
      return res;
   }

   public void loadTrack(InputStream in) throws IOException {
      this.loadSoundResource();
      LOTRMusic.addTrackToRegions(this);
   }

   private void loadSoundResource() {
      ResourceLocation resource = this.func_147650_b();
      SoundList soundList = new SoundList();
      soundList.func_148572_a(true);
      soundList.func_148571_a(SoundCategory.MUSIC);
      SoundEntry soundEntry = new SoundEntry();
      soundEntry.func_148561_a(this.filename);
      soundEntry.func_148553_a(this.func_147653_e());
      soundEntry.func_148559_b(this.func_147655_f());
      soundEntry.func_148554_a(1);
      soundEntry.func_148562_a(Type.SOUND_EVENT);
      soundEntry.func_148557_a(true);
      soundList.func_148570_a().add(soundEntry);
      SoundRegistry sndRegistry = LOTRMusic.Reflect.getSoundRegistry();
      SoundEventAccessorComposite soundAccessorComp;
      if (sndRegistry.func_148741_d(resource) && !soundList.func_148574_b()) {
         soundAccessorComp = (SoundEventAccessorComposite)sndRegistry.func_82594_a(resource);
      } else {
         soundAccessorComp = new SoundEventAccessorComposite(resource, 1.0D, 1.0D, soundList.func_148573_c());
         sndRegistry.func_148762_a(soundAccessorComp);
      }

      SoundPoolEntry soundPoolEntry = new SoundPoolEntry(resource, (double)soundEntry.func_148560_c(), (double)soundEntry.func_148558_b(), soundEntry.func_148552_f());
      ISoundEventAccessor soundAccessor = new LOTRMusicTrack.TrackSoundAccessor(soundPoolEntry, soundEntry.func_148555_d());
      soundAccessorComp.func_148727_a(soundAccessor);
   }

   public String getFilename() {
      return this.filename;
   }

   public String getTitle() {
      return this.title != null ? this.title : this.filename;
   }

   public void setTitle(String s) {
      this.title = s;
   }

   public Set getAllRegions() {
      return this.regions.keySet();
   }

   public LOTRTrackRegionInfo createRegionInfo(LOTRMusicRegion reg) {
      LOTRTrackRegionInfo info = (LOTRTrackRegionInfo)this.regions.get(reg);
      if (info == null) {
         info = new LOTRTrackRegionInfo(reg);
         this.regions.put(reg, info);
      }

      return info;
   }

   public LOTRTrackRegionInfo getRegionInfo(LOTRMusicRegion reg) {
      return this.regions.containsKey(reg) ? (LOTRTrackRegionInfo)this.regions.get(reg) : null;
   }

   public void addAuthor(String s) {
      this.authors.add(s);
   }

   public List getAuthors() {
      return this.authors;
   }

   public String[] getTrackInfo() {
      List list = new ArrayList();
      list.add("Title: " + this.getTitle());
      list.add("Filename: " + this.getFilename());
      list.add("Regions:");
      Iterator var2 = this.getAllRegions().iterator();

      while(true) {
         List categories;
         do {
            if (!var2.hasNext()) {
               list.add("Authors:");
               var2 = this.getAuthors().iterator();

               while(var2.hasNext()) {
                  String auth = (String)var2.next();
                  list.add(">" + auth);
               }

               return (String[])list.toArray(new String[0]);
            }

            LOTRMusicRegion reg = (LOTRMusicRegion)var2.next();
            LOTRTrackRegionInfo info = this.getRegionInfo(reg);
            list.add(">" + reg.regionName);
            list.add(">Weight: " + info.getWeight());
            List subs = info.getSubregions();
            if (!subs.isEmpty()) {
               list.add(">Subregions:");
               Iterator var6 = subs.iterator();

               while(var6.hasNext()) {
                  String s = (String)var6.next();
                  list.add(">>" + s);
               }
            }

            categories = info.getCategories();
         } while(categories.isEmpty());

         list.add(">Categories:");
         Iterator var11 = categories.iterator();

         while(var11.hasNext()) {
            LOTRMusicCategory cat = (LOTRMusicCategory)var11.next();
            list.add(">>" + cat.categoryName);
         }
      }
   }

   private static class TrackSoundAccessor implements ISoundEventAccessor {
      private final SoundPoolEntry soundEntry;
      private final int weight;

      private TrackSoundAccessor(SoundPoolEntry e, int i) {
         this.soundEntry = e;
         this.weight = i;
      }

      public int func_148721_a() {
         return this.weight;
      }

      public SoundPoolEntry func_148720_g() {
         return new SoundPoolEntry(this.soundEntry);
      }

      // $FF: synthetic method
      TrackSoundAccessor(SoundPoolEntry x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
