/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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