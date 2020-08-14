import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		try {

			String temp = "nike";
			// search with keyword + click used
			driver.get("https://sg.carousell.com/categories/1832/?search=" + temp);
			Actions act = new Actions(driver);

			System.out.println("Searching for:" + temp);
			
			// click show more options
			act.moveToElement(driver.findElement(By.xpath(
					"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[2]/div[1]/button[1]")))
					.click().perform();

			List<WebElement> checkBoxes = driver.findElements(By.xpath(
					"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[2]/div[1]/div[1]/label"));
			int checkBoxSize = checkBoxes.size();

			for (int i = 0; i < checkBoxSize; i++) {

				
				String checkBoxName;
				WebElement checkBox;

				checkBoxes = driver.findElements(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[2]/div[1]/div[1]/label"));
				checkBox = checkBoxes.get(i);
				checkBoxName = checkBox.getText();

				WebDriverWait wait = new WebDriverWait(driver, 60);
				Thread.sleep(3000);
				wait.until(ExpectedConditions.elementToBeClickable(checkBox));

				// click checkbox
				act.moveToElement(checkBox).click().perform();

				int listingsCount = 0;
				boolean flag = checkExist(driver,
						"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[1]/div[2]");

				if (!flag) {

					// click used
					act.moveToElement(driver.findElement(By.xpath("//div[2]//div[1]//div[1]//label[2]"))).click()
							.perform();

					System.out.println("Empty page used clicked!");
				}

				else {


//					Secondary check in url for correct options selected. Usable when there is a change in algo
//					String currentUrl = driver.getCurrentUrl();
//					while ( !currentUrl.contains("USED") || !currentUrl.contains("")) {
//						
//					}
					
					wait.until(ExpectedConditions
							.elementToBeClickable(checkBoxes.get(i)));
					
					if ( checkBox.isSelected()) {
						System.out.println("Empty box clicked");
						checkBox.click();
					}
					
					Thread.sleep(500);

					boolean isSelected = driver.findElement(By.xpath(
							"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[3]/div[1]/div[1]/label[2]"))
							.isSelected();

					if (isSelected) {

						System.out.println("Used not selected, selecting it now!");
						// click used if not selected
						act.moveToElement(driver.findElement(By.xpath(
								"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/form[1]/section[1]/div[3]/div[1]/div[1]/label[2]")))
								.click().perform();
					}

					// loadmore
					while (driver.findElements(By.xpath(
							"//button[contains(@class,'_3dxOPpKVs8 _2Hl0nzGgOH _3KEDnFP0dp _3AGrhxH5DS _2UF39lBLOv yYAF4gRW1m')]"))
							.size() > 0) {

						WebElement loadMore = driver.findElement(By.xpath(
								"//button[contains(@class,'_3dxOPpKVs8 _2Hl0nzGgOH _3KEDnFP0dp _3AGrhxH5DS _2UF39lBLOv yYAF4gRW1m')]"));

						act.moveToElement(loadMore).click().perform();
					}

					listingsCount = driver
							.findElements(By.xpath(
									"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[2]/main[1]/div[1]/div[1]/div"))
							.size();
					wait.until(ExpectedConditions
							.elementToBeClickable(checkBoxes.get(i)));
					act.moveToElement(checkBox).click().perform();

				}
				
				System.out.println("Sizing: " + checkBoxName + " || Number of listings: " + listingsCount);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		driver.close();
		driver.quit();

	}

}