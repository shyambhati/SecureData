package info.shyamkumar.securedata;

import java.util.Collection;

import info.shyamkumar.securedata.helper.exception.SecureJsonExceptionHandler;
import static info.shyamkumar.securedata.helper.ProcessFields.*;

public class ProcessData {

	/**
	 * Processes the given object by applying data masking. If the object is a
	 * collection, it will mask fields for each element in the collection. If the
	 * object is not a collection, it will mask fields for the object itself.
	 *
	 * @param <T>    the type of the object
	 * @param object the object to be processed
	 * @return the processed object with masked data
	 */
	static <T> T processData(T object) {
		try {
			// Check if the object is a collection
			if (object instanceof Collection<?>) {
			    Collection<?> resultCollection = (Collection<?>) object;
				// Cast result to Collection and mask fields for each element
				for (Object item : resultCollection) {
					// Mask fields for each element in the collection
					processFields(item);
				}
			} else {
				// Mask fields for each element in the collection
				processFields(object);
			}
		} catch (Exception e) {
			// Handle exceptions thrown during data processing and masking
			SecureJsonExceptionHandler.handleException(e);
		}
		// Return the processed object with masked data
		return object;
	}
}