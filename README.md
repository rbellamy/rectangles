# Rectangles

Like most software developers, I try to avoid the "Not Invented Here" syndrome. In my experience, it's rarely as simple
as reading about it in a "Build or Buy" best practices article.

For instance, this exercise. In taking the path I did, I choose to showcase my ability to integrate a new (for me)
library into existing requirements, rather than attempting to meet those requirements from first principles. In doing so
I risk missing the point of the exercise by NOT implementing the mathematical details of the geometry involved
in `intersects`, `contains`, or `adjacent`. On the other hand, by taking that risk, I offer you a better view into my
real-world ability to onboard a problem, dissect it, and render a working solution that CAN scale, and wasn't a heavy
lift to get to a working prototype.

## Requirements

The requirements do not mention dealing with rectangles that are oriented off-axis, e.g., the segments of the rectangle
are neither parallel to, nor perpendicular to, the x or y-axis. This implementation will work with rectangles of
arbitrary orientation.

The [`Rectangle`][rectangle-entity] entity wraps the functionality provided by
the [Esri Geometry API for Java][esri-geometry-api-java], which is released under an Apache 2.0 license.

## Tests

```shell
./mvnw test
```

[rectangle-entity]: src/main/java/com/pteradigm/nuvalence/rectangles/Rectangle.java

[esri-geometry-api-java]: https://github.com/Esri/geometry-api-java
