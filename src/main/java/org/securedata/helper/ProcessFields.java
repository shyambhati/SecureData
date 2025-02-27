package org.securedata.helper;

import java.util.List;
import java.util.Collection;
import java.util.LinkedList;
import java.lang.reflect.Field;
import org.securedata.annotation.Masked;
import static org.securedata.helper.Masking.applyMasking;
import static org.securedata.helper.ReflectionHelper.*;

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
				if (fieldValue instanceof Collection<?> collection) {
					if (isStringCollection(collection))
						continue;
					collection.forEach(ProcessFields::processFields);
				}
			}
		}
	}
}