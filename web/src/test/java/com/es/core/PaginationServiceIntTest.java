package com.es.core;

import com.es.phoneshop.web.controller.exception.throwable.InvalidUrlParamException;
import com.es.phoneshop.web.controller.service.PaginationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(value = "classpath:context/testContext-web.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class PaginationServiceIntTest {
    @Autowired
    private PaginationService paginationService;
    
    private final String SEARCH_ANY = "%";
    private final String SEARCH_NOT_EXISTING = "Don't exist";
    private final Integer VALID_PAGE_NUMBER = 1;
    private final Integer NOT_VALID_PAGE_NUMBER = -1;
    private final int AMOUNT_OF_AVAILABLE_PHONES = 16;

    @Test
    public void testGetPageBeginNumberWithValidPage() throws InvalidUrlParamException{
        Integer pageBeginNumber = paginationService.getPageBeginNumber(VALID_PAGE_NUMBER, SEARCH_ANY);
        Assert.assertTrue(pageBeginNumber.equals(1));
    }

    @Test(expected = InvalidUrlParamException.class)
    public void testGetPageBeginNumberWithNotValidPage() throws InvalidUrlParamException{
        paginationService.getPageBeginNumber(NOT_VALID_PAGE_NUMBER, SEARCH_ANY);
    }

    @Test
    public void testGetPageBeginNumberWhenPhonesAbsent() throws InvalidUrlParamException{
        Integer pageNumber = paginationService.getPageBeginNumber(VALID_PAGE_NUMBER, SEARCH_NOT_EXISTING);
        Assert.assertTrue(pageNumber.equals(1));
    }

    @Test
    public void testGetNewPage() throws InvalidUrlParamException{
        Integer pageFromFirstSegment = PaginationService.PREFERABLE_PAGES_AMOUNT - 1;
        Integer pageFromSecondSegment = PaginationService.PREFERABLE_PAGES_AMOUNT + 1;
        Integer newPage = paginationService.getNewPage(pageFromFirstSegment, PaginationService.NEXT_PAGE, SEARCH_ANY);
        Integer maxPhoneAmountInSegment = PaginationService.PREFERABLE_PAGES_AMOUNT * PaginationService.PHONES_TO_DISPLAY;
        if(AMOUNT_OF_AVAILABLE_PHONES >= maxPhoneAmountInSegment) {
            Assert.assertTrue(pageFromSecondSegment.equals(newPage));
        }
        else{
            Assert.assertFalse(pageFromFirstSegment.equals(newPage));
        }
    }

    @Test
    public void testGetPageAmountToDisplay(){
        Integer pageAmountToDisplay = paginationService.getPageAmountToDisplay(1, SEARCH_ANY);
        Integer expectedValue = (int) Math.ceil((double) (AMOUNT_OF_AVAILABLE_PHONES / PaginationService.PHONES_TO_DISPLAY));
        Assert.assertTrue(pageAmountToDisplay.equals(expectedValue));
    }
}
