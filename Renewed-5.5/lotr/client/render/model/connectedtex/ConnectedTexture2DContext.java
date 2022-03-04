package lotr.client.render.model.connectedtex;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class ConnectedTexture2DContext {
   private final EnumSet relativePositions;

   public ConnectedTexture2DContext(Collection positions) {
      this.relativePositions = EnumSet.copyOf(positions);
   }

   public boolean has(ConnectedTexture2DContext.RelativePosition pos) {
      return this.relativePositions.contains(pos);
   }

   public String toString() {
      String s = "ConnectedTexture2DContext[";
      int added = 0;
      ConnectedTexture2DContext.RelativePosition[] var3 = ConnectedTexture2DContext.RelativePosition.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ConnectedTexture2DContext.RelativePosition rPos = var3[var5];
         if (this.has(rPos)) {
            if (added > 0) {
               s = s + ", ";
            }

            s = s + rPos.name();
            ++added;
         }
      }

      s = s + "]";
      return s;
   }

   public Set getTextureElements(boolean includeBase) {
      Set set = EnumSet.noneOf(ConnectedTextureElement.class);
      if (includeBase && this.has(ConnectedTexture2DContext.RelativePosition.CENTRE)) {
         set.add(ConnectedTextureElement.BASE);
      }

      boolean topLeft = this.has(ConnectedTexture2DContext.RelativePosition.TOP_LEFT);
      boolean top = this.has(ConnectedTexture2DContext.RelativePosition.TOP);
      boolean topRight = this.has(ConnectedTexture2DContext.RelativePosition.TOP_RIGHT);
      boolean left = this.has(ConnectedTexture2DContext.RelativePosition.LEFT);
      boolean right = this.has(ConnectedTexture2DContext.RelativePosition.RIGHT);
      boolean bottomLeft = this.has(ConnectedTexture2DContext.RelativePosition.BOTTOM_LEFT);
      boolean bottom = this.has(ConnectedTexture2DContext.RelativePosition.BOTTOM);
      boolean bottomRight = this.has(ConnectedTexture2DContext.RelativePosition.BOTTOM_RIGHT);
      if (!left) {
         set.add(ConnectedTextureElement.SIDE_LEFT);
      }

      if (!right) {
         set.add(ConnectedTextureElement.SIDE_RIGHT);
      }

      if (!top) {
         set.add(ConnectedTextureElement.SIDE_TOP);
      }

      if (!bottom) {
         set.add(ConnectedTextureElement.SIDE_BOTTOM);
      }

      if (!left && !top) {
         set.add(ConnectedTextureElement.CORNER_TOPLEFT);
      }

      if (!right && !top) {
         set.add(ConnectedTextureElement.CORNER_TOPRIGHT);
      }

      if (!left && !bottom) {
         set.add(ConnectedTextureElement.CORNER_BOTTOMLEFT);
      }

      if (!right && !bottom) {
         set.add(ConnectedTextureElement.CORNER_BOTTOMRIGHT);
      }

      if (left && top && !topLeft) {
         set.add(ConnectedTextureElement.INVCORNER_TOPLEFT);
      }

      if (right && top && !topRight) {
         set.add(ConnectedTextureElement.INVCORNER_TOPRIGHT);
      }

      if (left && bottom && !bottomLeft) {
         set.add(ConnectedTextureElement.INVCORNER_BOTTOMLEFT);
      }

      if (right && bottom && !bottomRight) {
         set.add(ConnectedTextureElement.INVCORNER_BOTTOMRIGHT);
      }

      return set;
   }

   public static enum RelativePosition {
      TOP_LEFT,
      TOP,
      TOP_RIGHT,
      LEFT,
      CENTRE,
      RIGHT,
      BOTTOM_LEFT,
      BOTTOM,
      BOTTOM_RIGHT;
   }
}
