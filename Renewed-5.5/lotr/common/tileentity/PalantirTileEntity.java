package lotr.common.tileentity;

import java.util.Random;
import lotr.common.init.LOTRTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class PalantirTileEntity extends TileEntity implements ITickableTileEntity {
   private static final Random tickRand = new Random();
   private int animationTick;

   public PalantirTileEntity() {
      super((TileEntityType)LOTRTileEntities.PALANTIR.get());
      this.animationTick = tickRand.nextInt(1000);
   }

   public void func_73660_a() {
      ++this.animationTick;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }
}
