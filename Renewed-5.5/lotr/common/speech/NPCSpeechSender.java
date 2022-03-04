package lotr.common.speech;

import com.google.common.collect.ImmutableList;
import java.util.List;
import lotr.common.LOTRLog;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketSpeechbank;
import lotr.curuquesta.SpeechbankContext;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class NPCSpeechSender {
   public static void sendMessageInContext(ServerPlayerEntity player, NPCEntity npc, ResourceLocation speechbank) {
      sendMessageInContext((List)ImmutableList.of(player), npc, speechbank);
   }

   public static void sendMessageInContext(List players, NPCEntity npc, ResourceLocation speechbank) {
      long randomSpeechSeed = npc.func_70681_au().nextLong();
      players.forEach((player) -> {
         NPCSpeechbankContext contextProvider = new NPCSpeechbankContext(npc, player);
         SpeechbankContext context = LOTRSpeechbankEngine.INSTANCE.populateContext(contextProvider);
         LOTRLog.debug("Sending speechbank %s for NPC %s to player %s with context %s", speechbank, npc.func_200200_C_().getString(), player.func_200200_C_().getString(), context);
         SPacketSpeechbank packet = new SPacketSpeechbank(npc.func_145782_y(), speechbank, context, randomSpeechSeed);
         LOTRPacketHandler.sendTo(packet, player);
      });
   }
}
