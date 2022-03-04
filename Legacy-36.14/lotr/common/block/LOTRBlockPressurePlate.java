package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockPressurePlate.Sensitivity;
import net.minecraft.block.material.Material;

public class LOTRBlockPressurePlate extends BlockPressurePlate {
   public LOTRBlockPressurePlate(String iconPath, Material material, Sensitivity triggerType) {
      super(iconPath, material, triggerType);
      this.func_149647_a(LOTRCreativeTabs.tabMisc);
   }
}
