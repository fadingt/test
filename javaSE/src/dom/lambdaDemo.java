package dom;

@FunctionalInterface
interface myLambdaInterface {
    void doSomething(String s);
}

public class lambdaDemo {

    public static void setaBlockOfCode(myLambdaInterface aBlockOfCode, String s) {
        aBlockOfCode.doSomething(s);
    }

    public static void main(String[] args) {

        setaBlockOfCode(s -> System.out.println(s), "hello world");
    }

}
