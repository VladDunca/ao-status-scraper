package com.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class WebDriverConfig {

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Required for Heroku
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-logging");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        // Set binary location for Heroku
        String binaryPath = System.getenv("GOOGLE_CHROME_BIN");
        if (binaryPath != null) {
            options.setBinary(binaryPath);
        }

        return options;
    }

    static {
        WebDriverManager.chromedriver().setup();
    }
}