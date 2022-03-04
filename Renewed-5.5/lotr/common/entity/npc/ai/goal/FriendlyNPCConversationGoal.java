package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
import lotr.common.init.LOTRAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;

public class FriendlyNPCConversationGoal extends Goal {
   private final NPCEntity theEntity;
   private final double maxVerticalRange = 3.0D;
   private final float chance;
   private NPCEntity talkingTo;

   public FriendlyNPCConversationGoal(NPCEntity entity, float chanceIn) {
      this.theEntity = entity;
      this.chance = chanceIn;
      this.func_220684_a(EnumSet.of(Flag.LOOK));
   }

   private double getConversationRange() {
      return this.theEntity.func_233637_b_((Attribute)LOTRAttributes.NPC_CONVERSATION_RANGE.get());
   }

   public static boolean isAvailableForTalking(LivingEntity entity) {
      if (entity.func_70089_S()) {
         if (entity instanceof MobEntity) {
            return ((MobEntity)entity).func_70638_az() == null;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private boolean isAvailableToStartTalking(NPCEntity npc) {
      return isAvailableForTalking(npc) && npc.getTalkingToEntity() == null;
   }

   public boolean func_75250_a() {
      if (!this.isAvailableToStartTalking(this.theEntity)) {
         return false;
      } else {
         Random rand = this.theEntity.func_70681_au();
         if (rand.nextFloat() < this.chance) {
            Faction entityFac = this.theEntity.getFaction();
            double maxRange = this.getConversationRange();
            List friendlyNearbyTalkable = this.theEntity.field_70170_p.func_225316_b(NPCEntity.class, this.theEntity.func_174813_aQ().func_72314_b(maxRange, 3.0D, maxRange), (npc) -> {
               if (npc != this.theEntity && this.isAvailableToStartTalking(npc)) {
                  Faction npcFac = npc.getFaction();
                  if (npcFac == entityFac || !npc.getFaction().isBadRelation(entityFac) && rand.nextBoolean()) {
                     return this.theEntity.func_70635_at().func_75522_a(npc);
                  }
               }

               return false;
            });
            if (!friendlyNearbyTalkable.isEmpty()) {
               this.talkingTo = (NPCEntity)friendlyNearbyTalkable.get(rand.nextInt(friendlyNearbyTalkable.size()));
               return true;
            }
         }

         return false;
      }
   }

   public void func_75249_e() {
      Random rand = this.theEntity.func_70681_au();
      int time = 100 + rand.nextInt(300);
      if (rand.nextInt(30) == 0) {
         time += 400 + rand.nextInt(400);
      }

      this.theEntity.setTalkingToEntity(this.talkingTo, time);
      this.talkingTo.setTalkingToEntity(this.theEntity, time);
   }

   public void func_75251_c() {
      this.talkingTo = null;
   }
}
