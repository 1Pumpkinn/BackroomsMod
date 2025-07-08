package net.tyrone.backroomsmod.init;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tyrone.backroomsmod.BackroomsMod;

public class BackroomsTemplatepools {
    public static final DeferredRegister<StructureTemplatePool> TEMPLATE_POOLS =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_TEMPLATE_POOLS, BackroomsMod.MODID);

    public static final ResourceKey<StructureTemplatePool> BACKROOMS_HALL_START =
            ResourceKey.create(ForgeRegistries.STRUCTURE_TEMPLATE_POOLS.getRegistryKey(),
                    new ResourceLocation(BackroomsMod.MODID, "backrooms/hall_start"));

    public static final ResourceKey<StructureTemplatePool> BACKROOMS_HALLS =
            ResourceKey.create(ForgeRegistries.STRUCTURE_TEMPLATE_POOLS.getRegistryKey(),
                    new ResourceLocation(BackroomsMod.MODID, "backrooms/halls"));

    public static final ResourceKey<StructureTemplatePool> BACKROOMS_ROOMS =
            ResourceKey.create(ForgeRegistries.STRUCTURE_TEMPLATE_POOLS.getRegistryKey(),
                    new ResourceLocation(BackroomsMod.MODID, "backrooms/rooms"));

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        Holder<StructureTemplatePool> empty = context.lookup(Pools.EMPTY);

        // Hall start pool
        context.register(BACKROOMS_HALL_START, new StructureTemplatePool(
                empty,
                ImmutableList.of(
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/hall_start").apply(StructureTemplatePool.Projection.RIGID)
                ),
                StructureTemplatePool.Projection.RIGID
        ));

        // Halls pool
        context.register(BACKROOMS_HALLS, new StructureTemplatePool(
                empty,
                ImmutableList.of(
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/hall_straight").apply(StructureTemplatePool.Projection.RIGID),
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/hall_corner").apply(StructureTemplatePool.Projection.RIGID),
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/hall_t_junction").apply(StructureTemplatePool.Projection.RIGID),
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/hall_cross").apply(StructureTemplatePool.Projection.RIGID)
                ),
                StructureTemplatePool.Projection.RIGID
        ));

        // Rooms pool
        context.register(BACKROOMS_ROOMS, new StructureTemplatePool(
                empty,
                ImmutableList.of(
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/room_small").apply(StructureTemplatePool.Projection.RIGID),
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/room_large").apply(StructureTemplatePool.Projection.RIGID),
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/room_pillar").apply(StructureTemplatePool.Projection.RIGID),
                        StructurePoolElement.single(BackroomsMod.MODID + ":backrooms/room_corner").apply(StructureTemplatePool.Projection.RIGID)
                ),
                StructureTemplatePool.Projection.RIGID
        ));
    }
}