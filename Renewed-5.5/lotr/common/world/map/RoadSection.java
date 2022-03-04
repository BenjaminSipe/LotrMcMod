package lotr.common.world.map;

public class RoadSection {
   private final Road road;
   private final RouteRoadPoint[] startAndEndPoints;
   private final GeneratedRoadPoint[] roadPoints;

   public RoadSection(Road road, RouteRoadPoint startPoint, RouteRoadPoint endPoint, GeneratedRoadPoint[] roadPoints) {
      this.road = road;
      this.startAndEndPoints = new RouteRoadPoint[]{startPoint, endPoint};
      this.roadPoints = roadPoints;
   }

   public RouteRoadPoint[] getStartAndEndPoints() {
      return this.startAndEndPoints;
   }

   public GeneratedRoadPoint[] getRoutePoints() {
      return this.roadPoints;
   }
}
