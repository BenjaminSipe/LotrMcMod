package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRDimension;
import lotr.common.LOTRGuiMessageTypes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class LOTRTileEntityUtumnoPortal extends TileEntity {
   public static final int WIDTH = 3;
   public static final int HEIGHT = 30;
   public static final int PORTAL_ABOVE = 2;
   public static final int PORTAL_BELOW = 2;
   public static final int TARGET_COORDINATE_RANGE = 50000;
   public static final int TARGET_FUZZ_RANGE = 32;
   private int targetX;
   private int targetZ;
   private int targetResetTick;
   private static final int targetResetTick_max = 1200;

   public void func_145845_h() {
      if (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == this.func_145838_q()) {
         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      }

      if (!this.field_145850_b.field_72995_K) {
         if (this.targetResetTick > 0) {
            --this.targetResetTick;
         } else {
            this.targetX = MathHelper.func_76136_a(this.field_145850_b.field_73012_v, -50000, 50000);
            this.targetZ = MathHelper.func_76136_a(this.field_145850_b.field_73012_v, -50000, 50000);
            this.targetResetTick = 1200;
         }
      }

      if (!this.field_145850_b.field_72995_K) {
         List players = this.field_145850_b.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)(this.field_145851_c - 8), (double)this.field_145848_d, (double)(this.field_145849_e - 8), (double)(this.field_145851_c + 9), (double)(this.field_145848_d + 60), (double)(this.field_145849_e + 9)));
         Iterator var2 = players.iterator();

         while(var2.hasNext()) {
            Object obj = var2.next();
            EntityPlayer entityplayer = (EntityPlayer)obj;
            LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.UTUMNO_WARN);
         }
      }

      if (!this.field_145850_b.field_72995_K && this.field_145850_b.field_73012_v.nextInt(2000) == 0) {
         String s = "ambient.cave.cave";
         if (this.field_145850_b.field_73012_v.nextBoolean()) {
            s = "lotr:wight.ambience";
         }

         float volume = 6.0F;
         this.field_145850_b.func_72908_a((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D, s, volume, 0.8F + this.field_145850_b.field_73012_v.nextFloat() * 0.2F);
      }

   }

   public void transferEntity(Entity entity) {
      entity.field_70143_R = 0.0F;
      if (!this.field_145850_b.field_72995_K) {
         LOTRTileEntityUtumnoPortal actingPortal = this.findActingTargetingPortal();
         int dimension = LOTRDimension.UTUMNO.dimensionID;
         LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
         teleporter.setTargetCoords(actingPortal.targetX, actingPortal.targetZ);
         if (entity instanceof EntityPlayerMP) {
            MinecraftServer.func_71276_C().func_71203_ab().transferPlayerToDimension((EntityPlayerMP)entity, dimension, teleporter);
         } else {
            LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
         }

         entity.field_70143_R = 0.0F;
         actingPortal.targetResetTick = 1200;
      }

   }

   private LOTRTileEntityUtumnoPortal findActingTargetingPortal() {
      int range = 8;

      for(int i = range; i >= -range; --i) {
         for(int k = range; k >= -range; --k) {
            int i1 = this.field_145851_c + i;
            int k1 = this.field_145849_e + k;
            int j1 = this.field_145848_d;
            if (this.field_145850_b.func_147439_a(i1, j1, k1) == this.func_145838_q()) {
               TileEntity te = this.field_145850_b.func_147438_o(i1, j1, k1);
               if (te instanceof LOTRTileEntityUtumnoPortal) {
                  return (LOTRTileEntityUtumnoPortal)te;
               }
            }
         }
      }

      return this;
   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74768_a("TargetX", this.targetX);
      nbt.func_74768_a("TargetZ", this.targetZ);
      nbt.func_74768_a("TargetReset", this.targetResetTick);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.targetX = nbt.func_74762_e("TargetX");
      this.targetZ = nbt.func_74762_e("TargetZ");
      this.targetResetTick = nbt.func_74762_e("TargetReset");
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.func_72330_a((double)(this.field_145851_c - 2), (double)this.field_145848_d, (double)(this.field_145849_e - 2), (double)(this.field_145851_c + 3), (double)(this.field_145848_d + 30), (double)(this.field_145849_e + 3));
   }

   @SideOnly(Side.CLIENT)
   public double func_145833_n() {
      double d = 256.0D;
      return d * d;
   }
}
