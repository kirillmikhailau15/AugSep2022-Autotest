import mavenizer.helpers.Zarytski.Waits;
import mavenizer.staticPO.CartPage;
import mavenizer.staticPO.CataloguePage;
import mavenizer.TestBase;
import mavenizer.staticPO.ElementsPage;
import mavenizer.staticPO.TablesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BinTest extends TestBase {


    @Test
    public void checkAddOneElementToBin() throws Exception {

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        String before = CataloguePage.getCartQuantityOnRightTopCorner(driver);

        LOG.info("Add one element to bin");
        CataloguePage.addOneElementToBin(driver);
       LOG.info("Waiting add one element to bin");
        Waits.explicitWaitTextToBe(driver, "CataloguePage.cartQuantity", "1");

        String after = CataloguePage.getCartQuantityOnRightTopCorner(driver);
        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(after, "1");
    }

    @Test
    public void checkAddTreeElementToBin() {
        String before = CataloguePage.getCartQuantityOnRightTopCorner(driver);
        CataloguePage.addTreeElementsToBin(driver);

        Waits.explicitWaitTextToBe(driver, "CataloguePage.cartQuantity", "3");

        String after = CataloguePage.getCartQuantityOnRightTopCorner(driver);
        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(after, "3");
    }


    @Test
    public void reduceItemsInTheCart() {
        CataloguePage.addFiveSameElements(driver);

        TablesPage tablesPage1 = new TablesPage(driver);
        String getCellBefore = tablesPage1.example1.getCell(1,0).getText();
        System.out.println(getCellBefore);


        CartPage.reduceTheAmountToOne(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.xpath("//*[@class='footer']//strong[contains(text(), '€')]"), "14.80 €"));

        TablesPage tablesPage2 = new TablesPage(driver);
        String getCellAfter = tablesPage2.example1.getCell(1,0).getText();
        System.out.println(getCellAfter);

        Assert.assertEquals(getCellAfter, "1");

    }

    @Test
    public void increaseItemsInTheCart() {
        CataloguePage.addOneElementToBin(driver);
        Waits.explicitWaitTextToBe(driver, "CataloguePage.cartQuantity","1");
        CataloguePage.goToCartPage(driver);

        TablesPage tablesPage3 = new TablesPage(driver);
        String getCellBefore = tablesPage3.example1.getCell(1,0).getText();
        System.out.println(getCellBefore);

        CartPage.increaseItemsInTheCartByFive(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.xpath("//*[@class='footer']//strong[contains(text(), '€')]"), "74.00 €"));

        TablesPage tablesPage4 = new TablesPage(driver);
        String getCellAfter = tablesPage4.example1.getCell(1,0).getText();
        System.out.println(getCellAfter);

        Assert.assertEquals(getCellAfter, "5");

    }

    @Test
    public void removingOneElementFromTheCart() {
        CataloguePage.addTreeElementsToBin(driver);
        Waits.explicitWaitTextToBe(driver, "CataloguePage.cartQuantity","3");
        CataloguePage.goToCartPage(driver);

        ElementsPage beforeRemove = new ElementsPage(driver);
        int allSumBefore = beforeRemove.check1.getListOfElementsInBin();
        System.out.println(allSumBefore);

        Waits.explicitWaitNumberOfElementsToBe(driver, "CartPage.listOfElements", 3);

        ElementsPage myListOfElements = new ElementsPage(driver);
        myListOfElements.check1.selectElementFromListOfElementsInBin(0);

        CartPage.removeElement(driver);

        Waits.explicitWaitNumberOfElementsToBe(driver, "CartPage.listOfElements", 2);

        ElementsPage afterRemove = new ElementsPage(driver);
        int allSumAfter = afterRemove.check1.getListOfElementsInBin();
        System.out.println(allSumAfter);

        Assert.assertEquals(allSumAfter, 2);

    }
    
}
