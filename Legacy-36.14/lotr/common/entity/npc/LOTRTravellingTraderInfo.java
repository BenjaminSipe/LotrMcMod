package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

public class LOTRTravellingTraderInfo {
   private LOTREntityNPC theEntity;
   private LOTRTravellingTrader theTrader;
   public int timeUntilDespawn = -1;
   private List escortUUIDs = new ArrayList();

   public LOTRTravellingTraderInfo(LOTRTravellingTrader entity) {
      this.theEntity = (LOTREntityNPC)entity;
      this.theTrader = entity;
   }

   public void startVisiting(EntityPlayer entityplayer) {
      this.timeUntilDespawn = 24000;
      IChatComponent componentName;
      if (this.theEntity.field_70170_p.field_73010_i.size() <= 1) {
         componentName = this.theEntity.func_145748_c_();
         componentName.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
         LOTRSpeech.messageAllPlayers(new ChatComponentTranslation("lotr.travellingTrader.arrive", new Object[]{componentName}));
      } else {
         componentName = this.theEntity.func_145748_c_();
         componentName.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
         LOTRSpeech.messageAllPlayersInWorld(this.theEntity.field_70170_p, new ChatComponentTranslation("lotr.travellingTrader.arriveMP", new Object[]{componentName, entityplayer.func_70005_c_()}));
      }

      int i = MathHelper.func_76128_c(this.theEntity.field_70165_t);
      int j = MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.theEntity.field_70161_v);
      this.theEntity.func_110171_b(i, j, k, 16);
      int escorts = 2 + this.theEntity.field_70170_p.field_73012_v.nextInt(3);

      for(int l = 0; l < escorts; ++l) {
         LOTREntityNPC escort = this.theTrader.createTravellingEscort();
         if (escort != null) {
            escort.func_70012_b(this.theEntity.field_70165_t, this.theEntity.field_70163_u, this.theEntity.field_70161_v, this.theEntity.field_70177_z, this.theEntity.field_70125_A);
            escort.isNPCPersistent = true;
            escort.spawnRidingHorse = false;
            escort.func_110161_a((IEntityLivingData)null);
            this.theEntity.field_70170_p.func_72838_d(escort);
            escort.func_110171_b(i, j, k, 16);
            escort.isTraderEscort = true;
            this.escortUUIDs.add(escort.func_110124_au());
         }
      }

   }

   private void removeEscorts() {
      Iterator var1 = this.theEntity.field_70170_p.field_72996_f.iterator();

      while(var1.hasNext()) {
         Object obj = var1.next();
         Entity entity = (Entity)obj;
         UUID entityUUID = entity.func_110124_au();
         Iterator var5 = this.escortUUIDs.iterator();

         while(var5.hasNext()) {
            Object uuid = var5.next();
            if (entityUUID.equals(uuid)) {
               entity.func_70106_y();
            }
         }
      }

   }

   public void onUpdate() {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         if (this.timeUntilDespawn > 0) {
            --this.timeUntilDespawn;
         }

         if (this.timeUntilDespawn == 2400) {
            Iterator var1 = this.theEntity.field_70170_p.field_73010_i.iterator();

            while(var1.hasNext()) {
               Object player = var1.next();
               LOTRSpeech.sendSpeechBankWithChatMsg((EntityPlayer)player, this.theEntity, this.theTrader.getDepartureSpeech());
            }
         }

         if (this.timeUntilDespawn == 0) {
            IChatComponent componentName = this.theEntity.func_145748_c_();
            componentName.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            LOTRSpeech.messageAllPlayersInWorld(this.theEntity.field_70170_p, new ChatComponentTranslation("lotr.travellingTrader.depart", new Object[]{componentName}));
            this.theEntity.func_70106_y();
            this.removeEscorts();
         }
      }

   }

   public void onDeath() {
      if (!this.theEntity.field_70170_p.field_72995_K && this.timeUntilDespawn >= 0) {
         LOTRSpeech.messageAllPlayers(this.theEntity.func_110142_aN().func_151521_b());
         this.removeEscorts();
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      nbt.func_74768_a("DespawnTime", this.timeUntilDespawn);
      NBTTagList escortTags = new NBTTagList();
      Iterator i = this.escortUUIDs.iterator();

      while(i.hasNext()) {
         Object obj = i.next();
         if (obj instanceof UUID) {
            NBTTagCompound escortData = new NBTTagCompound();
            escortData.func_74772_a("UUIDMost", ((UUID)obj).getMostSignificantBits());
            escortData.func_74772_a("UUIDLeast", ((UUID)obj).getLeastSignificantBits());
            escortTags.func_74742_a(escortData);
         }
      }

      nbt.func_74782_a("Escorts", escortTags);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      this.timeUntilDespawn = nbt.func_74762_e("DespawnTime");
      this.escortUUIDs.clear();
      NBTTagList tags = nbt.func_150295_c("Escorts", 10);
      if (tags != null) {
         for(int i = 0; i < tags.func_74745_c(); ++i) {
            NBTTagCompound escortData = tags.func_150305_b(i);
            this.escortUUIDs.add(new UUID(escortData.func_74763_f("UUIDMost"), escortData.func_74763_f("UUIDLeast")));
         }
      }

   }
}
