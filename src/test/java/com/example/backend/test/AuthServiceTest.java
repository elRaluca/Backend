package com.example.backend.test;

import com.example.backend.service.AuthService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AuthServiceTest {

    @Test
    public void testIsPasswordComplexEnough() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        AuthService authService = new AuthService();
        Method method = AuthService.class.getDeclaredMethod("isPasswordComplexEnough", String.class);
        method.setAccessible(true);
        assertTrue((Boolean) method.invoke(authService, "Password123!"), "Test 1 - Complex enough");
        assertFalse((Boolean) method.invoke(authService, "password"), "Test 2 - No upper, " +
                "digits, or special");
        assertFalse((Boolean) method.invoke(authService, "PASSWORD123"), "Test 3 - No lower or special");
        assertFalse((Boolean) method.invoke(authService, "pass"), "Test 4 - Short password");
        assertFalse((Boolean) method.invoke(authService, "12345678"), "Test 5 - Digits only");
        assertTrue((Boolean) method.invoke(authService, "Pass.1234"), "Test 6 - " +
                "Minimum length and all conditions met");
        assertFalse((Boolean) method.invoke(authService, ""),  "Test 7 - Empty string");
    }
}


