import java.awt.Rectangle;
import java.util.ArrayList;


public class PlayerDeck extends Deck {

	ArrayList<Card> played;
	Rectangle playedLocation;
	
	ArrayList<Card> constructs;
	Rectangle constructLocation;
	
	public PlayerDeck() {
		this(new ArrayList<Card>(),new ArrayList<Card>(),new ArrayList<Card>(),new ArrayList<Card>(), 
				new ArrayList<Card>(), null, null, null);
	}

	public PlayerDeck(ArrayList<Card> notPlayed, Rectangle handLocation, Rectangle playedLocation, Rectangle constructLocation) {
		this(notPlayed,new ArrayList<Card>(),new ArrayList<Card>(),new ArrayList<Card>(), 
				new ArrayList<Card>(), handLocation, playedLocation, constructLocation);
	}
	
	public PlayerDeck(ArrayList<Card> notPlayed, ArrayList<Card> hand, ArrayList<Card> discard, 
			Rectangle handLocation, Rectangle playedLocation, Rectangle constructLocation) {
		this(notPlayed,hand,discard,new ArrayList<Card>(), new ArrayList<Card>(), 
				handLocation, playedLocation, constructLocation);
	}
	
	public PlayerDeck(ArrayList<Card> notPlayed, ArrayList<Card> hand, ArrayList<Card> discard, ArrayList<Card> played, 
			ArrayList<Card> constructs,Rectangle handLocation, Rectangle playedLocation, Rectangle constructLocation) {
		super(notPlayed, hand, discard, handLocation);
		this.played = played;
		this.playedLocation = playedLocation;
		this.constructs = constructs;
		this.constructLocation = constructLocation;
		resetHandLocation();
	}

	public Card playCard(Card c) {
		if(!super.hand.remove(c)) {
			throw new IllegalArgumentException("Cannot play a card that is not in your hand");
		}
		if(c.getType() == Card.Type.Construct) {
			constructs.add(c);
		} else if(c.getType() == Card.Type.Hero) {
			played.add(c);
		} else {
			throw new IllegalArgumentException("Cannot have a card that is not of type hero or construct");
		}
		
		return c;
	}
	
	public void resetHandLocation() {
		super.resetHandLocation();
		setCardListWithinLocation(played, playedLocation);
		setCardListWithinLocation(constructs, constructLocation);
	}
}
