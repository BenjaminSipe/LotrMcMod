package lotr.common.quest;

import com.google.common.base.Supplier;
import java.util.UUID;

public interface MiniQuestSelector {
   boolean include(LOTRMiniQuest var1);

   public static class BountyActiveFaction extends MiniQuestSelector.BountyActiveAnyFaction {
      private final Supplier factionGet;

      public BountyActiveFaction(Supplier sup) {
         this.factionGet = sup;
      }

      public boolean include(LOTRMiniQuest quest) {
         return super.include(quest) && quest.entityFaction == this.factionGet.get();
      }
   }

   public static class BountyActiveAnyFaction extends MiniQuestSelector.OptionalActive {
      public BountyActiveAnyFaction() {
         this.setActiveOnly();
      }

      public boolean include(LOTRMiniQuest quest) {
         if (super.include(quest) && quest instanceof LOTRMiniQuestBounty) {
            LOTRMiniQuestBounty bQuest = (LOTRMiniQuestBounty)quest;
            return !bQuest.killed;
         } else {
            return false;
         }
      }
   }

   public static class Faction extends MiniQuestSelector.OptionalActive {
      private final Supplier factionGet;

      public Faction(Supplier sup) {
         this.factionGet = sup;
      }

      public boolean include(LOTRMiniQuest quest) {
         return super.include(quest) && quest.entityFaction == this.factionGet.get();
      }
   }

   public static class EntityId extends MiniQuestSelector.OptionalActive {
      private final UUID entityID;

      public EntityId(UUID id) {
         this.entityID = id;
      }

      public boolean include(LOTRMiniQuest quest) {
         return super.include(quest) && quest.entityUUID.equals(this.entityID);
      }
   }

   public static class OptionalActive implements MiniQuestSelector {
      private boolean activeOnly = false;

      public MiniQuestSelector.OptionalActive setActiveOnly() {
         this.activeOnly = true;
         return this;
      }

      public boolean include(LOTRMiniQuest quest) {
         return this.activeOnly ? quest.isActive() : true;
      }
   }
}
