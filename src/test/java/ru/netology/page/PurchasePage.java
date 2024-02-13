package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PurchasePage {

    //Кнопка "купить"
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement buyHeading = $(byText("Оплата по карте"));

    //Кнопака "Купить в кредит"
    private final SelenideElement creditButton = $(byText("Купить в кредит"));
    private final SelenideElement creditHeading = $(byText("Кредит по данным карты"));

    //ввод данных
    //поле номера карты
    private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement cardNumberFieldError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    //поле месяца
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement monthFieldError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    //Поле года
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement yearFieldError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    //поле владельца
    private final SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    private final SelenideElement ownerFieldError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    //поле CVC
    private final SelenideElement cvcField = $("input[placeholder='999']");
    private final SelenideElement cvcFieldError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
    //уведомления
    private final SelenideElement notificationSuccessfully = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    //кнопка "продолжить"
    private final SelenideElement continueButton = $("form button");

    //нажать на кнопку "купить", заголовок "оплата по карте"
    public void cardPayment() {
        buyButton.click();
        buyHeading.shouldBe(Condition.visible);
    }

    //Нажать на кнопку "Купить в кредит", заголовок "Кредит по данным карты"
    public void cardCredit() {
        creditButton.click();
        creditHeading.shouldBe(Condition.visible);
    }

    //Пустые поля
    public void emptyForm() {
        cardNumberFieldError.shouldBe(Condition.visible);
        monthFieldError.shouldBe(Condition.visible);
        yearFieldError.shouldBe(Condition.visible);
        ownerFieldError.shouldBe(Condition.visible);
        cvcFieldError.shouldBe(Condition.visible);
    }

    public void setCardNumberFieldErrorHidden() {
        cardNumberFieldError.shouldBe(Condition.visible);
        monthFieldError.shouldBe(Condition.hidden);
        yearFieldError.shouldBe(Condition.hidden);
        ownerFieldError.shouldBe(Condition.hidden);
        cvcFieldError.shouldBe(Condition.hidden);
    }


    public void setOwnerFieldErrorHidden() {
        ownerFieldError.shouldBe(Condition.visible);
        cardNumberFieldError.shouldBe(Condition.hidden);
        monthFieldError.shouldBe(Condition.hidden);
        yearFieldError.shouldBe(Condition.hidden);
        cvcFieldError.shouldBe(Condition.hidden);
    }


    public void setCvcFieldErrorHidden() {
        cvcFieldError.shouldBe(Condition.visible);
        cardNumberFieldError.shouldBe(Condition.hidden);
        monthFieldError.shouldBe(Condition.hidden);
        yearFieldError.shouldBe(Condition.hidden);
        ownerFieldError.shouldBe(Condition.hidden);
    }


    public void setMonthFieldErrorHidden() {
        monthFieldError.shouldBe(Condition.visible);
        cardNumberFieldError.shouldBe(Condition.hidden);
        yearFieldError.shouldBe(Condition.hidden);
        ownerFieldError.shouldBe(Condition.hidden);
        cvcFieldError.shouldBe(Condition.hidden);
    }


    public void setYearFieldErrorHidden() {
        yearFieldError.shouldBe(Condition.visible);
        cardNumberFieldError.shouldBe(Condition.hidden);
        monthFieldError.shouldBe(Condition.hidden);
        ownerFieldError.shouldBe(Condition.hidden);
        cvcFieldError.shouldBe(Condition.hidden);
    }


    //отправка данных
    public void sendingData(DataHelper.CardInfo info) {
        cardNumberField.setValue(info.getCardNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        cvcField.setValue(info.getCvc());
        continueButton.click();
    }

    //Заявка принята
    public void bankApproved() {
        notificationSuccessfully.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    //Заявка отклонена
    public void bankDeclined() {
        notificationError.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}

