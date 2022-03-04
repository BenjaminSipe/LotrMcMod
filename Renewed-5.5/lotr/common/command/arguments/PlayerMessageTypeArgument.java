package lotr.common.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import lotr.common.data.PlayerMessageType;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerMessageTypeArgument implements ArgumentType {
   private static final Collection EXAMPLES;
   public static final DynamicCommandExceptionType MESSAGE_BAD_ID;

   public static PlayerMessageTypeArgument messageType() {
      return new PlayerMessageTypeArgument();
   }

   public PlayerMessageType parse(StringReader reader) throws CommandSyntaxException {
      int cursor = reader.getCursor();
      String name = reader.readUnquotedString();
      PlayerMessageType message = PlayerMessageType.forSaveName(name);
      if (message != null && message != PlayerMessageType.CUSTOM) {
         return message;
      } else {
         reader.setCursor(cursor);
         throw MESSAGE_BAD_ID.createWithContext(reader, name.toString());
      }
   }

   public static PlayerMessageType getMessageType(CommandContext context, String name) {
      return (PlayerMessageType)context.getArgument(name, PlayerMessageType.class);
   }

   public CompletableFuture listSuggestions(CommandContext context, SuggestionsBuilder builder) {
      return ISuggestionProvider.func_197005_b(PlayerMessageType.getAllPresetNamesForCommand(), builder);
   }

   public Collection getExamples() {
      return EXAMPLES;
   }

   static {
      EXAMPLES = Arrays.asList(PlayerMessageType.ALIGN_DRAIN.getSaveName(), PlayerMessageType.FRIENDLY_FIRE.getSaveName());
      MESSAGE_BAD_ID = new DynamicCommandExceptionType((arg) -> {
         return new TranslationTextComponent("argument.lotr.playerMessage.id.invalid", new Object[]{arg});
      });
   }
}
