package lotr.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Iterator;
import lotr.common.command.arguments.PlayerMessageTypeArgument;
import lotr.common.data.PlayerMessageType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerMessageCommand extends LOTRBaseCommand {
   public static void register(CommandDispatcher dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.func_197057_a("lotr_message").requires((context) -> {
         return context.func_197034_c(2);
      })).then(((RequiredArgumentBuilder)Commands.func_197056_a("targets", EntityArgument.func_197094_d()).then(Commands.func_197057_a("preset").then(Commands.func_197056_a("type", PlayerMessageTypeArgument.messageType()).executes((context) -> {
         return sendPresetMessage((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), PlayerMessageTypeArgument.getMessageType(context, "type"));
      })))).then(Commands.func_197057_a("custom").then(Commands.func_197056_a("text", StringArgumentType.string()).executes((context) -> {
         return sendCustomMessage((CommandSource)context.getSource(), EntityArgument.func_197090_e(context, "targets"), StringArgumentType.getString(context, "text"));
      })))));
   }

   private static int sendPresetMessage(CommandSource source, Collection players, PlayerMessageType messageType) {
      int sent = 0;

      for(Iterator var4 = players.iterator(); var4.hasNext(); ++sent) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         messageType.displayTo(player, true);
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.message.preset.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), messageType.getSaveName()}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.message.preset.multiple", new Object[]{players.size(), messageType.getSaveName()}), true);
      }

      return sent;
   }

   private static int sendCustomMessage(CommandSource source, Collection players, String customText) {
      int sent = 0;

      for(Iterator var4 = players.iterator(); var4.hasNext(); ++sent) {
         ServerPlayerEntity player = (ServerPlayerEntity)var4.next();
         PlayerMessageType.displayCustomMessageTo(player, true, customText);
      }

      if (players.size() == 1) {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.message.custom.single", new Object[]{((ServerPlayerEntity)players.iterator().next()).func_145748_c_(), customText}), true);
      } else {
         source.func_197030_a(new TranslationTextComponent("commands.lotr.message.custom.multiple", new Object[]{players.size(), customText}), true);
      }

      return sent;
   }
}
