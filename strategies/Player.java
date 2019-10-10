package strategies;

import java.util.ArrayList;
import java.util.HashMap;

import assets.Apple;
import assets.Asset;
import assets.Bread;
import assets.Cheese;
import assets.Chicken;

public class Player {
    protected ArrayList<Asset> assetsInHand = new ArrayList<Asset>();
	protected ArrayList<Asset> assetsOnMerchandStand = new ArrayList<Asset>();
	protected ArrayList<Asset> assetsInBag = new ArrayList<Asset>();
	private static final int COINS = 50;
	protected int coins = COINS;
	protected int bribe;
	protected boolean isSheriff = false;
	/*
	 * seteaza seriful
	 * @param boolean
	 */
	public void setIsSheriff(final boolean a) {
		isSheriff = a;
	}
	/*
	 * @returns isSheriff
	 */
	public boolean getIsSheriff() {
		return isSheriff;
	}
	/*
	 * @returns coins
	 */
	public int getCoins() {
		return coins;
	}
	/*
	 * @returns assetsInHand
	 */
	public ArrayList<Asset> getAssetsInHand() {
		return assetsInHand;
	}
	/*
	 * @returns assetsOnMerchandStand
	 */
	public ArrayList<Asset> getAssetsOnMerchandStand() {
		return assetsOnMerchandStand;
	}
	/*
	 * @returns assetsInBag
	 */
	public ArrayList<Asset> getAssetsInBag() {
		return assetsInBag;
	}
	/*
	 * @param assetsInput(pachetul)
	 */
	public void addInHand(final ArrayList<Asset> assetsInput) {

	}
	/*
	 * @param round
	 */
	public void createBag(final int round) {

	}
	/*
	 * @returns  declared type
	 */
	public String declareType() {
		return null;
	}
	/*
	 * calculeaza runda
	 * @returns round
	 */
	public int roundNr() {
		return 0;
	}
	/*
	 * @param player, assetsInput
	 */
	public void checkBag(final Player p, final ArrayList<Asset> assetsInput) {

	}

	/*
	 * transforma un arraylist de asset-uri intr-un hashmap cu cheia numele
	 * asset-ului si cu valoarea fecventa sa de aparitie in cadrul listei
	 */
	public HashMap<String, Integer> turnIntoMap(final ArrayList<Asset> assets) {

		HashMap<String, Integer> h = new HashMap<String, Integer>();
		int val = 0;

		for (Asset a : assets) {
			String key = a.whichAsset();
			if (h.containsKey(key)) {
				val = h.get(key);
				h.put(key, val + 1);
			} else {
				h.put(key, 1);
			}
		}

		return h;
	}

	/*
	 * gaseste playerii cu numarul maxim de asset-uri ce au numele "name" si le
	 * returnneaza indicii sub forma unui arraylist
	 */
	private static ArrayList<Integer> findMost(final ArrayList<Player> players,
			final String name) {

		ArrayList<Integer> index = new ArrayList<Integer>();
		int max = Integer.MIN_VALUE;
		int idx = -1;

		for (Player p : players) {
			HashMap<String, Integer> h = p.turnIntoMap(p.assetsOnMerchandStand);

			if (h.containsKey(name)) {
				int val = h.get(name);

				if (val == max) {
					index.add(players.indexOf(p));
				}

				if (val > max) {
					max = val;
					idx = players.indexOf(p);
				}
			}
		}

		// in caz ca nu s-a gasit un asset de acel tip in idx va fi -1
		index.add(idx);

		return index;
	}

	/*
	 * gaseste playerii cu al doilea cel mai mare numar de asset-uri ce au numele
	 * "name" si le returnneaza indicii sub forma unui arraylist
	 * primeste ca parametru si ce intoarce metoda findMost pentru a nu le lua in
	 * calcul de aceasta data
	 */
	private static ArrayList<Integer> findSecondMost(final ArrayList<Player> players,
			final String name, final ArrayList<Integer> firstMost) {

		ArrayList<Integer> index = new ArrayList<Integer>();
		int max = Integer.MIN_VALUE;
		int idx = -1;

		for (Player p : players) {
			int count = 0;
			HashMap<String, Integer> h = p.turnIntoMap(p.assetsOnMerchandStand);

			/*
			 * verifica daca playerul curent se afla printre cei returnati de metoda
			 * findMost
			 */
			for (Integer i : firstMost) {
				if (players.indexOf(p) == i) {
					count++;
				}
			}

			// cazul in care nu se afla(count == 0)
			if ((h.containsKey(name)) && (count == 0)) {
				int val = h.get(name);

				if (val == max) {
					index.add(players.indexOf(p));
				}

				if (val > max) {
					max = val;
					idx = players.indexOf(p);
				}
			}
		}

		// in caz ca nu s-a gasit un asset de acel tip in idx va fi -1
		index.add(idx);

		return index;
	}

	/*
	 * adauga la playerii corespunzatori bonusurile date ca parametru ale asset-ului
	 * cu numele "name"
	 */
	private static void kingAndQueenHelper(final ArrayList<Player> players,
			final String name, final int kBonus, final int qBonus) {

		int count = 0;
		final ArrayList<Integer> firstMost = findMost(players, name);
		final ArrayList<Integer> secondMost = findSecondMost(players, name, firstMost);

		/*
		 * daca nu a fost gasit niciun player pentru asset-ul respectiv toti playerii
		 * primesc KingBonus
		 */
		if (firstMost.get(0) == -1) {
			for (Player p : players) {
				p.coins += kBonus;
			}
		} else {
			for (Integer i : firstMost) {
				players.get(i).coins += kBonus;
			}

			/*
			 * daca nu a fost gasit niciun player cu al doilea cel mai mare numar de
			 * asset-uri toti playerii ce nu sunt king primesc QueenBonus
			 */
			if (secondMost.get(0) == -1) {
				for (Player p : players) {
					count = 0;

					for (Integer i : firstMost) {
						if (players.indexOf(p) == i) {
							count++;
						}
					}

					if (count == 0) {
						p.coins += qBonus;
					}
				}
			} else {
				for (Integer i : secondMost) {
					players.get(i).coins += qBonus;
				}
			}
		}

	}

	// foloseste metoda de mai sus pentru fiecare tip de asset
	public static void kingAndQueenBonus(final ArrayList<Player> players) {
		kingAndQueenHelper(players, "Apple", Apple.KING, Apple.QUEEN);
		kingAndQueenHelper(players, "Cheese", Cheese.KING, Cheese.QUEEN);
		kingAndQueenHelper(players, "Bread", Bread.KING, Bread.QUEEN);
		kingAndQueenHelper(players, "Chicken", Chicken.KING, Chicken.QUEEN);
	}

}
