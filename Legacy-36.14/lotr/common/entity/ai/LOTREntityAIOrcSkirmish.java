package lotr.common.entity.ai;

import java.util.List;
import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;

public class LOTREntityAIOrcSkirmish extends LOTREntityAINearestAttackableTargetBasic {
   private LOTREntityOrc theOrc;

   public LOTREntityAIOrcSkirmish(LOTREntityOrc orc, boolean flag) {
      super(orc, LOTREntityOrc.class, 0, flag, (IEntitySelector)null);
      this.theOrc = orc;
   }

   public boolean func_75250_a() {
      if (!LOTRConfig.enableOrcSkirmish) {
         return false;
      } else if (!this.canOrcSkirmish(this.theOrc)) {
         return false;
      } else {
         if (!this.theOrc.isOrcSkirmishing()) {
            int chance = 20000;
            List nearbyOrcs = this.theOrc.field_70170_p.func_72872_a(LOTREntityOrc.class, this.theOrc.field_70121_D.func_72314_b(16.0D, 8.0D, 16.0D));

            for(int i = 0; i < nearbyOrcs.size(); ++i) {
               LOTREntityOrc orc = (LOTREntityOrc)nearbyOrcs.get(i);
               if (orc.isOrcSkirmishing()) {
                  chance /= 10;
               }
            }

            if (chance < 40) {
               chance = 40;
            }

            if (this.theOrc.func_70681_au().nextInt(chance) != 0) {
               return false;
            }
         }

         return super.func_75250_a();
      }
   }

   protected boolean func_75296_a(EntityLivingBase entity, boolean flag) {
      return this.canOrcSkirmish(entity) && super.func_75296_a(entity, flag);
   }

   private boolean canOrcSkirmish(EntityLivingBase entity) {
      if (!(entity instanceof LOTREntityOrc)) {
         return false;
      } else {
         LOTREntityOrc orc = (LOTREntityOrc)entity;
         return !orc.isTrader() && !orc.hiredNPCInfo.isActive && orc.field_70154_o == null && orc.canOrcSkirmish();
      }
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.theOrc.setOrcSkirmishing();
   }
}
