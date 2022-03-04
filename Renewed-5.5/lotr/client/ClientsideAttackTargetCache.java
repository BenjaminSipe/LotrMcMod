package lotr.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lotr.common.LOTRLog;
import lotr.common.network.SPacketSetAttackTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class ClientsideAttackTargetCache {
   private static final Map attackTargetIds = new HashMap();

   public static void receivePacket(SPacketSetAttackTarget packet) {
      if (packet.getHasTarget()) {
         attackTargetIds.put(packet.getEntityId(), packet.getTargetEntityId());
      } else {
         attackTargetIds.remove(packet.getEntityId());
      }

   }

   public static void clearAll() {
      attackTargetIds.clear();
   }

   public static Optional getAttackTarget(MobEntity entity) {
      int entityId = entity.func_145782_y();
      if (attackTargetIds.containsKey(entityId)) {
         int targetEntityId = (Integer)attackTargetIds.get(entityId);
         Entity targetEntity = entity.field_70170_p.func_73045_a(targetEntityId);
         if (targetEntity instanceof LivingEntity) {
            return Optional.of((LivingEntity)targetEntity);
         }

         LOTRLog.warn("Entity %s [id %d] had an attack target with id %d in the clientside cache, but the target entity was %s (not an instanceof LivingEntity)!", entity.func_200200_C_().getString(), entityId, targetEntityId, targetEntity == null ? null : targetEntity.func_200200_C_().getString());
      }

      return Optional.empty();
   }
}
