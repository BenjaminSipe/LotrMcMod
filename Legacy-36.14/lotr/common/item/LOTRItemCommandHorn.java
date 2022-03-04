package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemCommandHorn extends Item implements LOTRSquadrons.SquadronItem {
   public LOTRItemCommandHorn() {
      this.func_77627_a(true);
      this.func_77656_e(0);
      this.func_77625_d(1);
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (!world.field_72995_K) {
         List entities = world.field_72996_f;

         for(int l = 0; l < entities.size(); ++l) {
            if (entities.get(l) instanceof LOTREntityNPC) {
               LOTREntityNPC npc = (LOTREntityNPC)entities.get(l);
               if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && LOTRSquadrons.areSquadronsCompatible(npc, itemstack)) {
                  if (itemstack.func_77960_j() == 1 && npc.hiredNPCInfo.getObeyHornHaltReady()) {
                     npc.hiredNPCInfo.halt();
                  } else if (itemstack.func_77960_j() == 2 && npc.hiredNPCInfo.getObeyHornHaltReady()) {
                     npc.hiredNPCInfo.ready();
                  } else if (itemstack.func_77960_j() == 3 && npc.hiredNPCInfo.getObeyHornSummon()) {
                     npc.hiredNPCInfo.tryTeleportToHiringPlayer(true);
                  }
               }
            }
         }
      }

      if (itemstack.func_77960_j() == 1) {
         itemstack.func_77964_b(2);
      } else if (itemstack.func_77960_j() == 2) {
         itemstack.func_77964_b(1);
      }

      world.func_72956_a(entityplayer, "lotr:item.horn", 4.0F, 1.0F);
      return itemstack;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 40;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.bow;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (itemstack.func_77960_j() == 0) {
         entityplayer.openGui(LOTRMod.instance, 9, world, 0, 0, 0);
      } else {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      }

      return itemstack;
   }

   public String func_77667_c(ItemStack itemstack) {
      String s = "";
      if (itemstack.func_77960_j() == 1) {
         s = ".halt";
      } else if (itemstack.func_77960_j() == 2) {
         s = ".ready";
      } else if (itemstack.func_77960_j() == 3) {
         s = ".summon";
      }

      return super.func_77667_c(itemstack) + s;
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j <= 3; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
