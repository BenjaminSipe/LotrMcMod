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
import lotr.common.command.LOTRArgumentTypes;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionPointers;
import lotr.common.fac.FactionSettings;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class FactionArgument implements ArgumentType {
   private static final Collection EXAMPLES;
   public static final DynamicCommandExceptionType FACTION_BAD_ID;

   public static FactionArgument faction() {
      return new FactionArgument();
   }

   public FactionPointer parse(StringReader reader) throws CommandSyntaxException {
      FactionSettings currentFactions = LOTRArgumentTypes.getCurrentSidedFactionSettings();
      if (currentFactions == null) {
         return FactionPointers.UNALIGNED;
      } else {
         int cursor = reader.getCursor();
         ResourceLocation name = ResourceLocation.func_195826_a(reader);
         FactionPointer pointer = FactionPointer.of(name);
         if (pointer.resolveFaction(currentFactions).filter(Faction::isPlayableAlignmentFaction).isPresent()) {
            return pointer;
         } else {
            reader.setCursor(cursor);
            throw FACTION_BAD_ID.createWithContext(reader, name.toString());
         }
      }
   }

   public static FactionPointer getFactionPointer(CommandContext context, String name) {
      return (FactionPointer)context.getArgument(name, FactionPointer.class);
   }

   public static Faction getFaction(CommandContext context, String name) {
      return (Faction)getFactionPointer(context, name).resolveFaction(LOTRArgumentTypes.getCurrentSidedFactionSettings()).orElse((Object)null);
   }

   public CompletableFuture listSuggestions(CommandContext context, SuggestionsBuilder builder) {
      return ISuggestionProvider.func_197014_a(LOTRArgumentTypes.getCurrentSidedFactionSettings().getPlayableFactionNames(), builder);
   }

   public Collection getExamples() {
      return EXAMPLES;
   }

   static {
      EXAMPLES = Arrays.asList(FactionPointers.ROHAN.getNameString(), FactionPointers.ISENGARD.getNameString());
      FACTION_BAD_ID = new DynamicCommandExceptionType((arg) -> {
         return new TranslationTextComponent("argument.lotr.faction.id.invalid", new Object[]{arg});
      });
   }
}
