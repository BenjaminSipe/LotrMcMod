package lotr.common.entity.npc;

import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityNPCRideable extends LOTREntityNPC implements LOTRNPCMount {
   private UUID tamingPlayer;
   private int npcTemper;

   public LOTREntityNPCRideable(World world) {
      super(world);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public boolean isNPCTamed() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setNPCTamed(boolean flag) {
      this.field_70180_af.func_75692_b(17, (byte)(flag ? 1 : 0));
   }

   public boolean isMountArmorValid(ItemStack itemstack) {
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemMountArmor) {
         LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.func_77973_b();
         return armor.isValid(this);
      } else {
         return false;
      }
   }

   public IInventory getMountInventory() {
      return null;
   }

   public void openGUI(EntityPlayer entityplayer) {
      IInventory inv = this.getMountInventory();
      if (inv != null && !this.field_70170_p.field_72995_K && (this.field_70153_n == null || this.field_70153_n == entityplayer) && this.isNPCTamed()) {
         entityplayer.openGui(LOTRMod.instance, 29, this.field_70170_p, this.func_145782_y(), inv.func_70302_i_(), 0);
      }

   }

   public void tameNPC(EntityPlayer entityplayer) {
      this.setNPCTamed(true);
      this.tamingPlayer = entityplayer.func_110124_au();
   }

   public EntityPlayer getTamingPlayer() {
      return this.field_70170_p.func_152378_a(this.tamingPlayer);
   }

   public boolean func_70692_ba() {
      return super.func_70692_ba() && !this.isNPCTamed();
   }

   public boolean canRenameNPC() {
      return this.isNPCTamed() ? true : super.canRenameNPC();
   }

   public void func_70636_d() {
      super.func_70636_d();
      LOTRMountFunctions.update(this);
   }

   public void func_70612_e(float strafe, float forward) {
      LOTRMountFunctions.move(this, strafe, forward);
   }

   public void super_moveEntityWithHeading(float strafe, float forward) {
      super.func_70612_e(strafe, forward);
   }

   public float getStepHeightWhileRiddenByPlayer() {
      return 1.0F;
   }

   public final double func_70042_X() {
      double d = this.getBaseMountedYOffset();
      if (this.field_70153_n != null) {
         d += (double)this.field_70153_n.field_70129_M - this.field_70153_n.func_70033_W();
      }

      return d;
   }

   protected double getBaseMountedYOffset() {
      return (double)this.field_70131_O * 0.5D;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("NPCTamed", this.isNPCTamed());
      if (this.tamingPlayer != null) {
         nbt.func_74778_a("NPCTamer", this.tamingPlayer.toString());
      }

      nbt.func_74768_a("NPCTemper", this.npcTemper);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setNPCTamed(nbt.func_74767_n("NPCTamed"));
      if (nbt.func_74764_b("NPCTamer")) {
         this.tamingPlayer = UUID.fromString(nbt.func_74779_i("NPCTamer"));
      }

      this.npcTemper = nbt.func_74762_e("NPCTemper");
   }

   public int getMaxNPCTemper() {
      return 100;
   }

   public int getNPCTemper() {
      return this.npcTemper;
   }

   public void setNPCTemper(int i) {
      this.npcTemper = i;
   }

   public int increaseNPCTemper(int i) {
      int temper = MathHelper.func_76125_a(this.getNPCTemper() + i, 0, this.getMaxNPCTemper());
      this.setNPCTemper(temper);
      return this.getNPCTemper();
   }

   public void angerNPC() {
      this.func_85030_a(this.func_70621_aR(), this.func_70599_aP(), this.func_70647_i() * 1.5F);
   }
}
