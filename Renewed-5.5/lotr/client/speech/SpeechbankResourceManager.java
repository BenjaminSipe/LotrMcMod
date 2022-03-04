package lotr.client.speech;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lotr.client.text.ParentTextResourceLoader;
import lotr.client.text.TranslatableTextReloadListener;
import lotr.common.speech.LOTRSpeechbankEngine;
import lotr.curuquesta.structure.Speechbank;
import net.minecraft.client.Minecraft;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class SpeechbankResourceManager extends TranslatableTextReloadListener {
   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private final SpeechbankLoader loader;
   private static final List FALLBACK_MESSAGES = ImmutableList.of("I don't have anything to say to you yet.", "Come back later for some fine conversation!", "They haven't given me speech banks yet.", "They haven't given me speech banks yet. But I do have these fine new speaking animations.", "Conditional speechbank engine - now available in a mod near you!", "$ERROR_UNKNOWN_TYPE$: SPEECHBANK NOT FOUND! No, no, just joking. But they really haven't given me any yet!", "Nopa is Based.", "I speak when I am spoken to. But I don't have much to say yet.", "What? If people were always clicking on you, you'd have trouble coming up with things to say too.", "SPEECH ERROR: java.lang.NullPointerException at NPCEn... no, that won't work on you, you're too good for that.", "Curuquesta: better than all the resta.");

   public SpeechbankResourceManager(Minecraft mc) {
      super(mc, ".json");
      this.loader = new SpeechbankLoader(LOTRSpeechbankEngine.INSTANCE);
   }

   public Speechbank getSpeechbank(ResourceLocation speechPath) {
      return (Speechbank)this.getOrLoadTextResource(speechPath);
   }

   protected ResourceLocation convertToFullResourcePath(ResourceLocation basePath) {
      return new ResourceLocation(basePath.func_110624_b(), String.format("speech/%s", basePath.func_110623_a()));
   }

   protected Speechbank loadResource(ResourceLocation langPath, BufferedReader reader, SpeechbankResourceManager.ParentSpeechbankLoader parentLoader) {
      JsonObject jsonObj = (JsonObject)JSONUtils.func_193839_a(GSON, reader, JsonObject.class);
      return this.loader.load(langPath, jsonObj, parentLoader);
   }

   protected SpeechbankResourceManager.ParentSpeechbankLoader createNewParentLoader(ResourceLocation topLevelPath, String lang) {
      return new SpeechbankResourceManager.ParentSpeechbankLoader(topLevelPath, lang, this::convertToFullResourcePath, (parentPath, parentLang, parentSpeechbankLoader) -> {
         return (Speechbank)this.getOrLoadTextResource(parentPath, parentLang, false, parentSpeechbankLoader);
      });
   }

   protected Speechbank loadErroredFallbackResource(ResourceLocation langPath, String errorMsg) {
      return Speechbank.getFallbackSpeechbank(langPath.toString(), FALLBACK_MESSAGES);
   }

   public static class ParentSpeechbankLoader extends ParentTextResourceLoader {
      private final Function fullPathConverter;
      private final SpeechbankResourceManager.ParentSpeechbankLoader.ParentSpeechbankGetter parentSpeechbankGetter;

      public ParentSpeechbankLoader(ResourceLocation topLevelSpeechbank, String langCode, Function fullPathConverter, SpeechbankResourceManager.ParentSpeechbankLoader.ParentSpeechbankGetter parentSpeechbankGetter) {
         super(topLevelSpeechbank, langCode);
         this.fullPathConverter = fullPathConverter;
         this.parentSpeechbankGetter = parentSpeechbankGetter;
      }

      public Optional getOrLoadParentResource(ResourceLocation parent) {
         this.checkInheritanceRecord((ResourceLocation)this.fullPathConverter.apply(parent));
         return Optional.ofNullable(this.parentSpeechbankGetter.getSpeechbank(parent, this.langCode, this));
      }

      protected IllegalArgumentException createCircularReferenceException(ResourceLocation topLevelPath, ResourceLocation parentPath) {
         return new IllegalArgumentException(String.format("Circular reference in speechbank %s parent tree! Speechbank %s already included", topLevelPath, parentPath));
      }

      @FunctionalInterface
      public interface ParentSpeechbankGetter {
         Speechbank getSpeechbank(ResourceLocation var1, String var2, SpeechbankResourceManager.ParentSpeechbankLoader var3);
      }
   }
}
