package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.block.LOTRBlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityChest extends TileEntity implements IInventory {
   private ItemStack[] chestContents = new ItemStack[this.func_70302_i_()];
   public float lidAngle;
   public float prevLidAngle;
   public String textureName;
   private int numPlayersUsing;
   private int ticksSinceSync;
   private String customName;

   public int func_70302_i_() {
      return 27;
   }

   public ItemStack func_70301_a(int i) {
      return this.chestContents[i];
   }

   public ItemStack func_70298_a(int i, int j) {
      if (this.chestContents[i] != null) {
         ItemStack itemstack;
         if (this.chestContents[i].field_77994_a <= j) {
            itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            this.func_70296_d();
            return itemstack;
         } else {
            itemstack = this.chestContents[i].func_77979_a(j);
            if (this.chestContents[i].field_77994_a == 0) {
               this.chestContents[i] = null;
            }

            this.func_70296_d();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack func_70304_b(int i) {
      if (this.chestContents[i] != null) {
         ItemStack itemstack = this.chestContents[i];
         this.chestContents[i] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void func_70299_a(int i, ItemStack itemstack) {
      this.chestContents[i] = itemstack;
      if (itemstack != null && itemstack.field_77994_a > this.func_70297_j_()) {
         itemstack.field_77994_a = this.func_70297_j_();
      }

      this.func_70296_d();
   }

   public String func_145825_b() {
      return this.func_145818_k_() ? this.customName : "container.chest";
   }

   public boolean func_145818_k_() {
      return this.customName != null && this.customName.length() > 0;
   }

   public void setCustomName(String s) {
      this.customName = s;
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      NBTTagList itemTags = nbt.func_150295_c("Items", 10);
      this.chestContents = new ItemStack[this.func_70302_i_()];

      for(int i = 0; i < itemTags.func_74745_c(); ++i) {
         NBTTagCompound slotData = itemTags.func_150305_b(i);
         int slot = slotData.func_74771_c("Slot") & 255;
         if (slot >= 0 && slot < this.chestContents.length) {
            this.chestContents[slot] = ItemStack.func_77949_a(slotData);
         }
      }

      if (nbt.func_150297_b("CustomName", 8)) {
         this.customName = nbt.func_74779_i("CustomName");
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      NBTTagList itemTags = new NBTTagList();

      for(int i = 0; i < this.chestContents.length; ++i) {
         if (this.chestContents[i] != null) {
            NBTTagCompound slotData = new NBTTagCompound();
            slotData.func_74774_a("Slot", (byte)i);
            this.chestContents[i].func_77955_b(slotData);
            itemTags.func_74742_a(slotData);
         }
      }

      nbt.func_74782_a("Items", itemTags);
      if (this.func_145818_k_()) {
         nbt.func_74778_a("CustomName", this.customName);
      }

   }

   public int func_70297_j_() {
      return 64;
   }

   public boolean func_70300_a(EntityPlayer entityplayer) {
      return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && entityplayer.func_70092_e((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
   }

   public void func_145845_h() {
      super.func_145845_h();
      this.prevLidAngle = this.lidAngle;
      ++this.ticksSinceSync;
      float pre;
      if (!this.field_145850_b.field_72995_K && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.field_145851_c + this.field_145848_d + this.field_145849_e) % 200 == 0) {
         this.numPlayersUsing = 0;
         pre = 5.0F;
         List players = this.field_145850_b.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)((float)this.field_145851_c - pre), (double)((float)this.field_145848_d - pre), (double)((float)this.field_145849_e - pre), (double)((float)(this.field_145851_c + 1) + pre), (double)((float)(this.field_145848_d + 1) + pre), (double)((float)(this.field_145849_e + 1) + pre)));
         Iterator var3 = players.iterator();

         while(var3.hasNext()) {
            Object obj = var3.next();
            EntityPlayer entityplayer = (EntityPlayer)obj;
            if (entityplayer.field_71070_bA instanceof ContainerChest) {
               IInventory iinventory = ((ContainerChest)entityplayer.field_71070_bA).func_85151_d();
               if (iinventory == this) {
                  ++this.numPlayersUsing;
               }
            }
         }
      }

      if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
         this.field_145850_b.func_72908_a((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D, "random.chestopen", 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
      }

      if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
         pre = this.lidAngle;
         float incr = 0.1F;
         if (this.numPlayersUsing > 0) {
            this.lidAngle += incr;
         } else {
            this.lidAngle -= incr;
         }

         this.lidAngle = Math.min(this.lidAngle, 1.0F);
         this.lidAngle = Math.max(this.lidAngle, 0.0F);
         float thr = 0.5F;
         if (this.lidAngle < thr && pre >= thr) {
            this.field_145850_b.func_72908_a((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D, "random.chestclosed", 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
         }
      }

   }

   public void func_70295_k_() {
      if (this.numPlayersUsing < 0) {
         this.numPlayersUsing = 0;
      }

      ++this.numPlayersUsing;
      this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, this.numPlayersUsing);
      this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q());
      this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.func_145838_q());
   }

   public void func_70305_f() {
      if (this.func_145838_q() instanceof LOTRBlockChest) {
         --this.numPlayersUsing;
         this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, this.numPlayersUsing);
         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q());
         this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.func_145838_q());
      }

   }

   public boolean func_145842_c(int i, int j) {
      if (i == 1) {
         this.numPlayersUsing = j;
         return true;
      } else {
         return super.func_145842_c(i, j);
      }
   }

   public boolean func_94041_b(int i, ItemStack itemstack) {
      return true;
   }

   public void func_145843_s() {
      super.func_145843_s();
      this.func_145836_u();
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.func_72330_a((double)(this.field_145851_c - 1), (double)this.field_145848_d, (double)(this.field_145849_e - 1), (double)(this.field_145851_c + 2), (double)(this.field_145848_d + 2), (double)(this.field_145849_e + 2));
   }
}
