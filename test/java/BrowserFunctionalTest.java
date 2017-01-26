package java;

import org.junit.Test;
import play.test.WithBrowser;

import static org.junit.Assert.assertEquals;

// #test-withbrowser
public class BrowserFunctionalTest extends WithBrowser {

    @Test
    public void runInBrowser() {
        browser.goTo("/speakers");
        assertEquals("Javantura Speakers database", browser.getDriver().getTitle());
    }

}