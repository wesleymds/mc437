Feature: Dev Search
    As an admin
    I want to search for a dev
    So it matches the search criteria

    Background:
        Given the page is open "http://localhost:8080/#"
        And I am logged in as an admin


	Scenario: Get dev by name
        When I search dev by "Danilo"
		Then the dev is found


    Scenario: Get dev by skills
        When I search dev by skill "PHP"
        Then the dev is found


    Scenario: Get dev by hours availability
        When I search by hours availability
        And the number of hours is "10"
        Then the dev is found

    Scenario: Get dev by availability
        When I search by availability
        And the availability is "available"
        Then the dev is found


    Scenario: Get dev max hour value
         When I search dev max hour value
         And the value is "20"
         Then the dev is found


