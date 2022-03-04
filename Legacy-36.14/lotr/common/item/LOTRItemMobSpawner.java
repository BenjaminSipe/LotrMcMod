package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRItemMobSpawner extends ItemBlock {
   public LOTRItemMobSpawner(Block block) {
      super(block);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public int func_77647_b(int i) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      Iterator it = LOTREntities.spawnEggs.values().iterator();

      while(it.hasNext()) {
         LOTREntities.SpawnEggInfo info = (LOTREntities.SpawnEggInfo)it.next();
         list.add(new ItemStack(item, 1, info.spawnedID));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      String entityName = LOTREntities.getStringFromID(itemstack.func_77960_j());
      list.add(entityName);
   }

   public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
      if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
         TileEntity tileentity = world.func_147438_o(i, j, k);
         if (tileentity != null && tileentity instanceof LOTRTileEntityMobSpawner) {
            ((LOTRTileEntityMobSpawner)tileentity).setEntityClassID(itemstack.func_77960_j());
            ((LOTRTileEntityMobSpawner)tileentity).spawnsPersistentNPCs = true;
         }

         return true;
      } else {
         return false;
      }
   }
}
