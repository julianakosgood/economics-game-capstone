//******************************************************************************
// Copyright (C) 2019 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Wed Feb 27 17:32:08 2019 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20190227 [weaver]:	Original file.
//
//******************************************************************************
//
// The model manages all of the user-adjustable variables utilized in the scene.
// (You can store non-user-adjustable scene data here too, if you want.)
//
// For each variable that you want to make interactive:
//
//   1. Add a member of the right type
//   2. Initialize it to a reasonable default value in the constructor.
//   3. Add a method to access a copy of the variable's current value.
//   4. Add a method to modify the variable.
//
// Concurrency management is important because the JOGL and the Java AWT run on
// different threads. The modify methods use the GLAutoDrawable.invoke() method
// so that all changes to variables take place on the JOGL thread. Because this
// happens at the END of GLEventListener.display(), all changes will be visible
// to the View.update() and render() methods in the next animation cycle.
//
//******************************************************************************

package edu.ou.cs.cg.assignment.homework03;

//import java.lang.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.*;
import com.jogamp.opengl.*;

import edu.ou.cs.cg.utilities.Utilities;

//******************************************************************************

/**
 * The <CODE>Model</CODE> class.
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Model
{
	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private final View					view;

	// Model variables
	private Point2D.Double				origin;	// Current origin coordinates
	private Point2D.Double				cursor;	// Current cursor coordinates
	private ArrayList<Point2D.Double>	points;	// Drawn polyline points
	
	// TODO: Windmill Blade speed.
	private int 						windmillSpeed;
	
	private int 						sky = 0;
	private int 						horizontal = 0;
	private int 						vertical = 0;
	private int 						star = 5;
	
	private ArrayList<Point2D.Double> 	drawStar = new ArrayList<>();
	private ArrayList<Point2D.Double> 	fencePoints = new ArrayList<>();
	
	private Point2D.Double 				ball;
	private Point2D.Double 				window;

	//**********************************************************************
	// Constructors and Finalizer
	//**********************************************************************

	public Model(View view)
	{
		this.view = view;

		// Initialize user-adjustable variables (with reasonable default values)
		origin = new Point2D.Double(0.0, 0.0);
		cursor = null;
		points = new ArrayList<Point2D.Double>();
	}

	//**********************************************************************
	// Public Methods (Access Variables)
	//**********************************************************************

	public Point2D.Double	getOrigin()
	{
		return new Point2D.Double(origin.x, origin.y);
	}

	public Point2D.Double	getCursor()
	{
		if (cursor == null)
			return null;
		else
			return new Point2D.Double(cursor.x, cursor.y);
	}

	public List<Point2D.Double>	getPolyline()
	{
		return Collections.unmodifiableList(points);
	}
	
	public int 	getWindmillSpeed() { return windmillSpeed; }
	
	public int	getSky() { return sky; }
	
	public int	getHorizontal() { return horizontal; }
	
	public int	getVertical() { return vertical; }
	
	public ArrayList<Point2D.Double>	getFencePoint() { return fencePoints; }
	
	public int	getStar() { return star; }
	
	public ArrayList<Point2D.Double>	getDrawStar() { return drawStar; }
	
	public Point2D.Double	getBall() { return ball; }
	
	public Point2D.Double	getWindow() { return window; }

	//**********************************************************************
	// Public Methods (Modify Variables)
	//**********************************************************************

	public void	setOriginInSceneCoordinates(Point2D.Double q)
	{
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				origin = new Point2D.Double(q.x, q.y);
			}
		});;
	}

	public void	setOriginInViewCoordinates(Point q)
	{
		view.getCanvas().invoke(false, new ViewPointUpdater(q) {
			public void	update(double[] p) {
				origin = new Point2D.Double(p[0], p[1]);
			}
		});;
	}

	public void	setCursorInViewCoordinates(Point q)
	{
		view.getCanvas().invoke(false, new ViewPointUpdater(q) {
			public void	update(double[] p) {
				cursor = new Point2D.Double(p[0], p[1]);
			}
		});;
	}

	public void	turnCursorOff()
	{
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				cursor = null;
			}
		});;
	}

	public void	addPolylinePointInViewCoordinates(Point q)
	{
		view.getCanvas().invoke(false, new ViewPointUpdater(q) {
			public void	update(double[] p) {
				points.add(new Point2D.Double(p[0], p[1]));
			}
		});;
	}		// TODO: Set the scene.

	public void	clearPolyline()
	{
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				points.clear();
			}
		});;
	}
	
	public void flipFence(Point point) {
		view.getCanvas().invoke(false, new ViewPointUpdater(point) {
			public void	update(double[] p) {
				fencePoints.add(new Point2D.Double(p[0], p[1]));
			}
		});;
	}
	
	public void	cycleSky()
	{
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				if (sky == 3) {
					sky = 0;
				}
				else {
					sky++;
				}
			}
		});;
	}
	
	public void moveHorizontal(int direction) {
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				if (direction == 0) horizontal--;
				else horizontal++;
			}
		});;
	}

	public void moveVertical(int direction) {
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				if (direction == 0) vertical--;
				else vertical++;
			}
		});;
	}
	
	public void star(int num) {
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void	update(GL2 gl) {
				star = num;
			}
		});;
	}
	
	public void drawStar(Point point) {
		view.getCanvas().invoke(false, new ViewPointUpdater(point) {
			public void	update(double[] p) {
				drawStar.add(new Point2D.Double(p[0], p[1]));
			}
		});;
	}
	
	public void removeStar(boolean old) {
		if (drawStar.isEmpty()) return;
		if (old) drawStar.remove(0);
		else drawStar.remove(drawStar.size() - 1);
	}
	
	public void bounceBall(Point point) {
		view.getCanvas().invoke(false, new ViewPointUpdater(point) {
			public void	update(double[] p) {
				ball = new Point2D.Double(p[0], p[1]);
			}
		});;
	}
	
	public void moveWindow(Point point) {
		view.getCanvas().invoke(false, new ViewPointUpdater(point) {
			public void	update(double[] p) {
				window = new Point2D.Double(p[0], p[1]);
			}
		});;
	}
	
	// TODO: Test windmill Speed.
	public void setWindmillSpeed(int speed) {
		view.getCanvas().invoke(false, new BasicUpdater() {
			public void update(GL2 gl) {
				windmillSpeed = speed;
			}
		});;
	}
	
	
	

	//**********************************************************************
	// Inner Classes
	//**********************************************************************

	// Convenience class to simplify the implementation of most updater.
	private abstract class BasicUpdater implements GLRunnable
	{
		public final boolean	run(GLAutoDrawable drawable)
		{
			GL2	gl = drawable.getGL().getGL2();

			update(gl);

			return true;	// Let animator take care of updating the display
		}

		public abstract void	update(GL2 gl);
	}

	// Convenience class to simplify updates in cases in which the input is a
	// single point in view coordinates (integers/pixels).
	private abstract class ViewPointUpdater extends BasicUpdater
	{
		private final Point	q;

		public ViewPointUpdater(Point q)
		{
			this.q = q;
		}

		public final void	update(GL2 gl)
		{
			int		h = view.getHeight();
			double[]	p = Utilities.mapViewToScene(gl, q.x, h - q.y, 0.0);

			update(p);
		}

		public abstract void	update(double[] p);
	}
}

//******************************************************************************
