package lotr.common.item;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import lotr.common.dispenser.DispenseLOTRBoat;
import lotr.common.entity.item.LOTRBoatEntity;
import lotr.common.init.LOTRItemGroups;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LOTRBoatItem extends Item {
   private static final Predicate entitySelector;
   private final LOTRBoatEntity.ModBoatType boatType;

   public LOTRBoatItem(LOTRBoatEntity.ModBoatType type) {
      super((new Properties()).func_200917_a(1).func_200916_a(LOTRItemGroups.MISC));
      this.boatType = type;
      DispenserBlock.func_199774_a(this, new DispenseLOTRBoat(this.boatType));
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      RayTraceResult target = func_219968_a(world, player, FluidMode.ANY);
      if (target.func_216346_c() == Type.MISS) {
         return ActionResult.func_226250_c_(heldItem);
      } else {
         Vector3d look = player.func_70676_i(1.0F);
         double reach = 5.0D;
         List collidedEntities = world.func_175674_a(player, player.func_174813_aQ().func_216361_a(look.func_186678_a(reach)).func_186662_g(1.0D), entitySelector);
         if (!collidedEntities.isEmpty()) {
            Vector3d eyePos = player.func_174824_e(1.0F);
            Iterator var11 = collidedEntities.iterator();

            while(var11.hasNext()) {
               Entity entity = (Entity)var11.next();
               AxisAlignedBB bb = entity.func_174813_aQ().func_186662_g((double)entity.func_70111_Y());
               if (bb.func_72318_a(eyePos)) {
                  return ActionResult.func_226250_c_(heldItem);
               }
            }
         }

         if (target.func_216346_c() == Type.BLOCK) {
            LOTRBoatEntity boat = new LOTRBoatEntity(world, target.func_216347_e().field_72450_a, target.func_216347_e().field_72448_b, target.func_216347_e().field_72449_c);
            boat.setModBoatType(this.boatType);
            boat.field_70177_z = player.field_70177_z;
            if (!world.func_226665_a__(boat, boat.func_174813_aQ().func_186662_g(-0.1D))) {
               return ActionResult.func_226251_d_(heldItem);
            } else {
               if (!world.field_72995_K) {
                  world.func_217376_c(boat);
                  if (!player.field_71075_bZ.field_75098_d) {
                     heldItem.func_190918_g(1);
                  }
               }

               player.func_71029_a(Stats.field_75929_E.func_199076_b(this));
               return ActionResult.func_226248_a_(heldItem);
            }
         } else {
            return ActionResult.func_226250_c_(heldItem);
         }
      }
   }

   static {
      entitySelector = EntityPredicates.field_180132_d.and(Entity::func_70067_L);
   }
}
