package lotr.common.entity.npc;

import lotr.common.init.LOTREntities;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class IsengardWargEntity extends WargEntity {
   public IsengardWargEntity(EntityType type, World w) {
      super(type, w);
   }

   protected WargType chooseWargType() {
      if (this.field_70146_Z.nextInt(5) == 0) {
         return WargType.BLACK;
      } else {
         return this.field_70146_Z.nextInt(3) == 0 ? WargType.GREY : WargType.BROWN;
      }
   }

   protected NPCEntity createWargRider() {
      return this.field_70146_Z.nextBoolean() ? (NPCEntity)((EntityType)LOTREntities.ISENGARD_SNAGA_ARCHER.get()).func_200721_a(this.field_70170_p) : (NPCEntity)((EntityType)LOTREntities.ISENGARD_SNAGA.get()).func_200721_a(this.field_70170_p);
   }
}
