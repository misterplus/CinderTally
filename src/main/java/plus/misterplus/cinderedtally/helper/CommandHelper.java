package plus.misterplus.cinderedtally.helper;

import net.minecraft.server.MinecraftServer;

public class CommandHelper {
    public static void executeCommand(MinecraftServer server, String command) {
        server.getCommands().performCommand(server.createCommandSourceStack(), command);
    }
}
