package lotr.common.network;

import java.util.function.Supplier;
import lotr.common.LOTRMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketSetAttackTarget {
   private final int entityId;
   private final boolean hasTarget;
   private final int targetEntityId;

   public SPacketSetAttackTarget(MobEntity entity) {
      this.entityId = entity.func_145782_y();
      LivingEntity target = entity.func_70638_az();
      this.hasTarget = target != null;
      this.targetEntityId = target != null ? target.func_145782_y() : 0;
   }

   private SPacketSetAttackTarget(int entityId, boolean hasTarget, int targetEntityId) {
      this.entityId = entityId;
      this.hasTarget = hasTarget;
      this.targetEntityId = targetEntityId;
   }

   public static void encode(SPacketSetAttackTarget packet, PacketBuffer buf) {
      buf.func_150787_b(packet.entityId);
      buf.writeBoolean(packet.hasTarget);
      if (packet.hasTarget) {
         buf.func_150787_b(packet.targetEntityId);
      }

   }

   public static SPacketSetAttackTarget decode(PacketBuffer buf) {
      int entityId = buf.func_150792_a();
      boolean hasTarget = buf.readBoolean();
      int targetEntityId = hasTarget ? buf.func_150792_a() : 0;
      return new SPacketSetAttackTarget(entityId, hasTarget, targetEntityId);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public boolean getHasTarget() {
      return this.hasTarget;
   }

   public int getTargetEntityId() {
      return this.targetEntityId;
   }

   public static void handle(SPacketSetAttackTarget packet, Supplier context) {
      LOTRMod.PROXY.receiveClientAttackTarget(packet);
      ((Context)context.get()).setPacketHandled(true);
   }
}
