package lotr.common.world.map;

import net.minecraft.util.math.MathHelper;

public class MapExplorationTile {
   private final int mapX;
   private final int mapZ;
   private final int size;
   private final int distanceFromExplored;
   public static final int SEARCH_DISTANCE_FROM_EXPLORED = 2;

   public MapExplorationTile(int mapX, int mapZ, int size, int distanceFromExplored) {
      this.mapX = mapX;
      this.mapZ = mapZ;
      this.size = size;
      this.distanceFromExplored = distanceFromExplored;
   }

   public int getMapLeft() {
      return this.mapX;
   }

   public int getMapRight() {
      return this.mapX + this.size;
   }

   public int getMapTop() {
      return this.mapZ;
   }

   public int getMapBottom() {
      return this.mapZ + this.size;
   }

   public int getSize() {
      return this.size;
   }

   public int getPositionalHash() {
      return Math.abs((int)MathHelper.func_180187_c(this.mapX, 0, this.mapZ));
   }

   public boolean isThickFog() {
      if (this.distanceFromExplored <= 1) {
         return false;
      } else if (this.distanceFromExplored <= 2) {
         return this.getPositionalHash() % 8 == 1;
      } else {
         return this.getPositionalHash() % 12 != 1;
      }
   }
}
