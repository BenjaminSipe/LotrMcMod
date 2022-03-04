package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemDart;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class LOTRTileEntityDartTrap extends TileEntityDispenser {
   private int fireCooldown;

   public String func_145825_b() {
      return this.func_145818_k_() ? this.field_146020_a : "container.lotr.dartTrap";
   }

   public void func_145845_h() {
      super.func_145845_h();
      if (!this.field_145850_b.field_72995_K) {
         if (this.fireCooldown > 0) {
            --this.fireCooldown;
         } else {
            int slot = this.func_146017_i();
            if (slot >= 0) {
               ItemStack itemstack = this.func_70301_a(slot);
               if (itemstack.func_77973_b() instanceof LOTRItemDart) {
                  AxisAlignedBB range = this.getTriggerRange();
                  List entities = this.field_145850_b.func_82733_a(EntityLivingBase.class, range, LOTRMod.selectLivingExceptCreativePlayers());
                  if (!entities.isEmpty()) {
                     IBehaviorDispenseItem dispense = (IBehaviorDispenseItem)BlockDispenser.field_149943_a.func_82594_a(itemstack.func_77973_b());
                     ItemStack result = dispense.func_82482_a(new BlockSourceImpl(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e), itemstack);
                     this.func_70299_a(slot, result.field_77994_a == 0 ? null : result);
                     this.fireCooldown = 20;
                  }
               }
            }
         }
      }

   }

   public AxisAlignedBB getTriggerRange() {
      new BlockSourceImpl(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
      EnumFacing facing = BlockDispenser.func_149937_b(this.func_145832_p());
      float front = 0.55F;
      float range = 16.0F;
      Vec3 vecPos = Vec3.func_72443_a((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D);
      Vec3 vecFront = vecPos.func_72441_c((double)((float)facing.func_82601_c() * front), (double)((float)facing.func_96559_d() * front), (double)((float)facing.func_82599_e() * front));
      Vec3 vecTarget = vecPos.func_72441_c((double)((float)facing.func_82601_c() * range), (double)((float)facing.func_96559_d() * range), (double)((float)facing.func_82599_e() * range));
      MovingObjectPosition hitBlock = this.field_145850_b.func_147447_a(vecFront, vecTarget, true, true, false);
      if (hitBlock != null) {
         vecTarget = Vec3.func_72443_a((double)hitBlock.field_72311_b + 0.5D - (double)facing.func_82601_c(), (double)hitBlock.field_72312_c + 0.5D - (double)facing.func_96559_d(), (double)hitBlock.field_72309_d + 0.5D - (double)facing.func_82599_e());
      }

      float f = 0.0F;
      AxisAlignedBB bb = AxisAlignedBB.func_72330_a((double)((float)this.field_145851_c + f), (double)((float)this.field_145848_d + f), (double)((float)this.field_145849_e + f), (double)((float)(this.field_145851_c + 1) - f), (double)((float)(this.field_145848_d + 1) - f), (double)((float)(this.field_145849_e + 1) - f));
      bb = bb.func_72321_a(vecTarget.field_72450_a - vecPos.field_72450_a, vecTarget.field_72448_b - vecPos.field_72448_b, vecTarget.field_72449_c - vecPos.field_72449_c);
      return bb;
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return this.getTriggerRange();
   }
}
