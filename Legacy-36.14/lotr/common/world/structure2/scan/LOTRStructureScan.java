package lotr.common.world.structure2.scan;

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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRMod;
import lotr.common.util.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.BOMInputStream;

public class LOTRStructureScan {
   private static final String strscanFormat = ".strscan";
   private static Map allLoadedScans = new HashMap();
   public final String scanName;
   public final List scanSteps = new ArrayList();
   public final List aliases = new ArrayList();

   public static void loadAllScans() {
      allLoadedScans.clear();
      Map scanNamesAndReaders = new HashMap();
      ZipFile zip = null;

      String nextLine;
      try {
         ModContainer mc = LOTRMod.getModContainer();
         if (mc.getSource().isFile()) {
            zip = new ZipFile(mc.getSource());
            Enumeration entries = zip.entries();

            while(entries.hasMoreElements()) {
               ZipEntry entry = (ZipEntry)entries.nextElement();
               String s = entry.getName();
               String path = "assets/lotr/strscan/";
               if (s.startsWith(path) && s.endsWith(".strscan")) {
                  s = s.substring(path.length());
                  int i = s.indexOf(".strscan");

                  try {
                     s = s.substring(0, i);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                     scanNamesAndReaders.put(s, reader);
                  } catch (Exception var26) {
                     FMLLog.severe("Failed to load LOTR structure scan " + s + "from zip file", new Object[0]);
                     var26.printStackTrace();
                  }
               }
            }
         } else {
            File scanDir = new File(LOTRMod.class.getResource("/assets/lotr/strscan").toURI());
            Collection subfiles = FileUtils.listFiles(scanDir, (String[])null, true);
            Iterator var34 = subfiles.iterator();

            while(var34.hasNext()) {
               File subfile = (File)var34.next();
               nextLine = subfile.getPath();
               nextLine = nextLine.substring(scanDir.getPath().length() + 1);
               nextLine = nextLine.replace(File.separator, "/");
               int i = nextLine.indexOf(".strscan");
               if (i < 0) {
                  FMLLog.severe("Failed to load LOTR structure scan " + nextLine + " from MCP folder - not in " + ".strscan" + " format", new Object[0]);
               } else {
                  try {
                     nextLine = nextLine.substring(0, i);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(subfile)), Charsets.UTF_8.name()));
                     scanNamesAndReaders.put(nextLine, reader);
                  } catch (Exception var25) {
                     FMLLog.severe("Failed to load LOTR structure scan " + nextLine + " from MCP folder", new Object[0]);
                     var25.printStackTrace();
                  }
               }
            }
         }
      } catch (Exception var28) {
         FMLLog.severe("Failed to load LOTR structure scans", new Object[0]);
         var28.printStackTrace();
      }

      Iterator var29 = scanNamesAndReaders.keySet().iterator();

      while(var29.hasNext()) {
         String strName = (String)var29.next();
         BufferedReader reader = (BufferedReader)scanNamesAndReaders.get(strName);
         int curLine = 0;

         try {
            ArrayList lines = new ArrayList();

            while((nextLine = reader.readLine()) != null) {
               lines.add(nextLine);
            }

            reader.close();
            if (lines.isEmpty()) {
               FMLLog.severe("LOTR structure scans " + strName + " is empty!", new Object[0]);
            } else {
               LOTRStructureScan scan = new LOTRStructureScan(strName);
               Iterator var41 = lines.iterator();

               while(var41.hasNext()) {
                  String line = (String)var41.next();
                  ++curLine;
                  if (line.length() != 0) {
                     String s1;
                     if (line.charAt(0) == LOTRScanAlias.Type.BLOCK.typeCode) {
                        s1 = line.substring(1, line.length() - 1);
                        scan.aliases.add(new LOTRScanAlias(s1, LOTRScanAlias.Type.BLOCK));
                     } else if (line.charAt(0) == LOTRScanAlias.Type.BLOCK_META.typeCode) {
                        s1 = line.substring(1, line.length() - 1);
                        scan.aliases.add(new LOTRScanAlias(s1, LOTRScanAlias.Type.BLOCK_META));
                     } else {
                        int i = 0;
                        int j = line.indexOf(".");
                        String s1 = line.substring(i, j);
                        int x = Integer.parseInt(s1);
                        LOTRStructureScan.ScanStepBase step = null;
                        boolean fillDown = false;
                        boolean findLowest = false;
                        int i = j + 1;
                        j = line.indexOf(".", i);
                        s1 = line.substring(i, j);
                        if (s1.endsWith("v")) {
                           fillDown = true;
                           s1 = s1.substring(0, s1.length() - 1);
                        } else if (s1.endsWith("_")) {
                           findLowest = true;
                           s1 = s1.substring(0, s1.length() - 1);
                        }

                        int y = Integer.parseInt(s1);
                        i = j + 1;
                        j = line.indexOf(".", i);
                        s1 = line.substring(i, j);
                        int z = Integer.parseInt(s1);
                        i = j + 1;
                        char c = line.charAt(i);
                        if (c == '"') {
                           j = line.indexOf("\"", i + 1);
                           s1 = line.substring(i, j + 1);
                           s1 = s1.substring(1, s1.length() - 1);
                           Block block = Block.func_149684_b(s1);
                           if (block == null) {
                              FMLLog.severe("LOTRStrScan: Block " + s1 + " does not exist!", new Object[0]);
                              block = Blocks.field_150348_b;
                           }

                           i = j + 2;
                           j = line.length();
                           s1 = line.substring(i, j);
                           int meta = Integer.parseInt(s1);
                           step = new LOTRStructureScan.ScanStep(x, y, z, block, meta);
                        } else if (c == LOTRScanAlias.Type.BLOCK.typeCode) {
                           j = line.indexOf(LOTRScanAlias.Type.BLOCK.typeCode, i + 1);
                           s1 = line.substring(i, j + 1);
                           s1 = s1.substring(1, s1.length() - 1);
                           String alias = s1;
                           i = j + 2;
                           j = line.length();
                           s1 = line.substring(i, j);
                           int meta = Integer.parseInt(s1);
                           step = new LOTRStructureScan.ScanStepBlockAlias(x, y, z, alias, meta);
                        } else if (c == LOTRScanAlias.Type.BLOCK_META.typeCode) {
                           j = line.indexOf(LOTRScanAlias.Type.BLOCK_META.typeCode, i + 1);
                           s1 = line.substring(i, j + 1);
                           s1 = s1.substring(1, s1.length() - 1);
                           step = new LOTRStructureScan.ScanStepBlockMetaAlias(x, y, z, s1);
                        } else if (c == '/') {
                           j = line.indexOf("/", i + 1);
                           s1 = line.substring(i, j + 1);
                           s1 = s1.substring(1, s1.length() - 1);
                           if (s1.equals("SKULL")) {
                              step = new LOTRStructureScan.ScanStepSkull(x, y, z);
                           }
                        }

                        if (step == null) {
                           throw new IllegalArgumentException("Invalid scan instruction on line " + curLine);
                        }

                        ((LOTRStructureScan.ScanStepBase)step).fillDown = fillDown;
                        ((LOTRStructureScan.ScanStepBase)step).findLowest = findLowest;
                        ((LOTRStructureScan.ScanStepBase)step).lineNumber = curLine;
                        scan.addScanStep((LOTRStructureScan.ScanStepBase)step);
                     }
                  }
               }

               allLoadedScans.put(scan.scanName, scan);
            }
         } catch (Exception var27) {
            FMLLog.severe("Failed to load LOTR structure scan " + strName + ": error on line " + curLine, new Object[0]);
            var27.printStackTrace();
         }
      }

      if (zip != null) {
         try {
            zip.close();
         } catch (IOException var24) {
            var24.printStackTrace();
         }
      }

   }

   public static boolean writeScanToFile(LOTRStructureScan scan) {
      File dir = new File(DimensionManager.getCurrentSaveRootDirectory(), "lotr_str_scans");
      if (!dir.exists()) {
         dir.mkdirs();
      }

      File scanFile = new File(dir, scan.scanName + ".strscan");

      try {
         if (!scanFile.exists()) {
            scanFile.createNewFile();
         }

         PrintStream writer = new PrintStream(new FileOutputStream(scanFile));
         Iterator var4;
         if (!scan.aliases.isEmpty()) {
            var4 = scan.aliases.iterator();

            while(var4.hasNext()) {
               LOTRScanAlias alias = (LOTRScanAlias)var4.next();
               writer.println(alias.getFullCode());
            }

            writer.println();
         }

         var4 = scan.scanSteps.iterator();

         while(var4.hasNext()) {
            LOTRStructureScan.ScanStepBase e = (LOTRStructureScan.ScanStepBase)var4.next();
            writer.print(e.x);
            writer.print(".");
            writer.print(e.y);
            if (e.fillDown) {
               writer.print("v");
            } else if (e.findLowest) {
               writer.print("_");
            }

            writer.print(".");
            writer.print(e.z);
            writer.print(".");
            if (e instanceof LOTRStructureScan.ScanStep) {
               LOTRStructureScan.ScanStep step = (LOTRStructureScan.ScanStep)e;
               writer.print("\"");
               String blockName = Block.field_149771_c.func_148750_c(step.block);
               if (blockName.startsWith("minecraft:")) {
                  blockName = blockName.substring("minecraft:".length());
               }

               writer.print(blockName);
               writer.print("\"");
               writer.print(".");
               writer.print(step.meta);
               writer.println();
            } else if (e instanceof LOTRStructureScan.ScanStepBlockAlias) {
               LOTRStructureScan.ScanStepBlockAlias step = (LOTRStructureScan.ScanStepBlockAlias)e;
               writer.print("#");
               writer.print(step.alias);
               writer.print("#");
               writer.print(".");
               writer.print(step.meta);
               writer.println();
            } else if (e instanceof LOTRStructureScan.ScanStepBlockMetaAlias) {
               LOTRStructureScan.ScanStepBlockMetaAlias step = (LOTRStructureScan.ScanStepBlockMetaAlias)e;
               writer.print("~");
               writer.print(step.alias);
               writer.print("~");
               writer.println();
            }
         }

         writer.close();
         return true;
      } catch (Exception var8) {
         LOTRLog.logger.error("Error saving strscan file " + scan.scanName);
         var8.printStackTrace();
         return false;
      }
   }

   public static LOTRStructureScan getScanByName(String name) {
      return (LOTRStructureScan)allLoadedScans.get(name);
   }

   public LOTRStructureScan(String name) {
      this.scanName = name;
   }

   public void addScanStep(LOTRStructureScan.ScanStepBase e) {
      this.scanSteps.add(e);
   }

   public void includeAlias(String alias, LOTRScanAlias.Type type) {
      this.includeAlias(new LOTRScanAlias(alias, type));
   }

   public void includeAlias(LOTRScanAlias alias) {
      Iterator var2 = this.aliases.iterator();

      LOTRScanAlias existingAlias;
      do {
         if (!var2.hasNext()) {
            this.aliases.add(alias);
            return;
         }

         existingAlias = (LOTRScanAlias)var2.next();
      } while(!existingAlias.name.equals(alias.name));

   }

   public static class ScanStepSkull extends LOTRStructureScan.ScanStepBase {
      public ScanStepSkull(int _x, int _y, int _z) {
         super(_x, _y, _z);
      }

      public boolean hasAlias() {
         return false;
      }

      public String getAlias() {
         return null;
      }

      public Block getBlock(Block aliasBlock) {
         return Blocks.field_150465_bP;
      }

      public int getMeta(int aliasMeta) {
         return 1;
      }
   }

   public static class ScanStepBlockMetaAlias extends LOTRStructureScan.ScanStepBase {
      public final String alias;

      public ScanStepBlockMetaAlias(int _x, int _y, int _z, String _alias) {
         super(_x, _y, _z);
         this.alias = _alias;
      }

      public boolean hasAlias() {
         return true;
      }

      public String getAlias() {
         return this.alias;
      }

      public Block getBlock(Block aliasBlock) {
         return aliasBlock;
      }

      public int getMeta(int aliasMeta) {
         return aliasMeta;
      }
   }

   public static class ScanStepBlockAlias extends LOTRStructureScan.ScanStepBase {
      public final String alias;
      public final int meta;

      public ScanStepBlockAlias(int _x, int _y, int _z, String _alias, int _meta) {
         super(_x, _y, _z);
         this.alias = _alias;
         this.meta = _meta;
      }

      public boolean hasAlias() {
         return true;
      }

      public String getAlias() {
         return this.alias;
      }

      public Block getBlock(Block aliasBlock) {
         return aliasBlock;
      }

      public int getMeta(int aliasMeta) {
         return this.meta;
      }
   }

   public static class ScanStep extends LOTRStructureScan.ScanStepBase {
      public final Block block;
      public final int meta;

      public ScanStep(int _x, int _y, int _z, Block _block, int _meta) {
         super(_x, _y, _z);
         this.block = _block;
         this.meta = _meta;
      }

      public boolean hasAlias() {
         return false;
      }

      public String getAlias() {
         return null;
      }

      public Block getBlock(Block aliasBlock) {
         return this.block;
      }

      public int getMeta(int aliasMeta) {
         return this.meta;
      }
   }

   public abstract static class ScanStepBase {
      public final int x;
      public final int y;
      public final int z;
      public boolean fillDown = false;
      public boolean findLowest = false;
      public int lineNumber;

      public ScanStepBase(int _x, int _y, int _z) {
         this.x = _x;
         this.y = _y;
         this.z = _z;
      }

      public abstract boolean hasAlias();

      public abstract String getAlias();

      public abstract Block getBlock(Block var1);

      public abstract int getMeta(int var1);
   }
}
