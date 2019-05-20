package ru.hse.spb.testing.sharkova.hw3.page.objects

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.FluentWait
import java.util.concurrent.TimeUnit

open class PageObject(protected val driver: WebDriver, protected val host: String) {
    fun findElementById(id: String): WebElement {
        val wait = FluentWait(driver).withTimeout(10, TimeUnit.SECONDS)
            .pollingEvery(100, TimeUnit.MILLISECONDS)
            .ignoring(NoSuchElementException::class.java)
        return wait.until { driver.findElement(By.id(id)) }
    }
}