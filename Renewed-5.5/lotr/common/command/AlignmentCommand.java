package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Iterator;
import lotr.common.command.arguments.FactionArgument;
import lotr.common.fac.Faction;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class AlignmentCommand extends LOTRBaseCommand {
   private static final float MAX_COMMAND_ALIGNMENT = 10000.0F;
   private static final float MIN_COMMAND_ALIGNMENT = -10000.0F;

   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("alignment").requires((context) -> {
         return context.func_197034_c(2);
      })).then(Commands.func_197057_a("set").then(((RequiredArgumentBuilder)Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197056_a("faction", FactionArgument.faction()).then(Commands.func_197056_a("amount", alignmentSetValueArgument()).executes((context) -> {
         return setAlignment((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), FactionArgument.getFaction(context, "faction"), FloatArgumentType.getFloat(context, "amount"));
      })))).then(Commands.func_197057_a("all").then(Commands.func_197056_a("amount", alignmentSetValueArgument()).executes((context) -> {
         return setAllAlignments((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), FloatArgumentType.getFloat(context, "amount"));
      })))))).then(Commands.func_197057_a("add").then(((RequiredArgumentBuilder)Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197056_a("faction", FactionArgument.faction()).then(Commands.func_197056_a("amount", FloatArgumentType.floatArg()).executes((context) -> {
         return addAlignment((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), FactionArgument.getFaction(context, "faction"), FloatArgumentType.getFloat(context, "amount"));
      })))).then(Commands.func_197057_a("all").then(Commands.func_197056_a("amount", FloatArgumentType.floatArg()).executes((context) -> {
         return addAllAlignments((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), FloatArgumentType.getFloat(context, "amount"));
      })))))).then(Commands.func_197057_a("query").then(Commands.func_197056_a("target", EntityArgument.func_197096_c()).then(Commands.func_197056_a("faction", FactionArgument.faction()).executes((context) -> {
         return queryAlignment((CommandSource)context.getSource(), EntityArgument.func_197089_d(context, "target"), FactionArgument.getFaction(context, "faction"));
      })))));
   }

   private static FloatArgumentType alignmentSetValueArgument() {
      return FloatArgumentType.floatArg(-10000.0F, 10000.0F);
   }

   private static ITextComponent getAmountComponent(float amount) {
      IFormattableTextComponent text = new StringTextComponent(String.valueOf(amount));
      if (amount > 0.0F) {
         text.func_240699_a_(TextFormatting.GREEN);
      } else if (amount < 0.0F) {
         text.func_240699_a_(TextFormatting.RED);
      }

      return text;
   }

   private static int setAlignment(CommandSource source, Collection players, Faction faction, float amount) throws CommandSyntaxException {
      int playersChanged = 0;

      for(Iterator var5 = players.iterator(); var5.hasNext(); ++playersChanged) {
         ServerPlayerEntity player = (ServerPlayerEntity)var5.next();
         getAlignData(player).setAlignment(faction, amount);
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.set.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), faction.getColoredDisplayName(), getAmountComponent(amount)}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.set.multiple", new Object[]{players.size(), faction.getColoredDisplayName(), getAmountComponent(amount)}), true);
      }

      return playersChanged;
   }

   private static int setAllAlignments(CommandSource source, Collection players, float amount) throws CommandSyntaxException {
      int playersChanged = 0;

      for(Iterator var4 = players.iterator(); var4.hasNext(); ++playersChanged) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         Iterator var6 = allFactions().iterator();

         while(var6.hasNext()) {
            Faction faction = (Faction)var6.next();
            getAlignData(player).setAlignment(faction, amount);
         }
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.set.all.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), getAmountComponent(amount)}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.set.all.multiple", new Object[]{players.size(), getAmountComponent(amount)}), true);
      }

      return playersChanged;
   }

   private static int addAlignment(CommandSource source, Collection players, Faction faction, float addAmount) throws CommandSyntaxException {
      int playersChanged = 0;

      for(Iterator var5 = players.iterator(); var5.hasNext(); ++playersChanged) {
         ServerPlayerEntity player = (ServerPlayerEntity)var5.next();
         checkResultingAlignmentWithinBounds(player, faction, addAmount);
         getAlignData(player).addAlignment(faction, addAmount);
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.add.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), faction.getColoredDisplayName(), getAmountComponent(addAmount)}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.add.multiple", new Object[]{players.size(), faction.getColoredDisplayName(), getAmountComponent(addAmount)}), true);
      }

      return playersChanged;
   }

   private static int addAllAlignments(CommandSource source, Collection players, float addAmount) throws CommandSyntaxException {
      int playersChanged = 0;

      for(Iterator var4 = players.iterator(); var4.hasNext(); ++playersChanged) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         Iterator var6 = allFactions().iterator();

         while(var6.hasNext()) {
            Faction faction = (Faction)var6.next();
            checkResultingAlignmentWithinBounds(player, faction, addAmount);
            getAlignData(player).addAlignment(faction, addAmount);
         }
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.add.all.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), getAmountComponent(addAmount)}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.add.all.multiple", new Object[]{players.size(), getAmountComponent(addAmount)}), true);
      }

      return playersChanged;
   }

   private static void checkResultingAlignmentWithinBounds(ServerPlayerEntity player, Faction faction, float addAmount) throws CommandSyntaxException {
      float currentAmount = getAlignData(player).getAlignment(faction);
      if (currentAmount + addAmount > 10000.0F) {
         throw new CommandException(new TranslationTextComponent("commands.lotr.alignment.add.tooHigh", new Object[]{player.func_145748_c_(), faction.getDisplayName(), 10000.0F}));
      } else if (currentAmount + addAmount < -10000.0F) {
         throw new CommandException(new TranslationTextComponent("commands.lotr.alignment.add.tooLow", new Object[]{player.func_145748_c_(), faction.getDisplayName(), -10000.0F}));
      }
   }

   private static int queryAlignment(CommandSource source, ServerPlayerEntity player, Faction faction) {
      float currentAmount = getAlignData(player).getAlignment(faction);
      source.func_197030_a(new TranslationTextComponent("commands.lotr.alignment.query", new Object[]{player.func_145748_c_(), faction.getColoredDisplayName(), getAmountComponent(currentAmount)}), false);
      int i = Math.round(currentAmount);
      return i;
   }
}
