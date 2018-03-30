package derivation;

public class Child extends Parent {

    private String childName;

    public Child() {
        System.out.println(this + ", construct child "
                + Integer.toHexString(hashCode()) + " "
                + Integer.toHexString(super.hashCode()));
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
