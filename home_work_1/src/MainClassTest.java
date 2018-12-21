import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass Num = new MainClass();

    @Test
    public void testGetLocalNumber() {

        int a = Num.getLocalNumber();

        Assert.assertEquals("Возвращаемое значение не равно 14, тест не пройден", 14, a);
    }

    @Test
    public void testGetClassNumber() {

        int a = Num.getClassNumber();

        Assert.assertTrue("Возвращаемое значение меньше 45", a > 45);

    }

    @Test
    public void testGetClassString() {

        String str = Num.getClassString();

        Assert.assertTrue("в строке нет подстроки Hello или hello", str.contains("hello") || str.contains("Hello"));
    }

}
