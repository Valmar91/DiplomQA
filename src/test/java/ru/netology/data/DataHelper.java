package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataHelper {
    private static final String validCard = "4444 4444 4444 4441";
    private static final String invalidCard = "4444 4444 4444 4442";
    private static final Faker faker = new Faker();

    private DataHelper() {
    }

    //1
    //public static String validMonth() {
    //    return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    //}

    public static String Month(int n) {
        return LocalDate.now().plusMonths(n).format(DateTimeFormatter.ofPattern("MM"));
    }

    //2
    //public static String validYear() {
    //    return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    //}

    public static String randomOwner() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String randomCVC() {
        Random random = new Random();
        return Integer.toString(random.nextInt(10)) +
                Integer.toString(random.nextInt(10)) +
                Integer.toString(random.nextInt(10));
    }

    public static String randomCVC2() {
        Random random = new Random();
        return Integer.toString(random.nextInt(10)) +
                Integer.toString(random.nextInt(10));
    }

    //неполный номер карты
    public static String incompleteCardNumber() {
        return "4444 4444 4444 444";
    }

    //месяц 00

    private static String getLowerMonth() {
        return "00";
    }

    private static String getGreaterMonth() {
        return "13";
    }

    //3
    //public static String getOverdueYear() {
    //    return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    //}

    //4
    //public static String getFutureYear() {
    //    return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    //}

    //5
    //private static String getNextYear() {    //Следующий год, текущий год + 1 год:
    //    return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    //}

    //Год
    private static String Year(int n) {
        return LocalDate.now().plusYears(n).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getWhitespace() {
        return " ";
    }

    public static String getHyphen() {
        return "-";
    }

    private static String getSpecialSymbols() {
        Random random = new Random();
        final String[] specialSymbols = new String[]{"!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+",
                ",", ".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"};
        var fistSymbol = specialSymbols[random.nextInt(31)];
        var secondSymbol = specialSymbols[random.nextInt(31)];
        return fistSymbol + secondSymbol;
    }

    public static CardInfo getCardInfo() {
        return new CardInfo(validCard, Month(0), Year(0), randomOwner(), randomCVC());
    }

    public static CardInfo getInvalidCardInfo() {
        return new CardInfo(invalidCard, Month(0), Year(0), randomOwner(), randomCVC());
    }

    public static CardInfo getCardInfoWithIncompliteCardNumber() {
        return new CardInfo(incompleteCardNumber(), Month(0), Year(0), randomOwner(), randomCVC());
    }

    public static CardInfo getCardInfoWith00Month() {
        return new CardInfo(validCard, getLowerMonth(), Year(1), randomOwner(), randomCVC());
    }

    public static CardInfo getCardInfoWith13Month() {
        return new CardInfo(validCard, getGreaterMonth(), Year(0), randomOwner(), randomCVC());
    }

    //просроченный год
    public static CardInfo getCardInfoWithOverdueYear() {
        return new CardInfo(validCard, Month(0), Year(-1), randomOwner(), randomCVC());
    }

    //Год за пределами окончания
    public static CardInfo getCardInfoWithFutureYear() {
        return new CardInfo(validCard, Month(0), Year(6), randomOwner(), randomCVC());
    }

    //пробел в поле владельца
    public static CardInfo getCardInfoWithWhitespace() {
        return new CardInfo(validCard, Month(0), Year(0), getWhitespace(), randomCVC());
    }

    //- в поле владельца
    public static CardInfo getCardInfoWithHyphen() {
        return new CardInfo(validCard, Month(0), Year(0), getHyphen(), randomCVC());
    }

    //спецсимволы в поле владельца
    public static CardInfo getCardInfoWithSpecialSymbols() {
        return new CardInfo(validCard, Month(0), Year(0), getSpecialSymbols(), randomCVC());
    }

    //цифры в поле владельца
    public static CardInfo getCardInfoWithNumbers() {
        return new CardInfo(validCard, Month(0), Year(0), randomCVC2(), randomCVC());
    }

    //неполный CVC
    public static CardInfo getCardInfoWithIncompleteCVC() {
        return new CardInfo(validCard, Month(0), Year(0), randomOwner(), randomCVC2());
    }

    //private static String getOverdueMonth() {
    //    return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    //}

    public static CardInfo getCardInfoWithOverdueMonth() {
        return new CardInfo(validCard, Month(-1), Year(0), randomOwner(), randomCVC());
    }

    public static CardInfo getEmptyAllFields() {
        return new CardInfo("", "", "", "", "");
    }

    public static CardInfo getEmptyNumberCardInfo() {
        return new CardInfo("", Month(0), Year(0), randomOwner(), randomCVC());
    }
    public static CardInfo getEmptyMonth() {
        return new CardInfo(validCard, "", Year(1), randomOwner(), randomCVC());
    }
    public static CardInfo getEmptyYear() {
        return new CardInfo(validCard, Month(0), "", randomOwner(), randomCVC());
    }
    public static CardInfo getEmptyOwner() {
        return new CardInfo(validCard, Month(0), Year(0), "", randomCVC());
    }

    public static CardInfo getEmptyCvc() {
        return new CardInfo(validCard, Month(0), Year(0), randomOwner(), "");
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String owner;
        String cvc;
    }

    @Data
    @RequiredArgsConstructor
    public static class PaymentSpreadsheet {
        String id;
        String amount;
        String created;
        String status;
        String transaction_id;
    }

    @Data
    @RequiredArgsConstructor
    public static class OrderSpreadsheet {
        String id;
        String created;
        String credit_id;
        String payment_id;
    }

    @Data
    @RequiredArgsConstructor
    public static class CreditSpreadsheet {
        String id;
        String bank_id;
        String created;
        String status;
    }
}
