package dev.lupluv.cb.utils;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.reflections8.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterManager {


    public static void registerAll() {
        Reflections reflections = new Reflections("dev.lupluv.cb");
        Instant start = Instant.now();

        AtomicInteger listeners = new AtomicInteger();
        reflections.getSubTypesOf(Listener.class).forEach(clazz -> {
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();

                constructor.setAccessible(true);

                Listener listener = (Listener) constructor.newInstance();

                Citybuild.getInstance().getServer().getPluginManager().registerEvents(listener, Citybuild.getInstance());
                Bukkit.getConsoleSender().sendMessage("§a[Citybuild] §fRegistered listener §e" + listener.getClass().getSimpleName());
                listeners.getAndIncrement();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        AtomicInteger commands = new AtomicInteger();
        reflections.getTypesAnnotatedWith(RegisterCommand.class).forEach(clazz -> {
            try {
                RegisterCommand annotation = clazz.getAnnotation(RegisterCommand.class);
                Class<PluginCommand> commandClass = PluginCommand.class;
                Constructor<PluginCommand> constructor = commandClass.getDeclaredConstructor(String.class, Plugin.class);

                constructor.setAccessible(true);

                PluginCommand command = constructor.newInstance(annotation.name(), Citybuild.getInstance());
                command.setAliases(java.util.Arrays.asList(annotation.aliases()));
                command.setDescription(annotation.description());
                command.setPermission((new Permission(annotation.permission(), annotation.permissionDefault())).getName());
                command.setExecutor((org.bukkit.command.CommandExecutor) clazz.getDeclaredConstructor().newInstance());

                Bukkit.getCommandMap().register(Citybuild.getInstance().getName().toLowerCase(), command);
                Bukkit.getConsoleSender().sendMessage("§a[Citybuild] §fRegistered command §e" + annotation.name());
                commands.getAndIncrement();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Registered " + commands.get() + " commands and " + listeners.get() + " listeners in " + timeElapsed + "ms");
    }


}
