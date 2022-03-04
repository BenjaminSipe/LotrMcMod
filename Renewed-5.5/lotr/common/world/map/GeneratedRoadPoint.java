package lotr.common.world.map;

public class GeneratedRoadPoint implements RoadPoint {
   private final Road road;
   private final double mapX;
   private final double mapZ;
   private final double worldX;
   private final double worldZ;

   public GeneratedRoadPoint(MapSettings map, Road road, double mapX, double mapZ) {
      this.road = road;
      this.mapX = mapX;
      this.mapZ = mapZ;
      this.worldX = map.mapToWorldX_frac(mapX);
      this.worldZ = map.mapToWorldZ_frac(mapZ);
   }

   public double getMapX() {
      return this.mapX;
   }

   public double getMapZ() {
      return this.mapZ;
   }

   public double getWorldX() {
      return this.worldX;
   }

   public double getWorldZ() {
      return this.worldZ;
   }
}
