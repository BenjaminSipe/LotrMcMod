package lotr.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class PocketMatchItem extends Item {
   public PocketMatchItem(Properties properties) {
      super(properties);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      World world = context.func_195991_k();
      ItemStack itemstack = context.func_195996_i();
      ItemStack proxyItem = new ItemStack(Items.field_151033_d);
      ItemUseContext proxyContext = new PocketMatchItem.ProxyItemUse(world, context.func_195999_j(), context.func_221531_n(), proxyItem, new BlockRayTraceResult(context.func_221532_j(), context.func_196000_l(), context.func_195995_a(), context.func_221533_k()));
      if (proxyItem.func_196084_a(proxyContext) == ActionResultType.SUCCESS) {
         itemstack.func_190918_g(1);
         return ActionResultType.SUCCESS;
      } else {
         return ActionResultType.PASS;
      }
   }

   private static class ProxyItemUse extends ItemUseContext {
      protected ProxyItemUse(World world, PlayerEntity player, Hand hand, ItemStack heldItem, BlockRayTraceResult hit) {
         super(world, player, hand, heldItem, hit);
      }
   }
}
