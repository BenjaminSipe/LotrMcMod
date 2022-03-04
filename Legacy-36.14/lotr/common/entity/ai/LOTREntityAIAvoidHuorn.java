package lotr.common.entity.ai;

import cpw.mods.fml.common.FMLLog;
import java.lang.reflect.Field;
import lotr.common.LOTRReflection;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class LOTREntityAIAvoidHuorn extends EntityAIAvoidEntity {
   public LOTREntityAIAvoidHuorn(final EntityCreature entity, float range, double near, double far) {
      super(entity, LOTREntityHuornBase.class, range, near, far);

      try {
         IEntitySelector replaceSelect = new IEntitySelector() {
            public boolean func_82704_a(Entity target) {
               if (target.func_70089_S() && entity.func_70635_at().func_75522_a(target)) {
                  LOTREntityHuornBase huorn = (LOTREntityHuornBase)target;
                  return huorn.isHuornActive();
               } else {
                  return false;
               }
            }
         };
         Field[] fs = EntityAIAvoidEntity.class.getFields();
         Field[] var9 = fs;
         int var10 = fs.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Field f = var9[var11];
            Object inst = f.get(this);
            if (inst == this.field_98218_a) {
               LOTRReflection.unlockFinalField(f);
               f.set(this, replaceSelect);
               break;
            }
         }
      } catch (Exception var14) {
         FMLLog.warning("LOTR: Error constructing Avoid Huorn AI", new Object[0]);
         var14.printStackTrace();
      }

   }
}
