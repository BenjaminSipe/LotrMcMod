package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTRBoss;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityAIBossJumpAttack extends EntityAIBase {
   private World theWorld;
   private LOTREntityNPC theBoss;
   private double jumpSpeed;
   private float jumpChance;

   public LOTREntityAIBossJumpAttack(LOTREntityNPC boss, double d, float f) {
      this.theBoss = boss;
      this.theWorld = boss.field_70170_p;
      this.jumpSpeed = d;
      this.jumpChance = f;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theBoss.func_70638_az() == null) {
         return false;
      } else if (this.theBoss.bossInfo.jumpAttack) {
         return false;
      } else if (this.theBoss.func_70681_au().nextInt(20) == 0) {
         float f = ((LOTRBoss)this.theBoss).getBaseChanceModifier();
         f *= this.jumpChance;
         int enemies = this.theBoss.bossInfo.getNearbyEnemies().size();
         if ((float)enemies > 1.0F) {
            f *= (float)enemies * 4.0F;
         }

         float distance = this.theBoss.func_70032_d(this.theBoss.func_70638_az());
         distance = 8.0F - distance;
         distance /= 2.0F;
         if (distance > 1.0F) {
            f *= distance;
         }

         return this.theBoss.func_70681_au().nextFloat() < f;
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.theBoss.bossInfo.doJumpAttack(this.jumpSpeed);
   }
}
