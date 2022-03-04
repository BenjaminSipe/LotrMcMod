package lotr.common.init;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class LOTRDamageSources {
   public static final DamageSource PLANT = (new DamageSource("lotr.plant")).func_76348_h();
   public static final DamageSource FROST = (new DamageSource("lotr.frost")).func_76348_h();
   public static final DamageSource POISON_DRINK = (new DamageSource("lotr.poisonDrink")).func_76348_h().func_82726_p();
   public static final DamageSource STALAGMITE = (new DamageSource("lotr.stalagmite")).func_76348_h();
   public static final DamageSource QUAGMIRE = (new DamageSource("lotr.quagmire")).func_76348_h();

   public static DamageSource causeThrownSpearDamage(Entity spear, @Nullable Entity shooter) {
      return (new IndirectEntityDamageSource("lotr.thrownSpear", spear, shooter)).func_76349_b();
   }
}
