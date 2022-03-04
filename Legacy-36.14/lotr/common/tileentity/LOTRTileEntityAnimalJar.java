package lotr.common.tileentity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.block.LOTRBlockAnimalJar;
import lotr.common.entity.AnimalJarUpdater;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LOTRTileEntityAnimalJar extends TileEntity {
   private static final int PACKET_ALL = 0;
   private static final int PACKET_ROTATE = 1;
   private NBTTagCompound jarEntityData;
   private Entity jarEntity;
   public int ticksExisted = -1;
   private float targetYaw;
   private boolean hasTargetYaw = false;

   public void func_145845_h() {
      Random rand = this.field_145850_b.field_73012_v;
      super.func_145845_h();
      if (this.ticksExisted < 0) {
         this.ticksExisted = rand.nextInt(100);
      }

      ++this.ticksExisted;
      this.getOrCreateJarEntity();
      if (this.jarEntity != null) {
         this.jarEntity.field_70173_aa = this.ticksExisted;
         this.jarEntity.field_70142_S = this.jarEntity.field_70169_q = this.jarEntity.field_70165_t;
         this.jarEntity.field_70137_T = this.jarEntity.field_70167_r = this.jarEntity.field_70163_u;
         this.jarEntity.field_70136_U = this.jarEntity.field_70166_s = this.jarEntity.field_70161_v;
         this.jarEntity.field_70126_B = this.jarEntity.field_70177_z;
         if (this.jarEntity instanceof AnimalJarUpdater) {
            ((AnimalJarUpdater)this.jarEntity).updateInAnimalJar();
         }

         if (!this.field_145850_b.field_72995_K) {
            if (this.jarEntity instanceof EntityLiving) {
               EntityLiving jarLiving = (EntityLiving)this.jarEntity;
               ++jarLiving.field_70757_a;
               if (rand.nextInt(1000) < jarLiving.field_70757_a) {
                  jarLiving.field_70757_a = -jarLiving.func_70627_aG();
                  jarLiving.func_70642_aH();
               }

               if (rand.nextInt(200) == 0) {
                  this.targetYaw = rand.nextFloat() * 360.0F;
                  this.sendJarPacket(1);
                  this.jarEntity.field_70177_z = this.targetYaw;
               }
            }
         } else if (this.hasTargetYaw) {
            float delta = this.targetYaw - this.jarEntity.field_70177_z;
            delta = MathHelper.func_76142_g(delta);
            delta *= 0.1F;
            Entity var10000 = this.jarEntity;
            var10000.field_70177_z += delta;
            if (Math.abs(this.jarEntity.field_70177_z - this.targetYaw) <= 0.01F) {
               this.hasTargetYaw = false;
            }
         }
      }

   }

   public void setEntityData(NBTTagCompound nbt) {
      this.jarEntityData = nbt;
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public void clearEntityData() {
      this.jarEntity = null;
      this.setEntityData((NBTTagCompound)null);
   }

   public NBTTagCompound getEntityData() {
      return this.jarEntityData;
   }

   public Entity getOrCreateJarEntity() {
      if (this.jarEntityData != null && !this.jarEntityData.func_82582_d()) {
         if (this.jarEntity == null) {
            this.jarEntity = EntityList.func_75615_a(this.jarEntityData, this.field_145850_b);
            this.jarEntity.field_70173_aa = this.ticksExisted;
            float[] coords = this.getInitialEntityCoords(this.jarEntity);
            this.jarEntity.func_70012_b((double)coords[0], (double)coords[1], (double)coords[2], this.jarEntity.field_70177_z, this.jarEntity.field_70125_A);
         }

         return this.jarEntity;
      } else {
         return null;
      }
   }

   private float[] getInitialEntityCoords(Entity entity) {
      float[] xyz = new float[]{(float)this.field_145851_c + 0.5F, (float)this.field_145848_d + this.getEntityHeight() - entity.field_70131_O / 2.0F, (float)this.field_145849_e + 0.5F};
      return xyz;
   }

   private float getEntityHeight() {
      Block block = this.func_145838_q();
      return block instanceof LOTRBlockAnimalJar ? ((LOTRBlockAnimalJar)block).getJarEntityHeight() : 0.5F;
   }

   public float getEntityBobbing(float f) {
      return MathHelper.func_76126_a(((float)this.ticksExisted + f) * 0.2F) * 0.05F;
   }

   public boolean isEntityWatching() {
      this.getOrCreateJarEntity();
      return this.jarEntity instanceof LOTREntityButterfly;
   }

   public int getLightValue() {
      this.getOrCreateJarEntity();
      if (this.jarEntity instanceof LOTREntityButterfly) {
         LOTREntityButterfly butterfly = (LOTREntityButterfly)this.jarEntity;
         if (butterfly.getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
            return 7;
         }
      }

      return -1;
   }

   public void func_145834_a(World world) {
      super.func_145834_a(world);
      if (this.jarEntity != null) {
         this.jarEntity = null;
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      this.getOrCreateJarEntity();
      if (this.jarEntity != null && this.jarEntityData != null) {
         this.jarEntity.func_70039_c(this.jarEntityData);
      }

      if (this.jarEntityData != null) {
         nbt.func_74782_a("JarEntityData", this.jarEntityData);
      }

   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      if (nbt.func_74764_b("JarEntityData")) {
         this.jarEntityData = nbt.func_74775_l("JarEntityData");
      } else if (nbt.func_74764_b("ButterflyData")) {
         this.jarEntityData = nbt.func_74775_l("ButterflyData");
         this.jarEntityData.func_74778_a("id", LOTREntities.getStringFromClass(LOTREntityButterfly.class));
      }

      if (this.jarEntity != null) {
         this.jarEntity.func_70020_e(this.jarEntityData);
      }

   }

   public Packet func_145844_m() {
      return this.getJarPacket(0);
   }

   private Packet getJarPacket(int type) {
      this.getOrCreateJarEntity();
      NBTTagCompound data = new NBTTagCompound();
      data.func_74774_a("JarPacketType", (byte)type);
      if (type == 0) {
         this.func_145841_b(data);
      } else if (type == 1) {
         data.func_74776_a("TargetYaw", this.targetYaw);
      }

      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   private void sendJarPacket(int type) {
      Packet packet = this.getJarPacket(type);
      int i = MathHelper.func_76128_c((double)this.field_145851_c) >> 4;
      int k = MathHelper.func_76128_c((double)this.field_145849_e) >> 4;
      PlayerManager playermanager = ((WorldServer)this.field_145850_b).func_73040_p();
      List players = this.field_145850_b.field_73010_i;
      Iterator var7 = players.iterator();

      while(var7.hasNext()) {
         Object obj = var7.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, i, k)) {
            entityplayer.field_71135_a.func_147359_a(packet);
         }
      }

   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      this.getOrCreateJarEntity();
      NBTTagCompound data = packet.func_148857_g();
      int type = 0;
      if (data.func_74764_b("JarPacketType")) {
         type = data.func_74771_c("JarPacketType");
      }

      if (type == 0) {
         this.func_145839_a(packet.func_148857_g());
      } else if (type == 1) {
         this.targetYaw = data.func_74760_g("TargetYaw");
         this.hasTargetYaw = true;
      }

      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
   }
}
