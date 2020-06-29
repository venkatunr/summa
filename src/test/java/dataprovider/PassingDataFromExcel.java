package dataprovider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PassingDataFromExcel {
public WebDriver driver;
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		 driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("https://mvc.syncfusion.com/demos/web/grid/foreignkey");
		
	}
	
	@Test(dataProvider="foreignkey")
	public void foreignKeyAdaptor(String orderID,String customerID,String firstname,String frieght,String shipname,String shipcountry) throws InterruptedException {
	
	driver.findElement(By.id("Editing_add")).click();
	WebDriverWait wait=new WebDriverWait(driver,20);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("EditingOrderID")));
	
	driver.findElement(By.id("EditingOrderID")).sendKeys(orderID);
	driver.findElement(By.id("EditingCustomerID")).sendKeys(customerID);

	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='EditingEmployeeID_FirstName_hidden']")));
	driver.findElement(By.xpath("//input[@id='EditingEmployeeID_FirstName_hidden']")).click();
	List<WebElement> listOfFirstName=driver.findElements(By.xpath("//div[@id=\"EditingEmployeeID_FirstName_popup\"]//child::ul/li"));
	
	for (int i = 0; i < listOfFirstName.size(); i++) {
		WebElement element=listOfFirstName.get(i);	
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
		String name=listOfFirstName.get(i).getText();
		if(name.equalsIgnoreCase(firstname)) {
			listOfFirstName.get(i).click();
		}
	}
	
	WebElement  valueofFreight=driver.findElement(By.xpath("//input[@id='EditingFreight']//preceding-sibling::input"));
	valueofFreight.click();
	driver.findElement(By.xpath("//input[@id='EditingFreight']")).sendKeys(frieght);
	driver.findElement(By.id("EditingShipName")).sendKeys(shipname);
	
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='EditingShipCountry_hidden']")));

	driver.findElement(By.xpath("//input[@id='EditingShipCountry_hidden']")).click();
	List<WebElement> listOfShipConuntry=driver.findElements(By.xpath("//div[@id=\"EditingShipCountry_popup\"]//child::ul//li"));
	
	for (int i = 0; i < listOfShipConuntry.size(); i++) {
		WebElement element=listOfShipConuntry.get(i);
		JavascriptExecutor jse=(JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
		String name=listOfShipConuntry.get(i).getText();
		if(name.equalsIgnoreCase(shipcountry )) {
			listOfShipConuntry.get(i).click();
		}
	}
	
	wait.until(ExpectedConditions.
			presenceOfElementLocated(By.xpath("//*[@id=\"Editing_update\"]")));

	driver.findElement(By.xpath("//*[@id=\"Editing_update\"]")).click();
	Thread.sleep(3000);
	
	}
	
	@DataProvider(name = "foreignkey")
	public static Object[][] getdata() throws IOException{
		Object[][] tabArray=ExcelReader.readingDataFromExcel("C:\\Users\\Mugu\\eclipse-workspace\\Z_DataProvide\\Files\\DataFiles.xlsx", "foreignkey");
		return tabArray;
	}
	
	
}
