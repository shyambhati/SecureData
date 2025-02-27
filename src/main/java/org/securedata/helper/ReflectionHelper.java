package org.securedata.helper;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class ReflectionHelper {

protected static List<Field> getAllFields(List<Field> fields, Class<?> obj) {
    Class<?> superClass = obj.getSuperclass();
    if (superClass != null) {
        fields = getAllFields(fields, superClass);
    }
    fields.addAll(Arrays.asList(obj.getDeclaredFields()));
    return fields;
}

static Object getFieldValue(Field field, Object object) throws IllegalAccessException {
    boolean accessible = field.isAccessible();
    field.setAccessible(true);
    Object value = field.get(object);
    field.setAccessible(accessible);
    return value;
}

static void setFieldValue(Field field, Object object, Object value)  {
     try {
         boolean accessible = field.isAccessible();
         field.setAccessible(true);
         field.set(object, value);
         field.setAccessible(accessible);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
     }
}

static boolean isNestedObject(Class<?> fieldType) {
    List<Class<?>> WRAPPER_TYPES = Arrays.asList(
            Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
            Character.class, Boolean.class, String.class, Enum.class);

    return !(fieldType.isPrimitive() || WRAPPER_TYPES.contains(fieldType));
}

 static boolean isNumber(Object fieldValue) {
    return fieldValue instanceof Integer || fieldValue instanceof Long || fieldValue instanceof Double || fieldValue instanceof Float || fieldValue instanceof BigDecimal;
}

 static boolean isStringCollection(Collection<?> collection) {
    if (collection.isEmpty()) {
        return false;
    }
    for (Object element : collection) {
        return element instanceof String;
    }
     return false;
 }

 static Object createCollectionOfType(Class<?> clazz) {
    if (List.class.isAssignableFrom(clazz)) {
        return new ArrayList<>();
    } else if (Set.class.isAssignableFrom(clazz)) {
        return new HashSet<>();
    } else if (Queue.class.isAssignableFrom(clazz)) {
        return new LinkedList<>();
    } else {
        throw new UnsupportedOperationException("Unsupported collection type: " + clazz);
    }
}
}