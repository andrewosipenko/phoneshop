package com.es.phoneshop.web.services;

import com.es.phoneshop.web.controller.exceptions.InvalidParametersInUrlException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context/test-config.xml")
public class PaginationServiceTest {
    private final String DEFAULT_SEARCH = "";
    private final String WRONG_SEARCH = "";
    private final int FIRST_PAGE = 1;
    private final int ERROR_NUMBER_PAGE = 0;
    private final int MAX_PAGE_NUMBER = 3;

    @Resource
    private PaginationService paginationService;

    @Test
    public void shouldGetPageStartNumber() {
        int pageStartNumber = paginationService.getPageStartNumber(FIRST_PAGE, DEFAULT_SEARCH);

        Assert.assertEquals(FIRST_PAGE, pageStartNumber);
    }

    @Test
    public void shouldGetPageStartNumberWithWrongSearch() {
        int pageStartNumber = paginationService.getPageStartNumber(FIRST_PAGE, WRONG_SEARCH);

        Assert.assertEquals(FIRST_PAGE, pageStartNumber);
    }

    @Test(expected = InvalidParametersInUrlException.class)
    public void shouldThrowInvalidParametersInUrlExceptionInGetPageStartNumber() {
        paginationService.getPageStartNumber(ERROR_NUMBER_PAGE, DEFAULT_SEARCH);
    }

    @Test
    public void shouldGetAmountPagesToDisplay() {
        int amountPagesToDisplay = paginationService.getAmountPagesToDisplay(FIRST_PAGE, DEFAULT_SEARCH);

        Assert.assertEquals(MAX_PAGE_NUMBER, amountPagesToDisplay);
    }

    @Test
    public void shouldGetMaxPageNumber() {
        int maxPageNumber = paginationService.getMaxPageNumber(DEFAULT_SEARCH);

        Assert.assertEquals(MAX_PAGE_NUMBER, maxPageNumber);
    }

    @Test
    public void shouldGetNewPage() {

    }

    @Test(expected = InvalidParametersInUrlException.class)
    public void shouldThrowInvalidParametersInUrlExceptionInGetNewPage() {

    }
}
