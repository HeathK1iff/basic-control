package com.ytarzimanov.controls.basic.graphics;

import java.awt.Graphics2D;

import com.ytarzimanov.controls.basic.BasicComponent;
import com.ytarzimanov.controls.basic.painters.Painters;

public class GraphicObject{
	public enum GraphicObjectFocusedState {fsNone, fsFocused, fsSelected};

	protected int left, top, width, height = 0;
	protected BasicComponent component;
    protected Object obj;
    protected GraphicObjectFocusedState state = GraphicObjectFocusedState.fsNone;
	
	public GraphicObject(BasicComponent parent, Object obj){
		component = parent;
		this.obj = obj;
	}
	
	public GraphicObjectFocusedState getFocusedState(){
		if (this.equals(component.getSelected())){
		  return GraphicObjectFocusedState.fsSelected;
		}else{
		  return state;
		}
	}
	
	public void dispose(){
		obj = null;
	}
	
	public void draw(Graphics2D g2d, Painters Painters){

	}
	         
	public Object getObject(){
		return obj;
	}
	
    public int getWidth(){
    	return width;
    }
    
    public int getHeight(){
    	return height;
    }
}
    