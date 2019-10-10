package assets;

public class Apple extends Asset {
	public static final int KING = 20;
	public static final int QUEEN = 10;

	private static final int PROFIT = 2;
	private static final int PENALTY = 2;

	public Apple() {
		super("legal", PROFIT, PENALTY, null);
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Apple";
	}
}
