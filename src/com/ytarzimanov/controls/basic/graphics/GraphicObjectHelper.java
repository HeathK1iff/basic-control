package com.ytarzimanov.controls.basic.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.ytarzimanov.controls.basic.BasicComponent;
import com.ytarzimanov.controls.basic.interfaces.OnClickListener;

public class GraphicObjectHelper extends GraphicObject{
	private enum MouseButtom {mbLeft, mbRight};
	private ArrayList<OnClickListener> onClickListeners = new ArrayList<OnClickListener>();   
	private MouseListener mouse_listener = new MouseListener(){
		@Override
		public void mouseClicked(MouseEvent e) {
			onMouseClicked(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			onMouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			onMouseExited(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			onMousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			onMouseReleased(e);
		}
	};
	
	private MouseMotionListener mouse_motion_listener = new MouseMotionListener(){

		@Override
		public void mouseDragged(MouseEvent e) {
			;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			onMouseMoved(e);
		}
	
	};
				
	public GraphicObjectHelper(BasicComponent parent, Object obj) {
		super(parent, obj);	
		component.addMouseListener(mouse_listener);
		component.addMouseMotionListener(mouse_motion_listener);
	}
	
	
	public void dispose(){
		super.dispose();
		component.removeMouseListener(mouse_listener);
		component.removeMouseMotionListener(mouse_motion_listener);
	}
	
	public void addOnClickListener(OnClickListener Listener){
		onClickListeners.add(Listener);
	}
	
	public void removeOnClickListener(OnClickListener Listener){
		onClickListeners.remove(Listener);
	}
	
	protected void NotifyOnClick(MouseButtom Button){
		int i = 0;
		while (i < onClickListeners.size()) {
			if (Button == MouseButtom.mbLeft){
			  onClickListeners.get(i).onClickMouseLeftButton(this);
			} else {
			  onClickListeners.get(i).onClickMouseRightButton(this);
			}
			i++;
		}
	}
	
	public Boolean PointBelongIt(GraphicObjectHelper AParentObj, int X, int Y){
		Boolean XAccept, YAccept = false; 
		int shiftLeft = 0;
		int shiftTop = 0;
		
		if (AParentObj != null){
			shiftLeft = AParentObj.getLeft();
			shiftTop = AParentObj.getTop();
		}	
		
		XAccept = ((X >= shiftLeft + getLeft()) && (X <= shiftLeft + getLeft()+getWidth()));
		YAccept = ((Y > shiftTop + getTop()) && (Y < shiftTop + getTop()+getHeight()));
		return ((XAccept && YAccept) == true); 
	}
	
    public int getLeft(){
    	return left;
    }
    
    public int getTop(){
    	return top;
    }
    
    public void setHeight(int Height){
    	this.height = Height;
    }
    
    public void setWidth(int Width){
    	this.width = Width;
    }

    public void setLeft(int Left){
    	this.left = Left;
    }
    
    public void setTop(int Top){
    	this.top = Top;
    }

	protected void onMouseMoved(MouseEvent e) {
		state = GraphicObjectFocusedState.fsNone;
		if (PointBelongIt(null, e.getX(), e.getY()) == true){
			if (state == GraphicObjectFocusedState.fsNone) {
				state = GraphicObjectFocusedState.fsFocused;
			};
		} 
		component.repaint();
	}    
	
	private void onMouseClicked(MouseEvent e) {
		if (PointBelongIt(null, e.getX(), e.getY()) == true){
			  if (SwingUtilities.isLeftMouseButton(e) == true){
			    if (component.getPopupMenu() != null){
				  component.getPopupMenu().setVisible(false);}
			    onMouseLeftClicked(e);}
			  else{
				  if (component.getPopupMenu() != null){
						Boolean isShowPopup = true;  
						if (component.getOnPopupMenuHandler() != null)
						  isShowPopup = component.getOnPopupMenuHandler().onPopupMenu(this);
						if (isShowPopup == true)
						{ component.getPopupMenu().show(component, e.getX(), e.getY());}
						else
						  component.getPopupMenu().setVisible(false);  
						}

				onMouseRightClicked(e);} 
		 }
	}
	
	
    protected void onMouseLeftClicked(MouseEvent e){
    	NotifyOnClick(MouseButtom.mbLeft);

	}
	
	protected void onMouseRightClicked(MouseEvent e){
		NotifyOnClick(MouseButtom.mbRight); 	
	}

	private void onMouseEntered(MouseEvent e) {
		;
	}

	private void onMouseExited(MouseEvent e) {
		state = GraphicObjectFocusedState.fsNone;
		component.repaint();
	}

	private void onMousePressed(MouseEvent e) {
		;
	}

	private void onMouseReleased(MouseEvent e) {
        ;
	}; 	
	
	
	protected int DrawWordWrapText(Graphics2D g2d, int Left, int Top, int Width, String Text, Boolean OnlyCalculate){
         return DrawWordWrapText(g2d, Left, Top, Width, Text, g2d.getFont(), OnlyCalculate);
	}

	protected int DrawWordWrapText(Graphics2D g2d, int Left, int Top, int Width, String Text){
		return DrawWordWrapText(g2d, Left, Top, Width, Text, g2d.getFont(), false);
	}
	
	protected int DrawWordWrapText(Graphics2D g2d, int Left, int Top, int Width, String Text, Font Dispalyfont){
		return DrawWordWrapText(g2d, Left, Top, Width, Text, Dispalyfont, false);
	}
	
	protected int DrawWordWrapText(Graphics2D g2d, int Left, int Top, int Width, String Text, Font Dispalyfont, Boolean OnlyCalculate){

		String[] lines =  Text.split(System.getProperty("line.separator"));
		float y = 0;
		for (int i = 0; i < lines.length; i++){
			AttributedString styledText = new AttributedString(lines[i]);
			styledText.addAttribute(TextAttribute.FONT, Dispalyfont);
			AttributedCharacterIterator m_iterator  = styledText.getIterator();
			int m_start = m_iterator.getBeginIndex();
			int m_end = m_iterator.getEndIndex();
        
			FontRenderContext frc = g2d.getFontRenderContext();

        
			LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
			measurer.setPosition(m_start);

			float x = 0;
			while (measurer.getPosition() < m_end)
			{
      	      TextLayout layout = measurer.nextLayout(Width);
            
      	      y += layout.getAscent();
      	      float dx = layout.isLeftToRight() ?
            		0 : Width - layout.getAdvance();
      	      
      	      if (OnlyCalculate == false)
      	    	  layout.draw(g2d, Left + x + dx, Top + y);
      	      y += layout.getDescent() + layout.getLeading();
			}
       
	  }
        return (int) (y + (Dispalyfont.getSize() / 2)); 
	}
}