package org.securedata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MaskingProperties {

	public static int emailPrefixLength = 1; // Default value
	public static int emailSuffixLength = 1; // Default value
	public static int normalSuffixLength = 4; // Default value
	public static int normalPrefixLength = 0; // Default value
	public static char symbol = '*'; // Default value
	public static boolean isEmailDomainMask = false; // Default value
	public static int emailDomainPrefixLength = 1; // Default value

	public static int phonePrefixLength = 0; // Default value
	public static int phoneSuffixLength = 4;

	public static int cardPrefixLength = 2; // Default value
	public static int cardSuffixLength = 2;

	@Value("${securedata.masking.email-prefix-length:1}")
	public void setEmailPrefixLength(String emailPrefixLength) {
		MaskingProperties.emailPrefixLength = parseInt(emailPrefixLength, 1, "securedata.masking.email-prefix-length");
	}

	@Value("${securedata.masking.email-suffix-length:1}")
	public void setEmailPostfixLength(String emailSuffixLength) {
		MaskingProperties.emailSuffixLength = parseInt(emailSuffixLength, 1, "securedata.masking.email-suffix-length");
	}

	@Value("${securedata.masking.normal-postfix-length:4}")
	public void setNormalPostfixLength(String normalSuffixLength) {
		MaskingProperties.normalSuffixLength = parseInt(normalSuffixLength, 4,
				"securedata.masking.normal-suffix-length");
	}

	@Value("${securedata.masking.normal-prefix-length:0}")
	public void setNormalPrefixLength(String normalPrefixLength) {
		MaskingProperties.normalPrefixLength = parseInt(normalPrefixLength, 0,
				"securedata.masking.normal-prefix-length");
	}

	@Value("${securedata.masking.symbol:*}")
	public void setSymbol(char symbol) {
		MaskingProperties.symbol = symbol;
	}

	@Value("${securedata.masking.email-domain-mask:false}")
	public void setEmailDomainMask(boolean isEmailDomainMask) {
		MaskingProperties.isEmailDomainMask = isEmailDomainMask;
	}

	@Value("${securedata.masking.email-domain-prefix-length:1}")
	public void setEmailDomainPrefixLength(String emailDomainPrefixLength) {
		MaskingProperties.emailDomainPrefixLength = parseInt(emailDomainPrefixLength, 1, "email-domain-prefix-length");
	}

	@Value("${securedata.masking.phone-prefix-length:0}")
	public void setPhonePrefixLength(String phonePrefixLength) {
		MaskingProperties.phonePrefixLength = parseInt(phonePrefixLength, 0, "securedata.masking.phone-prefix-length");
	}

	@Value("${securedata.masking.phone-suffix-length:2}")
	public void setPhoneSuffixLength(String phoneSuffixLength) {
		MaskingProperties.phoneSuffixLength = parseInt(phoneSuffixLength, 4, "securedata.masking.phone-suffix-length");
	}

	@Value("${securedata.masking.card-prefix-length:2}")
	public void setCardPrefixLength(String cardPrefixLength) {
		MaskingProperties.cardPrefixLength = parseInt(cardPrefixLength, 2, "securedata.masking.card-prefix-length");
	}

	@Value("${securedata.masking.card-suffix-length:2}")
	public void setCardSuffixLength(String cardSuffixLength) {
		MaskingProperties.cardSuffixLength = parseInt(cardSuffixLength, 2, "securedata.masking.card-suffix-length");
	}

	private int parseInt(String value, int defaultValue, String propertyName) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			handleError("SecureJson : Invalid value for " + propertyName + " : " + value + ". Using default value: "
					+ defaultValue);
			return defaultValue; // Return the default value if parsing fails
		}
	}

	private void handleError(String message) {
		System.err.println(message);
	}
}