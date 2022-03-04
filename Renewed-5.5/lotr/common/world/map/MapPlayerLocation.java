package lotr.common.world.map;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;

public class MapPlayerLocation {
   public final GameProfile profile;
   public final double posX;
   public final double posZ;

   public MapPlayerLocation(GameProfile p, double x, double z) {
      this.profile = p;
      this.posX = x;
      this.posZ = z;
   }

   public static MapPlayerLocation ofPlayer(PlayerEntity player) {
      return new MapPlayerLocation(player.func_146103_bH(), player.func_226277_ct_(), player.func_226281_cx_());
   }

   public MapPlayerLocation withFullProfile(GameProfile fullProfile) {
      return new MapPlayerLocation(fullProfile, this.posX, this.posZ);
   }
}
