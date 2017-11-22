package pkgCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;

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

		HandScoreBlackJack HSB = (HandScoreBlackJack)h.ScoreHand();
		
		for (int n : HSB.getNumericScores()) {
			if (n <= 21) {
				bCanPlayerDraw = true;
			}
		}
		return bCanPlayerDraw;
	}
	
	
	
	public boolean bDoesDealerHaveToDraw() throws HandException
	{
		boolean bDoesDealerHaveToDraw = true;
		
		
		HandScoreBlackJack HSB = (HandScoreBlackJack)hDealer.ScoreHand();
		
		for (int n : HSB.getNumericScores()) {
			if (n > 16) {
				bDoesDealerHaveToDraw = false;
			}
		}
		return bDoesDealerHaveToDraw;
	}
	
	private boolean isDealerBusted() throws HandException {

		HandScoreBlackJack HSB = (HandScoreBlackJack)hDealer.ScoreHand();
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
	
	public void ScoreGame(GamePlayerHand GPH) throws HandException
	{
		boolean bIsHandWinner = false;

		Hand h = this.gethmGameHand(GPH);
		HandScoreBlackJack HSB = (HandScoreBlackJack)h.ScoreHand();
		HandScoreBlackJack HSBDealer = (HandScoreBlackJack)hDealer.ScoreHand();

		if (bCanPlayerDraw(GPH) && !isDealerBusted() ) {
			{
				if (BestScore(HSB) > BestScore(HSBDealer)) {
					 
				}
				else if (BestScore(HSB) == BestScore(HSBDealer)) {
					
				}
			}
		}
	
	
		//	TODO: Determine if player is a winner
		
		//	TODO: Find the Dealer's hand
		//	TODO: Score Dealer's hand
		
		//	TODO: -Find Player's hand 
		//	TODO: -Score Player's hand
		
		//	TODO: If Player's hand > Dealer's hand and <= 21, then eBlackJackResult = WIN
		//			If Player's hand < Dealer's hand and Dealer didn't bust = LOSE
		//			If Player's hand == Dealer's hand and both didn't bust = TIE
		
		
	}

	public Player getpDealer() {
		return pDealer;
	}
}
