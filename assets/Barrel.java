package assets;

import java.util.ArrayList;

public class Barrel extends Asset {
    private static final int PROFIT = 7;
	private static final int PENALTY = 4;

	private static ArrayList<Asset> bonusList() {
		ArrayList<Asset> l = new ArrayList<Asset>();
		Bread b1 = new Bread();
		Bread b2 = new Bread();
		l.add(b1);
		l.add(b2);
		return l;
	}

	public Barrel() {
		super("illegal", PROFIT, PENALTY, bonusList());
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Barrel";
	}
}
