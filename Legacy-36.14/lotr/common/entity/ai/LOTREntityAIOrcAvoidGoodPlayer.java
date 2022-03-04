package lotr.common.entity.ai;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class LOTREntityAIOrcAvoidGoodPlayer extends EntityAIBase {
   private LOTREntityOrc theOrc;
   private double speed;
   private EntityLivingBase closestEnemyPlayer;
   private float distanceFromEntity;
   private PathEntity entityPathEntity;
   private PathNavigate entityPathNavigate;

   public LOTREntityAIOrcAvoidGoodPlayer(LOTREntityOrc orc, float f, double d) {
      this.theOrc = orc;
      this.distanceFromEntity = f;
      this.speed = d;
      this.entityPathNavigate = orc.func_70661_as();
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.theOrc.isWeakOrc && !this.theOrc.hiredNPCInfo.isActive) {
         if (this.theOrc.getFaction() == LOTRFaction.MORDOR) {
            return false;
         } else if (this.theOrc.currentRevengeTarget == null && !this.anyNearbyOrcsAttacked()) {
            List validPlayers = this.theOrc.field_70170_p.func_82733_a(EntityPlayer.class, this.theOrc.field_70121_D.func_72314_b((double)this.distanceFromEntity, (double)this.distanceFromEntity / 2.0D, (double)this.distanceFromEntity), new IEntitySelector() {
               public boolean func_82704_a(Entity entity) {
                  EntityPlayer entityplayer = (EntityPlayer)entity;
                  if (!entityplayer.field_71075_bZ.field_75098_d && LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.currentRevengeTarget != entityplayer) {
                     float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.getFaction());
                     return alignment <= -500.0F;
                  } else {
                     return false;
                  }
               }
            });
            if (validPlayers.isEmpty()) {
               return false;
            } else {
               this.closestEnemyPlayer = (EntityLivingBase)validPlayers.get(0);
               Vec3 fleePath = RandomPositionGenerator.func_75461_b(this.theOrc, 16, 7, Vec3.func_72443_a(this.closestEnemyPlayer.field_70165_t, this.closestEnemyPlayer.field_70163_u, this.closestEnemyPlayer.field_70161_v));
               if (fleePath == null) {
                  return false;
               } else if (this.closestEnemyPlayer.func_70092_e(fleePath.field_72450_a, fleePath.field_72448_b, fleePath.field_72449_c) < this.closestEnemyPlayer.func_70068_e(this.theOrc)) {
                  return false;
               } else {
                  this.entityPathEntity = this.entityPathNavigate.func_75488_a(fleePath.field_72450_a, fleePath.field_72448_b, fleePath.field_72449_c);
                  return this.entityPathEntity == null ? false : this.entityPathEntity.func_75880_b(fleePath);
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean anyNearbyOrcsAttacked() {
      List nearbyAllies = this.theOrc.field_70170_p.func_82733_a(EntityLiving.class, this.theOrc.field_70121_D.func_72314_b((double)this.distanceFromEntity, (double)this.distanceFromEntity / 2.0D, (double)this.distanceFromEntity), new IEntitySelector() {
         public boolean func_82704_a(Entity entity) {
            return entity != LOTREntityAIOrcAvoidGoodPlayer.this.theOrc ? LOTRMod.getNPCFaction(entity).isGoodRelation(LOTREntityAIOrcAvoidGoodPlayer.this.theOrc.getFaction()) : false;
         }
      });
      Iterator var2 = nearbyAllies.iterator();

      while(var2.hasNext()) {
         Object obj = var2.next();
         EntityLiving ally = (EntityLiving)obj;
         if (ally instanceof LOTREntityOrc) {
            if (((LOTREntityOrc)ally).currentRevengeTarget instanceof EntityPlayer) {
               return true;
            }
         } else if (ally.func_70638_az() instanceof EntityPlayer) {
            return true;
         }
      }

      return false;
   }

   public boolean func_75253_b() {
      return !this.entityPathNavigate.func_75500_f() && this.theOrc.func_70643_av() != this.closestEnemyPlayer && !this.anyNearbyOrcsAttacked();
   }

   public void func_75249_e() {
      this.entityPathNavigate.func_75484_a(this.entityPathEntity, this.speed);
   }

   public void func_75251_c() {
      this.closestEnemyPlayer = null;
   }
}
