package ru.hse.spb.testing.sharkova.hw3.page.elements

import org.openqa.selenium.WebElement

class Button(private val element: WebElement) {
    fun click() {
        element.click()
    }
}