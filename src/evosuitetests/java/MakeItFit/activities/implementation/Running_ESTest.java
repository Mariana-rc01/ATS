/*
 * This file was automatically generated by EvoSuite
 * Wed Jun 04 20:08:47 GMT 2025
 */

package MakeItFit.activities.implementation;

import java.util.UUID;

import MakeItFit.activities.implementation.Running;
import MakeItFit.utils.MakeItFitDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockUUID;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true,
                     useVFS                = true,
                     useVNET               = true,
                     resetStaticState      = true,
                     useJEE                = true)
public class Running_ESTest extends Running_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0,
                                       (MakeItFitDate) null,
                                       (-2034),
                                       "7xE4rLd}Kk8{=ly",
                                       "7xE4rLd}Kk8{=ly",
                                       (-2034),
                                       (-2034));
        Running running1 = new Running(uUID0,
                                       (MakeItFitDate) null,
                                       (-2034),
                                       "7xE4rLd}Kk8{=ly",
                                       "7xE4rLd}Kk8{=ly",
                                       (-2034),
                                       (-42_074_876));
        boolean boolean0 = running0.equals(running1);
        assertFalse(running1.equals((Object) running0));
        assertFalse(boolean0);
        assertEquals((-4.2074876E7), running1.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0       = new Running(uUID0,
                                       makeItFitDate0,
                                       2,
                                       (String) null,
                                       "Speed: ",
                                       (-292.854423),
                                       (-292.854423));
        running0.setSpeed(0.0);
        double double0 = running0.getSpeed();
        assertEquals(0.0, double0, 0.01);
    }

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        UUID          uUID0          = MockUUID.fromString("");
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0       = new Running(uUID0,
                                       makeItFitDate0,
                                       (-1820),
                                       "MakeItFit.activities.types.Distance",
                                       "",
                                       (-126.30769484),
                                       (-1820));
        double        double0        = running0.getSpeed();
        assertEquals((-1820.0), double0, 0.01);
    }

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0       = new Running(uUID0,
                                       makeItFitDate0,
                                       0,
                                       "e$r;'}2,Z<8YEX#OF!",
                                       " meters, Elevation Loss: ",
                                       0,
                                       0.0);
        running0.clone();
        assertEquals(0.0, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        UUID    uUID0 = MockUUID.randomUUID();
        Running running0 =
            new Running(uUID0, (MakeItFitDate) null, (-3914), "B", "B3-jV", (-604.69), 0.0);
        running0.setDuration(1);
        running0.clone();
        assertEquals(0.0, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0,
                                       (MakeItFitDate) null,
                                       (-2034),
                                       "7xE4rLd}Kk8{=ly",
                                       "7xE4rLd}Kk8{=ly",
                                       (-2034),
                                       837.69947212829);
        running0.setDuration((-2034));
        Running running1 = running0.clone();
        assertEquals(837.69947212829, running1.getSpeed(), 0.01);
        assertEquals(837.69947212829, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0       = new Running(uUID0,
                                       makeItFitDate0,
                                       2,
                                       (String) null,
                                       "Speed: ",
                                       (-292.854423),
                                       (-292.854423));
        running0.setDistance(0.0);
        Running running1 = running0.clone();
        assertEquals((-292.854423), running0.getSpeed(), 0.01);
        assertEquals((-292.854423), running1.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0       = new Running(uUID0,
                                       makeItFitDate0,
                                       2,
                                       (String) null,
                                       "Speed: ",
                                       (-292.854423),
                                       (-292.854423));
        running0.setSpeed(775.0F);
        running0.calculateCaloricWaste((-659));
        running0.clone();
        assertEquals(775.0, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        UUID          uUID0          = MockUUID.fromString("");
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running running0 = new Running(uUID0, makeItFitDate0, 1073, "", "", 1073, 2765.410931544);
        running0.setDistance((-1416.54184271848));
        running0.calculateCaloricWaste(1.0F);
        running0.clone();
        assertEquals((-19586), running0.getCaloricWaste());
    }

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        UUID          uUID0          = MockUUID.fromString("");
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running running0 = new Running(uUID0, makeItFitDate0, 1073, "", "", 1073, 2765.410931544);
        int     int0     = running0.caloricWaste(0.0F);
        assertEquals(0, int0);
        assertEquals(2765.410931544, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        UUID          uUID0          = MockUUID.fromString("");
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running running0 = new Running(uUID0, makeItFitDate0, 1073, "", "", 1073, 2765.410931544);
        running0.setDistance((-1416.54184271848));
        int int0 = running0.caloricWaste((-4119.27F));
        assertEquals(80_682_500, int0);
    }

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0,
                                       (MakeItFitDate) null,
                                       (-2044),
                                       "7xE4rLd}Kk8{=ly",
                                       "7xE4rLd}Kk8{=ly",
                                       (-2044),
                                       837.69947212829);
        Running running1 = new Running(uUID0,
                                       (MakeItFitDate) null,
                                       (-2044),
                                       "7xE4rLd}Kk8{=ly",
                                       "7xE4rLd}Kk8{=ly",
                                       837.69947212829,
                                       837.69947212829);
        running0.setDesignation((String) null);
        // Undeclared exception!
        try {
            running0.equals(running1);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.activities.Activity", e);
        }
    }

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Running running0 = null;
        try {
            running0 = new Running((Running) null);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.activities.Activity", e);
        }
    }

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        UUID          uUID0          = MockUUID.fromString("");
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running running0 = new Running(uUID0, makeItFitDate0, 1073, "", "", 1073, 2765.410931544);
        double  double0  = running0.getSpeed();
        assertEquals(2765.410931544, double0, 0.01);
    }

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0, (MakeItFitDate) null, (-8), "", "", (-8), (-8));
        int     int0     = running0.caloricWaste((-8));
        assertEquals((-2), int0);
    }

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        UUID    uUID0 = MockUUID.randomUUID();
        Running running0 =
            new Running(uUID0, (MakeItFitDate) null, 122, "m", "m", 122, 0.04436024619040431);
        Running running1 = new Running(uUID0,
                                       (MakeItFitDate) null,
                                       122,
                                       "m",
                                       "m",
                                       0.04436024619040431,
                                       0.04436024619040431);
        boolean boolean0 = running0.equals(running1);
        assertFalse(boolean0);
        assertEquals(0.04436024619040431, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0 =
            new Running(uUID0, makeItFitDate0, 1065, "}ZS--1i^fM!", "}ZS--1i^fM!", 1065, 1065);
        boolean boolean0 = running0.equals("}ZS--1i^fM!");
        assertEquals(1065.0, running0.getSpeed(), 0.01);
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0, (MakeItFitDate) null, (-8), "", "", (-8), (-8));
        boolean boolean0 = running0.equals(running0);
        assertTrue(boolean0);
        assertEquals((-8.0), running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0, (MakeItFitDate) null, (-8), "", "", (-8), (-8));
        Running running1 = new Running(uUID0, (MakeItFitDate) null, (-8), "", "", (-8), (-2));
        boolean boolean0 = running0.equals(running1);
        assertEquals((-2.0), running1.getSpeed(), 0.01);
        assertFalse(running1.equals((Object) running0));
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Running       running0 =
            new Running(uUID0, makeItFitDate0, 1065, "}ZS--1i^fM!", "}ZS--1i^fM!", 1065, 1065);
        String string0 = running0.toString();
        assertEquals(
            "Activity: }ZS--1i^fM!, Code: 04000000-0500-4000-8600-000007000000, Designation: }ZS--1i^fM!, Expected Duration: 1065 minutes, Realization Date: 14/02/2014, Caloric Waste: 0 calories, Distance: 1065.0 meters, Speed: 1065.0 Km/h\n",
            string0);
    }

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        UUID    uUID0 = MockUUID.randomUUID();
        Running running0 =
            new Running(uUID0, (MakeItFitDate) null, 122, "m", "m", 122, 0.04436024619040431);
        Running running1 = running0.clone();
        assertEquals(0.04436024619040431, running1.getSpeed(), 0.01);
        assertEquals(0.04436024619040431, running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        UUID    uUID0    = MockUUID.randomUUID();
        Running running0 = new Running(uUID0, (MakeItFitDate) null, (-8), "", "", (-8), (-8));
        Running running1 = new Running(running0);
        boolean boolean0 = running0.equals(running1);
        assertTrue(boolean0);
        assertEquals((-8.0), running0.getSpeed(), 0.01);
    }

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Running running0 = null;
        try {
            running0 = new Running();
            fail("Expecting exception: NoSuchMethodError");

        } catch (NoSuchMethodError e) {
            //
            // org.evosuite.runtime.mock.java.util.MockUUID.UUID(JJ)Ljava/util/UUID;
            //
            verifyException("MakeItFit.activities.Activity", e);
        }
    }
}
