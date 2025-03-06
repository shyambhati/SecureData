package info.shyamkumar.securedata.helper;

import java.util.List;

import info.shyamkumar.securedata.annotation.Masked;

import java.util.Collection;
import java.util.LinkedList;
import java.lang.reflect.Field;
import static info.shyamkumar.securedata.helper.ReflectionHelper.*;
import static info.shyamkumar.securedata.helper.Masking.*;

public class ProcessFields {
	public static void processFields(Object data) {
		if (data == null)
			return;

		List<Field> fields = getAllFields(new LinkedList<>(), data.getClass());
		for (Field field : fields) {
			if (field.isAnnotationPresent(Masked.class)) {
				try {
					field.setAccessible(true);
				} catch (Exception e) {
					// Handle the specific accessibility exception and continue
					continue;
				}
				Object fieldValue;
				try {
					fieldValue = field.get(data);
				} catch (IllegalAccessException e) {
					// Handle the specific accessibility exception and continue
					continue;
				}

				if (field.isAnnotationPresent(Masked.class)) {
					applyMasking(field, data);
				}

				if (isNestedObject(field.getType()) && !(fieldValue instanceof Collection<?>) && fieldValue != null) {
					processFields(fieldValue);
				}

				// Handle collections
				if (fieldValue instanceof Collection<?>) {
					    Collection<?> collection = (Collection<?>) fieldValue;
					if (isStringCollection(collection))
						continue;
					collection.forEach(ProcessFields::processFields);
				}
			}
		}
	}
}