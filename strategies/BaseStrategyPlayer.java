package strategies;

import java.util.ArrayList;

import java.util.HashMap;

import assets.Asset;

public class BaseStrategyPlayer extends Player {
    public BaseStrategyPlayer() {
		super();
		bribe = 0;
	}

	/*
	 *  completeaza asset-urile din mana in functie de input
	 */
	public void addInHand(final ArrayList<Asset> assetsInput) {
		final int maxSize = 6;
		while (assetsInHand.size() < maxSize) {
			assetsInHand.add(assetsInput.get(0));
			assetsInput.remove(0);
		}

	}

	/*
	 * @param round
	 */
	public void createBag(final int round) {

		HashMap<String, Integer> h = turnIntoMap(assetsInHand);

		int max = Integer.MIN_VALUE;
		int maxProfit = Integer.MIN_VALUE;
		Asset illegal = null;
		Asset legal = null;
		String legalAssetName = null;
		int val = 0;
		final int maxSize = 5;

		for (Asset a : assetsInHand) {
			val = h.get(a.whichAsset());

			// gaseste asset-ul legal cu cu frecventa maxima
			if (a.getType().compareTo("legal") == 0) {
				if (val > max) {
					max = val;
					legal = a; // asset-ul cu frecventa cea mai are
					legalAssetName = a.whichAsset();
				}

				// in cazul frecventelor egale este comparat profitul
				if ((val == max) && (legal != null)) {
					if (val * a.getProfit() > val * legal.getProfit()) {
						legal = a;
						legalAssetName = a.whichAsset();
					}
				}
			} else {
				// gaseste asset-ul ilegal cu profit maxim
				if (a.getProfit() > maxProfit) {
					maxProfit = a.getProfit();
					illegal = a; // asset-ul ilegal cu profitul maxim
				}
			}
		}

		// nu au fost gasite bunuri legale deci se pune bunul ilegal
		if (max == Integer.MIN_VALUE) {
			assetsInBag.add(illegal);
			assetsInHand.remove(illegal);
		} else {
			for (int i = 0; i < max; i++) {
				if (assetsInBag.size() < maxSize) {
					assetsInBag.add(removeAsset(legalAssetName, assetsInHand));
				}
			}
		}

	}

	// scoate din lista asset-ul cu numele "name"
	private static Asset removeAsset(final String name, final ArrayList<Asset> list) {

		for (Asset a : list) {
			if (a.whichAsset().equals(name)) {
				Asset aux = a;
				list.remove(a);
				return aux;
			}
		}

		return null;

	}

	/*
	 *  returneaza un string corespunzator asset-ului declarat
	 * @returns asset's name
	 */
	public String declareType() {
		if (assetsInBag.get(0).getType().compareTo("illegal") == 0) {
			return "Apple";
		}
		return assetsInBag.get(0).whichAsset();
	}

	/*
	 *  primeste un jucator al carui sac e verificat si pachetul de carti(non-Javadoc)
	 * @param player, assetsInput
	 */
	public void checkBag(final Player p, final ArrayList<Asset> assetsInput) {
		/*
		 * jucatorul basic nu accepta mita aceasta se intoarce la player
		 */
		if (p.bribe != 0) {
			p.coins += p.bribe;
			// reseteaza mita la 0
			p.bribe = 0;
		}

		ArrayList<Asset> assets = p.assetsInBag;
		int illegalPenalty = 0; // suma pe care o plateste micinosul
		int legalPenalty = 0; // suma pe care o castiag cel onest

		for (int i = 0; i < assets.size(); i++) {
			Asset a = assets.get(i);

			// daca e ilegal sau nedeclarat se intoarce la finalul pachetului
			if ((a.getType().compareTo("illegal") == 0)
					|| (a.whichAsset().compareTo(p.declareType()) != 0)) {
				illegalPenalty += a.getPenalty();
				assetsInput.add(a);
			} else {
				legalPenalty += a.getPenalty();
			}
		}

		// seriful castiga, mincinosul pierde
		if (illegalPenalty == 0) {
			coins -= legalPenalty;
			p.coins += legalPenalty;
		}

		// asset-urile legale sunt puse pe taraba
		for (int i = 0; i < assets.size(); i++) {
			Asset a = assets.get(i);
			if (a.getType().equals("legal")) {
				p.assetsOnMerchandStand.add(a);
				p.coins += a.getProfit();
			}
		}

		coins += illegalPenalty;
		p.coins -= illegalPenalty;

		// sacul este golit
		while (!p.assetsInBag.isEmpty()) {
			p.assetsInBag.remove(0);
		}

	}

}
