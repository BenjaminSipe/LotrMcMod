package lotr.common.entity.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlateFallingDataStorage implements IStorage {
   public INBT writeNBT(Capability capability, PlateFallingData instance, Direction side) {
      return new CompoundNBT();
   }

   public void readNBT(Capability capability, PlateFallingData instance, Direction side, INBT nbt) {
   }
}
