package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTRItemMorgulShroom extends ItemBlock {
   public LOTRItemMorgulShroom(Block block) {
      super(block);
   }

   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      --itemstack.field_77994_a;
      if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) > 0.0F) {
         entityplayer.func_71024_bL().func_75122_a(4, 0.4F);
      } else if (!world.field_72995_K) {
         entityplayer.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 80));
      }

      if (!world.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMorgulShroom);
      }

      world.func_72956_a(entityplayer, "random.burp", 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
      return itemstack;
   }

   public int func_77626_a(ItemStack itemstack) {
      return 32;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.eat;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      if (entityplayer.func_71043_e(false)) {
         entityplayer.func_71008_a(itemstack, this.func_77626_a(itemstack));
      }

      return itemstack;
   }
}
