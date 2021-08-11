# References
- this framework is follow https://blackswantechnologies.atlassian.net/wiki/spaces/QA/pages/1446969921/Clean+code+rules
- https://en.wikipedia.org/wiki/Don%27t_repeat_yourself
- https://en.wikipedia.org/wiki/KISS_principle
- https://en.wikipedia.org/wiki/SOLID
- see also: object pattern and page object factory pattern

# Project dependencies
## Lombok
- lombok documentation: https://projectlombok.org/features/all
## Spring
- spring documentation: https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/
## Testng
- testng documentation: https://testng.org/doc/documentation-main.html
## Selenium
- selenium documentation: https://www.selenium.dev/documentation/en/webdriver/
## Maven
- maven documentation: https://maven.apache.org
## Creating jenkins pipeline
- jenkinsfile documentation: https://www.jenkins.io/doc/book/pipeline/jenkinsfile/

# Preconditions
- install maven http://maven.apache.org/download.cgi#
- install intellij community edition https://www.jetbrains.com/idea/
- install open java 8 or higher https://openjdk.java.net/
- install following intellij plugins: cucumber, gherkin, lombok
## Configuration
- configure maven env variables https://www.tutorialspoint.com/maven/maven_environment_setup.htm
- configure java env variables https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html
## Downloading webdriver
- execute download.sh or download.bat in resources.driver.linux/macos/windows folder (depends on system that you are using)

# Executing tests
## Executing tests from commandline
- Rebuilding project with all changes, execute tests and generating report: maven clean install
- Executing already compiled tests from target folder but without generating cucumber report: mvn test
## Overriding default configuration
- executing tests that using configuration file from classpath:/resources/config/moicher.yml: mvn test -Dconfig.file=sparta-dev3
- executing tests that using different operating system: mvn install -Ddriver-details.os=windows
- executing tests that using sparta-dev3.yml but override field inside this configuration file: mvn install -Dconfig.file=moicher, -Ddriver-details.os=windows
- executing tests tagged by "@sanity" annotation: mvn install -Dcucumber.filter.tags=@sanity
- executing tests and pass array of users: mvn clean install -Denvironment.users="user1/user1password, user2/user2password"
- executing tests in 4 threads: mvn install -Dthreads.count=4
- execute test and try to rerun failed tests 2 times: mvn clean install -Dsurefire.rerunFailingTestsCount=4
- execute tests after code edition, select configuration file, override some of the values in config file, set parallel execution to 2, execute tests that are
 annotated with @yourtest annotation and try to rerun failed tests 3 times: mvn install -Dconfig.file=moicher2 -Ddriver-details.os=windows
 -Dtimeout.implicit=100 -Dthreads.count=2 -Dcucumber.options="-t @yourtest" -Dsurefire.rerunFailingTestsCount=3
- you can find more configuration options inside com.bst.configuration.Config

# Executing tests from intellij
- open intellij and click on  Run -> edit configuration
- add new maven configuration
- in command line property set: clean install -Dcucumber.filter.tags=@tests   (where @tests is your tagged test case)
- click on play button and set previously created configuration

# Reporting
## generating cucumber reports
- after every test execution by "install" command - maven is creating cucumber report in ui_test_automation/target/generated-report/index.html
## Generating allure reports (not stable)
- run this command after test execution: mvn allure:report
- allure report will be generated in directory: target/site/allure-maven/index.html
- start server and publish allure report: mvn allure:serve

# Creating tests
## adding page objects
- add @Scope("cucumber-glue") and @Component under all page objects
- inject object by call @Autowire under filed or constructor

### Readme file is still in progress

### Known defects
- cucumber is not failing build when surefire has testFailureIgnore=true
but when it is set to false then cucumber is not generating report
- findbyng does not return list of elements
- user is not able to pass variables from commandline level anymore
- log printer is not showing locators for findbyng: Looking for element: [unknown locator]


### Improvements:
- add user list in yaml file instead of list of users in one string