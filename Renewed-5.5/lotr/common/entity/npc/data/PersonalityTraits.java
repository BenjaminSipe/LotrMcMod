package lotr.common.entity.npc.data;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.common.LOTRLog;
import lotr.common.data.DataUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;

public class PersonalityTraits {
   private final Set traits;

   private PersonalityTraits(Set traits) {
      this.traits = traits;
   }

   public static PersonalityTraits of(Set traits) {
      return new PersonalityTraits(traits);
   }

   public boolean hasTrait(PersonalityTrait trait) {
      return this.traits.contains(trait);
   }

   public boolean hasOppositeTrait(PersonalityTrait trait) {
      return !this.hasTrait(trait);
   }

   public String toString() {
      return String.format("PersonalityTraits[%s]", Stream.of(PersonalityTrait.values()).sorted().map((trait) -> {
         return this.hasTrait(trait) ? trait.getMainName() : trait.getOppositeName();
      }).collect(Collectors.joining(", ")));
   }

   public void save(CompoundNBT nbt) {
      nbt.func_218657_a("Traits", DataUtil.saveCollectionAsPrimitiveListNBT(this.traits, (trait) -> {
         return StringNBT.func_229705_a_(trait.getMainName());
      }));
   }

   public static PersonalityTraits load(CompoundNBT nbt) {
      EnumSet traits = EnumSet.noneOf(PersonalityTrait.class);
      DataUtil.loadCollectionFromPrimitiveListNBT(traits, nbt.func_150295_c("Traits", 8), ListNBT::func_150307_f, (name) -> {
         PersonalityTrait trait = PersonalityTrait.fromMainName(name);
         if (trait == null) {
            LOTRLog.warn("Loaded nonexistent personality trait %s", name);
         }

         return trait;
      });
      return of(traits);
   }

   public void write(PacketBuffer buf) {
      DataUtil.writeCollectionToBuffer(buf, this.traits, (trait) -> {
         buf.func_150787_b(trait.getNetworkID());
      });
   }

   public static PersonalityTraits read(PacketBuffer buf) {
      EnumSet traits = EnumSet.noneOf(PersonalityTrait.class);
      DataUtil.fillCollectionFromBuffer(buf, traits, () -> {
         int id = buf.func_150792_a();
         PersonalityTrait trait = PersonalityTrait.fromNetworkID(id);
         if (trait == null) {
            LOTRLog.warn("Received nonexistent personality trait ID %d from server", id);
         }

         return trait;
      });
      return of(traits);
   }
}
