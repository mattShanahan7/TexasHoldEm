package texas;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;
	// Hint: You may find that preprocessing the cards to group them by rank is helpful.
	// Hint: Also, feel free to add more auxiliary variables here.

	public Hand(ArrayList<Card> cards) {
		setCards(cards);
	}

	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = new ArrayList<Card>(cards);
	}
	
	public void selectionSort()
	{
		boolean madeChange = false;
		
		int index = 0;
		int smallestIndex = 0;
		int location = 0;
		
		Card temp;
		
		for (index = 0; index < cards.size() - 1; index++)
		{
			smallestIndex = index;
			
			madeChange = false;
			
			for (location = index + 1; location < cards.size(); location++)
			{
				if (cards.get(location).getRank() < cards.get(smallestIndex).getRank())
				{
					smallestIndex = location;
					madeChange = true;
				}
			}//END inner loop
			
			if (madeChange)
			{
				temp = new Card(cards.get(smallestIndex).getRank());
				cards.set(smallestIndex, cards.get(index));
				cards.set(index, temp);
			}
			
		}//END outer loop
		
	}//END selectionSort
	

	public boolean isPair() 
	{
		//deal with cases that may cause this to return true
		if (this.isTwoPairs() || this.isFullHouse() || this.isThreeofKind())
		{
			return false;
		}
		else if (cards.get(0).getRank() == cards.get(1).getRank() || cards.get(1).getRank() == cards.get(2).getRank() || 
				cards.get(2).getRank() == cards.get(3).getRank() || cards.get(3).getRank() == cards.get(4).getRank() )
		
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public boolean isTwoPairs() 
	{
		//deal with cases that may cause this to return true
		if (this.isFourofKind() || this.isFullHouse() || this.isThreeofKind() )
		{
			return false;
		}
		//if there are two sets of cards that match each other, return true
		else if (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(2).getRank() == cards.get(3).getRank() ||
				cards.get(0).getRank() == cards.get(1).getRank() && cards.get(3).getRank() == cards.get(4).getRank() ||
				cards.get(1).getRank() == cards.get(2).getRank() && cards.get(3).getRank() == cards.get(4).getRank() )
		{
			return true;
		}
		else
			return false;
	}
	
	public boolean isThreeofKind() 
	{
		//deal with cases that may cause this to return true
		if (this.isFourofKind() || this.isFullHouse())
		{
			return false;
		}
		//if three consecutive cards are the same, return true
		else if ( (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(1).getRank() == cards.get(2).getRank() ) ||
				(cards.get(1).getRank() == cards.get(2).getRank() && cards.get(2).getRank() == cards.get(3).getRank() ) ||
				(cards.get(2).getRank() == cards.get(3).getRank() && cards.get(3).getRank() == cards.get(4).getRank() ) )
		{
			return true;
		}
		else
			return false;		
	}

	public boolean isStraight() 
	{
		if ( cards.get(0).getRank() + 1 == cards.get(1).getRank() &&
				cards.get(1).getRank() + 1 == cards.get(2).getRank() &&
				cards.get(2).getRank() + 1 == cards.get(3).getRank() &&
				cards.get(3).getRank() + 1 == cards.get(4).getRank() )
		{
			return true;
		}
		else
			return false;
	}

	public boolean isFullHouse() 
	{
		//if the first three and last two are separately matching, if if the first two and last three are separately matching
		//then return true;
		if ( (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(1).getRank() == cards.get(2).getRank() 
				&& cards.get(3).getRank() == cards.get(4).getRank() ) || 
				( cards.get(0).getRank() == cards.get(1).getRank() && cards.get(2).getRank() == cards.get(3).getRank() &&
				cards.get(3).getRank() == cards.get(4).getRank() ) )
		{
			return true; //if the first three cards are the same, and the last two are different but the same
		}
		else
			return false;
	}

	public boolean isFourofKind() 
	{
		//if four consecutive cards are the same, return true
		if ( (cards.get(0).getRank() == cards.get(1).getRank() && cards.get(1).getRank() == cards.get(2).getRank()
				&& cards.get(2).getRank() == cards.get(3).getRank() ) || (cards.get(1).getRank() == cards.get(2).getRank() && 
				cards.get(2).getRank() == cards.get(3).getRank() && cards.get(3).getRank() == cards.get(4).getRank() ) )
		{
			return true;//if the first four cards are the same return true
		}
		else
			return false;		
	}
	
	public boolean isNone() 
	{
		if (this.isFourofKind() || this.isFullHouse() || this.isPair() || this.isStraight() ||  
				this.isThreeofKind() || this.isTwoPairs() )
		{
			return false;
		}
		else
			return true;
	}
	
	public int compareTo(Hand hand) 
	{
		//start with if statements to check if different types can beat each other
		if (this.isFourofKind() && (!hand.isFourofKind()))
		{
			return 1;
		}
		else if (this.isFullHouse() && hand.isFourofKind())
		{
			return -1; //four of kind beats full house
		}
		if (this.isFullHouse() && (hand.isPair() || hand.isStraight() || hand.isThreeofKind() ||
				hand.isTwoPairs() || hand.isNone() ))
		{
			return 1; //full house beats straight, three of kind, two pairs and a pair
		}
		if (this.isStraight() && (hand.isFourofKind() || hand.isFullHouse()) )
		{
			return -1;//four of kind and full house beat straight
		}
		else if (this.isStraight() && (hand.isThreeofKind() || hand.isTwoPairs() || hand.isPair() ||
				hand.isNone() ))
		{
			return 1; //straight beats three of a kind, two pairs and a pair
		}
		if (this.isThreeofKind() && (hand.isFourofKind() || hand.isFullHouse() || hand.isStraight() ))
		{
			return -1; //four of a kind, full house, and straight beat three of a kind
		}
		else if (this.isThreeofKind() && (hand.isTwoPairs() || hand.isPair() || hand.isNone() ))
		{
			return 1; //three of a kind beats two pairs and a single pair
		}
		if (this.isTwoPairs() && (hand.isFourofKind() || hand.isFullHouse() || hand.isStraight() ||
				hand.isThreeofKind() ))
		{
			return -1; //two pairs is beaten by four of a kind, full house, and straight
		}
		else if (this.isTwoPairs() && ( (hand.isPair()) || hand.isNone() ))
		{
			return 1; //two pairs beat a pair
		}
		if (this.isPair() && !(hand.isNone() || hand.isPair() ))
		{
			return -1; //if the other card is not none, the other hand beats this one
		}
		else if (this.isPair() && hand.isNone() )
		{
			return 1; //a pair beats none
		}
		
		//cases to see who wins when cards are the same
		
		//case for four of a kind
		if (this.isFourofKind() && hand.isFourofKind() )
		{
			//check if winner determined by first 4 cards
			if(cards.get(3).getRank() > hand.getCards().get(3).getRank())
			{
				return 1; //if these first 4 cards are greater, this wins
			}
			else if (cards.get(3).getRank() < hand.getCards().get(3).getRank())
			{
				return -1;//if these first 4 cards are less, the other hand wins
			}
			//if that doesn't provide a winner, check the last card
			else if (cards.get(4).getRank() > hand.getCards().get(4).getRank() )
			{
				return 1;
			}
			else if ( cards.get(4).getRank() < hand.getCards().get(4).getRank() )
			{
				return -1;
			}
			//if the first card doesn't provide a winner, check the first card
			else if (cards.get(0).getRank() > hand.getCards().get(0).getRank() )
			{
				return 1;
			}
			else if ( cards.get(0).getRank() < hand.getCards().get(0).getRank() )
			{
				return -1;
			}
			//if that doesn't provide a winner, then there is a tie
			else
			{
				return 0;
			}
		}//END case for both four of a kind
		
		if (this.isFullHouse() && hand.isFullHouse() )
		{
			//if both are a full house, compare card index 2
			if (cards.get(2).getRank() > hand.getCards().get(2).getRank() )
			{
				return 1;
			}
			else if (cards.get(2).getRank() < hand.getCards().get(2).getRank() )
			{
				return -1;
			}
			//if that doesn't provide an answer, check where the group of two cards is located
			
			else if (cards.get(1).getRank() == cards.get(2).getRank() && 
					hand.getCards().get(1).getRank() == hand.getCards().get(2).getRank())
			{
				//if the 3 card group is to the left, compare the last two cards
				if (cards.get(3).getRank() > hand.getCards().get(3).getRank() )
				{
					return 1;
				}
				else if (cards.get(3).getRank() < hand.getCards().get(3).getRank() )
				{
					return -1;
				}
				else
					return 0;
			}
			//check if the 3 card groups are on opposite sides
			else if (cards.get(1).getRank() == cards.get(2).getRank() && 
					hand.getCards().get(2).getRank() == hand.getCards().get(3).getRank() )
			{
				if (cards.get(3).getRank() > hand.getCards().get(3).getRank() )
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			else if (cards.get(1).getRank() != cards.get(2).getRank() &&
					hand.getCards().get(1).getRank() == hand.getCards().get(2).getRank() )
			{
				//if the group of 3 is to the right, compare the first two cards
				if (cards.get(1).getRank() > hand.getCards().get(1).getRank() )
				{
					return 1;
				}
				else if (cards.get(1).getRank() < hand.getCards().get(1).getRank() )
				{
					return -1;
				}
				else 
					return 0;
			}
			
			else
			{
				return 0;
			}
		}//END case for both full house
		
		if (this.isStraight() && hand.isStraight() )
		{
			//this one is easy, just compare 
			if (cards.get(0).getRank() > hand.getCards().get(0).getRank() )
			{
				return 1;
			}
			else if (cards.get(0).getRank() < hand.getCards().get(0).getRank() )
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}//END case for both straight
		
		if (this.isThreeofKind() && hand.isThreeofKind() )
		{
			//check if the group of three can determine an answer
			if (cards.get(2).getRank() > hand.getCards().get(2).getRank() )
			{
				return 1;
			}
			else if (cards.get(2).getRank() < hand.getCards().get(2).getRank() )
			{
				return -1;
			}
			//if not, just check the last card, which is the highest value card not involved
			//in the group of three
			else if (cards.get(4).getRank() > hand.getCards().get(4).getRank() )
			{
				return 1;
			}
			else if (cards.get(4).getRank() < hand.getCards().get(4).getRank() )
			{
				return -1;
			}
			//if THAT doesn't work, check index 3, and if that doesn't work consider it a tie
			else if (cards.get(3).getRank() > hand.getCards().get(3).getRank() )
			{
				return 1;
			}
			else if (cards.get(3).getRank() < hand.getCards().get(3).getRank() )
			{
				return -1;
			}
			else 
				return 0;
			
		}//END case for both Three of Kind
		
		if ( (this.isTwoPairs() && hand.isTwoPairs() ) || 
				(this.isPair() && hand.isPair() ) )
		{
			//work backwards from index 4, and see if there is a difference
			
			for (int i = 4; i >= 0; i--)
			{
				if (cards.get(i).getRank() > hand.getCards().get(i).getRank() )
				{
					return 1;
				}
				else if (cards.get(i).getRank() < hand.getCards().get(i).getRank() )
				{
					return -1;
				}
			}
			return 0;
			
		}//END case for both two pairs
		
		if (this.isNone() && hand.isNone() )
		{
			for (int i = 4; i >= 0; i--)
			{
				if (cards.get(i).getRank() > hand.getCards().get(i).getRank() )
				{
					return 1;
				}
				else if (cards.get(i).getRank() < hand.getCards().get(i).getRank() )
				{
					return -1;
				}
			}
			return 0;
		}//END case for both none
		
		else
			return 0;
		
	}//END Hand.compareTo
}
