import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class n11test {
    public  WebDriver driver;
    public String productName;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    @Before
    public void setupDriver(){
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Ramazan\\IdeaProjects\\test_automation_n11\\geckodriver.exe");
        driver = new FirefoxDriver();
        String url = "https://www.n11.com/";
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div[6]/div/div[2]/span")).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(By.className("closeBtn")).click();
        System.out.println("n11 e hoşgeldiniz...");

    }
    @Test
    public void TestHome() {

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(By.className("closeBtn")).click();
        driver.findElement(By.className("btnSignIn")).click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        String MainWindow = driver.getWindowHandle();
        driver.findElement(By.className("btnLogin")).click();

        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        while (i1.hasNext()) {
            String ChildWindow = i1.next();

            if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                WebElement mailbox = driver.findElement(By.id("email"));
                mailbox.click();
                mailbox.sendKeys("testotomasyon@yandex.com.tr");
                WebElement password = driver.findElement(By.id("pass"));
                password.click();
                password.sendKeys("Razor1234");
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                driver.findElement(By.id("loginbutton")).click();
            }
        }
        System.out.println("Facebook hesabı ile giriş yapıldı.");
        driver.switchTo().window(MainWindow);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(By.id("searchData")).click();
        driver.findElement(By.id("searchData")).sendKeys("samsung");
        driver.findElement(By.className("searchBtn")).click();
        driver.findElement(By.xpath("(//div[@class='pagination'])/a[contains(text(),'2')]")).click();
        if (driver.getCurrentUrl().contains("pg=2")) {
            System.out.println("Arama işleminde ikinci sayfa açıldı.");

        } else {
            System.out.println("Arama işleminde ikinci sayfa açılamadı.");
        }

        productName = driver.findElement(By.xpath("//div[@id='view']/ul/li[3]//h3[@class='productName bold']")).getText();
        System.out.println("seçilen 3. ürünün ismi: " + productName);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div[@id='view']/ul/li[3]//span[contains(@class,'followBtn')]")).click();

        WebElement myAccount = driver.findElement(By.xpath("//a[text()='Hesabım']"));
        Actions builder = new Actions(driver);
        builder.moveToElement(myAccount).build().perform();
        driver.findElement(By.xpath("//a[text()='İstek Listem / Favorilerim']")).click();

        String favouriteProductName = driver.findElement(By.xpath("//h3[@class='productName bold']")).getText();
        System.out.println(favouriteProductName);
        if (favouriteProductName.equals(productName)) {
            System.out.println("Ürün favoriye eklendi");
        } else {
            System.out.println("Ürün favoriye eklenemedi.");
        }
        driver.findElement(By.xpath("//span[@class='deleteProFromFavorites']")).click();

        WebElement checkProductDelete = driver.findElement(By.xpath("//span[text()='Ürününüz listeden silindi.']"));
        if (checkProductDelete != null) {
            System.out.println("Favori listesinde ürün bulunmamaktadır");
        } else {
            System.out.println("İstediğiniz ürün silinemedi");
        }

    }
    @After
    public void quitDriver(){
        if (driver != null)
            driver.quit();
    }
}
