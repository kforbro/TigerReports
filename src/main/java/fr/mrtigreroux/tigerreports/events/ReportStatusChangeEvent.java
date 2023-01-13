package fr.mrtigreroux.tigerreports.events;

import fr.mrtigreroux.tigerreports.objects.reports.Report;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

/**
 * @author MrTigreroux
 */

public class ReportStatusChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Report r;
    private final boolean bungee;

    public ReportStatusChangeEvent(Report r, boolean bungee) {
        this.r = Objects.requireNonNull(r);
        this.bungee = bungee;
    }

    public Report getReport() {
        return r;
    }

    public boolean isFromBungeeCord() {
        return bungee;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
