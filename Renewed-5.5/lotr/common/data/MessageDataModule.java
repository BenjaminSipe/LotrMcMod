package lotr.common.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

public class MessageDataModule extends PlayerDataModule {
   private Set sentMessages = new HashSet();

   protected MessageDataModule(LOTRPlayerData pd) {
      super(pd);
   }

   public void save(CompoundNBT playerNBT) {
      ListNBT sentMessageTags = new ListNBT();
      Iterator var3 = this.sentMessages.iterator();

      while(var3.hasNext()) {
         PlayerMessageType message = (PlayerMessageType)var3.next();
         sentMessageTags.add(StringNBT.func_229705_a_(message.getSaveName()));
      }

      playerNBT.func_218657_a("SentMessageTypes", sentMessageTags);
   }

   public void load(CompoundNBT playerNBT) {
      this.sentMessages.clear();
      ListNBT sentMessageTags = playerNBT.func_150295_c("SentMessageTypes", 8);

      for(int i = 0; i < sentMessageTags.size(); ++i) {
         String messageName = sentMessageTags.func_150307_f(i);
         PlayerMessageType messageType = PlayerMessageType.forSaveName(messageName);
         if (messageType != null) {
            this.sentMessages.add(messageType);
         } else {
            this.playerData.logPlayerError("Loaded nonexistent player message type %s", messageName);
         }
      }

   }

   public void sendMessageIfNotReceived(PlayerMessageType message) {
      this.executeIfPlayerExistsServerside((player) -> {
         if (!this.sentMessages.contains(message)) {
            this.sentMessages.add(message);
            this.markDirty();
            message.displayTo(player, false);
         }

      });
   }
}
