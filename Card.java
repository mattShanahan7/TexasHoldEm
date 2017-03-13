package texas;

public class Card {
	protected int rank; // {A, K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2}
	
	public Card(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank; 
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public String toString() {
		String result = "";
		switch (rank) {
			case 14: result += "A"; break;
			case 13: result += "K"; break;
			case 12: result += "Q"; break;
			case 11: result += "J"; break;
			default: result += rank;
		}
		
		return result;
	}
}
