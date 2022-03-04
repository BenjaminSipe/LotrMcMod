package lotr.common.init;

import net.minecraft.item.Food;
import net.minecraft.item.Food.Builder;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class LOTRFoods {
   public static final Food BLUBBER = (new Builder()).func_221456_a(2).func_221454_a(0.4F).func_221451_a().func_221453_d();
   public static final Food CHERRY = (new Builder()).func_221456_a(2).func_221454_a(0.2F).func_221453_d();
   public static final Food CRAM = (new Builder()).func_221456_a(8).func_221454_a(1.0F).func_221453_d();
   public static final Food FISH_AND_CHIPS = (new Builder()).func_221456_a(12).func_221454_a(1.2F).func_221453_d();
   public static final Food GAMMON = (new Builder()).func_221456_a(8).func_221454_a(0.8F).func_221451_a().func_221453_d();
   public static final Food LEMBAS = (new Builder()).func_221456_a(20).func_221454_a(1.5F).func_221453_d();
   public static final Food LETTUCE = (new Builder()).func_221456_a(3).func_221454_a(0.4F).func_221453_d();
   public static final Food MAGGOTY_BREAD = (new Builder()).func_221456_a(4).func_221454_a(0.5F).func_221453_d();
   public static final Food MALLORN_NUT = (new Builder()).func_221456_a(4).func_221454_a(0.4F).func_221453_d();
   public static final Food MAN_FLESH = (new Builder()).func_221456_a(6).func_221454_a(0.6F).func_221451_a().func_221453_d();
   public static final Food MAPLE_SYRUP = (new Builder()).func_221456_a(2).func_221454_a(0.1F).func_221453_d();
   public static final Food MIRK_SHROOM = (new Builder()).func_221456_a(3).func_221454_a(0.3F).effect(() -> {
      return new EffectInstance(Effects.field_76436_u, 100, 0);
   }, 0.3F).func_221453_d();
   public static final Food MORGUL_SHROOM = (new Builder()).func_221456_a(4).func_221454_a(0.4F).func_221453_d();
   public static final Food PEAR = (new Builder()).func_221456_a(4).func_221454_a(0.3F).func_221453_d();
   public static final Food SUSPICIOUS_MEAT = (new Builder()).func_221456_a(7).func_221454_a(0.6F).func_221451_a().func_221453_d();
}
