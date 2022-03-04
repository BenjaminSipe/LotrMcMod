package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityGaladhrimElf extends LOTREntityElf {
   public LOTREntityGaladhrimElf(World world) {
      super(world);
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorGaladhrim));
      return horse;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.LOTHLORIEN;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killElf;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void dropElfItems(boolean flag, int i) {
      super.dropElfItems(flag, i);
      if (flag) {
         int dropChance = 20 - i * 4;
         dropChance = Math.max(dropChance, 1);
         if (this.field_70146_Z.nextInt(dropChance) == 0) {
            ItemStack elfDrink = new ItemStack(LOTRMod.mugMiruvor);
            elfDrink.func_77964_b(1 + this.field_70146_Z.nextInt(3));
            LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(this.field_70146_Z), true);
            this.func_70099_a(elfDrink, 0.0F);
         }
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.ELF_HOUSE, 1, 1 + i);
      }

   }

   public boolean canElfSpawnHere() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == biome.field_76752_A;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenLothlorien) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "galadhrim/elf/hired" : "galadhrim/elf/friendly";
      } else {
         return "galadhrim/elf/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.GALADHRIM.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.GALADHRIM;
   }
}
