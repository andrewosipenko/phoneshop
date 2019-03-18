package com.es.core.service.phone;

import com.es.core.dao.color.ColorDao;
import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneServiceImplTest {
    private final static int OFFSET = 10;
    private final static int PAGE_SIZE = 10;
    private final static Long PHONE_ID = 1000L;
    private final static String SEARCH_TEXT = "ARCHOS";
    private final static String SORT = "model";
    private final static String ORDER = "asc";

    @InjectMocks
    private PhoneService phoneService = new PhoneServiceImpl();

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private ColorDao colorDao;

    @Test
    public void shouldReturnListOfPhoneByPage() {
        phoneService.findActivePhonesByPage(OFFSET, PAGE_SIZE);

        verify(phoneDao, times(1)).findActivePhonesByPage(OFFSET, PAGE_SIZE);
    }

    @Test
    public void shouldReturnPhone() {
        phoneService.get(PHONE_ID);

        verify(phoneDao, times(1)).get(PHONE_ID);
    }

    @Test
    public void shouldReturnListOfPhoneByPageLikeSearchText() {
        phoneService.findPhonesLikeSearchText(OFFSET, PAGE_SIZE, SEARCH_TEXT);

        verify(phoneDao, times(1)).findPhonesLikeSearchText(OFFSET, PAGE_SIZE, SEARCH_TEXT);
    }

    @Test
    public void shouldSortPhones() {
        phoneService.sortPhones(OFFSET, PAGE_SIZE, SORT, ORDER);

        verify(phoneDao, times(1)).sortPhones(OFFSET, PAGE_SIZE, SORT, ORDER);
    }

    @Test
    public void shouldReturnSortPhonesLikeSearchText() {
        phoneService.sortPhonesLikeSearchText(OFFSET, PAGE_SIZE, SORT, ORDER, SEARCH_TEXT);

        verify(phoneDao, times(1)).sortPhonesLikeSearchText(OFFSET, PAGE_SIZE, SORT, ORDER, SEARCH_TEXT);
    }

    @Test
    public void shouldReturnPageCount() {
        int expectedCount = 10;
        when(phoneDao.findPageCount()).thenReturn(100);

        int actualCount = phoneService.findPageCount(PAGE_SIZE);

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void shouldReturnPageCountOfPhonesWithSearchText() {
        int expectedCount = 10;
        when(phoneDao.findPageCountWithSearchText(SEARCH_TEXT)).thenReturn(100);

        int actualCount = phoneService.findPageCountWithSearchText(PAGE_SIZE, SEARCH_TEXT);

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void save() {
        Phone phone = new Phone();

        phoneService.save(phone);

        verify(phoneDao, times(1)).saveOrUpdate(phone);
    }
}