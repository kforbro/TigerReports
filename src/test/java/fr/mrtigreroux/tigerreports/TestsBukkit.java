package fr.mrtigreroux.tigerreports;

import fr.mrtigreroux.tigerreports.data.Holder;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author MrTigreroux
 */
public class TestsBukkit {

    public static void mockPluginManagerCallEvent(MockedStatic<Bukkit> bukkitMock, Holder<Event> calledEvent) {
        Server server = mock(Server.class);
        PluginManager pm = mock(PluginManager.class);

        bukkitMock.when(() -> Bukkit.getServer()).thenReturn(server);
        when(server.getPluginManager()).thenReturn(pm);
        doAnswer((invocation) -> {
            Event event = invocation.getArgument(0);
            calledEvent.set(event);
            return true;
        }).when(pm).callEvent(any(Event.class));
    }

}
