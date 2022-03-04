package lotr.common.tileentity;

import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRTileEntities;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselOperations;
import lotr.common.item.VesselType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IItemProvider;

public class VesselDrinkTileEntity extends TileEntity {
   private ItemStack drinkItem;
   private VesselType vesselType;

   public VesselDrinkTileEntity() {
      super((TileEntityType)LOTRTileEntities.VESSEL_DRINK.get());
      this.drinkItem = ItemStack.field_190927_a;
      this.vesselType = VesselType.WOODEN_MUG;
   }

   public VesselType getVesselType() {
      return this.vesselType;
   }

   public void setVesselType(VesselType vessel) {
      this.vesselType = vessel;
      this.func_70296_d();
      if (this.func_145830_o()) {
         this.func_145831_w().func_184138_a(this.func_174877_v(), this.func_195044_w(), this.func_195044_w(), 3);
      }

   }

   public ItemStack getVesselItem() {
      if (this.drinkItem.func_190926_b()) {
         return this.vesselType.createEmpty();
      } else {
         ItemStack copy = this.drinkItem.func_77946_l();
         if (copy.func_77973_b() instanceof VesselDrinkItem) {
            VesselDrinkItem.setVessel(copy, this.vesselType);
         }

         copy.func_190920_e(1);
         return copy;
      }
   }

   public void setVesselItem(ItemStack itemstack) {
      this.drinkItem = itemstack.func_77946_l();
      this.drinkItem.func_190920_e(1);
      this.func_70296_d();
      if (this.func_145830_o()) {
         this.func_145831_w().func_184138_a(this.func_174877_v(), this.func_195044_w(), this.func_195044_w(), 3);
      }

   }

   public boolean isEmpty() {
      return VesselOperations.isItemEmptyVessel(this.getVesselItem());
   }

   public void setEmpty() {
      this.setVesselItem(this.getVesselType().createEmpty());
   }

   public void fillFromRain() {
      if (!this.field_145850_b.field_72995_K && this.isEmpty()) {
         ItemStack waterItem = new ItemStack((IItemProvider)LOTRItems.WATER_DRINK.get());
         VesselDrinkItem.setVessel(waterItem, this.getVesselType());
         this.setVesselItem(waterItem);
      }

   }

   public void func_230337_a_(BlockState state, CompoundNBT nbt) {
      super.func_230337_a_(state, nbt);
      this.readVessel(nbt);
   }

   private void readVessel(CompoundNBT nbt) {
      this.drinkItem = ItemStack.func_199557_a(nbt.func_74775_l("DrinkItem"));
      this.vesselType = VesselType.forName(nbt.func_74779_i("Vessel"));
   }

   public CompoundNBT func_189515_b(CompoundNBT nbt) {
      super.func_189515_b(nbt);
      this.writeVessel(nbt);
      return nbt;
   }

   private void writeVessel(CompoundNBT nbt) {
      nbt.func_218657_a("DrinkItem", this.drinkItem.func_77955_b(new CompoundNBT()));
      nbt.func_74778_a("Vessel", this.vesselType.getCodeName());
   }

   public CompoundNBT func_189517_E_() {
      return this.func_189515_b(new CompoundNBT());
   }

   public SUpdateTileEntityPacket func_189518_D_() {
      CompoundNBT nbt = new CompoundNBT();
      this.writeVessel(nbt);
      return new SUpdateTileEntityPacket(this.field_174879_c, 0, nbt);
   }

   public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
      this.readVessel(pkt.func_148857_g());
   }
}
