package dev.lupluv.cb.annotations;

import org.bukkit.permissions.PermissionDefault;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterCommand {
    String name();
    String description() default "";

    String[] aliases() default {};

    String permission() default "";
    PermissionDefault permissionDefault() default PermissionDefault.OP;
}
