package com.github.dmogn.watervolume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

    public static void invokeStaticMethod(Class targetClass,
            String methodName, Class[] argClasses, Object[] argObjects)
            throws InvocationTargetException {

        try {
            Method method = targetClass.getDeclaredMethod(methodName,
                    argClasses);
            method.setAccessible(true);
            method.invoke(null, argObjects);
        } catch (NoSuchMethodException e) {
            // Should happen only rarely, because most times the
            // specified method should exist. If it does happen, just let
            // the test fail so the programmer can fix the problem.
            throw new InvocationTargetException(e);
        } catch (SecurityException e) {
            // Should happen only rarely, because the setAccessible(true)
            // should be allowed in when running unit tests. If it does
            // happen, just let the test fail so the programmer can fix
            // the problem.
            throw new InvocationTargetException(e);
        } catch (IllegalAccessException e) {
            // Should never happen, because setting accessible flag to
            // true. If setting accessible fails, should throw a security
            // exception at that point and never get to the invoke. But
            // just in case, wrap it in a TestFailedException and let a
            // human figure it out.
            throw new InvocationTargetException(e);
        } catch (IllegalArgumentException e) {
            // Should happen only rarely, because usually the right
            // number and types of arguments will be passed. If it does
            // happen, just let the test fail so the programmer can fix
            // the problem.
            throw new InvocationTargetException(e);
        }
    }
}
