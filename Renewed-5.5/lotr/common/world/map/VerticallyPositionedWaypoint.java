package lotr.common.world.map;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class VerticallyPositionedWaypoint implements Waypoint {
   public abstract int getWorldY();

   public ITextComponent getCoordsText() {
      return new TranslationTextComponent("gui.lotr.map.coordsY", new Object[]{this.getWorldX(), this.getWorldY(), this.getWorldZ()});
   }

   public double getDistanceFromPlayer(PlayerEntity player) {
      Vector3d playerPos = player.func_213303_ch();
      double x = (double)this.getWorldX() + 0.5D;
      double z = (double)this.getWorldZ() + 0.5D;
      double y = (double)this.getWorldY();
      Vector3d pos = new Vector3d(x, y, z);
      return pos.func_72438_d(playerPos);
   }
}
