package lotr.common.entity.npc;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRDrunkenSpeech;
import lotr.common.LOTRMod;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCSpeech;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

public class LOTRSpeech {
   private static Map allSpeechBanks = new HashMap();
   private static Random rand = new Random();

   public static void loadAllSpeechBanks() {
      Map speechBankNamesAndReaders = new HashMap();
      ZipFile zip = null;

      try {
         ModContainer mc = LOTRMod.getModContainer();
         if (mc.getSource().isFile()) {
            zip = new ZipFile(mc.getSource());
            Enumeration entries = zip.entries();

            while(entries.hasMoreElements()) {
               ZipEntry entry = (ZipEntry)entries.nextElement();
               String s = entry.getName();
               String path = "assets/lotr/speech/";
               if (s.startsWith(path) && s.endsWith(".txt")) {
                  s = s.substring(path.length());
                  int i = s.indexOf(".txt");

                  try {
                     s = s.substring(0, i);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                     speechBankNamesAndReaders.put(s, reader);
                  } catch (Exception var12) {
                     FMLLog.severe("Failed to load LOTR speech bank " + s + "from zip file", new Object[0]);
                     var12.printStackTrace();
                  }
               }
            }
         } else {
            File speechBankDir = new File(LOTRMod.class.getResource("/assets/lotr/speech").toURI());
            Collection subfiles = FileUtils.listFiles(speechBankDir, (String[])null, true);
            Iterator var20 = subfiles.iterator();

            while(var20.hasNext()) {
               File subfile = (File)var20.next();
               String s = subfile.getPath();
               s = s.substring(speechBankDir.getPath().length() + 1);
               s = s.replace(File.separator, "/");
               int i = s.indexOf(".txt");
               if (i < 0) {
                  FMLLog.severe("Failed to load LOTR speech bank " + s + " from MCP folder; speech bank files must be in .txt format", new Object[0]);
               } else {
                  try {
                     s = s.substring(0, i);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(subfile)), Charsets.UTF_8.name()));
                     speechBankNamesAndReaders.put(s, reader);
                  } catch (Exception var11) {
                     FMLLog.severe("Failed to load LOTR speech bank " + s + " from MCP folder", new Object[0]);
                     var11.printStackTrace();
                  }
               }
            }
         }
      } catch (Exception var14) {
         FMLLog.severe("Failed to load LOTR speech banks", new Object[0]);
         var14.printStackTrace();
      }

      Iterator var15 = speechBankNamesAndReaders.keySet().iterator();

      while(var15.hasNext()) {
         String speechBankName = (String)var15.next();
         BufferedReader reader = (BufferedReader)speechBankNamesAndReaders.get(speechBankName);

         try {
            List speeches = new ArrayList();
            List allLines = new ArrayList();

            boolean random;
            String line;
            for(random = true; (line = reader.readLine()) != null; allLines.add(line)) {
               if (line.equals("!RANDOM")) {
                  random = false;
               } else {
                  speeches.add(line);
               }
            }

            reader.close();
            if (speeches.isEmpty()) {
               FMLLog.severe("LOTR speech bank " + speechBankName + " is empty!", new Object[0]);
            } else {
               LOTRSpeech.SpeechBank bank;
               if (random) {
                  bank = new LOTRSpeech.SpeechBank(speechBankName, random, speeches);
               } else {
                  bank = new LOTRSpeech.SpeechBank(speechBankName, random, allLines);
               }

               allSpeechBanks.put(speechBankName, bank);
            }
         } catch (Exception var13) {
            FMLLog.severe("Failed to load LOTR speech bank " + speechBankName, new Object[0]);
            var13.printStackTrace();
         }
      }

      if (zip != null) {
         try {
            zip.close();
         } catch (IOException var10) {
            var10.printStackTrace();
         }
      }

   }

   private static LOTRSpeech.SpeechBank getSpeechBank(String name) {
      LOTRSpeech.SpeechBank bank = (LOTRSpeech.SpeechBank)allSpeechBanks.get(name);
      return bank != null ? bank : new LOTRSpeech.SpeechBank("dummy_" + name, true, Arrays.asList("Speech bank " + name + " could not be found!"));
   }

   public static String getRandomSpeech(String bankName) {
      return getSpeechBank(bankName).getRandomSpeech(rand);
   }

   public static String getSpeechAtLine(String bankName, int i) {
      return getSpeechBank(bankName).getSpeechAtLine(i);
   }

   public static String getRandomSpeechForPlayer(LOTREntityNPC entity, String speechBankName, EntityPlayer entityplayer) {
      return getRandomSpeechForPlayer(entity, speechBankName, entityplayer, (String)null, (String)null);
   }

   public static String getRandomSpeechForPlayer(LOTREntityNPC entity, String speechBankName, EntityPlayer entityplayer, String location, String objective) {
      String s = getRandomSpeech(speechBankName);
      s = formatSpeech(s, entityplayer, location, objective);
      if (entity.isDrunkard()) {
         float f = entity.getDrunkenSpeechFactor();
         s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
      }

      return s;
   }

   public static String getSpeechLineForPlayer(LOTREntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer) {
      return getSpeechLineForPlayer(entity, speechBankName, i, entityplayer, (String)null, (String)null);
   }

   public static String getSpeechLineForPlayer(LOTREntityNPC entity, String speechBankName, int i, EntityPlayer entityplayer, String location, String objective) {
      String s = getSpeechAtLine(speechBankName, i);
      s = formatSpeech(s, entityplayer, location, objective);
      if (entity.isDrunkard()) {
         float f = entity.getDrunkenSpeechFactor();
         s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
      }

      return s;
   }

   public static String formatSpeech(String speech, EntityPlayer entityplayer, String location, String objective) {
      if (entityplayer != null) {
         speech = speech.replace("#", entityplayer.func_70005_c_());
      }

      if (location != null) {
         speech = speech.replace("@", location);
      }

      if (objective != null) {
         speech = speech.replace("$", objective);
      }

      return speech;
   }

   public static void sendSpeech(EntityPlayer entityplayer, LOTREntityNPC entity, String speech) {
      sendSpeech(entityplayer, entity, speech, false);
   }

   public static void sendSpeech(EntityPlayer entityplayer, LOTREntityNPC entity, String speech, boolean forceChatMsg) {
      LOTRPacketNPCSpeech packet = new LOTRPacketNPCSpeech(entity.func_145782_y(), speech, forceChatMsg);
      LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
   }

   public static void sendSpeechBankWithChatMsg(EntityPlayer entityplayer, LOTREntityNPC entity, String speechBankName) {
      String speech = getRandomSpeechForPlayer(entity, speechBankName, entityplayer, (String)null, (String)null);
      sendSpeech(entityplayer, entity, speech, true);
   }

   public static void messageAllPlayers(IChatComponent message) {
      if (MinecraftServer.func_71276_C() != null) {
         Iterator var1 = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b.iterator();

         while(var1.hasNext()) {
            Object player = var1.next();
            ((EntityPlayer)player).func_145747_a(message);
         }

      }
   }

   public static void messageAllPlayersInWorld(World world, IChatComponent message) {
      Iterator var2 = world.field_73010_i.iterator();

      while(var2.hasNext()) {
         Object player = var2.next();
         ((EntityPlayer)player).func_145747_a(message);
      }

   }

   private static class SpeechBank {
      public final String name;
      public final boolean isRandom;
      public final List speeches;

      public SpeechBank(String s, boolean r, List spc) {
         this.name = s;
         this.isRandom = r;
         this.speeches = spc;
      }

      public String getRandomSpeech(Random random) {
         if (!this.isRandom) {
            return "ERROR: Tried to retrieve random speech from non-random speech bank " + this.name;
         } else {
            String s = (String)this.speeches.get(LOTRSpeech.rand.nextInt(this.speeches.size()));
            s = this.internalFormatSpeech(s);
            return s;
         }
      }

      public String getSpeechAtLine(int line) {
         if (this.isRandom) {
            return "ERROR: Tried to retrieve indexed speech from random speech bank " + this.name;
         } else {
            int index = line - 1;
            if (index >= 0 && index < this.speeches.size()) {
               String s = (String)this.speeches.get(index);
               s = this.internalFormatSpeech(s);
               return s;
            } else {
               return "ERROR: Speech line " + line + " is out of range!";
            }
         }
      }

      private String internalFormatSpeech(String s) {
         if (LOTRMod.isAprilFools() || LOTRSpeech.rand.nextInt(2000) == 0) {
            s = "Tbh, " + s.substring(0, 1).toLowerCase() + s.substring(1, s.length() - 1) + ", tbh.";
         }

         return s;
      }
   }
}
