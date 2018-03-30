package derivation;

public class Test {

    public static void main(String[] args) {

        Child child = new Child();
        Child child2 = new Child();

        child.setName("parent 1");
        child.setChildName("child 1");

        child2.setName("parent 2");
        child2.setChildName("child 2");

        System.out.println(child.getName());
        System.out.println(child2.getName());
    }
}
