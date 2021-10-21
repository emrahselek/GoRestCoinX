Feature: Herokuapp testing for CRUID operators

#  {
#  "firstname": "Orcun",
#  "lastname": "Jackson",
#  "totalprice": 1000,
#  "depositpaid": false,
#  "bookingdates": {
#  "checkin": "2015-11-05",
#  "checkout": "2019-01-17"
#  },
#  "additionalneeds": "Breakfast"
#  }

  Background:
    Given user given api url "https://restful-booker.herokuapp.com/"

    Scenario Outline:
      Given set api endpoint "booking/8"
      And user create new user with request body "<firstname>","<lastname>","<totalprice>","<depositpaid>"
      Then validate the status code 201
      And validate the user firstname is "<firstname>"
      And validate the user lastname is "<lastname>"
      And validate the user totalprice is "<totalprice>"
      And validate the user depositpaid is "<depositpaid>"

      Examples:
        | firstname | lastname | totalprice | depositpaid |
        | Orcun     | Jackson  | 1000       | false       |
