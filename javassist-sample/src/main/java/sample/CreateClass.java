package sample;


import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class CreateClass {


    public static void main(String[] args) throws CannotCompileException, NotFoundException, IOException, URISyntaxException {

        ClassPool classPool = ClassPool.getDefault();
        CtClass stuClass = classPool.makeClass("com.asn.Student");
        System.out.println(stuClass);
        ClassFile stuClassFile = stuClass.getClassFile();
        ConstPool constPool = stuClassFile.getConstPool();

        // 增加 id 属性
        CtField idField = new CtField(CtClass.longType, "id", stuClass);
        stuClass.addField(idField);

        // 增加 name 属性
        CtField nameField = new CtField(classPool.get("java.lang.String"), "name", stuClass);
        stuClass.addField(nameField);

        // 增加 age 属性
        CtField ageField = new CtField(CtClass.intType, "age", stuClass);
        stuClass.addField(ageField);

        CtMethod getMethod = CtNewMethod.make("public int getAge() { return this.age; }", stuClass);
        CtMethod setMethod = CtNewMethod.make("public void setAge(int age) { this.age = age;}", stuClass);

        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annot = new Annotation("MyAnnotation", constPool);
        annotationsAttribute.addAnnotation(annot);

        //给getAge方法添加注解
        getMethod.getMethodInfo().addAttribute(annotationsAttribute);

        stuClass.addMethod(getMethod);
        stuClass.addMethod(setMethod);

        System.out.println(stuClass);

        Class clazz = stuClass.toClass();
        System.out.println(clazz);


        URL url = ClassLoader.getSystemClassLoader().getResource("");

        File f = new File(url.toURI());
        String classPath = f.getAbsolutePath();
        if (! new File(classPath + "/com/asn/").exists()) {
            new File(classPath + "/com/").mkdir();
            new File(classPath + "/com/asn/").mkdir();
        }
        String outPath = classPath + "/com/asn/Student.class";

       DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(outPath)));
        stuClass.toBytecode(dataOutputStream);


    }
}
