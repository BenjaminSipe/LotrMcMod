package lotr.common.entity.npc.data;

import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiConsumer;
import lotr.common.data.DataUtil;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketNPCPersonalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class NPCPersonalInfo {
   private static final UUID DRUNK_ATTACK_BOOST_ID = UUID.fromString("ad7395ad-3452-449f-b2e0-fe35d55692a9");
   private static final AttributeModifier DRUNK_ATTACK_BOOST;
   private final NPCEntity theEntity;
   private boolean doneFirstUpdate = false;
   private boolean resendData = true;
   private int age;
   private boolean isMale;
   private String name;
   private boolean prevWasDrunk = false;
   private int timeUntilDrunkSpeech;
   private boolean clientIsDrunk;
   private PersonalityTraits personalityTraits;

   public NPCPersonalInfo(NPCEntity npc) {
      this.theEntity = npc;
   }

   public int getAge() {
      return this.age;
   }

   public void setAge(int i) {
      this.age = i;
      this.markDirty();
   }

   public boolean isChild() {
      return this.age < 0;
   }

   public boolean isMale() {
      return this.isMale;
   }

   public boolean isFemale() {
      return !this.isMale;
   }

   public void setMale(boolean flag) {
      this.isMale = flag;
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
      return !this.theEntity.field_70170_p.field_72995_K ? this.theEntity.func_70644_a(Effects.field_76431_k) : this.clientIsDrunk;
   }

   private void updateDrunkEffects() {
      boolean isDrunk = this.isDrunk();
      if (isDrunk != this.prevWasDrunk) {
         ModifiableAttributeInstance attrib = this.theEntity.func_110148_a(Attributes.field_233823_f_);
         attrib.func_188479_b(DRUNK_ATTACK_BOOST_ID);
         if (isDrunk) {
            attrib.func_233767_b_(DRUNK_ATTACK_BOOST);
         }

         this.markDirty();
      }

      this.prevWasDrunk = isDrunk;
   }

   public PersonalityTraits getPersonalityTraits() {
      return this.personalityTraits;
   }

   public void assumeRandomPersonalityTraits(Random rand) {
      if (this.personalityTraits != null) {
         throw new IllegalStateException("Personality traits already set!");
      } else {
         EnumSet traits = EnumSet.noneOf(PersonalityTrait.class);
         PersonalityTrait[] var3 = PersonalityTrait.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PersonalityTrait trait = var3[var5];
            if (rand.nextBoolean()) {
               traits.add(trait);
            }
         }

         this.personalityTraits = PersonalityTraits.of(traits);
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

   public void tick() {
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

         this.updateDrunkEffects();
         if (this.isDrunk()) {
         }
      }

   }

   public void write(CompoundNBT nbt) {
      nbt.func_74768_a("NPCAge", this.getAge());
      nbt.func_74757_a("NPCMale", this.isMale());
      if (this.getName() != null) {
         nbt.func_74778_a("NPCName", this.getName());
      }

      if (this.personalityTraits != null) {
         CompoundNBT personalityNbt = new CompoundNBT();
         this.personalityTraits.save(personalityNbt);
         nbt.func_218657_a("NPCPersonality", personalityNbt);
      }

   }

   public void read(CompoundNBT nbt) {
      this.setAge(nbt.func_74762_e("NPCAge"));
      if (nbt.func_74764_b("NPCMale")) {
         this.setMale(nbt.func_74767_n("NPCMale"));
      }

      if (nbt.func_74764_b("NPCName")) {
         this.setName(nbt.func_74779_i("NPCName"));
      }

      if (nbt.func_74764_b("NPCPersonality")) {
         this.personalityTraits = PersonalityTraits.load(nbt.func_74775_l("NPCPersonality"));
      }

   }

   public void sendData(ServerPlayerEntity player) {
      SPacketNPCPersonalInfo packet = new SPacketNPCPersonalInfo(this);
      LOTRPacketHandler.sendTo(packet, player);
   }

   private void sendDataToAllWatchers() {
      SPacketNPCPersonalInfo packet = new SPacketNPCPersonalInfo(this);
      LOTRPacketHandler.sendToAllTrackingEntity(packet, this.theEntity);
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.theEntity.func_145782_y());
      buf.func_150787_b(this.age);
      buf.writeBoolean(this.isMale);
      boolean hasName = this.name != null;
      buf.writeBoolean(hasName);
      if (hasName) {
         buf.func_180714_a(this.name);
      }

      buf.writeBoolean(this.isDrunk());
      DataUtil.writeNullableToBuffer(buf, this.personalityTraits, (BiConsumer)(PersonalityTraits::write));
   }

   public static void read(PacketBuffer buf, World world) {
      int entityId = buf.func_150792_a();
      Entity entity = world.func_73045_a(entityId);
      if (entity instanceof NPCEntity) {
         NPCEntity npc = (NPCEntity)entity;
         NPCPersonalInfo personalInfo = npc.getPersonalInfo();
         personalInfo.setAge(buf.func_150792_a());
         personalInfo.setMale(buf.readBoolean());
         boolean hasName = buf.readBoolean();
         personalInfo.setName(hasName ? buf.func_218666_n() : null);
         personalInfo.clientIsDrunk = buf.readBoolean();
         personalInfo.personalityTraits = (PersonalityTraits)DataUtil.readNullableFromBuffer(buf, () -> {
            return PersonalityTraits.read(buf);
         });
      }

   }

   static {
      DRUNK_ATTACK_BOOST = new AttributeModifier(DRUNK_ATTACK_BOOST_ID, "Drunk melee attack boost", 4.0D, Operation.ADDITION);
   }
}
