package lotr.common.entity.ai;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHobbitChildFollowGoodPlayer extends EntityAIBase {
   private LOTREntityHobbit theHobbit;
   private EntityPlayer playerToFollow;
   private float range;
   private double speed;
   private int followDelay;

   public LOTREntityAIHobbitChildFollowGoodPlayer(LOTREntityHobbit hobbit, float f, double d) {
      this.theHobbit = hobbit;
      this.range = f;
      this.speed = d;
   }

   public boolean func_75250_a() {
      if (this.theHobbit.familyInfo.getAge() >= 0) {
         return false;
      } else {
         List list = this.theHobbit.field_70170_p.func_72872_a(EntityPlayer.class, this.theHobbit.field_70121_D.func_72314_b((double)this.range, 3.0D, (double)this.range));
         EntityPlayer entityplayer = null;
         double distanceSq = Double.MAX_VALUE;
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            EntityPlayer playerCandidate = (EntityPlayer)iterator.next();
            if (LOTRLevelData.getData(playerCandidate).getAlignment(this.theHobbit.getFaction()) >= 200.0F) {
               double d = this.theHobbit.func_70068_e(playerCandidate);
               if (d <= distanceSq) {
                  distanceSq = d;
                  entityplayer = playerCandidate;
               }
            }
         }

         if (entityplayer == null) {
            return false;
         } else if (distanceSq < 9.0D) {
            return false;
         } else {
            this.playerToFollow = entityplayer;
            return true;
         }
      }
   }

   public boolean func_75253_b() {
      if (this.playerToFollow.func_70089_S() && this.theHobbit.familyInfo.getAge() < 0) {
         double distanceSq = this.theHobbit.func_70068_e(this.playerToFollow);
         return distanceSq >= 9.0D && distanceSq <= 256.0D;
      } else {
         return false;
      }
   }

   public void func_75249_e() {
      this.followDelay = 0;
   }

   public void func_75251_c() {
      this.playerToFollow = null;
   }

   public void func_75246_d() {
      if (--this.followDelay <= 0) {
         this.followDelay = 10;
         this.theHobbit.func_70661_as().func_75497_a(this.playerToFollow, this.speed);
      }

   }
}
