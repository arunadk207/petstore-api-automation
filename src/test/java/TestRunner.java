import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-reports/Reports.json", "junit:target/cucumber-reports/Reports.xml", "html:target/cucumber-reports/Reports.html"},
        features = {"src/test/java/features/pet.feature", "src/test/java/features/order.feature", "src/test/java/features/user.feature"},
        glue = {"steps"},
        monochrome = true)
public class TestRunner {
}
