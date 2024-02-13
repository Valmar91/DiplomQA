package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.DataHelper.getInvalidCardInfo;
import static ru.netology.data.SQLHelper.*;


public class PaymentTest {

    PurchasePage purchasePage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        Configuration.headless = true;
        purchasePage = open("http://localhost:8080", PurchasePage.class);
        purchasePage.cardPayment();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        cleanDataBase();
    }

    //Валидная карта
    @Test
    @DisplayName("Purchase with a valid card")
    public void shouldPaymentValidCard() {
        purchasePage.sendingData(getCardInfo());
        purchasePage.bankApproved();
        var expected = "APPROVED";
        var paymentInfo = getPaymentSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, paymentInfo.getStatus());
        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
    }

    //Невалидная карта
    @Test
    @DisplayName("Purchase with a invalid card")
    public void shouldPaymentInvalidCard() {
        purchasePage.sendingData(getInvalidCardInfo());
        purchasePage.bankDeclined();
        var expected = "DECLINED";
        var paymentInfo = getPaymentSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, paymentInfo.getStatus());

        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
    }

    //Проверка полей
    //Пустые поля "Купить"
    @Test
    @DisplayName("All fields are empty")
    public void shouldEmpty() {
        purchasePage.sendingData(getEmptyAllFields());
        purchasePage.emptyForm();

    }

    //Пустое поле номера карты "Купить"
    @Test
    @DisplayName("Empty card number field")
    public void shouldEmptyNCard() {
        purchasePage.sendingData(getEmptyNumberCardInfo());
        purchasePage.setCardNumberFieldErrorHidden();
    }

    //Неполный номер карты "Купить"
    @Test
    @DisplayName("Incomplete card number field")
    public void shouldIncompleteNCard() {
        purchasePage.sendingData(getCardInfoWithIncompliteCardNumber());
        purchasePage.setCardNumberFieldErrorHidden();
    }

    //Пустое поле месяца "купить"
    @Test
    @DisplayName("Empty month field")
    public void shouldEmptyMonth() {
        purchasePage.sendingData(getEmptyMonth());
        purchasePage.setMonthFieldErrorHidden();
    }

    //Поле месяц 00 "купить"
    @Test
    @DisplayName("00 month field")
    public void should00Month() {
        purchasePage.sendingData(getCardInfoWith00Month());
        purchasePage.setMonthFieldErrorHidden();
    }

    //Поле месяц 13 "купить"
    @Test
    @DisplayName("13 month field")
    public void should13Month() {
        purchasePage.sendingData(getCardInfoWith13Month());
        purchasePage.setMonthFieldErrorHidden();
    }

    //Пустое поле года
    @Test
    @DisplayName("Empty field of the year")
    public void shouldEmptyFieldYear() {
        purchasePage.sendingData(getEmptyYear());
        purchasePage.setYearFieldErrorHidden();
    }

    //Просроченный год
    @Test
    @DisplayName("Field of the Overdue year")
    public void shouldFieldOverdueYear() {
        purchasePage.sendingData(getCardInfoWithOverdueYear());
        purchasePage.setYearFieldErrorHidden();
    }

    //год +6
    @Test
    @DisplayName("Field of the Future year")
    public void shouldFieldFutureYear() {
        purchasePage.sendingData(getCardInfoWithFutureYear());
        purchasePage.setYearFieldErrorHidden();
    }

    //Пустое поле владельца
    @Test
    @DisplayName("Empty owner field")
    public void shouldEmptyOwnerField() {
        purchasePage.sendingData(getEmptyOwner());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца с пробелом
    @Test
    @DisplayName("Owner's field with a space")
    public void shouldOwnerFieldWithSpace() {
        purchasePage.sendingData(getCardInfoWithWhitespace());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца с -
    @Test
    @DisplayName("Owner's field -")
    public void shouldOwnerFieldWithHyphen() {
        purchasePage.sendingData(getCardInfoWithHyphen());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца со спецсимволами
    @Test
    @DisplayName("Owner's field with special Symbols")
    public void shouldOwnerFieldSpecialSymbols() {
        purchasePage.sendingData(getCardInfoWithSpecialSymbols());
        purchasePage.setOwnerFieldErrorHidden();
    }


    //Поле владельца с цифрами
    @Test
    @DisplayName("Owner's field with Numbers")
    public void shouldOwnerFieldSpecialNumbers() {
        purchasePage.sendingData(getCardInfoWithNumbers());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Пустое поле CVC
    @Test
    @DisplayName("Empty cvc field")
    public void shouldEmptyCVCField() {
        purchasePage.sendingData(getEmptyCvc());
        purchasePage.setCvcFieldErrorHidden();
    }

    //Не полный CVC
    @Test
    @DisplayName("2 numbers cvc field")
    public void should2NumbersCVCField() {
        purchasePage.sendingData(getCardInfoWithIncompleteCVC());
        purchasePage.setCvcFieldErrorHidden();
    }

    //Просроченный месяц текущего года
    @Test
    @DisplayName("Overdue month of the current year")
    public void shouldOverdueMoth() {
        purchasePage.sendingData(getCardInfoWithOverdueMonth());
        purchasePage.setMonthFieldErrorHidden();
    }

}
