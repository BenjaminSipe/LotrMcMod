package lotr.common.entity.item;

import java.util.function.Supplier;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTREntities;
import lotr.common.init.LOTRItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LOTRBoatEntity extends BoatEntity {
   private static final DataParameter MOD_BOAT_TYPE;

   public LOTRBoatEntity(EntityType type, World w) {
      super(type, w);
      this.field_70156_m = true;
   }

   public LOTRBoatEntity(World w, double x, double y, double z) {
      this((EntityType)LOTREntities.BOAT.get(), w);
      this.func_70107_b(x, y, z);
      this.func_213317_d(Vector3d.field_186680_a);
      this.field_70169_q = x;
      this.field_70167_r = y;
      this.field_70166_s = z;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(MOD_BOAT_TYPE, LOTRBoatEntity.ModBoatType.PINE.ordinal());
   }

   public void setModBoatType(LOTRBoatEntity.ModBoatType type) {
      this.field_70180_af.func_187227_b(MOD_BOAT_TYPE, type.ordinal());
   }

   public LOTRBoatEntity.ModBoatType getModBoatType() {
      return LOTRBoatEntity.ModBoatType.byId((Integer)this.field_70180_af.func_187225_a(MOD_BOAT_TYPE));
   }

   protected void func_213281_b(CompoundNBT nbt) {
      super.func_213281_b(nbt);
      nbt.func_74778_a("ModType", this.getModBoatType().getName());
   }

   protected void func_70037_a(CompoundNBT nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_150297_b("ModType", 8)) {
         this.setModBoatType(LOTRBoatEntity.ModBoatType.getTypeFromString(nbt.func_74779_i("ModType")));
      }

   }

   public Item func_184455_j() {
      return this.getModBoatType().asBoatItem();
   }

   public ItemEntity func_199703_a(IItemProvider item) {
      if (item == this.func_184453_r().func_195933_b()) {
         item = this.getModBoatType().asPlank();
      }

      return super.func_199703_a((IItemProvider)item);
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static {
      MOD_BOAT_TYPE = EntityDataManager.func_187226_a(LOTRBoatEntity.class, DataSerializers.field_187192_b);
   }

   public static enum ModBoatType {
      PINE(LOTRBlocks.PINE_PLANKS, LOTRItems.PINE_BOAT, "pine"),
      MALLORN(LOTRBlocks.MALLORN_PLANKS, LOTRItems.MALLORN_BOAT, "mallorn"),
      MIRK_OAK(LOTRBlocks.MIRK_OAK_PLANKS, LOTRItems.MIRK_OAK_BOAT, "mirk_oak"),
      CHARRED(LOTRBlocks.CHARRED_PLANKS, LOTRItems.CHARRED_BOAT, "charred"),
      APPLE(LOTRBlocks.APPLE_PLANKS, LOTRItems.APPLE_BOAT, "apple"),
      PEAR(LOTRBlocks.PEAR_PLANKS, LOTRItems.PEAR_BOAT, "pear"),
      CHERRY(LOTRBlocks.CHERRY_PLANKS, LOTRItems.CHERRY_BOAT, "cherry"),
      LEBETHRON(LOTRBlocks.LEBETHRON_PLANKS, LOTRItems.LEBETHRON_BOAT, "lebethron"),
      BEECH(LOTRBlocks.BEECH_PLANKS, LOTRItems.BEECH_BOAT, "beech"),
      MAPLE(LOTRBlocks.MAPLE_PLANKS, LOTRItems.MAPLE_BOAT, "maple"),
      ASPEN(LOTRBlocks.ASPEN_PLANKS, LOTRItems.ASPEN_BOAT, "aspen"),
      LAIRELOSSE(LOTRBlocks.LAIRELOSSE_PLANKS, LOTRItems.LAIRELOSSE_BOAT, "lairelosse"),
      CEDAR(LOTRBlocks.CEDAR_PLANKS, LOTRItems.CEDAR_BOAT, "cedar"),
      FIR(LOTRBlocks.FIR_PLANKS, LOTRItems.FIR_BOAT, "fir"),
      LARCH(LOTRBlocks.LARCH_PLANKS, LOTRItems.LARCH_BOAT, "larch"),
      HOLLY(LOTRBlocks.HOLLY_PLANKS, LOTRItems.HOLLY_BOAT, "holly"),
      GREEN_OAK(LOTRBlocks.GREEN_OAK_PLANKS, LOTRItems.GREEN_OAK_BOAT, "green_oak"),
      CYPRESS(LOTRBlocks.CYPRESS_PLANKS, LOTRItems.CYPRESS_BOAT, "cypress"),
      ROTTEN(LOTRBlocks.ROTTEN_PLANKS, LOTRItems.ROTTEN_BOAT, "rotten"),
      CULUMALDA(LOTRBlocks.CULUMALDA_PLANKS, LOTRItems.CULUMALDA_BOAT, "culumalda");

      private final Supplier plankSup;
      private final Supplier boatSup;
      private final String name;

      private ModBoatType(Supplier plank, Supplier boat, String s) {
         this.plankSup = plank;
         this.boatSup = boat;
         this.name = s;
      }

      public String getName() {
         return this.name;
      }

      public Block asPlank() {
         return (Block)this.plankSup.get();
      }

      public Item asBoatItem() {
         return (Item)this.boatSup.get();
      }

      public String toString() {
         return this.name;
      }

      public static LOTRBoatEntity.ModBoatType byId(int id) {
         LOTRBoatEntity.ModBoatType[] types = values();
         if (id < 0 || id >= types.length) {
            id = 0;
         }

         return types[id];
      }

      public static LOTRBoatEntity.ModBoatType getTypeFromString(String nameIn) {
         LOTRBoatEntity.ModBoatType[] types = values();

         for(int i = 0; i < types.length; ++i) {
            if (types[i].getName().equals(nameIn)) {
               return types[i];
            }
         }

         return types[0];
      }
   }
}
