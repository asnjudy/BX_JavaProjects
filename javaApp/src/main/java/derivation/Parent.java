package derivation;

public class Parent {

    private String name;



    public Parent() {
        System.out.println("construct parent");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
