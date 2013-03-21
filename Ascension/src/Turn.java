

import javax.swing.JComponent;


public class Turn {

	Player player;
	Deck gameDeck;
	
	public Turn(Player player, Deck gameDeck){
		
		this.player = player;
		this.gameDeck = gameDeck;
		
	}
	
	public void f() {
		
	}
	
	
	public JComponent interact(JComponent panel){
		
		
			panel.addMouseListener(new MouseListen(this));
			
			return panel;
	}
	
	public void startTurn(){
		this.player.startingHand();
		
	}
	
	
}
