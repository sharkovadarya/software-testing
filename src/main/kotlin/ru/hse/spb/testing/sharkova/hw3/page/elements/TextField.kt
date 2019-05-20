package ru.hse.spb.testing.sharkova.hw3.page.elements

import org.openqa.selenium.WebElement

class TextField(private val element: WebElement) {
    fun setText(text: String) {
        element.clear()
        element.sendKeys(text)
    }
}