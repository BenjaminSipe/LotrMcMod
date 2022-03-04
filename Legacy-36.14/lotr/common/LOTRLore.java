package lotr.common;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.ModContainer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.util.LOTRLog;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.MathHelper;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;

public class LOTRLore {
   private static final String newline = "\n";
   private static final String codeMetadata = "#";
   private static final String codeTitle = "title:";
   private static final String codeAuthor = "author:";
   private static final String codeCategory = "types:";
   private static final String codeCategorySeparator = ",";
   private static final String codeReward = "reward";
   public final String loreName;
   public final String loreTitle;
   public final String loreAuthor;
   public final String loreText;
   public final List loreCategories;
   public final boolean isRewardable;

   public static LOTRLore getMultiRandomLore(List categories, Random random, boolean rewardsOnly) {
      List allLore = new ArrayList();
      Iterator var4 = categories.iterator();

      label36:
      while(var4.hasNext()) {
         LOTRLore.LoreCategory c = (LOTRLore.LoreCategory)var4.next();
         Iterator var6 = c.loreList.iterator();

         while(true) {
            LOTRLore lore;
            do {
               do {
                  if (!var6.hasNext()) {
                     continue label36;
                  }

                  lore = (LOTRLore)var6.next();
               } while(allLore.contains(lore));
            } while(rewardsOnly && !lore.isRewardable);

            allLore.add(lore);
         }
      }

      if (!allLore.isEmpty()) {
         return (LOTRLore)allLore.get(random.nextInt(allLore.size()));
      } else {
         return null;
      }
   }

   public static void loadAllLore() {
      Map loreReaders = new HashMap();
      ZipFile zip = null;

      String title;
      int numReward;
      int num;
      try {
         ModContainer mc = LOTRMod.getModContainer();
         if (mc.getSource().isFile()) {
            zip = new ZipFile(mc.getSource());
            Enumeration entries = zip.entries();

            while(entries.hasMoreElements()) {
               ZipEntry entry = (ZipEntry)entries.nextElement();
               String s = entry.getName();
               title = "assets/lotr/lore/";
               if (s.startsWith(title) && s.endsWith(".txt")) {
                  s = s.substring(title.length());
                  numReward = s.indexOf(".txt");

                  try {
                     s = s.substring(0, numReward);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                     loreReaders.put(s, reader);
                  } catch (Exception var22) {
                     LOTRLog.logger.error("Failed to load LOTR lore " + s + "from zip file");
                     var22.printStackTrace();
                  }
               }
            }
         } else {
            File nameBankDir = new File(LOTRMod.class.getResource("/assets/lotr/lore").toURI());
            File[] var30 = nameBankDir.listFiles();
            int var33 = var30.length;

            for(num = 0; num < var33; ++num) {
               File file = var30[num];
               String s = file.getName();
               int i = s.indexOf(".txt");
               if (i < 0) {
                  LOTRLog.logger.error("Failed to load LOTR lore " + s + " from MCP folder; name bank files must be in .txt format");
               } else {
                  try {
                     s = s.substring(0, i);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file)), Charsets.UTF_8.name()));
                     loreReaders.put(s, reader);
                  } catch (Exception var21) {
                     LOTRLog.logger.error("Failed to load LOTR lore " + s + " from MCP folder");
                     var21.printStackTrace();
                  }
               }
            }
         }
      } catch (Exception var24) {
         LOTRLog.logger.error("Failed to load LOTR lore");
         var24.printStackTrace();
      }

      Iterator var25 = loreReaders.entrySet().iterator();

      label158:
      while(var25.hasNext()) {
         Entry entry = (Entry)var25.next();
         String loreName = (String)entry.getKey();
         BufferedReader reader = (BufferedReader)entry.getValue();

         try {
            title = "";
            String author = "";
            List categories = new ArrayList();
            String text = "";
            boolean reward = false;

            while(true) {
               while(true) {
                  String line;
                  label124:
                  while((line = reader.readLine()) != null) {
                     if (line.startsWith("#")) {
                        String metadata = line.substring("#".length());
                        if (metadata.startsWith("title:")) {
                           title = metadata.substring("title:".length());
                        } else if (metadata.startsWith("author:")) {
                           author = metadata.substring("author:".length());
                        } else if (!metadata.startsWith("types:")) {
                           if (metadata.startsWith("reward")) {
                              reward = true;
                           }
                        } else {
                           String categoryString = metadata.substring("types:".length());

                           while(true) {
                              while(true) {
                                 String categoryName;
                                 do {
                                    if (categoryString.length() <= 0) {
                                       continue label124;
                                    }

                                    categoryName = null;
                                    int indexOf = categoryString.indexOf(",");
                                    if (indexOf >= 0) {
                                       categoryName = categoryString.substring(0, indexOf);
                                       categoryString = categoryString.substring(indexOf + 1);
                                    } else {
                                       categoryName = categoryString;
                                       categoryString = "";
                                    }
                                 } while(categoryName == null);

                                 if (categoryName.equals("all")) {
                                    LOTRLore.LoreCategory[] var48 = LOTRLore.LoreCategory.values();
                                    int var17 = var48.length;

                                    for(int var18 = 0; var18 < var17; ++var18) {
                                       LOTRLore.LoreCategory category = var48[var18];
                                       if (!categories.contains(category)) {
                                          categories.add(category);
                                       }
                                    }
                                 } else {
                                    LOTRLore.LoreCategory category = LOTRLore.LoreCategory.forName(categoryName);
                                    if (category != null) {
                                       if (!categories.contains(category)) {
                                          categories.add(category);
                                       }
                                    } else {
                                       LOTRLog.logger.warn("LOTRLore: Loading lore " + loreName + ", no category exists for name " + categoryName);
                                    }
                                 }
                              }
                           }
                        }
                     } else {
                        text = text + line;
                        text = text + "\n";
                     }
                  }

                  reader.close();
                  LOTRLore lore = new LOTRLore(loreName, title, author, text, categories, reward);
                  Iterator var46 = categories.iterator();

                  while(true) {
                     if (!var46.hasNext()) {
                        continue label158;
                     }

                     LOTRLore.LoreCategory category = (LOTRLore.LoreCategory)var46.next();
                     category.addLore(lore);
                  }
               }
            }
         } catch (Exception var23) {
            LOTRLog.logger.error("Failed to load LOTR lore: " + loreName);
            var23.printStackTrace();
         }
      }

      LOTRLore.LoreCategory[] var26 = LOTRLore.LoreCategory.values();
      int var29 = var26.length;

      for(int var32 = 0; var32 < var29; ++var32) {
         LOTRLore.LoreCategory category = var26[var32];
         num = category.loreList.size();
         numReward = 0;
         Iterator var42 = category.loreList.iterator();

         while(var42.hasNext()) {
            LOTRLore lore = (LOTRLore)var42.next();
            if (lore.isRewardable) {
               ++numReward;
            }
         }

         LOTRLog.logger.info("LOTRLore: Category " + category.categoryName + " has loaded " + num + " lore texts, of which " + numReward + " rewardable");
      }

      if (zip != null) {
         try {
            zip.close();
         } catch (IOException var20) {
            var20.printStackTrace();
         }
      }

   }

   public LOTRLore(String name, String title, String auth, String text, List categories, boolean reward) {
      this.loreName = name;
      this.loreTitle = title;
      this.loreAuthor = auth;
      this.loreText = text;
      this.loreCategories = categories;
      this.isRewardable = reward;
   }

   private static List organisePages(String loreText) {
      List loreTextPages = new ArrayList();
      String remainingText = loreText;
      ArrayList splitTxtWords = new ArrayList();

      while(true) {
         int numLines;
         String currentLine;
         while(remainingText.length() > 0) {
            String part;
            if (remainingText.startsWith("\n")) {
               part = "\n";
               if (!splitTxtWords.isEmpty()) {
                  splitTxtWords.add(part);
               }

               remainingText = remainingText.substring(part.length());
            } else {
               part = "";
               int indexOf = remainingText.indexOf("\n");
               if (indexOf >= 0) {
                  part = remainingText.substring(0, indexOf);
               } else {
                  part = remainingText;
               }

               String[] splitWords = StringUtils.split(part, " ");
               String[] var7 = splitWords;
               int var8 = splitWords.length;

               for(numLines = 0; numLines < var8; ++numLines) {
                  currentLine = var7[numLines];
                  splitTxtWords.add(currentLine);
               }

               remainingText = remainingText.substring(part.length());
            }
         }

         int pageLengthMax = true;
         int maxLines = true;
         int avgLineLength = true;

         for(int var17 = 0; !splitTxtWords.isEmpty(); ++var17) {
            String pageText = "";
            numLines = 0;
            currentLine = "";
            int usedWords = 0;

            int i;
            for(i = 0; i < splitTxtWords.size(); ++i) {
               String word = (String)splitTxtWords.get(i);
               if (pageText.length() + word.length() > 256) {
                  break;
               }

               if (word.equals("\n")) {
                  if (currentLine.length() > 0) {
                     pageText = pageText + currentLine;
                     currentLine = "";
                     ++numLines;
                     if (numLines >= 13) {
                        break;
                     }
                  }

                  ++usedWords;
                  if (pageText.length() > 0) {
                     pageText = pageText + word;
                     ++numLines;
                     if (numLines >= 13) {
                        break;
                     }
                  }
               } else {
                  currentLine = currentLine + word;
                  ++usedWords;
                  if (i < splitTxtWords.size() - 1) {
                     currentLine = currentLine + " ";
                  }

                  if (currentLine.length() >= 17) {
                     pageText = pageText + currentLine;
                     currentLine = "";
                     ++numLines;
                     if (numLines >= 13) {
                        break;
                     }
                  }
               }
            }

            if (currentLine.length() > 0) {
               pageText = pageText + currentLine;
               currentLine = "";
               ++numLines;
            }

            for(i = 0; i < usedWords; ++i) {
               splitTxtWords.remove(0);
            }

            loreTextPages.add(pageText);
         }

         return loreTextPages;
      }
   }

   private String formatRandom(String text, Random random) {
      int lastIndexStart = -1;

      while(true) {
         int indexStart = text.indexOf("{", lastIndexStart + 1);
         int indexEnd = text.indexOf("}");
         lastIndexStart = indexStart;
         if (indexStart < 0 || indexEnd <= indexStart) {
            return text;
         }

         String unformatted = text.substring(indexStart, indexEnd + 1);
         String formatted = unformatted.substring(1, unformatted.length() - 1);
         String remaining;
         String word;
         if (formatted.startsWith("num:")) {
            try {
               remaining = formatted.substring("num:".length());
               int i1 = remaining.indexOf(",");
               word = remaining.substring(0, i1);
               String s3 = remaining.substring(i1 + ",".length());
               int min = Integer.parseInt(word);
               int max = Integer.parseInt(s3);
               int number = MathHelper.func_76136_a(random, min, max);
               formatted = String.valueOf(number);
            } catch (Exception var16) {
               LOTRLog.logger.error("LOTRLore: Error formatting number " + unformatted + " in text: " + this.loreName);
               var16.printStackTrace();
            }
         } else if (formatted.startsWith("name:")) {
            try {
               remaining = formatted.substring("name:".length());
               if (!LOTRNames.nameBankExists(remaining)) {
                  LOTRLog.logger.error("LOTRLore: No namebank exists for " + remaining + "!");
               } else {
                  word = LOTRNames.getRandomName(remaining, random);
                  formatted = word;
               }
            } catch (Exception var15) {
               LOTRLog.logger.error("LOTRLore: Error formatting name " + unformatted + " in text: " + this.loreName);
               var15.printStackTrace();
            }
         } else if (formatted.startsWith("choose:")) {
            try {
               remaining = formatted.substring("choose:".length());

               ArrayList words;
               for(words = new ArrayList(); remaining.length() > 0; words.add(word)) {
                  int indexOf = remaining.indexOf("/");
                  if (indexOf >= 0) {
                     word = remaining.substring(0, indexOf);
                     remaining = remaining.substring(indexOf + "/".length());
                  } else {
                     word = remaining;
                     remaining = "";
                  }
               }

               formatted = (String)words.get(random.nextInt(words.size()));
            } catch (Exception var17) {
               LOTRLog.logger.error("LOTRLore: Error formatting choice " + unformatted + " in text: " + this.loreName);
               var17.printStackTrace();
            }
         }

         text = Pattern.compile(unformatted, 16).matcher(text).replaceFirst(Matcher.quoteReplacement(formatted));
      }
   }

   public ItemStack createLoreBook(Random random) {
      ItemStack itemstack = new ItemStack(Items.field_151164_bB);
      NBTTagCompound data = new NBTTagCompound();
      itemstack.func_77982_d(data);
      String title = this.formatRandom(this.loreTitle, random);
      String author = this.formatRandom(this.loreAuthor, random);
      String text = this.formatRandom(this.loreText, random);
      List textPages = organisePages(text);
      data.func_74778_a("title", title);
      data.func_74778_a("author", author);
      NBTTagList pages = new NBTTagList();
      Iterator var9 = textPages.iterator();

      while(var9.hasNext()) {
         String pageText = (String)var9.next();
         pages.func_74742_a(new NBTTagString(pageText));
      }

      data.func_74782_a("pages", pages);
      return itemstack;
   }

   public static enum LoreCategory {
      RUINS("ruins"),
      SHIRE("shire"),
      BREE("bree"),
      BLUE_MOUNTAINS("blue_mountains"),
      LINDON("lindon"),
      ERIADOR("eriador"),
      RIVENDELL("rivendell"),
      EREGION("eregion"),
      DUNLAND("dunland"),
      GUNDABAD("gundabad"),
      ANGMAR("angmar"),
      WOODLAND_REALM("woodland_realm"),
      DOL_GULDUR("dol_guldur"),
      DALE("dale"),
      DURIN("durins_folk"),
      LOTHLORIEN("lothlorien"),
      ROHAN("rohan"),
      ISENGARD("isengard"),
      GONDOR("gondor"),
      MORDOR("mordor"),
      DORWINION("dorwinion"),
      RHUN("rhun"),
      HARNENNOR("harnennor"),
      SOUTHRON("southron"),
      UMBAR("umbar"),
      NOMAD("nomad"),
      GULF("gulf"),
      FAR_HARAD("far_harad"),
      FAR_HARAD_JUNGLE("far_harad_jungle"),
      HALF_TROLL("half_troll");

      public static final String allCode = "all";
      public final String categoryName;
      private List loreList = new ArrayList();

      private LoreCategory(String s) {
         this.categoryName = s;
      }

      private void addLore(LOTRLore lore) {
         this.loreList.add(lore);
      }

      private LOTRLore getRandomLore(Random random) {
         return (LOTRLore)this.loreList.get(random.nextInt(this.loreList.size()));
      }

      public static LOTRLore.LoreCategory forName(String s) {
         LOTRLore.LoreCategory[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRLore.LoreCategory r = var1[var3];
            if (s.equalsIgnoreCase(r.categoryName)) {
               return r;
            }
         }

         return null;
      }
   }
}
