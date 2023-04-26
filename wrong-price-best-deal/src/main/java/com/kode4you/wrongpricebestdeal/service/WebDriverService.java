package com.kode4you.wrongpricebestdeal.service;

import com.kode4you.wrongpricebestdeal.dao.CategoryDao;
import com.kode4you.wrongpricebestdeal.dao.ItemDao;
import com.kode4you.wrongpricebestdeal.domain.dto.ItemDTO;
import com.kode4you.wrongpricebestdeal.domain.dto.ItemPriceDTO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class WebDriverService {

    WebDriver driver;

    WebDriverWait waitLoading;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private CategoryDao categoryDao;

    public WebDriverService(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        waitLoading = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Scheduled(fixedDelay = 11160000)
    public void loadWebSite(){
        driver.get("https://www.amazon.fr");
        waitLoading.until(ExpectedConditions.elementToBeClickable(By.id("sp-cc-accept")));
        driver.findElement(By.id("sp-cc-accept")).click();

        categoryDao.findAll().forEach(categoryDTO -> {
            loopPage(categoryDTO.getName());
        });
    }

    public void loopPage(String searchParam){
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).clear();
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys(searchParam);
        waitLoading.until(ExpectedConditions.textToBePresentInElementValue(By.id("twotabsearchtextbox"), searchParam));
        waitLoading.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button")));
        driver.findElement(By.id("nav-search-submit-button")).click();

        String nbPage = driver.findElement(By.xpath("//span[@class='s-pagination-item s-pagination-disabled']")).getText();
        Integer nbPageInteger = Integer.parseInt(nbPage);

        itemDao.saveItem(getListItemDTO());

        for (int i = 2; i<=nbPageInteger; i++){
            waitLoading.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='Accéder à la page " + i + "']")));
            driver.findElement(By.xpath("//a[@aria-label='Accéder à la page " + i + "']")).click();
            waitLoading.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[data-component-type='s-search-result']")));
            itemDao.saveItem(getListItemDTO());
        }
    }

    public List<ItemDTO> getListItemDTO(){
        List<WebElement> listOfElements = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']"));
        List<ItemDTO> itemDTOList = new ArrayList<>();
        listOfElements.forEach(webElement -> {
            ItemDTO itemDTO = itemDao.findItemByAsin(webElement.getAttribute("data-asin"));
            itemDTO = itemDTO == null ? new ItemDTO() : itemDTO;
            ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
            List<ItemPriceDTO> itemPriceDTOList = new ArrayList<>();
            itemDTO.setName(webElement.findElement(By.xpath(".//span[@class='a-size-base-plus a-color-base a-text-normal']")).getText());
            itemDTO.setAsin(webElement.getAttribute("data-asin"));
            boolean t = webElement.findElements(By.xpath(".//span[@class='a-price-whole']")).size() != 0;
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number = null;
            try {
                if(t){
                    number = format.parse(webElement.findElement(By.xpath(".//span[@class='a-price-whole']")).getText());
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            itemPriceDTO.setPrice(number != null ? number.doubleValue() : null);
            itemPriceDTOList.add(itemPriceDTO);
            WebElement elementImage = webElement.findElement(By.xpath(".//img[@class='s-image']"));
            itemDTO.setImageLink(elementImage.getAttribute("src"));
            itemDTO.setItemPriceList(itemPriceDTOList);
            if(itemDTO.getCreatedDate() == null){
                itemDTO.setCreatedDate(LocalDateTime.now());
            }
            itemDTO.setUpdatedDate(LocalDateTime.now());
            itemDTOList.add(itemDTO);
        });

        return itemDTOList;
    }
}
