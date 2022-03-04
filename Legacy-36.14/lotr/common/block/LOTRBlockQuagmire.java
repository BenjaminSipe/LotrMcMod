package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class LOTRBlockQuagmire extends Block {
   public LOTRBlockQuagmire() {
      super(Material.field_151578_c);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      if (entity instanceof LOTREntitySpiderBase) {
         ((LOTREntitySpiderBase)entity).setInQuag();
      } else {
         entity.func_70110_aj();
      }

   }

   public boolean func_149662_c() {
      return false;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }
}
