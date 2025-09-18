package data;

import com.github.javafaker.Faker;

public class TestData {

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static Faker fakeData = new Faker();
    public static final String RANDOM_EMAIL = fakeData.name().firstName() + "." + System.currentTimeMillis() + "@yandex.ru";
    public static final String RANDOM_PASSWORD = "1234" + System.currentTimeMillis();
    public static final String RANDOM_NAME = fakeData.name().firstName();
    public static final String hashBun = "61c0c5a71d1f82001bdaaa6d";
    public static final String hashFilling = "61c0c5a71d1f82001bdaaa7a";
    public static final String falseHashBun = "zzz0c5a71d1f82001bdaazzz";

    public static String getRandomEmail() {
        return fakeData.name().firstName() + "." + System.currentTimeMillis() + "@yandex.ru";
    }

    public static String getRandomPassword() {
        return "1234" + System.currentTimeMillis();
    }
}
