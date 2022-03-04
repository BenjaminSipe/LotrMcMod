package lotr.common.entity;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountControl;
import lotr.common.network.LOTRPacketMountControlServerEnforce;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRMountFunctions {
   public static void setNavigatorRangeFromNPC(LOTRNPCMount mount, LOTREntityNPC npc) {
      EntityLiving entity = (EntityLiving)mount;
      double d = npc.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
      entity.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(d);
   }

   public static void update(LOTRNPCMount mount) {
      EntityLiving entity = (EntityLiving)mount;
      World world = entity.field_70170_p;
      Random rand = entity.func_70681_au();
      if (!world.field_72995_K) {
         if (rand.nextInt(900) == 0 && entity.func_70089_S()) {
            entity.func_70691_i(1.0F);
         }

         if (!(entity instanceof LOTREntityNPC)) {
            EntityLivingBase target;
            if (entity.func_70638_az() != null) {
               target = entity.func_70638_az();
               if (!target.func_70089_S() || target instanceof EntityPlayer && ((EntityPlayer)target).field_71075_bZ.field_75098_d) {
                  entity.func_70624_b((EntityLivingBase)null);
               }
            }

            if (entity.field_70153_n instanceof EntityLiving) {
               target = ((EntityLiving)entity.field_70153_n).func_70638_az();
               entity.func_70624_b(target);
            } else if (entity.field_70153_n instanceof EntityPlayer) {
               entity.func_70624_b((EntityLivingBase)null);
            }
         }
      }

   }

   public static boolean interact(LOTRNPCMount mount, EntityPlayer entityplayer) {
      EntityLiving entity = (EntityLiving)mount;
      if (mount.getBelongsToNPC() && entity.field_70153_n == null) {
         if (!entity.field_70170_p.field_72995_K) {
            entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.mountOwnedByNPC", new Object[0]));
         }

         return true;
      } else {
         return false;
      }
   }

   public static void move(LOTRNPCMount mount, float strafe, float forward) {
      EntityLiving entity = (EntityLiving)mount;
      Entity rider = entity.field_70153_n;
      if (rider != null && rider instanceof EntityPlayer && mount.isMountSaddled()) {
         entity.field_70126_B = entity.field_70177_z = rider.field_70177_z;
         entity.field_70125_A = rider.field_70125_A * 0.5F;
         entity.field_70177_z %= 360.0F;
         entity.field_70125_A %= 360.0F;
         entity.field_70759_as = entity.field_70761_aq = entity.field_70177_z;
         strafe = ((EntityLivingBase)rider).field_70702_br * 0.5F;
         forward = ((EntityLivingBase)rider).field_70701_bs;
         if (forward <= 0.0F) {
            forward *= 0.25F;
         }

         entity.field_70138_W = mount.getStepHeightWhileRiddenByPlayer();
         entity.field_70747_aH = entity.func_70689_ay() * 0.1F;
         if (canRiderControl_elseNoMotion(entity)) {
            entity.func_70659_e((float)entity.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
            mount.super_moveEntityWithHeading(strafe, forward);
         }

         entity.field_70722_aY = entity.field_70721_aZ;
         double d0 = entity.field_70165_t - entity.field_70169_q;
         double d1 = entity.field_70161_v - entity.field_70166_s;
         float f4 = MathHelper.func_76133_a(d0 * d0 + d1 * d1) * 4.0F;
         if (f4 > 1.0F) {
            f4 = 1.0F;
         }

         entity.field_70721_aZ += (f4 - entity.field_70721_aZ) * 0.4F;
         entity.field_70754_ba += entity.field_70721_aZ;
      } else {
         entity.field_70138_W = 0.5F;
         entity.field_70747_aH = 0.02F;
         mount.super_moveEntityWithHeading(strafe, forward);
      }

   }

   public static boolean sendControlToServer(EntityPlayer clientPlayer) {
      return sendControlToServer(clientPlayer, (LOTRPacketMountControlServerEnforce)null);
   }

   public static boolean isPlayerControlledMount(Entity mount) {
      return mount != null && mount.field_70153_n instanceof EntityPlayer && isMountControllable(mount) ? canRiderControl(mount) : false;
   }

   public static boolean isMountControllable(Entity mount) {
      if (mount instanceof EntityHorse && ((EntityHorse)mount).func_110248_bS()) {
         return true;
      } else {
         return mount instanceof LOTREntityNPCRideable && ((LOTREntityNPCRideable)mount).isNPCTamed();
      }
   }

   public static boolean sendControlToServer(EntityPlayer clientPlayer, LOTRPacketMountControlServerEnforce pktSet) {
      Entity mount = clientPlayer.field_70154_o;
      if (isPlayerControlledMount(mount)) {
         if (pktSet != null) {
            mount.func_70080_a(pktSet.posX, pktSet.posY, pktSet.posZ, pktSet.rotationYaw, pktSet.rotationPitch);
            mount.func_70043_V();
         }

         LOTRPacketMountControl pkt = new LOTRPacketMountControl(mount);
         LOTRPacketHandler.networkWrapper.sendToServer(pkt);
         return true;
      } else {
         return false;
      }
   }

   public static boolean canRiderControl(Entity entity) {
      Entity rider = entity.field_70153_n;
      if (rider instanceof EntityPlayer) {
         return ((EntityPlayer)rider).func_70613_aW();
      } else {
         return !entity.field_70170_p.field_72995_K;
      }
   }

   public static boolean canRiderControl_elseNoMotion(EntityLiving entity) {
      boolean flag = canRiderControl(entity);
      if (!flag && entity.field_70153_n instanceof EntityPlayer && isMountControllable(entity)) {
         entity.field_70159_w = 0.0D;
         entity.field_70181_x = 0.0D;
         entity.field_70179_y = 0.0D;
      }

      return flag;
   }
}
