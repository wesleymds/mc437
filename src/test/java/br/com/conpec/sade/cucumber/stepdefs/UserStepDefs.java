package br.com.conpec.sade.cucumber.stepdefs;

import br.com.conpec.sade.web.rest.AccountResource;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserStepDefs extends StepDefs {
    private WebDriver webDriver;

    @Inject
    private AccountResource accountResource;
    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");//Especificar caminho completo na máquina quando for testar

    }

//    @Given("^the page is open \"([^\"]*)\"$")
//    public void thePageIsOpen(String page) throws Throwable {
//        webDriver = new ChromeDriver();
//        webDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
//        webDriver.get(page);}

    @When("^I request to change my password$")
    public void iRequestToChangeMyPassword() throws Throwable {
        webDriver.findElement(By.id("account-menu")).click();
        webDriver.findElement(By.id("login")).click();
        WebElement element = webDriver.findElement(By.id("forgot-password"));
        Thread.sleep(5000);
        element.click();
        webDriver.findElement(By.id("email")).sendKeys("437test@gmail.com");
        WebElement element2 = webDriver.findElement(By.id("reset-password"));
        Thread.sleep(5000);
        element2.click();
        webDriver.close();
    }

    @Then("^I receive an email with a link to change my password$")
    public void iReceiveAnEmailWithALinkToChangeMyPassword() throws Throwable {
        RequestPostProcessor postProcessor = new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.addHeader("Content-Type", "application/json;charset=UTF-8");
                request.addHeader("Cookie", "XSRF-TOKEN=433821c0-5e27-47c6-b3fc-c30c38d1c370");
                request.addHeader("X-XSRF-TOKEN", "433821c0-5e27-47c6-b3fc-c30c38d1c370");
                return request;
            }
        };

        actions = restUserMockMvc.perform(post("/api/account/reset_password/init").with(postProcessor)
            .accept(MediaType.TEXT_PLAIN).contentType(MediaType.APPLICATION_JSON_UTF8).content("teste@teste"));
        actions
            .andExpect(status().isBadRequest());
    }



    @When("^I click the link$")
    public void iClickTheLink() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        webDriver.close();

        throw new PendingException();
    }

    @And("^I fill in the new password input and the confirmation input$")
    public void iFillInTheNewPasswordInputAndTheConfirmationInput() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        webDriver.close();

        throw new PendingException();
    }

    @Then("^my password is set to the new password$")
    public void myPasswordIsSetToTheNewPassword() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        webDriver.close();

        throw new PendingException();
    }

    @When("^I resgister in the system$")
    public void iResgisterInTheSystem() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        webDriver.close();

        throw new PendingException();
    }

    @And("^I get a confirmation email$")
    public void iGetAConfirmationEmail() throws Throwable {
        webDriver.close();
// Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^my account is confirmed$")
    public void myAccountIsConfirmed() throws Throwable {
        webDriver.close();
// Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I register in the system$")
    public void iRegisterInTheSystem() throws Throwable {
        webDriver.close();
// Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^I confirm my account$")
    public void iConfirmMyAccount() throws Throwable {
        webDriver.close();
// Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^C_O get an email$")
    public void c_oGetAnEmail() throws Throwable {

        webDriver.close();
// Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^the page is open user \"([^\"]*)\"$")
    public void thePageIsOpenUser(String arg0) throws Throwable {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        webDriver.get(arg0);
    }

    @Then("^I don  t receive an email with a link to change my password$")
    public void iDonTReceiveAnEmailWithALinkToChangeMyPassword() throws Throwable {
        RequestPostProcessor postProcessor = new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.addHeader("Content-Type", "application/json;charset=UTF-8");
                request.addHeader("Cookie", "XSRF-TOKEN=433821c0-5e27-47c6-b3fc-c30c38d1c370");
                request.addHeader("X-XSRF-TOKEN", "433821c0-5e27-47c6-b3fc-c30c38d1c370");
                return request;
            }
        };
        actions = restUserMockMvc.perform(post("/api/account/reset_password/init").with(postProcessor)
            .accept(MediaType.TEXT_PLAIN).contentType(MediaType.APPLICATION_JSON_UTF8).content("errado@errado"));
        actions
            .andExpect(status().isBadRequest());
    }
}
