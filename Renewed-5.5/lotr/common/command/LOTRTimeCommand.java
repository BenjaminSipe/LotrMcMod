package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lotr.common.command.arguments.LOTRTimeArgument;
import lotr.common.time.LOTRTime;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class LOTRTimeCommand {
   private static final int SUNRISE = 1680;
   private static final int NOON = 12000;
   private static final int SUNSET = 25920;
   private static final int MIDNIGHT = 36000;

   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("lotr_time").requires((context) -> {
         return context.func_197034_c(2);
      })).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("set").then(Commands.func_197057_a("day").executes((context) -> {
         return setTime((CommandSource)context.getSource(), 1680);
      }))).then(Commands.func_197057_a("noon").executes((context) -> {
         return setTime((CommandSource)context.getSource(), 12000);
      }))).then(Commands.func_197057_a("night").executes((context) -> {
         return setTime((CommandSource)context.getSource(), 25920);
      }))).then(Commands.func_197057_a("midnight").executes((context) -> {
         return setTime((CommandSource)context.getSource(), 36000);
      }))).then(Commands.func_197056_a("time", LOTRTimeArgument.create()).executes((context) -> {
         return setTime((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "time"));
      })))).then(Commands.func_197057_a("add").then(Commands.func_197056_a("time", LOTRTimeArgument.create()).executes((context) -> {
         return addTime((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "time"));
      })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("query").then(Commands.func_197057_a("daytime").executes((context) -> {
         return sendQueryResults((CommandSource)context.getSource(), getModuloDayTime(((CommandSource)context.getSource()).func_197023_e()));
      }))).then(Commands.func_197057_a("gametime").executes((context) -> {
         return sendQueryResults((CommandSource)context.getSource(), (int)(((CommandSource)context.getSource()).func_197023_e().func_82737_E() % 2147483647L));
      }))).then(Commands.func_197057_a("day").executes((context) -> {
         return sendQueryResults((CommandSource)context.getSource(), (int)(getTotalDayTime(((CommandSource)context.getSource()).func_197023_e()) / 48000L % 2147483647L));
      }))));
   }

   private static long getTotalDayTime(ServerWorld world) {
      return LOTRTime.getWorldTime(world);
   }

   private static int getModuloDayTime(ServerWorld world) {
      return (int)(getTotalDayTime(world) % 48000L);
   }

   private static int sendQueryResults(CommandSource source, int time) {
      source.func_197030_a(new TranslationTextComponent("commands.time.query", new Object[]{time}), false);
      return time;
   }

   public static int setTime(CommandSource source, int time) {
      LOTRTime.setWorldTime(source.func_197023_e(), (long)time);
      source.func_197030_a(new TranslationTextComponent("commands.time.set", new Object[]{time}), true);
      return getModuloDayTime(source.func_197023_e());
   }

   public static int addTime(CommandSource source, int amount) {
      LOTRTime.addWorldTime(source.func_197023_e(), (long)amount);
      int i = getModuloDayTime(source.func_197023_e());
      source.func_197030_a(new TranslationTextComponent("commands.time.set", new Object[]{i}), true);
      return i;
   }
}
