package lotr.common.entity.ai;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class LOTREntityAINPCAvoidEvilPlayer extends EntityAIBase {
   private LOTREntityNPC theNPC;
   private double farSpeed;
   private double nearSpeed;
   private Entity closestLivingEntity;
   private float distanceFromEntity;
   private PathEntity entityPathEntity;
   private PathNavigate entityPathNavigate;

   public LOTREntityAINPCAvoidEvilPlayer(LOTREntityNPC npc, float f, double d, double d1) {
      this.theNPC = npc;
      this.distanceFromEntity = f;
      this.farSpeed = d;
      this.nearSpeed = d1;
      this.entityPathNavigate = npc.func_70661_as();
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      List validPlayers = new ArrayList();
      List list = this.theNPC.field_70170_p.func_72872_a(EntityPlayer.class, this.theNPC.field_70121_D.func_72314_b((double)this.distanceFromEntity, (double)this.distanceFromEntity / 2.0D, (double)this.distanceFromEntity));
      if (list.isEmpty()) {
         return false;
      } else {
         for(int i = 0; i < list.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)list.get(i);
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction());
               if (this.theNPC.familyInfo.getAge() < 0 && alignment < 0.0F || this.theNPC instanceof LOTREntityHobbit && alignment <= -100.0F) {
                  validPlayers.add(entityplayer);
               }
            }
         }

         if (validPlayers.isEmpty()) {
            return false;
         } else {
            this.closestLivingEntity = (Entity)validPlayers.get(0);
            Vec3 fleePath = RandomPositionGenerator.func_75461_b(this.theNPC, 16, 7, Vec3.func_72443_a(this.closestLivingEntity.field_70165_t, this.closestLivingEntity.field_70163_u, this.closestLivingEntity.field_70161_v));
            if (fleePath == null) {
               return false;
            } else if (this.closestLivingEntity.func_70092_e(fleePath.field_72450_a, fleePath.field_72448_b, fleePath.field_72449_c) < this.closestLivingEntity.func_70068_e(this.theNPC)) {
               return false;
            } else {
               this.entityPathEntity = this.entityPathNavigate.func_75488_a(fleePath.field_72450_a, fleePath.field_72448_b, fleePath.field_72449_c);
               return this.entityPathEntity == null ? false : this.entityPathEntity.func_75880_b(fleePath);
            }
         }
      }
   }

   public boolean func_75253_b() {
      return !this.entityPathNavigate.func_75500_f();
   }

   public void func_75249_e() {
      this.entityPathNavigate.func_75484_a(this.entityPathEntity, this.farSpeed);
   }

   public void func_75251_c() {
      this.closestLivingEntity = null;
   }

   public void func_75246_d() {
      if (this.theNPC.func_70068_e(this.closestLivingEntity) < 49.0D) {
         this.theNPC.func_70661_as().func_75489_a(this.nearSpeed);
      } else {
         this.theNPC.func_70661_as().func_75489_a(this.farSpeed);
      }

   }
}
