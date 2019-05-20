package ru.hse.spb.testing.sharkova.hw3.page.objects

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import ru.hse.spb.testing.sharkova.hw3.page.elements.Button
import ru.hse.spb.testing.sharkova.hw3.page.elements.TextField


class UsersPageObject(driver: WebDriver, host: String) : PageObject(driver, host) {
    init {
        loadUsersPage()
    }

    fun loadUsersPage() {
        driver.get("$host/users")
    }

    fun getUsers(): List<User> {
        val users = mutableListOf<User>()
        val totalLinks = findElementById("id_l.U.usersList.usersList").findElements(By.tagName("tr"))
        for (element in totalLinks.drop(1)) {
            users.add(User(element))
        }

        return users
    }

    fun createUser(): CreateUserDialog {
        val createUserButton = findElementById("id_l.U.createNewUser")
        createUserButton.click()
        val login = TextField(findElementById("id_l.U.cr.login"))
        val password = TextField(findElementById("id_l.U.cr.password"))
        val passwordConfirmation = TextField(findElementById("id_l.U.cr.confirmPassword"))
        val okButton = Button(findElementById("id_l.U.cr.createUserOk"))
        val cancelButton = Button(findElementById("id_l.U.cr.createUserCancel"))
        return CreateUserDialog(okButton, cancelButton, login, password, passwordConfirmation)
    }

    fun deleteUser(username: String) {
        val users = getUsers()
        val user = users.filter { it.fullName == username }[0]
        val deleteAction = user.actions.find { it.getAttribute("cn") == "l.U.usersList.deleteUser" }
        deleteAction?.click()
        driver.switchTo().alert().accept()
    }

    class CreateUserDialog(
        private val okButton: Button,
        private val cancelButton: Button,
        private val login: TextField,
        private val password: TextField,
        private val passwordConfirmation: TextField
    ) {
        fun setLogin(login: String) = this.login.setText(login)

        fun setPassword(password: String) = this.password.setText(password)

        fun setPasswordConfirmation(passwordConfirmation: String) =
            this.passwordConfirmation.setText(passwordConfirmation)

        fun createUser() = okButton.click()

        fun cancel() = cancelButton.click()
    }
}