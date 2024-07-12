Feature: Book Controller

  Scenario Outline: Create a Book Through a Web page
    Given a URL <URL>
    When The URL is used to get the page
    Then Check if the connection is ok
    Examples:
      | URL  |
      | "/CreateBook" |