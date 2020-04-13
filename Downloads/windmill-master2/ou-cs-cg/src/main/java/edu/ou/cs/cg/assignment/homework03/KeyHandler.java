//******************************************************************************
// Copyright (C) 2016-2019 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Wed Feb 27 17:33:04 2019 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160225 [weaver]:	Original file.
// 20190227 [weaver]:	Updated to use model and asynchronous event handling.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.cg.assignment.homework03;

//import java.lang.*;
import java.awt.Component;
import java.awt.event.*;
import java.awt.geom.Point2D;
import edu.ou.cs.cg.utilities.Utilities;

//******************************************************************************

/**
 * The <CODE>KeyHandler</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class KeyHandler extends KeyAdapter
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View		view;
	private final Model	model;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public KeyHandler(View view, Model model)
	{
		this.view = view;
		this.model = model;

		Component	component = view.getCanvas();

		component.addKeyListener(this);
	}

	//**********************************************************************
	// Override Methods (KeyListener)
	//**********************************************************************

	public void		keyPressed(KeyEvent e)
	{
		Point2D.Double	p = model.getOrigin();
		double			a = (Utilities.isShiftDown(e) ? 0.01 : 0.1);

		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				model.moveHorizontal(0);
				p.x -= a;		p.y += 0.0;	break;

			case KeyEvent.VK_RIGHT:
				model.moveHorizontal(1);
				p.x += a;		p.y += 0.0;	break;
				
			case KeyEvent.VK_DOWN:
				model.moveVertical(0);
				p.x += 0.0;	p.y -= a;		break;

			case KeyEvent.VK_UP:
				model.moveVertical(1);
				p.x += 0.0;	p.y += a;		break;
				
			case KeyEvent.VK_Q:
				model.removeStar(true);
				return;
				
			case KeyEvent.VK_W:
				model.removeStar(false);
				return;
				
			case KeyEvent.VK_NUMPAD5:
			case KeyEvent.VK_5:
				model.star(5);
				return;
				
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_6:
				model.star(6);
				return;
				
			case KeyEvent.VK_NUMPAD7:
			case KeyEvent.VK_7:
				model.star(7);
				return;
				
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_8:
				model.star(8);
				return;
			
			case KeyEvent.VK_NUMPAD9:
			case KeyEvent.VK_9:
				model.star(9);
				return;
				
			case KeyEvent.VK_S:
				model.cycleSky();
				return;

			case KeyEvent.VK_CLEAR:
			case KeyEvent.VK_D:
			case KeyEvent.VK_DELETE:
				model.clearPolyline();
				return;
		}

		model.setOriginInSceneCoordinates(p);
	}
}

//******************************************************************************
