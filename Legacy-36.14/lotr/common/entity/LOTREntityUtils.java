package lotr.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;

public class LOTREntityUtils {
   public static EntityAITaskEntry removeAITask(EntityCreature entity, Class taskClass) {
      int i;
      EntityAITaskEntry taskEntry;
      for(i = 0; i < entity.field_70714_bg.field_75782_a.size(); ++i) {
         taskEntry = (EntityAITaskEntry)entity.field_70714_bg.field_75782_a.get(i);
         if (taskClass.isAssignableFrom(taskEntry.field_75733_a.getClass())) {
            entity.field_70714_bg.func_85156_a(taskEntry.field_75733_a);
            return taskEntry;
         }
      }

      for(i = 0; i < entity.field_70715_bh.field_75782_a.size(); ++i) {
         taskEntry = (EntityAITaskEntry)entity.field_70715_bh.field_75782_a.get(i);
         if (taskClass.isAssignableFrom(taskEntry.field_75733_a.getClass())) {
            entity.field_70715_bh.func_85156_a(taskEntry.field_75733_a);
            return taskEntry;
         }
      }

      return null;
   }
}
