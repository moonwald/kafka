Feature: Publish events on kafka based on rules 

Scenario: Be able to take a rule and create get events on kafka 

    Given zookeeper runs at localhost:2181 
 
    When getallevents rule is requested by rulesservice
    
    Then there should be a message FETCH_EVENTS_COMMAND sent to topic Matchbook.DATA_REQ