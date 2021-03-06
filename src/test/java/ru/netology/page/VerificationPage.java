package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidCode(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        $(withText("Ошибка!")).shouldBe(Condition.visible);
    }

    public void blockedCode(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        verifyButton.click();
        verifyButton.click();
        $(withText("Ошибка! Ваш аккаунт заблокирован")).shouldBe(Condition.visible);
    }
}
