package lotr.common;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

public class LOTRSquadrons {
   public static int SQUADRON_LENGTH_MAX = 200;

   public static String checkAcceptableLength(String squadron) {
      if (squadron != null && squadron.length() > SQUADRON_LENGTH_MAX) {
         squadron = squadron.substring(0, SQUADRON_LENGTH_MAX);
      }

      return squadron;
   }

   public static boolean areSquadronsCompatible(LOTREntityNPC npc, ItemStack itemstack) {
      String npcSquadron = npc.hiredNPCInfo.getSquadron();
      String itemSquadron = getSquadron(itemstack);
      return StringUtils.func_151246_b(npcSquadron) ? StringUtils.func_151246_b(itemSquadron) : npcSquadron.equalsIgnoreCase(itemSquadron);
   }

   public static String getSquadron(ItemStack itemstack) {
      if (itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem && itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("LOTRSquadron")) {
         String squadron = itemstack.func_77978_p().func_74779_i("LOTRSquadron");
         return squadron;
      } else {
         return null;
      }
   }

   public static void setSquadron(ItemStack itemstack, String squadron) {
      if (itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem) {
         if (itemstack.func_77978_p() == null) {
            itemstack.func_77982_d(new NBTTagCompound());
         }

         itemstack.func_77978_p().func_74778_a("LOTRSquadron", squadron);
      }

   }

   public interface SquadronItem {
   }
}
