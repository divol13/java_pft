package ru.divol13.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance1(){
        Point p1 = new Point(5, 10);
        Point p2 = new Point(5, 10);

        double result = p1.distance(p2);
        Assert.assertEquals(result, 0.0);
    }

    @Test
    public void testDistance2(){
        Point p1 = new Point(10, 10);
        Point p2 = new Point(5, 10);

        double result = p1.distance(p2);
        Assert.assertEquals(result, 5.0);
    }

    @Test
    public void testDistance3(){
        Point p1 = new Point(20, 0);
        Point p2 = new Point(10, 0);

        double result = p1.distance(p2);
        Assert.assertEquals(result, 10.0);
    }
}
