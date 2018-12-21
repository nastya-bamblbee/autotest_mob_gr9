import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass Num = new MainClass();

    @Test
    public void testGetLocalNumber() {

        int a = Num.getLocalNumber();

        Assert.assertEquals("Метод getLocalNumber возвращает значение не равное 14", 14, a);
    }

    @Test
    public void testGetClassNumber() {

        int a = Num.getClassNumber();

        Assert.assertTrue("Метод getClassNumber возвращает значение меньше 45", a > 45);

    }

    @Test
    public void testGetClassString() {

        String str = Num.getClassString();

        Assert.assertTrue("Метод getClassString возвращает строку, не содержащую подстроку Hello или hello", str.contains("hello") || str.contains("Hello"));
    }

}
