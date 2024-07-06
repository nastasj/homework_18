package tests.steps;

import io.qameta.allure.Step;
import models.*;
import org.openqa.selenium.Cookie;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.*;

public class BookStoreSteps {

    @Step("Create a new user")
    public void userRegistrationApi(String userName, String password) {
        RegistrationLoginRequestModel userData = new RegistrationLoginRequestModel();
        userData.setUserName(userName);
        userData.setPassword(password);
        given(registrationAndLoginRequestSpec)
                .body(userData)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(statusCode201Spec)
                .extract().as(RegistrationResponseModel.class);
    }

    @Step("Get authorization token")
    public void getTokenApi(String userName, String password) {
        RegistrationLoginRequestModel userData = new RegistrationLoginRequestModel();
        userData.setUserName(userName);
        userData.setPassword(password);
        given(registrationAndLoginRequestSpec)
                .body(userData)
                .when()
                .post("Account/v1/GenerateToken")
                .then()
                .spec(statusCode200Spec)
                .extract().response();
    }

    @Step("Login a user")
    public LoginResponseModel loginUserApi(String userName, String password) {
        RegistrationLoginRequestModel userData = new RegistrationLoginRequestModel();
        userData.setUserName(userName);
        userData.setPassword(password);
        return (given(registrationAndLoginRequestSpec)
                .body(userData)
                .when()
                .post("Account/v1/Login")
                .then()
                .spec(statusCode200Spec)
                .extract().as(LoginResponseModel.class));
    }

    @Step("Add a book to user profile")
    public void addBookToProfileApi(LoginResponseModel authResponse, String isbn) {
        AddBookRequestModel bookData = new AddBookRequestModel();
        bookData.setUserId(authResponse.getUserId());
        AddBookRequestModel.Isbn isbns = new AddBookRequestModel.Isbn();
        isbns.setIsbn(isbn);
        bookData.setCollectionOfIsbns(List.of(isbns));
        given(authorizedRequestSpec(authResponse.getToken()))
                .body(bookData)
                .when()
                .post("BookStore/v1/Books")
                .then()
                .spec(statusCode201Spec);
    }

    @Step("Set authorization cookies")
    public void setAuthCookie(LoginResponseModel authResponse) {
        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
    }
}
