package ru.hse.spb.testing.sharkova.hw3.page.objects

import org.openqa.selenium.WebDriver
import ru.hse.spb.testing.sharkova.hw3.page.elements.Button
import ru.hse.spb.testing.sharkova.hw3.page.elements.TextField

class LoginPageObject(driver: WebDriver, host: String) : PageObject(driver, host) {
    private val loginButton: Button
    private val loginTextField: TextField
    private val passwordTextField: TextField

    init {
        driver.get("$host/login")
        loginButton = Button(findElementById("id_l.L.loginButton"))
        loginTextField = TextField(findElementById("id_l.L.login"))
        passwordTextField = TextField(findElementById("id_l.L.password"))
    }

    fun login(login: String, password: String) {
        loginTextField.setText(login)
        passwordTextField.setText(password)
        loginButton.click()
    }
}