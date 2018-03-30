package annotation.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class Test {

    public static void testChangeTypeAnnotation() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {

        // Class<?> aClass = Class.forName();
        ClassLoader classLoader = Test.class.getClassLoader();
        Class<?> objectClass = classLoader.loadClass("annotation.demo.Bar");

        {
            Foo fooAnno = objectClass.getAnnotation(Foo.class);
            System.out.println(fooAnno);
            changeAnnotationAttributeValue(fooAnno, "value", "ddd343");
        }
        {
            Foo fooAnno = objectClass.getAnnotation(Foo.class);
            System.out.println(fooAnno);
        }
    }

    public static void testChangeMethodAnnotation() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {

        ClassLoader classLoader = Test.class.getClassLoader();
        Class<?> clazz = classLoader.loadClass("annotation.demo.Bar");

        {
            Method infoMethod = clazz.getDeclaredMethod("info");
            MFoo mFooAnn = (MFoo) infoMethod.getDeclaredAnnotations()[0];
            System.out.println(mFooAnn);
            changeAnnotationAttributeValue(mFooAnn, "value", "TTT");

        }
        {
            Method infoMethod = clazz.getDeclaredMethod("info");
            MFoo mFooAnn = (MFoo) infoMethod.getDeclaredAnnotations()[0];
            System.out.println(mFooAnn);
        }

    }


    public static void changeAnnotationAttributeValue(Object annotation, String attributeName, Object value) throws NoSuchFieldException, IllegalAccessException {

        //获取注解Class实例的这个代理实例所持有的 InvocationHandler
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map<String, Object> memberValues = (Map) hField.get(invocationHandler);
        // 修改 value 属性值
        memberValues.put(attributeName, value);
    }


    public static void printAnnotationInfo() {
        String packName = "annotation";

        Predicate<String> filter = new FilterBuilder().includePackage(packName);

        Collection<URL> urlTotals = new ArrayList<URL>();
        urlTotals.addAll(ClasspathHelper.forPackage(packName));

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .filterInputsBy(filter)
                        .setScanners(new TypeAnnotationsScanner().filterResultsBy(filter))
                        .setUrls(urlTotals)
        );


        Set<Class<?>> annotatedWith = reflections.getTypesAnnotatedWith(Foo.class);

        for (Class<?> aClass : annotatedWith) {
            Foo annotation = aClass.getAnnotation(Foo.class);
            System.out.println(annotation.value());
        }
    }


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {


        // testChangeTypeAnnotation();

        testChangeMethodAnnotation();

    }
}
