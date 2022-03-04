package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockCoralReef extends Block {
   private IIcon[] plantIcons;
   private static final String[] plantNames = new String[]{"purple", "yellow", "blue", "red", "green"};
   private static final Random iconRand = new Random();

   public LOTRBlockCoralReef() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      this.plantIcons = new IIcon[plantNames.length];

      for(int i = 0; i < plantNames.length; ++i) {
         this.plantIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + plantNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return super.func_149691_a(i, j);
   }

   public IIcon getRandomPlantIcon(int i, int j, int k) {
      int hash = i * 25799626 ^ k * 6879038 ^ j;
      iconRand.setSeed((long)hash);
      iconRand.setSeed(iconRand.nextLong());
      return this.plantIcons[iconRand.nextInt(this.plantIcons.length)];
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getCoralRenderID();
   }

   public Item func_149650_a(int i, Random random, int j) {
      return LOTRMod.coral;
   }

   public int func_149745_a(Random random) {
      return 1 + random.nextInt(2);
   }

   public int func_149679_a(int i, Random random) {
      int drops = this.func_149745_a(random);
      if (i > 0) {
         int factor = random.nextInt(i + 2) - 1;
         factor = Math.max(factor, 0);
         drops *= factor + 1;
      }

      return drops;
   }

   public void func_149690_a(World world, int i, int j, int k, int meta, float f, int fortune) {
      super.func_149690_a(world, i, j, k, meta, f, fortune);
      int amountXp = MathHelper.func_76136_a(world.field_73012_v, 0, 2);
      this.func_149657_c(world, i, j, k, amountXp);
   }

   public void func_149724_b(World world, int i, int j, int k, Entity entity) {
      if (entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob)) {
         entity.func_70097_a(DamageSource.field_76367_g, 0.5F);
      }

   }
}
