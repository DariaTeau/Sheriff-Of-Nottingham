package strategies;

import java.util.ArrayList;

import assets.Asset;

public class BribeStrategyPlayer extends BaseStrategyPlayer {
    public BribeStrategyPlayer() {
        super();
	}

	/*
	 * gaseste asset-ul ilegal cu cel mai mare profit il returneaza si il scoate din
	 * lista
	 */
	private Asset findMax(final ArrayList<Asset> list) {

		int max = Integer.MIN_VALUE;
		Asset maxAsset = null;

		for (int i = 0; i < list.size(); i++) {
			Asset a = list.get(i);

			if (a.getType().compareTo("illegal") == 0) {
				if (a.getProfit() > max) {
					max = a.getProfit();
					maxAsset = a;
				}
			}
		}

		if (maxAsset != null) {
			list.remove(maxAsset);
		}

		return maxAsset;
	}

	/*
	 *  creaza sacul(non-Javadoc)
	 * @see strategies.BaseStrategyPlayer#createBag(int)
	 */
	public void createBag(final int round) {
		final int minBribe = 5;
		final int maxBribe = 10;
		final int maxSize = 5;
		// daca nu are suficienti coins pentru mita joaca strategia de baza
		if (coins < minBribe) {
			super.createBag(round);
		} else {
			// cauta asset-urile ilagale de profit maxim
			Asset a = findMax(assetsInHand);

			// daca nu are asset-uri ilegale joaca strategia de baza
			if (a == null) {
				super.createBag(round);
			} else {
				// daca are asset-uri ilegale le pune in sac
				assetsInBag.add(a);

				a = findMax(assetsInHand);
				while ((assetsInBag.size() < maxSize) && (a != null)) {
					assetsInBag.add(a);
					a = findMax(assetsInHand);
				}

				// da mita in functie de numarul bunurilor ilegale
				if (assetsInBag.size() <= 2) {
					bribe = minBribe;
					coins -= minBribe;
				} else {

					if ((assetsInBag.size() > 2) && (coins >= maxBribe)) {
						bribe = maxBribe;
						coins -= maxBribe;
					} else {
						// daca nu isi permite mita joaca strategia de baza
						for (Asset b : assetsInBag) {
							assetsInBag.remove(b);
						}
						super.createBag(round);
					}
				}
			}

		}

	}

	/*
	 *  returneaza "Apple" daca are bunuri ilegale(non-Javadoc)
	 * @see strategies.BaseStrategyPlayer#declareType()
	 */
	public String declareType() {
		if (bribe == 0) {
			return super.declareType();
		} else {
			return "Apple";
		}
	}
}
