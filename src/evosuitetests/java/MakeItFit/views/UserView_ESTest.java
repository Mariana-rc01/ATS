/*
 * This file was automatically generated by EvoSuite
 * Wed Jun 04 20:31:11 GMT 2025
 */

package MakeItFit.views;

import java.util.NoSuchElementException;

import MakeItFit.views.UserView;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.util.SystemInUtil;
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
public class UserView_ESTest extends UserView_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        SystemInUtil.addInputLine("Y");
        SystemInUtil.addInputLine("Y");
        UserView userView0 = new UserView();
        // Undeclared exception!
        try {
            userView0.login();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        SystemInUtil.addInputLine("Z");
        SystemInUtil.addInputLine("Z");
        UserView userView0 = new UserView();
        // Undeclared exception!
        try {
            userView0.login();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SystemInUtil.addInputLine("y");
        UserView userView0 = new UserView();
        // Undeclared exception!
        try {
            userView0.login();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }
}
