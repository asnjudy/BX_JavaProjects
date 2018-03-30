package constant;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConfigTest {

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    public static void main(String[] args) throws Exception {

        System.out.println(Config.common);
        setFinalStatic(Config.class.getField("common"), "sdfsfdsfsfs");
        System.out.println(Config.common);


        System.out.println(File.separator);
        setFinalStatic(File.class.getField("separator"), "222");
        System.out.println(File.separator); // prints "#"
    }
}
