package lotr.common.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetBasic extends EntityAITarget {
   private final Class targetClass;
   private final int targetChance;
   private final LOTREntityAINearestAttackableTargetBasic.TargetSorter targetSorter;
   private final IEntitySelector targetSelector;
   private EntityLivingBase targetEntity;

   public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight) {
      this(entity, cls, chance, checkSight, false, (IEntitySelector)null);
   }

   public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, IEntitySelector selector) {
      this(entity, cls, chance, checkSight, false, selector);
   }

   public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, boolean nearby, final IEntitySelector selector) {
      super(entity, checkSight, nearby);
      this.targetClass = cls;
      this.targetChance = chance;
      this.targetSorter = new LOTREntityAINearestAttackableTargetBasic.TargetSorter(entity);
      this.func_75248_a(1);
      this.targetSelector = new IEntitySelector() {
         public boolean func_82704_a(Entity testEntity) {
            if (testEntity instanceof EntityLivingBase) {
               EntityLivingBase testEntityLiving = (EntityLivingBase)testEntity;
               return selector != null && !selector.func_82704_a(testEntityLiving) ? false : LOTREntityAINearestAttackableTargetBasic.this.func_75296_a(testEntityLiving, false);
            } else {
               return false;
            }
         }
      };
   }

   public boolean func_75250_a() {
      if (this.targetChance > 0 && this.field_75299_d.func_70681_au().nextInt(this.targetChance) != 0) {
         return false;
      } else {
         if (this.field_75299_d instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)this.field_75299_d;
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.isHalted()) {
               return false;
            }

            if (npc.func_70631_g_()) {
               return false;
            }
         }

         if (this.field_75299_d instanceof LOTREntityNPCRideable) {
            LOTREntityNPCRideable rideable = (LOTREntityNPCRideable)this.field_75299_d;
            if (rideable.isNPCTamed() || rideable.field_70153_n instanceof EntityPlayer) {
               return false;
            }
         }

         double range = this.func_111175_f();
         double rangeY = Math.min(range, 8.0D);
         List entities = this.field_75299_d.field_70170_p.func_82733_a(this.targetClass, this.field_75299_d.field_70121_D.func_72314_b(range, rangeY, range), this.targetSelector);
         Collections.sort(entities, this.targetSorter);
         if (entities.isEmpty()) {
            return false;
         } else {
            this.targetEntity = (EntityLivingBase)entities.get(0);
            return true;
         }
      }
   }

   public void func_75249_e() {
      this.field_75299_d.func_70624_b(this.targetEntity);
      super.func_75249_e();
   }

   protected boolean func_75296_a(EntityLivingBase entity, boolean flag) {
      if (entity != this.field_75299_d.field_70154_o && entity != this.field_75299_d.field_70153_n) {
         if (!super.func_75296_a(entity, flag)) {
            return false;
         } else if (entity instanceof EntityPlayer) {
            return this.isPlayerSuitableTarget((EntityPlayer)entity);
         } else if (!(entity instanceof LOTREntityBandit)) {
            return true;
         } else {
            return this.field_75299_d instanceof LOTREntityNPC && ((LOTREntityNPC)this.field_75299_d).hiredNPCInfo.isActive;
         }
      } else {
         return false;
      }
   }

   protected boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
      return this.isPlayerSuitableAlignmentTarget(entityplayer);
   }

   protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
      float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(this.field_75299_d));
      return alignment < 0.0F;
   }

   public static class TargetSorter implements Comparator {
      private final EntityLivingBase theNPC;

      public TargetSorter(EntityLivingBase entity) {
         this.theNPC = entity;
      }

      public int compare(Entity e1, Entity e2) {
         double d1 = this.distanceMetricSq(e1);
         double d2 = this.distanceMetricSq(e2);
         if (d1 < d2) {
            return -1;
         } else {
            return d1 > d2 ? 1 : 0;
         }
      }

      private double distanceMetricSq(Entity target) {
         double dSq = this.theNPC.func_70068_e(target);
         double avg = 12.0D;
         double avgSq = avg * avg;
         dSq /= avgSq;
         int dupes = 0;
         double nearRange = 8.0D;
         List nearbyEntities = this.theNPC.field_70170_p.func_72872_a(LOTREntityNPC.class, this.theNPC.field_70121_D.func_72314_b(nearRange, nearRange, nearRange));
         Iterator var12 = nearbyEntities.iterator();

         while(var12.hasNext()) {
            Object obj = var12.next();
            LOTREntityNPC nearby = (LOTREntityNPC)obj;
            if (nearby != this.theNPC && nearby.func_70089_S() && nearby.func_70638_az() == target) {
               ++dupes;
            }
         }

         int dupesSq = dupes * dupes;
         return dSq + (double)dupesSq;
      }
   }
}
