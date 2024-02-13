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

public class CreditTest {

    PurchasePage purchasePage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        Configuration.headless = true;
        purchasePage = open("http://localhost:8080", PurchasePage.class);
        purchasePage.cardCredit();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        cleanDataBase();
    }

    @Test
    @DisplayName("Credit with a valid card")
    public void shouldCreditInvalidCard() {
        purchasePage.sendingData(getCardInfo());
        purchasePage.bankApproved();
        var expected = "APPROVED";
        var creditInfo = getCreditSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, creditInfo.getStatus());
        assertEquals(creditInfo.getBank_id(), orderInfo.getCredit_id());
    }

    @Test
    @DisplayName("Credit with a invalid card")
    public void shouldCreditValidCard() {
        purchasePage.sendingData(getInvalidCardInfo());
        purchasePage.bankDeclined();
        var expected = "DECLINED";
        var creditInfo = getCreditSpreadsheet();
        var orderInfo = getOrderSpreadsheet();
        assertEquals(expected, creditInfo.getStatus());

        assertEquals(creditInfo.getBank_id(), orderInfo.getCredit_id());
    }

    //Пустые поля "Купить в кредит"
    @Test
    @DisplayName("All fields are empty credit")
    public void shouldEmptyCredit() {
        purchasePage.sendingData(getEmptyAllFields());
        purchasePage.emptyForm();
    }

    //Пустое поле номера карты "Купить в кредит"
    @Test
    @DisplayName("Empty card number Credit field")
    public void shouldEmptyNCardCredit() {
        purchasePage.sendingData(getEmptyNumberCardInfo());
        purchasePage.setCardNumberFieldErrorHidden();
    }

    //Неполный номер карты "Купить в кредит"
    @Test
    @DisplayName("Incomplete card number credit field")
    public void shouldIncompleteNCardCredit() {
        purchasePage.sendingData(getCardInfoWithIncompliteCardNumber());
        purchasePage.setCardNumberFieldErrorHidden();
    }

    //Пустое поле месяца "купить в кредит"
    @Test
    @DisplayName("Empty month credit field")
    public void shouldEmptyMonthCredit() {
        purchasePage.sendingData(getEmptyMonth());
        purchasePage.setMonthFieldErrorHidden();
    }

    //Поле месяц 00 "купить в кредит"
    @Test
    @DisplayName("00 month field Credit")
    public void should00MonthCredit() {
        purchasePage.sendingData(getCardInfoWith00Month());
        purchasePage.setMonthFieldErrorHidden();
    }

    //Поле месяц 13 "купить в кредит"
    @Test
    @DisplayName("13 month field Credit")
    public void should13MonthCredit() {
        purchasePage.sendingData(getCardInfoWith13Month());
        purchasePage.setMonthFieldErrorHidden();
    }

    //Пустое поле года "Купть в кредит"
    @Test
    @DisplayName("Empty field of the year Credit")
    public void shouldEmptyFieldYearCredit() {
        purchasePage.sendingData(getEmptyYear());
        purchasePage.setYearFieldErrorHidden();
    }

    //Просроченный год "Купить в кредит"
    @Test
    @DisplayName("Field of the Overdue year Credit")
    public void shouldFieldOverdueYearCredit() {
        purchasePage.sendingData(getCardInfoWithOverdueYear());
        purchasePage.setYearFieldErrorHidden();
    }

    //год +6 "Купить в кредит"
    @Test
    @DisplayName("Field of the Future year Credit")
    public void shouldFieldFutureYearCredit() {
        purchasePage.sendingData(getCardInfoWithFutureYear());
        purchasePage.setYearFieldErrorHidden();
    }

    //Пустое поле владельца "Купить в кредит
    @Test
    @DisplayName("Empty owner field Credit")
    public void shouldEmptyOwnerFieldCredit() {
        purchasePage.sendingData(getEmptyOwner());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца с пробелом "Купить в кредит"
    @Test
    @DisplayName("Owner's field with a space Credit")
    public void shouldOwnerFieldWithSpaceCredit() {
        purchasePage.sendingData(getCardInfoWithWhitespace());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца с - "купить в кредит"
    @Test
    @DisplayName("Owner's field -")
    public void shouldOwnerFieldWithHyphenCredit() {
        purchasePage.sendingData(getCardInfoWithHyphen());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца со спецсимволами "Купить в кредит"
    @Test
    @DisplayName("Owner's field with special Symbols Credit")
    public void shouldOwnerFieldSpecialSymbolsCredit() {
        purchasePage.sendingData(getCardInfoWithSpecialSymbols());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Поле владельца с цифрами "Купить в кредит"
    @Test
    @DisplayName("Owner's field with Numbers Credit")
    public void shouldOwnerFieldSpecialNumbersCredit() {
        purchasePage.sendingData(getCardInfoWithNumbers());
        purchasePage.setOwnerFieldErrorHidden();
    }

    //Пустое поле CVC "купить в кредит"
    @Test
    @DisplayName("Empty cvc field Credit")
    public void shouldEmptyCVCFieldCredit() {
        purchasePage.sendingData(getEmptyCvc());
        purchasePage.setCvcFieldErrorHidden();
    }

    @Test
    @DisplayName("2 numbers cvc field Credit")
    public void should2NumbersCVCFieldCredit() {
        purchasePage.sendingData(getCardInfoWithIncompleteCVC());
        purchasePage.setCvcFieldErrorHidden();
    }

    @Test
    @DisplayName("Overdue month of the current year")
    public void shouldOverdueMothCredit() {
        purchasePage.sendingData(getCardInfoWithOverdueMonth());
        purchasePage.setMonthFieldErrorHidden();
    }

}
