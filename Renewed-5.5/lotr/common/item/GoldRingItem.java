package lotr.common.item;

import lotr.common.data.LOTRLevelData;
import lotr.common.entity.item.RingPortalEntity;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTREntities;
import lotr.common.stat.LOTRStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.server.ServerWorld;

public class GoldRingItem extends Item {
   public GoldRingItem(Properties properties) {
      super(properties);
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity ring) {
      World world = ring.field_70170_p;
      if (!world.field_72995_K) {
         Entity thrower = ((ServerWorld)world).func_217461_a(ring.func_200214_m());
         if (ring.func_70027_ad()) {
            LOTRLevelData levelData = LOTRLevelData.serverInstance();
            if (LOTRDimensions.isDimension(world, World.field_234918_g_) && !levelData.madePortal()) {
               BlockPos portalPos = ring.func_233580_cy_();
               levelData.setMadePortal(true);
               levelData.markOverworldPortalLocation(portalPos);
               BlockPos abovePos = portalPos.func_177981_b(3);
               ring.func_70106_y();
               world.func_217385_a(thrower, (double)abovePos.func_177958_n(), (double)abovePos.func_177956_o(), (double)abovePos.func_177952_p(), 3.0F, Mode.DESTROY);
               RingPortalEntity portal = (RingPortalEntity)((EntityType)LOTREntities.RING_PORTAL.get()).func_200721_a(world);
               portal.func_174828_a(abovePos, 0.0F, 0.0F);
               world.func_217376_c(portal);
               if (thrower instanceof PlayerEntity) {
                  ((PlayerEntity)thrower).func_195066_a(LOTRStats.RING_INTO_FIRE);
               }
            }
         }
      }

      return false;
   }
}
