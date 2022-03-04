package lotr.client.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import net.minecraft.client.resources.I18n;

public class LocalizableDecimalFormat {
   private final DecimalFormat format;
   private final String keyDecimalSeparatorChar;
   private final String keyGroupSeparatorChar;
   private final DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();

   public LocalizableDecimalFormat(DecimalFormat format, String keyDecimalSeparatorChar, String keyGroupSeparatorChar) {
      this.format = format;
      this.keyDecimalSeparatorChar = keyDecimalSeparatorChar;
      this.keyGroupSeparatorChar = keyGroupSeparatorChar;
   }

   public String format(double number) {
      this.localizeFormat();
      return this.format.format(number);
   }

   public String format(long number) {
      this.localizeFormat();
      return this.format.format(number);
   }

   public Number parse(String source) throws ParseException {
      this.localizeFormat();
      return this.format.parse(source);
   }

   private void localizeFormat() {
      char decimalSeparatorChar = '.';
      char groupSeparatorChar = ',';
      String decimalSeparator = I18n.func_135052_a(this.keyDecimalSeparatorChar, new Object[0]);
      if (decimalSeparator.length() == 1) {
         decimalSeparatorChar = decimalSeparator.charAt(0);
      }

      String groupSeparator = I18n.func_135052_a(this.keyGroupSeparatorChar, new Object[0]);
      if (groupSeparator.length() == 1) {
         groupSeparatorChar = groupSeparator.charAt(0);
      }

      this.formatSymbols.setDecimalSeparator(decimalSeparatorChar);
      this.formatSymbols.setGroupingSeparator(groupSeparatorChar);
      this.format.setDecimalFormatSymbols(this.formatSymbols);
   }
}
