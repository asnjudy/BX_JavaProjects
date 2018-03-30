package reflect;

import java.lang.reflect.Field;

public class PersonTest {


    /*
            Person person = new Person("XWT", 22, "male");

        Field nameField = person.getClass().getDeclaredField("name");
        nameField.setAccessible(true);

        // 获取属性的值
        System.out.println(nameField.get(person));
        // 设置属性的值
        nameField.set(person, "asn");
        System.out.println(person);
     */



    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String str = "  aa    bb         cc       dd           ee            ff         ";
        String s = "";
        boolean isStart = true;

        for (int i = 0; i < str.length() - 1; i++) {


            if (isStart) {
                if (str.charAt(i) == ' ') {
                    continue;
                } else {
                    s += str.charAt(i);
                    isStart = false;
                }

            } else {
                char c_1 = str.charAt(i);
                char c_2 = str.charAt(i + 1);
                //空格转成int型代表数字是32
                if (c_1 == ' ' && (int) c_2 == ' ') {
                    continue;
                }
                s += c_1;
            }
        }
        if (str.charAt(str.length() - 1) != ' ') {
            s += str.charAt(str.length() - 1);
        }

        System.out.println(s);
    }
}
