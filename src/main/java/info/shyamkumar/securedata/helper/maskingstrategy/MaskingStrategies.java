package info.shyamkumar.securedata.helper.maskingstrategy;

import info.shyamkumar.securedata.config.SecureMaskingProperties;

public class MaskingStrategies {

	private static final SecureMaskingProperties maskingProperties = SecureMaskingProperties.setConfig();

	static String maskDefault(String value, int preFixLength, int postFixLength, char maskSymbol) {
		String MASKED_SYMBOL = String.valueOf(maskSymbol);
		if (preFixLength == 0)
			preFixLength = maskingProperties.getNormalPrefixLength();
		if (postFixLength == 0)
			postFixLength = maskingProperties.getNormalSuffixLength();

		int[] lengths = fixLength(preFixLength, postFixLength, value.length());

		preFixLength = lengths[0];
		postFixLength = lengths[1];

		return MASKED_SYMBOL.repeat(Math.max(0, value.length() - (preFixLength + postFixLength)))
				+ value.substring(Math.max(0, value.length() - postFixLength));
	}

	static String phoneMask(String value, int preFixLength, int postFixLength, char maskSymbol) {
		String MASKED_SYMBOL = String.valueOf(maskSymbol);
		if (preFixLength == 0)
			preFixLength = maskingProperties.getPhonePrefixLength();
		if (postFixLength == 0)
			postFixLength = maskingProperties.getPhoneSuffixLength();

		int[] lengths = fixLength(preFixLength, postFixLength, value.length());

		preFixLength = lengths[0];
		postFixLength = lengths[1];

		StringBuilder maskedPrefix = new StringBuilder();
		if (value.length() <= (preFixLength + postFixLength) && value.length() < 3) {
			maskedPrefix.append(MASKED_SYMBOL.repeat(value.length()));
		} else {
			for (int i = 0; i < value.length() - postFixLength; i++) {
				if (value.charAt(i) == '-') {
					maskedPrefix.append('-');
				} else if (i == 0 && value.charAt(i) == '+') {
					maskedPrefix.append('+');
				} else {
					maskedPrefix.append(MASKED_SYMBOL);
				}
			}
			maskedPrefix.append(value.substring(value.length() - postFixLength));
		}
		return maskedPrefix.toString();
	}

	static String emailMask(String email, int preFixLength, int postFixLength, char maskSymbol) {
		String MASKED_SYMBOL = String.valueOf(maskSymbol);

		if (preFixLength == 0)
			preFixLength = maskingProperties.getEmailPrefixLength();
		if (postFixLength == 0)
			postFixLength = maskingProperties.getEmailSuffixLength();

		StringBuilder emailMask = new StringBuilder();
		String[] parts = email.split("@");

		if (parts.length != 2) {
			return email;
		}
		String prefix = parts[0];
		String domain = parts[1];

		int[] lengths = fixLength(preFixLength, postFixLength, prefix.length());

		preFixLength = lengths[0];
		postFixLength = lengths[1];

		if (prefix.length() <= (preFixLength + postFixLength) || prefix.length() < 2) {
			emailMask.append(MASKED_SYMBOL.repeat(prefix.length()));
		} else {

			emailMask.append(prefix, 0, preFixLength)
					.append(MASKED_SYMBOL.repeat(prefix.length() - (preFixLength + postFixLength)))
					.append(prefix.substring(prefix.length() - postFixLength));
		}
		emailMask.append("@").append(maskEmailDomain(domain, MASKED_SYMBOL));
		return emailMask.toString();
	}

	private static String maskEmailDomain(String domain, String MASKED_SYMBOL) {
		if (maskingProperties.isEmailDomainMask()) {
			StringBuilder masked = new StringBuilder();
			String[] domainParts = domain.split("\\.");
			for (int i = 0; i < domainParts.length; i++) {
				String domainPart = domainParts[i];
				if (i == 0) { // For the first part of the domain
					masked.append(domainPart.charAt(0)) // Keep the first character
							.append(MASKED_SYMBOL.repeat(Math.max(0, domainPart.length() - 1))); // Mask the rest
				} else { // For other domain parts (e.g., .com, .org)
					masked.append(".").append(domainPart); // Add the remaining parts unchanged
				}
			}
			return masked.toString();
		} else {
			return domain;
		}
	}

	static String maskCard(String value, int preFixLength, int postFixLength, char maskSymbol) {
		StringBuilder maskedValue = new StringBuilder();
		String MASKED_SYMBOL = String.valueOf(maskSymbol);

		if (preFixLength == 0)
			preFixLength = maskingProperties.getCardPrefixLength();
		if (postFixLength == 0)
			postFixLength = maskingProperties.getCardSuffixLength();

		int[] lengths = fixLength(preFixLength, postFixLength, value.length());

		preFixLength = lengths[0];
		postFixLength = lengths[1];
		int maskLength = value.length() - preFixLength - postFixLength;
		if (value.length() > preFixLength + postFixLength) {
			maskedValue.append(value, 0, preFixLength).append(MASKED_SYMBOL.repeat(maskLength))
					.append(value.substring(value.length() - postFixLength));
		} else {
			maskedValue.append(MASKED_SYMBOL.repeat(value.length()));
		}
		return maskedValue.toString();
	}

	private static int[] fixLength(int preFixLength, int postFixLength, int valueLength) {
		if (valueLength < preFixLength + postFixLength) {
			int min = Math.min(valueLength, preFixLength + postFixLength);
			preFixLength = min / 2;
			postFixLength = min - preFixLength;
		}
		return new int[] { preFixLength, postFixLength };
	}
}