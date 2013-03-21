import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JComponent {
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		//g2.draw(gameDeck.location);
		Image image = new ImageIcon(this.getClass().getResource("BasicUser.jpg")).getImage();
		g2.draw(gameDeck.handLocation);
		for(Card c : gameDeck.hand) {
			g2.drawImage(image, c.getLocation().x, c.getLocation().y, c.getLocation().width, c.getLocation().height, null);
		}
	}

	
	public int totalHonor;
	public ArrayList<Player> players;
	public Deck gameDeck;
	
	Game(int honor, ArrayList<Player> players){
		this(honor, players, null);
	}
	
	Game(int honor, ArrayList<Player> players, Deck gameDeck){
		this.totalHonor = honor;
		this.players = players;
		this.gameDeck = gameDeck;
	}
	
	
	public void playTheGame(){

		JComponent panel = new JPanel();
		
		
		
//		while(this.totalHonor >= 0){
			
			
				Turn thisTurn = new Turn(players.get(0), this.gameDeck);
				
				thisTurn.interact(panel);
				
			
			
//		}
				while(true) {
					try{
						Thread.sleep(400);
						this.repaint();
					}
					catch(Exception e) {
						
					}
				}
					
		
		
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(1500, 900);
		f.setVisible(true);
		ArrayList<Player> pl  = new ArrayList<Player>();
		pl.add(new Player());
		pl.add(new Player());
		ArrayList<Card> notPlayed = DeckTest.randomCardList();
		
		ArrayList<Card> hand =  DeckTest.randomCardList();
		
		ArrayList<Card> discard =  DeckTest.randomCardList();
		
		Deck d = new Deck(notPlayed, hand, discard, new Rectangle(0,500,1000,300));
		Game g = new Game(100,pl, d);
		f.add(g);
		g.playTheGame();
	}
}
