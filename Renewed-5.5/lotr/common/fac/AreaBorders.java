package lotr.common.fac;

public class AreaBorders {
   public final double xMin;
   public final double xMax;
   public final double zMin;
   public final double zMax;

   public AreaBorders(double xMin, double xMax, double zMin, double zMax) {
      this.xMin = xMin;
      this.xMax = xMax;
      this.zMin = zMin;
      this.zMax = zMax;
   }

   public double getXMin() {
      return this.xMin;
   }

   public double getXMax() {
      return this.xMax;
   }

   public double getZMin() {
      return this.zMin;
   }

   public double getZMax() {
      return this.zMax;
   }

   public double getXCentre() {
      return (this.xMin + this.xMax) / 2.0D;
   }

   public double getZCentre() {
      return (this.zMin + this.zMax) / 2.0D;
   }

   public double getWidth() {
      return this.xMax - this.xMin;
   }

   public double getHeight() {
      return this.zMax - this.zMin;
   }
}
