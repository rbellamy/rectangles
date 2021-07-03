package com.pteradigm.nuvalence.rectangles;

import com.esri.core.geometry.*;
import org.jetbrains.annotations.NotNull;

public class Rectangle {
	private final Polygon polygon;

	/**
	 * Creates a {@link Rectangle} using four points for each vertex, moving clockwise from the first point.
	 *
	 * @param x1 x coordinate of the upper left corner
	 * @param y1 y coordinate of the upper left corner
	 * @param x2 x coordinate of the upper right corner
	 * @param y2 y coordinate of the upper right corner
	 * @param x3 x coordinate of the lower right corner
	 * @param y3 y coordinate of the lower right corner
	 * @param x4 x coordinate of the lower left corner
	 * @param y4 y coordinate of the lower left corner
	 */
	public Rectangle(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		Point2D a = new Point2D(x1, y1);
		Point2D b = new Point2D(x2, y2);
		Point2D c = new Point2D(x3, y3);
		Point2D d = new Point2D(x4, y4);
		// the diagonals must be of equal length
		// the points must not be collinear
		double c1 = Math.sqrt(Point2D.sqrDistance(a, c));
		double c2 = Math.sqrt(Point2D.sqrDistance(b, d));
		if (Math.abs(c1 - c2) <= 0.000001 &&
		    (Point2D.orientationRobust(a, b, c) != 0 &&
		     Point2D.orientationRobust(c, d, a) != 0)) {
			Polygon polygon = new Polygon();
			polygon.startPath(x1, y1);
			polygon.lineTo(x2, y2);
			polygon.lineTo(x3, y3);
			polygon.lineTo(x4, y4);
			this.polygon = polygon;
		} else {
			throw new IllegalArgumentException("The four points do not define a Rectangle.");
		}
	}

	public boolean intersects(@NotNull Rectangle rectangle) {
		return OperatorIntersects.local().execute(polygon, rectangle.polygon, null, null);
	}

	public boolean contains(@NotNull Rectangle rectangle) {
		return OperatorContains.local().execute(polygon, rectangle.polygon, null, null);
	}

	public Adjacent adjacent(@NotNull Rectangle rectangle) {
		// the two rectangles are touching
		if (OperatorTouches.local().execute(polygon, rectangle.polygon, null, null)) {
			// iterate through the four segments, which are the boundary of the rectangle, and test them against the four
			// segments of the polygon under evaluation
			final SegmentIterator iterator = polygon.querySegmentIterator();
			while (iterator.nextPath()) {
				while (iterator.hasNextSegment()) {
					Segment segment = iterator.nextSegment();
					final SegmentIterator otherIterator = rectangle.polygon.querySegmentIterator();
					while (otherIterator.nextPath()) {
						while (otherIterator.hasNextSegment()) {
							Segment otherSegment = otherIterator.nextSegment();
							if (OperatorEquals.local().execute(segment, otherSegment, null, null)) {
								return Adjacent.Proper;
							}
							if (OperatorContains.local().execute(segment, otherSegment, null, null)) {
								return Adjacent.SubLine;
							}
						}
					}
				}
			}
			// if the touch isn't Properly or SubLine Adjacent, it must be Partially Adjacent
			return Adjacent.Partial;
		}
		return Adjacent.Not;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Rectangle rectangle = (Rectangle) o;
		return OperatorEquals.local().execute(polygon, rectangle.polygon, null, null);
	}

	@Override
	public int hashCode() {
		return polygon.hashCode();
	}
}
