package com.company;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class Autotest {
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws InterruptedException{
// Create a new instance of the Firefox driver
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver;
        ChromeOptions chromeOptions= new ChromeOptions();
        driver=new ChromeDriver(chromeOptions);;
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");

        String url="http://portal.aait.edu.et";
        driver.get(url);
        var username= "ATR/5410/09";
        var password= "7330";
        driver.findElement(By.xpath("//input[@name='UserName']")).clear();
        driver.findElement(By.xpath("//input[@name='Password']")).clear();
        Thread.sleep(1000);

        driver.findElement(By.name("UserName")).sendKeys(username);
        System.out.println("Username entered");
        Thread.sleep(1000);

        driver.findElement(By.name("Password")).sendKeys(password);
        Thread.sleep(1000);
        System.out.println("Password entered");

        driver.findElement(By.name("Password")).sendKeys(Keys.ENTER);
        System.out.println("Login clicked");

        System.out.println("Successfully Logged In");

        driver.navigate().to("https://portal.aait.edu.et/Grade/GradeReport");
        Thread.sleep(2000);
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Grade Report.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.addAuthor("Abenezer Kindie");
        document.addTitle("Grade Report for ATR/5410/09");

        document.open();
        Paragraph paragraph=new Paragraph();
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-bordered table-striped table-hover']//tr"));
// for every line, store both columns
        for (WebElement row : rows) {
            WebElement key = row.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/div/div/table/tbody[1]"));
            System.out.println(key.getText());
            paragraph.add(key.getText());
            try {
                document.add(paragraph);
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            break;
            }
        document.close();
        System.out.println("Grade Report Generated (PDF) Find it in your working root directory");
    driver.close();
    }

}