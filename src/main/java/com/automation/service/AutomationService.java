package com.automation.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AutomationService {
    private static final Logger logger = LoggerFactory.getLogger(AutomationService.class);
    private final WebDriverFactory webDriverFactory;

    public AutomationService(WebDriverFactory webDriverFactory) {
        this.webDriverFactory = webDriverFactory;
    }

    public void performAutomation(String opportunityId, String username, String password) {
        WebDriver webDriver = null;
        try {
            webDriver = webDriverFactory.createWebDriver();
            logger.info("Starting automation for opportunity ID: {}", opportunityId);

            // Rest of your existing code remains the same until the finally block
            String url = "https://ui.boondmanager.com/opportunities/" + opportunityId + "/information";
            logger.info("Navigating to URL: {}", url);
            webDriver.get(url);

            // Wait for login form and fill credentials
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            logger.info("Waiting for the login form to appear...");

            WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#login-field")));
            logger.info("Found username field. Entering username...");
            usernameField.click();
            usernameField.sendKeys(username);

            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ember4")));
            logger.info("Found password field. Entering password...");
            passwordField.click();
            passwordField.sendKeys(password);

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#login > div.bml-login-page > div.bml-login-page_content > div.bml-login_content > div.bml-login_content-connect-form > div.bml-login-credentials_button > button")));
            logger.info("Found submit button. Attempting to log in...");
            submitButton.click();

            // Locate the dropdown container
            logger.info("Waiting for the dropdown container to appear...");
            WebElement dropdownContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#bml-page-content > div.bmc-layout-profile > div.bml-content > div.tab-content > div > div > div:nth-child(3) > div.col-12.col-xl-5 > div:nth-child(1) > div.bmc-layout-profile-section-content.bm-visible > div:nth-child(1) > div:nth-child(1) > div > div.bmc-field-select.required.bm-has-value")));

            // Hover over the dropdown container
            logger.info("Hovering over the dropdown container...");
            Actions actions = new Actions(webDriver);
            actions.moveToElement(dropdownContainer).perform();

            // Wait for the dropdown button to appear and become clickable
            logger.info("Waiting for the dropdown button to become clickable...");

            // Scroll to the dropdown button if necessary
            logger.info("Ensuring the dropdown button is in the viewport...");
            actions.moveToElement(dropdownContainer).perform();

            logger.info("Clicking the dropdown button...");
            dropdownContainer.click();

            // Wait for and click the last option
            logger.info("Waiting for the last dropdown option to become clickable...");
            WebElement lastOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[10]/div/div/ul/li[8]")));
            logger.info("Selecting the last dropdown option...");
            lastOption.click();

            // Wait for and click the save button
            logger.info("Waiting for the save button to become clickable...");
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bml-content-action-button-save > button")));
            logger.info("Clicking the save button...");
            saveButton.click();

            // Wait for 2 seconds to ensure changes are saved
            logger.info("Saving changes. Waiting for 2 seconds...");
            Thread.sleep(2000);

            logger.info("Automation completed successfully for opportunity ID: {}", opportunityId);

        } catch (Exception e) {
            logger.error("Automation failed for opportunity ID: {}. Error: {}", opportunityId, e.getMessage(), e);
            throw new RuntimeException("Automation failed: " + e.getMessage(), e);
        } finally {
            if (webDriver != null) {
                try {
                    webDriver.quit();
                    logger.info("WebDriver closed successfully");
                } catch (Exception e) {
                    logger.error("Error closing WebDriver: {}", e.getMessage());
                }
            }
        }
    }
}