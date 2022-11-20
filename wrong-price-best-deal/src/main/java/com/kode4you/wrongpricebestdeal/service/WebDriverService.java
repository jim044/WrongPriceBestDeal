package com.kode4you.wrongpricebestdeal.service;

import com.kode4you.wrongpricebestdeal.domain.dto.Item;
import com.kode4you.wrongpricebestdeal.domain.dto.ItemPrice;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WebDriverService {

    WebDriver driver;

    WebDriverWait waitLoading;

    public WebDriverService(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        waitLoading = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Scheduled(fixedDelay = 60000)
    public void loadWebSite(){
        driver.get("https://www.amazon.fr");
        waitLoading.until(ExpectedConditions.elementToBeClickable(By.id("sp-cc-accept")));
        driver.findElement(By.id("sp-cc-accept")).click();
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("ordinateur");
        waitLoading.until(ExpectedConditions.textToBePresentInElementValue(By.id("twotabsearchtextbox"), "ordinateur"));
        waitLoading.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button")));
        driver.findElement(By.id("nav-search-submit-button")).click();

        List<WebElement> listOfElements = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']"));
        List<Item> itemList = new ArrayList<>();
        listOfElements.forEach(webElement -> {
            Item item = new Item();
            ItemPrice itemPrice = new ItemPrice();
            item.setName(webElement.findElement(By.xpath(".//span[@class='a-size-base-plus a-color-base a-text-normal']")).getText());
            boolean t = driver.findElements(By.xpath(".//span[@class='a-price-whole']")).size() != 0;
            itemPrice.setPrice(t ? webElement.findElement(By.xpath(".//span[@class='a-price-whole']")).getText() : null);
            WebElement elementImage = webElement.findElement(By.xpath(".//img[@class='s-image']"));
            item.setImageLink(elementImage.getAttribute("src"));
            item.setItemPrice(itemPrice);
            itemList.add(item);
        });
    }
}
