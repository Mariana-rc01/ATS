/*
 * This file was automatically generated by EvoSuite
 * Wed Jun 04 19:59:02 GMT 2025
 */

package MakeItFit.menu;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import MakeItFit.menu.Menu;
import MakeItFit.menu.MenuItem;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.util.SystemInUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true,
                     useVFS                = true,
                     useVNET               = true,
                     resetStaticState      = true,
                     useJEE                = true)
public class Menu_ESTest extends Menu_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        SystemInUtil.addInputLine("2");
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        MenuItem.Handler     menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem("", menuItem_Handler0);
        linkedList0.add(menuItem0);
        linkedList0.offer(menuItem0);
        linkedList0.add(menuItem0);
        // Undeclared exception!
        try {
            menu0.run();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        linkedList0.add(0, (MenuItem) null);
        Menu menu0 = new Menu(linkedList0);
        // Undeclared exception!
        try {
            menu0.run();
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.menu.Menu", e);
        }
    }

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        SystemInUtil.addInputLine("9");
        Menu menu0 = new Menu((List<MenuItem>) null);
        // Undeclared exception!
        try {
            menu0.getUserChoice();
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.menu.Menu", e);
        }
    }

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Menu menu0 = new Menu((List<MenuItem>) null);
        SystemInUtil.addInputLine("9");
        // Undeclared exception!
        try {
            menu0.executeSelectedOption();
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.menu.Menu", e);
        }
    }

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        linkedList0.add((MenuItem) null);
        // Undeclared exception!
        try {
            menu0.display();
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("MakeItFit.menu.Menu", e);
        }
    }

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        SystemInUtil.addInputLine("2");
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        MenuItem.Handler     menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem("", menuItem_Handler0);
        linkedList0.add(menuItem0);
        int int0 = menu0.getUserChoice();
        assertEquals(2, int0);
    }

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        SystemInUtil.addInputLine("2");
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        MenuItem.Handler     menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem("", menuItem_Handler0);
        linkedList0.add(menuItem0);
        menu0.run();
    }

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        SystemInUtil.addInputLine("2");
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        MenuItem.Handler     menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem("", menuItem_Handler0);
        linkedList0.add(menuItem0);
        linkedList0.add(menuItem0);
        menu0.executeSelectedOption();
    }

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        SystemInUtil.addInputLine("2");
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        MenuItem.Handler     menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem("", menuItem_Handler0);
        linkedList0.add(menuItem0);
        menu0.executeSelectedOption();
    }

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        SystemInUtil.addInputLine("0");
        Menu menu0 = new Menu(linkedList0);
        // Undeclared exception!
        try {
            menu0.getUserChoice();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        SystemInUtil.addInputLine("9");
        // Undeclared exception!
        try {
            menu0.getUserChoice();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        Menu                 menu0       = new Menu(linkedList0);
        // Undeclared exception!
        try {
            menu0.executeSelectedOption();
            fail("Expecting exception: NoSuchElementException");

        } catch (NoSuchElementException e) {
            //
            // No line found
            //
            verifyException("java.util.Scanner", e);
        }
    }

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        LinkedList<MenuItem>  linkedList0 = new LinkedList<MenuItem>();
        MenuItem.PreCondition menuItem_PreCondition0 =
            mock(MenuItem.PreCondition.class, new ViolatedAssumptionAnswer());
        doReturn(false).when(menuItem_PreCondition0).validate();
        MenuItem.Handler menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem(";7C{(co0f~", menuItem_PreCondition0, menuItem_Handler0);
        linkedList0.add(menuItem0);
        Menu menu0 = new Menu(linkedList0);
        menu0.display();
    }

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        LinkedList<MenuItem> linkedList0 = new LinkedList<MenuItem>();
        MenuItem.Handler     menuItem_Handler0 =
            mock(MenuItem.Handler.class, new ViolatedAssumptionAnswer());
        MenuItem menuItem0 = new MenuItem("", menuItem_Handler0);
        linkedList0.add(menuItem0);
        Menu menu0 = new Menu(linkedList0);
        menu0.display();
    }
}
