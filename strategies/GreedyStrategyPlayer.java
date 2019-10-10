package strategies;

import java.util.ArrayList;

import assets.Asset;

public class GreedyStrategyPlayer extends BaseStrategyPlayer {
    private int round = 0;
    public GreedyStrategyPlayer() {
         super();
	}

	/*
	 *  face update rundei(non-Javadoc)
	 */
	public int roundNr() {
		if (!isSheriff) {
			round++;
		}
		return round;
	}

	/*
	 *  creaza sacul(non-Javadoc)
	 * @see strategies.BaseStrategyPlayer#createBag(int)
	 */
	public void createBag(final int round) {
		final int maxSize = 5;
		// aplica strategia de baza
		super.createBag(round);

		// daca runda e para pune un bun ilegal
		if ((round % 2 == 0) && (assetsInBag.size() < maxSize)) {

			int max = Integer.MIN_VALUE;
			Asset maxAsset = null;

			// cauta bunul ilegal cu profitul maxim
			for (int i = 0; i < assetsInHand.size(); i++) {
				Asset a = assetsInHand.get(i);

				if (a.getType().compareTo("illegal") == 0) {
					if (a.getProfit() > max) {
						max = a.getProfit();
						maxAsset = a;
					}
				}

			}
			// pune bunul in sac si il scoate din mana
			if (maxAsset != null) {
				assetsInBag.add(maxAsset);
				assetsInHand.remove(maxAsset);
			}

		}

	}

	/*
	 *  primeste un jucator al carui sac e verificat si pachetul de carti(non-Javadoc)
	 * @see strategies.BaseStrategyPlayer#checkBag(strategies.Player, java.util.ArrayList)
	 */
	public void checkBag(final Player p, final ArrayList<Asset> assetsInput) {

		// daca exista mita o accepta
		if (p.bribe != 0) {
			coins += p.bribe;

			// reseteaza mita la 0
			p.bribe = 0;

			// playerul isi pune toate bunurile pe taraba
			while (!p.assetsInBag.isEmpty()) {
				Asset a = p.assetsInBag.get(0);
				p.assetsOnMerchandStand.add(a);

				// pune pe taraba si bonusurile
				if (a.getBonus() != null) {
					for (Asset asset : a.getBonus()) {
						p.assetsOnMerchandStand.add(asset);
						p.coins += asset.getProfit();
					}
				}

				p.coins += a.getProfit();
				p.assetsInBag.remove(0);
			}
		} else {
			// daca nu exista mita adopta strategia de baza
			super.checkBag(p, assetsInput);
		}
	}
}
