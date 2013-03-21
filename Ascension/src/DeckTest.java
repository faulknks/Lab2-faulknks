import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {

	Rectangle handLocation;
	ArrayList<Card> notPlayedOrigional;
	ArrayList<Card> handOrigional;
	ArrayList<Card> discardOrigional;
	Deck d;

	@Before
	public void makeDeck() {
		ArrayList<Card> notPlayed = randomCardList();
		notPlayedOrigional = new ArrayList<Card>();
		notPlayedOrigional.addAll(notPlayed);

		ArrayList<Card> hand = randomCardList();
		handOrigional = new ArrayList<Card>();
		handOrigional.addAll(hand);

		ArrayList<Card> discard = randomCardList();
		discardOrigional = new ArrayList<Card>();
		discardOrigional.addAll(discard);

		handLocation = new Rectangle(0, 500, 1000, 300);
		d = new Deck(notPlayed, hand, discard, handLocation);
	}

	@Test
	public void testInitialization() {
		Deck d = new Deck();
		assertNotNull(d);
	}

	@Test
	public void testDeaultConstructor() {
		Deck d = new Deck();
		assertEquals(new ArrayList<Card>(), d.notPlayed);
		assertEquals(new ArrayList<Card>(), d.hand);
		assertEquals(new ArrayList<Card>(), d.discard);
	}

	@Test
	public void testConstructorWLocation() {
		Deck d = new Deck(handLocation);
		assertEquals(new ArrayList<Card>(), d.notPlayed);
		assertEquals(new ArrayList<Card>(), d.hand);
		assertEquals(new ArrayList<Card>(), d.discard);
		assertEquals(handLocation, d.handLocation);
	}

	@Test
	public void testConstructorWNotPlayed() {
		ArrayList<Card> notPlayed = randomCardList();
		Deck d = new Deck(notPlayed, handLocation);
		assertEquals(notPlayed, d.notPlayed);
		assertEquals(new ArrayList<Card>(), d.hand);
		assertEquals(new ArrayList<Card>(), d.discard);
	}

	@Test
	public void testConstructorWAll() {
		ArrayList<Card> notPlayed = randomCardList();
		ArrayList<Card> hand = randomCardList();
		ArrayList<Card> discard = randomCardList();
		Deck d = new Deck(notPlayed, hand, discard, handLocation);
		assertEquals(notPlayed, d.notPlayed);
		assertEquals(hand, d.hand);
		assertEquals(discard, d.discard);
	}

	@Test
	public void testDrawCardWEmpty() {
		Deck d = new Deck();
		assertFalse(d.drawCard());
	}

	@Test
	public void testDrawCardWNormal() {
		assertTrue(d.drawCard());

		assertTrue(notPlayedOrigional.containsAll(d.notPlayed));
		assertFalse(d.notPlayed.containsAll(notPlayedOrigional));

		assertTrue(d.hand.containsAll(handOrigional));
		assertFalse(handOrigional.containsAll(d.hand));

		assertEquals(notPlayedOrigional.size() - 1, d.notPlayed.size());
		assertEquals(handOrigional.size() + 1, d.hand.size());

		notPlayedOrigional.removeAll(d.notPlayed);
		d.hand.removeAll(handOrigional);
		assertEquals(d.hand.get(0), notPlayedOrigional.get(0));
	}

	@Test
	public void testShuffle() {
		d.notPlayed = new ArrayList<Card>();
		d.notPlayed.add(new Card());
		d.notPlayed.add(new Card());
		d.notPlayed.add(new Card());
		d.notPlayed.add(new Card());
		d.notPlayed.add(new Card());
		d.notPlayed.add(new Card());
		notPlayedOrigional = new ArrayList<Card>();
		notPlayedOrigional.addAll(d.notPlayed);
		d.generator = new Random(12345432);
		d.shuffle();
		assertTrue(notPlayedOrigional.containsAll(d.notPlayed));
		assertTrue(d.notPlayed.containsAll(notPlayedOrigional));
		boolean pass = false;
		for (int i = 0; i < d.notPlayed.size(); i++) {
			pass |= (d.notPlayed.get(i) != notPlayedOrigional.get(i));
		}
		assertTrue(pass);
	}

	@Test
	public void testAddNewCardToDiscard() {

		Card c = new Card();
		d.addNewCardToDiscard(c);
		assertTrue(d.discard.containsAll(discardOrigional));
		assertFalse(discardOrigional.containsAll(d.discard));
		d.discard.removeAll(discardOrigional);
		assertEquals(c, d.discard.get(0));
	}

	@Test
	public void testHandleClickFirst() {
		Card c;
		assertEquals(
				(c = handOrigional.get(0)),
				d.handleClick(new Point(handLocation.x + handLocation.width
						/ d.hand.size() / 2 - 10, handLocation.y + d.handLocation.height / 4)));
		// check that it drew something
		assertFalse(d.hand.containsAll(handOrigional));

		// check that it got rid of the old card
		assertFalse(d.hand.contains(c));
	}

	@Test
	public void testHandleClickLast() {
		Card c;
		assertEquals((c = handOrigional.get(handOrigional.size() - 1)),
				d.handleClick(new Point(handLocation.x + handLocation.width
						- handLocation.width / d.hand.size() / 2 + 10,
						handLocation.y + d.handLocation.height / 4)));

		// check that it drew something
		assertFalse(d.hand.containsAll(handOrigional));

		// check that it got rid of the old card
		assertFalse(d.hand.contains(c));
	}

	@Test
	public void testHandleClickNothing() {
		Card c;
		assertEquals(null,
				d.handleClick(new Point(handLocation.x, handLocation.y)));

		assertTrue(handOrigional.containsAll(d.hand));
		assertTrue(d.hand.containsAll(handOrigional));

	}

	@Test
	public void testHandleClickOutside() {
		Card c;
		assertEquals(null, d.handleClick(new Point(0, 0)));

		assertTrue(handOrigional.containsAll(d.hand));
		assertTrue(d.hand.containsAll(handOrigional));

	}

	public static ArrayList<Card> randomCardList() {
		ArrayList<Card> ret = new ArrayList<Card>();
		Random generator = new Random();
		int c = 10;
		for (int i = 0; i < c; i++) {
			int t = generator.nextInt(1);
			if (t == 0)
				ret.add(new Card(Card.Type.Construct, Card.Faction.Enlightened));
			else
				ret.add(new Card(Card.Type.Hero, Card.Faction.Enlightened));
		}
		return ret;
	}

}
