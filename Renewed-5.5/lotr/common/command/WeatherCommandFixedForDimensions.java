package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;

public class WeatherCommandFixedForDimensions {
   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("weather").requires((context) -> {
         return context.func_197034_c(2);
      })).then(((LiteralArgumentBuilder)Commands.func_197057_a("clear").executes((context) -> {
         return setClear((CommandSource)context.getSource(), 6000);
      })).then(Commands.func_197056_a("duration", IntegerArgumentType.integer(0, 1000000)).executes((context) -> {
         return setClear((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "duration") * 20);
      })))).then(((LiteralArgumentBuilder)Commands.func_197057_a("rain").executes((context) -> {
         return setRain((CommandSource)context.getSource(), 6000);
      })).then(Commands.func_197056_a("duration", IntegerArgumentType.integer(0, 1000000)).executes((context) -> {
         return setRain((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "duration") * 20);
      })))).then(((LiteralArgumentBuilder)Commands.func_197057_a("thunder").executes((context) -> {
         return setThunder((CommandSource)context.getSource(), 6000);
      })).then(Commands.func_197056_a("duration", IntegerArgumentType.integer(0, 1000000)).executes((context) -> {
         return setThunder((CommandSource)context.getSource(), IntegerArgumentType.getInteger(context, "duration") * 20);
      }))));
   }

   private static IServerWorldInfo locateOverworldInfo(CommandSource source) {
      ServerWorld world = source.func_197023_e().func_73046_m().func_71218_a(World.field_234918_g_);
      return (IServerWorldInfo)world.func_72912_H();
   }

   private static int setClear(CommandSource source, int time) {
      IServerWorldInfo winfo = locateOverworldInfo(source);
      winfo.func_230391_a_(time);
      winfo.func_76080_g(0);
      winfo.func_76090_f(0);
      winfo.func_76084_b(false);
      winfo.func_76069_a(false);
      source.func_197030_a(new TranslationTextComponent("commands.weather.set.clear"), true);
      return time;
   }

   private static int setRain(CommandSource source, int time) {
      IServerWorldInfo winfo = locateOverworldInfo(source);
      winfo.func_230391_a_(0);
      winfo.func_76080_g(time);
      winfo.func_76090_f(time);
      winfo.func_76084_b(true);
      winfo.func_76069_a(false);
      source.func_197030_a(new TranslationTextComponent("commands.weather.set.rain"), true);
      return time;
   }

   private static int setThunder(CommandSource source, int time) {
      IServerWorldInfo winfo = locateOverworldInfo(source);
      winfo.func_230391_a_(0);
      winfo.func_76080_g(time);
      winfo.func_76090_f(time);
      winfo.func_76084_b(true);
      winfo.func_76069_a(true);
      source.func_197030_a(new TranslationTextComponent("commands.weather.set.thunder"), true);
      return time;
   }
}
