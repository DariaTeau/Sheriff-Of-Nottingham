package assets;

public class Chicken extends Asset {
    public static final int KING = 10;
	public static final int QUEEN = 5;

	private static final int PROFIT = 4;
	private static final int PENALTY = 2;

	public Chicken() {
		super("legal", PROFIT, PENALTY, null);
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Chicken";
	}
}
