package com.honemy.ht.remain;

import com.honemy.ht.ReflectionUtil;
import com.honemy.ht.plugin.ModernPlugin;
import org.bukkit.Server;
import org.bukkit.command.Command;

import java.lang.reflect.Method;

/**
 * Utility class for accessing and manipulating command-related objects.
 */
public final class Remain {

	/**
	 * Retrieves the register method from the command map.
	 *
	 * @param commandMap the command map object.
	 * @return the register method.
	 * @throws NoSuchMethodException if the method does not exist.
	 * @throws SecurityException     if a security violation occurred.
	 */
	public static Method getRegisterMethod(Object commandMap) throws NoSuchMethodException, SecurityException {
		return commandMap.getClass().getMethod("register", String.class, Command.class);
	}

	/**
	 * Retrieves the command map from the server.
	 *
	 * @return the command map.
	 * @throws NoSuchFieldException   if the field does not exist.
	 * @throws IllegalAccessException if the field is not accessible.
	 */
	public static Object getCommandMap() throws NoSuchFieldException, IllegalAccessException {
		Server server = ModernPlugin.getInstance().getServer();
		return ReflectionUtil.getPrivateField(server, "commandMap");
	}
}
