import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass Num = new MainClass();

    @Test
    public void testGetLocalNumber() {

        int a = Num.getLocalNumber();

//        if (a == 14)
//        {
//            System.out.println("Число равно 14, тест пройден");
//        } else {
//            System.out.println("Число не равно 14, тест провален");
//        }

        Assert.assertEquals("Возвращаемое значение не равно 14, тест не пройден", 14, a);
    }

    @Test
    public void testGetClassNumber() {

        int a = Num.getClassNumber();

        if (a > 45)
        {
            Assert.assertTrue("Возвращаемое значение больше 45", true);
        } else {

            Assert.assertFalse("Возвращаемое значение меньше 45", true);
        }
    }

    @Test
    public void testGetClassString() {

        String str = Num.getClassString();
        boolean a = str.toLowerCase().contains("hello");

        Assert.assertTrue("в строке нет подстроки Hello или hello", a);
    }

}
