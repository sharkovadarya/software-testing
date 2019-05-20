package ru.hse.spb.testing.sharkova.hw3

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import ru.hse.spb.testing.sharkova.hw3.page.objects.LoginPageObject
import ru.hse.spb.testing.sharkova.hw3.page.objects.UsersPageObject

class YoutrackUsersTest {
    private val host = "http://localhost:8080"
    private lateinit var driver: WebDriver

    @Before
    fun setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver")
        driver = ChromeDriver()
    }

    @After
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun createOneUserTest() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "singleuser"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        usersPage.deleteUser(username)
    }

    @Test
    fun createMultipleUsersTest() {
        login()
        val usersPage = UsersPageObject(driver, host)
        for (i in 0..5) {
            val username = "user$i"
            createUser(usersPage, username)
            assertTrue(usersContain(usersPage, username))
        }
        for (i in 0..5) {
            val username = "user$i"
            deleteUser(usersPage, "user$i")
            assertFalse(usersContain(usersPage, username))
        }
    }

    @Test
    fun cancelUserCreation() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val createUserDialog = usersPage.createUser()
        val username = "username"
        createUserDialog.setLogin(username)
        createUserDialog.setPassword("pass")
        createUserDialog.setPasswordConfirmation("pass")
        createUserDialog.cancel()
        usersPage.loadUsersPage()
        assertFalse(usersContain(usersPage, username))
    }

    @Test
    fun testAlphanumericLogin() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "ltrs7454dgts892"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        usersPage.deleteUser(username)
    }

    @Test
    fun testLoginStartsWithDigit() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "1username"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        usersPage.deleteUser(username)
    }

    @Test
    fun testNumericLogin() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "654893"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        usersPage.deleteUser(username)
    }

    @Test
    fun testLoginStartsWithUnderscore() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "__1username"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        usersPage.deleteUser(username)
    }

    @Test
    fun testEmptyLogin() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = ""
        createUser(usersPage, username)
        assertFalse(usersContain(usersPage, username))
    }

    @Test
    fun testLoginWithSpace() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "user name"
        createUser(usersPage, username)
        assertFalse(usersContain(usersPage, username))
    }

    @Test
    fun testLoginCyrillic() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "пользователь"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        usersPage.deleteUser(username)
    }

    @Test
    fun testDuplicateLogin() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "username"
        createUser(usersPage, username)
        assertTrue(usersContain(usersPage, username))
        createUser(usersPage, username)
        val users = usersPage.getUsers()
        assertEquals(1, users.filter { it.fullName == username }.size)
        usersPage.deleteUser(username)
    }

    @Test
    fun testMismatchedPasswordAndConfirmation() {
        login()
        val usersPage = UsersPageObject(driver, host)
        val username = "username"
        createUser(usersPage, username, "password", "passwor")
        assertFalse(usersContain(usersPage, username))
    }

    private fun login() {
        LoginPageObject(driver, host).login("root", "root")
    }

    private fun createUser(
        usersPage: UsersPageObject,
        username: String,
        password: String = "password",
        passwordConfirmation: String = "password"
    ) {
        val dialog = usersPage.createUser()
        dialog.setLogin(username)
        dialog.setPassword(password)
        dialog.setPasswordConfirmation(passwordConfirmation)
        dialog.createUser()
        Thread.sleep(200)
        usersPage.loadUsersPage()
    }

    private fun deleteUser(usersPage: UsersPageObject, username: String) {
        usersPage.deleteUser(username)
        Thread.sleep(200)
        usersPage.loadUsersPage()
    }

    private fun usersContain(usersPage: UsersPageObject, username: String) =
        usersPage.getUsers().map { it.fullName }.contains(username)
}
