package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;

public class LOTRBlockFlower extends BlockBush {
   public LOTRBlockFlower() {
      this(Material.field_151585_k);
   }

   public LOTRBlockFlower(Material material) {
      super(material);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149779_h);
   }

   public Block setFlowerBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
      this.func_149676_a(minX, minY, minZ, maxX, maxY, maxZ);
      return this;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getFlowerRenderID();
   }
}
