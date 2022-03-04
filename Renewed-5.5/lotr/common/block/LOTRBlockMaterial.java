package lotr.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class LOTRBlockMaterial {
   public static final Material CRYSTAL;
   public static final Material ICE_BRICK;
   public static final Material SNOW_BRICK;
   public static final Material PALANTIR;

   static {
      CRYSTAL = (new LOTRBlockMaterial.Builder(MaterialColor.field_151660_b)).notOpaque().notSolid().pushDestroys().build();
      ICE_BRICK = (new LOTRBlockMaterial.Builder(MaterialColor.field_151657_g)).build();
      SNOW_BRICK = (new LOTRBlockMaterial.Builder(MaterialColor.field_151666_j)).build();
      PALANTIR = (new LOTRBlockMaterial.Builder(MaterialColor.field_151660_b)).notOpaque().notSolid().build();
   }

   private static class Builder {
      private PushReaction pushReaction;
      private boolean blocksMovement;
      private boolean canBurn;
      private boolean isLiquid;
      private boolean isReplaceable;
      private boolean isSolid;
      private final MaterialColor color;
      private boolean isOpaque;

      public Builder(MaterialColor c) {
         this.pushReaction = PushReaction.NORMAL;
         this.blocksMovement = true;
         this.isSolid = true;
         this.isOpaque = true;
         this.color = c;
      }

      public LOTRBlockMaterial.Builder liquid() {
         this.isLiquid = true;
         return this;
      }

      public LOTRBlockMaterial.Builder notSolid() {
         this.isSolid = false;
         return this;
      }

      public LOTRBlockMaterial.Builder doesNotBlockMovement() {
         this.blocksMovement = false;
         return this;
      }

      public LOTRBlockMaterial.Builder notOpaque() {
         this.isOpaque = false;
         return this;
      }

      public LOTRBlockMaterial.Builder flammable() {
         this.canBurn = true;
         return this;
      }

      public LOTRBlockMaterial.Builder replaceable() {
         this.isReplaceable = true;
         return this;
      }

      public LOTRBlockMaterial.Builder pushDestroys() {
         this.pushReaction = PushReaction.DESTROY;
         return this;
      }

      public LOTRBlockMaterial.Builder pushBlocks() {
         this.pushReaction = PushReaction.BLOCK;
         return this;
      }

      public Material build() {
         return new Material(this.color, this.isLiquid, this.isSolid, this.blocksMovement, this.isOpaque, this.canBurn, this.isReplaceable, this.pushReaction);
      }
   }
}
