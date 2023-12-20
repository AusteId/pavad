import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class TC1_Example_Broken {
    WebDriver driver;

    @BeforeEach
    void setup() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");

        driver = new EdgeDriver(options);
        driver.get("https://todomvc.com/examples/emberjs/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void smokeTest() {
        add("A", "B", "C");
        todosShouldBe("A,B,C");

        toggle("B");
        clearCompleted();
        todosShouldBe("A,C");

        delete("C");
        todosShouldBe("A");
    }

    @Test
    void filterTest() {
        add("A", "B", "C");
        toggle("B");

        filter("Active");
        todosShouldBe("A,C");

        filter("Completed");
        todosShouldBe("B");

        filter("All");
        todosShouldBe("A,B,C");
    }

    @Test
    public void itemLeftTest() {

        itemLeftShouldBeNotDisplayed();
        add("A", "B");
        itemLeftShouldBe(2);

        toggle("A");
        itemLeftShouldBe(1);

        delete("B");
        itemLeftShouldBe(0);


    }

    @Test
    public void completeAll() {
        add("a", "b");

        toggleAll();

        allTodosShouldBeCompleted();
    }

    @Test
    public void hoverTest() {
        add("A");
        deleteButtonShouldBeVisible();
    }

    private boolean deleteButtonShouldBeVisible() {
//        assertThat(
                return driver.findElement(By.cssSelector("#todo-list li .destroy")).isDisplayed();
//        ).isTrue();
    }

    private void hover(String todo) {
        WebElement element = driver.findElement(By.xpath("//*[@id='todo-list']//*[text()='todo']"));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    private void allTodosShouldBeCompleted() {
//        double completedSize = driver.findElements(By.cssSelector(".completed")).size();
//        Dimension allSize = driver.findElement(By.cssSelector("#todo-list li")).getSize();
//
//        double size = allSize.getHeight() * allSize.getWidth();
//
//        assertThat(size).isEqualTo(completedSize);

        double completedSize = driver.findElements(By.cssSelector(".completed")).size();
        double allSize = driver.findElements(By.cssSelector("#todo-list li")).size();

       assertThat(allSize).isEqualTo(completedSize);

    }

    private void toggleAll() {
        driver.findElement(By.cssSelector("#toggle-all")).click();
    }

    private void itemLeftShouldBeNotDisplayed() {
        assertThat(
                driver.findElements(By.cssSelector("#todo-count"))
        ).isEmpty();
    }

    private void itemLeftShouldBe(int size) {
        String expectedText;
//        if (size ==0) expectedText = "0 items left";
        if (size == 1) expectedText = "1 item left";
        else expectedText = size + " items left";
        String actualText = driver.findElement(By.cssSelector("#todo-count")).getText();
        assertThat(actualText).isEqualTo(expectedText);
    }

    private void clearCompleted() {
//        driver.findElement(By.xpath("//a[text()='Completed']")).click();
        driver.findElement(By.xpath("//button[@id='clear-completed']")).click();

    }

    private void add(String... texts) {
        for (String text : texts) {
            driver.findElement(By.cssSelector("#new-todo"))
                    .sendKeys(text+ Keys.ENTER);
        }
    }

    private void filter(String criteria) {
        driver.findElement(By.xpath("//*[@id='filters']//*[text()='" + criteria + "']"))
                .click();
    }

    private void todosShouldBe(String expectedTodos) {
        assertThat(
                driver.findElement(By.cssSelector("#todo-list")).getText().replace("\n", ",")
        ).isEqualTo(expectedTodos);
    }

    private void delete(String todo) {
        WebElement element = driver.findElement(By.xpath("//*[@id='todo-list']//*[text()='" + todo + "']"));
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();

        driver.findElement(By.xpath("//*[@id='todo-list']//*[text()='" + todo + "']/../button"))
                .click();

    }

    private void toggle(String todo) {

        driver.findElement(By.xpath("//*[@id='todo-list']//*[text()='" + todo + "']/../input"))
                .click();
    }
}
