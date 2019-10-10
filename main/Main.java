package main;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import assets.Apple;
import assets.Asset;
import assets.Barrel;
import assets.Bread;
import assets.Cheese;
import assets.Chicken;
import assets.Pepper;
import assets.Silk;
import strategies.BaseStrategyPlayer;
import strategies.BribeStrategyPlayer;
import strategies.GreedyStrategyPlayer;
import strategies.Player;

public final class Main {

	private static final class GameInputLoader {
		private final String mInputPath;

		private GameInputLoader(final String path) {
			mInputPath = path;
		}

		public GameInput load() {
			List<Integer> assetsIds = new ArrayList<>();
			List<String> playerOrder = new ArrayList<>();

			try {
				BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
				String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
				String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

				for (String strAssetId : assetIdsLine.split(",")) {
					assetsIds.add(Integer.parseInt(strAssetId));
				}

				for (String strPlayer : playerOrderLine.split(",")) {
					playerOrder.add(strPlayer);
				}
				inStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return new GameInput(assetsIds, playerOrder);
		}
	}

	public static void main(final String[] args) {
		GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
		GameInput gameInput = gameInputLoader.load();

		// lista in care se afla bunurile date ca input
		ArrayList<Asset> assetsList = turnToAssetList(gameInput.getAssetIds());

		// lista in care se afla jucatorii
		final ArrayList<Player> players = turnToPlayerList(gameInput.getPlayerNames());

		// numarul de runde e dublul numarului jucatorilor
		for (int round = 1; round <= 2 * players.size(); round++) {

			int idx = 0;
			// se gaseste seriful rundei respective
			if (round <= players.size()) {
				idx = round - 1;
				Player sheriff = players.get(idx);
				sheriff.setIsSheriff(true);
			} else {
				idx = round - players.size() - 1;
				Player sheriff = players.get(idx);
				sheriff.setIsSheriff(true);
			}

			// in acest for se (re)fac cartile din maine si se pun bunuri in sac
			for (Player p : players) {

				p.addInHand(assetsList);

				if (!p.getIsSheriff()) {
					int r = p.roundNr();
					p.createBag(r);
				}

			}

			Player sheriff = players.get(idx);

			// in acest for se face verificarea sacilor de catre serif
			for (Player p : players) {

				if (players.indexOf(p) != idx) {
					sheriff.checkBag(p, assetsList);
				}
			}

			// i se ia rolul de serif la finalul rundei
			Player p = players.get(idx);
			p.setIsSheriff(false);

		}

		// se atribuie bonusurile de King&Queen
		Player.kingAndQueenBonus(players);

		int[] punctaje = new int[players.size()];
		// se pun punctajele intr-un vector pt a fi sortate
		for (Player p : players) {
			punctaje[players.indexOf(p)] = p.getCoins();
		}

		Arrays.sort(punctaje);
		int idx = -1;
		String string = null;

		// se afiseaza punctajele finale
		for (int i = (players.size() - 1); i >= 0; i--) {
			for (Player p : players) {
				if (p.getCoins() == punctaje[i] && (idx != players.indexOf(p))) {
					idx = players.indexOf(p);
					string = gameInput.getPlayerNames().get(idx).toUpperCase();
					System.out.println(string + ":" + punctaje[i]);
					break;
				}
			}
		}

		// TODO Implement the game logic.
	}

	// creaza o un arraylist de asset-uri in functie de indicii de la input
	public static ArrayList<Asset> turnToAssetList(final List<Integer> integers) {

		final ArrayList<Asset> list = new ArrayList<Asset>();
		final int idxApple = 0;
		final int idxCheese = 1;
		final int idxBread = 2;
		final int idxChicken = 3;
		final int idxSilk = 10;
		final int idxPepper = 11;
		final int idxBarrel = 12;

		for (Integer e : integers) {
			if (e == idxApple) {
				Asset a = new Apple();
				list.add(a);
			}

			if (e == idxCheese) {
				Asset a = new Cheese();
				list.add(a);
			}

			if (e == idxBread) {
				Asset a = new Bread();
				list.add(a);
			}

			if (e == idxChicken) {
				Asset a = new Chicken();
				list.add(a);
			}

			if (e == idxSilk) {
				Asset a = new Silk();
				list.add(a);
			}

			if (e == idxPepper) {
				Asset a = new Pepper();
				list.add(a);
			}

			if (e == idxBarrel) {
				Asset a = new Barrel();
				list.add(a);
			}
		}

		return list;

	}

	// creaza un arraylist de playeri in functie de string-urile de la input
	public static ArrayList<Player> turnToPlayerList(final List<String> strings) {

		final ArrayList<Player> players = new ArrayList<Player>();
		for (String s : strings) {
			if (s.equals("basic")) {
				Player player = new BaseStrategyPlayer();
				players.add(player);
			}
			if (s.equals("greedy")) {
				Player player = new GreedyStrategyPlayer();
				players.add(player);
			}
			if (s.equals("bribed")) {
				Player player = new BribeStrategyPlayer();
				players.add(player);
			}
		}

		return players;
	}

}
