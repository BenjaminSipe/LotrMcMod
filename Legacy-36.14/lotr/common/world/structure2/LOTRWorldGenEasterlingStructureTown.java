package lotr.common.world.structure2;

public abstract class LOTRWorldGenEasterlingStructureTown extends LOTRWorldGenEasterlingStructure {
   public LOTRWorldGenEasterlingStructureTown(boolean flag) {
      super(flag);
   }

   protected boolean useTownBlocks() {
      return true;
   }
}
