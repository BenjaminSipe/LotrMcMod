package lotr.common.world.mapgen.tpyr;

import java.util.List;
import java.util.Random;
import lotr.common.world.structure2.LOTRWorldGenTauredainPyramid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentTauredainPyramid extends StructureComponent {
   private int posX;
   private int posY = -1;
   private int posZ;
   private static LOTRWorldGenTauredainPyramid pyramidGen = new LOTRWorldGenTauredainPyramid(false);
   private int direction;
   private static Random pyramidRand;
   private long pyramidSeed = -1L;

   public LOTRComponentTauredainPyramid() {
   }

   public LOTRComponentTauredainPyramid(World world, int l, Random random, int i, int k) {
      super(l);
      int r = LOTRWorldGenTauredainPyramid.RADIUS + 5;
      this.field_74887_e = new StructureBoundingBox(i - r, 0, k - r, i + r, 255, k + r);
      this.posX = i;
      this.posZ = k;
      this.direction = random.nextInt(4);
   }

   protected void func_143012_a(NBTTagCompound nbt) {
      nbt.func_74768_a("PyrX", this.posX);
      nbt.func_74768_a("PyrY", this.posY);
      nbt.func_74768_a("PyrZ", this.posZ);
      nbt.func_74768_a("Direction", this.direction);
      nbt.func_74772_a("Seed", this.pyramidSeed);
   }

   protected void func_143011_b(NBTTagCompound nbt) {
      this.posX = nbt.func_74762_e("PyrX");
      this.posY = nbt.func_74762_e("PyrY");
      this.posZ = nbt.func_74762_e("PyrZ");
      this.direction = nbt.func_74762_e("Direction");
      this.pyramidSeed = nbt.func_74763_f("Seed");
   }

   public void func_74861_a(StructureComponent component, List list, Random random) {
   }

   public boolean func_74875_a(World world, Random random, StructureBoundingBox structureBoundingBox) {
      if (this.posY == -1) {
         this.posY = world.func_72825_h(structureBoundingBox.func_78881_e(), structureBoundingBox.func_78891_g());
      }

      if (this.pyramidSeed == -1L) {
         this.pyramidSeed = random.nextLong();
      }

      pyramidGen.setStructureBB(structureBoundingBox);
      pyramidRand.setSeed(this.pyramidSeed);
      pyramidGen.generateWithSetRotation(world, pyramidRand, this.posX, this.posY, this.posZ, this.direction);
      return true;
   }

   static {
      pyramidGen.restrictions = false;
      pyramidRand = new Random();
   }
}
