package lotr.common.entity.item;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityFallingTreasure extends Entity implements IEntityAdditionalSpawnData {
   public Block theBlock;
   public int theBlockMeta;
   private int ticksFalling;

   public LOTREntityFallingTreasure(World world) {
      super(world);
   }

   public LOTREntityFallingTreasure(World world, double d, double d1, double d2, Block block) {
      this(world, d, d1, d2, block, 0);
   }

   public LOTREntityFallingTreasure(World world, double d, double d1, double d2, Block block, int meta) {
      super(world);
      this.blockMetaConstructor(d, d1, d2, block, meta);
   }

   private void blockMetaConstructor(double d, double d1, double d2, Block block, int meta) {
      this.theBlock = block;
      this.theBlockMeta = meta;
      this.field_70156_m = true;
      this.func_70105_a(0.98F, 0.98F);
      this.field_70129_M = this.field_70131_O / 2.0F;
      this.func_70107_b(d, d1, d2);
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      this.field_70169_q = d;
      this.field_70167_r = d1;
      this.field_70166_s = d2;
   }

   public void writeSpawnData(ByteBuf data) {
      data.writeDouble(this.field_70169_q);
      data.writeDouble(this.field_70167_r);
      data.writeDouble(this.field_70166_s);
      data.writeInt(Block.func_149682_b(this.theBlock));
      data.writeByte(this.theBlockMeta);
   }

   public void readSpawnData(ByteBuf data) {
      double x = data.readDouble();
      double y = data.readDouble();
      double z = data.readDouble();
      Block block = Block.func_149729_e(data.readInt());
      int meta = data.readByte();
      this.blockMetaConstructor(x, y, z, block, meta);
   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected void func_70088_a() {
   }

   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   public void func_70071_h_() {
      if (this.theBlock.func_149688_o() == Material.field_151579_a) {
         this.func_70106_y();
      } else {
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         ++this.ticksFalling;
         this.field_70181_x -= 0.04D;
         this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.98D;
         this.field_70181_x *= 0.98D;
         this.field_70179_y *= 0.98D;
         if (!this.field_70170_p.field_72995_K) {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            Block block = this.field_70170_p.func_147439_a(i, j, k);
            int meta = this.field_70170_p.func_72805_g(i, j, k);
            if (this.ticksFalling == 1) {
               if (block != this.theBlock) {
                  this.func_70106_y();
                  return;
               }

               this.field_70170_p.func_147468_f(i, j, k);
            }

            if (this.field_70122_E) {
               this.field_70159_w *= 0.7D;
               this.field_70179_y *= 0.7D;
               this.field_70181_x *= -0.5D;
               if (block != Blocks.field_150326_M) {
                  this.func_70106_y();
                  boolean placedTreasure = false;
                  if (block == this.theBlock && meta < 7) {
                     while(this.theBlockMeta >= 0 && meta < 7) {
                        --this.theBlockMeta;
                        ++meta;
                     }

                     this.field_70170_p.func_72921_c(i, j, k, meta, 3);
                     placedTreasure = true;
                     ++j;
                  }

                  if (this.theBlockMeta >= 0) {
                     if (this.field_70170_p.func_147472_a(this.theBlock, i, j, k, true, 1, (Entity)null, (ItemStack)null) && this.field_70170_p.func_147465_d(i, j, k, this.theBlock, this.theBlockMeta, 3)) {
                        placedTreasure = true;
                     } else {
                        this.func_70099_a(new ItemStack(this.theBlock, this.theBlock.quantityDropped(this.theBlockMeta, 0, this.field_70146_Z), this.theBlock.func_149692_a(this.theBlockMeta)), 0.0F);
                     }
                  }

                  if (placedTreasure) {
                     SoundType stepSound = this.theBlock.field_149762_H;
                     this.field_70170_p.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), stepSound.func_150496_b(), (stepSound.func_150497_c() + 1.0F) / 2.0F, stepSound.func_150494_d() * 0.8F);
                  }
               }
            } else if (this.ticksFalling > 100 && !this.field_70170_p.field_72995_K && (j < 1 || j > 256 || this.ticksFalling > 600)) {
               this.func_70099_a(new ItemStack(this.theBlock, this.theBlock.quantityDropped(this.theBlockMeta, 0, this.field_70146_Z), this.theBlock.func_149692_a(this.theBlockMeta)), 0.0F);
               this.func_70106_y();
            }
         }
      }

   }

   protected void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74768_a("TileID", Block.func_149682_b(this.theBlock));
      nbt.func_74774_a("Data", (byte)this.theBlockMeta);
      nbt.func_74774_a("Time", (byte)this.ticksFalling);
   }

   protected void func_70037_a(NBTTagCompound nbt) {
      this.theBlock = Block.func_149729_e(nbt.func_74762_e("TileID"));
      this.theBlockMeta = nbt.func_74771_c("Data") & 255;
      this.ticksFalling = nbt.func_74771_c("Time") & 255;
      if (this.theBlock.func_149688_o() == Material.field_151579_a) {
         this.theBlock = Blocks.field_150354_m;
      }

   }

   @SideOnly(Side.CLIENT)
   public float func_70053_R() {
      return 0.0F;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_90999_ad() {
      return false;
   }
}
