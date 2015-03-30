package org.opentdc.rates;

import java.util.Formatter;
import java.util.Locale;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class RatesModel {

	private String id;
	private String title;
	private int rate;
	private Currency currency;
	private String description;

	public RatesModel() {
		this.id = UUID.randomUUID().toString();
		this.title = "UNDEFINED_TITLE";
		this.rate = 100;
		this.currency = Currency.getDefaultCurrency();
		this.description = "UNDEFINED_DESCRIPTION";
	}

	public RatesModel(String title, int rate, String description) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.rate = rate;
		this.currency = Currency.getDefaultCurrency();
		this.description = description;
	}

	/**
	 * 
	 * @return the ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the ID to a unique random number
	 */
	public void setId() {
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}

	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder _sb = new StringBuilder();
		Formatter _formatter = new Formatter(_sb, Locale.US);
		_formatter
				.format("{\n\tid:\t%s\n\ttitle:\t%s\n\trate:\t%s\n\tcurrency:\t%s\n\tdescription:\t%s\n}",
						getId(), getTitle(), getRate(), getCurrency(),
						getDescription());
		_formatter.close();
		return _sb.toString();
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
