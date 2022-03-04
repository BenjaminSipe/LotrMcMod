package lotr.common.entity.npc;

import lotr.common.init.LOTREntities;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class MordorWargEntity extends WargEntity {
   public MordorWargEntity(EntityType type, World w) {
      super(type, w);
   }

   protected WargType chooseWargType() {
      return this.field_70146_Z.nextInt(5) == 0 ? WargType.BROWN : WargType.BLACK;
   }

   protected NPCEntity createWargRider() {
      return this.field_70146_Z.nextBoolean() ? (NPCEntity)((EntityType)LOTREntities.MORDOR_ORC_ARCHER.get()).func_200721_a(this.field_70170_p) : (NPCEntity)((EntityType)LOTREntities.MORDOR_ORC.get()).func_200721_a(this.field_70170_p);
   }
}
