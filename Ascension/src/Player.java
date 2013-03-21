
public class Player {
	
	public PlayerDeck playerDeck;
	public int honorTotal;
	
	public Player(){
		
	}
	
	public Player(PlayerDeck deck){
		this.playerDeck = deck;
		this.honorTotal = 0;
	}
	
	public void inncrementHonor(int incre){
		this.honorTotal += incre;
	}
	
	
	public void startingHand(){
		for(int i = 0; i < 5; i++){
			this.playerDeck.drawCard();
		}
	}
	
}
