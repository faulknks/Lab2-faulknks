import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;

import org.junit.Test;

public class CardTest {

	@Test
	public void testInitialization() {
		Card c= new Card();
		assertNotNull(c);
	}
	
	@Test
	public void testType() {
		Card c = new Card();
		c.setType(Card.Type.Construct);
		assertEquals(c.getType(), Card.Type.Construct);
		c.setType(Card.Type.Hero);
		assertEquals(c.getType(), Card.Type.Hero);
		c.setType(Card.Type.Monster);
		assertEquals(c.getType(), Card.Type.Monster);
	}
	
	@Test
	public void testFaction() {
		Card c = new Card();
		c.setFaction(Card.Faction.Enlightened);
		assertEquals(c.getFaction(), Card.Faction.Enlightened);
		c.setFaction(Card.Faction.Void);
		assertEquals(c.getFaction(), Card.Faction.Void);
		c.setFaction(Card.Faction.Lifebound);
		assertEquals(c.getFaction(), Card.Faction.Lifebound);
		c.setFaction(Card.Faction.Mechana);
		assertEquals(c.getFaction(), Card.Faction.Mechana);
	}
	
	@Test
	public void testLocation() {
		Rectangle r = new Rectangle(10, 10, 50, 50);
		Card c = new Card();
		c.setLocation(r);
		assertEquals(new Rectangle(10, 10, 50, 50), c.getLocation());
		c.setLocation(new Rectangle(15, 12, 30, 70));
		assertEquals(new Rectangle(15, 12, 30, 70), c.getLocation());
		c.setLocation(new Rectangle(1, 2, 3, 4));
		assertEquals(new Rectangle(1, 2, 3, 4), c.getLocation());
	}
	
	@Test
	public void testOnCard() {
		Card c = new Card();
		Rectangle r = new Rectangle(10, 10, 50, 50);
		Point p = new Point(30, 30);
		c.setLocation(r);
		assertTrue(c.onCard(p));
		p = new Point(120, 120);
		assertFalse(c.onCard(p));
	}
}
