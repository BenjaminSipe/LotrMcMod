package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lotr.common.command.arguments.FactionArgument;
import lotr.common.data.AlignmentDataModule;
import lotr.common.fac.Faction;
import lotr.common.util.LOTRUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PledgeCommand extends LOTRBaseCommand {
   private static final int MAX_COOLDOWN_SECONDS = 1000000;

   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("pledge").requires((context) -> {
         return context.func_197034_c(2);
      })).then(Commands.func_197057_a("set").then(Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(((RequiredArgumentBuilder)Commands.func_197056_a("faction", FactionArgument.faction()).executes((context) -> {
         return setPledge((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), FactionArgument.getFaction(context, "faction"), false);
      })).then(Commands.func_197057_a("force").executes((context) -> {
         return setPledge((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), FactionArgument.getFaction(context, "faction"), true);
      })))))).then(Commands.func_197057_a("break").then(Commands.func_197056_a("targets", EntityArgument.func_197094_d()).executes((context) -> {
         return breakPledge((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"));
      })))).then(Commands.func_197057_a("cooldown").then(Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197056_a("seconds", IntegerArgumentType.integer(0, 1000000)).executes((context) -> {
         return setPledgeCooldown((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), IntegerArgumentType.getInteger(context, "seconds"));
      }))))).then(Commands.func_197057_a("query").then(Commands.func_197056_a("target", EntityArgument.func_197096_c()).executes((context) -> {
         return queryPledge((CommandSource)context.getSource(), EntityArgument.func_197089_d(context, "target"));
      }))));
   }

   private static int setPledge(CommandSource source, Collection players, Faction faction, boolean force) throws CommandSyntaxException {
      int playersPledged = 0;
      Iterator var5 = players.iterator();

      ServerPlayerEntity player;
      label39:
      do {
         while(var5.hasNext()) {
            player = (ServerPlayerEntity)var5.next();
            AlignmentDataModule alignData = getAlignData(player);
            if (!alignData.isValidPledgeFaction(faction)) {
               throw new CommandException(new TranslationTextComponent("commands.lotr.pledge.set.failure.invalid", new Object[]{faction.getDisplayName()}));
            }

            if (!force && !alignData.canPledgeToNow(faction)) {
               continue label39;
            }

            if (alignData.getPledgeFaction() != null) {
               alignData.revokePledgeFaction(player, true);
            }

            if (force) {
               alignData.setAlignment(faction, faction.getPledgeAlignment());
            }

            alignData.setPledgeFaction(faction);
            ++playersPledged;
         }

         if (players.size() == 1) {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.set.success.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), faction.getColoredDisplayName()}), true);
         } else {
            source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.set.success.multiple", new Object[]{players.size(), faction.getColoredDisplayName()}), true);
         }

         return playersPledged;
      } while(players.size() != 1);

      throw new CommandException(new TranslationTextComponent("commands.lotr.pledge.set.failure.requirements", new Object[]{player.func_145748_c_(), faction.getDisplayName()}));
   }

   private static int breakPledge(CommandSource source, Collection players) throws CommandSyntaxException {
      int playersUnpledged = 0;
      Map brokenPledgesTo = new HashMap();
      Iterator var4 = players.iterator();

      while(var4.hasNext()) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         AlignmentDataModule alignData = getAlignData(player);
         Faction wasPledgedTo = alignData.getPledgeFaction();
         if (wasPledgedTo == null) {
            if (players.size() == 1) {
               throw new CommandException(new TranslationTextComponent("commands.lotr.pledge.break.failure.none", new Object[]{player.func_145748_c_()}));
            }
         } else {
            brokenPledgesTo.put(player, wasPledgedTo);
            alignData.revokePledgeFaction(player, true);
            ++playersUnpledged;
         }
      }

      if (players.size() == 1) {
         ServerPlayerEntity onePlayer = (ServerPlayerEntity)players.iterator().next();
         source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.break.success.single", new Object[]{onePlayer.func_145748_c_(), ((Faction)brokenPledgesTo.get(onePlayer)).getColoredDisplayName()}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.break.success.multiple", new Object[]{players.size()}), true);
      }

      return playersUnpledged;
   }

   private static int setPledgeCooldown(CommandSource source, Collection players, int seconds) {
      ITextComponent hmsTime = LOTRUtil.getHMSTime_Seconds(seconds);
      int ticks = seconds * 20;
      int cooldownsSet = 0;

      for(Iterator var6 = players.iterator(); var6.hasNext(); ++cooldownsSet) {
         ServerPlayerEntity player = (ServerPlayerEntity)var6.next();
         getAlignData(player).setPledgeBreakCooldown(ticks);
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.cooldown.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), seconds, hmsTime}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.cooldown.multiple", new Object[]{players.size(), seconds, hmsTime}), true);
      }

      return cooldownsSet;
   }

   private static int queryPledge(CommandSource source, ServerPlayerEntity player) {
      Faction pledgeFaction = getAlignData(player).getPledgeFaction();
      if (pledgeFaction != null) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.query.pledged", new Object[]{player.func_145748_c_(), pledgeFaction.getColoredDisplayName()}), false);
         return 1;
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.pledge.query.none", new Object[]{player.func_145748_c_()}), false);
         return 0;
      }
   }
}
