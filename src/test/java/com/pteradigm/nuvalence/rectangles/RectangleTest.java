package com.pteradigm.nuvalence.rectangles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * By convention, all rectangles in tests start at the lower-left corner, and go clockwise from there
 */
class RectangleTest {

	@Test
	void malformedConstruction() {
		// four points in a line
		Exception e1 = assertThrows(IllegalArgumentException.class,
		                            () -> new Rectangle(0, 0, 1, 1, 2, 2, 3, 3));
		assertEquals("The four points do not define a Rectangle.", e1.getMessage());

		// two points, repeated
		assertThrows(IllegalArgumentException.class,
		             () -> new Rectangle(0, 0, 0, 5, 0, 0, 0, 5));

		// trapezoid
		assertThrows(IllegalArgumentException.class,
		             () -> new Rectangle(0, 0, 1, 2, 3, 2, 2, 0));

	}

	/*
     ┌──────────────────┐
     │                  │
     │                  │  ┌─────────┐
     │        1         │  │         │
     │                  │  │    3    │
     │                  │  │         │
     │                  │  │         │
     │   ┌──────────┐   │  └─────────┘
     │   │          │   │
     │   │          │   │
     └───┼──────────┼───┘
         │          │
         │    2     │
         └──────────┘
	 */
	@Test
	void intersects() {
		Rectangle r1 = new Rectangle(0, 0, 0, 3, 3, 3, 3, 0);
		Rectangle r2 = new Rectangle(1, -1, 1, 1, 2, 1, 2, -1);
		Rectangle r3 = new Rectangle(4, 1, 4, 2, 5, 2, 5, 1);

		assertAll("intersects",
		          () -> assertTrue(r1.intersects(r2)),
		          () -> assertFalse(r2.intersects(r3)),
		          () -> assertFalse(r1.intersects(r3)));
	}

	/*
   ┌─────────────────────┐
   │            1        │
   │                     │
   │  ┌──────┐      ┌────┼───┐   ┌────────┐
   │  │      │      │    │   │   │        │
   │  │  2   │      │    │ 3 │   │   4    │
   │  │      │      │    │   │   │        │
   │  └──────┘      └────┼───┘   └────────┘
   │                     │
   │                     │
   └─────────────────────┘
	 */
	@Test
	void contains() {
		Rectangle r1 = new Rectangle(0, 0, 0, 5, 5, 5, 5, 0);
		Rectangle r2 = new Rectangle(1, 3, 1, 4, 2, 4, 2, 3);
		Rectangle r3 = new Rectangle(4, 1, 4, 3, 6, 3, 6, 1);
		Rectangle r4 = new Rectangle(7, 1, 7, 2, 8, 2, 8, 1);

		assertAll("contains",
		          () -> assertTrue(r1.contains(r2)),
		          () -> assertFalse(r1.contains(r3)),
		          () -> assertFalse(r1.contains(r4)));
	}

	/*
                   ┌────────┐     ┌──────┐
 ┌─────────┐       │        │     │      │
 │         │       │        │     │  4   │
 │         │       │   3    │     └──────┘
 │         │       │        │
 │         ├───────┴────────┴───────────┬────────────┐
 │    2    │                            │            │
 │         │                            │            │
 │         │                            │            │
 │         │                            │            │
 │         │             1              │     5      │
 └─────────┤                            │            │
           │                            │            │
           │                            │            │
           │                            │            │
           │                            │            │
           └────────────────────────────┴────────────┘
	 */
	@Test
	void adjacent() {
		Rectangle r1 = new Rectangle(0, 0, 0, 5, 5, 5, 5, 0);
		Rectangle r2 = new Rectangle(-2, 3, -2, 6, 0, 6, 0, 3);
		Rectangle r3 = new Rectangle(2, 5, 2, 7, 3, 7, 3, 5);
		Rectangle r4 = new Rectangle(4, 6, 4, 7, 5, 7, 5, 6);
		Rectangle r5 = new Rectangle(5, 0, 5, 5, 7, 5, 7, 0);

		assertAll("adjacent",
		          () -> assertEquals(r1.adjacent(r2), Adjacent.Partial),
		          () -> assertEquals(r1.adjacent(r3), Adjacent.SubLine),
		          () -> assertEquals(r1.adjacent(r4), Adjacent.Not),
		          () -> assertEquals(r1.adjacent(r5), Adjacent.Proper));
	}

	@Test
	void adjacent45() {
		// two rectangles at 45°
		Rectangle r1 = new Rectangle(0, 2, 2, 4, 4, 2, 2, 0);
		Rectangle r2 = new Rectangle(2, 0, 4, 2, 6, 0, 4, -2);

		assertEquals(r1.adjacent(r2), Adjacent.Proper);
	}
}
