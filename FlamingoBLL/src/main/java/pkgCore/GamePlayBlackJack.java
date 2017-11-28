package pkgCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;
import pkgEnum.eBlackJackResult;
import pkgEnum.eGameType;

public class GamePlayBlackJack extends GamePlay {

	private Player pDealer = new Player("Dealer", 0);
	private Hand hDealer = new HandBlackJack();

	public GamePlayBlackJack(HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {

		super(eGameType.BLACKJACK, hmTablePlayers, dGameDeck);

		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			Hand h = new HandBlackJack();
			GamePlayerHand GPH = new GamePlayerHand(this.getGameID(), p.getPlayerID(), h.getHandID());
			this.putHandToGame(GPH, h);
		}
	}

	@Override
	protected Card Draw(GamePlayerHand GPH) throws DeckException, HandException {

		Card c = null;

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			c = h.Draw(this.getdGameDeck());

			h.AddCard(c);

			this.putHandToGame(GPH, h);

		}
		return c;
	}

	private boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
		boolean bCanPlayerDraw = false;

		Hand h = this.gethmGameHand(GPH);

		HandScoreBlackJack HSB = (HandScoreBlackJack) h.ScoreHand();

		for (int n : HSB.getNumericScores()) {
			if (n <= 21) {
				bCanPlayerDraw = true;
			}
		}
		return bCanPlayerDraw;
	}

	public boolean bDoesDealerHaveToDraw() throws HandException {
		boolean bDoesDealerHaveToDraw = true;

		HandScoreBlackJack HSB = (HandScoreBlackJack) hDealer.ScoreHand();

		for (int n : HSB.getNumericScores()) {
			if (n > 16) {
				bDoesDealerHaveToDraw = false;
			}
		}
		return bDoesDealerHaveToDraw;
	}

	private boolean isDealerBusted() throws HandException {

		HandScoreBlackJack HSB = (HandScoreBlackJack) hDealer.ScoreHand();
		boolean bDealerIsBusted = true;

		for (int n : HSB.getNumericScores()) {
			if (n <= 21) {
				bDealerIsBusted = false;
			}
		}
		return bDealerIsBusted;
	}

	private int BestScore(HandScoreBlackJack HSB) throws HandException {

		int k = 0;
		for (int n : HSB.getNumericScores()) {
			if (n > k && n <= 21) {
				k = n;
			}
		}
		return k;
	}

	public void ScoreGame(GamePlayerHand GPH) throws HandException {

;
		Hand h = this.gethmGameHand(GPH);
		HandScoreBlackJack HSB = (HandScoreBlackJack) h.ScoreHand();
		HandScoreBlackJack HSBDealer = (HandScoreBlackJack) hDealer.ScoreHand();

		// player and dealer did NOT bust
		if (bCanPlayerDraw(GPH) && !isDealerBusted()) {
			{
				// win
				if (BestScore(HSB) > BestScore(HSBDealer)) {
					eBlackJackResult result = eBlackJackResult.WIN;
				}
				// tie
				else if (BestScore(HSB) == BestScore(HSBDealer)) {
					eBlackJackResult result = eBlackJackResult.LOSE;
				}
				// lose
				else {
					eBlackJackResult result = eBlackJackResult.TIE;
				}
			}
		}
		// just dealer busted
		if (bCanPlayerDraw(GPH) && isDealerBusted()) {
			eBlackJackResult result = eBlackJackResult.WIN;
		}
		// just player busted
		if (!bCanPlayerDraw(GPH) && !isDealerBusted()) {
			eBlackJackResult result = eBlackJackResult.LOSE;
		}
		// player and dealer BOTH bust
		if (!bCanPlayerDraw(GPH) && isDealerBusted()) {
			eBlackJackResult result = eBlackJackResult.LOSE;
		}
		
		// 
	}


	

	public Player getpDealer() {
		return pDealer;
	}
}
