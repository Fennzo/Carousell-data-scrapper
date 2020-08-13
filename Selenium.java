import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Introduction:
 * Selenium is an open-source software that automates web-application testing
 * Web-application testing involves usability, functionality, image testing, consistency ( e.g. multiple browsers )
 * Supported by community
 * 
 * Shortcomings:
 * No in-built object repository - Selenium doesn't identify the objects used in a test script
 * Resolved by installing kit 
 * 
 * Unable to perform image-testing. E.g. check if the images are performing as required 
 * Resolved by integrating sikuli
 * 
 * Doesn't generate test/bug reports
 * Resolved by integrating TestNG
 * 
 * Why Java?
 * Java has the additional add-ons to make up for Selenium's inadequecies
 * Java is a common language used in web-applications
 * 
 * Process
 * 
 * Test Script -> Web driver -> IE, mozzila, chrome, opera, safari
 * 	 
 */

public class Selenium {

	public static boolean checkExist(WebDriver driver, String xpath) {
		 boolean flag = false;
	        try {
	        	WebElement element = driver.findElement(By.xpath(xpath));
	            if (element.isDisplayed() || element.isEnabled())
	                flag = true;
	        } catch (NoSuchElementException e) {
	            flag = false;
	        } 
	        return flag;
	}
	
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\jinyi\\Downloads\\chromedriver_win32 (3)\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		try {
			driver.get(
					"https://sg.carousell.com/categories/1832/?condition_v2=USED&sc=1202081422020a002a160a0c636f6e646974696f6e5f763222060a04555345442a150a0b636f6c6c656374696f6e7322060a04313833322a160a0c636f6e646974696f6e5f763222060a045553454432090a07616464696461733a0408bbe17242037765624a02656e&search=addidas&sort_by=");
			driver.manage().window().maximize();
			Actions act = new Actions(driver); 
			// click show more options
			act.moveToElement(driver.findElement(
					By.xpath("/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[2]/div[1]/button[1]"))).click().perform();;
			int checkBoxSize = driver
					.findElements(By.xpath("//div[@class='dLZJAZVTAg']//div[2]//div[1]//div[1]//label")).size();

			for ( int i = 1; i < checkBoxSize + 1; i++) {
				String checkBoxName = driver.findElement(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[2]/div[1]/div[1]/label["
								+ i + "]/div[1]/p[1]"))
						.getText();

				
				// click checkbox
				act.moveToElement(driver.findElement(By.xpath("//div[@class='dLZJAZVTAg']//div[2]//div[1]//div[1]//label[" + i + "]"))).click().perform();
				int listingsCount = 0;
				boolean flag = checkExist(driver, "/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[1]/div[2]");
				
				if (!flag) {
					act.moveToElement(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/form[1]/section[1]/div[2]/div[1]/div[1]/label[2]"))).click().perform();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					act.moveToElement(driver.findElement(By.xpath("//div[@class='SlZgBjnLiz']//div[3]//div[1]//div[1]//label[2]"))).click().perform();
				}
				
				else {
				while (driver.findElements(By.xpath(
						"//button[contains(@class,'_3dxOPpKVs8 _2Hl0nzGgOH _3KEDnFP0dp _3AGrhxH5DS _2UF39lBLOv yYAF4gRW1m')]"))
						.size() > 0) {
					
					WebElement loadMore = driver.findElement(By.xpath(	
							"//button[contains(@class,'_3dxOPpKVs8 _2Hl0nzGgOH _3KEDnFP0dp _3AGrhxH5DS _2UF39lBLOv yYAF4gRW1m')]"));
				    
					act.moveToElement(loadMore).click().perform();
				}

				 listingsCount = driver
						.findElements(By
								.xpath("/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[1]/div"))
						.size();
				
                
				act.moveToElement(driver.findElement(By.xpath("//div[@class='dLZJAZVTAg']//div[2]//div[1]//div[1]//label[" + i + "]"))).click().perform();
				
				// click used to refresh page
				act.moveToElement(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[3]/div[1]/div[1]/label[2]"))).click().perform();
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[3]/div[1]/div[1]/label[2]")).click();
			}
				
				System.out.println("Sizing: " + checkBoxName + " || Number of listings: " + listingsCount);	
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	driver.quit();
	driver.close();
	}

}
