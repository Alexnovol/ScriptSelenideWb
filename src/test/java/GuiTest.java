import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Enabled;
import com.codeborne.selenide.ex.InvalidStateError;
import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.ElementClickInterceptedException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class GuiTest {


    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();

        Configuration.timeout = 15000;
    }


    @Test
    @DisplayName("Добавление товара в корзину")
    public void addProductBasket() {

        open("https://www.wildberries.ru/");

        $x("//*[@class='main-page__content']/article[1]").shouldBe(enabled);

        $("#searchInput").setValue("мобильный телефон");

        $("#applySearchBtn").click();

        SelenideElement firstProduct = $x("//*[@class='product-card-list']/article[1]");
        try {
            firstProduct.click();
        } catch (InvalidStateError e) {
            $x("//*[@class='autocomplete__scroll-container']/ul[1]").click();
            firstProduct.click();
        }

        $x("//*[@class='product-page__order-buttons']//*[@class='order__button btn-main']").click();

        $("a[data-wba-header-name='Cart']").click();

        $(".list-item__good").shouldBe(visible);
    }
}
