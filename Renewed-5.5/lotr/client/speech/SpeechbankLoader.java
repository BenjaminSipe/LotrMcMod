package lotr.client.speech;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.curuquesta.SpeechbankEngine;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.predicate.SpeechbankConditionAndPredicate;
import lotr.curuquesta.structure.AlternativeConditionSets;
import lotr.curuquesta.structure.Speechbank;
import lotr.curuquesta.structure.SpeechbankConditionSet;
import lotr.curuquesta.structure.SpeechbankEntry;
import net.minecraft.util.ResourceLocation;

public class SpeechbankLoader {
   private final SpeechbankEngine speechbankEngine;

   public SpeechbankLoader(SpeechbankEngine speechbankEngine) {
      this.speechbankEngine = speechbankEngine;
   }

   public Speechbank load(ResourceLocation speechbankName, JsonObject json, SpeechbankResourceManager.ParentSpeechbankLoader parentLoader) {
      List entries = new ArrayList();
      JsonArray entriesArray = json.get("speech").getAsJsonArray();
      Iterator var6 = entriesArray.iterator();

      while(var6.hasNext()) {
         JsonElement entryElem = (JsonElement)var6.next();
         JsonObject entryObj = entryElem.getAsJsonObject();
         SpeechbankEntry entry = this.loadEntry(speechbankName, entryObj);
         entries.add(entry);
      }

      this.warnOfDuplicateLines(speechbankName, entries);
      this.loadAndInheritParentLines(speechbankName, json, entries, parentLoader);
      return new Speechbank(speechbankName.toString(), entries);
   }

   private void loadAndInheritParentLines(ResourceLocation speechbankName, JsonObject json, List entries, SpeechbankResourceManager.ParentSpeechbankLoader parentLoader) {
      if (json.has("parent")) {
         try {
            ResourceLocation parentSpeechbankName = new ResourceLocation(json.get("parent").getAsString());
            Optional loadedParent = parentLoader.getOrLoadParentResource(parentSpeechbankName);
            if (loadedParent.isPresent()) {
               List parentEntries = ((Speechbank)loadedParent.get()).getEntriesView();
               this.warnOfDuplicateLinesAfterInheritance(speechbankName, parentSpeechbankName, entries, parentEntries);
               entries.addAll(parentEntries);
            } else {
               LOTRLog.error("Failed to load parent speechbank for speechbank %s - parent not found", speechbankName);
            }
         } catch (Exception var8) {
            LOTRLog.error("Failed to load parent speechbank for speechbank %s", speechbankName);
            var8.printStackTrace();
         }
      }

   }

   private void warnOfDuplicateLines(ResourceLocation speechbankName, List entries) {
      Set trackedLines = new HashSet();
      Iterator var4 = entries.iterator();

      while(var4.hasNext()) {
         SpeechbankEntry entry = (SpeechbankEntry)var4.next();
         entry.streamLines().forEach((line) -> {
            if (!trackedLines.contains(line)) {
               trackedLines.add(line);
            } else {
               LOTRLog.error("Found duplicate line in speechbank %s - line duplicated is '%s'. Speechbanks should not have duplicate lines!", speechbankName, line);
            }

         });
      }

   }

   private void warnOfDuplicateLinesAfterInheritance(ResourceLocation speechbankName, ResourceLocation parentSpeechbankName, List entriesBeforeMerge, List entriesFromParent) {
      Set allLinesFromParent = (Set)entriesFromParent.stream().flatMap(SpeechbankEntry::streamLines).collect(Collectors.toSet());
      Set trackedLines = new HashSet();
      Iterator var7 = entriesBeforeMerge.iterator();

      while(var7.hasNext()) {
         SpeechbankEntry entry = (SpeechbankEntry)var7.next();
         entry.streamLines().forEach((line) -> {
            if (!trackedLines.contains(line)) {
               trackedLines.add(line);
               if (allLinesFromParent.contains(line)) {
                  LOTRLog.error("Found duplicate line in speechbank %s after merging in lines from parent %s - line duplicated is '%s'. Speechbanks should not have duplicate lines!", speechbankName, parentSpeechbankName, line);
               }
            }

         });
      }

   }

   private SpeechbankEntry loadEntry(ResourceLocation speechbankName, JsonObject json) {
      Set contextSatisfiers = new HashSet();
      if (json.has("conditions")) {
         JsonObject conditionsObj = json.get("conditions").getAsJsonObject();
         Iterator var5 = conditionsObj.entrySet().iterator();

         while(var5.hasNext()) {
            Entry e = (Entry)var5.next();
            String key = (String)e.getKey();
            JsonElement elem = (JsonElement)e.getValue();
            if (key.equalsIgnoreCase("alternatives")) {
               JsonArray alternativesArray = elem.getAsJsonArray();
               AlternativeConditionSets alternatives = this.loadAlternatives(speechbankName, alternativesArray);
               if (alternatives != null) {
                  contextSatisfiers.add(alternatives);
               }
            } else {
               SpeechbankConditionAndPredicate conditionAndPredicate = this.loadConditionAndPredicate(speechbankName, key, elem);
               if (conditionAndPredicate != null) {
                  contextSatisfiers.add(conditionAndPredicate);
               }
            }
         }
      }

      List lines = new ArrayList();
      JsonArray linesArray = json.get("lines").getAsJsonArray();
      Iterator var13 = linesArray.iterator();

      while(var13.hasNext()) {
         JsonElement lineElem = (JsonElement)var13.next();
         lines.add(lineElem.getAsString());
      }

      return new SpeechbankEntry(contextSatisfiers, lines);
   }

   private SpeechbankConditionAndPredicate loadConditionAndPredicate(ResourceLocation speechbankName, String key, JsonElement elem) {
      String predicateString = elem.getAsString();
      return this.loadConditionAndPredicate(speechbankName, key, predicateString);
   }

   private SpeechbankConditionAndPredicate loadConditionAndPredicate(ResourceLocation speechbankName, String conditionName, String predicateString) {
      SpeechbankCondition condition = this.speechbankEngine.getCondition(conditionName);
      if (condition == null) {
         LOTRLog.warn("Error loading speechbank %s: condition '%s' does not exist!", speechbankName, conditionName);
         return null;
      } else {
         Predicate predicate = condition.parsePredicateFromJsonString(predicateString);
         return SpeechbankConditionAndPredicate.of(condition, predicate);
      }
   }

   private AlternativeConditionSets loadAlternatives(ResourceLocation speechbankName, JsonArray alternativesArray) {
      List alternatives = new ArrayList();
      Iterator var4 = alternativesArray.iterator();

      while(var4.hasNext()) {
         JsonElement elem = (JsonElement)var4.next();
         JsonObject conditionsObj = elem.getAsJsonObject();
         Set conditionsAndPredicates = new HashSet();
         Iterator var8 = conditionsObj.entrySet().iterator();

         while(var8.hasNext()) {
            Entry e = (Entry)var8.next();
            SpeechbankConditionAndPredicate conditionAndPredicate = this.loadConditionAndPredicate(speechbankName, (String)e.getKey(), (JsonElement)e.getValue());
            if (conditionAndPredicate != null) {
               conditionsAndPredicates.add(conditionAndPredicate);
            }
         }

         SpeechbankConditionSet conditionSet = new SpeechbankConditionSet(conditionsAndPredicates);
         alternatives.add(conditionSet);
      }

      return new AlternativeConditionSets(alternatives);
   }
}
