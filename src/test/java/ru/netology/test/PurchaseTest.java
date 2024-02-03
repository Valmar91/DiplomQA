package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.Page;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.DataHelper.getInvalidCardInfo;
import static ru.netology.data.SQLHelper.*;

public class PurchaseTest {

    Page page;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        Configuration.headless = true;
        page = open("http://localhost:8080", Page.class);
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
        page.cardPayment();
        page.sendingData(getCardInfo());
        page.bankApproved();
        var expected = "APPROVED";
        var paymentInfo = getPaymentSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, paymentInfo.getStatus());
        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
    }

    @Test
    @DisplayName("Credit with a valid card")
    public void shouldCreditInvalidCard() {
        page.cardCredit();
        page.sendingData(getCardInfo());
        page.bankApproved();
        var expected = "APPROVED";
        var creditInfo = getCreditSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, creditInfo.getStatus());
        assertEquals(creditInfo.getBank_id(), orderInfo.getCredit_id());
    }

    //Невалидная карта
    @Test
    @DisplayName("Purchase with a invalid card")
    public void shouldPaymentInvalidCard() {
        page.cardPayment();
        page.sendingData(getInvalidCardInfo());
        page.bankDeclined();
        var expected = "DECLINED";
        var paymentInfo = getPaymentSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, paymentInfo.getStatus());
        //Проверка соответствия в базе данных id в таблице покупок и в таблице заявок:
        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
    }

    @Test
    @DisplayName("Credit with a invalid card")
    public void shouldCreditValidCard() {
        page.cardCredit();
        page.sendingData(getInvalidCardInfo());
        page.bankDeclined();
        var expected = "DECLINED";
        var creditInfo = getCreditSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, creditInfo.getStatus());
        //Проверка соответствия в базе данных id в таблице покупок и в таблице заявок:
        assertEquals(creditInfo.getBank_id(), orderInfo.getCredit_id());
    }

    //Проверка полей
    //Пустые поля "Купить"
    @Test
    @DisplayName("All fields are empty")
    public void shouldEmpty() {
        page.cardPayment();
        page.emptyForm();
    }

    //Пустые поля "Купить в кредит"
    @Test
    @DisplayName("All fields are empty credit")
    public void shouldEmptyCredit() {
        page.cardCredit();
        page.emptyForm();
    }

    //Пустое поле номера карты "Купить"
    @Test
    @DisplayName("Empty card number field")
    public void shouldEmptyNCard() {
        page.cardPayment();
        page.emptyCardNumberField(getCardInfo());
    }

    //Пустое поле номера карты "Купить в кредит"
    @Test
    @DisplayName("Empty card number Credit field")
    public void shouldEmptyNCardCredit() {
        page.cardCredit();
        page.emptyCardNumberField(getCardInfo());
    }

    //Неполный номер карты "Купить"
    @Test
    @DisplayName("Incomplete card number field")
    public void shouldIncompleteNCard() {
        page.cardPayment();
        page.invalidCardNumberField(getCardInfoWithIncompliteCardNumber());
    }

    //Неполный номер карты "Купить в кредит"
    @Test
    @DisplayName("Incomplete card number credit field")
    public void shouldIncompleteNCardCredit() {
        page.cardCredit();
        page.invalidCardNumberField(getCardInfoWithIncompliteCardNumber());
    }

    //Пустое поле месяца "купить"
    @Test
    @DisplayName("Empty month field")
    public void shouldEmptyMonth() {
        page.cardPayment();
        page.emptyMonthField(getCardInfo());
    }

    //Пустое поле месяца "купить в кредит"
    @Test
    @DisplayName("Empty month credit field")
    public void shouldEmptyMonthCredit() {
        page.cardCredit();
        page.emptyMonthField(getCardInfo());
    }

    //Поле месяц 00 "купить"
    @Test
    @DisplayName("00 month field")
    public void should00Month() {
        page.cardPayment();
        page.invalidMonthField(getCardInfoWith00Month());
    }

    //Поле месяц 00 "купить в кредит"
    @Test
    @DisplayName("00 month field Credit")
    public void should00MonthCredit() {
        page.cardCredit();
        page.invalidMonthField(getCardInfoWith00Month());
    }

    //Поле месяц 13 "купить"
    @Test
    @DisplayName("13 month field")
    public void should13Month() {
        page.cardPayment();
        page.invalidMonthField(getCardInfoWith13Month());
    }

    //Поле месяц 13 "купить в кредит"
    @Test
    @DisplayName("13 month field Credit")
    public void should13MonthCredit() {
        page.cardCredit();
        page.invalidMonthField(getCardInfoWith13Month());
    }

    //Пустое поле года
    @Test
    @DisplayName("Empty field of the year")
    public void shouldEmptyFieldYear() {
        page.cardPayment();
        page.emptyYearField(getCardInfo());
    }

    //Пустое поле года "Купть в кредит"
    @Test
    @DisplayName("Empty field of the year Credit")
    public void shouldEmptyFieldYearCredit() {
        page.cardCredit();
        page.emptyYearField(getCardInfo());
    }

    //Просроченный год
    @Test
    @DisplayName("Field of the Overdue year")
    public void shouldFieldOverdueYear() {
        page.cardPayment();
        page.invalidYearField(getCardInfoWithOverdueYear());
    }

    //Просроченный год "Купить в кредит"
    @Test
    @DisplayName("Field of the Overdue year Credit")
    public void shouldFieldOverdueYearCredit() {
        page.cardCredit();
        page.invalidYearField(getCardInfoWithOverdueYear());
    }

    //год +6
    @Test
    @DisplayName("Field of the Future year")
    public void shouldFieldFutureYear() {
        page.cardPayment();
        page.invalidYearField(getCardInfoWithFutureYear());
    }

    //год +6 "Купить в кредит"
    @Test
    @DisplayName("Field of the Future year Credit")
    public void shouldFieldFutureYearCredit() {
        page.cardCredit();
        page.invalidYearField(getCardInfoWithFutureYear());
    }

    //Пустое поле владельца
    @Test
    @DisplayName("Empty owner field")
    public void shouldEmptyOwnerField() {
        page.cardPayment();
        page.emptyOwnerField(getCardInfo());
    }

    //Пустое поле владельца "Купить в кредит
    @Test
    @DisplayName("Empty owner field Credit")
    public void shouldEmptyOwnerFieldCredit() {
        page.cardCredit();
        page.emptyOwnerField(getCardInfo());
    }

    //Поле владельца с пробелом
    @Test
    @DisplayName("Owner's field with a space")
    public void shouldOwnerFieldWithSpace() {
        page.cardPayment();
        page.invalidOwnerField(getCardInfoWithWhitespace());
    }

    //Поле владельца с пробелом "Купить в кредит"
    @Test
    @DisplayName("Owner's field with a space Credit")
    public void shouldOwnerFieldWithSpaceCredit() {
        page.cardCredit();
        page.invalidOwnerField(getCardInfoWithWhitespace());
    }

    //Поле владельца с -
    @Test
    @DisplayName("Owner's field -")
    public void shouldOwnerFieldWithHyphen() {
        page.cardPayment();
        page.invalidOwnerField(getCardInfoWithHyphen());
    }

    //Поле владельца с - "купить в кредит"
    @Test
    @DisplayName("Owner's field -")
    public void shouldOwnerFieldWithHyphenCredit() {
        page.cardCredit();
        page.invalidOwnerField(getCardInfoWithHyphen());
    }

    //Поле владельца со спецсимволами
    @Test
    @DisplayName("Owner's field with special Symbols")
    public void shouldOwnerFieldSpecialSymbols() {
        page.cardPayment();
        page.invalidOwnerField(getCardInfoWithSpecialSymbols());
    }

    //Поле владельца со спецсимволами "Купить в кредит"
    @Test
    @DisplayName("Owner's field with special Symbols Credit")
    public void shouldOwnerFieldSpecialSymbolsCredit() {
        page.cardCredit();
        page.invalidOwnerField(getCardInfoWithSpecialSymbols());
    }

    //Поле владельца с цифрами
    @Test
    @DisplayName("Owner's field with Numbers")
    public void shouldOwnerFieldSpecialNumbers() {
        page.cardPayment();
        page.invalidOwnerField(getCardInfoWithNumbers());
    }

    //Поле владельца с цифрами "Купить в кредит"
    @Test
    @DisplayName("Owner's field with Numbers Credit")
    public void shouldOwnerFieldSpecialNumbersCredit() {
        page.cardCredit();
        page.invalidOwnerField(getCardInfoWithNumbers());
    }

    //Пустое поле CVC
    @Test
    @DisplayName("Empty cvc field")
    public void shouldEmptyCVCField() {
        page.cardPayment();
        page.emptyCVCField(getCardInfo());
    }

    //Пустое поле CVC "купить в кредит"
    @Test
    @DisplayName("Empty cvc field Credit")
    public void shouldEmptyCVCFieldCredit() {
        page.cardCredit();
        page.emptyCVCField(getCardInfo());
    }

    //Не полный CVC
    @Test
    @DisplayName("2 numbers cvc field")
    public void should2NumbersCVCField() {
        page.cardPayment();
        page.invalidCVCField(getCardInfoWithIncompleteCVC());
    }

    //Не полный CVC "купить в кредит"
    @Test
    @DisplayName("2 numbers cvc field Credit")
    public void should2NumbersCVCFieldCredit() {
        page.cardCredit();
        page.invalidCVCField(getCardInfoWithIncompleteCVC());
    }

    //Просроченный месяц текущего года
    @Test
    @DisplayName("Overdue month of the current year")
    public void shouldOverdueMoth() {
        page.cardPayment();
        page.invalidMonthField(getCardInfoWithOverdueMonth());
    }
}
