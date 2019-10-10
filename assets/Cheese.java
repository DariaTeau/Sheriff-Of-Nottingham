package assets;

public class Cheese extends Asset {
	public static final int KING = 15;
	public static final int QUEEN = 10;

	public static final int PROFIT = 3;
	public static final int PENALTY = 2;

	public Cheese() {
		super("legal", PROFIT, PENALTY, null);
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Cheese";
	}
}
