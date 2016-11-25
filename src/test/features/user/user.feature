Feature: User management

	Scenario: Request change for user password
		When I request to change my password
		Then I receive an email with a link to change my password

    Scenario: Change user password
        When I click the link
        And I fill in the new password input and the confirmation input
        Then my password is set to the new password

    Scenario: Confirm account
        When I resgister in the system
        And I get a confirmation email
        And I click the link
        Then my account is confirmed

    Scenario:
        When I register in the system
        And I confirm my account
        Then C_O get an email
