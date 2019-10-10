package assets;

public class Bread extends Asset {
    public static final int KING = 15;
	public static final int QUEEN = 10;

	private static final int PROFIT = 4;
	private static final int PENALTY = 2;

	public Bread() {
		super("legal", PROFIT, PENALTY, null);
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Bread";
	}

}
