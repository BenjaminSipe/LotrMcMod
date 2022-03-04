package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainMercenary extends LOTREntityMoredain implements LOTRMercenary {
   private static ItemStack[] weaponsIron;
   private static ItemStack[] weaponsBronze;
   private static int[] turbanColors;

   public LOTREntityMoredainMercenary(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
      this.spawnRidingHorse = false;
   }

   protected EntityAIBase createHaradrimAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.7D, true);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.NEAR_HARAD;
   }

   public LOTRFaction getHiringFaction() {
      return LOTRFaction.NEAR_HARAD;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i;
      if (this.field_70146_Z.nextInt(3) == 0) {
         i = this.field_70146_Z.nextInt(weaponsBronze.length);
         this.npcItemsInv.setMeleeWeapon(weaponsBronze[i].func_77946_l());
         if (this.field_70146_Z.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
         }
      } else {
         i = this.field_70146_Z.nextInt(weaponsIron.length);
         this.npcItemsInv.setMeleeWeapon(weaponsIron[i].func_77946_l());
         if (this.field_70146_Z.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
         }
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextInt(8) == 0) {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsGulfHarad));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsGulfHarad));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyGulfHarad));
      } else if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsHarnedor));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsHarnedor));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyHarnedor));
      } else if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsUmbar));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsUmbar));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyUmbar));
      } else {
         this.func_70062_b(1, new ItemStack(LOTRMod.bootsNearHarad));
         this.func_70062_b(2, new ItemStack(LOTRMod.legsNearHarad));
         this.func_70062_b(3, new ItemStack(LOTRMod.bodyNearHarad));
      }

      if (this.field_70146_Z.nextInt(10) == 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else {
         ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
         int robeColor = turbanColors[this.field_70146_Z.nextInt(turbanColors.length)];
         LOTRItemHaradRobes.setRobesColor(turban, robeColor);
         this.func_70062_b(4, turban);
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public int getMercBaseCost() {
      return 20;
   }

   public float getMercAlignmentRequired() {
      return 0.0F;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireMoredainMercenary);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/mercenary/hired" : "nearHarad/mercenary/friendly";
      } else {
         return "nearHarad/mercenary/hostile";
      }
   }

   static {
      weaponsIron = new ItemStack[]{new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};
      weaponsBronze = new ItemStack[]{new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
      turbanColors = new int[]{10487808, 5976610, 14864579, 10852752, 11498561, 12361037};
   }
}
