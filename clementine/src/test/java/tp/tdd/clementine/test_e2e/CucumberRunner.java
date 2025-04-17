package tp.tdd.clementine.test_e2e;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "tp.tdd.clementine.test_e2e",
    plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberRunner {
    
    @SpringBootApplication
    @ComponentScan(basePackages = {" tp.tdd.clementine"})
    public static class TestConfig {
    }
}