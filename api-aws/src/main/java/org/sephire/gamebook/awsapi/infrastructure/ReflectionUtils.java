package org.sephire.gamebook.awsapi.infrastructure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {

    /**
     * Given a generic parameterized class, get the class of the given parameter position.
     * <p>
     * The target class <b>must</b> be a subclass with defined parameter types of a generic
     * superclass, otherwise the workaround reflection trick won't work.
     * </p>
     * <p>
     * For example, this will work:
     * <pre>
     *         public class SomeGenericSuperClass&lt;A,B,C&gt;{}
     *         public class SomeGenericSubclass&lt;String,Boolean,Integer&gt; {}
     *
     *         public class Test {
     *             public static void main(String[] args){
     *                 Class&lt;?&gt; param1Class = ReflectionUtils.getParameterClass(SomeGenericSubclass.class,0);
     *                 // param1.getClass().getName() == String.class.getName()
     *             }
     *         }
     *     </pre>
     * This will not:
     * <pre>
     *         public class SomeGenericClass&lt;A,B,C&gt;{}
     *         public class Test {
     *             public static void main(String[] args){
     *                 Class&lt;?&gt; param1Class = ReflectionUtils.getParameterClass(SomeGenericClass.class,0);
     *                 // RuntimeException
     *             }
     *         }
     *     </pre>
     * </p>
     * <p>
     * This solution is based on the <a href="https://www.javacodegeeks.com/2013/12/advanced-java-generics-retreiving-generic-type-arguments.html">
     * following article</a>
     * </p>
     *
     * @param targetClass       the subclass of a generic super class
     * @param parameterPosition the paramenter position in the generic list starting from zero
     * @return The class of the generic parameter
     */
    public static Class<?> getParameterClass(Class<?> targetClass, int parameterPosition) {
        Class<?> genericClass;
        try {
            ParameterizedType fieldGenericType = (ParameterizedType) targetClass.getGenericSuperclass();
            Type finalType = fieldGenericType.getActualTypeArguments()[parameterPosition];
            genericClass = Class.forName(finalType.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not get the field class: " + e.getMessage(), e);
        }

        return genericClass;
    }

    /**
     * <p>
     * Given a parameter position for a parameterized class, checks if it is "empty", meaning
     * that the Void type has been used to fill that parameter slot.
     * </p>
     * <p>
     * The same restrictions as the getParameterClass apply to the target class.
     * </p>
     *
     * @param targetClass       <b>must</b> be a subclass with defined types in the parameters
     * @param parameterPosition The parameter position in the parameters list
     * @return Whether the parameter is empty or not
     */
    public static boolean isParameterEmpty(Class<?> targetClass, int parameterPosition) {
        Class<?> genericClass = getParameterClass(targetClass, parameterPosition);

        return genericClass.getName().equals(Void.class.getName());
    }
}
