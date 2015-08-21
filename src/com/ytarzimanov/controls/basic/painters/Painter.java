package com.ytarzimanov.controls.basic.painters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.ytarzimanov.controls.basic.graphics.GraphicObject;
import com.ytarzimanov.controls.basic.graphics.GraphicObject.GraphicObjectFocusedState;



abstract public class Painter {
	private int width, height = 0;
	private BufferedImage buffer;
	private GraphicObjectFocusedState PreviusState = GraphicObjectFocusedState.fsNone;
	
	public AlphaComposite makeTransparent(float alpha) {
		  int type = AlphaComposite.SRC_OVER;
		  return(AlphaComposite.getInstance(type, alpha));
	 }
	
	protected Boolean ValidateOnChange(GraphicObject AGraphicObj){
		Boolean IsChangeSize = (width != AGraphicObj.getWidth()) || (height != AGraphicObj.getHeight());
		
		if (IsChangeSize == true)  {
			width = AGraphicObj.getWidth();
			height = AGraphicObj.getHeight();
		}
		
		Boolean Result = IsChangeSize || (PreviusState != AGraphicObj.getFocusedState());
		PreviusState = AGraphicObj.getFocusedState();
		return Result;
	}
	
	private void buildBuffer(Graphics2D graphic, GraphicObject obj){
		switch (obj.getFocusedState()){
		  case fsFocused:
			drawFocused(graphic, obj);
		  break;
		  case fsSelected:
			drawSelected(graphic, obj);  
		  break;
		  default:
			draw(graphic, obj);
		  break; 
		};
	}
	
	abstract protected void drawSelected(Graphics2D Graphic, GraphicObject AGraphicObj);
	abstract protected void drawFocused(Graphics2D Graphic, GraphicObject AGraphicObj);	
	abstract protected void draw(Graphics2D Graphic, GraphicObject AGraphicObj);
	
	
	protected void rebuildBuffer(GraphicObject AGraphicObj){
		buffer = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D Graphic = buffer.createGraphics();
		buildBuffer(Graphic, AGraphicObj);
	}
	
	public BufferedImage drawObj(GraphicObject AGraphicObj)
	{
		if ((ValidateOnChange(AGraphicObj) == true) || (buffer == null)) {
		  rebuildBuffer(AGraphicObj);
		};
		return buffer;
	}
}