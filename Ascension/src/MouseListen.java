import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseListen implements MouseListener{
	private Turn turn;
	
	MouseListen(Turn t) {
		this.turn = t;
	}

	public void mouseClicked(MouseEvent e) {
		
		Point location = e.getLocationOnScreen();
		
		if(e.getButton() == MouseEvent.BUTTON1){
			System.out.println("hello");	
			turn.player.playerDeck.handleClick(location);
			
			
		}else if(e.getButton() == MouseEvent.BUTTON3){
			System.out.println("hello friend");
			
			
		}else{
			//
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
