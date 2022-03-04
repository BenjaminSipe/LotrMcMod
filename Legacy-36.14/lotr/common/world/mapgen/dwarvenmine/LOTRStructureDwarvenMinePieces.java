package lotr.common.world.mapgen.dwarvenmine;

import java.util.List;
import java.util.Random;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRStructureDwarvenMinePieces {
   private static StructureComponent getRandomComponent(List list, Random random, int i, int j, int k, int direction, int iteration, boolean ruined) {
      int l = random.nextInt(100);
      StructureBoundingBox structureboundingbox;
      if (l >= 80) {
         structureboundingbox = LOTRComponentDwarvenMineCrossing.findValidPlacement(list, random, i, j, k, direction);
         if (structureboundingbox != null) {
            return new LOTRComponentDwarvenMineCrossing(iteration, random, structureboundingbox, direction, ruined);
         }
      } else if (l >= 70) {
         structureboundingbox = LOTRComponentDwarvenMineStairs.findValidPlacement(list, random, i, j, k, direction);
         if (structureboundingbox != null) {
            return new LOTRComponentDwarvenMineStairs(iteration, random, structureboundingbox, direction, ruined);
         }
      } else {
         structureboundingbox = LOTRComponentDwarvenMineCorridor.findValidPlacement(list, random, i, j, k, direction);
         if (structureboundingbox != null) {
            return new LOTRComponentDwarvenMineCorridor(iteration, random, structureboundingbox, direction, ruined);
         }
      }

      return null;
   }

   private static StructureComponent getNextMineComponent(StructureComponent component, List list, Random random, int i, int j, int k, int direction, int iteration, boolean ruined) {
      if (iteration > 12) {
         return null;
      } else if (Math.abs(i - component.func_74874_b().field_78897_a) <= 80 && Math.abs(k - component.func_74874_b().field_78896_c) <= 80) {
         StructureComponent structurecomponent1 = getRandomComponent(list, random, i, j, k, direction, iteration + 1, ruined);
         if (structurecomponent1 != null) {
            list.add(structurecomponent1);
            structurecomponent1.func_74861_a(component, list, random);
         }

         return structurecomponent1;
      } else {
         return null;
      }
   }

   public static StructureComponent getNextComponent(StructureComponent component, List list, Random random, int i, int j, int k, int direction, int iteration, boolean ruined) {
      return getNextMineComponent(component, list, random, i, j, k, direction, iteration, ruined);
   }
}
