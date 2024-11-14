package tangent;

/**
 *  Represents a straight line of the form: f(x) = ax + b
 */
final class Line {
    private double a;
    private double b;
    Line(double a, double b) {
        this.a = a;
        this.b = b;
    }
    public double solve(double x) {
        return a * x + b;
    }
    /**
     * Returns a coordinate of intersection lines if any or null
     * @param l any straight line
     * @return a value of intersection lines or null
     */
    public Double findPointOfIntersection(Line l) {
        if (this.a - l.a == 0) return null;
        return (l.b - this.b) / (this.a - l.a);
    }
    @Override
    public String toString() {
        return String.format("f(x) = %fx + %f", a, b);
    }
}