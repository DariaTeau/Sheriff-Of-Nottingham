package assets;

import java.util.ArrayList;

public class Silk extends Asset {

	private static final int PROFIT = 9;
	private static final int PENALTY = 4;
	
	private static ArrayList<Asset> bonusList() {
		ArrayList<Asset> l = new ArrayList<Asset>();
		Cheese c1 = new Cheese();
		Cheese c2 = new Cheese();
		Cheese c3 = new Cheese();
		l.add(c1);
		l.add(c2);
		l.add(c3);
		return l;
	}

	public Silk() {
		super("illegal", PROFIT, PENALTY, bonusList());
	}
	/*
	 * @see assets.Asset#whichAsset()
	 */
	public String whichAsset() {
		return "Silk";
	}
}
