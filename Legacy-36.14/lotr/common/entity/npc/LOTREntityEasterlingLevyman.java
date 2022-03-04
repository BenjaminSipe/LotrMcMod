package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemKaftan;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingLevyman extends LOTREntityEasterling {
   private static ItemStack[] levyWeapons;
   private static ItemStack[] levySpears;
   private static ItemStack[] levyBodies;
   private static ItemStack[] levyLegs;
   private static ItemStack[] levyBoots;
   private static final int[] kaftanColors;

   public LOTREntityEasterlingLevyman(World world) {
      super(world);
      this.addTargetTasks(true);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(levyWeapons.length);
      this.npcItemsInv.setMeleeWeapon(levyWeapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         i = this.field_70146_Z.nextInt(levySpears.length);
         this.npcItemsInv.setMeleeWeapon(levySpears[i].func_77946_l());
      }

      i = this.field_70146_Z.nextInt(levyBoots.length);
      this.func_70062_b(1, levyBoots[i].func_77946_l());
      i = this.field_70146_Z.nextInt(levyLegs.length);
      this.func_70062_b(2, levyLegs[i].func_77946_l());
      i = this.field_70146_Z.nextInt(levyBodies.length);
      this.func_70062_b(3, levyBodies[i].func_77946_l());
      this.func_70062_b(4, (ItemStack)null);
      if (this.field_70146_Z.nextBoolean()) {
         ItemStack kaftan = new ItemStack(LOTRMod.bodyKaftan);
         int kaftanColor = kaftanColors[this.field_70146_Z.nextInt(kaftanColors.length)];
         LOTRItemKaftan.setRobesColor(kaftan, kaftanColor);
         this.func_70062_b(3, kaftan);
         if (this.field_70146_Z.nextBoolean()) {
            ItemStack kaftanLegs = new ItemStack(LOTRMod.legsKaftan);
            LOTRItemKaftan.setRobesColor(kaftanLegs, kaftanColor);
            this.func_70062_b(2, kaftanLegs);
         }
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "rhun/warrior/hired" : "rhun/warrior/friendly";
      } else {
         return "rhun/warrior/hostile";
      }
   }

   static {
      levyWeapons = new ItemStack[]{new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.daggerRhunPoisoned), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(Items.field_151040_l), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze)};
      levySpears = new ItemStack[]{new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze)};
      levyBodies = new ItemStack[]{new ItemStack(Items.field_151027_R), new ItemStack(LOTRMod.bodyBronze)};
      levyLegs = new ItemStack[]{new ItemStack(Items.field_151026_S), new ItemStack(LOTRMod.legsBronze)};
      levyBoots = new ItemStack[]{new ItemStack(Items.field_151021_T), new ItemStack(LOTRMod.bootsBronze)};
      kaftanColors = new int[]{14823729, 11862016, 5512477, 14196753, 11374145, 7366222};
   }
}
