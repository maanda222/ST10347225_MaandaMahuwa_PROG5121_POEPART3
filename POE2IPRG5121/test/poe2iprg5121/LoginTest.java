/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package poe2iprg5121;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {

        Login login = new Login();

        login.setUsername("kyl_1");

        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {

        Login login = new Login();

        login.setUsername("kyle!!!!!!");

        assertFalse(login.checkUserName());
    }

    @Test
    public void testPasswordCorrectlyFormatted() {

        Login login = new Login();

        login.setPassword("Ch&&sec@ke99!");

        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordIncorrectlyFormatted() {

        Login login = new Login();

        login.setPassword("password");

        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {

        Login login = new Login();

        login.setCellPhone("+27838968976");

        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {

        Login login = new Login();

        login.setCellPhone("08966553");

        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccess() {

        Login login = new Login();

        login.setUsername("kyl_1");
        login.setPassword("Password1!");

        assertTrue(login.loginUser("kyl_1", "Password1!"));
    }

    @Test
    public void testLoginFail() {

        Login login = new Login();

        login.setUsername("kyl_1");
        login.setPassword("Password1!");

        assertFalse(login.loginUser("wrong", "wrong"));
    }
}