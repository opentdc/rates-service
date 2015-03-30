package org.opentdc.rates;

import java.util.HashMap;
import java.util.Map;

public enum Currency {
	CHF("Swiss Franc"), USD("US Dollar"), EUR("Euro");

	private String label;
	private static Map<String, Currency> stringToEnumMapping;

	private Currency(String label) {
		this.label = label;
	}

	public static Currency getCurrency(String label) {
		if (stringToEnumMapping == null) {
			initMapping();
		}
		return stringToEnumMapping.get(label);
	}

	private static void initMapping() {
		stringToEnumMapping = new HashMap<String, Currency>();
		for (Currency _c : values()) {
			stringToEnumMapping.put(_c.label, _c);
		}
	}

	public String getLabel() {
		return label;
	}

	public static Currency getDefaultCurrency() {
		return CHF;
	}
}
