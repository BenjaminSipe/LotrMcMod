package lotr.common.fac;

import lotr.common.resources.GenderedTranslationDecomposer;

public class FactionRankNameDecomposer {
   private final GenderedTranslationDecomposer shortName;
   private final GenderedTranslationDecomposer fullName;

   private FactionRankNameDecomposer(String translatedName) {
      String[] shortAndFullNames = decomposeIntoShortAndFull(translatedName);
      this.shortName = GenderedTranslationDecomposer.actOn(shortAndFullNames[0]);
      this.fullName = GenderedTranslationDecomposer.actOn(shortAndFullNames[1]);
   }

   public static FactionRankNameDecomposer actOn(String translatedName) {
      return new FactionRankNameDecomposer(translatedName);
   }

   private static String[] decomposeIntoShortAndFull(String name) {
      return GenderedTranslationDecomposer.splitInHalfAndTrim(name, '|');
   }

   public String getShortName(RankGender gender) {
      return this.shortName.getName(gender.isMale());
   }

   public String getFullName(RankGender gender) {
      return this.fullName.getName(gender.isMale());
   }
}
