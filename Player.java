package texas;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> cards;
	private Hand bestHand;
	
	public Player(String name, ArrayList<Card> cards) {
		this.name = name;
		this.cards = new ArrayList<Card>(cards);
	}
	
	public Hand getBestHand()
	{
		return bestHand;
	}
	
	public void setBestHand(Hand theBestHand)
	{
		bestHand = new Hand(theBestHand.getCards());
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
}
