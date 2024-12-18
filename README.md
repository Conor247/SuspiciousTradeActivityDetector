# Suspicious Trade Activity Detector
 Java application that takes in trade and order information and determines if the activity is attempting to falsely drive upward or downward pressure on a stock price.
 
### Assumptions
* Assumption 1: The trades and orders sent to the suspiciousActivityDetector method are all from the same user.
* Assumption 2: Since the return type was not specified in the exercise description, I think a map of key value pairs 
might be best so that each trade is directly linked with a list of orders found suspicious. 
Perhaps this could be wrapped in an object, but I want to keep it simple.

## Technical Features

### Builder Pattern
* Necessary to build the data for trades and orders in a clean fashion.
* Ensures type safe creation of object properties.
* Avoids long constructors.
* Immutable object creation.

### Strategy Pattern
* Promotes single responsibility - each strategy has a single responsibility.
* Open/Closed principle - easily add new strategy classes without changing existing Inspector service implementation.
* Testability - each strategy can be tested independently and the Inspector service only focuses on coordinating strategies.

## Testing

Open a bash terminal at the project directory and use the following commands to run tests and generate reports.
* Test reports can be found in build>>reports, open the respective index.html files in a browser to view them.
```bash
./gradlew test
````
* Run all JUnit tests.

```bash
./gradlew pitest
````
* Generates pitest report on the line coverage and mutation coverage which can be found at build>>reports>>pitest>>index.html
