package lotr.common.world.gen.feature;

public class TreeCluster {
   public final int scale;
   public final int chance;

   public static TreeCluster of(int scale, int chance) {
      return new TreeCluster(scale, chance);
   }

   private TreeCluster(int scale, int chance) {
      this.scale = scale;
      this.chance = chance;
   }
}
