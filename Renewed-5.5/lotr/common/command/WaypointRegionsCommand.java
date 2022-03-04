package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Iterator;
import lotr.common.command.arguments.WaypointRegionArgument;
import lotr.common.data.FastTravelDataModule;
import lotr.common.world.map.WaypointRegion;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointRegionsCommand extends LOTRBaseCommand {
   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("wpregion").requires((context) -> {
         return context.func_197034_c(2);
      })).then(Commands.func_197057_a("unlock").then(((RequiredArgumentBuilder)Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197056_a("region", WaypointRegionArgument.waypointRegion()).executes((context) -> {
         return unlockRegion((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), WaypointRegionArgument.getRegion(context, "region"));
      }))).then(Commands.func_197057_a("all").executes((context) -> {
         return unlockAllRegions((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"));
      }))))).then(Commands.func_197057_a("lock").then(((RequiredArgumentBuilder)Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197056_a("region", WaypointRegionArgument.waypointRegion()).executes((context) -> {
         return lockRegion((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), WaypointRegionArgument.getRegion(context, "region"));
      }))).then(Commands.func_197057_a("all").executes((context) -> {
         return lockAllRegions((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"));
      })))));
   }

   private static int unlockRegion(CommandSource source, Collection players, WaypointRegion region) throws CommandSyntaxException {
      int unlocked = 0;
      Iterator var4 = players.iterator();

      while(var4.hasNext()) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         FastTravelDataModule ftData = getLevelData().getData(player).getFastTravelData();
         if (!ftData.isWaypointRegionUnlocked(region)) {
            ftData.unlockWaypointRegion(region);
            ++unlocked;
         }
      }

      if (unlocked == 0) {
         if (players.size() == 1) {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.unlock.failure.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), region.getName()}));
         } else {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.unlock.failure.multiple", new Object[]{players.size(), region.getName()}));
         }
      } else {
         if (players.size() == 1) {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.unlock.success.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), region.getName()}), true);
         } else {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.unlock.success.multiple", new Object[]{players.size(), region.getName()}), true);
         }

         return unlocked;
      }
   }

   private static int lockRegion(CommandSource source, Collection players, WaypointRegion region) throws CommandSyntaxException {
      int locked = 0;
      Iterator var4 = players.iterator();

      while(var4.hasNext()) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         FastTravelDataModule ftData = getLevelData().getData(player).getFastTravelData();
         if (ftData.isWaypointRegionUnlocked(region)) {
            ftData.lockWaypointRegion(region);
            ++locked;
         }
      }

      if (locked == 0) {
         if (players.size() == 1) {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.lock.failure.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), region.getName()}));
         } else {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.lock.failure.multiple", new Object[]{players.size(), region.getName()}));
         }
      } else {
         if (players.size() == 1) {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.lock.success.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), region.getName()}), true);
         } else {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.lock.success.multiple", new Object[]{players.size(), region.getName()}), true);
         }

         return locked;
      }
   }

   private static int unlockAllRegions(CommandSource source, Collection players) throws CommandSyntaxException {
      int unlocked = 0;
      Iterator var3 = players.iterator();

      while(var3.hasNext()) {
         ServerPlayerEntity player = (ServerPlayerEntity)var3.next();
         FastTravelDataModule ftData = getLevelData().getData(player).getFastTravelData();
         boolean unlockedAny = false;
         Iterator var7 = currentLoadedMap().getWaypointRegions().iterator();

         while(var7.hasNext()) {
            WaypointRegion region = (WaypointRegion)var7.next();
            if (!ftData.isWaypointRegionUnlocked(region)) {
               ftData.unlockWaypointRegion(region);
               unlockedAny = true;
            }
         }

         if (unlockedAny) {
            ++unlocked;
         }
      }

      if (unlocked == 0) {
         if (players.size() == 1) {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.unlock.all.failure.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_()}));
         } else {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.unlock.all.failure.multiple", new Object[]{players.size()}));
         }
      } else {
         if (players.size() == 1) {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.unlock.all.success.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_()}), true);
         } else {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.unlock.all.success.multiple", new Object[]{players.size()}), true);
         }

         return unlocked;
      }
   }

   private static int lockAllRegions(CommandSource source, Collection players) throws CommandSyntaxException {
      int locked = 0;
      Iterator var3 = players.iterator();

      while(var3.hasNext()) {
         ServerPlayerEntity player = (ServerPlayerEntity)var3.next();
         FastTravelDataModule ftData = getLevelData().getData(player).getFastTravelData();
         boolean lockedAny = false;
         Iterator var7 = currentLoadedMap().getWaypointRegions().iterator();

         while(var7.hasNext()) {
            WaypointRegion region = (WaypointRegion)var7.next();
            if (ftData.isWaypointRegionUnlocked(region)) {
               ftData.lockWaypointRegion(region);
               lockedAny = true;
            }
         }

         if (lockedAny) {
            ++locked;
         }
      }

      if (locked == 0) {
         if (players.size() == 1) {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.lock.all.failure.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_()}));
         } else {
            throw new CommandException(new TranslationTextComponent("commands.lotr.wpregion.lock.all.failure.multiple", new Object[]{players.size()}));
         }
      } else {
         if (players.size() == 1) {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.lock.all.success.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_()}), true);
         } else {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.wpregion.lock.all.success.multiple", new Object[]{players.size()}), true);
         }

         return locked;
      }
   }
}
