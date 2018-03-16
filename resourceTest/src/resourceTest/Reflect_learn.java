package resourceTest;

public class Reflect_learn {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        Class clazz = Class.forName("Reflect_learn");
        Class<Reflect_learn> clazz = Reflect_learn.class;
        Reflect_learn reflect_learn = (Reflect_learn) clazz.newInstance();
        System.out.println(reflect_learn.toString());
    }
}
