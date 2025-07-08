package net.tyrone.backroomsmod.init;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tyrone.backroomsmod.BackroomsMod;
import net.tyrone.backroomsmod.world.structure.BackroomsStructureProcessor;

public class BackroomsProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSOR_TYPES =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_PROCESSOR_TYPES, BackroomsMod.MODID);

    public static final RegistryObject<StructureProcessorType<BackroomsStructureProcessor>> BACKROOMS_PROCESSOR =
            PROCESSOR_TYPES.register("backrooms_processor", () -> () -> BackroomsStructureProcessor.CODEC);
}