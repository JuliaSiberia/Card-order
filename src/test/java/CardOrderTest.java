import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardOrderTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        $("[data-test-id=name] input").setValue("Кульпина Юлия");
        $("[data-test-id=phone] input").setValue("+98765432101");
        $("[data-test-id=agreement]").click();
        $("[type=button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void enteringInvalidNameValues() {
        $("[data-test-id=name] input").setValue("Kulpina Yuliya");
        $("[data-test-id=phone] input").setValue("+98765432101");
        $("[data-test-id=agreement]").click();
        $("[type=button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void invalidInputPhone() {
        $("[data-test-id=name] input").setValue("Кульпина Юлия");
        $("[data-test-id=phone] input").setValue("+987654321");
        $("[data-test-id=agreement]").click();
        $("[type=button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void nameFieldNotFilled() {
        $("[data-test-id=phone] input").setValue("+98765432101");
        $("[data-test-id=agreement]").click();
        $("[type=button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void phoneFieldNotFilled() {
        $("[data-test-id=name] input").setValue("Кульпина Юлия");
        $("[data-test-id=agreement]").click();
        $("[type=button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void disagreementWithTheProcessingOfPersonalData() {
        $("[data-test-id=name] input").setValue("Кульпина Юлия");
        $("[data-test-id=phone] input").setValue("+98765432101");
        $("[type=button").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}