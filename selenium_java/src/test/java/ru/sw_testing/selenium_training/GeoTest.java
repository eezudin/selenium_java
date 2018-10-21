package ru.sw_testing.selenium_training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class GeoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void checkCountriesAlphabet( List<WebElement> rows){
        List<String> beforeSort = new ArrayList<>();
        for (WebElement row : rows ) {
            WebElement link = row.findElement(By.cssSelector("a"));
            beforeSort.add(link.getText());
        }
        List<String> afterSort = new ArrayList<>();
        for (WebElement row : rows ) {
            WebElement link = row.findElement(By.cssSelector("a"));
            afterSort.add(link.getText());

        }
        Collections.sort(afterSort);
        assertTrue((beforeSort.equals(afterSort)));
    }

    public void checkGeoZonesAlphabet(){
        List<WebElement> geoZonesIDs = driver.findElements(
                By.cssSelector("table#table-zones tr:not(.header) input[type=hidden][name$='[id]']"));
        List<String> zonesIDs = new ArrayList<>();
        for (WebElement id : geoZonesIDs){
            zonesIDs.add(id.getAttribute("value"));
        }
        List<String> geoZonesBeforeSort = new ArrayList<>();
        for (String id : zonesIDs) {
            geoZonesBeforeSort.add(driver.findElement(
                    By.cssSelector("table#table-zones tr:not(.header) input[type=hidden][name='zones["+id+"][name]']"))
                    .getAttribute("value"));
        }
        List<String> geoZonesAfterSort = new ArrayList<>();
        for (String id : zonesIDs) {
            geoZonesAfterSort.add(driver.findElement(
                    By.cssSelector("table#table-zones tr:not(.header) input[type=hidden][name='zones["+id+"][name]']"))
                    .getAttribute("value"));
        }
        Collections.sort(geoZonesAfterSort);
        assertTrue((geoZonesBeforeSort.equals(geoZonesAfterSort)));
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
    }

    public void checkGeoZonesAlphabet2() {
        List<WebElement> geoZonesSelects = driver.findElements(
                By.cssSelector("select[name$='[zone_code]'"));

        List<String> selectedZonesBeforeSort = new ArrayList<>();
        for (WebElement select : geoZonesSelects) {
            selectedZonesBeforeSort.add(select.findElement(By.cssSelector("option[selected=selected]")).getText());
        }

        List<String> selectedZonesAfterSort = new ArrayList<>();
        for (WebElement select : geoZonesSelects) {
            selectedZonesAfterSort.add(select.findElement(By.cssSelector("option[selected=selected]")).getText());
        }
        Collections.sort(selectedZonesAfterSort);
        assertTrue(selectedZonesBeforeSort.equals(selectedZonesAfterSort));
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    }

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    @Test
    public void geoTest() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> rows = driver.findElements(By.cssSelector("table.dataTable  tr.row"));
        checkCountriesAlphabet(rows);

        List<WebElement> countriesWithGeoZone = new ArrayList<>(); //\s*\d{1,3}\s[A-Z]{2}\D*[1-9]{1,2}
        for (WebElement row : rows){
            if(row.getAttribute("outerText").matches("\\t\\t\\d{1,3}\t[A-Z]{2}\t\\D*\t[1-9]{1,2}\\t")){
                countriesWithGeoZone.add(row);
            }
        }

        List<String> countriesNames = new ArrayList<>();
        for (WebElement country : countriesWithGeoZone){
            WebElement link = country.findElement(By.cssSelector("a"));
            countriesNames.add(link.getText());
            }

        for (String country : countriesNames ){
            List<WebElement> links = driver.findElements(By.cssSelector("a"));
            for (WebElement link : links){
                if(link.getText().equals(country)){
                    link.click();
                    checkGeoZonesAlphabet();
                    break;
                }
            }
        }

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> geoRows = driver.findElements(By.cssSelector("table.dataTable tr.row a:not([title=Edit])"));
        List<String> geoRowsNames = new ArrayList<>();
        for (WebElement row : geoRows) {
            geoRowsNames.add(row.getText());
        }
        for (String country : geoRowsNames ){
            List<WebElement> links = driver.findElements(By.cssSelector("table.dataTable tr.row a:not([title=Edit])"));
            for (WebElement link : links){
                if(link.getText().equals(country)){
                    link.click();
                    checkGeoZonesAlphabet2();
                    break;
                }
            }
        }
    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }
}
