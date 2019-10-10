package assets;

import java.util.ArrayList;

public class Pepper extends Asset {
    private static final int PROFIT = 8;
	private static final int PENALTY = 4;

	private static ArrayList<Asset> bonusList() {
		ArrayList<Asset> l = new ArrayList<Asset>();
		Chicken c1 = new Chicken();
		Chicken c2 = new Chicken();
		l.add(c1);
		l.add(c2);
		return l;
	}

	public Pepper() {
		super("illegal", PROFIT, PENALTY, bonusList());
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Pepper";
	}

}
