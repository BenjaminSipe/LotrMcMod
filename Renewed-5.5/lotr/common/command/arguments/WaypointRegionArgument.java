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
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.WaypointRegion;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointRegionArgument implements ArgumentType {
   private static final Collection EXAMPLES = Arrays.asList("lotr:shire", "lotr:gondor");
   public static final DynamicCommandExceptionType REGION_BAD_ID = new DynamicCommandExceptionType((arg) -> {
      return new TranslationTextComponent("argument.lotr.waypointRegion.id.invalid", new Object[]{arg});
   });

   public static WaypointRegionArgument waypointRegion() {
      return new WaypointRegionArgument();
   }

   public WaypointRegion parse(StringReader reader) throws CommandSyntaxException {
      MapSettings currentMap = LOTRArgumentTypes.getCurrentSidedMapSettings();
      if (currentMap == null) {
         return null;
      } else {
         int cursor = reader.getCursor();
         ResourceLocation name = ResourceLocation.func_195826_a(reader);
         WaypointRegion region = currentMap.getWaypointRegionByName(name);
         if (region != null) {
            return region;
         } else {
            reader.setCursor(cursor);
            throw REGION_BAD_ID.createWithContext(reader, name.toString());
         }
      }
   }

   public static WaypointRegion getRegion(CommandContext context, String name) {
      return (WaypointRegion)context.getArgument(name, WaypointRegion.class);
   }

   public CompletableFuture listSuggestions(CommandContext context, SuggestionsBuilder builder) {
      return ISuggestionProvider.func_197014_a(LOTRArgumentTypes.getCurrentSidedMapSettings().getWaypointRegionNames(), builder);
   }

   public Collection getExamples() {
      return EXAMPLES;
   }
}
