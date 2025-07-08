package net.tyrone.backroomsmod.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.stream.Stream;

public class BackroomsBiomeSource extends BiomeSource {
    public static final Codec<BackroomsBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(source -> source.biomes),
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(source -> source.biomeList)
            ).apply(instance, BackroomsBiomeSource::new));

    private final Registry<Biome> biomes;
    private final List<Holder<Biome>> biomeList;

    public BackroomsBiomeSource(Registry<Biome> biomes, List<Holder<Biome>> biomeList) {
        super(biomeList.stream());
        this.biomes = biomes;
        this.biomeList = biomeList;
    }

    public BackroomsBiomeSource(List<Holder<Biome>> biomeList) {
        super(biomeList.stream());
        this.biomes = null;
        this.biomeList = biomeList;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomeList.stream();
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        // Simple biome selection based on position
        // 90% chance for normal backrooms, 10% for moldy
        long hash = (long) x * 341873128712L + (long) z * 132897987541L;
        if (Math.abs(hash) % 10 == 0) {
            return biomeList.get(1); // Moldy backrooms
        } else {
            return biomeList.get(0); // Normal backrooms
        }
    }
}