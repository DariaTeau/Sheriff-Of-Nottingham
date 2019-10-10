package assets;

import java.util.ArrayList;

public class Asset {
    private final String type; // legal or illegal
	private final int profit;
	private final int penalty;
	private final ArrayList<Asset> bonus;

	public Asset(final String type, final int profit, final int penalty,
			final ArrayList<Asset> bonus) {

		this.type = type;
		this.profit = profit;
		this.penalty = penalty;
		this.bonus = bonus;
	}
	/*
	 * @returns type
	 */
	public String getType() {
		return this.type;
	}
	/*
	 * @returns profit
	 */
	public int getProfit() {
		return this.profit;
	}
	/*
	 * @returns penalty
	 */
	public int getPenalty() {
		return this.penalty;
	}
	/*
	 * @returns bonus
	 */
	public ArrayList<Asset> getBonus() {
		return this.bonus;
	}
	/*
	 * @returns name
	 */
	public String whichAsset() {
		return null;
	}
}
