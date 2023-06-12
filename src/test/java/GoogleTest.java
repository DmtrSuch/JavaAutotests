import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;

public class GoogleTest extends BaseTest {
    private final static String BASE_URL = "https://www.google.com/";

    @Test
    public void checkgoogle() {
        Selenide.open(BASE_URL);
    }
}
