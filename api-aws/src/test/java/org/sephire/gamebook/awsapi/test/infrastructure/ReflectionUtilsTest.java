package org.sephire.gamebook.awsapi.test.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.sephire.gamebook.awsapi.infrastructure.ReflectionUtils.getParameterClass;
import static org.sephire.gamebook.awsapi.infrastructure.ReflectionUtils.isParameterEmpty;

@DisplayName("The reflection utils class,")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReflectionUtilsTest {

    @DisplayName("for the get parameter class method,")
    @Test
    public void testGetParameterClass() {
        Class<?> firstParameterClass = getParameterClass(SomeGenericSubclass.class, 0);
        Class<?> secondParameterClass = getParameterClass(SomeGenericSubclass.class, 1);
        Class<?> thirdParameterClass = getParameterClass(SomeGenericSubclass.class, 2);


        assertEquals(firstParameterClass.getName(), String.class.getName(),
                "should identify correctly the first parameter's class");
        assertEquals(secondParameterClass.getName(), Boolean.class.getName(),
                "should identify correctly the second parameter's class");
        assertEquals(thirdParameterClass.getName(), Integer.class.getName(),
                "should identify correctly the third parameter's class");

    }

    @DisplayName("for the is parameter empty method,")
    @Test
    public void testIsParameterEmpty() {
        boolean isFirstParameterEmpty = isParameterEmpty(SomeGenericSubclassWithVoid.class, 0);
        boolean isSecondParameterEmpty = isParameterEmpty(SomeGenericSubclassWithVoid.class, 1);

        assertTrue(isFirstParameterEmpty, "should detect the first parameter as empty");
        assertFalse(isSecondParameterEmpty, "should detect the second parameter as not empty");
    }

    private static class SomeGenericClass<A, B, C> {
    }

    private static class SomeGenericSubclass extends SomeGenericClass<String, Boolean, Integer> {
    }

    private static class SomeGenericSubclassWithVoid extends SomeGenericClass<Void, Long, Void> {
    }

}
