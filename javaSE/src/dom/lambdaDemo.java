package dom;

@FunctionalInterface
interface myLambdaInterface {
    void doSomething(String s);
}

public class lambda {
    public static void main(String[] args) {
        myLambdaInterface aBlockOfCode = (s)-> System.out.println(s);
//        myLambdaInterface aBlockOfCode = public void doSomething(String s){
//            System.out.println(s);
//        }
    }

}
