package lotr.common.fellowship;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.StatCollector;

public class LOTRFellowshipProfile extends GameProfile {
   public static final String fellowshipPrefix = "f/";
   private LOTREntityBanner theBanner;
   private String fellowshipName;

   public LOTRFellowshipProfile(LOTREntityBanner banner, UUID fsID, String fsName) {
      super(fsID, fsName);
      this.theBanner = banner;
      this.fellowshipName = fsName;
   }

   public LOTRFellowship getFellowship() {
      return LOTRFellowshipData.getActiveFellowship(this.getId());
   }

   public LOTRFellowshipClient getFellowshipClient() {
      return LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer()).getClientFellowshipByName(this.fellowshipName);
   }

   public String getName() {
      return addFellowshipCode(super.getName());
   }

   public static boolean hasFellowshipCode(String s) {
      return s.toLowerCase().startsWith("f/".toLowerCase());
   }

   public static String addFellowshipCode(String s) {
      return "f/" + s;
   }

   public static String stripFellowshipCode(String s) {
      return s.substring("f/".length());
   }

   public static String getFellowshipCodeHint() {
      return StatCollector.func_74837_a("lotr.gui.bannerEdit.fellowshipHint", new Object[]{"f/"});
   }
}
