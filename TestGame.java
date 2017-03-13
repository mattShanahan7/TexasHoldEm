package texas;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class TestGame {

	@Test
	public void testCompareTo() 
	{
		//Set card ranks here in ascending order, if if not in order, call selection sort
		//before calling comparing the two hands
		int [] cardRanks1 = {3, 10, 10, 13, 13};
		int [] cardRanks2 = {3, 10, 10, 11, 13};
		
		
		ArrayList<Card> cards1 = new ArrayList<Card>();
		ArrayList<Card> cards2 = new ArrayList<Card>();
		
		for (int i = 0; i < cardRanks1.length; i++)
		{
			cards1.add(new Card(cardRanks1[i]));
			cards2.add(new Card(cardRanks2[i]));
		}
		
		Hand hand1 = new Hand(cards1);
		Hand hand2 = new Hand(cards2);
		
		//if the cards are unsorted, uncomment the code below to sort 
		//the two lists before comparing
		
		//hand1.selectionSort();
		//hand2.selectionSort();
		
		assertEquals(1, hand1.compareTo(hand2));
		assertTrue(hand2.isPair());
		assertTrue(hand1.isTwoPairs());
	}
	
	
	
	@Test
	public void testGenerateCandidates()
	{
		Game game = new Game();
		ArrayList<Card> board = new ArrayList<Card>();
		board.add(new Card(4));
		board.add(new Card(13));
		board.add(new Card(4));
		board.add(new Card(8));
		board.add(new Card(7));
		
		ArrayList<Card> playerCards = new ArrayList<Card>();
		playerCards.add(new Card(14));
		playerCards.add(new Card(4));
		
		Player player = new Player("Player", playerCards);
		ArrayList<Hand> candidates = new ArrayList<Hand>();
		candidates = game.generateCandidates(board, player);
		
		//check that the length of candidates is 21, meaning all possible candidates
		//have been generated
		assertEquals(21, candidates.size());
	}
	
	@Test
	public void testAnnounceWinner()
	{
		//This test basically just follows the same procedure as main, and just confirms that
		//the final output is correct
		Game game = new Game();
		game.readSettings("settings.conf");
		assertEquals("Ted wins", game.announceWinner());
	}
	
	@Test
	public void testTie()
	{
		//This test makes sure that the tie condition is working by creating a new player with 
		//the same hand as the winner from settings.conf, which is Ted
		Game game = new Game();
		game.readSettings("settings.conf");
		
		ArrayList<Card> newPCards = new ArrayList<Card>();
		newPCards.add(new Card(13));
		newPCards.add(new Card(13));
		
		Player newPlayer = new Player("Newguy", newPCards);
		game.addPlayer(newPlayer);
		
		assertEquals("Ted Newguy tie", game.announceWinner());
	}
	
	@Test
	public void testReadSettings()
	{
		//Reads in settings.conf, then checks whether the size of the board is 5 cards,
		//whether there are 4 players, and whether each player starts with 2 cards
		Game game = new Game();
		game.readSettings("settings.conf");
		
		assertEquals(5, game.getBoard().size());
		assertEquals(4, game.getPlayers().size());
		
		//check that each player starts with 2 cards
		for (int i = 0; i < game.getPlayers().size(); i++)
		{
			assertEquals(2, game.getPlayers().get(i).getCards().size());
		}
	}
	
	@Test
	public void test2Wins()
	{
		Game game = new Game();
		ArrayList<Card> board = new ArrayList<Card>();
		
		board.add(new Card(4));
		board.add(new Card(13));
		board.add(new Card(4));
		board.add(new Card(2));
		board.add(new Card(8));
		
		game.setBoard(board);
		
		ArrayList<Card> p1cards = new ArrayList<Card>();
		
		p1cards.add(new Card(13));
		p1cards.add(new Card(3));
		Player player1 = new Player("Quavo", p1cards);
		
		ArrayList<Card> p2cards = new ArrayList<Card>();
		
		p2cards.add(new Card(7));
		p2cards.add(new Card(10));
		Player player2 = new Player("Offset", p2cards);
		
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		assertEquals("Quavo wins", game.announceWinner());
	}

}
