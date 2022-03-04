package lotr.common.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotr.common.world.structure2.scan.LOTRScanAlias;
import lotr.common.world.structure2.scan.LOTRStructureScan;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRCommandStrScan extends CommandBase {
   private boolean scanning = false;
   private int originX;
   private int originY;
   private int originZ;
   private int minX;
   private int minY;
   private int minZ;
   private int maxX;
   private int maxY;
   private int maxZ;
   private List aliasOrder = new ArrayList();
   private Map blockAliases = new HashMap();
   private Map blockMetaAliases = new HashMap();
   private Set aliasesToInclude = new HashSet();

   public String func_71517_b() {
      return "strscan";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "development command";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 1) {
         String option = args[0];
         if (option.equals("begin")) {
            if (!this.scanning) {
               this.scanning = true;
               this.aliasOrder.clear();
               this.blockAliases.clear();
               this.blockMetaAliases.clear();
               func_152373_a(sender, this, "Begun scanning", new Object[0]);
               return;
            }

            throw new WrongUsageException("Already begun scanning", new Object[0]);
         }

         String scanName;
         int meta;
         Block block;
         if (option.equals("assoc") && args.length >= 3 && this.scanning) {
            scanName = args[1];
            block = Block.func_149684_b(scanName);
            if (block == null) {
               meta = Integer.parseInt(scanName);
               block = Block.func_149729_e(meta);
            }

            if (block != null) {
               String alias = args[2];
               if (!this.blockAliases.containsValue(alias)) {
                  this.blockAliases.put(block, alias);
                  this.aliasOrder.add(alias);
                  func_152373_a(sender, this, "Associated block %s to alias %s", new Object[]{scanName, alias});
                  return;
               }

               throw new WrongUsageException("Alias %s already used", new Object[]{alias});
            }

            throw new WrongUsageException("Block %s does not exist", new Object[]{scanName});
         }

         if (option.equals("assoc_meta") && args.length >= 4 && this.scanning) {
            scanName = args[1];
            block = Block.func_149684_b(scanName);
            if (block == null) {
               meta = Integer.parseInt(scanName);
               block = Block.func_149729_e(meta);
            }

            if (block != null) {
               meta = func_71526_a(sender, args[2]);
               if (meta >= 0 && meta <= 15) {
                  String alias = args[3];
                  if (!this.blockMetaAliases.containsValue(alias)) {
                     this.blockMetaAliases.put(Pair.of(block, meta), alias);
                     this.aliasOrder.add(alias);
                     func_152373_a(sender, this, "Associated block %s and metadata %s to alias %s", new Object[]{scanName, meta, alias});
                     return;
                  }

                  throw new WrongUsageException("Alias %s already used", new Object[]{alias});
               }

               throw new WrongUsageException("Invalid metadata value %s", new Object[]{meta});
            }

            throw new WrongUsageException("Block %s does not exist", new Object[]{scanName});
         }

         ChunkCoordinates coords;
         int i;
         int k;
         if (option.equals("origin") && args.length >= 4 && this.scanning) {
            coords = sender.func_82114_b();
            i = coords.field_71574_a;
            meta = coords.field_71572_b;
            k = coords.field_71573_c;
            i = MathHelper.func_76128_c(func_110666_a(sender, (double)i, args[1]));
            meta = MathHelper.func_76128_c(func_110666_a(sender, (double)meta, args[2]));
            k = MathHelper.func_76128_c(func_110666_a(sender, (double)k, args[3]));
            this.minX = this.maxX = this.originX = i;
            this.minY = this.maxY = this.originY = meta;
            this.minZ = this.maxZ = this.originZ = k;
            func_152373_a(sender, this, "Set scan origin to %s %s %s", new Object[]{this.originX, this.originY, this.originZ});
            return;
         }

         if (option.equals("expand") && args.length >= 4 && this.scanning) {
            coords = sender.func_82114_b();
            i = coords.field_71574_a;
            meta = coords.field_71572_b;
            k = coords.field_71573_c;
            i = MathHelper.func_76128_c(func_110666_a(sender, (double)i, args[1]));
            meta = MathHelper.func_76128_c(func_110666_a(sender, (double)meta, args[2]));
            k = MathHelper.func_76128_c(func_110666_a(sender, (double)k, args[3]));
            this.minX = Math.min(i, this.minX);
            this.minY = Math.min(meta, this.minY);
            this.minZ = Math.min(k, this.minZ);
            this.maxX = Math.max(i, this.maxX);
            this.maxY = Math.max(meta, this.maxY);
            this.maxZ = Math.max(k, this.maxZ);
            func_152373_a(sender, this, "Expanded scan region to include %s %s %s", new Object[]{i, meta, k});
            return;
         }

         if (option.equals("scan") && args.length >= 2 && this.scanning) {
            scanName = args[1];
            LOTRStructureScan scan = new LOTRStructureScan(scanName);
            Block fillBelowKey = Blocks.field_150357_h;
            Block findLowestKey = Blocks.field_150377_bs;
            World world = sender.func_130014_f_();

            for(int j = this.minY; j <= this.maxY; ++j) {
               for(int k = this.minZ; k <= this.maxZ; ++k) {
                  for(int i = this.minX; i <= this.maxX; ++i) {
                     int i1 = i - this.originX;
                     int j1 = j - this.originY;
                     int k1 = k - this.originZ;
                     Block block = world.func_147439_a(i, j, k);
                     int meta = world.func_72805_g(i, j, k);
                     boolean fillBelow = false;
                     boolean findLowest = false;
                     if (block != Blocks.field_150350_a && block != fillBelowKey && block != findLowestKey) {
                        if (world.func_147439_a(i, j - 1, k) == fillBelowKey) {
                           fillBelow = true;
                        } else if (world.func_147439_a(i, j - 1, k) == findLowestKey) {
                           findLowest = true;
                        }

                        LOTRStructureScan.ScanStepBase step = null;
                        String alias;
                        if (this.blockMetaAliases.containsKey(Pair.of(block, meta))) {
                           alias = (String)this.blockMetaAliases.get(Pair.of(block, meta));
                           step = new LOTRStructureScan.ScanStepBlockMetaAlias(i1, j1, k1, alias);
                           this.aliasesToInclude.add(alias);
                        } else if (this.blockAliases.containsKey(block)) {
                           alias = (String)this.blockAliases.get(block);
                           step = new LOTRStructureScan.ScanStepBlockAlias(i1, j1, k1, alias, meta);
                           this.aliasesToInclude.add(alias);
                        } else {
                           step = new LOTRStructureScan.ScanStep(i1, j1, k1, block, meta);
                        }

                        if (step != null) {
                           ((LOTRStructureScan.ScanStepBase)step).fillDown = fillBelow;
                           ((LOTRStructureScan.ScanStepBase)step).findLowest = findLowest;
                           scan.addScanStep((LOTRStructureScan.ScanStepBase)step);
                        }
                     }
                  }
               }
            }

            Iterator var28 = this.aliasOrder.iterator();

            while(var28.hasNext()) {
               String alias = (String)var28.next();
               if (this.aliasesToInclude.contains(alias)) {
                  if (this.blockMetaAliases.containsValue(alias)) {
                     scan.includeAlias(alias, LOTRScanAlias.Type.BLOCK_META);
                  } else if (this.blockAliases.containsValue(alias)) {
                     scan.includeAlias(alias, LOTRScanAlias.Type.BLOCK);
                  }
               }
            }

            if (LOTRStructureScan.writeScanToFile(scan)) {
               this.scanning = false;
               func_152373_a(sender, this, "Scanned structure as %s", new Object[]{scanName});
               return;
            }

            throw new WrongUsageException("Error scanning structure as %s", new Object[]{scanName});
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      return null;
   }

   public boolean func_82358_a(String[] args, int i) {
      return false;
   }
}
