package lotr.common;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountControl;
import lotr.common.network.LOTRPacketMountControlServerEnforce;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;

public class LOTRNetHandlerPlayServer extends NetHandlerPlayServer {
   private MinecraftServer theServer;
   private double defaultReach = -1.0D;
   private int lastAttackTime = 0;
   private double lastX;
   private double lastY;
   private double lastZ;
   private int floatingMountTick;

   public LOTRNetHandlerPlayServer(MinecraftServer server, NetworkManager nm, EntityPlayerMP entityplayer) {
      super(server, nm, entityplayer);
      this.theServer = server;
   }

   public void update() {
      this.updateAttackTime();
   }

   public void func_147358_a(C0CPacketInput packet) {
      super.func_147358_a(packet);
      float forward = packet.func_149616_d();
      float strafing = packet.func_149620_c();
      boolean jump = packet.func_149618_e();
      if (forward != 0.0F || strafing != 0.0F || jump) {
         LOTRLevelData.getData((EntityPlayer)this.field_147369_b).cancelFastTravel();
      }

   }

   public void func_147347_a(C03PacketPlayer packet) {
      super.func_147347_a(packet);
      if (!this.field_147369_b.func_70115_ae() && packet.func_149466_j()) {
         double newX = packet.func_149464_c();
         double newY = packet.func_149467_d();
         double newZ = packet.func_149472_e();
         if (newX != this.lastX || newY != this.lastY || newZ != this.lastZ) {
            LOTRLevelData.getData((EntityPlayer)this.field_147369_b).cancelFastTravel();
         }
      }

      this.lastX = this.field_147369_b.field_70165_t;
      this.lastY = this.field_147369_b.field_70163_u;
      this.lastZ = this.field_147369_b.field_70161_v;
   }

   public void processMountControl(LOTRPacketMountControl packet) {
      double x = packet.posX;
      double y = packet.posY;
      double z = packet.posZ;
      float yaw = packet.rotationYaw;
      float pitch = packet.rotationPitch;
      if (Doubles.isFinite(x) && Doubles.isFinite(y) && Doubles.isFinite(z) && Floats.isFinite(yaw) && Floats.isFinite(pitch)) {
         Entity mount = this.field_147369_b.field_70154_o;
         if (mount != null && mount != this.field_147369_b && mount.field_70153_n == this.field_147369_b && LOTRMountFunctions.isMountControllable(mount)) {
            WorldServer world = this.field_147369_b.func_71121_q();
            MinecraftServer server = world.func_73046_m();
            double d0 = mount.field_70165_t;
            double d1 = mount.field_70163_u;
            double d2 = mount.field_70161_v;
            double dx = x - d0;
            double dy = y - d1;
            double dz = z - d2;
            double speedSq = mount.field_70159_w * mount.field_70159_w + mount.field_70181_x * mount.field_70181_x + mount.field_70179_y * mount.field_70179_y;
            double distSq = dx * dx + dy * dy + dz * dz;
            if (distSq - speedSq > 150.0D && (!server.func_71264_H() || !server.func_71214_G().equals(this.field_147369_b.func_70005_c_()))) {
               LOTRPacketMountControlServerEnforce pktClient = new LOTRPacketMountControlServerEnforce(mount);
               LOTRPacketHandler.networkWrapper.sendTo(pktClient, this.field_147369_b);
               return;
            }

            double check = 0.0625D;
            boolean noCollideBeforeMove = world.func_72945_a(mount, mount.field_70121_D.func_72329_c().func_72331_e(check, check, check)).isEmpty();
            dx = x - d0;
            dy = y - d1 - 1.0E-6D;
            dz = z - d2;
            mount.func_70091_d(dx, dy, dz);
            double movedY = dy;
            dx = x - mount.field_70165_t;
            dy = y - mount.field_70163_u;
            dz = z - mount.field_70161_v;
            if (dy > -0.5D || dy < 0.5D) {
               dy = 0.0D;
            }

            distSq = dx * dx + dy * dy + dz * dz;
            boolean clientServerConflict = false;
            if (distSq > 10.0D) {
               clientServerConflict = true;
            }

            mount.func_70080_a(x, y, z, yaw, pitch);
            this.field_147369_b.func_70080_a(x, y, z, yaw, pitch);
            boolean noCollideAfterMove = world.func_72945_a(mount, mount.field_70121_D.func_72329_c().func_72331_e(check, check, check)).isEmpty();
            if (noCollideBeforeMove && (clientServerConflict || !noCollideAfterMove)) {
               mount.func_70080_a(d0, d1, d2, yaw, pitch);
               this.field_147369_b.func_70080_a(d0, d1, d2, yaw, pitch);
               LOTRPacketMountControlServerEnforce pktClient = new LOTRPacketMountControlServerEnforce(mount);
               LOTRPacketHandler.networkWrapper.sendTo(pktClient, this.field_147369_b);
               return;
            }

            AxisAlignedBB flyCheckBox = mount.field_70121_D.func_72329_c().func_72314_b(check, check, check).func_72321_a(0.0D, -0.55D, 0.0D);
            if (!server.func_71231_X() && !world.func_72829_c(flyCheckBox)) {
               if (movedY >= -0.03125D) {
                  ++this.floatingMountTick;
                  if (this.floatingMountTick > 80) {
                     FMLLog.warning(this.field_147369_b.func_70005_c_() + " was kicked for floating too long on mount " + mount.func_70005_c_() + "!", new Object[0]);
                     this.func_147360_c("Flying is not enabled on this server");
                     return;
                  }
               }
            } else {
               this.floatingMountTick = 0;
            }

            server.func_71203_ab().func_72358_d(this.field_147369_b);
            this.field_147369_b.func_71000_j(this.field_147369_b.field_70165_t - d0, this.field_147369_b.field_70163_u - d1, this.field_147369_b.field_70161_v - d2);
         }

      } else {
         this.field_147369_b.field_71135_a.func_147360_c("Invalid mount movement");
      }
   }

   public void func_147340_a(C02PacketUseEntity packet) {
      WorldServer world = this.theServer.func_71218_a(this.field_147369_b.field_71093_bK);
      Entity target = packet.func_149564_a(world);
      this.field_147369_b.func_143004_u();
      if (target != null) {
         ItemStack itemstack = this.field_147369_b.func_70694_bm();
         double reach = (double)LOTRWeaponStats.getMeleeReachDistance(this.field_147369_b);
         reach += (double)LOTRWeaponStats.getMeleeExtraLookWidth();
         reach += (double)target.func_70111_Y();
         int attackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
         if (this.field_147369_b.func_70068_e(target) < reach * reach) {
            if (packet.func_149565_c() == Action.INTERACT) {
               this.field_147369_b.func_70998_m(target);
            } else if (packet.func_149565_c() == Action.ATTACK && (this.lastAttackTime <= 0 || !(target instanceof EntityLivingBase))) {
               if (target instanceof EntityItem || target instanceof EntityXPOrb || target instanceof EntityArrow || target == this.field_147369_b) {
                  this.func_147360_c("Attempting to attack an invalid entity");
                  this.theServer.func_71236_h("Player " + this.field_147369_b.func_70005_c_() + " tried to attack an invalid entity");
                  return;
               }

               this.field_147369_b.func_71059_n(target);
               this.lastAttackTime = attackTime;
            }
         }
      }

   }

   public void updateAttackTime() {
      if (this.lastAttackTime > 0) {
         --this.lastAttackTime;
      }

   }

   public void func_147345_a(C07PacketPlayerDigging packet) {
      this.setBlockReach();
      super.func_147345_a(packet);
   }

   public void func_147346_a(C08PacketPlayerBlockPlacement packet) {
      this.setBlockReach();
      super.func_147346_a(packet);
   }

   private void setBlockReach() {
      if (this.defaultReach == -1.0D) {
         this.defaultReach = this.field_147369_b.field_71134_c.getBlockReachDistance();
      }

      double reach = this.defaultReach;
      reach *= (double)LOTRWeaponStats.getMeleeReachFactor(this.field_147369_b.func_70694_bm());
      this.field_147369_b.field_71134_c.setBlockReachDistance(reach);
   }
}
