package net.tyrone.backroomsmod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tyrone.backroomsmod.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BackroomsMod.MODID)
public class BackroomsMod {
    public static final String MODID = "backrooms";
    public static final Logger LOGGER = LogManager.getLogger();

    public BackroomsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        // Register deferred registries
        BackroomsBlocks.BLOCKS.register(modEventBus);
        BackroomsItems.ITEMS.register(modEventBus);
        BackroomsStructures.STRUCTURE_TYPES.register(modEventBus);
        BackroomsStructures.STRUCTURE_PIECES.register(modEventBus);
        BackroomsProcessorTypes.PROCESSOR_TYPES.register(modEventBus);

        LOGGER.info("Backrooms mod initialized!");
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}