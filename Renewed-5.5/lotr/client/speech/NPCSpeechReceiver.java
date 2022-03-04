package lotr.client.speech;

import java.util.Random;
import lotr.client.LOTRClientProxy;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.event.SpeechGarbler;
import lotr.common.network.SPacketSpeechbank;
import lotr.curuquesta.SpeechbankContext;
import lotr.curuquesta.structure.Speechbank;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class NPCSpeechReceiver {
   private static final Random SEEDED_SPEECH_RAND = new Random();
   private static final SpeechGarbler SPEECH_GARBLER = new SpeechGarbler();

   public static void receiveSpeech(World world, ClientPlayerEntity player, SPacketSpeechbank packet) {
      int eId = packet.entityId;
      Entity entity = world.func_73045_a(eId);
      if (entity instanceof NPCEntity) {
         NPCEntity npc = (NPCEntity)entity;
         LOTRLog.debug("Received speechbank %s for NPC %s with context %s", packet.speechbank, npc.func_200200_C_().getString(), packet.context);
         addSpeechMessage(player, npc, packet.speechbank, packet.context, packet.randomSpeechSeed, packet.forceChatLog);
      } else if (entity == null) {
         LOTRLog.warn("Received speechbank packet on behalf of entity with ID %d, but the entity does not exist on the client side!");
      } else {
         LOTRLog.warn("Received speechbank packet on behalf of entity with ID %d, but the entity is not an NPC - it is %s!", eId, entity.func_200200_C_().getString());
      }

   }

   private static void addSpeechMessage(PlayerEntity player, NPCEntity npc, ResourceLocation speechbank, SpeechbankContext context, long randomSpeechSeed, boolean forceChatLog) {
      String speechLine = getSpeechbankLine(speechbank, context, randomSpeechSeed, npc);
      if ((Boolean)LOTRConfig.CLIENT.immersiveSpeech.get()) {
         ImmersiveSpeech.receiveSpeech(npc, speechLine);
      }

      if (!(Boolean)LOTRConfig.CLIENT.immersiveSpeech.get() || (Boolean)LOTRConfig.CLIENT.immersiveSpeechChatLog.get() || forceChatLog) {
         ITextComponent speechComponent = formatSpeechLineForNPC(npc, speechLine);
         player.func_145747_a(speechComponent, npc.func_110124_au());
      }

   }

   private static String getSpeechbankLine(ResourceLocation speechbank, SpeechbankContext context, long randomSpeechSeed, NPCEntity npc) {
      SEEDED_SPEECH_RAND.setSeed(randomSpeechSeed);
      String line = getSpeechbank(speechbank).getRandomSpeech(context, SEEDED_SPEECH_RAND);
      line = applyGarbling(line, npc);
      return line;
   }

   private static Speechbank getSpeechbank(ResourceLocation speechbank) {
      return LOTRClientProxy.getSpeechbankResourceManager().getSpeechbank(speechbank);
   }

   private static String applyGarbling(String line, NPCEntity npc) {
      if (SpeechGarbler.isEnabledInConfig() && npc.isDrunk()) {
         float f = npc.getDrunkenSpeechFactor();
         line = SPEECH_GARBLER.garbleString(line, f);
      }

      return line;
   }

   private static ITextComponent formatSpeechLineForNPC(NPCEntity npc, String speechLine) {
      ITextComponent speechComponent = new StringTextComponent(speechLine);
      ITextComponent nameComponent = (new TranslationTextComponent("<%s>", new Object[]{npc.func_200200_C_()})).func_240699_a_(TextFormatting.YELLOW);
      ITextComponent fullMessage = new TranslationTextComponent("%s %s", new Object[]{nameComponent, speechComponent});
      return fullMessage;
   }
}
