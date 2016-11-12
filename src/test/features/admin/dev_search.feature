Feature: Dev Search
    As an admin
    I want to search for a dev
    So it matches the search criteria

    Background:
        Given the page is open "http://localhost:8080/#/search"
        And I am logged in as an admin


	Scenario: Get dev by name
        When I search dev by "name"
		Then the dev is found
		And his name is "name"

    Scenario: Get dev by skills
        When I search dev by "skill"
        And Skill is "Java"
        Then the dev is found
        And his skill is "Java"

    Scenario: Get dev by availability
        When I search by availability
        And initial date is 10/10/2016
        And due date is 10/11/2016
        Then the dev is found
        And his available between 10/10/2016 and 10/11/2016

    Scenario: Get dev by project
         When I search dev by project
         And project name is "Sade"
         Then the dev is found
         And he is working on project "Sade"

    Scenario: Get dev by experience
        When I search dev by experience
        And input is has worked before
        Then the dev is found
        And he has worked for Conpec before
