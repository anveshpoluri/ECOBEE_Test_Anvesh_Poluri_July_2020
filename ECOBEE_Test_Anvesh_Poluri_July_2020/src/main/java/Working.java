import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Working
{
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup(); // Setting up ChromeDriver from WebDriver Manager
        WebDriver driver = new ChromeDriver(); // Creating a driver to run Chrome browser
        driver.get("http://slashdot.org/"); // Opening Website URL

        // Through driver find the element that is 'Section of body' that has all the articles
        // Loading the found WebElement into website_body_that_has_articles
        WebElement website_body_that_has_articles = driver.findElement(By.xpath("//article[@id='thisday']"));
        int sizeloop = website_body_that_has_articles.findElements(By.tagName("tr")).size();
        List<WebElement> articels =  website_body_that_has_articles.findElements(By.tagName("tr"));
        int totalcomments = 0;
        for (int i=0; i<sizeloop;i++)
        {

            WebElement each_article = articels.get(i);
            String te_string = "//tr"+"["+(i+1)+"]";
            String text = each_article.findElement(By.xpath(te_string+"//td[3]//span[1]//span[2]")).getText();
            int number = Integer.parseInt(text);
            totalcomments = totalcomments+number;

        }
        System.out.println(totalcomments);

    }
}
