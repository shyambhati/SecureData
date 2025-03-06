package info.shyamkumar.securedata.config;

import java.io.Serializable;

public class SecureMaskingProperties implements Cloneable, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private static volatile SecureMaskingProperties instance;

	private int emailPrefixLength = 2;
	private int emailSuffixLength = 1;
	private int normalSuffixLength = 4;
	private int normalPrefixLength = 0;
	private char symbol = '*';
	private boolean isEmailDomainMask = false;
	private int emailDomainPrefixLength = 1;

	private int phonePrefixLength = 0;
	private int phoneSuffixLength = 4;

	private int cardPrefixLength = 2;
	private int cardSuffixLength = 2;

	private SecureMaskingProperties() {
		if (instance != null) {
			throw new IllegalStateException("SecureMaskingProperties instance already exists");
		}
	}

	public static SecureMaskingProperties setConfig() {
		if (instance == null) {
			synchronized (SecureMaskingProperties.class) {
				if (instance == null) {
					instance = new SecureMaskingProperties();
				}
			}
		}
		return instance;
	}

	protected Object readResolve() {
		return instance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Cloning of SecureMaskingProperties objects is not allowed.");
	}

	public int getEmailPrefixLength() {
		return emailPrefixLength;
	}

	public void setEmailPrefixLength(int emailPrefixLength) {
		this.emailPrefixLength = emailPrefixLength;
	}

	public int getEmailSuffixLength() {
		return emailSuffixLength;
	}

	public void setEmailSuffixLength(int emailSuffixLength) {
		this.emailSuffixLength = emailSuffixLength;
	}

	public int getNormalSuffixLength() {
		return normalSuffixLength;
	}

	public void setNormalSuffixLength(int normalSuffixLength) {
		this.normalSuffixLength = normalSuffixLength;
	}

	public int getNormalPrefixLength() {
		return normalPrefixLength;
	}

	public void setNormalPrefixLength(int normalPrefixLength) {
		this.normalPrefixLength = normalPrefixLength;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public boolean isEmailDomainMask() {
		return isEmailDomainMask;
	}

	public void setEmailDomainMask(boolean isEmailDomainMask) {
		this.isEmailDomainMask = isEmailDomainMask;
	}

	public int getEmailDomainPrefixLength() {
		return emailDomainPrefixLength;
	}

	public void setEmailDomainPrefixLength(int emailDomainPrefixLength) {
		this.emailDomainPrefixLength = emailDomainPrefixLength;
	}

	public int getPhonePrefixLength() {
		return phonePrefixLength;
	}

	public void setPhonePrefixLength(int phonePrefixLength) {
		this.phonePrefixLength = phonePrefixLength;
	}

	public int getPhoneSuffixLength() {
		return phoneSuffixLength;
	}

	public void setPhoneSuffixLength(int phoneSuffixLength) {
		this.phoneSuffixLength = phoneSuffixLength;
	}

	public int getCardPrefixLength() {
		return cardPrefixLength;
	}

	public void setCardPrefixLength(int cardPrefixLength) {
		this.cardPrefixLength = cardPrefixLength;
	}

	public int getCardSuffixLength() {
		return cardSuffixLength;
	}

	public void setCardSuffixLength(int cardSuffixLength) {
		this.cardSuffixLength = cardSuffixLength;
	}
}
