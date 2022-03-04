package lotr.common.entity.ai.goal;

import java.util.Random;
import java.util.stream.IntStream;
import lotr.common.entity.animal.CaracalEntity;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CaracalRaidChestGoal extends MoveToBlockGoal {
   private final CaracalEntity theCaracal;
   private final Random rand;
   private int raidingTick = 0;
   private boolean isChestEmpty = false;

   public CaracalRaidChestGoal(CaracalEntity caracal, double speed) {
      super(caracal, speed, 8, 2);
      this.theCaracal = caracal;
      this.rand = this.theCaracal.func_70681_au();
   }

   public boolean func_75250_a() {
      return !this.theCaracal.hasItemInMouth() && !this.theCaracal.func_233685_eM_() && super.func_75250_a();
   }

   protected int func_203109_a(CreatureEntity entity) {
      return LOTRUtil.secondsToTicks(15 + this.rand.nextInt(30));
   }

   protected boolean func_179488_a(IWorldReader world, BlockPos pos) {
      if (world.func_175623_d(pos.func_177984_a())) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof IInventory) {
            IInventory inv = (IInventory)te;
            IntStream var10000 = IntStream.range(0, inv.func_70302_i_());
            inv.getClass();
            return var10000.mapToObj(inv::func_70301_a).anyMatch(this::isWorthRaidingChestFor);
         }
      }

      return false;
   }

   private boolean isWorthRaidingChestFor(ItemStack stack) {
      return !stack.func_190926_b() && CaracalEntity.WANTS_TO_EAT.test(stack);
   }

   public void func_75249_e() {
      this.raidingTick = 0;
      this.isChestEmpty = false;
      super.func_75249_e();
   }

   public double func_203110_f() {
      return 1.5D;
   }

   public boolean func_203108_i() {
      return super.func_203108_i() && !this.func_179487_f();
   }

   public void func_75246_d() {
      super.func_75246_d();
      this.theCaracal.func_233686_v_(false);
      if (this.func_179487_f()) {
         this.theCaracal.func_70661_as().func_75499_g();
         this.theCaracal.func_70671_ap().func_220674_a(Vector3d.func_237489_a_(this.field_179494_b));
         this.theCaracal.setIsRaidingChest(true);
         ++this.raidingTick;
         if (this.raidingTick > 20 && this.rand.nextInt(10) == 0) {
            World world = this.theCaracal.field_70170_p;
            TileEntity te = world.func_175625_s(this.field_179494_b);
            if (te instanceof IInventory) {
               IInventory inv = (IInventory)te;
               int[] occupiedSlots = IntStream.range(0, inv.func_70302_i_()).filter((slotx) -> {
                  return !inv.func_70301_a(slotx).func_190926_b();
               }).toArray();
               if (occupiedSlots.length > 0) {
                  int slot = Util.func_240988_a_(occupiedSlots, this.rand);
                  ItemStack dropStack = inv.func_70298_a(slot, 1 + this.rand.nextInt(4));
                  if (this.theCaracal.canEatItem(dropStack) && this.rand.nextInt(3) == 0) {
                     this.theCaracal.setItemInMouth(dropStack);
                  } else {
                     ItemEntity dropEntity = new ItemEntity(world, (double)this.field_179494_b.func_177958_n() + 0.5D, (double)(this.field_179494_b.func_177956_o() + 1), (double)this.field_179494_b.func_177952_p() + 0.5D, dropStack);
                     dropEntity.func_213317_d(dropEntity.func_213322_ci().func_186678_a(2.0D));
                     world.func_217376_c(dropEntity);
                     world.func_184133_a((PlayerEntity)null, this.field_179494_b, SoundEvents.field_187552_ah, SoundCategory.NEUTRAL, 0.5F, 0.8F + this.rand.nextFloat() * 0.2F);
                  }
               } else {
                  this.isChestEmpty = true;
               }
            }
         }
      } else {
         if (this.raidingTick > 0) {
            --this.raidingTick;
         }

         this.theCaracal.setIsRaidingChest(false);
      }

   }

   public boolean func_75253_b() {
      if (this.raidingTick > 200) {
         return false;
      } else if (this.isChestEmpty) {
         return false;
      } else {
         return this.theCaracal.hasItemInMouth() ? false : super.func_75253_b();
      }
   }

   public void func_75251_c() {
      super.func_75251_c();
      this.theCaracal.setIsRaidingChest(false);
   }
}
