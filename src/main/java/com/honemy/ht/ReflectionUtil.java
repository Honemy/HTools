package com.honemy.ht;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtil {

	/**
	 * Retrieves the value of a private field from an object using reflection.
	 *
	 * @param object the object from which to retrieve the field value.
	 * @param field  the name of the private field to retrieve.
	 * @return the value of the private field.
	 * @throws NoSuchFieldException   if the field with the specified name is not found.
	 * @throws IllegalAccessException if access to the field is denied.
	 */
	public static Object getPrivateField(Object object, String field) throws NoSuchFieldException, IllegalAccessException {
		Field objectField = object.getClass().getDeclaredField(field);
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;
	}

}
