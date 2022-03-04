package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.util.LOTRColorUtil;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBreeGuard extends LOTREntityBreeMan {
   private static final ItemStack[] guardWeapons;
   private static final int[] leatherDyes;

   public LOTREntityBreeGuard(World world) {
      super(world);
      this.addTargetTasks(true);
      this.npcShield = LOTRShields.ALIGNMENT_BREE;
   }

   protected int addBreeAttackAI(int prio) {
      this.field_70714_bg.func_75776_a(prio, new LOTREntityAIAttackOnCollide(this, 1.45D, false));
      return prio;
   }

   protected void addBreeAvoidAI(int prio) {
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(guardWeapons.length);
      this.npcItemsInv.setMeleeWeapon(guardWeapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_70062_b(1, new ItemStack(Items.field_151029_X));
      } else {
         this.func_70062_b(1, LOTRColorUtil.dyeLeather(new ItemStack(Items.field_151021_T), 3354152));
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_70062_b(2, new ItemStack(Items.field_151022_W));
      } else {
         this.func_70062_b(2, LOTRColorUtil.dyeLeather(new ItemStack(Items.field_151026_S), leatherDyes, this.field_70146_Z));
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_70062_b(3, new ItemStack(Items.field_151023_V));
      } else {
         this.func_70062_b(3, LOTRColorUtil.dyeLeather(new ItemStack(Items.field_151027_R), leatherDyes, this.field_70146_Z));
      }

      this.func_70062_b(4, new ItemStack(Items.field_151028_Y));
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "bree/guard/hired" : "bree/guard/friendly";
      } else {
         return "bree/guard/hostile";
      }
   }

   static {
      guardWeapons = new ItemStack[]{new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151040_l), new ItemStack(LOTRMod.pikeIron)};
      leatherDyes = new int[]{11373426, 7823440, 5983041, 9535090};
   }
}
