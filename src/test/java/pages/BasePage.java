package pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BasePage {

    public BasePage removeBanner() {
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        return this;
    }
}
