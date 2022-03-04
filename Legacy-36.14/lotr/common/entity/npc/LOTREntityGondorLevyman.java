package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.util.LOTRColorUtil;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorLevyman extends LOTREntityGondorMan {
   private static ItemStack[] militiaWeapons;
   private static int[] leatherDyes;

   public LOTREntityGondorLevyman(World world) {
      super(world);
      this.addTargetTasks(true);
   }

   protected EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, true);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(militiaWeapons.length);
      this.npcItemsInv.setMeleeWeapon(militiaWeapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, LOTRColorUtil.dyeLeather(new ItemStack(Items.field_151021_T), leatherDyes, this.field_70146_Z));
      this.func_70062_b(2, LOTRColorUtil.dyeLeather(new ItemStack(Items.field_151026_S), leatherDyes, this.field_70146_Z));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGondorGambeson));
      if (this.field_70146_Z.nextInt(3) != 0) {
         this.func_70062_b(4, (ItemStack)null);
      } else {
         i = this.field_70146_Z.nextInt(3);
         if (i == 0) {
            this.func_70062_b(4, new ItemStack(LOTRMod.helmetGondor));
         } else if (i == 1) {
            this.func_70062_b(4, new ItemStack(Items.field_151028_Y));
         } else if (i == 2) {
            this.func_70062_b(4, LOTRColorUtil.dyeLeather(new ItemStack(Items.field_151024_Q), leatherDyes, this.field_70146_Z));
         }
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "gondor/soldier/hired" : "gondor/soldier/friendly";
      } else {
         return "gondor/soldier/hostile";
      }
   }

   static {
      militiaWeapons = new ItemStack[]{new ItemStack(LOTRMod.swordGondor), new ItemStack(LOTRMod.hammerGondor), new ItemStack(LOTRMod.pikeGondor), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151036_c), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze)};
      leatherDyes = new int[]{10855845, 8026746, 5526612, 3684408, 8350297, 10388590, 4799795, 5330539, 4211801, 2632504};
   }
}
