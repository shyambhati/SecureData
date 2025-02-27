package org.securedata.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Annotation to indicate that a field should be masked. This can be used to
 * protect sensitive information by masking parts of the data. Author: Shyam
 * Kumar Timestamp: 2025-10-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Masked {

	/**
	 * Specifies the name of the data field to be masked. Default is an empty
	 * string, which means the field name will be used.
	 *
	 * Example: private int accountNumber; @Masked(dataField="accountNumber") //
	 * Name of the source field to be masked private String maskedAccountNumber;
	 */
	String dataField() default "";

	/**
	 * Specifies the number of characters to keep unmasked at the beginning of the
	 * data. Default is 0.
	 */
	int prefixLength() default 0;

	/**
	 * Specifies the number of characters to keep unmasked at the end of the data.
	 * Default is 0.
	 */
	int suffixLength() default 0;

	/**
	 * Specifies the type of masking to be applied. Default is "DEFAULT".
	 */
	String type() default "DEFAULT";

	/**
	 * Specifies the symbol to be used for masking. Default is '*'.
	 */
	char maskSymbol() default '*';

	/**
	 * Specifies a regex pattern for masking. Default is an empty string.
	 */
	String pattern() default "";
}
