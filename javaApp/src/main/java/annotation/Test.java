package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws NoSuchMethodException {

        Annotation annotation = TheClass.class.getAnnotation(MyAnnotation.class);


        if (annotation instanceof MyAnnotation) {
            System.out.println("name: " + ((MyAnnotation) annotation).name());
            System.out.println("value: " + ((MyAnnotation) annotation).value());
        }

        Method declaredMethod = TheClass.class.getDeclaredMethod("dosomething");
        annotation = declaredMethod.getAnnotation(MyAnnotation.class);
        if (annotation instanceof MyAnnotation) {
            System.out.println("name: " + ((MyAnnotation) annotation).name());
            System.out.println("value: " + ((MyAnnotation) annotation).value());
        }


    }
}
