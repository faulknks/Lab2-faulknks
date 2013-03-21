
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;


public class Deck {
	///////////////Used to draw cards within Deck Location////////////
	public static final float cardDimensionRatio = 2; //height divided by width
	int yBorder = 4;  // Space between cards and top/bottom
	int xBorder = 4;  // Space between cards and edges
	/////////////////////////////////////////////////////////////////
	
	ArrayList<Card> notPlayed;
	ArrayList<Card> hand;
	ArrayList<Card> discard;
	Random generator = new Random();
	Rectangle handLocation;

	public Deck() {
		this(new ArrayList<Card>(), new ArrayList<Card>(), new ArrayList<Card>(), null);
	}
	
	public Deck(Rectangle location) {
		this(new ArrayList<Card>(), new ArrayList<Card>(), new ArrayList<Card>(), location);
	}

	public Deck(ArrayList<Card> notPlayed, Rectangle location) {
		this(notPlayed, new ArrayList<Card>(), new ArrayList<Card>(), location);
	}
	
	public Deck(ArrayList<Card> notPlayed, ArrayList<Card> hand, ArrayList<Card> discard, Rectangle location) {
		this.notPlayed = notPlayed;
		this.hand = hand;
		this.discard = discard;
		this.handLocation = location;
		
		resetHandLocation();
	}

	public boolean drawCard() {
		//if the not played deck is gone, replace with discard deck
		if(notPlayed.size() == 0) {
			//if discard deck is also gone, you cannot draw anymore
			if(discard.size() == 0) {
				return false;
			}
			notPlayed.addAll(discard);
			discard.clear();
			shuffle();
		}
		//remove from not played and add to hand
		Card c = notPlayed.remove(0);
		hand.add(c);
		resetHandLocation();
		return true;
	}
	
	public void shuffle() {
		//generate a random index and draw that card.  replaces shuffling
		ArrayList<Card> temp = new ArrayList<Card>();
		while(notPlayed.size() > 0) {
			int i = generator.nextInt(notPlayed.size());
			temp.add(notPlayed.remove(i));
		}
		notPlayed.addAll(temp);
	}
	
	public void addNewCardToDiscard(Card c) {
		discard.add(c);
	}
	
	public boolean drawNCards(int n) {
		for(int i = 0; i < n; i++) {
			if(!drawCard())
				return false;
		}
		return true;
	}
	
	public Card handleClick(Point p) {
		if(handLocation.contains(p)) {
			for(Card c : hand) {
				if(c.onCard(p)) {
					return playCard(c);
				}
			}
		}
		return null;
	}
	
	public Card playCard(Card c) {
		if(!hand.remove(c)) {
			throw new IllegalArgumentException();
		}
		drawCard();
		return c;
	}
	
	public void resetHandLocation() {
		nullOutCardLocation(notPlayed);
		nullOutCardLocation(discard);
		setCardListWithinLocation(hand, handLocation);
	}
	
	public void setCardListWithinLocation(ArrayList<Card> cardList, Rectangle location) {
		if(location == null)
			return;
		
		int size = cardList.size();
		int sf1 = (int) Math.round(location.height/cardDimensionRatio-2*yBorder);
		int sf2 = (int) Math.round((location.width - (size + 1) * xBorder)/(size + 0.0));
		int sf = Math.min(sf1, sf2);
		int x = xBorder + location.x;
		if(sf2 > sf1) {
			x += (location.width - ((size + 1) * xBorder + size * sf))/2;
		}
		for(Card c : cardList) {
			c.setLocation(new Rectangle(x, yBorder + location.y, sf, (int) (sf * cardDimensionRatio)));
			x += sf + xBorder;
		}
	}
	
	public void nullOutCardLocation(ArrayList<Card> cardList) {
		for(Card c : cardList) {
			c.setLocation(null);
		}
	}
	

}
