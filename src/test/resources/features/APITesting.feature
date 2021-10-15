Feature: Api testing for CRUID operators

  Background:
    Given user given api url "https://gorest.co.in/"


  Scenario Outline: Post-Create a new user
    Given set api endpoint "public/v1/users"
    And user create new user with request body "<Name>","<Gender>","<Email>","<Status>"
    Then validate the status code 201
    And validate the userId is not null
    And validate the user Name is "<Name>"
    And validate the user Gender is "<Gender>"
    And validate the user Email is "<Email>"
    And validate the user Status is "<Status>"


    Examples:
      | Name    | Gender | Email            | Status |
      | big man | male   | bigman@gmail.com | active |


#  pipi line siraya sokmak icin kullaniyoruz:
#  ctrl+Alt+L
#  option+command+L

