package lotr.common.datafix;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import java.util.function.BiFunction;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.NamespacedSchema;

public class LOTRDataFixes {
   private static final int VERSION_1_15_2 = 2230;
   private static final BiFunction NAMESPACED_SCHEMA_FACTORY = NamespacedSchema::new;

   public static void printCurrentVersion() {
      System.out.println(SharedConstants.func_215069_a().getWorldVersion());
   }

   public static void addFixers(DataFixerBuilder builder) {
      Schema schema11512 = builder.addSchema(2230, NAMESPACED_SCHEMA_FACTORY);
   }
}
