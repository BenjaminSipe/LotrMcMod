package lotr.common.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class LOTRBlockStates {
   public static final EnumProperty SLAB_AXIS;
   public static final EnumProperty VERTICAL_ONLY_SLAB_AXIS;
   public static final BooleanProperty MOSSY;
   public static final BooleanProperty PILLAR_ABOVE;
   public static final BooleanProperty PILLAR_BELOW;
   public static final EnumProperty DRIPSTONE_TYPE;
   public static final int MAX_CLOVERS = 4;
   public static final IntegerProperty CLOVERS_1_4;
   public static final int MAX_CANDLES = 4;
   public static final IntegerProperty CANDLES_1_4;
   public static final BooleanProperty WATTLE_CONNECTED;
   public static final int TREASURE_PILE_MAX_LEVEL = 8;
   public static final IntegerProperty TREASURE_PILE_LEVEL;
   public static final BooleanProperty SUCH_WEALTH;
   public static final EnumProperty HANGING_WEB_TYPE;
   public static final BooleanProperty BRICK_ABOVE;
   public static final BooleanProperty DIRTY_CHALK_BELOW;
   public static final EnumProperty REEDS_TYPE;
   public static final BooleanProperty BEACON_FULLY_LIT;
   public static final BooleanProperty GATE_OPEN;

   static {
      SLAB_AXIS = BlockStateProperties.field_208148_A;
      VERTICAL_ONLY_SLAB_AXIS = BlockStateProperties.field_208199_z;
      MOSSY = BooleanProperty.func_177716_a("mossy");
      PILLAR_ABOVE = BooleanProperty.func_177716_a("above");
      PILLAR_BELOW = BooleanProperty.func_177716_a("below");
      DRIPSTONE_TYPE = EnumProperty.func_177709_a("type", DripstoneBlock.Type.class);
      CLOVERS_1_4 = IntegerProperty.func_177719_a("clovers", 1, 4);
      CANDLES_1_4 = IntegerProperty.func_177719_a("candles", 1, 4);
      WATTLE_CONNECTED = BooleanProperty.func_177716_a("connected");
      TREASURE_PILE_LEVEL = IntegerProperty.func_177719_a("level", 1, 8);
      SUCH_WEALTH = BooleanProperty.func_177716_a("such_wealth");
      HANGING_WEB_TYPE = EnumProperty.func_177709_a("type", HangingWebBlock.Type.class);
      BRICK_ABOVE = BooleanProperty.func_177716_a("above");
      DIRTY_CHALK_BELOW = BooleanProperty.func_177716_a("below");
      REEDS_TYPE = EnumProperty.func_177709_a("type", ReedsBlock.Type.class);
      BEACON_FULLY_LIT = BooleanProperty.func_177716_a("fully_lit");
      GATE_OPEN = BooleanProperty.func_177716_a("open");
   }
}
