package lotr.common.entity.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlateFallingDataProvider implements ICapabilitySerializable {
   public static final ResourceLocation KEY = new ResourceLocation("lotr", "plate_falling_data");
   @CapabilityInject(PlateFallingData.class)
   public static final Capability CAPABILITY = null;
   private LazyOptional instance;

   public PlateFallingDataProvider() {
      Capability var10001 = CAPABILITY;
      var10001.getClass();
      this.instance = LazyOptional.of(var10001::getDefaultInstance);
   }

   public LazyOptional getCapability(Capability cap, Direction side) {
      return cap == CAPABILITY ? this.instance.cast() : LazyOptional.empty();
   }

   public INBT serializeNBT() {
      return CAPABILITY.getStorage().writeNBT(CAPABILITY, this.instance.orElseThrow(() -> {
         return new IllegalArgumentException("LazyOptional must not be empty!");
      }), (Direction)null);
   }

   public void deserializeNBT(INBT nbt) {
      CAPABILITY.getStorage().readNBT(CAPABILITY, this.instance.orElseThrow(() -> {
         return new IllegalArgumentException("LazyOptional must not be empty!");
      }), (Direction)null, nbt);
   }
}
