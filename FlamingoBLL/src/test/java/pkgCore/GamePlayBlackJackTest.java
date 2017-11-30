package pkgCore;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import pkgEnum.eBlackJackResult;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlayBlackJackTest {

	@Test
	public void TestPlayerWinning() throws HandException, DeckException {

		HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID, Player>();

		Player d1 = new Player("Dealer", 0);
		Player p1 = new Player("Maddie", 1);

		hmTablePlayer.put(p1.getPlayerID(), d1);
		hmTablePlayer.put(p1.getPlayerID(), p1);

		Deck d = new Deck();

		GamePlayBlackJack g1 = new GamePlayBlackJack(hmTablePlayer, d);
		Iterator it = g1.getHmGameHands().entrySet().iterator();

		GamePlayerHand Thing1 = null;
		HandBlackJack Thing2 = null;

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack h1 = (HandBlackJack) pair.getValue();
			GamePlayerHand gh1 = (GamePlayerHand) pair.getKey();

			if (gh1.getPlayerID() == d1.getPlayerID()) {
				g1.putHandToGame(gh1, h1);
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.CLUBS, eRank.ACE));
				g1.putHandToGame(gh1, h1);
				Thing1 = gh1;

				Thing2 = h1;
			}

			if (gh1.getPlayerID() == p1.getPlayerID()) {
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.SPADES, eRank.EIGHT));
				g1.putHandToGame(gh1, h1);
			}

		}

		g1.dealerHand(new Card(eSuit.SPADES, eRank.SIX));
		g1.dealerHand(new Card(eSuit.DIAMONDS, eRank.JACK));

		g1.ScoreGame(Thing1);
		assertEquals(g1.getResult(), eBlackJackResult.WIN);

	}

	@Test
	public void TestPlayerLosing() throws DeckException, HandException {
		HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID, Player>();

		Player d1 = new Player("Dealer", 0);
		Player p1 = new Player("Maddie", 1);

		hmTablePlayer.put(p1.getPlayerID(), d1);
		hmTablePlayer.put(p1.getPlayerID(), p1);

		Deck d = new Deck();

		GamePlayBlackJack g1 = new GamePlayBlackJack(hmTablePlayer, d);
		Iterator it = g1.getHmGameHands().entrySet().iterator();

		GamePlayerHand Thing1 = null;
		HandBlackJack Thing2 = null;

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack h1 = (HandBlackJack) pair.getValue();
			GamePlayerHand gh1 = (GamePlayerHand) pair.getKey();

			if (gh1.getPlayerID() == d1.getPlayerID()) {
				g1.putHandToGame(gh1, h1);
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.CLUBS, eRank.TEN));				
				g1.Draw2(gh1, new Card(eSuit.CLUBS, eRank.TEN));

				g1.putHandToGame(gh1, h1);
				Thing1 = gh1;

				Thing2 = h1;
			}

			if (gh1.getPlayerID() == p1.getPlayerID()) {
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.SPADES, eRank.EIGHT));				
				g1.Draw2(gh1, new Card(eSuit.SPADES, eRank.EIGHT));

				g1.putHandToGame(gh1, h1);
			}

		}

		g1.dealerHand(new Card(eSuit.SPADES, eRank.SIX));
		g1.dealerHand(new Card(eSuit.DIAMONDS, eRank.JACK));

		g1.ScoreGame(Thing1);
		assertEquals(g1.getResult(), eBlackJackResult.LOSE);

	}

	@Test
	public void TestPlayerPush() throws DeckException, HandException {
		HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID, Player>();

		Player d1 = new Player("Dealer", 0);
		Player p1 = new Player("Maddie", 1);

		hmTablePlayer.put(p1.getPlayerID(), d1);
		hmTablePlayer.put(p1.getPlayerID(), p1);

		Deck d = new Deck();

		GamePlayBlackJack g1 = new GamePlayBlackJack(hmTablePlayer, d);
		Iterator it = g1.getHmGameHands().entrySet().iterator();

		GamePlayerHand Thing1 = null;
		HandBlackJack Thing2 = null;

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack h1 = (HandBlackJack) pair.getValue();
			GamePlayerHand gh1 = (GamePlayerHand) pair.getKey();

			if (gh1.getPlayerID() == d1.getPlayerID()) {
				g1.putHandToGame(gh1, h1);
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.CLUBS, eRank.TEN));
				g1.putHandToGame(gh1, h1);
				Thing1 = gh1;

				Thing2 = h1;
			}

			if (gh1.getPlayerID() == p1.getPlayerID()) {
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.SPADES, eRank.TEN));
				g1.putHandToGame(gh1, h1);
			}
		

		}

		g1.dealerHand(new Card(eSuit.SPADES, eRank.QUEEN));
		g1.dealerHand(new Card(eSuit.DIAMONDS, eRank.TEN));

		g1.ScoreGame(Thing1);
		assertEquals(g1.getResult(), eBlackJackResult.TIE);

	}

	@Test
	public void TestTwoPlayersWinning() throws DeckException, HandException {
		HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID, Player>();

		Player d1 = new Player("Dealer", 0);
		Player p1 = new Player("Maddie", 1);
		Player p2 = new Player("Michelle", 2);

		hmTablePlayer.put(p1.getPlayerID(), d1);
		hmTablePlayer.put(p1.getPlayerID(), p1);
		hmTablePlayer.put(p1.getPlayerID(), p2);

		Deck d = new Deck();

		GamePlayBlackJack g1 = new GamePlayBlackJack(hmTablePlayer, d);
		Iterator it = g1.getHmGameHands().entrySet().iterator();

		GamePlayerHand Thing1 = null;
		HandBlackJack Thing2 = null;

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack h1 = (HandBlackJack) pair.getValue();
			GamePlayerHand gh1 = (GamePlayerHand) pair.getKey();

			if (gh1.getPlayerID() == d1.getPlayerID()) {
				g1.putHandToGame(gh1, h1);
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.FIVE));
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.SIX));
				g1.Draw2(gh1, new Card(eSuit.CLUBS, eRank.TEN));
				g1.putHandToGame(gh1, h1);
				Thing1 = gh1;
				Thing2 = h1;
			}

			if (gh1.getPlayerID() == p1.getPlayerID()) {
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.QUEEN));
				g1.Draw2(gh1, new Card(eSuit.SPADES, eRank.EIGHT));
				g1.putHandToGame(gh1, h1);
			}
			
			if (gh1.getPlayerID() == p2.getPlayerID()) {
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.FIVE));
				g1.Draw2(gh1, new Card(eSuit.HEARTS, eRank.SIX));
				g1.Draw2(gh1, new Card(eSuit.CLUBS, eRank.TEN));
				g1.putHandToGame(gh1, h1);
			}

		}

		g1.dealerHand(new Card(eSuit.SPADES, eRank.SEVEN));
		g1.dealerHand(new Card(eSuit.DIAMONDS, eRank.TEN));

		g1.ScoreGame(Thing1);

		assertEquals(g1.getResult(), eBlackJackResult.WIN);


	}
}

/*
 * HandBlackJack HBJ = new HandBlackJack();
 * 
 * HBJ.AddCard(new Card(eSuit.CLUBS, eRank.TWO)); HBJ.AddCard(new
 * Card(eSuit.HEARTS, eRank.TEN)); HBJ.AddCard(new Card(eSuit.HEARTS,
 * eRank.EIGHT));
 * 
 * 
 * GamePlayBlackJack GPBJ = new GamePlayBlackJack(hmTablePlayer , d);
 * GPBJ.setHmGameHands(GPBJ.gethmGameHand(GP), HBJ);
 */
