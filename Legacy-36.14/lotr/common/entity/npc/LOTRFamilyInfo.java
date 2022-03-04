package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.network.LOTRPacketFamilyInfo;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class LOTRFamilyInfo {
   private LOTREntityNPC theEntity;
   public Class marriageEntityClass;
   public Item marriageRing;
   public float marriageAlignmentRequired;
   public LOTRAchievement marriageAchievement;
   public int potentialMaxChildren;
   public int timeToMature;
   public int breedingDelay;
   public UUID spouseUniqueID;
   public int children;
   public int maxChildren;
   public UUID maleParentID;
   public UUID femaleParentID;
   public UUID ringGivingPlayer;
   private boolean doneFirstUpdate = false;
   private boolean resendData = true;
   private int age;
   private boolean male;
   private String name;
   private int drunkTime;
   private int timeUntilDrunkSpeech;

   public LOTRFamilyInfo(LOTREntityNPC npc) {
      this.theEntity = npc;
   }

   public int getAge() {
      return this.age;
   }

   public void setAge(int i) {
      this.age = i;
      this.markDirty();
   }

   public boolean isMale() {
      return this.male;
   }

   public void setMale(boolean flag) {
      this.male = flag;
      this.markDirty();
   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      this.name = s;
      this.markDirty();
   }

   public boolean isDrunk() {
      return this.drunkTime > 0;
   }

   public void setDrunkTime(int i) {
      boolean prevDrunk = this.isDrunk();
      this.drunkTime = i;
      if (this.isDrunk() != prevDrunk) {
         this.markDirty();
      }

   }

   private void markDirty() {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         if (this.theEntity.field_70173_aa > 0) {
            this.resendData = true;
         } else {
            this.sendDataToAllWatchers();
         }
      }

   }

   public void sendData(EntityPlayerMP entityplayer) {
      LOTRPacketFamilyInfo packet = new LOTRPacketFamilyInfo(this.theEntity.func_145782_y(), this.getAge(), this.isMale(), this.getName(), this.isDrunk());
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   private void sendDataToAllWatchers() {
      int x = MathHelper.func_76128_c(this.theEntity.field_70165_t) >> 4;
      int z = MathHelper.func_76128_c(this.theEntity.field_70161_v) >> 4;
      PlayerManager playermanager = ((WorldServer)this.theEntity.field_70170_p).func_73040_p();
      List players = this.theEntity.field_70170_p.field_73010_i;
      Iterator var5 = players.iterator();

      while(var5.hasNext()) {
         Object obj = var5.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, x, z)) {
            this.sendData(entityplayer);
         }
      }

   }

   public void receiveData(LOTRPacketFamilyInfo packet) {
      this.setAge(packet.age);
      this.setMale(packet.isMale);
      this.setName(packet.name);
      if (packet.isDrunk) {
         this.setDrunkTime(100000);
      } else {
         this.setDrunkTime(0);
      }

   }

   public void onUpdate() {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         if (!this.doneFirstUpdate) {
            this.doneFirstUpdate = true;
         }

         if (this.resendData) {
            this.sendDataToAllWatchers();
            this.resendData = false;
         }

         if (this.getAge() < 0) {
            this.setAge(this.getAge() + 1);
         } else if (this.getAge() > 0) {
            this.setAge(this.getAge() - 1);
         }

         if (this.drunkTime > 0) {
            this.setDrunkTime(this.drunkTime - 1);
         }

         if (this.isDrunk()) {
            this.theEntity.func_70690_d(new PotionEffect(Potion.field_76431_k.field_76415_H, 20));
            if (this.timeUntilDrunkSpeech > 0) {
               --this.timeUntilDrunkSpeech;
            }

            if (this.theEntity.func_70089_S() && this.theEntity.func_70638_az() == null && this.timeUntilDrunkSpeech == 0) {
               double range = 12.0D;
               List players = this.theEntity.field_70170_p.func_72872_a(EntityPlayer.class, this.theEntity.field_70121_D.func_72314_b(range, range, range));
               Iterator var4 = players.iterator();

               while(var4.hasNext()) {
                  Object obj = var4.next();
                  EntityPlayer entityplayer = (EntityPlayer)obj;
                  if (entityplayer.func_70089_S() && !entityplayer.field_71075_bZ.field_75098_d) {
                     String speechBank = this.theEntity.getSpeechBank(entityplayer);
                     if (speechBank != null && this.theEntity.func_70681_au().nextInt(3) == 0) {
                        this.theEntity.sendSpeechBank(entityplayer, speechBank);
                     }
                  }
               }

               this.timeUntilDrunkSpeech = 20 * MathHelper.func_76136_a(this.theEntity.func_70681_au(), 5, 20);
            }
         }
      }

   }

   public boolean canMarryNPC(LOTREntityNPC npc) {
      if (npc.getClass() == this.theEntity.getClass() && npc.familyInfo.spouseUniqueID == null && npc.familyInfo.getAge() == 0 && npc.func_71124_b(4) == null) {
         if (npc == this.theEntity || npc.familyInfo.isMale() == this.isMale() || this.maleParentID != null && this.maleParentID == npc.familyInfo.maleParentID || this.femaleParentID != null && this.femaleParentID == npc.familyInfo.femaleParentID) {
            return false;
         } else {
            ItemStack heldItem = npc.func_71124_b(0);
            return heldItem != null && heldItem.func_77973_b() == this.marriageRing;
         }
      } else {
         return false;
      }
   }

   public LOTREntityNPC getSpouse() {
      if (this.spouseUniqueID == null) {
         return null;
      } else {
         List list = this.theEntity.field_70170_p.func_72872_a(this.theEntity.getClass(), this.theEntity.field_70121_D.func_72314_b(16.0D, 8.0D, 16.0D));

         for(int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);
            if (entity instanceof LOTREntityNPC && entity != this.theEntity && entity.func_110124_au().equals(this.spouseUniqueID)) {
               LOTREntityNPC npc = (LOTREntityNPC)entity;
               if (npc.familyInfo.spouseUniqueID != null && this.theEntity.func_110124_au().equals(npc.familyInfo.spouseUniqueID)) {
                  return npc;
               }
            }
         }

         return null;
      }
   }

   public LOTREntityNPC getParentToFollow() {
      UUID parentToFollowID = this.isMale() ? this.maleParentID : this.femaleParentID;
      List list = this.theEntity.field_70170_p.func_72872_a(this.theEntity.getClass(), this.theEntity.field_70121_D.func_72314_b(16.0D, 8.0D, 16.0D));

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = (Entity)list.get(i);
         if (entity instanceof LOTREntityNPC && entity != this.theEntity && parentToFollowID != null && entity.func_110124_au().equals(parentToFollowID)) {
            return (LOTREntityNPC)entity;
         }
      }

      return null;
   }

   public boolean interact(EntityPlayer entityplayer) {
      if (this.theEntity.hiredNPCInfo.isActive) {
         return false;
      } else {
         ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
         if (itemstack != null && itemstack.func_77973_b() == this.marriageRing && LOTRLevelData.getData(entityplayer).getAlignment(this.theEntity.getFaction()) >= this.marriageAlignmentRequired && this.theEntity.getClass() == this.marriageEntityClass && this.getAge() == 0 && this.theEntity.func_71124_b(0) == null && this.theEntity.func_71124_b(4) == null && this.spouseUniqueID == null) {
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
               }
            }

            if (!this.theEntity.field_70170_p.field_72995_K) {
               this.theEntity.func_70062_b(0, new ItemStack(this.marriageRing));
               this.ringGivingPlayer = entityplayer.func_110124_au();
            }

            this.theEntity.isNPCPersistent = true;
            return true;
         } else {
            return false;
         }
      }
   }

   public EntityPlayer getRingGivingPlayer() {
      if (this.ringGivingPlayer != null) {
         Iterator var1 = this.theEntity.field_70170_p.field_73010_i.iterator();

         while(var1.hasNext()) {
            Object obj = var1.next();
            EntityPlayer entityplayer = (EntityPlayer)obj;
            if (entityplayer.func_110124_au().equals(this.ringGivingPlayer)) {
               return entityplayer;
            }
         }
      }

      return null;
   }

   public void setChild() {
      this.setAge(-this.timeToMature);
   }

   public void setMaxBreedingDelay() {
      float f = (float)this.breedingDelay;
      f *= 0.5F + this.theEntity.func_70681_au().nextFloat() * 0.5F;
      this.setAge((int)f);
   }

   public int getRandomMaxChildren() {
      return 1 + this.theEntity.func_70681_au().nextInt(this.potentialMaxChildren);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      nbt.func_74768_a("NPCAge", this.getAge());
      nbt.func_74757_a("NPCMale", this.isMale());
      if (this.getName() != null) {
         nbt.func_74778_a("NPCName", this.getName());
      }

      nbt.func_74768_a("NPCDrunkTime", this.drunkTime);
      if (this.spouseUniqueID != null) {
         nbt.func_74772_a("SpouseUUIDMost", this.spouseUniqueID.getMostSignificantBits());
         nbt.func_74772_a("SpouseUUIDLeast", this.spouseUniqueID.getLeastSignificantBits());
      }

      nbt.func_74768_a("Children", this.children);
      nbt.func_74768_a("MaxChildren", this.maxChildren);
      if (this.maleParentID != null) {
         nbt.func_74772_a("MaleParentUUIDMost", this.maleParentID.getMostSignificantBits());
         nbt.func_74772_a("MaleParentUUIDLeast", this.maleParentID.getLeastSignificantBits());
      }

      if (this.femaleParentID != null) {
         nbt.func_74772_a("FemaleParentUUIDMost", this.femaleParentID.getMostSignificantBits());
         nbt.func_74772_a("FemaleParentUUIDLeast", this.femaleParentID.getLeastSignificantBits());
      }

      if (this.ringGivingPlayer != null) {
         nbt.func_74772_a("RingGivingPlayerUUIDMost", this.ringGivingPlayer.getMostSignificantBits());
         nbt.func_74772_a("RingGivingPlayerUUIDLeast", this.ringGivingPlayer.getLeastSignificantBits());
      }

   }

   public void readFromNBT(NBTTagCompound nbt) {
      this.setAge(nbt.func_74762_e("NPCAge"));
      if (nbt.func_74764_b("NPCMale")) {
         this.setMale(nbt.func_74767_n("NPCMale"));
      }

      if (nbt.func_74764_b("NPCName")) {
         this.setName(nbt.func_74779_i("NPCName"));
      }

      this.setDrunkTime(nbt.func_74762_e("NPCDrunkTime"));
      if (nbt.func_74764_b("SpouseUUIDMost") && nbt.func_74764_b("SpouseUUIDLeast")) {
         this.spouseUniqueID = new UUID(nbt.func_74763_f("SpouseUUIDMost"), nbt.func_74763_f("SpouseUUIDLeast"));
      }

      this.children = nbt.func_74762_e("Children");
      this.maxChildren = nbt.func_74762_e("MaxChildren");
      if (nbt.func_74764_b("MaleParentUUIDMost") && nbt.func_74764_b("MaleParentUUIDLeast")) {
         this.maleParentID = new UUID(nbt.func_74763_f("MaleParentUUIDMost"), nbt.func_74763_f("MaleParentUUIDLeast"));
      }

      if (nbt.func_74764_b("FemaleParentUUIDMost") && nbt.func_74764_b("FemaleParentUUIDLeast")) {
         this.femaleParentID = new UUID(nbt.func_74763_f("FemaleParentUUIDMost"), nbt.func_74763_f("FemaleParentUUIDLeast"));
      }

      if (nbt.func_74764_b("RingGivingPlayer")) {
         this.ringGivingPlayer = new UUID(nbt.func_74763_f("RingGivingPlayerUUIDMost"), nbt.func_74763_f("RingGivingPlayerUUIDLeast"));
      }

   }
}
