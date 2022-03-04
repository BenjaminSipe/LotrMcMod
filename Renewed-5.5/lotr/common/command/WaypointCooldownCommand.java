package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lotr.common.util.LOTRUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointCooldownCommand extends LOTRBaseCommand {
   private static final int MAX_COOLDOWN_SECONDS = 86400;

   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("wpcooldown").requires((context) -> {
         return context.func_197034_c(2);
      })).then(Commands.func_197057_a("max").then(Commands.func_197056_a("seconds", IntegerArgumentType.integer(0, 86400)).executes((context) -> {
         return setMaxCooldown((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "seconds"));
      })))).then(Commands.func_197057_a("min").then(Commands.func_197056_a("seconds", IntegerArgumentType.integer(0, 86400)).executes((context) -> {
         return setMinCooldown((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "seconds"));
      }))));
   }

   private static int setMaxCooldown(CommandSource source, int max) {
      int min = getLevelData().getWaypointCooldownMin();
      boolean updatedMin = false;
      if (max < min) {
         min = max;
         updatedMin = true;
      }

      getLevelData().setWaypointCooldown(source.func_197023_e(), max, min);
      source.func_197030_a(new TranslationTextComponent("commands.lotr.wpcooldown.max.set", new Object[]{max, LOTRUtil.getHMSTime_Seconds(max)}), true);
      if (updatedMin) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.wpcooldown.max.updatedMin", new Object[]{min}), true);
      }

      return 1;
   }

   private static int setMinCooldown(CommandSource source, int min) {
      int max = getLevelData().getWaypointCooldownMax();
      boolean updatedMax = false;
      if (min > max) {
         max = min;
         updatedMax = true;
      }

      getLevelData().setWaypointCooldown(source.func_197023_e(), max, min);
      source.func_197030_a(new TranslationTextComponent("commands.lotr.wpcooldown.min.set", new Object[]{max, LOTRUtil.getHMSTime_Seconds(min)}), true);
      if (updatedMax) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.wpcooldown.min.updatedMax", new Object[]{min}), true);
      }

      return 1;
   }
}
