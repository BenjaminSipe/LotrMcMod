package lotr.common.entity.ai;

import java.util.List;
import lotr.common.entity.npc.IBandit;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class LOTREntityAIBanditFlee extends EntityAIBase {
   private IBandit theBandit;
   private LOTREntityNPC theBanditAsNPC;
   private double speed;
   private double range;
   private EntityPlayer targetPlayer;

   public LOTREntityAIBanditFlee(IBandit bandit, double d) {
      this.theBandit = bandit;
      this.theBanditAsNPC = this.theBandit.getBanditAsNPC();
      this.speed = d;
      this.range = this.theBanditAsNPC.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (this.theBanditAsNPC.func_70638_az() != null) {
         return false;
      } else if (this.theBandit.getBanditInventory().isEmpty()) {
         return false;
      } else {
         this.targetPlayer = this.findNearestPlayer();
         return this.targetPlayer != null;
      }
   }

   private EntityPlayer findNearestPlayer() {
      List players = this.theBanditAsNPC.field_70170_p.func_72872_a(EntityPlayer.class, this.theBanditAsNPC.field_70121_D.func_72314_b(this.range, this.range, this.range));
      double distance = this.range;
      EntityPlayer ret = null;

      for(int i = 0; i < players.size(); ++i) {
         EntityPlayer entityplayer = (EntityPlayer)players.get(i);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            double d = (double)this.theBanditAsNPC.func_70032_d(entityplayer);
            if (d < distance) {
               distance = d;
               ret = entityplayer;
            }
         }
      }

      return ret;
   }

   public void func_75246_d() {
      if (this.theBanditAsNPC.func_70661_as().func_75500_f()) {
         Vec3 away = RandomPositionGenerator.func_75461_b(this.theBanditAsNPC, (int)this.range, 10, Vec3.func_72443_a(this.targetPlayer.field_70165_t, this.targetPlayer.field_70163_u, this.targetPlayer.field_70161_v));
         if (away != null) {
            this.theBanditAsNPC.func_70661_as().func_75492_a(away.field_72450_a, away.field_72448_b, away.field_72449_c, this.speed);
         }

         this.targetPlayer = this.findNearestPlayer();
      }

   }

   public boolean func_75253_b() {
      if (this.targetPlayer != null && this.targetPlayer.func_70089_S() && !this.targetPlayer.field_71075_bZ.field_75098_d) {
         return this.theBanditAsNPC.func_70638_az() == null && this.theBanditAsNPC.func_70068_e(this.targetPlayer) < this.range * this.range;
      } else {
         return false;
      }
   }

   public void func_75251_c() {
      this.targetPlayer = null;
   }
}
