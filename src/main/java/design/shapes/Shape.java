package design.shapes;

// Assumptions:
// two shapes are equals if their attribute values equal.
// two equal shapes share same hashCode.
// assume double is sufficient for accuracy
// x or y coordinate of any point within a shape is in range of [-sqrt(double.MAX_VALUE) , sqrt(double.MAX_VALUE)]

public abstract class Shape implements Cloneable, Detectable {
  String name;
  RigidBody body;

  final static double MAX_VALUE = Math.sqrt(Double.MAX_VALUE);
  final static double MIN_VALUE = -MAX_VALUE;

  public Shape() {
    this("", 0, 0, 0);
  }

  public Shape(String name, double x, double y, double rotation) {
    setName(name);
    body = new RigidBody(new Vector2D(x, y), rotation);

  }

  // copy constructor
  public Shape(Shape shape) {
    copy(shape);

  }

  // copy constructor helper
  void copy(Shape shape) {
    this.body = new RigidBody(shape.body);
    this.name = shape.name;
  }

  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o.getClass() != this.getClass())
      return false;

    Shape that = (Shape) o;
    return that.body.equals(this.body) && that.name.equals(this.name);

  }

  public int hashCode() {
    return this.body.hashCode() * 31 + this.name.hashCode();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Vector2D getPosition() {
    // read only
    return new Vector2D(body.position);
  }

  public double getRotation() {
    return this.body.rotation;
  }

  public void translate(double x, double y) {
    this.body.translate(new Vector2D(x, y));
  }

  public void rotate(double angdeg) {
    this.body.rotate(angdeg);
  }

  public String toString() {
    return String.format("'name':%s, 'position': %s, 'rotation': %f", getName(), getPosition().toString(),
        getRotation());
  }

}

interface Detectable {
  public boolean isPointInside(double x, double y);
}

// container store all physics related information
class RigidBody {
  public Vector2D position;
  // unit is degree
  public double rotation;
  final static double MAX_ROTATION = 360;

  public RigidBody(Vector2D position, double rotation) {
    this.position = new Vector2D(position);
    this.rotation = rotation % MAX_ROTATION;
  }

  public RigidBody(RigidBody body) {
    this(body.position, body.rotation);
  }

  public void translate(Vector2D v) {
    position.add(v);
  }

  public void rotate(double angle) {

    rotation = (rotation % MAX_ROTATION + angle % MAX_ROTATION) % MAX_ROTATION;
  }

  public double getRotation() {
    return rotation % MAX_ROTATION;
  }

  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o.getClass() != this.getClass())
      return false;

    RigidBody that = (RigidBody) o;
    return this.position.equals(that.position) && this.getRotation() == that.getRotation();
  }

  public int hashCode() {
    return (int) ((this.position.hashCode() * 31 + this.rotation) % (Integer.MAX_VALUE));
  }

}

class Vector2D implements Cloneable {
  public double x, y;
  final static double EPSILON = Float.MIN_VALUE;

  public Vector2D() {
    this(0, 0);
  }

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D(Vector2D v) {
    set(v);
  }

  public Object clone() {
    return new Vector2D(this);
  }

  public void set(Vector2D v) {
    this.x = v.x;
    this.y = v.y;
  }

  public double getDistSquare(Vector2D v) {
    double distX = (this.x - v.x);
    double distY = (this.y - v.y);
    return distX * distX + distY * distY;
  }

  public Vector2D add(Vector2D v) {
    this.x += v.x;
    this.y += v.y;
    return this;
  }

  public Vector2D sub(Vector2D v) {
    this.x -= v.x;
    this.y -= v.y;
    return this;
  }

  public Vector2D mul(Vector2D v) {
    this.x *= v.x;
    this.y *= v.y;
    return this;
  }

  public Vector2D mul(double f) {
    this.x *= f;
    this.y *= f;
    return this;
  }

  public Vector2D rotate(double angdeg) {
    double radian = Math.toRadians(angdeg);
    double cos = Math.cos(radian);
    double sin = Math.sin(radian);

    double xPrime = cos * x - sin * y;
    double yPrime = sin * x + cos * y;

    this.x = xPrime;
    this.y = yPrime;
    return this;

  }

  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o.getClass() != this.getClass())
      return false;

    Vector2D that = (Vector2D) o;
    return this.isCloseTo(that);

  }

  public static int floatCompare(double a, double b) {
    double epsilon = Math.max(Math.max(a, b), 1) * EPSILON;

    if (Math.abs(a - b) < epsilon)
      return 0;
    else
      return (int) Math.ceil(a - b);

  }

  public boolean isCloseTo(Vector2D v) {

    return floatCompare(this.x, v.x) == 0 && floatCompare(this.y, v.y) == 0;
  }

  public int hashCode() {
    return (int) ((this.x * 31 + this.y) % (Integer.MAX_VALUE));
  }

  public String toString() {
    return "[" + x + ":" + y + "]";
  }
}

class Rectangle extends Shape {
  Vector2D size;
  Vector2D halfSize;

  public Rectangle() {
    this(0, 0, 0, 0, 0);
  }

  public Rectangle(double x, double y, double angledegree, double width, double height) {
    super("Rectangle", x, y, angledegree);
    this.size = new Vector2D(width, height);
    this.halfSize = new Vector2D(size).mul(0.5);
    validate();
  }

  public Rectangle(Vector2D position, double angledegree, Vector2D size) {
    this(position.x, position.y, angledegree, size.x, size.y);
  }

  // copy constructor
  public Rectangle(Rectangle rectangle) {
    this(rectangle.getPosition(), rectangle.getRotation(), rectangle.getSize());

  }

  public Vector2D getSize() {
    return this.size;
  }

  public void setSize(Vector2D size) {
    this.size = new Vector2D(size);
    this.halfSize = new Vector2D(size).mul(0.5);
  }

  public Vector2D getHalfSize() {
    return this.halfSize;
  }

  // for copy assignment operator
  @Override
  public Object clone() {
    Rectangle clone = new Rectangle(this);
    return clone;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o.getClass() != this.getClass())
      return false;

    Rectangle that = (Rectangle) o;

    return (super.equals(that) && that.size.equals(this.size) && that.halfSize.equals(this.halfSize));

  }

  public int hashCode() {
    return (int) (super.hashCode() * 31 + this.size.hashCode());
  }

  public String toString() {
    return super.toString() + ", width: " + this.getWidth() + ", height: " + this.getHeight();
  }

  public double getWidth() {
    return this.size.x;
  }

  public double getHeight() {
    return this.size.y;
  }

  public Vector2D getCenter() {
    return this.getPosition();
  }

  public Vector2D getMin() {
    Vector2D center = this.getCenter();
    return center.sub(halfSize);
  }

  public Vector2D getMax() {
    Vector2D center = this.getCenter();
    return center.add(halfSize);
  }

  public void validate() {
    Vector2D min = getMin();
    Vector2D max = getMax();

    if (Math.min(min.x, min.y) < Shape.MIN_VALUE || Math.max(max.x, max.y) > Shape.MAX_VALUE) {
      throw new RuntimeException("Size exceeds limits");
    }
  }

  public boolean isPointInside(double x, double y) {

    return isPointInside(new Vector2D(x, y));
  }

  public boolean isPointInside(Vector2D v) {
    // if the rect is rotated, rotate the axises as well so the rect is axis aligned
    // calculate the new coord for v relative to the new axises.
    Vector2D relativeV = new Vector2D(v).rotate(-this.getRotation());

    return relativeV.x > getMin().x && relativeV.x < getMax().x && relativeV.y > getMin().y && relativeV.y < getMax().y;
  }

}

// demo on how to add a circle class
class Circle extends Shape {
  double radius;

  public Circle(double x, double y, double radius) {
    super("Circle", x, y, 0);
    this.radius = radius;
  }

  public Circle(Vector2D position, double radius) {
    this(position.x, position.y, radius);
  }

  public Circle(Circle circle) {
    this(circle.getPosition(), circle.radius);
  }

  public Vector2D getCenter() {
    return this.getPosition();
  }

  @Override
  public Object clone() {
    return new Circle(this);
  }

  public boolean isPointInside(double x, double y) {
    return this.getCenter().getDistSquare(new Vector2D(x, y)) < radius * radius;
  }

}

// to simplify the testing, I choose not to use Junit or other test framework
class UnitTest {
  // test utils
  public void assertTrue(boolean test) {
    if (!test) {
      throw new RuntimeException("Assertion Failed");
    }
  }

  public void assertFalse(boolean test) {
    assertTrue(!test);
  }

  public void assertEquals(Object a, Object b) {
    assertTrue(a.equals(b));
  }

  public void assertNotEquals(Object a, Object b) {
    assertTrue(!a.equals(b));
  }

  public void assertAlmostEquals(double a, double b, double epsilon) {
    assertTrue(Math.abs(a - b) < epsilon);
  }

  // test on Rectangle.equals
  public void equalityTest() {

    Rectangle rect1 = new Rectangle(1, 2, 3, 4, 5);
    Rectangle rect2 = new Rectangle(1, 2, 3, 4, 5);
    assertEquals(rect1, rect2);
    assertFalse(rect1 == rect2);

    // test on decimal numbers with neglible difference
    rect1 = new Rectangle(11222312.000000000009, 2, 3, 4, 5);
    rect2 = new Rectangle(11222312.000000000008, 2, 3, 4, 5);
    assertEquals(rect1, rect2);

    rect1 = new Rectangle(0.1, 0, 3, 3, 3);
    rect2 = new Rectangle(1.1, 0, 3, 3, 3);
    assertNotEquals(rect1, rect2);
    rect1 = new Rectangle(0, 0.1, 3, 3, 3);
    rect2 = new Rectangle(0, 0.2, 3, 3, 3);
    assertNotEquals(rect1, rect2);
    rect1 = new Rectangle(0, 0, 3.1, 3, 3);
    rect2 = new Rectangle(0, 0, 3.2, 3, 3);
    assertNotEquals(rect1, rect2);
    rect1 = new Rectangle(0, 0, 3, 3.3, 3);
    rect2 = new Rectangle(0, 0, 3, 3.5, 3);
    assertNotEquals(rect1, rect2);
    rect1 = new Rectangle(0, 0, 3, 3, 3.3);
    rect2 = new Rectangle(0, 0, 3, 3, 3.9);
    assertNotEquals(rect1, rect2);
    rect1 = new Rectangle(123.3, 23.3, 23.3, 3.2, 3.3);
    rect2 = new Rectangle(0, 0, 3, 3, 3.9);
    assertNotEquals(rect1, rect2);

    System.out.println("pass equalityTest");
  }

  // utils to generate random number
  public double getRandomNumber(double min, double max) {
    // System.out.println("minmax: " + min +':' + max);
    return ((Math.random() * (max - min)) + min);
  }

  final static double MAX_VALUE = Shape.MAX_VALUE / 10;
  final static double MIN_VALUE = Shape.MIN_VALUE / 10;
  final static double epsilon = Float.MIN_VALUE;

  // utils to create a randomly generated Rectangle
  public Rectangle getRandomRect() {

    double xcen = getRandomNumber(MIN_VALUE, MAX_VALUE);
    double ycen = getRandomNumber(MIN_VALUE, MAX_VALUE);
    double w = getRandomNumber(0, MAX_VALUE);
    double h = getRandomNumber(0, MAX_VALUE);
    double rotation = getRandomNumber(0, RigidBody.MAX_ROTATION);
    Rectangle rect = new Rectangle(xcen, ycen, rotation, w, h);
    return rect;
  }

  // utils to genreate a point within a given rect
  public Vector2D getRandomPointWithinRect(Rectangle rect) {
    double xcen = rect.getCenter().x;
    double ycen = rect.getCenter().y;
    double w = rect.getWidth();
    double h = rect.getHeight();
    double rotation = rect.getRotation();

    double x = getRandomNumber(xcen - (w / 2) + epsilon, xcen + (w / 2));
    double y = getRandomNumber(ycen - (h / 2) + epsilon, ycen + (h / 2));

    return new Vector2D(x, y).rotate(rotation);
  }

  public void copyContructorTest() {
    Rectangle rect = getRandomRect();
    Rectangle copy1 = new Rectangle(rect);
    Rectangle copy2 = new Rectangle(copy1);

    // obj identity check
    assertFalse(rect == copy1);
    assertFalse(rect == copy2);
    assertFalse(copy1 == copy2);

    // equality check
    assertEquals(rect, copy1);
    assertEquals(rect, copy2);
    assertEquals(copy1, copy2);
    // hashCode check
    assertEquals(rect.hashCode(), copy1.hashCode());
    assertEquals(rect.hashCode(), copy2.hashCode());
    assertEquals(copy2.hashCode(), copy1.hashCode());

    // modification check
    double xTranslate = 1.12;
    double yTranslate = 3.3123;
    double rotation = 233;
    rect.translate(xTranslate, yTranslate);
    rect.rotate(rotation);
    assertNotEquals(rect, copy1);
    assertNotEquals(rect, copy2);
    assertEquals(copy1, copy2);
    copy1.translate(xTranslate, yTranslate);
    copy1.rotate(rotation);
    assertEquals(rect, copy1);
    assertNotEquals(rect, copy2);
    assertNotEquals(copy1, copy2);
    copy2.translate(xTranslate, yTranslate);
    copy2.rotate(rotation);
    assertEquals(rect, copy1);
    assertEquals(rect, copy2);
    assertEquals(copy1, copy2);

    System.out.println("pass copyContructorTest");

  }

  // test on Rectangle.clone
  public void assignmentOperatorTest() {

    Rectangle rect = getRandomRect();
    Rectangle assigned = (Rectangle) rect.clone();

    // obj identity check
    assertFalse(rect == assigned);

    // equality check
    assertEquals(rect, assigned);

    // modification check
    double xTranslate = 112.12;
    double yTranslate = 333.3123;
    double rotation = 45.323;
    rect.translate(xTranslate, yTranslate);
    rect.rotate(rotation);
    assertNotEquals(rect, assigned);
    assigned.translate(xTranslate, yTranslate);
    assigned.rotate(rotation);
    assertEquals(rect, assigned);

    System.out.println("pass assignmentOperatorTest");

  }

  public void pointInsideTest() {

    // axis-aligned rect
    Rectangle rect1 = new Rectangle(0, 0, 0, 3, 4);
    assertTrue(rect1.isPointInside(1.1, 1.999));
    assertTrue(rect1.isPointInside(0, 0));
    assertTrue(rect1.isPointInside(-1.1, 1.999));
    assertTrue(rect1.isPointInside(1.1, -1.999));
    assertTrue(rect1.isPointInside(-0.5, -0.5));

    assertFalse(rect1.isPointInside(1.1, 2));
    assertFalse(rect1.isPointInside(1.1, -2.3));
    assertFalse(rect1.isPointInside(1.1, 2.3));
    assertFalse(rect1.isPointInside(10, 1));
    assertFalse(rect1.isPointInside(-10, 1));

    // rotate 180 degrees
    rect1.rotate(180);
    assertTrue(rect1.isPointInside(1.1, 1.999));
    assertTrue(rect1.isPointInside(0, 0));
    assertTrue(rect1.isPointInside(-1.1, 1.999));
    assertTrue(rect1.isPointInside(1.1, -1.999));
    assertTrue(rect1.isPointInside(-0.5, -0.5));

    assertFalse(rect1.isPointInside(1.1, 2));
    assertFalse(rect1.isPointInside(1.1, -2.3));
    assertFalse(rect1.isPointInside(1.1, 2.3));
    assertFalse(rect1.isPointInside(10, 1));
    assertFalse(rect1.isPointInside(-10, 1));

    // rotate 360*100 degrees
    rect1.rotate(-180);
    rect1.rotate(360 * 100);
    assertTrue(rect1.isPointInside(1.1, 1.999));
    assertTrue(rect1.isPointInside(0, 0));
    assertTrue(rect1.isPointInside(-1.1, 1.999));
    assertTrue(rect1.isPointInside(1.1, -1.999));
    assertTrue(rect1.isPointInside(-0.5, -0.5));

    assertFalse(rect1.isPointInside(1.1, 2));
    assertFalse(rect1.isPointInside(1.1, -2.3));
    assertFalse(rect1.isPointInside(1.1, 2.3));
    assertFalse(rect1.isPointInside(10, 1));
    assertFalse(rect1.isPointInside(-10, 1));

    // rotate 90 degree
    rect1 = new Rectangle(0, 0, 90, 3, 4);
    assertTrue(rect1.isPointInside(1.999, 1.1));
    assertTrue(rect1.isPointInside(0, 0));
    assertTrue(rect1.isPointInside(1.999, -1.1));
    assertTrue(rect1.isPointInside(-1.999, 1.1));
    assertTrue(rect1.isPointInside(-0.5, -0.5));

    assertFalse(rect1.isPointInside(2, 1.1));
    assertFalse(rect1.isPointInside(-2.3, 1.1));
    assertFalse(rect1.isPointInside(2.3, 1.1));
    assertFalse(rect1.isPointInside(1, 10));
    assertFalse(rect1.isPointInside(1, -10));

    // randomly generated test case
    for (int i = 0; i < 1000; i++) {
      Rectangle rect = getRandomRect();
      Vector2D p = getRandomPointWithinRect(rect);
      assertTrue(rect.isPointInside(p));
    }

    System.out.println("pass pointInsideTest");
  }

  public static void main(String[] args) {
    UnitTest testSuit = new UnitTest();
    testSuit.equalityTest();
    testSuit.assignmentOperatorTest();
    testSuit.copyContructorTest();
    testSuit.pointInsideTest();

  }
}