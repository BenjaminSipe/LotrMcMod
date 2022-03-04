package lotr.common.entity.npc.ai;

import java.util.function.Function;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.inv.NPCItemsInventory;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class StandardAttackModeUpdaters {
   public static final AttackModeUpdater meleeOnly() {
      return (npc, mode, mounted) -> {
         helperSetMainhandItem(npc, mode == AttackMode.IDLE ? NPCItemsInventory::getIdleItem : NPCItemsInventory::getMeleeWeapon);
      };
   }

   public static final AttackModeUpdater mountableMeleeOnly() {
      return (npc, mode, mounted) -> {
         if (mounted) {
            helperSetMainhandItem(npc, mode == AttackMode.IDLE ? NPCItemsInventory::getIdleItemMounted : NPCItemsInventory::getMeleeWeaponMounted);
         } else {
            helperSetMainhandItem(npc, mode == AttackMode.IDLE ? NPCItemsInventory::getIdleItem : NPCItemsInventory::getMeleeWeapon);
         }

      };
   }

   public static final AttackModeUpdater rangedOnly() {
      return (npc, mode, mounted) -> {
         helperSetMainhandItem(npc, mode == AttackMode.IDLE ? NPCItemsInventory::getIdleItem : NPCItemsInventory::getRangedWeapon);
      };
   }

   public static final AttackModeUpdater meleeRangedSwitching() {
      return (npc, mode, mounted) -> {
         Goal meleeGoal = npc.getAttackGoalsHolder().getNonNullMeleeAttackGoal();
         Goal rangedGoal = npc.getAttackGoalsHolder().getNonNullRangedAttackGoal();
         npc.field_70714_bg.func_85156_a(meleeGoal);
         npc.field_70714_bg.func_85156_a(rangedGoal);
         if (mode == AttackMode.IDLE) {
            helperSetMainhandItem(npc, NPCItemsInventory::getIdleItem);
         } else {
            int goalIndex = npc.getAttackGoalIndex();
            if (goalIndex < 0) {
               throw new IllegalStateException("Tried to run melee-range switching for an NPC " + npc.func_200200_C_().getString() + " without a defined attack goal index - this is a development error!");
            }

            if (mode == AttackMode.MELEE) {
               npc.field_70714_bg.func_75776_a(goalIndex, meleeGoal);
               helperSetMainhandItem(npc, NPCItemsInventory::getMeleeWeapon);
            } else if (mode == AttackMode.RANGED) {
               npc.field_70714_bg.func_75776_a(goalIndex, rangedGoal);
               helperSetMainhandItem(npc, NPCItemsInventory::getRangedWeapon);
            }
         }

      };
   }

   public static final AttackModeUpdater meleeOnlyOrcWithBomb() {
      return (npc, mode, mounted) -> {
         if (!npc.getNPCItemsInv().getBomb().func_190926_b()) {
            helperSetMainhandItem(npc, NPCItemsInventory::getBombingItem);
         } else {
            helperSetMainhandItem(npc, mode == AttackMode.IDLE ? NPCItemsInventory::getIdleItem : NPCItemsInventory::getMeleeWeapon);
         }

      };
   }

   private static void helperSetMainhandItem(NPCEntity npc, Function itemFetcher) {
      npc.func_184611_a(Hand.MAIN_HAND, (ItemStack)itemFetcher.apply(npc.getNPCItemsInv()));
   }
}
