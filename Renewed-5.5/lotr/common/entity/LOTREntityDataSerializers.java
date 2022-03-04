package lotr.common.entity;

import java.util.Optional;
import lotr.common.fac.FactionPointer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;

public class LOTREntityDataSerializers {
   public static final IDataSerializer FACTION_POINTER = new IDataSerializer() {
      public void write(PacketBuffer buf, FactionPointer value) {
         buf.func_192572_a(value.getName());
      }

      public FactionPointer read(PacketBuffer buf) {
         return FactionPointer.of(buf.func_192575_l());
      }

      public FactionPointer copyValue(FactionPointer value) {
         return value;
      }
   };
   public static final IDataSerializer OPTIONAL_FACTION_POINTER = new IDataSerializer() {
      public void write(PacketBuffer buf, Optional value) {
         buf.writeBoolean(value.isPresent());
         if (value.isPresent()) {
            buf.func_192572_a(((FactionPointer)value.get()).getName());
         }

      }

      public Optional read(PacketBuffer buf) {
         return !buf.readBoolean() ? Optional.empty() : Optional.of(FactionPointer.of(buf.func_192575_l()));
      }

      public Optional copyValue(Optional value) {
         return value;
      }
   };

   public static void register() {
      DataSerializers.func_187189_a(FACTION_POINTER);
      DataSerializers.func_187189_a(OPTIONAL_FACTION_POINTER);
   }
}
