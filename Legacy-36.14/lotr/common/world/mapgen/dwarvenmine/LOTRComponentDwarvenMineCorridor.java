package lotr.common.world.mapgen.dwarvenmine;

import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineCorridor extends StructureComponent {
   private int sectionCount;
   private boolean ruined;

   public LOTRComponentDwarvenMineCorridor() {
   }

   public LOTRComponentDwarvenMineCorridor(int i, Random random, StructureBoundingBox structureBoundingBox, int j, boolean r) {
      super(i);
      this.field_74885_f = j;
      this.field_74887_e = structureBoundingBox;
      if (this.field_74885_f != 2 && this.field_74885_f != 0) {
         this.sectionCount = this.field_74887_e.func_78883_b() / 4;
      } else {
         this.sectionCount = this.field_74887_e.func_78880_d() / 4;
      }

      this.ruined = r;
   }

   protected void func_143012_a(NBTTagCompound nbt) {
      nbt.func_74768_a("Sections", this.sectionCount);
      nbt.func_74757_a("Ruined", this.ruined);
   }

   protected void func_143011_b(NBTTagCompound nbt) {
      this.sectionCount = nbt.func_74762_e("Sections");
      this.ruined = nbt.func_74767_n("Ruined");
   }

   public static StructureBoundingBox findValidPlacement(List list, Random random, int i, int j, int k, int l) {
      StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 3, k);

      int i1;
      for(i1 = random.nextInt(3) + 2; i1 > 0; --i1) {
         int j1 = i1 * 4;
         switch(l) {
         case 0:
            structureboundingbox.field_78893_d = i + 2;
            structureboundingbox.field_78892_f = k + (j1 - 1);
            break;
         case 1:
            structureboundingbox.field_78897_a = i - (j1 - 1);
            structureboundingbox.field_78892_f = k + 2;
            break;
         case 2:
            structureboundingbox.field_78893_d = i + 2;
            structureboundingbox.field_78896_c = k - (j1 - 1);
            break;
         case 3:
            structureboundingbox.field_78893_d = i + (j1 - 1);
            structureboundingbox.field_78892_f = k + 2;
         }

         if (StructureComponent.func_74883_a(list, structureboundingbox) == null) {
            break;
         }
      }

      return i1 > 0 ? structureboundingbox : null;
   }

   public void func_74861_a(StructureComponent component, List list, Random random) {
      int i = this.func_74877_c();
      int j = random.nextInt(4);
      switch(this.field_74885_f) {
      case 0:
         if (j <= 1) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78892_f + 1, this.field_74885_f, i, this.ruined);
         } else if (j == 2) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78892_f - 3, 1, i, this.ruined);
         } else {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78892_f - 3, 3, i, this.ruined);
         }
         break;
      case 1:
         if (j <= 1) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c, this.field_74885_f, i, this.ruined);
         } else if (j == 2) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c - 1, 2, i, this.ruined);
         } else {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78892_f + 1, 0, i, this.ruined);
         }
         break;
      case 2:
         if (j <= 1) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c - 1, this.field_74885_f, i, this.ruined);
         } else if (j == 2) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c, 1, i, this.ruined);
         } else {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c, 3, i, this.ruined);
         }
         break;
      case 3:
         if (j <= 1) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c, this.field_74885_f, i, this.ruined);
         } else if (j == 2) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d - 3, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78896_c - 1, 2, i, this.ruined);
         } else {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d - 3, this.field_74887_e.field_78895_b - 1 + random.nextInt(3), this.field_74887_e.field_78892_f + 1, 0, i, this.ruined);
         }
      }

      if (i < 12) {
         int k;
         int l;
         if (this.field_74885_f != 2 && this.field_74885_f != 0) {
            for(k = this.field_74887_e.field_78897_a + 3; k + 3 <= this.field_74887_e.field_78893_d; k += 4) {
               l = random.nextInt(5);
               if (l == 0) {
                  LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, k, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, 2, i + 1, this.ruined);
               } else if (l == 1) {
                  LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, k, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, 0, i + 1, this.ruined);
               }
            }
         } else {
            for(k = this.field_74887_e.field_78896_c + 3; k + 3 <= this.field_74887_e.field_78892_f; k += 4) {
               l = random.nextInt(5);
               if (l == 0) {
                  LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, k, 1, i + 1, this.ruined);
               } else if (l == 1) {
                  LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, k, 3, i + 1, this.ruined);
               }
            }
         }
      }

   }

   public boolean func_74875_a(World world, Random random, StructureBoundingBox structureBoundingBox) {
      if (this.func_74860_a(world, structureBoundingBox)) {
         return false;
      } else {
         int length = this.sectionCount * 4 - 1;
         this.func_151549_a(world, structureBoundingBox, 0, 0, 0, 2, 2, length, Blocks.field_150350_a, Blocks.field_150350_a, false);

         int k;
         int k;
         for(k = 0; k < this.sectionCount; ++k) {
            k = 2 + k * 4;
            int[] var7 = new int[]{0, 2};
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               int i = var7[var9];
               int wallHeight = this.ruined ? random.nextInt(3) : 2;

               for(int j = 0; j <= wallHeight; ++j) {
                  this.func_151550_a(world, LOTRMod.wall, 7, i, j, k, structureBoundingBox);
               }
            }

            this.func_151549_a(world, structureBoundingBox, -1, 0, k, -1, 2, k, LOTRMod.pillar, Blocks.field_150350_a, false);
            this.func_151549_a(world, structureBoundingBox, 3, 0, k, 3, 2, k, LOTRMod.pillar, Blocks.field_150350_a, false);
            this.func_151549_a(world, structureBoundingBox, 1, -1, k - 2, 1, -1, k + 2, LOTRMod.pillar, Blocks.field_150350_a, false);
            if (this.func_151548_a(world, 1, -1, k - 3, structureBoundingBox) != Blocks.field_150350_a) {
               this.func_151550_a(world, LOTRMod.pillar, 0, 1, -1, k - 3, structureBoundingBox);
            }

            if (this.func_151548_a(world, 1, -1, k + 3, structureBoundingBox) != Blocks.field_150350_a) {
               this.func_151550_a(world, LOTRMod.pillar, 0, 1, -1, k + 3, structureBoundingBox);
            }

            if (!this.ruined) {
               this.func_151550_a(world, LOTRMod.brick3, 12, 1, -1, k, structureBoundingBox);
               if (random.nextInt(80) == 0) {
                  this.func_151550_a(world, Blocks.field_150462_ai, 0, 2, 0, k - 1, structureBoundingBox);
               }

               if (random.nextInt(80) == 0) {
                  this.func_151550_a(world, Blocks.field_150462_ai, 0, 0, 0, k + 1, structureBoundingBox);
               }
            }

            if (random.nextInt(120) == 0) {
               this.func_74879_a(world, structureBoundingBox, random, 2, 0, k - 1, LOTRChestContents.DWARVEN_MINE_CORRIDOR.items, LOTRChestContents.getRandomItemAmount(LOTRChestContents.DWARVEN_MINE_CORRIDOR, random));
            }

            if (random.nextInt(120) == 0) {
               this.func_74879_a(world, structureBoundingBox, random, 0, 0, k + 1, LOTRChestContents.DWARVEN_MINE_CORRIDOR.items, LOTRChestContents.getRandomItemAmount(LOTRChestContents.DWARVEN_MINE_CORRIDOR, random));
            }
         }

         for(k = 0; k <= length; ++k) {
            Block block;
            for(k = -1; k <= 3; ++k) {
               block = this.func_151548_a(world, k, -1, k, structureBoundingBox);
               if (block.func_149688_o().func_76222_j() || block.func_149688_o() == Material.field_151595_p) {
                  this.func_151550_a(world, Blocks.field_150348_b, 0, k, -1, k, structureBoundingBox);
               }

               int j = 3;
               block = this.func_151548_a(world, k, j, k, structureBoundingBox);
               if (block.func_149688_o().func_76222_j() || block.func_149688_o() == Material.field_151595_p) {
                  this.func_151550_a(world, Blocks.field_150348_b, 0, k, j, k, structureBoundingBox);
               }
            }

            for(k = 0; k <= 2; ++k) {
               block = this.func_151548_a(world, -1, k, k, structureBoundingBox);
               if (block.func_149688_o().func_76222_j() || block.func_149688_o() == Material.field_151595_p) {
                  this.func_151550_a(world, Blocks.field_150348_b, 0, -1, k, k, structureBoundingBox);
               }

               block = this.func_151548_a(world, 3, k, k, structureBoundingBox);
               if (block.func_149688_o().func_76222_j() || block.func_149688_o() == Material.field_151595_p) {
                  this.func_151550_a(world, Blocks.field_150348_b, 0, 3, k, k, structureBoundingBox);
               }
            }
         }

         return true;
      }
   }
}
