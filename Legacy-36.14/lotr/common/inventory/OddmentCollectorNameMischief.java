package lotr.common.inventory;

import java.util.Random;

public class OddmentCollectorNameMischief {
   private static final String VOWELS = "aeiou";
   private static final String CONSONANTS = "bcdfghjklmnopqrstvwxyz";
   private static final char FORMATTING_CHARACTER = '§';

   public static String garbleName(String name, Random rand) {
      int deletes = rand.nextInt(3);

      int replaces;
      int dupes;
      for(replaces = 0; replaces < deletes; ++replaces) {
         if (name.length() > 3) {
            dupes = rand.nextInt(name.length());
            if (!isFormattingCharacter(name, dupes)) {
               name.charAt(dupes);
               name = name.substring(0, dupes) + name.substring(dupes + 1);
            }
         }
      }

      replaces = rand.nextInt(3);

      int x;
      char cNew;
      for(dupes = 0; dupes < replaces; ++dupes) {
         x = rand.nextInt(name.length());
         if (!isFormattingCharacter(name, x)) {
            char c = name.charAt(x);
            if ("aeiou".indexOf(Character.toLowerCase(c)) >= 0) {
               cNew = "aeiou".charAt(rand.nextInt("aeiou".length()));
               if (Character.isUpperCase(c)) {
                  cNew = Character.toUpperCase(cNew);
               }

               c = cNew;
            } else if ("bcdfghjklmnopqrstvwxyz".indexOf(Character.toLowerCase(c)) >= 0) {
               cNew = "bcdfghjklmnopqrstvwxyz".charAt(rand.nextInt("bcdfghjklmnopqrstvwxyz".length()));
               if (Character.isUpperCase(c)) {
                  cNew = Character.toUpperCase(cNew);
               }

               c = cNew;
            }

            name = name.substring(0, x) + c + name.substring(x + 1);
         }
      }

      dupes = rand.nextInt(2);

      for(x = 0; x < dupes; ++x) {
         int x = rand.nextInt(name.length());
         if (!isFormattingCharacter(name, x)) {
            cNew = name.charAt(x);
            if (Character.isAlphabetic(cNew)) {
               name = name.substring(0, x) + cNew + cNew + name.substring(x + 1);
            }
         }
      }

      return name;
   }

   private static boolean isFormattingCharacter(String s, int index) {
      char charAt = s.charAt(index);
      if (charAt == 167) {
         return true;
      } else {
         if (index >= 1) {
            char charBefore = s.charAt(index - 1);
            if (charBefore == 167) {
               return true;
            }
         }

         return false;
      }
   }
}
