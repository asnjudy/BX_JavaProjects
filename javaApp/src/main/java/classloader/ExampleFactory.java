package classloader;


import java.net.MalformedURLException;

import java.net.URL;

import java.net.URLClassLoader;




public class ExampleFactory {

   private static class MyClassLoader extends URLClassLoader {

        public MyClassLoader(URL[] urls) {
            super(urls);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            /**
             * 如果是classloader.Example类，则在当前自定义类加载器中调用findClass去加载一个全新的Class实例
             */
            if ("classloader.Example".equals(name)) {
                return findClass(name);
            }
            // 对于其他的类，调用父类URLClassLoader实例去加载
            return super.loadClass(name);
        }
    }

    public static IExample newInstance() {
        URL[] urls = {
                getClassPath()
        };

        // 每次都创建一个新的类加载器
        URLClassLoader classLoader = new MyClassLoader(urls);

        try {
            Class<?> aClass = classLoader.loadClass("classloader.Example");
            System.out.println(aClass + "@" + aClass.hashCode());
            return (IExample) aClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static URL getClassPath() {
        String resName = ExampleFactory.class.getName().replace('.', '/') + ".class";
        String loc = ExampleFactory.class.getClassLoader().getResource(resName).toExternalForm();
        URL cp;
        try {
            cp = new URL(loc.substring(0, loc.length() - resName.length()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return cp;
    }
}


