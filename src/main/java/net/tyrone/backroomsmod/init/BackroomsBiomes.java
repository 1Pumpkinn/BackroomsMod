package net.tyrone.backroomsmod.init;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraftforge.registries.DeferredRegister;
import net.tyrone.backroomsmod.BackroomsMod;

public class BackroomsBiomes {
    public static final DeferredRegister<Biome> BIOMES =
            DeferredRegister.create(Registries.BIOME, BackroomsMod.MODID);

    public static final ResourceKey<Biome> BACKROOMS_BIOME =
            ResourceKey.create(Registries.BIOME, new ResourceLocation(BackroomsMod.MODID, "backrooms"));

    public static final ResourceKey<Biome> MOLDY_BACKROOMS_BIOME =
            ResourceKey.create(Registries.BIOME, new ResourceLocation(BackroomsMod.MODID, "moldy_backrooms"));

    public static void bootstrap(BootstapContext<Biome> context) {
        // Main backrooms biome
        context.register(BACKROOMS_BIOME, createBackroomsBiome(false));

        // Moldy variant
        context.register(MOLDY_BACKROOMS_BIOME, createBackroomsBiome(true));
    }

    private static Biome createBackroomsBiome(boolean moldy) {
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();

        // Add backrooms structure generation
        generationSettings.addFeature(net.minecraft.world.level.levelgen.GenerationStep.Decoration.SURFACE_STRUCTURES,
                BackroomsFeatures.BACKROOMS_STRUCTURE_PLACED);

        // Mob spawn settings - mostly empty for that eerie feeling
        MobSpawnSettings.Builder mobSpawnSettings = new MobSpawnSettings.Builder();

        // Occasionally spawn hostile mobs for danger
        mobSpawnSettings.addSpawn(MobCategory.MONSTER,
                new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.ZOMBIE, 1, 1, 1));

        // Special effects
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .fogColor(0xC0D8FF) // Yellowish fog
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .skyColor(0x77ADFF)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_CREATIVE));

        if (moldy) {
            effects.foliageColorOverride(0x4E7A34) // Moldy green
                    .grassColorOverride(0x4E7A34);
        } else {
            effects.foliageColorOverride(0xE6E651) // Backrooms yellow
                    .grassColorOverride(0xE6E651);
        }

        // Add particle effects for atmosphere
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.MYCELIUM, 0.001f));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.8f)
                .downfall(0.0f)
                .specialEffects(effects.build())
                .mobSpawnSettings(mobSpawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}