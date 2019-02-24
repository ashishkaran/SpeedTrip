# SpeedTrip

SpeedTrip is a skeleton for a Selenium Project with simple examples.

## Features
* ThreadLocal WebDriver
* TestNG DataProviders
* A SeleniumHelper class with wrappers around core Selenium APIs for better handling of click events and wait events.
* A Custom TestNG listener class to enable added functionality such as logging and taking screenshots as detailed below.
* A DateUtils class for various date manipulations.
* A Properties file reader to read files from the config folder.
* Testcase wise logging into the folder structure using Log4j.
  > logs/{ClassName}/{MethodName}-{unixtime}.log
* Automatically take screenshots on test failure into the folder structure.
  > screenshot/{yyyyMMdds}/{ClassName}/{MethodName}.jpg
* Sample TestNG suite xmls for
  * Parallel Tests
  * Parallel Methods
  * Single Test  


###  Gotchas
* For the screenshots on failure to work, Tests have to be triggered via the suite XML files as that's where the CustomListener binding occurs.
* Logging will still work as long as the Logging folders are initialized in the @BeforeClass and @BeforeMethod calls as done in the BasicTest class. 

### Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License
[MIT](https://choosealicense.com/licenses/mit/)
