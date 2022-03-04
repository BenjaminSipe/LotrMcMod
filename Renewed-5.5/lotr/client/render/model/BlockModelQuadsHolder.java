package lotr.client.render.model;

import java.util.List;
import java.util.Map;
import net.minecraft.util.Direction;

public class BlockModelQuadsHolder {
   public final List generalQuads;
   public final Map faceQuads;

   public BlockModelQuadsHolder(List general, Map faces) {
      this.generalQuads = general;
      this.faceQuads = faces;
   }

   public List getQuads(Direction side) {
      return side == null ? this.generalQuads : (List)this.faceQuads.get(side);
   }
}
