package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import lotr.common.time.LOTRDate;
import lotr.common.time.MiddleEarthCalendar;
import lotr.common.time.ShireReckoning;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DateCommand extends LOTRBaseCommand {
   public static final int MAX_DATE = 1000000;
   public static final int MIN_DATE = -1000000;

   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("lotr_date").requires((context) -> {
         return context.func_197034_c(2);
      })).then(Commands.func_197057_a("get").executes((context) -> {
         return queryCurrentDate((CommandSource)context.getSource());
      }))).then(Commands.func_197057_a("set").then(Commands.func_197056_a("date", IntegerArgumentType.integer(-1000000, 1000000)).executes((context) -> {
         return setCurrentDate((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "date"));
      })))).then(Commands.func_197057_a("add").then(Commands.func_197056_a("date", IntegerArgumentType.integer()).executes((context) -> {
         return addToCurrentDate((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "date"));
      })))).then(Commands.func_197057_a("display").then(Commands.func_197056_a("targets", EntityArgument.func_197094_d()).executes((context) -> {
         return displayCurrentDate((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"));
      }))));
   }

   private static int queryCurrentDate(CommandSource source) {
      int date = MiddleEarthCalendar.currentDay;
      ITextComponent dateName = ShireReckoning.INSTANCE.getCurrentDateAndYearShortform();
      source.func_197030_a(new TranslationTextComponent("commands.lotr.lotr_date.get", new Object[]{date, dateName}), false);
      return date;
   }

   private static int setCurrentDate(CommandSource source, int date) {
      LOTRDate.setDate(source.func_197023_e(), date);
      ITextComponent dateName = ShireReckoning.INSTANCE.getCurrentDateAndYearShortform();
      source.func_197030_a(new TranslationTextComponent("commands.lotr.lotr_date.set", new Object[]{date, dateName}), true);
      return date;
   }

   private static int addToCurrentDate(CommandSource source, int addDate) {
      int currentDate = MiddleEarthCalendar.currentDay;
      int newDate = currentDate + addDate;
      if (newDate >= -1000000 && newDate <= 1000000) {
         LOTRDate.setDate(source.func_197023_e(), newDate);
         ITextComponent dateName = ShireReckoning.INSTANCE.getCurrentDateAndYearShortform();
         source.func_197030_a(new TranslationTextComponent("commands.lotr.lotr_date.add", new Object[]{addDate, newDate, dateName}), true);
         return newDate;
      } else {
         throw new CommandException(new TranslationTextComponent("commands.lotr.lotr_date.add.failure.outOfBounds", new Object[]{newDate}));
      }
   }

   private static int displayCurrentDate(CommandSource source, Collection players) {
      players.forEach(LOTRDate::sendDisplayPacket);
      int numPlayers = players.size();
      if (numPlayers == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.lotr_date.display.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_()}), true);
         return 1;
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.lotr_date.display.multiple", new Object[]{numPlayers}), true);
         return numPlayers;
      }
   }
}
