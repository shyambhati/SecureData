package org.securedata.helper;

import org.securedata.annotation.Masked;
import org.securedata.config.MaskingProperties;
import org.securedata.helper.exception.SecureJsonException;
import org.securedata.helper.maskingstrategy.MaskingRegistry;
import org.securedata.helper.maskingstrategy.MaskingStrategy;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.securedata.helper.ProcessFields.processFields;
import static org.securedata.helper.ReflectionHelper.*;

public class Masking {

	static void applyMasking(Field field, Object data) {
		if (field == null)
			return;

		Object fieldValue;
		Masked column = field.getAnnotation(Masked.class);
		String maskedToField = column.dataField();
		try {
			if (!maskedToField.isEmpty()) {
				fieldValue = getFieldValue(data.getClass().getDeclaredField(maskedToField), data);
			} else {
				fieldValue = getFieldValue(field, data);
			}
			if (fieldValue instanceof String value) {
				// Handle email and normal strings
				String maskedValue = processStringMasking(field, data, column, value);
				if (!maskedValue.isEmpty()) {
					setFieldValue(field, data, maskedValue);
				}
			} else if (fieldValue instanceof Collection<?> collection && isStringCollection(collection)) {
				Collection<String> maskedCollection = ((Collection<?>) collection).stream()
						.filter(String.class::isInstance)
						.map(element -> processStringMasking(field, element, column, (String) element))
						.collect(Collectors.toCollection(
								() -> (Collection<String>) createCollectionOfType(fieldValue.getClass())));
				setFieldValue(field, data, maskedCollection);
			}
		} catch (Exception e) {
			// Handle the specific accessibility exception and continue
			throw new SecureJsonException("Secure Json : Error while applying masking", e);
		}

	}

	private static String processStringMasking(Field field, Object data, Masked column, String value) {
		StringBuilder maskedValue = new StringBuilder();
		char maskedSymbol = column.maskSymbol() == '*' ? MaskingProperties.symbol : column.maskSymbol();

		if (column.pattern() != null && !column.pattern().isEmpty()) {
			return value.replaceAll(column.pattern(), maskedSymbol + "");
		}
		// Get the masking strategy from registry
		if (!MaskingRegistry.validateStrategy(column.type()))
			System.err.println("Warning :- Secure Json : Masking strategy not found for type : "
					+ field.getDeclaringClass().getSimpleName() + "." + field.getName() + "." + column.type()
					+ " \nApplying default masking strategy \n");
		MaskingStrategy strategy = MaskingRegistry.getStrategy(column.type());

		return strategy.mask(value, column.prefixLength(), column.suffixLength(), maskedSymbol);
	}
}