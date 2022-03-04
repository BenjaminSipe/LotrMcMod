package lotr.common.block;

import java.util.ArrayList;
import java.util.Random;
import lotr.common.LOTRDamage;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorThorn extends LOTRBlockMordorPlant implements IShearable {
   public LOTRBlockMordorThorn() {
      this.func_149676_a(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
      return true;
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(this));
      return drops;
   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      if (LOTRMod.getNPCFaction(entity) != LOTRFaction.MORDOR) {
         entity.func_70097_a(LOTRDamage.plantHurt, 2.0F);
      }

   }
}
