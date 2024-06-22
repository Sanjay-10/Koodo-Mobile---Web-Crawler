package web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.io.FileWriter;
import java.io.File;
import java.util.*;


public class crawl {

    public static void main(String[] args) throws IOException {
        // Set the path for the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");

        // Initialize a new WebDriver instance using ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the Koodo Mobile website
        driver.get("https://www.koodomobile.com/en");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Define the path for the output CSV file and delete it if it already exists
        Path file = Paths.get("D:/MAC-Course/ACC/Assignment1/Koodo.csv");
        Files.deleteIfExists(file);
        System.out.println("File deleted successfully.");

        // Interact with the dropdown menu to navigate to the Phones section
        WebElement DropdownPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".header__menu-item-button")));
        DropdownPhone.click();
        WebElement phone = driver.findElement(By.cssSelector("li[data-test='subMenuItem0']"));
        phone.click();

        // Extract phone details
        List<WebElement> phones = driver.findElements(By.cssSelector(".sc-c64e8282-0.ilWKoL"));

        String filePath = "D:/MAC-Course/ACC/Assignment1/Koodo.csv";
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.append("Phones, Rates, Images\n\n");

            for (WebElement phoneitem : phones) {

                String phonename = phoneitem.findElement(By.cssSelector("[class='css-1rynq56']")).getText().replaceAll("\\s+", " ").trim();
                String model = phoneitem.findElement(By.cssSelector("[data-media='rnmq-265e82d3 rnmq-8ac0ab7a rnmq-2568a35c rnmq-fcb13a46 rnmq-6d6ae753']")).getText();
                String rate = phoneitem.findElement(By.cssSelector(".PriceLockup__PriceTextContainer-components-web__sc-1x6duay-6.gIneyq")).getText().replaceAll("\\s+", " ").trim();
                String imageUrl = phoneitem.findElement(By.cssSelector("picture")).findElement(By.cssSelector("img")).getAttribute("src");

                System.out.println("Phone name : " + phonename + " " + model);
                System.out.println("Rate : " + rate);
                System.out.println("Image URL : " + imageUrl);

                // Write phone details to the CSV file
                fileWriter.write(phonename + " " + model + "," + rate + "," + imageUrl + "\n");
            }
        }

        // Interact with the dropdown menu to navigate to the Plans section
        WebElement DropdownPlan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".header__menu-item-button")));
        DropdownPlan.click();
        WebElement plan = driver.findElement(By.cssSelector("li[data-test='subMenuItem1']"));
        plan.click();


        try (FileWriter fileWriter = new FileWriter(filePath, true)) {


            // Extract plan details
            List<WebElement> plans = driver.findElements(By.cssSelector(".sc-cPiKLX.bWWNcz"));
            fileWriter.append("\n\n\n\n\n\n\nPrices, Plans, Details\n\n");
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            for (WebElement planitem : plans) {
                // Get the plan name
                String prices = planitem.findElement(By.cssSelector(".PriceLockup__PriceLockupContainer-components-web__sc-1x6duay-0.fqMdJw")).getText().replaceAll("\\s+", " ").trim();
                String data = planitem.findElement(By.cssSelector(".sc-eDPEul.sc-eldPxv.ejyAOJ.iZsFvQ")).findElement(By.cssSelector(".css-175oi2r.r-eqz5dr")).getText().replaceAll("\\s+", " ").trim();
                String details = planitem.findElement(By.cssSelector(".Paragraph__StyledParagraph-components-web__sc-1bg9r8p-0.dfAHHh")).getText().replaceAll("\\s+", " ").trim();

                // Write plan details to the CSV file
                if (!prices.equals("") || !data.equals("") || !details.equals("")) {
                    fileWriter.write(prices + "," + data + "," + details + "\n");
                    System.out.println("Price : " + prices);
                    System.out.println("Data : " + data);
                    System.out.println("Details : " + details) ;
                }

            }

            // Close the browser
            driver.quit();
        }
    }
}
