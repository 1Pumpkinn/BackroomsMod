package net.tyrone.backroomsmod.world.dimension;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tyrone.backroomsmod.BackroomsMod;
import net.tyrone.backroomsmod.init.BackroomsDimensions;

import java.util.List;
import java.util.OptionalLong;

@Mod.EventBusSubscriber(modid = BackroomsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BackroomsDimension {

    @SubscribeEvent
    public static void registerDimension(ServerAboutToStartEvent event) {
        BackroomsMod.LOGGER.info("Registering Backrooms dimension");
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(BackroomsDimensions.BACKROOMS_DIMENSION_TYPE, new DimensionType(
                OptionalLong.empty(), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                false, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                0.0f, // ambientLight
                new DimensionType.MonsterSettings(
                        false, // piglinSafe
                        false, // hasRaids
                        UniformInt.of(0, 7), // monsterSpawnLightLevel
                        0 // monsterSpawnBlockLightLimit
                )
        ));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        var biomeRegistry = context.lookup(Registries.BIOME);
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        // Create a simple biome source that only uses backrooms biomes
        var biomeSource = new BackroomsBiomeSource(List.of(
                biomeRegistry.getOrThrow(BackroomsBiomes.BACKROOMS_BIOME),
                biomeRegistry.getOrThrow(BackroomsBiomes.MOLDY_BACKROOMS_BIOME)
        ));

        // Use a custom noise generator for flat generation
        var chunkGenerator = new NoiseBasedChunkGenerator(
                biomeSource,
                noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
        );

        var dimensionType = dimensionTypes.getOrThrow(BackroomsDimensions.BACKROOMS_DIMENSION_TYPE);

        context.register(ResourceKey.create(Registries.LEVEL_STEM,
                        BackroomsDimensions.BACKROOMS_DIMENSION.location()),
                new LevelStem(dimensionType, chunkGenerator));
    }
}