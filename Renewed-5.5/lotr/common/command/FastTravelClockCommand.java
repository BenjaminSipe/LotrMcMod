package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Iterator;
import lotr.common.data.FastTravelDataModule;
import lotr.common.util.LOTRUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FastTravelClockCommand extends LOTRBaseCommand {
   private static final int MAX_CLOCK_SECONDS = 1000000;

   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("ftclock").requires((context) -> {
         return context.func_197034_c(2);
      })).then(((RequiredArgumentBuilder)Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197056_a("seconds", IntegerArgumentType.integer(0, 1000000)).executes((context) -> {
         return setFTClock((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), IntegerArgumentType.getInteger(context, "seconds"));
      }))).then(Commands.func_197057_a("max").executes((context) -> {
         return setFTClock((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), 1000000);
      }))));
   }

   private static int setFTClock(CommandSource source, Collection players, int seconds) {
      ITextComponent hmsTime = LOTRUtil.getHMSTime_Seconds(seconds);
      int ticks = seconds * 20;
      int clocked = 0;

      for(Iterator var6 = players.iterator(); var6.hasNext(); ++clocked) {
         ServerPlayerEntity player = (ServerPlayerEntity)var6.next();
         FastTravelDataModule ftData = getPlayerData(player).getFastTravelData();
         ftData.setTimeSinceFTWithUpdate(ticks);
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.ftclock.set.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), seconds, hmsTime}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.ftclock.set.multiple", new Object[]{players.size(), seconds, hmsTime}), true);
      }

      return clocked;
   }
}
