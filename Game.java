package texas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {
	private ArrayList<Card> board;
	private ArrayList<Player> players; 
	
	public Game() {
		board = new ArrayList<Card>();
		players = new ArrayList<Player>();
	}
	
	public ArrayList<Card> getBoard()
	{
		return board;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	public void addPlayer(Player player) {
		players.add(player);		
	}
	
	public void setBoard(ArrayList<Card> board) {
		this.board = new ArrayList<Card>(board);
	}
	
	public void readSettings(String filename) 
	{
		// First line contains five cards
		
		// Second line contains an integer -- the number of players
		
		// Each following line contains name of the player and two cards
		
		try{
			File file = new File(filename);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String inputText = "";
			int numPlayers = 0;
			//String junk = "";
			ArrayList<Card> tempBoard = new ArrayList<Card>();
		
			String [] inputArray;
			inputText = reader.readLine();
			
			//split the string into an array, then parse the strings to ints
			inputArray = inputText.split(" ");
			for (int i = 0; i < inputArray.length; i++)
			{
				//create new card object and add it to the board ArrayList
				Card newCard = new Card(Integer.parseInt(inputArray[i]));
				tempBoard.add(newCard);
			}//END for loop for creating the board
			
			//set the game board to the temp board
			setBoard(tempBoard);
			
			//read in the number of players and subtract the ASCII offset
			numPlayers = (reader.read() - 48);
			
			//read in the junk from the rest of the line
			reader.readLine();
			
			//Run for loop for number of players in game
			for (int i = 0; i < numPlayers; i++)
			{
				String [] playerArray;
				ArrayList<Card> tempCards = new ArrayList<Card>();
				
				inputText = reader.readLine();
				playerArray = inputText.split(" ");

				//create objects for both cards and add them to the temp array
				tempCards.add(new Card(Integer.parseInt(playerArray[1])));
				tempCards.add(new Card(Integer.parseInt(playerArray[2])));
				
				//create a new player object with information read in, and add it the player arraylist
				Player newPlayer = new Player(playerArray[0], tempCards);
				addPlayer(newPlayer);
			}//END for loop for inputting players in game
			
		}//END try block
		catch (IOException except)
		{
			System.err.println("Error: " + except.getMessage());
			System.exit(1);
		}
		
	}//END read settings

	public ArrayList<Hand> generateCandidates(ArrayList<Card> board, Player player) 
	{
		ArrayList<Hand> candidates = new ArrayList<Hand>();
		
		//uses an algorithm for generating indexes of combinations found online
		//http://hmkcode.com/calculate-find-all-possible-combinations-of-an-array-using-java/
		
		//Creating all combinations of taking two cards from the deck
		int boardLength = 5;
		int toSelect = 3;
		
		int [] combo = new int[toSelect];
		
		int r = 0;
		int index = 0;
		
		while (r >= 0)
		{
			if (index  <= (boardLength + (r - toSelect)))
			{
				combo[r] = index;
				if (r == toSelect - 1)
				{
					ArrayList<Card> tempCards = new ArrayList<Card>();
					//add the player's first two cards
					tempCards.add(player.getCards().get(0));
					tempCards.add(player.getCards().get(1));
					
					for (int i = 0; i < combo.length; i++)
					{
						//create a new card from the index in combo to add to the hand
						Card newCard = new Card(board.get(combo[i]).getRank() );
						
						//add new card to the temp ArrayList
						tempCards.add(newCard);

					}
					//add create a hand object from the temp arraylist
					Hand newHand = new Hand(tempCards);
					
					//add the hand object to the hands arrayList
					candidates.add(newHand);
					index++;
				}
				else
				{
					index = combo[r] + 1;
					r++;
				}
				
			}//END if
			else
			{
				r--;
				if (r > 0)
					index = combo[r] + 1;
				else
					index = combo[0] + 1;
			}//END else
		}//END while loop
		
		//Repeat procedure up top, but only add 1 card from each player
		//Start by setting toSelect to 4, and recreating combo
		toSelect = 4;
		combo = new int[toSelect];
		
		r = 0;
		index = 0;
		
		while (r >= 0)
		{
			if (index  <= (boardLength + (r - toSelect)))
			{
				combo[r] = index;
				if (r == toSelect - 1)
				{
					//loop through this procedure twice now, each time adding one card from
					//the player
					for (int j = 0; j < 2; j++)
					{
						ArrayList<Card> tempCards = new ArrayList<Card>();
						tempCards.add(player.getCards().get(j));
						
						for (int i = 0; i < combo.length; i++)
						{
							//create a new card from the index in combo to add to the hand
							Card newCard = new Card(board.get(combo[i]).getRank() );
							
							//add new card to the temp ArrayList
							tempCards.add(newCard);
						}
						//add create a hand object from the temp arraylist
						Hand newHand = new Hand(tempCards);
						
						//add the hand object to the hands arrayList
						candidates.add(newHand);
					}//END for loop
					
					index++;
				}
				else
				{
					index = combo[r] + 1;
					r++;
				}
				
			}//END if
			else
			{
				r--;
				if (r > 0)
					index = combo[r] + 1;
				else
					index = combo[0] + 1;
			}//END else
		}//END while loop
		
		Hand boardHand = new Hand(board);
		candidates.add(boardHand);
		
		//call selection sort on all of the candidates
		for (int i = 0; i < candidates.size(); i++)
		{
			candidates.get(i).selectionSort();
		}
		
		return candidates;
	}//END generateCandidates()
	
	
	public String announceWinner() 
	{
		//ArrayList<Hand> bestHands = new ArrayList<Hand>();
		//idea: iterate through each player's candidates, go through if statement conditions, if satisfied, break
		//and add bestHand to the arrayList, then 
		String returnString = "";
		
		//find the best Hand for each player
		for (int i = 0; i < players.size(); i++)
		{
			ArrayList<Hand> candidatesList = new ArrayList<Hand>();
			candidatesList = generateCandidates(board, players.get(i));
			
			//set the best hand as the first in the list
			players.get(i).setBestHand(candidatesList.get(0));
			
			for (int j = 1; j < candidatesList.size(); j++)
			{
				//use compareTo function to compare index j to the bestHand
				//if it returns 1, make index j the best hand, if not, do nothing
				if (candidatesList.get(j).compareTo(players.get(i).getBestHand() ) == 1)
				{
					players.get(i).setBestHand(candidatesList.get(j));
				}

			}//END for loop to call compareTo for the other hands
			
		}//END for loop to find the best hand for each player
		
		//set the winning hand to the best Hand of the first player, and the winning name is the first name
		Hand winningHand = new Hand(players.get(0).getBestHand().getCards());
		String winner = players.get(0).getName();
		boolean isTie = false;
		
		//iterate through players and compare the best hand of each player to find the winner
		for (int i = 1; i < players.size(); i++)
		{
			//if a player has a better hand, update the winningHand and the name of the winner
			if (players.get(i).getBestHand().compareTo(winningHand) == 1)
			{
				winningHand = new Hand(players.get(i).getBestHand().getCards());
				winner = "";
				winner += players.get(i).getName();
			}
			//if there is a tie, set the isTie flag to true and add another winner
			else if (players.get(i).getBestHand().compareTo(winningHand) == 0 )
			{
				//set the isTie flag 
				isTie = true;
				winner += " ";
				winner += players.get(i).getName();
			}
		}//END for loop to compare players' best hands
		
		//if there was a tie, output names of winners and "tie", otherwise output the name of the winner
		if (isTie)
		{
			returnString += winner;
			returnString += " tie";
			return returnString;
		}
		else
		{
			returnString += winner;
			returnString += " wins";
			return returnString;
		}
			
	}//END announceWinner()	
	
	public static void main(String[] Args) {
		// NOTE: Please do not change the main function; Needed for AutoGrading
		// In Eclipse, you can create settings.conf file under the project
		Game game = new Game();
		game.readSettings("settings.conf");
		System.out.println(game.announceWinner());
	}
}
