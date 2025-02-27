package org.securedata.helper.maskingstrategy;

public interface MaskingStrategy {
	String mask(String value, int preFixLength, int postFixLength, char maskSymbol);
}