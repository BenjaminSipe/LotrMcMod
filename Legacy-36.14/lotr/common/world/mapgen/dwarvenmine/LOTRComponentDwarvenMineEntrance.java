package lotr.common.world.mapgen.dwarvenmine;

import java.util.List;
import java.util.Random;
import lotr.common.world.structure2.LOTRWorldGenDwarvenMineEntrance;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentDwarvenMineEntrance extends StructureComponent {
   private int posX;
   private int posY = -1;
   private int posZ;
   private static LOTRWorldGenDwarvenMineEntrance entranceGen = new LOTRWorldGenDwarvenMineEntrance(false);
   private int direction;
   private boolean ruined;

   public LOTRComponentDwarvenMineEntrance() {
   }

   public LOTRComponentDwarvenMineEntrance(World world, int l, Random random, int i, int k, boolean r) {
      super(l);
      this.field_74887_e = new StructureBoundingBox(i - 4, 40, k - 4, i + 4, 256, k + 4);
      this.posX = i;
      this.posZ = k;
      this.ruined = r;
   }

   protected void func_143012_a(NBTTagCompound nbt) {
      nbt.func_74768_a("EntranceX", this.posX);
      nbt.func_74768_a("EntranceY", this.posY);
      nbt.func_74768_a("EntranceZ", this.posZ);
      nbt.func_74768_a("Direction", this.direction);
      nbt.func_74757_a("Ruined", this.ruined);
   }

   protected void func_143011_b(NBTTagCompound nbt) {
      this.posX = nbt.func_74762_e("EntranceX");
      this.posY = nbt.func_74762_e("EntranceY");
      this.posZ = nbt.func_74762_e("EntranceZ");
      this.direction = nbt.func_74762_e("Direction");
      this.ruined = nbt.func_74767_n("Ruined");
   }

   public void func_74861_a(StructureComponent component, List list, Random random) {
      StructureBoundingBox structureBoundingBox = null;
      this.direction = random.nextInt(4);
      switch(this.direction) {
      case 0:
         structureBoundingBox = new StructureBoundingBox(this.posX - 1, this.field_74887_e.field_78895_b + 1, this.posZ + 4, this.posX + 1, this.field_74887_e.field_78895_b + 4, this.posZ + 15);
         break;
      case 1:
         structureBoundingBox = new StructureBoundingBox(this.posX - 15, this.field_74887_e.field_78895_b + 1, this.posZ - 1, this.posX - 4, this.field_74887_e.field_78895_b + 4, this.posZ + 1);
         break;
      case 2:
         structureBoundingBox = new StructureBoundingBox(this.posX - 1, this.field_74887_e.field_78895_b + 1, this.posZ - 15, this.posX + 1, this.field_74887_e.field_78895_b + 4, this.posZ - 4);
         break;
      case 3:
         structureBoundingBox = new StructureBoundingBox(this.posX + 4, this.field_74887_e.field_78895_b + 1, this.posZ - 1, this.posX + 15, this.field_74887_e.field_78895_b + 4, this.posZ + 1);
      }

      LOTRComponentDwarvenMineCorridor corridor = new LOTRComponentDwarvenMineCorridor(0, random, structureBoundingBox, this.direction, this.ruined);
      list.add(corridor);
      corridor.func_74861_a(component, list, random);
   }

   public boolean func_74875_a(World world, Random random, StructureBoundingBox structureBoundingBox) {
      if (this.posY == -1) {
         this.posY = world.func_72825_h(this.posX, this.posZ);
      }

      Block block = world.func_147439_a(this.posX, this.posY - 1, this.posZ);
      if (block != Blocks.field_150349_c) {
         return false;
      } else {
         entranceGen.isRuined = this.ruined;
         entranceGen.generateWithSetRotation(world, random, this.posX, this.posY, this.posZ, this.direction);
         return true;
      }
   }

   static {
      entranceGen.restrictions = false;
   }
}
