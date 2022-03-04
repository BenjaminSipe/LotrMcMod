package lotr.client;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRMod;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.input.BOMInputStream;

public class LOTRLang {
   public static void runUpdateThread() {
      Thread thread = new Thread("LOTR language update") {
         public void run() {
            LOTRLang.updateTranslations();
         }
      };
      thread.setDaemon(true);
      thread.start();
   }

   private static void updateTranslations() {
      try {
         ModContainer container = LOTRMod.getModContainer();
         File mod = container.getSource();
         if (mod.isFile()) {
            ZipFile zip = new ZipFile(mod);
            ZipEntry en_US = null;
            ZipEntry en_GB = null;
            List langFiles = new ArrayList();
            Enumeration entries = zip.entries();

            while(entries.hasMoreElements()) {
               ZipEntry file = (ZipEntry)entries.nextElement();
               String filename = file.getName();
               if (filename.endsWith(".lang")) {
                  langFiles.add(file);
               }

               if (filename.endsWith("en_US.lang")) {
                  en_US = file;
               }

               if (filename.endsWith("en_GB.lang")) {
                  en_GB = file;
               }
            }

            File newLangFolder = new File(mod.getParentFile(), "LOTR_UpdatedLangFiles");
            File newLang;
            if (newLangFolder.exists()) {
               File[] contents = newLangFolder.listFiles();
               File[] var9 = contents;
               int var10 = contents.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  newLang = var9[var11];
                  newLang.delete();
               }

               newLangFolder.delete();
            }

            newLangFolder.mkdir();
            generateReadmeFile(newLangFolder);
            outputEnUS(newLangFolder, zip, en_US);
            Iterator var26 = langFiles.iterator();

            while(true) {
               ZipEntry entry;
               do {
                  do {
                     if (!var26.hasNext()) {
                        return;
                     }

                     entry = (ZipEntry)var26.next();
                  } while(entry.equals(en_US));
               } while(entry.equals(en_GB));

               String name = FilenameUtils.getName(entry.getName());
               FMLLog.info("Checking LOTR lang file for updates " + name, new Object[0]);
               File oldLang_temp = File.createTempFile(name + "_old", ".lang");
               copyZipEntryToFile(zip, entry, oldLang_temp);
               newLang = new File(newLangFolder, name);
               newLang.createNewFile();
               PrintStream writer = new PrintStream(new FileOutputStream(newLang), true, Charsets.UTF_8.name());
               BufferedReader en_US_reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(en_US)), Charsets.UTF_8.name()));
               String en_US_line = "";

               while(true) {
                  while((en_US_line = en_US_reader.readLine()) != null) {
                     int i1 = en_US_line.indexOf("=");
                     if (i1 < 0) {
                        writer.println(en_US_line);
                     } else {
                        String en_US_key = en_US_line.substring(0, i1);
                        boolean foundKey = false;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(oldLang_temp)), Charsets.UTF_8.name()));
                        String line = "";

                        while((line = reader.readLine()) != null) {
                           int i2 = line.indexOf("=");
                           if (i2 >= 0) {
                              String key = line.substring(0, i2);
                              if (key.equals(en_US_key)) {
                                 foundKey = true;
                                 writer.println(line);
                                 break;
                              }
                           }
                        }

                        reader.close();
                        if (!foundKey) {
                           writer.println(en_US_key + "=");
                        }
                     }
                  }

                  writer.close();
                  en_US_reader.close();
                  oldLang_temp.delete();
                  break;
               }
            }
         }
      } catch (IOException var23) {
         var23.printStackTrace();
      }
   }

   private static void copyZipEntryToFile(ZipFile zip, ZipEntry entry, File copy) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
      PrintStream writer = new PrintStream(new FileOutputStream(copy), true, Charsets.UTF_8.name());
      String line = "";

      while((line = reader.readLine()) != null) {
         writer.println(line);
      }

      reader.close();
      writer.close();
   }

   private static void generateReadmeFile(File folder) throws IOException {
      File readme = new File(folder, "readme.txt");
      readme.createNewFile();
      PrintStream writer = new PrintStream(new FileOutputStream(readme));
      writer.println("LOTR lang file update-helper");
      writer.println();
      writer.println("This helper system is to assist people in updating the mod's lang files after a mod update.");
      writer.println("To enable the helper, go to the mod's config file - or ingame config menu - and set 'Run language update helper' to 'true'.");
      writer.println();
      writer.println("When the mod is loaded, it checks all lang files in the mod zip file against en_US.lang, and outputs a copy of them here.");
      writer.println("If a lang file is missing any keys (names of new blocks, items, etc. added in an update) then those keys are added to the copy here.");
      writer.println("Unused keys are also removed - for example, if a feature is removed from the mod, or a key is renamed.");
      writer.println("The lang files outputted here also have their contents ordered in the same order as en_US.lang, to make comparisons easier.");
      writer.println();
      writer.println("The mod's current en_US.lang is also outputted here for convenience.");
      writer.println();
      writer.println("I hope this system will be much easier than checking a lang file against en_US.lang, for every update, to find out what has been added.");
      writer.println();
      writer.println("DO NOT STORE ANYTHING in this folder! This folder, and its contents, are re-created every time the mod loads.");
      writer.println("Anything in the folder will be deleted.");
      writer.println("If you want to update one of the lang files, copy and paste it somewhere safe!");
      writer.println();
      writer.println("And finally, if you have updated a lang file (or created a new one), the best way to send it to us is through the mod's Facebook page.");
      writer.println("We credit everyone by name in the mod's credits file unless asked not to. If you do not want your name listed, then please say so.");
      writer.println();
      writer.println("Please note: Lang files must be in UTF-8 format, otherwise errors will occur.");
      writer.close();
   }

   private static void outputEnUS(File folder, ZipFile zip, ZipEntry entry) throws IOException {
      String name = FilenameUtils.getName(entry.getName());
      File output = new File(folder, name);
      copyZipEntryToFile(zip, entry, output);
   }
}
