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


/**
 * @author bruno
 *
 */
public enum Currency {
	CHF("Swiss Franc", 756), USD("US Dollar", 840), EUR("Euro", 978);

	private String label;
	private int isoCode;
	
	/**
	 * Constructor.
	 * 
	 * @param label the label of the currency.
	 */
	private Currency(String label, int isoCode) {
		this.label = label;
		this.isoCode = isoCode;
	}

	/**
	 * Get the label. The label is the usual name of the currency in english.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Get currency iso code.
	 * 
	 * @return
	 */
	public int getIsoCode() {
		return isoCode;
	}
	
	/**
	 * Map ISO currency code to Currency.
	 * 
	 * @param isoCode
	 * @return
	 */
	public static Currency toCurrency(
		int isoCode
	) {
		if(isoCode == 756) {
			return CHF;
		} else if(isoCode == 840) {
			return USD;
		} else if(isoCode == 978) {
			return EUR;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Get the default currency (CHF). This is the official international currency code.
	 * 
	 * @return the default currency
	 */
	public static Currency getDefaultCurrency() {
		return CHF;
	}
	
}
