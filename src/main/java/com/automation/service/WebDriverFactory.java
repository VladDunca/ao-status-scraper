package com.automation.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class WebDriverFactory {
    private final ChromeOptions chromeOptions;

    public WebDriverFactory(ChromeOptions chromeOptions) {
        this.chromeOptions = chromeOptions;
    }

    public WebDriver createWebDriver() {
        return new ChromeDriver(chromeOptions);
    }
}