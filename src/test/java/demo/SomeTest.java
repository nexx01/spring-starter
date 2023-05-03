package demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SomeTest {
    @Test
    void name() {
        assertEquals(0, 1 >> 1);
        assertEquals(0, 0 >> 1);
        assertEquals(1, 2 >> 1);
        assertEquals(1, 3 >> 1);
        assertEquals(2, 4 >> 1);
        assertEquals(2, 5 >> 1);
        assertEquals(3, 6 >> 1);
        assertEquals(3, 7 >> 1);
        assertEquals(4, 8 >> 1);
        assertEquals(49, 99 >> 1);
        assertEquals(50, 100 >> 1);
    }


    @Test
    void name1() {
        assertEquals(0,1>>>1);
        assertEquals(1,2>>>1);
        assertEquals(1,3>>>1);
        assertEquals(2,4>>>1);
        assertEquals(2,4>>>1);
        assertEquals(2,5>>>1);
        assertEquals(3,6>>>1);
        assertEquals(3,7>>>1);
        assertEquals(4,8>>>1);
        assertEquals(4,9>>>1);
        assertEquals(5,10>>>1);
    }

    @Test
    void name3() {
        assertEquals(0,1>>2);
        assertEquals(0,2>>2);
        assertEquals(0,3>>2);
        assertEquals(1,4>>2);
        assertEquals(1,5>>2);
        assertEquals(1,6>>2);
        assertEquals(1,7>>2);
        assertEquals(2,8>>2);
        assertEquals(24,99>>2);
        assertEquals(25,100>>2);
    }

    @Test
    void name4() {
        assertEquals(0,1&2);
        assertEquals(2,2&2);
        assertEquals(2,3&2);
        assertEquals(0,4&2);
        assertEquals(0,5&2);
        assertEquals(2,6&2);
        assertEquals(2,7&2);
        assertEquals(0,8&2);
        assertEquals(0,9&2);
        assertEquals(2,10&2);
//        assertEquals(3,3|2);
    }

    @Test
    void name5() {
        assertEquals(0,1&1);
        assertEquals(1,2&1);
        assertEquals(2,3&2);
        assertEquals(0,4&2);
        assertEquals(0,5&2);
        assertEquals(2,6&2);
        assertEquals(2,7&2);
        assertEquals(0,8&2);
        assertEquals(0,9&2);
        assertEquals(2,10&2);
//        assertEquals(3,3|2);
    }

    @Test
    public void reverseBits() {
        int n=100;
        System.out.println((byte)n);
//        if (n == 0) return 0;

        int res = 0;
        for (int i = 0; i < 32; i++) {
            res = res << 1;
            if ((n & 1) == 1) res++;
            n = n >> 1;
        }
//        return res;
        System.out.println(res);
    }

}



