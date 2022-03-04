package lotr.common.world.mapgen.dwarvenmine;

import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineCrossing extends StructureComponent {
   private int corridorDirection;
   private boolean isMultipleFloors;
   private boolean ruined;

   public LOTRComponentDwarvenMineCrossing() {
   }

   public LOTRComponentDwarvenMineCrossing(int i, Random random, StructureBoundingBox structureBoundingBox, int j, boolean r) {
      super(i);
      this.corridorDirection = j;
      this.field_74887_e = structureBoundingBox;
      this.isMultipleFloors = this.field_74887_e.func_78882_c() > 3;
      this.ruined = r;
   }

   protected void func_143012_a(NBTTagCompound nbt) {
      nbt.func_74768_a("Direction", this.corridorDirection);
      nbt.func_74757_a("Multiple", this.isMultipleFloors);
      nbt.func_74757_a("Ruined", this.ruined);
   }

   protected void func_143011_b(NBTTagCompound nbt) {
      this.corridorDirection = nbt.func_74762_e("Direction");
      this.isMultipleFloors = nbt.func_74767_n("Multiple");
      this.ruined = nbt.func_74767_n("Ruined");
   }

   public static StructureBoundingBox findValidPlacement(List list, Random random, int i, int j, int k, int l) {
      StructureBoundingBox structureboundingbox = new StructureBoundingBox(i, j, k, i, j + 2, k);
      if (random.nextInt(4) == 0) {
         structureboundingbox.field_78894_e += 4;
      }

      switch(l) {
      case 0:
         structureboundingbox.field_78897_a = i - 1;
         structureboundingbox.field_78893_d = i + 3;
         structureboundingbox.field_78892_f = k + 4;
         break;
      case 1:
         structureboundingbox.field_78897_a = i - 4;
         structureboundingbox.field_78896_c = k - 1;
         structureboundingbox.field_78892_f = k + 3;
         break;
      case 2:
         structureboundingbox.field_78897_a = i - 1;
         structureboundingbox.field_78893_d = i + 3;
         structureboundingbox.field_78896_c = k - 4;
         break;
      case 3:
         structureboundingbox.field_78893_d = i + 4;
         structureboundingbox.field_78896_c = k - 1;
         structureboundingbox.field_78892_f = k + 3;
      }

      return StructureComponent.func_74883_a(list, structureboundingbox) != null ? null : structureboundingbox;
   }

   public void func_74861_a(StructureComponent component, List list, Random random) {
      int i = this.func_74877_c();
      switch(this.corridorDirection) {
      case 0:
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, 0, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, 1, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, 3, i, this.ruined);
         break;
      case 1:
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, 2, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, 0, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, 1, i, this.ruined);
         break;
      case 2:
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, 2, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, 1, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, 3, i, this.ruined);
         break;
      case 3:
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c - 1, 2, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f + 1, 0, i, this.ruined);
         LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, 3, i, this.ruined);
      }

      if (this.isMultipleFloors) {
         if (random.nextBoolean()) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78896_c - 1, 2, i, this.ruined);
         }

         if (random.nextBoolean()) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78896_c + 1, 1, i, this.ruined);
         }

         if (random.nextBoolean()) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78896_c + 1, 3, i, this.ruined);
         }

         if (random.nextBoolean()) {
            LOTRStructureDwarvenMinePieces.getNextComponent(component, list, random, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b + 3 + 1, this.field_74887_e.field_78892_f + 1, 0, i, this.ruined);
         }
      }

   }

   public boolean func_74875_a(World world, Random random, StructureBoundingBox structureBoundingBox) {
      if (this.func_74860_a(world, structureBoundingBox)) {
         return false;
      } else {
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f, Blocks.field_150350_a, Blocks.field_150350_a, false);
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78897_a, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78893_d, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f - 1, Blocks.field_150350_a, Blocks.field_150350_a, false);
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78896_c + 1, LOTRMod.pillar, Blocks.field_150350_a, false);
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f - 1, this.field_74887_e.field_78897_a + 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f - 1, LOTRMod.pillar, Blocks.field_150350_a, false);
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78896_c + 1, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78896_c + 1, LOTRMod.pillar, Blocks.field_150350_a, false);
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78895_b, this.field_74887_e.field_78892_f - 1, this.field_74887_e.field_78893_d - 1, this.field_74887_e.field_78894_e, this.field_74887_e.field_78892_f - 1, LOTRMod.pillar, Blocks.field_150350_a, false);

         for(int i = this.field_74887_e.field_78897_a; i <= this.field_74887_e.field_78893_d; ++i) {
            for(int j = this.field_74887_e.field_78896_c; j <= this.field_74887_e.field_78892_f; ++j) {
               Block block = this.func_151548_a(world, i, this.field_74887_e.field_78895_b - 1, j, structureBoundingBox);
               if (block.func_149688_o().func_76222_j() || block.func_149688_o() == Material.field_151595_p) {
                  this.func_151550_a(world, Blocks.field_150348_b, 0, i, this.field_74887_e.field_78895_b - 1, j, structureBoundingBox);
               }

               block = this.func_151548_a(world, i, this.field_74887_e.field_78894_e + 1, j, structureBoundingBox);
               if (block.func_149688_o().func_76222_j() || block.func_149688_o() == Material.field_151595_p) {
                  this.func_151550_a(world, Blocks.field_150348_b, 0, i, this.field_74887_e.field_78894_e + 1, j, structureBoundingBox);
               }
            }
         }

         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78897_a + 2, this.field_74887_e.field_78895_b - 1, this.field_74887_e.field_78896_c - 1, this.field_74887_e.field_78897_a + 2, this.field_74887_e.field_78895_b - 1, this.field_74887_e.field_78892_f + 1, LOTRMod.pillar, Blocks.field_150350_a, false);
         this.func_151549_a(world, structureBoundingBox, this.field_74887_e.field_78897_a - 1, this.field_74887_e.field_78895_b - 1, this.field_74887_e.field_78896_c + 2, this.field_74887_e.field_78893_d + 1, this.field_74887_e.field_78895_b - 1, this.field_74887_e.field_78896_c + 2, LOTRMod.pillar, Blocks.field_150350_a, false);
         if (!this.ruined) {
            this.func_151550_a(world, LOTRMod.brick3, 12, this.field_74887_e.field_78897_a + 2, this.field_74887_e.field_78895_b - 1, this.field_74887_e.field_78896_c + 2, structureBoundingBox);
         }

         return true;
      }
   }
}
