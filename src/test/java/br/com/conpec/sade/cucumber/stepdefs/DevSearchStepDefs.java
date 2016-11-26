package br.com.conpec.sade.cucumber.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by ra121051 on 11/11/16.
 */
public class DevSearchStepDefs extends StepDefs {
    private WebDriver webDriver;


    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");//Especificar caminho completo na máquina quando for testar

    }

    @cucumber.api.java.en.Given("^the page is open \"([^\"]*)\"$")
    public void thePageIsOpen(String page) throws Throwable {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        webDriver.get(page);

    }

    @cucumber.api.java.en.And("^I am logged in as an admin$")
    public void iAmLoggedInAsAnAdmin() throws Throwable {
        webDriver.findElement(By.id("account-menu")).click();
        webDriver.findElement(By.id("login")).click();
        webDriver.findElement(By.id("username")).sendKeys("admin");
        webDriver.findElement(By.id("password")).sendKeys("admin");
        webDriver.findElement(By.id("loginButton")).click();

    }

    @cucumber.api.java.en.When("^I search dev by \"([^\"]*)\"$")
    public void iSearchDevByName(String arg0) throws Throwable {
        WebElement element = webDriver.findElement(By.id("search-dev"));
        Thread.sleep(5000);
        element.click();

        webDriver.findElement(By.id("dev-name")).sendKeys(arg0);
        webDriver.findElement(By.id("search-devs-button")).click();

    }

    @cucumber.api.java.en.Then("^the dev is found$")
    public void theDevIsFound() throws Throwable {
        webDriver.close();
    }

    @When("^I search dev by skill \"([^\"]*)\"$")
    public void iSearchDevBySkill(String arg0) throws Throwable {
        WebElement element = webDriver.findElement(By.id("search-dev"));
        Thread.sleep(5000);
        element.click();
        webDriver.findElement(By.name("dev-skills")).click();
        webDriver.findElement(By.name(arg0)).click();
        webDriver.findElement(By.className("md-select-backdrop")).click();
        webDriver.findElement(By.id("search-devs-button")).click();
    }


    @cucumber.api.java.en.When("^I search by availability$")
    public void iSearchByAvailability() throws Throwable {
        WebElement element = webDriver.findElement(By.id("search-dev"));
        Thread.sleep(5000);
        element.click();
        webDriver.findElement(By.name("dev-availability")).click();
    }

    @And("^the availability is \"([^\"]*)\"$") public void theAvailabilityIs(String arg0)
        throws Throwable {
        webDriver.findElement(By.name(arg0)).click();
        webDriver.findElement(By.id("search-devs-button")).click();
    }

    @When("^I search by hours availability$") public void iSearchByHoursAvailability()
        throws Throwable {
        WebElement element = webDriver.findElement(By.id("search-dev"));
        Thread.sleep(5000);
        element.click();
    }

    @And("^the number of hours is \"([^\"]*)\"$")
    public void theNumberOfHoursIs(String arg0) throws Throwable {
        webDriver.findElement(By.id("dev-hour-availability")).sendKeys(arg0);
        webDriver.findElement(By.id("search-devs-button")).click();
    }

    @When("^I search dev max hour value$") public void iSearchDevMaxHourValue() throws Throwable {
        WebElement element = webDriver.findElement(By.id("search-dev"));
        Thread.sleep(5000);
        element.click();
    }

    @And("^the value is \"([^\"]*)\"$") public void theValueIs(String arg0) throws Throwable {
        webDriver.findElement(By.id("dev-max-hour-cost")).sendKeys(arg0);
        webDriver.findElement(By.id("search-devs-button")).click();
    }
}
