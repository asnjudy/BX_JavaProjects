package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();
        list.add("dp03");
        list.add("dp01");
        list.add("dp05");
        list.add("DP06");
        list.add("dp07");
        list.add("dp04");
        list.add("dp02");
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        System.out.println(list);

        list = new ArrayList<String>();
        list.add("1.0.0");
        list.add("1.2.0");
        list.add("1.3.0");
        list.add("1.3.1");
        list.add("1.4.3");
        list.add("1.4.0");
        list.add("1.2.6");
        list.add("1.1.5");
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        System.out.println(list + ", " + list.get(list.size() - 1));

    }

}
