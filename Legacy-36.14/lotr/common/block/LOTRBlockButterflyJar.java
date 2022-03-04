package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;

public class LOTRBlockButterflyJar extends LOTRBlockAnimalJar {
   @SideOnly(Side.CLIENT)
   private IIcon glassIcon;
   @SideOnly(Side.CLIENT)
   private IIcon lidIcon;

   public LOTRBlockButterflyJar() {
      super(Material.field_151592_s);
      this.func_149676_a(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149778_k);
   }

   public boolean canCapture(Entity entity) {
      return entity instanceof LOTREntityButterfly;
   }

   public float getJarEntityHeight() {
      return 0.25F;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i == -1 ? this.lidIcon : this.glassIcon;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.glassIcon = iconregister.func_94245_a(this.func_149641_N() + "_glass");
      this.lidIcon = iconregister.func_94245_a(this.func_149641_N() + "_lid");
   }

   @SideOnly(Side.CLIENT)
   public int func_149701_w() {
      return 1;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getButterflyJarRenderID();
   }
}
