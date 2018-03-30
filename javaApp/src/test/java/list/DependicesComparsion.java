package list;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DependicesComparsion {


    static Class clazz = DependicesComparsion.class;



    public static List<String>  readDependices(String depsFile) throws IOException {

        List<String> depsList = new ArrayList<String>();

        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(depsFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        while((str = bufferedReader.readLine()) != null) {
            depsList.add(str);
        }

        inputStream.close();
        bufferedReader.close();
        return depsList;
    }


    public static void main(String[] args) throws IOException {


        List<String> newDepsWithDisconf = readDependices("deps.txt");

        List<String> oldDeps = readDependices("deps2.txt");

        int index_add = 1;

        // 发现多的依赖jar
        for (String dep : newDepsWithDisconf) {
            if (!oldDeps.contains(dep)) {
                System.out.println(index_add + " - " +dep);
                index_add++;
            }
        }


        int index_remove = 1;

        // 发现多的依赖jar
        for (String dep : oldDeps) {
            if (!newDepsWithDisconf.contains(dep)) {
                System.out.println(index_remove + " - " +dep);
                index_remove++;
            }
        }

    }
}
