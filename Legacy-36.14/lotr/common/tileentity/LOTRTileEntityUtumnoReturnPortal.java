package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketUtumnoReturn;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityUtumnoReturnPortal extends TileEntity {
   public static int PORTAL_TOP = 250;
   private int beamCheck = 0;
   public int ticksExisted;

   public void func_145845_h() {
      ++this.ticksExisted;
      if (!this.field_145850_b.field_72995_K) {
         if (this.beamCheck % 20 == 0) {
            int i = this.field_145851_c;
            int k = this.field_145849_e;

            for(int j = this.field_145848_d + 1; j <= PORTAL_TOP; ++j) {
               this.field_145850_b.func_147465_d(i, j, k, LOTRMod.utumnoReturnLight, 0, 3);
            }
         }

         ++this.beamCheck;
      }

      List nearbyEntities = this.field_145850_b.func_72872_a(Entity.class, AxisAlignedBB.func_72330_a((double)this.field_145851_c, (double)this.field_145848_d, (double)this.field_145849_e, (double)(this.field_145851_c + 1), (double)PORTAL_TOP, (double)(this.field_145849_e + 1)));
      Iterator var9 = nearbyEntities.iterator();

      while(true) {
         Entity entity;
         EntityPlayer entityplayer;
         do {
            do {
               if (!var9.hasNext()) {
                  return;
               }

               Object obj = var9.next();
               entity = (Entity)obj;
            } while(LOTRMod.getNPCFaction(entity) == LOTRFaction.UTUMNO);

            if (!(entity instanceof EntityPlayer)) {
               break;
            }

            entityplayer = (EntityPlayer)entity;
         } while(entityplayer.field_71075_bZ.field_75100_b);

         if (!this.field_145850_b.field_72995_K) {
            float accel = 0.2F;
            entity.field_70181_x += (double)accel;
            entity.field_70181_x = Math.max(entity.field_70181_x, 0.0D);
            entity.func_70107_b((double)this.field_145851_c + 0.5D, entity.field_70121_D.field_72338_b, (double)this.field_145849_e + 0.5D);
            entity.field_70143_R = 0.0F;
         }

         if (entity instanceof EntityPlayer) {
            entityplayer = (EntityPlayer)entity;
            LOTRMod.proxy.setInUtumnoReturnPortal(entityplayer);
            if (entityplayer instanceof EntityPlayerMP) {
               EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
               LOTRPacketUtumnoReturn packet = new LOTRPacketUtumnoReturn(entityplayer.field_70165_t, entityplayer.field_70161_v);
               LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayermp);
               entityplayermp.field_71135_a.func_147359_a(new S12PacketEntityVelocity(entityplayer));
            }
         }

         if (!this.field_145850_b.field_72995_K && entity.field_70163_u >= (double)PORTAL_TOP - 5.0D) {
            int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
            LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
            if (entity instanceof EntityPlayerMP) {
               EntityPlayerMP entityplayer = (EntityPlayerMP)entity;
               MinecraftServer.func_71276_C().func_71203_ab().transferPlayerToDimension(entityplayer, dimension, teleporter);
               LOTRLevelData.getData((EntityPlayer)entityplayer).addAchievement(LOTRAchievement.leaveUtumno);
            } else {
               LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return INFINITE_EXTENT_AABB;
   }
}
