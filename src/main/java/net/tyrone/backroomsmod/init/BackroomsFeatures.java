package net.tyrone.backroomsmod.init;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tyrone.backroomsmod.BackroomsMod;
import net.tyrone.backroomsmod.world.structure.BackroomsStructure;

public class BackroomsFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, BackroomsMod.MODID);

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, BackroomsMod.MODID);

    public static final DeferredRegister<StructureSet> STRUCTURE_SETS =
            DeferredRegister.create(Registries.STRUCTURE_SET, BackroomsMod.MODID);

    public static final DeferredRegister<Structure> FEATURES =
            DeferredRegister.create(Registries.STRUCTURE, BackroomsMod.MODID);

    public static final ResourceKey<Structure> BACKROOMS_STRUCTURE =
            ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BackroomsMod.MODID, "backrooms_structure"));

    public static final ResourceKey<StructureSet> BACKROOMS_STRUCTURE_SET =
            ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(BackroomsMod.MODID, "backrooms_structure_set"));

    public static final ResourceKey<PlacedFeature> BACKROOMS_STRUCTURE_PLACED =
            ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(BackroomsMod.MODID, "backrooms_structure_placed"));

    public static void bootstrapStructures(BootstapContext<Structure> context) {
        context.register(BACKROOMS_STRUCTURE, new BackroomsStructure(
                new Structure.StructureSettings.Builder(
                        context.lookup(Registries.BIOME).getOrThrow(BackroomsBiomes.BACKROOMS_BIOME)
                ).build()
        ));
    }

    public static void bootstrapStructureSets(BootstapContext<StructureSet> context) {
        Holder<Structure> backroomsStructure = context.lookup(Registries.STRUCTURE)
                .getOrThrow(BACKROOMS_STRUCTURE);

        context.register(BACKROOMS_STRUCTURE_SET, new StructureSet(
                backroomsStructure,
                new RandomSpreadStructurePlacement(16, 8, RandomSpreadType.LINEAR, 123456789)
        ));
    }
}