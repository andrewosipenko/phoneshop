package com.es.core;

import com.es.phoneshop.web.controller.exception.InvalidUrlParamException;
import com.es.phoneshop.web.controller.paginator.PaginatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(value = "classpath:context/testContext-web.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PaginatorServiceIntTest {
    private final String SEARCH_ANY = "%";
    private final String SEARCH_NOT_EXISTING = "Don't exist";
    private final Integer VALID_PAGE_NUMBER = 1;
    private final Integer NOT_VALID_PAGE_NUMBER = -1;
    private final int AMOUNT_OF_AVAILABLE_PHONES = 16;

    @Test
    public void testGetPageBeginNumberWithValidPage() throws InvalidUrlParamException{
        Integer pageBeginNumber = PaginatorService.getPageBeginNumber(VALID_PAGE_NUMBER, SEARCH_ANY);
        Assert.assertTrue(pageBeginNumber.equals(1));
    }

    @Test(expected = InvalidUrlParamException.class)
    public void testGetPageBeginNumberWithNotValidPage() throws InvalidUrlParamException{
        PaginatorService.getPageBeginNumber(NOT_VALID_PAGE_NUMBER, SEARCH_ANY);
    }

    @Test
    public void testGetPageBeginNumberWhenPhonesAbsent() throws InvalidUrlParamException{
        Integer pageNumber = PaginatorService.getPageBeginNumber(VALID_PAGE_NUMBER, SEARCH_NOT_EXISTING);
        Assert.assertTrue(pageNumber.equals(1));
    }

    @Test
    public void testGetNewPage() throws InvalidUrlParamException{
        Integer pageFromFirstSegment = PaginatorService.PREFERABLE_PAGES_AMOUNT - 1;
        Integer pageFromSecondSegment = PaginatorService.PREFERABLE_PAGES_AMOUNT + 1;
        Integer newPage = PaginatorService.getNewPage(pageFromFirstSegment, PaginatorService.NEXT_PAGE, SEARCH_ANY);
        Integer maxPhoneAmountInSegment = PaginatorService.PREFERABLE_PAGES_AMOUNT * PaginatorService.PHONES_TO_DISPLAY;
        if(AMOUNT_OF_AVAILABLE_PHONES >= maxPhoneAmountInSegment) {
            Assert.assertTrue(pageFromSecondSegment.equals(newPage));
        }
        else{
            Assert.assertFalse(pageFromFirstSegment.equals(newPage));
        }
    }

    @Test
    public void testGetPageAmountToDisplay(){
        Integer pageAmountToDisplay = PaginatorService.getPageAmountToDisplay(1, SEARCH_ANY);
        Integer expectedValue = (int) Math.ceil((double) (AMOUNT_OF_AVAILABLE_PHONES / PaginatorService.PHONES_TO_DISPLAY));
        Assert.assertTrue(pageAmountToDisplay.equals(expectedValue));
    }
}
