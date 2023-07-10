package com.ankoki.joyonghan.misc;

import com.ankoki.sakura.JSONSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Please note this is based on English and Welsh income tax only.
 * This does not include national insurance.
 * At this time this will not support foreign tax systems, including but not limited to Scotland and Ireland.
 */
// TODO support national insurance at some point.
public class PaymentCalculator extends JSONSerializable {

	/**
	 * Required method by Sakura. Used to deserialize a payment calculator object.
	 *
	 * @param map the serialized object.
	 * @return the deserialized object.
	 */
	@NotNull
	@SuppressWarnings("unused") // Sakura uses reflection to access this method, it will appear unused.
	public static PaymentCalculator deserialize(Map<String, Object> map) {
		PayFrequency frequency = PayFrequency.valueOf((String) map.get("pay-frequency"));
		return new PaymentCalculator(frequency, (double) map.get("hourly"), (boolean) map.get("taxable"));
	}

	@NotNull
	private PayFrequency frequency;
	private double hourly;
	private boolean taxable;

	/**
	 * Creates a new object to calculate how much someone makes.
	 * There is no hourly rate or pay frequency provided, make sure to use the appropriate method to change this.
	 * The pay frequency will default to {@link PayFrequency#MONTHLY}, so errors are not spit.
	 */
	public PaymentCalculator() {
		this.frequency = PayFrequency.MONTHLY;
	}

	/**
	 * Creates a new object to calculate how much someone makes.
	 *
	 * @param frequency the frequency they get paid.
	 * @param hourly the amount they earn per hour.
	 */
	public PaymentCalculator(@NotNull PayFrequency frequency, double hourly) {
		this(frequency, hourly, true);
	}

	/**
	 * Creates a new object to calculate how much someone makes.
	 *
	 * @param frequency the frequency they get paid.
	 * @param hourly the amount they earn per hour.
	 * @param taxable whether you pay tax, for example, students do not pay tax.
	 */
	public PaymentCalculator(@NotNull PayFrequency frequency, double hourly, boolean taxable) {
		this.frequency = frequency;
		this.hourly = hourly;
		this.taxable = taxable;
	}

	/**
	 * Gets the frequency this person is paid.
	 *
	 * @return the frequency.
	 */
	public @NotNull PayFrequency getFrequency() {
		return frequency;
	}

	/**
	 * Gets the amount this person gets per hour.
	 *
	 * @return their hourly rate.
	 */
	public double getHourly() {
		return hourly;
	}

	/**
	 * Gets whether this person is taxed.
	 *
	 * @return true if taxable.
	 */
	public boolean isTaxable() {
		return taxable;
	}

	/**
	 * Sets the pay frequency for this person.
	 *
	 * @param frequency the pay frequency.
	 */
	public void setFrequency(@NotNull PayFrequency frequency) {
		this.frequency = frequency;
	}

	/**
	 * Sets the hourly rate of this person.
	 *
	 * @param hourly the hourly.
	 */
	public void setHourly(double hourly) {
		this.hourly = hourly;
	}

	/**
	 * Sets whether this person is taxed or not.
	 *
	 * @param taxable true if taxed.
	 */
	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}

	/**
	 * Gets the yearly income of someone before income tax is applied.
	 *
	 * @param hours the hours they have worked in a single period of pay, based on their pay frequency field.
	 * @return the income before tax.
	 */
	public double getYearlyIncome(double hours) {
		double payPeriod = this.hourly * hours;
		return switch (this.frequency) {
			case WEEKLY -> payPeriod * 52;
			case BI_WEEKLY -> payPeriod * 26;
			case SEMI_MONTHLY -> payPeriod * 24;
			case FOUR_WEEKLY -> payPeriod * 13;
			case MONTHLY -> payPeriod * 12;
		};
	}

	/**
	 * Gets the monthly income of someone before income tax is applied.
	 *
	 * @param hours the hours they have worked in a single period of pay.
	 * @return the income before tax.
	 */
	public double getPaydayIncome(double hours) {
		return this.hourly * hours;
	}

	/**
	 * Gets the yearly income of someone after income tax is applied.
	 * This does not include national insurance.
	 *
	 * @param hours the hours they have worked in a single period of pay, based on their pay frequency field.
	 * @return the income after tax.
	 */
	public double getYearlyIncomeAfterTax(double hours) {
		double payPeriod = this.hourly * hours;
		double yearly;
		switch (this.frequency) {
			case WEEKLY -> yearly = payPeriod * 52;
			case BI_WEEKLY -> yearly = payPeriod * 26;
			case SEMI_MONTHLY -> yearly = payPeriod * 24;
			case FOUR_WEEKLY -> yearly = payPeriod * 13;
			case MONTHLY -> yearly = payPeriod * 12;
			default -> yearly = -1;
		}
		if (!this.taxable || yearly - 12570 <= 0)
			return yearly;
		if (yearly < 50271)
			return (yearly - 12570 * 0.8) + 12570;
		else if (yearly < 125141)
			return ((yearly - 50270) * 0.6) + (37700 * 0.8) + 12570;
		else
			// TODO make this equal to 85,297 with 130,000 as the yearly, currently outputs 82,783.
			return ((yearly - 125140) * 0.55) + (62300 * 0.6) + (37700 * 0.8) + 12570;
	}

	/**
	 * Gets the income this person would get on payday after income tax.
	 * This does not include national insurance.
	 *
	 * @param hours the hours they worked in the period.
	 * @return the income after tax.
	 */
	public double getPaydayIncomeAfterTax(double hours) {
		double yearlyTax = this.getYearlyIncomeAfterTax(hours);
		return switch (this.frequency) {
			case WEEKLY -> yearlyTax / 52;
			case BI_WEEKLY -> yearlyTax / 26;
			case SEMI_MONTHLY -> yearlyTax / 24;
			case FOUR_WEEKLY -> yearlyTax / 13;
			case MONTHLY -> yearlyTax / 12;
		};
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("pay-frequency", this.frequency.name());
		map.put("hourly", this.hourly);
		map.put("taxable", this.taxable);
		return map;
	}

}
