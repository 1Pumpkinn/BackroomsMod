package net.tyrone.backroomsmod.init;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.registries.DeferredRegister;
import net.tyrone.backroomsmod.BackroomsMod;

public class BackroomsDimensions {
    public static final DeferredRegister<DimensionType> DIMENSIONS =
            DeferredRegister.create(Registries.DIMENSION_TYPE, BackroomsMod.MODID);

    public static final ResourceKey<Level> BACKROOMS_DIMENSION =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(BackroomsMod.MODID, "backrooms"));

    public static final ResourceKey<DimensionType> BACKROOMS_DIMENSION_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(BackroomsMod.MODID, "backrooms"));
}