package crawler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;

import org.bson.types.Binary;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import com.example.benkyo.word.*;

public class Crawler {

    private String baseURL = "https://jisho.org/word/"; 

    public Crawler() {
    }

    public Word crawler(String word) {
        try {
            System.setProperty("webdriver.edge.driver", "benkyo/src/crawler/crawler/msedgedriver.exe");
            
            WebDriver driver = new EdgeDriver();
            driver.get(this.baseURL + word);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'meaning-meaning')]")));
            String meaning =  driver.findElement(By.xpath("//span[contains(@class, 'meaning-meaning')]")).getText();
            WebElement reading = driver.findElement(By.xpath("//div[contains(@class, 'concept_light-representation')]"));
            String kanji = reading.findElements(By.xpath("*")).get(1).getText();
            String hiragana = reading.findElements(By.xpath("*")).get(0).getText();
            byte[] audio = null;
            try {
                List<WebElement> audioLst = driver.findElements(By.xpath("//audio"));
                if (audioLst.size() > 0) {
                    String audioSrc = audioLst.get(0)
                        .findElement(By.xpath("*"))
                        .getAttribute("src");
                    URI uri = new URI(audioSrc);
                    BufferedInputStream in = new BufferedInputStream(uri.toURL().openStream());
                    audio = in.readAllBytes();
                    in.close();
                }
            } catch (NoSuchElementException e) {
                System.out.println("Audio not found");
            }
            Word word2;
            if (audio == null) {
                word2 = new Word(word, kanji, hiragana, meaning);
            }
            else {
                word2 = new Word(word, kanji, hiragana, meaning, new Binary(audio));
            }

            driver.quit();
            return word2;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        Word word = crawler.crawler("家");
        System.out.println(word);
    }
}