package lotr.common.item;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketLocationFX;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTRItemCommandSword extends LOTRItemSword implements LOTRSquadrons.SquadronItem {
   private static final float COMMAND_RANGE = 12.0F;
   private static final float TARGET_RANGE = 64.0F;
   private static final float TARGET_SPREAD = 6.0F;

   public LOTRItemCommandSword() {
      super(ToolMaterial.IRON);
      this.func_77656_e(0);
      this.lotrWeaponDamage = 1.0F;
   }

   public boolean func_77645_m() {
      return false;
   }

   public int func_77619_b() {
      return 0;
   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      entityplayer.func_71038_i();
      if (!world.field_72995_K) {
         Entity entity = this.getEntityTarget(entityplayer);
         if (entity != null) {
            MovingObjectPosition entityHit = new MovingObjectPosition(entity, Vec3.func_72443_a(entity.field_70165_t, entity.field_70121_D.field_72338_b + (double)(entity.field_70131_O / 2.0F), entity.field_70161_v));
            this.command(entityplayer, world, itemstack, entityHit);
         } else {
            double range = 64.0D;
            Vec3 eyePos = Vec3.func_72443_a(entityplayer.field_70165_t, entityplayer.field_70163_u + (double)entityplayer.func_70047_e(), entityplayer.field_70161_v);
            Vec3 look = entityplayer.func_70040_Z();
            Vec3 sight = eyePos.func_72441_c(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range);
            MovingObjectPosition rayTrace = world.func_147447_a(eyePos, sight, false, false, true);
            if (rayTrace != null && rayTrace.field_72313_a == MovingObjectType.BLOCK) {
               this.command(entityplayer, world, itemstack, rayTrace);
            } else {
               this.command(entityplayer, world, itemstack, (MovingObjectPosition)null);
            }
         }
      }

      return itemstack;
   }

   private Entity getEntityTarget(EntityPlayer entityplayer) {
      double range = 64.0D;
      Vec3 eyePos = Vec3.func_72443_a(entityplayer.field_70165_t, entityplayer.field_70163_u + (double)entityplayer.func_70047_e(), entityplayer.field_70161_v);
      Vec3 look = entityplayer.func_70040_Z();
      Vec3 sight = eyePos.func_72441_c(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range);
      float sightWidth = 1.0F;
      List list = entityplayer.field_70170_p.func_72839_b(entityplayer, entityplayer.field_70121_D.func_72321_a(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range).func_72314_b((double)sightWidth, (double)sightWidth, (double)sightWidth));
      Entity pointedEntity = null;
      double entityDist = range;

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = (Entity)list.get(i);
         if (entity instanceof EntityLivingBase && entity.func_70067_L()) {
            float width = 1.0F;
            AxisAlignedBB axisalignedbb = entity.field_70121_D.func_72314_b((double)width, (double)width, (double)width);
            MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(eyePos, sight);
            if (axisalignedbb.func_72318_a(eyePos)) {
               if (entityDist >= 0.0D) {
                  pointedEntity = entity;
                  entityDist = 0.0D;
               }
            } else if (movingobjectposition != null) {
               double d = eyePos.func_72438_d(movingobjectposition.field_72307_f);
               if (d < entityDist || entityDist == 0.0D) {
                  if (entity == entityplayer.field_70154_o && !entity.canRiderInteract()) {
                     if (entityDist == 0.0D) {
                        pointedEntity = entity;
                     }
                  } else {
                     pointedEntity = entity;
                     entityDist = d;
                  }
               }
            }
         }
      }

      if (pointedEntity != null) {
         return pointedEntity;
      } else {
         return null;
      }
   }

   private void command(final EntityPlayer entityplayer, World world, ItemStack itemstack, MovingObjectPosition hitTarget) {
      entityplayer.func_70604_c((EntityLivingBase)null);
      List spreadTargets = new ArrayList();
      if (hitTarget != null) {
         Vec3 vec = hitTarget.field_72307_f;
         AxisAlignedBB aabb = AxisAlignedBB.func_72330_a(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c, vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
         aabb = aabb.func_72314_b(6.0D, 6.0D, 6.0D);
         spreadTargets = world.func_82733_a(EntityLivingBase.class, aabb, new IEntitySelector() {
            public boolean func_82704_a(Entity entity) {
               return entity.func_70089_S() && LOTRMod.canPlayerAttackEntity(entityplayer, (EntityLivingBase)entity, false);
            }
         });
      }

      boolean anyAttackCommanded = false;
      List nearbyHiredUnits = world.func_72872_a(LOTREntityNPC.class, entityplayer.field_70121_D.func_72314_b(12.0D, 12.0D, 12.0D));

      for(int i = 0; i < nearbyHiredUnits.size(); ++i) {
         LOTREntityNPC npc = (LOTREntityNPC)nearbyHiredUnits.get(i);
         if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.getObeyCommandSword() && LOTRSquadrons.areSquadronsCompatible(npc, itemstack)) {
            List validTargets = new ArrayList();
            if (!((List)spreadTargets).isEmpty()) {
               Iterator var11 = ((List)spreadTargets).iterator();

               while(var11.hasNext()) {
                  Object obj = var11.next();
                  EntityLivingBase entity = (EntityLivingBase)obj;
                  if (LOTRMod.canNPCAttackEntity(npc, entity, true)) {
                     validTargets.add(entity);
                  }
               }
            }

            if (!validTargets.isEmpty()) {
               Comparator sorter = new LOTREntityAINearestAttackableTargetBasic.TargetSorter(npc);
               Collections.sort(validTargets, sorter);
               EntityLivingBase target = (EntityLivingBase)validTargets.get(0);
               npc.hiredNPCInfo.commandSwordAttack(target);
               npc.hiredNPCInfo.wasAttackCommanded = true;
               anyAttackCommanded = true;
            } else {
               npc.hiredNPCInfo.commandSwordCancel();
            }
         }
      }

      if (anyAttackCommanded && hitTarget != null) {
         Vec3 vec = hitTarget.field_72307_f;
         IMessage packet = new LOTRPacketLocationFX(LOTRPacketLocationFX.Type.SWORD_COMMAND, vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }
}
