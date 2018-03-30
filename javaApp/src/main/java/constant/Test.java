package constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Test {

    private static final int primitiveInt = 42;
    private static final Integer wrappedInt = 42;
    private static final String stringValue = new String("42");



    public void changeField(String name, Object value) throws IllegalAccessException, NoSuchFieldException {
        Field field = Test.class.getDeclaredField(name);
        field.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
        System.out.println("reflection: " + name + " = " + field.get(null));
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Test test = new Test();

        test.changeField("primitiveInt", new Integer(84));
        test.changeField("wrappedInt", new Integer(84));
        test.changeField("stringValue", "84");

        System.out.println("direct: primitiveInt = " + primitiveInt);
        System.out.println("direct: wrappedInt = " + wrappedInt);
        System.out.println("direct: stringValue = " + stringValue);
    }
}
