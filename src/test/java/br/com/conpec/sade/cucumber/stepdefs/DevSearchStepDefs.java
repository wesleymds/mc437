package br.com.conpec.sade.cucumber.stepdefs;

/**
 * Created by ra121051 on 11/11/16.
 */
public class DevSearchStepDefs extends StepDefs {
    private WebDriver driver;

    @cucumber.api.java.en.Given("^the page is open \"([^\"]*)\"$")
    public void thePageIsOpen(String page) throws Throwable {
        driver.get(page);

    }

    @cucumber.api.java.en.And("^I am logged in as an admin$")
    public void iAmLoggedInAsAnAdmin() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.When("^I search dev by \"([^\"]*)\"$")
    public void iSearchDevByName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.Then("^the dev is found$")
    public void theDevIsFound() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^his name is \"([^\"]*)\"$")
    public void hisNameIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.When("^I search dev by \"([^\"]*)\"$")
    public void iSearchDevBy(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^Skill is \"([^\"]*)\"$")
    public void skillIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.Then("^the dev is found$")
    public void theDevIsFound() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^his skill is \"([^\"]*)\"$")
    public void hisSkillIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.When("^I search by availability$")
    public void iSearchByAvailability() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^initial date is (\\d+)/(\\d+)/(\\d+)$")
    public void initialDateIs(int arg0, int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^due date is (\\d+)/(\\d+)/(\\d+)$")
    public void dueDateIs(int arg0, int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.Then("^the dev is found$")
    public void theDevIsFound() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^his available between (\\d+)/(\\d+)/(\\d+) and (\\d+)/(\\d+)/(\\d+)$")
    public void hisAvailableBetweenAnd(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.When("^I search dev by project$")
    public void iSearchDevByProject() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^project name is \"([^\"]*)\"$")
    public void projectNameIs(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^he is working on project \"([^\"]*)\"$")
    public void heIsWorkingOnProject(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.When("^I search dev by experience$")
    public void iSearchDevByExperience() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^input is has worked before$")
    public void inputIsHasWorkedBefore() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @cucumber.api.java.en.And("^he has worked for Conpec before$")
    public void heHasWorkedForConpecBefore() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
