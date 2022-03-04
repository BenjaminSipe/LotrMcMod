package lotr.client.align;

import java.util.List;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.common.fac.Faction;
import lotr.common.network.SPacketNotifyAlignRequirement;
import lotr.common.util.LOTRUtil;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class NotifyAlignmentRequirement {
   public static void displayMessage(ClientPlayerEntity player, SPacketNotifyAlignRequirement packet) {
      String alignString = AlignmentFormatter.formatAlignForDisplay(packet.getAlignmentRequired());
      IFormattableTextComponent componentAlignReq = new StringTextComponent(alignString);
      componentAlignReq.func_240699_a_(TextFormatting.YELLOW);
      List factionNames = (List)packet.getAnyOfFactions().stream().map(Faction::getColoredDisplayName).collect(Collectors.toList());
      if (!factionNames.isEmpty()) {
         TranslationTextComponent fullMessage;
         if (factionNames.size() == 1) {
            fullMessage = new TranslationTextComponent("chat.lotr.align.insufficient", new Object[]{componentAlignReq, factionNames.get(0)});
         } else if (factionNames.size() == 2) {
            fullMessage = new TranslationTextComponent("chat.lotr.align.insufficient.2", new Object[]{componentAlignReq, factionNames.get(0), factionNames.get(1)});
         } else if (factionNames.size() == 3) {
            fullMessage = new TranslationTextComponent("chat.lotr.align.insufficient.3", new Object[]{componentAlignReq, factionNames.get(0), factionNames.get(1), factionNames.get(2)});
         } else {
            fullMessage = new TranslationTextComponent("chat.lotr.align.insufficient.more", new Object[]{componentAlignReq, factionNames.get(0), factionNames.get(1), factionNames.get(2), factionNames.size() - 3});
         }

         LOTRUtil.sendMessage(player, fullMessage);
      } else {
         LOTRLog.error("Received notify alignment requirement packet from server with an empty list of factions - this shouldn't happen!");
      }

   }
}
