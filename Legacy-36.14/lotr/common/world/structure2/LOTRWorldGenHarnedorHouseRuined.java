package lotr.common.world.structure2;

public class LOTRWorldGenHarnedorHouseRuined extends LOTRWorldGenHarnedorHouse {
   public LOTRWorldGenHarnedorHouseRuined(boolean flag) {
      super(flag);
   }

   protected boolean isRuined() {
      return true;
   }
}
