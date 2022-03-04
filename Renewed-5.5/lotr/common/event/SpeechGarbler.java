package lotr.common.event;

import java.util.Random;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.ServerChatEvent;
import org.apache.commons.lang3.StringUtils;

public class SpeechGarbler {
   private final Random rand = new Random(13541482055L);

   public static boolean isEnabledInConfig() {
      return (Boolean)LOTRConfig.COMMON.drunkSpeech.get();
   }

   public void handle(ServerChatEvent event) {
      ServerPlayerEntity player = event.getPlayer();
      String message = event.getMessage();
      String username = event.getUsername();
      ITextComponent chatComponent = event.getComponent();
      if (isEnabledInConfig()) {
         EffectInstance nausea = player.func_70660_b(Effects.field_76431_k);
         if (nausea != null) {
            int duration = nausea.func_76459_b();
            float chance = (float)duration / 4800.0F;
            chance = Math.min(chance, 1.0F);
            chance *= 0.4F;
            if (chatComponent instanceof TranslationTextComponent) {
               TranslationTextComponent ttc = (TranslationTextComponent)chatComponent;
               String key = ttc.func_150268_i();
               Object[] formatArgs = ttc.func_150271_j();

               for(int a = 0; a < formatArgs.length; ++a) {
                  Object arg = formatArgs[a];
                  String chatText = null;
                  if (arg instanceof StringTextComponent) {
                     StringTextComponent stc = (StringTextComponent)arg;
                     chatText = stc.func_150261_e();
                  } else if (arg instanceof String) {
                     chatText = (String)arg;
                  }

                  if (chatText != null && chatText.equals(message)) {
                     String newText = this.garbleString(chatText, chance);
                     if (arg instanceof String) {
                        formatArgs[a] = newText;
                     } else if (arg instanceof StringTextComponent) {
                        formatArgs[a] = new StringTextComponent(newText);
                     }
                  }
               }

               TranslationTextComponent newComponent = new TranslationTextComponent(key, formatArgs);
               newComponent.func_230530_a_(((ITextComponent)chatComponent).func_150256_b());
               chatComponent = newComponent;
            } else {
               LOTRLog.warn("SpeechGarbler expected a TranslationTextComponent, instead got a " + chatComponent.getClass().getName());
            }
         }
      }

      event.setComponent((ITextComponent)chatComponent);
   }

   public String garbleString(String speech, float chance) {
      String newSpeech = "";

      for(int i = 0; i < speech.length(); ++i) {
         String s = speech.substring(i, i + 1);
         if (this.rand.nextFloat() < chance) {
            s = "";
         } else if (this.rand.nextFloat() < chance * 0.4F) {
            s = s + " *hic* ";
         }

         newSpeech = newSpeech + s;
      }

      return StringUtils.normalizeSpace(newSpeech);
   }
}
