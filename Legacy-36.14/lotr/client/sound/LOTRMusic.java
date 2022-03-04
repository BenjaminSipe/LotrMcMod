package lotr.client.sound;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRDimension;
import lotr.common.LOTRReflection;
import lotr.common.util.LOTRLog;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRMusicRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.input.BOMInputStream;

public class LOTRMusic implements IResourceManagerReloadListener {
   private static File musicDir;
   private static final String jsonFilename = "music.json";
   public static final String musicResourcePath = "lotrmusic";
   public static final LOTRMusicResourceManager trackResourceManager = new LOTRMusicResourceManager();
   private static List allTracks = new ArrayList();
   private static Map regionTracks = new HashMap();
   private static boolean initSubregions = false;
   private static Random musicRand = new Random();

   public LOTRMusic() {
      ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a(this);
      MinecraftForge.EVENT_BUS.register(this);
   }

   public void func_110549_a(IResourceManager resourcemanager) {
      loadMusicPacks(Minecraft.func_71410_x().field_71412_D, (SimpleReloadableResourceManager)resourcemanager);
   }

   public void update() {
      LOTRMusicTicker.update(musicRand);
   }

   @SubscribeEvent
   public void onPlaySound(PlaySoundEvent17 event) {
      Minecraft mc = Minecraft.func_71410_x();
      if (!allTracks.isEmpty() && event.category == SoundCategory.MUSIC && !(event.sound instanceof LOTRMusicTrack)) {
         if (isLOTRDimension()) {
            event.result = null;
            return;
         }

         if (isMenuMusic() && !getTracksForRegion(LOTRMusicRegion.MENU, (String)null).isEmpty()) {
            event.result = null;
            return;
         }
      }

   }

   public static boolean isLOTRDimension() {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      EntityPlayer entityplayer = mc.field_71439_g;
      return entityplayer != null && world != null && world.field_73011_w instanceof LOTRWorldProvider;
   }

   public static boolean isMenuMusic() {
      Minecraft mc = Minecraft.func_71410_x();
      return mc.func_147109_W() == MusicType.MENU;
   }

   public static LOTRRegionTrackPool getTracksForRegion(LOTRMusicRegion region, String sub) {
      if (region.hasSubregion(sub) || region.hasNoSubregions() && sub == null) {
         LOTRMusicRegion.Sub key = region.getSubregion(sub);
         LOTRRegionTrackPool regionPool = (LOTRRegionTrackPool)regionTracks.get(key);
         if (regionPool == null) {
            regionPool = new LOTRRegionTrackPool(region, sub);
            regionTracks.put(key, regionPool);
         }

         return regionPool;
      } else {
         LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
         return null;
      }
   }

   public static void addTrackToRegions(LOTRMusicTrack track) {
      allTracks.add(track);
      Iterator var1 = track.getAllRegions().iterator();

      while(true) {
         while(var1.hasNext()) {
            LOTRMusicRegion region = (LOTRMusicRegion)var1.next();
            if (region.hasNoSubregions()) {
               getTracksForRegion(region, (String)null).addTrack(track);
            } else {
               Iterator var3 = track.getRegionInfo(region).getSubregions().iterator();

               while(var3.hasNext()) {
                  String sub = (String)var3.next();
                  getTracksForRegion(region, sub).addTrack(track);
               }
            }
         }

         return;
      }
   }

   private static void loadMusicPacks(File mcDir, SimpleReloadableResourceManager resourceMgr) {
      musicDir = new File(mcDir, "lotrmusic");
      if (!musicDir.exists()) {
         musicDir.mkdirs();
      }

      allTracks.clear();
      regionTracks.clear();
      int var3;
      int var4;
      if (!initSubregions) {
         LOTRDimension[] var2 = LOTRDimension.values();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            LOTRDimension dim = var2[var4];
            LOTRBiome[] var6 = dim.biomeList;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               LOTRBiome biome = var6[var8];
               if (biome != null) {
                  biome.getBiomeMusic();
               }
            }
         }

         initSubregions = true;
      }

      File[] var12 = musicDir.listFiles();
      var3 = var12.length;

      for(var4 = 0; var4 < var3; ++var4) {
         File file = var12[var4];
         if (file.isFile() && file.getName().endsWith(".zip")) {
            try {
               AbstractResourcePack resourcePack = new FileResourcePack(file);
               resourceMgr.func_110545_a(resourcePack);
               ZipFile zipFile = new ZipFile(file);
               loadMusicPack(zipFile, resourceMgr);
            } catch (Exception var11) {
               LOTRLog.logger.warn("LOTRMusic: Failed to load music pack " + file.getName() + "!");
               var11.printStackTrace();
            }
         }
      }

      try {
         generateReadme();
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }

   private static void loadMusicPack(ZipFile zip, SimpleReloadableResourceManager resourceMgr) throws IOException {
      ZipEntry entry = zip.getEntry("music.json");
      if (entry != null) {
         InputStream stream = zip.getInputStream(entry);
         JsonReader reader = new JsonReader(new InputStreamReader(new BOMInputStream(stream), Charsets.UTF_8.name()));
         JsonParser parser = new JsonParser();
         List packTracks = new ArrayList();
         JsonObject root = parser.parse(reader).getAsJsonObject();
         JsonArray rootArray = root.get("tracks").getAsJsonArray();
         Iterator var9 = rootArray.iterator();

         while(true) {
            while(var9.hasNext()) {
               JsonElement e = (JsonElement)var9.next();
               JsonObject trackData = e.getAsJsonObject();
               String filename = trackData.get("file").getAsString();
               ZipEntry trackEntry = zip.getEntry("assets/lotrmusic/" + filename);
               if (trackEntry == null) {
                  LOTRLog.logger.warn("LOTRMusic: Track " + filename + " in pack " + zip.getName() + " does not exist!");
               } else {
                  InputStream trackStream = zip.getInputStream(trackEntry);
                  LOTRMusicTrack track = new LOTRMusicTrack(filename);
                  if (trackData.has("title")) {
                     String title = trackData.get("title").getAsString();
                     track.setTitle(title);
                  }

                  JsonArray regions = trackData.get("regions").getAsJsonArray();
                  Iterator var17 = regions.iterator();

                  while(true) {
                     String author;
                     label127:
                     while(var17.hasNext()) {
                        JsonElement r = (JsonElement)var17.next();
                        JsonObject regionData = r.getAsJsonObject();
                        author = regionData.get("name").getAsString();
                        boolean allRegions = false;
                        LOTRMusicRegion region;
                        if (author.equalsIgnoreCase("all")) {
                           region = null;
                           allRegions = true;
                        } else {
                           region = LOTRMusicRegion.forName(author);
                           if (region == null) {
                              LOTRLog.logger.warn("LOTRMusic: No region named " + author + "!");
                              continue;
                           }
                        }

                        List subregionNames = new ArrayList();
                        if (region != null && regionData.has("sub")) {
                           JsonArray subList = regionData.get("sub").getAsJsonArray();
                           Iterator var25 = subList.iterator();

                           while(var25.hasNext()) {
                              JsonElement s = (JsonElement)var25.next();
                              String sub = s.getAsString();
                              if (region.hasSubregion(sub)) {
                                 subregionNames.add(sub);
                              } else {
                                 LOTRLog.logger.warn("LOTRMusic: No subregion " + sub + " for region " + region.regionName + "!");
                              }
                           }
                        }

                        List regionCategories = new ArrayList();
                        if (region != null && regionData.has("categories")) {
                           JsonArray catList = regionData.get("categories").getAsJsonArray();
                           Iterator var40 = catList.iterator();

                           while(var40.hasNext()) {
                              JsonElement cat = (JsonElement)var40.next();
                              String categoryName = cat.getAsString();
                              LOTRMusicCategory category = LOTRMusicCategory.forName(categoryName);
                              if (category != null) {
                                 regionCategories.add(category);
                              } else {
                                 LOTRLog.logger.warn("LOTRMusic: No category named " + categoryName + "!");
                              }
                           }
                        }

                        double weight = -1.0D;
                        if (regionData.has("weight")) {
                           weight = regionData.get("weight").getAsDouble();
                        }

                        List regionsAdd = new ArrayList();
                        if (allRegions) {
                           regionsAdd.addAll(Arrays.asList(LOTRMusicRegion.values()));
                        } else {
                           regionsAdd.add(region);
                        }

                        Iterator var43 = regionsAdd.iterator();

                        while(true) {
                           while(true) {
                              if (!var43.hasNext()) {
                                 continue label127;
                              }

                              LOTRMusicRegion reg = (LOTRMusicRegion)var43.next();
                              LOTRTrackRegionInfo regInfo = track.createRegionInfo(reg);
                              if (weight >= 0.0D) {
                                 regInfo.setWeight(weight);
                              }

                              Iterator var31;
                              if (subregionNames.isEmpty()) {
                                 regInfo.addAllSubregions();
                              } else {
                                 var31 = subregionNames.iterator();

                                 while(var31.hasNext()) {
                                    String sub = (String)var31.next();
                                    regInfo.addSubregion(sub);
                                 }
                              }

                              if (regionCategories.isEmpty()) {
                                 regInfo.addAllCategories();
                              } else {
                                 var31 = regionCategories.iterator();

                                 while(var31.hasNext()) {
                                    LOTRMusicCategory cat = (LOTRMusicCategory)var31.next();
                                    regInfo.addCategory(cat);
                                 }
                              }
                           }
                        }
                     }

                     if (trackData.has("authors")) {
                        JsonArray authorList = trackData.get("authors").getAsJsonArray();
                        Iterator var35 = authorList.iterator();

                        while(var35.hasNext()) {
                           JsonElement a = (JsonElement)var35.next();
                           author = a.getAsString();
                           track.addAuthor(author);
                        }
                     }

                     track.loadTrack(trackStream);
                     packTracks.add(track);
                     break;
                  }
               }
            }

            reader.close();
            LOTRLog.logger.info("LOTRMusic: Successfully loaded music pack " + zip.getName() + " with " + packTracks.size() + " tracks");
            break;
         }
      }

   }

   private static void generateReadme() throws IOException {
      File readme = new File(musicDir, "readme.txt");
      readme.createNewFile();
      PrintStream writer = new PrintStream(new FileOutputStream(readme));
      ResourceLocation template = new ResourceLocation("lotr:music/readme.txt");
      InputStream templateIn = Minecraft.func_71410_x().func_110442_L().func_110536_a(template).func_110527_b();
      BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(templateIn), Charsets.UTF_8.name()));
      String line = "";

      while(true) {
         while((line = reader.readLine()) != null) {
            int var7;
            int var8;
            String regionString;
            if (line.equals("#REGIONS#")) {
               writer.println("all");
               LOTRMusicRegion[] var15 = LOTRMusicRegion.values();
               var7 = var15.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  LOTRMusicRegion region = var15[var8];
                  regionString = "";
                  regionString = regionString + region.regionName;
                  List subregions = region.getAllSubregions();
                  if (!subregions.isEmpty()) {
                     String subs = "";

                     String s;
                     for(Iterator var13 = subregions.iterator(); var13.hasNext(); subs = subs + s) {
                        s = (String)var13.next();
                        if (subs.length() > 0) {
                           subs = subs + ", ";
                        }
                     }

                     regionString = regionString + ": {" + subs + "}";
                  }

                  writer.println(regionString);
               }
            } else if (line.equals("#CATEGORIES#")) {
               LOTRMusicCategory[] var6 = LOTRMusicCategory.values();
               var7 = var6.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  LOTRMusicCategory category = var6[var8];
                  regionString = category.categoryName;
                  writer.println(regionString);
               }
            } else {
               writer.println(line);
            }
         }

         writer.close();
         reader.close();
         return;
      }
   }

   public static class Reflect {
      public static void putDomainResourceManager(String domain, IResourceManager manager) {
         SimpleReloadableResourceManager masterManager = (SimpleReloadableResourceManager)Minecraft.func_71410_x().func_110442_L();

         try {
            Map map = (Map)ObfuscationReflectionHelper.getPrivateValue(SimpleReloadableResourceManager.class, masterManager, new String[]{"domainResourceManagers", "field_110548_a"});
            map.put(domain, manager);
         } catch (Exception var4) {
            LOTRReflection.logFailure(var4);
         }

      }

      public static SoundRegistry getSoundRegistry() {
         SoundHandler handler = Minecraft.func_71410_x().func_147118_V();

         try {
            return (SoundRegistry)ObfuscationReflectionHelper.getPrivateValue(SoundHandler.class, handler, new String[]{"sndRegistry", "field_147697_e"});
         } catch (Exception var2) {
            LOTRReflection.logFailure(var2);
            return null;
         }
      }
   }
}
