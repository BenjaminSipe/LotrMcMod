package lotr.common.entity.npc;

import lotr.common.init.LOTREntities;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class GundabadWargEntity extends WargEntity {
   public GundabadWargEntity(EntityType type, World w) {
      super(type, w);
   }

   protected WargType chooseWargType() {
      if (this.field_70146_Z.nextInt(500) == 0) {
         return WargType.WHITE;
      } else if (this.field_70146_Z.nextInt(24) == 0) {
         return WargType.BLACK;
      } else if (this.field_70146_Z.nextInt(10) == 0) {
         return WargType.SILVER;
      } else {
         return this.field_70146_Z.nextInt(5) == 0 ? WargType.BROWN : WargType.GREY;
      }
   }

   protected NPCEntity createWargRider() {
      return this.field_70146_Z.nextBoolean() ? (NPCEntity)((EntityType)LOTREntities.GUNDABAD_ORC_ARCHER.get()).func_200721_a(this.field_70170_p) : (NPCEntity)((EntityType)LOTREntities.GUNDABAD_ORC.get()).func_200721_a(this.field_70170_p);
   }
}
