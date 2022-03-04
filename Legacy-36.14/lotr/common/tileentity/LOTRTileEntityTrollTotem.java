package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityTrollTotem extends TileEntity {
   private int prevJawRotation;
   private int jawRotation;
   private boolean prevCanSummon;
   private boolean clientCanSummon;

   public void func_145845_h() {
      if (!this.field_145850_b.field_72995_K) {
         boolean flag = this.canSummon();
         if (flag != this.prevCanSummon) {
            this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         }

         this.prevCanSummon = flag;
      } else {
         this.prevJawRotation = this.jawRotation;
         if (this.jawRotation < 60 && this.canSummon()) {
            ++this.jawRotation;
         } else if (this.jawRotation > 0 && !this.canSummon()) {
            --this.jawRotation;
         }
      }

   }

   public float getJawRotation(float f) {
      float rotation = (float)this.prevJawRotation + (float)(this.jawRotation - this.prevJawRotation) * f;
      return rotation / 60.0F * -35.0F;
   }

   public boolean canSummon() {
      if (this.field_145850_b.field_72995_K) {
         return this.clientCanSummon;
      } else if (this.field_145850_b.func_72935_r()) {
         return false;
      } else if (!this.field_145850_b.func_72937_j(this.field_145851_c, this.field_145848_d, this.field_145849_e)) {
         return false;
      } else {
         if (this.func_145838_q() == LOTRMod.trollTotem && (this.func_145832_p() & 3) == 0) {
            int rotation = (this.func_145832_p() & 12) >> 2;
            if (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == LOTRMod.trollTotem && this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == (1 | rotation << 2) && this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 2, this.field_145849_e) == LOTRMod.trollTotem && this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d - 2, this.field_145849_e) == (2 | rotation << 2)) {
               return true;
            }
         }

         return false;
      }
   }

   public void summon() {
      if (!this.field_145850_b.field_72995_K) {
         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d, this.field_145849_e);
         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d - 2, this.field_145849_e);
         LOTREntityMountainTrollChieftain troll = new LOTREntityMountainTrollChieftain(this.field_145850_b);
         troll.func_70012_b((double)this.field_145851_c + 0.5D, (double)(this.field_145848_d - 2), (double)this.field_145849_e + 0.5D, this.field_145850_b.field_73012_v.nextFloat() * 360.0F, 0.0F);
         troll.func_110161_a((IEntityLivingData)null);
         this.field_145850_b.func_72838_d(troll);
      }

   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      data.func_74757_a("CanSummon", this.canSummon());
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.clientCanSummon = data.func_74767_n("CanSummon");
   }
}
