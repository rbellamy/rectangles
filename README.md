# Rectangles

## Rectangles and Java APIs

`Rectangle`s are `Polygon`s are `Shape`s. `Rectangle`s exist in the JDK under the `java.awt.*` namespace, and would seem
the immediate choice when designing an entity that addresses the needs of the requirements. However, the APIs in
the `java.awt.*`s namespace have a rudimentary understanding of Adjacency. For instance, the code to determine each type
of Adjacency would have to be custom-written for this exercise.

The [Esri Geometry API for Java][esri-geometry-api-java] is a spatial processing API designed for use-cases exactly like
the one outlined in the exercise. It includes all the machinery, out-of-the-box, needed to provide a complete solution
with minimal custom code. This implementation of a [`Rectangle`][rectangle-entity] entity wraps the functionality
provided by this API, and the tests verify the code meets the requirements.

## A Note about the Requirements

The requirements do not mention dealing with rectangles that are oriented off-axis, e.g., the segments of the rectangle
are neither parallel to, nor perpendicular to, the x or y-axis. This implementation will work with rectangles of
arbitrary orientation.

## Tests

```shell
./mvnw test
```

[rectangle-entity]: src/main/java/com/pteradigm/nuvalence/rectangles/Rectangle.java

[esri-geometry-api-java]: https://github.com/Esri/geometry-api-java
