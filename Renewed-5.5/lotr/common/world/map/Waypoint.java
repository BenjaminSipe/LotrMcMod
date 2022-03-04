package lotr.common.world.map;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public interface Waypoint extends SelectableMapObject {
   String getRawName();

   ITextComponent getDisplayName();

   @Nullable
   ITextComponent getDisplayLore();

   @Nullable
   ITextComponent getDisplayOwnership();

   double getMapX();

   double getMapZ();

   int getWorldX();

   int getWorldZ();

   default ITextComponent getCoordsText() {
      return new TranslationTextComponent("gui.lotr.map.coords", new Object[]{this.getWorldX(), this.getWorldZ()});
   }

   default double getDistanceFromPlayer(PlayerEntity player) {
      Vector3d playerPos = player.func_213303_ch();
      double x = (double)this.getWorldX() + 0.5D;
      double z = (double)this.getWorldZ() + 0.5D;
      Vector3d pos = new Vector3d(x, playerPos.field_72448_b, z);
      return pos.func_72438_d(playerPos);
   }

   @Nullable
   BlockPos getTravelPosition(ServerWorld var1, PlayerEntity var2);

   default boolean verifyFastTravellable(ServerWorld world, PlayerEntity player) {
      return true;
   }

   boolean hasPlayerUnlocked(PlayerEntity var1);

   ITextComponent getNotUnlockedMessage(PlayerEntity var1);

   boolean isCustom();

   boolean isSharedCustom();

   boolean isSharedHidden();

   Waypoint.WaypointDisplayState getDisplayState(@Nullable PlayerEntity var1);

   WaypointNetworkType getNetworkType();

   default int getMapIconWidth() {
      return 6;
   }

   public static enum WaypointDisplayState {
      HIDDEN(0, 0),
      STANDARD(0, 200),
      STANDARD_LOCKED(6, 200),
      STANDARD_LOCKED_TO_ENEMIES(12, 200),
      STANDARD_CONQUERED(18, 200),
      CUSTOM(24, 200),
      CUSTOM_LOCKED(30, 200),
      SHARED_CUSTOM(36, 200),
      SHARED_CUSTOM_LOCKED(42, 200);

      public final int iconU;
      public final int iconV;
      public final int highlightIconU;
      public final int highlightIconV;

      private WaypointDisplayState(int u, int v) {
         this.iconU = u;
         this.iconV = v;
         this.highlightIconU = this.iconU;
         this.highlightIconV = this.iconV + 6;
      }

      public boolean isHidden() {
         return this == HIDDEN;
      }
   }
}
