/*
 * This file was automatically generated by EvoSuite
 * Wed Jun 04 20:06:37 GMT 2025
 */

package MakeItFit.activities.implementation;

import java.util.UUID;

import MakeItFit.activities.implementation.Trail;
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
public class Trail_ESTest extends Trail_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail(uUID0,
                                 makeItFitDate0,
                                 0,
                                 ", Series: ",
                                 ", Series: ",
                                 0.0,
                                 (-1.0),
                                 (-371.353),
                                 0);
        Trail         trail1         = trail0.clone();
        trail1.setTrailType(1);
        boolean boolean0 = trail1.equals(trail0);
        assertEquals(1, trail1.getTrailType());
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail   trail0   = new Trail(uUID0, makeItFitDate0, 0, " Kg\n", " Kg\n", (-1.0), 0, 0.0, 2);
        Trail   trail1   = new Trail(trail0);
        boolean boolean0 = trail1.equals(trail0);
        assertTrue(trail0.equals((Object) trail1));
        assertEquals(2, trail0.getTrailType());
        assertTrue(boolean0);
    }

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail(uUID0,
                                 makeItFitDate0,
                                 0,
                                 "I(%K0^CD]/@",
                                 "Zl 91B2+<",
                                 0,
                                 (-746.20786369341),
                                 0,
                                 0);
        int           int0           = trail0.getTrailType();
        assertEquals(0, int0);
    }

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0 =
            new Trail(uUID0, makeItFitDate0, (-785), "h", "h", (-785), (-785), (-785), (-785));
        int int0 = trail0.getTrailType();
        assertEquals((-785), int0);
    }

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail trail0 = new Trail(uUID0, makeItFitDate0, 0, " Kg\n", " Kg\n", (-1.0), 0, 0.0, 2);
        Trail trail1 = trail0.clone();
        assertEquals(2, trail0.getTrailType());
        assertEquals(2, trail1.getTrailType());
    }

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0 =
            new Trail(uUID0, makeItFitDate0, (-785), "h", "h", (-785), (-785), (-785), (-785));
        Trail trail1 = trail0.clone();
        assertEquals((-785), trail1.getTrailType());
        assertEquals((-785), trail0.getTrailType());
    }

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        trail0.setDuration(1592);
        Trail trail1 = trail0.clone();
        assertEquals(1363, trail1.getTrailType());
        assertEquals(1363, trail0.getTrailType());
    }

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        MakeItFitDate makeItFitDate0 = MakeItFitDate.of(2, 2, 1);
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 1,
                                 "l-*E7'c(Hr(",
                                 "l-*E7'c(Hr(",
                                 (-428.91462161926546),
                                 2,
                                 1,
                                 1);
        trail0.setDuration((-2412));
        Trail trail1 = trail0.clone();
        assertEquals(1, trail0.getTrailType());
        assertEquals(1, trail1.getTrailType());
    }

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        UUID          uUID0          = MockUUID.randomUUID();
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail trail0 = new Trail(uUID0, makeItFitDate0, 0, "\n", "\n", 0.0, 0.0, (-2361.998297), 0);
        trail0.calculateCaloricWaste((-4404.8145F));
        trail0.clone();
        assertEquals(10404, trail0.getCaloricWaste());
    }

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        UUID  uUID0  = MockUUID.randomUUID();
        Trail trail0 = new Trail(uUID0,
                                 (MakeItFitDate) null,
                                 1023,
                                 "<)C;,KM.)*yuK.|",
                                 "<)C;,KM.)*yuK.|",
                                 1023,
                                 2401.8,
                                 (-210.9),
                                 (-4665));
        trail0.setCaloricWaste((-1));
        Trail trail1 = trail0.clone();
        assertEquals((-4665), trail0.getTrailType());
        assertEquals((-4665), trail1.getTrailType());
    }

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        UUID          uUID0          = MockUUID.fromString("E1tM&+u>ta+)nt17");
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0 =
            new Trail(uUID0, makeItFitDate0, 1291, "'u_f8R8'dJ", "", 1291, 1291, 1544.3615783, 2);
        int int0 = trail0.caloricWaste(1);
        assertEquals(9, int0);
        assertEquals(2, trail0.getTrailType());
    }

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        int           int0           = trail0.caloricWaste(1);
        assertEquals(1363, trail0.getTrailType());
        assertEquals((-4), int0);
    }

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        UUID  uUID0  = MockUUID.fromString((String) null);
        Trail trail0 = new Trail(uUID0,
                                 (MakeItFitDate) null,
                                 (-1),
                                 (String) null,
                                 (String) null,
                                 0.1,
                                 0.1,
                                 0.1,
                                 (-547));
        Trail trail1 = trail0.clone();
        // Undeclared exception!
        try {
            trail0.equals(trail1);
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
        Trail trail0 = null;
        try {
            trail0 = new Trail((Trail) null);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.activities.Activity", e);
        }
    }

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        int           int0           = trail0.caloricWaste(0.0F);
        assertEquals(1363, trail0.getTrailType());
        assertEquals(0, int0);
    }

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        int           int0           = trail0.getTrailType();
        assertEquals(1363, int0);
    }

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-36),
                                 "3FjXD.V^djy ^P[",
                                 "3FjXD.V^djy ^P[",
                                 (-36),
                                 (-36),
                                 (-36),
                                 (-36));
        Trail         trail1         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 0,
                                 "3FjXD.V^djy ^P[",
                                 "3FjXD.V^djy ^P[",
                                 1,
                                 2,
                                 1,
                                 2);
        boolean       boolean0       = trail0.equals(trail1);
        assertEquals(2, trail1.getTrailType());
        assertEquals((-36), trail0.getTrailType());
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        MakeItFitDate makeItFitDate0 = MakeItFitDate.of(2, 2, 1);
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 1,
                                 "l-*E7'c(Hr(",
                                 "l-*E7'c(Hr(",
                                 (-428.91462161926546),
                                 2,
                                 1,
                                 1);
        Object        object0        = new Object();
        boolean       boolean0       = trail0.equals(object0);
        assertEquals(1, trail0.getTrailType());
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0 = new Trail((UUID) null, makeItFitDate0, 0, "nld", "nld", 0, 0, 0, 0);
        boolean       boolean0 = trail0.equals(trail0);
        assertEquals(0, trail0.getTrailType());
        assertTrue(boolean0);
    }

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        String        string0        = trail0.toString();
        assertEquals(
            "Activity: , Code: 00000000-0100-4000-8200-000003000000, Designation: , Expected Duration: -2995 minutes, Realization Date: 14/02/2014, Caloric Waste: 0 calories, Distance: -920.21864351 meters, Elevation Gain: 650.08524624706 meters, Elevation Loss: -441.59161388802 meters, Trail Type: 1363\n",
            string0);
    }

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        assertEquals(1363, trail0.getTrailType());

        trail0.setTrailType((-2995));
        assertEquals(0, trail0.getTrailType());
    }

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        MakeItFitDate makeItFitDate0 = new MakeItFitDate();
        Trail         trail0         = new Trail((UUID) null,
                                 makeItFitDate0,
                                 (-2995),
                                 "",
                                 "",
                                 (-920.21864351),
                                 650.08524624706,
                                 (-441.59161388802),
                                 1363);
        Trail         trail1         = new Trail(trail0);
        assertEquals(1363, trail0.getTrailType());

        trail0.setTrailType((-2995));
        boolean boolean0 = trail0.equals(trail1);
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Trail trail0 = null;
        try {
            trail0 = new Trail();
            fail("Expecting exception: NoSuchMethodError");

        } catch (NoSuchMethodError e) {
            //
            // org.evosuite.runtime.mock.java.util.MockUUID.UUID(JJ)Ljava/util/UUID;
            //
            verifyException("MakeItFit.activities.Activity", e);
        }
    }
}
