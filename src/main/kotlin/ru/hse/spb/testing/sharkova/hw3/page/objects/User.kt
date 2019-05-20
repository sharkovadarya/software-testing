package ru.hse.spb.testing.sharkova.hw3.page.objects

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class User(parent: WebElement) {
    val login: WebElement
    val fullName: String
    val contactInfo: String
    val groups: List<WebElement>
    val actions: List<WebElement>

    init {
        val tableRows = parent.findElements(By.tagName("td"))
        login = tableRows[0].findElement(By.tagName("a"))
        fullName = tableRows[1].findElement(By.tagName("div")).text
        contactInfo = tableRows[2].findElement(By.tagName("div")).text
        groups = tableRows[3].findElements(By.tagName("a"))
        actions = tableRows[5].findElements(By.tagName("a"))
    }
}