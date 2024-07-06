package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.steps.BookStoreSteps;
import com.github.javafaker.Faker;

@DisplayName("Tests for Book Store Application")
public class BookStoreTests extends TestBase {

    private final ProfilePage profilePage = new ProfilePage();

    @Test
    @DisplayName("Delete a book from user profile")
    void deleteBookFromProfileBooksListTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        BookStoreSteps step = new BookStoreSteps();
        Faker faker = new Faker();
        String userName = faker.name().username();
        String password = faker.internet().password(8, 10, true, true, true);
        String isbn = "9781449331818";
        step.userRegistrationApi(userName, password);
        step.getTokenApi(userName, password);
        LoginResponseModel loginResponse = step.loginUserApi(userName, password);
        step.addBookToProfileApi(loginResponse, isbn);
        step.setAuthCookie(loginResponse);
        profilePage.openProfilePage();
        profilePage.checkBookIsInProfile(isbn);
        profilePage.deleteBook();
        profilePage.checkBooksListIsEmpty();
    }
}
