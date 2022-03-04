package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockBanana extends LOTRBlockHangingFruit {
   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      int dir = world.func_72805_g(i, j, k);
      switch(dir) {
      case 0:
         this.func_149676_a(0.375F, 0.1875F, 0.0F, 0.625F, 0.9375F, 0.25F);
         break;
      case 1:
         this.func_149676_a(0.375F, 0.1875F, 0.75F, 0.625F, 0.9375F, 1.0F);
         break;
      case 2:
         this.func_149676_a(0.0F, 0.1875F, 0.375F, 0.25F, 0.9375F, 0.625F);
         break;
      case 3:
         this.func_149676_a(0.75F, 0.1875F, 0.375F, 1.0F, 0.9375F, 0.625F);
      }

   }

   public Item func_149650_a(int i, Random random, int j) {
      return LOTRMod.banana;
   }

   public boolean removedByPlayer(World world, EntityPlayer entityplayer, int i, int j, int k, boolean willHarvest) {
      boolean flag = super.removedByPlayer(world, entityplayer, i, j, k, willHarvest);
      if (flag && !world.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.pickBanana);
      }

      return flag;
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return LOTRMod.banana;
   }
}
