import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Ecobee_TASK_2
    {
        /**
         * This code opens http://slashdot.org/ on Chrome Browser
         * Counts Total Number of Articles in Green and prints the Number
         * Saves all Unique Icons on the page and counts how many times each icon is used on page
         * Prints each unique Icon Name and total number of times it is used on page
         * Prints Daily Poll Name
         * Selects a Random option in the poll and prints its details and Submit's the poll
         * Prints total number of people that voted for the Same option as we have choose
         * Whole code is written in one Main function block for ease of running, simply press Run command
         * Using java version "1.8.0_251"
         * Using webdrivermanager 4.0.0 through POM.XML
         * Using selenium-java 3.141.59 through POM.XML
         * Code is written in IntelliJ IDEA IntelliJ IDEA 2020.1.2 (Community Edition)
         * Written By Anvesh Poluri, Email: anvesh.poluri@gmail.com, Date: July_15_2020
         * I could also use TestNG to write the same code as test cases without main() method, given enough time
         */

        public static void main(String[] args) throws InterruptedException
        {
            WebDriverManager.chromedriver().setup(); // Setting up ChromeDriver from WebDriver Manager
            WebDriver driver = new ChromeDriver(); // Creating a driver to run Chrome browser
            driver.get("http://slashdot.org/"); // Opening Website URL

            // Through driver find the element that is 'Section of body' that has all the articles
            // Loading the found WebElement into website_body_that_has_articles
            WebElement website_body_that_has_articles = driver.findElement(By.xpath("//div[@id='firehose']"));

            //Inside the Articles Section we have to find a right TagName that represents each individual article
            // Right TagName = article
            // Loading the TagName into a String TagToWorkWith
            String TagToWorkWith = "article"; // here simply change the tag name on which you want to work

            /*
             Finding the Total Number of Articles inside that Section of Articles
             With the TagToWorkWith we find all WebElements, This would have easily given our total number of articles
             But While running automated Code Noticed a Strange occurrence, Inside our Section of Body-
             I found a Article in Brown So we have to Eliminate it and Count the rest of articles
             The Following Code does this work
             */

            // Finding the total number of articles inside website_body_that_has_articles using TagToWorkWith
            // And Loading the size total number of article into a articles_size
            int articles_size = website_body_that_has_articles.findElements(By.tagName(TagToWorkWith)).size();
            // Creating another Int that has articles_size, so we can work with this without touching original articles_size
            int total_no_of_articles = articles_size;
            // Creating a TreeMap to load all of out unique icon names and total number of times they are used
            // unique_icon_image_map = { 'key'= 'Unique icon name' , 'Value' = 'Number of times it is used'
            Map<String, Integer> unique_icon_image_map = new TreeMap<>();

            // Creating a List of all WebElements from website_body_that_has_articles that has TagToWorkWith
            List<WebElement> total_articles_list =  website_body_that_has_articles.findElements(By.tagName(TagToWorkWith));

            /*
             Inside this loop we check for brown articles and remove them from our count of total_no_of_articles
             After that find Unique icon name on each article and store it inside a map as key
             And store total number of times each icon used as a value inside the map
             */

            for (int i = 0; i < articles_size; i++)
            {
                // Using for loop we will cycle through each article from the list of total_articles_list
                // Loading each articles as a webElement
                WebElement each_article = total_articles_list.get(i);
                /*
                 Since All Brown articles are Sponsored Content
                 We are finding whether the current choosen each_article has any information related to Sponsored content
                 Once we find it we reducing the size of total_no_of_articles to reflect our total articles in green
                 */
                // Creating a Boolean called Sponsored
                // We are finding all Elements inside each_article that has class name ntv-sponsored-disclaimer
                // Since we are using findElements we can get size of it and return True or False based on whether
                // Current Choosen article has any Sponsored Content or not
                Boolean Sponsored = each_article.findElements(By.className("ntv-sponsored-disclaimer")).size() > 0;
                if (Sponsored)
                {
                    //reducing the size of total_no_of_articles to reflect our total articles in green
                    total_no_of_articles = total_no_of_articles-1;
                }
                // IF Sponsored content is not present then we will execute the following
                else
                {
                    // Finding the Unique icon name from each_article
                    // Since our Unique icon is an image we are finding the it using tagName "img"
                    String current_unique_icon_name = each_article.findElement(By.tagName("img")).getAttribute("title");
                    /*
                     Once we find the unique icon for every article we have to store it inside a Map as a Key
                     IF an unique icon is already found previously and loaded into our unique_icon_image_map
                     we have to find out and increase the count of its value to represent total number of times its used
                     IF it is first time we are find a unique icon then we have to store it inside unique_icon_image_map,
                     with its Key and value as '1'
                     */
                    if (unique_icon_image_map.containsKey(current_unique_icon_name))// Checking for Existence of already found unique icons
                    {
                        // Getting the Value of current_unique_icon_name
                        int temp = unique_icon_image_map.get(current_unique_icon_name);
                        temp++;// Increasing the value of current_unique_icon_name appearance on articles
                        // Over Writing inside map with current_unique_icon_name and its value
                        unique_icon_image_map.put(current_unique_icon_name, temp);

                    }
                    // IF it is first time we found a unique image
                    else
                    {
                        // Loading current_unique_icon_name and its value into map
                        unique_icon_image_map.put(current_unique_icon_name, 1);

                    }

                }
            }
            System.out.println("/****************************************/");// For Format setting
            System.out.println();// For Format setting
            // Printing Total Number of Articles in green
            System.out.println("Total Number of articles on the page that are highlighted in green: "+total_no_of_articles);
            System.out.println();// For Format setting
            // Printing Total Unique Icons and number of times they appeared on articles
            System.out.println("The list of unique icons used on article titles are: ");
            //Iterating through our unique_icon_image_map to print our results
            for (Map.Entry<String, Integer> e : unique_icon_image_map.entrySet())
            {
                if (e.getValue()>1)
                {
                    // If our Unique icon is used only once then this line will print
                    System.out.println("The unique icon '"+e.getKey()+"' is used "+e.getValue()+" times on article titles" );
                }
                else
                {
                    // If our Unique icon is used more than once then this line will print
                    System.out.println("The unique icon '"+e.getKey()+"' is used "+e.getValue()+" time on article titles" );
                }
            }

            // Through driver find the element that is 'daily_poll_section' and create a WebElement
            WebElement daily_poll_section = driver.findElement(By.xpath("//section[@id='poll-content']"));

            // Finding the Name of Today's Daily Poll
            String daily_poll_name = daily_poll_section.findElement(By.xpath("/html[1]/body[1]/section[1]/div[4]/aside" +
                    "[1]/article[3]/section[1]/div[1]/h3[1]")).getText();
            System.out.println();// For Format setting
            // Printing the Name of Today's Daily Poll
            System.out.println("Today's daily poll is About: "+daily_poll_name);
            // Finding out how many options we have to choose for our daily poll
            // If in case there are more than four options this code will help us
            // Loading the Size of our options into daily_poll_options_size
            int daily_poll_options_size = daily_poll_section.findElements(By.tagName("label")).size();
            // Creating a List of WebElements from our daily_poll_section for daily_poll_each_option
            List<WebElement> daily_poll_each_option =  daily_poll_section.findElements(By.tagName("label"));

            /*
             Since it was mentioned in the task that voting for daily poll options should be random,
             We are generating a Random number with in the size of daily_poll_options_size,
             and using it to vote for poll and gathering rest of its details
             */
            // Declaring a Random Variable
            Random rand = new Random();
            Thread.sleep(1000);// Creating a Sleep to allow enough time to load website
            // Creating a Random Number with in the size of daily_poll_options_size
            int random_number_for_poll_options = rand.nextInt(daily_poll_options_size);

            // Printing the Content of our Choosen option and its number
            System.out.println("The Random option that we choose on the poll is: "+"'"+ random_number_for_poll_options +
                    "."+ daily_poll_each_option.get(random_number_for_poll_options).getText()+"'");
            // Selecting our Random option in our daily poll and clicking it
            daily_poll_each_option.get(random_number_for_poll_options).click();
            // Submitting our vote of option to daily poll
            daily_poll_section.findElement(By.tagName("button")).click();
            Thread.sleep(2000); // Waiting for our vote to submit in daily poll
            // Creating a WebElement for our daily_poll_result
            WebElement daily_poll_result = driver.findElement(By.xpath("//div[@class='units-6']"));
            // Creating a List of WebElements that contains daily_poll_each_option_result
            List<WebElement> daily_poll_each_option_result =  daily_poll_result.findElements(By.className("poll-bar-group"));
            //from the list of results we are finding our choice of vote_result using random_number_for_poll_options
            WebElement vote_result = daily_poll_each_option_result.get(random_number_for_poll_options);
            // from vote_result we are finding total number of votes for the result
            String votes_count = vote_result.findElement(By.className("poll-bar-text")).getText();
            // Finding total_number_of_voters
            String total_number_of_voters = daily_poll_result.findElement(By.xpath("//div[@class='totalVotes']")).getText();
            System.out.println("The total Number of people voted for this option are: "+votes_count +" of " + total_number_of_voters);



        }
    }
