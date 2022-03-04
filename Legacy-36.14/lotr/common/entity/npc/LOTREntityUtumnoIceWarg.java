package lotr.common.entity.npc;

import lotr.common.LOTRDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceWarg extends LOTREntityUtumnoWarg {
   public LOTREntityUtumnoIceWarg(World world) {
      super(world);
      this.isChilly = true;
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.setWargType(LOTREntityWarg.WargType.ICE);
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         if (entity instanceof EntityPlayerMP) {
            LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
         }

         if (entity instanceof EntityLivingBase) {
            int difficulty = this.field_70170_p.field_73013_u.func_151525_a();
            int duration = difficulty * (difficulty + 5) / 2;
            if (duration > 0) {
               ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, duration * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
