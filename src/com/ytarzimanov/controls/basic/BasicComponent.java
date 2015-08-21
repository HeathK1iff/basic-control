package com.ytarzimanov.controls.basic;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;

import com.ytarzimanov.controls.basic.graphics.GraphicObject;
import com.ytarzimanov.controls.basic.interfaces.OnPopupMenu;


public class BasicComponent extends JComponent{
	private static final long serialVersionUID = 1L;
    private JPopupMenu menu = null;
    private OnPopupMenu onPopupMenuHandler = null;
    private GraphicObject selected = null;
    
	public BasicComponent() {

	}
	
	public GraphicObject getSelected(){
		return selected;
	}
	
	public void setSelected(GraphicObject obj){
		this.selected = obj;
	}
	
	public void setOnPopupMenuHandler(OnPopupMenu onPopupMenu){
		this.onPopupMenuHandler = onPopupMenu;
	}
	
	public OnPopupMenu getOnPopupMenuHandler(){
		return onPopupMenuHandler;
	}
	
	public void setPopupMenu(JPopupMenu menu){
		this.menu = menu;
	}
	
	public JPopupMenu getPopupMenu(){
		return menu;
	}

	protected void setComponentHeight(int Height){
		if (this.getParent().getClass().equals(JViewport.class)){		
			JViewport viewport = (JViewport) this.getParent();
			this.setPreferredSize(new Dimension((int) (viewport.getViewRect().getWidth() - 15), Height));
			this.revalidate();	
		}
	}
	
	
}
