package lotr.common.tileentity;

import lotr.client.render.tileentity.PlateTileEntityRenderer;
import lotr.common.init.LOTRTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;

public class PlateTileEntity extends TileEntity {
   private ItemStack foodItem;
   private LazyOptional fallingDataForRender;

   public static boolean isValidFoodItem(ItemStack itemstack) {
      if (!itemstack.func_190926_b()) {
         Item item = itemstack.func_77973_b();
         if (item.func_219971_r()) {
            if (!(item instanceof SoupItem) && !(item instanceof SuspiciousStewItem)) {
               if (item.hasContainerItem(itemstack)) {
                  return false;
               }

               return true;
            }

            return true;
         }

         if (item == Items.field_151054_z) {
            return true;
         }
      }

      return false;
   }

   public static int getMaxStackSizeOnPlate(ItemStack itemstack) {
      return itemstack.func_77973_b() == Items.field_151054_z ? 1 : itemstack.func_77976_d();
   }

   public PlateTileEntity() {
      super((TileEntityType)LOTRTileEntities.PLATE.get());
      this.foodItem = ItemStack.field_190927_a;
      this.fallingDataForRender = LazyOptional.empty();
   }

   public ItemStack getFoodItem() {
      return this.foodItem;
   }

   public void setFoodItem(ItemStack item) {
      this.foodItem = item;
      this.func_70296_d();
      if (this.func_145830_o()) {
         this.func_145831_w().func_184138_a(this.func_174877_v(), this.func_195044_w(), this.func_195044_w(), 3);
      }

   }

   public void func_230337_a_(BlockState state, CompoundNBT nbt) {
      super.func_230337_a_(state, nbt);
      this.readFood(nbt);
   }

   private void readFood(CompoundNBT nbt) {
      this.foodItem = ItemStack.func_199557_a(nbt.func_74775_l("FoodItem"));
   }

   public CompoundNBT func_189515_b(CompoundNBT nbt) {
      super.func_189515_b(nbt);
      this.writeFood(nbt);
      return nbt;
   }

   private void writeFood(CompoundNBT nbt) {
      nbt.func_218657_a("FoodItem", this.foodItem.func_77955_b(new CompoundNBT()));
   }

   public CompoundNBT func_189517_E_() {
      return this.func_189515_b(new CompoundNBT());
   }

   public SUpdateTileEntityPacket func_189518_D_() {
      CompoundNBT nbt = new CompoundNBT();
      this.writeFood(nbt);
      return new SUpdateTileEntityPacket(this.field_174879_c, 0, nbt);
   }

   public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
      this.readFood(pkt.func_148857_g());
   }

   @OnlyIn(Dist.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      AxisAlignedBB bb = super.getRenderBoundingBox();
      if (!this.foodItem.func_190926_b()) {
         float itemHeight = PlateTileEntityRenderer.getItemHeight(this.foodItem);
         bb = bb.func_72321_a(0.0D, (double)((float)this.foodItem.func_190916_E() * itemHeight), 0.0D);
      }

      return bb;
   }

   public void setFallingDataForRender(LazyOptional fallingData) {
      this.fallingDataForRender = fallingData;
   }

   public LazyOptional getFallingDataForRender() {
      return this.fallingDataForRender;
   }
}
