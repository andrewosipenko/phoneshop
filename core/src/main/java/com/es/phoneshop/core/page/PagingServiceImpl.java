package com.es.phoneshop.core.page;


import com.es.phoneshop.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PagingServiceImpl implements PagingService {
    private final static int COUNT_PER_PAGE = 10;
    private final static int PAGES_ON_SCREEN = 9;
    private final static int PAGE_MIDDLE_INDEX = 5;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public int getTotalPages(String search) {
        int total;

        if (search != null) {
            total = phoneDao.searchCount(search) / COUNT_PER_PAGE + 1;
        } else {
            total = phoneDao.phoneCount() / COUNT_PER_PAGE + 1;
        }
        return total;
    }

    @Override
    public String getPageURL(String search, String order) {
        String pageUrl;

        if(search != null) {
            pageUrl = "?search=%s&order=%s";
            return String.format(pageUrl, search, order);
        } else {
            pageUrl = "?order=%s";
            return String.format(pageUrl, order);
        }
    }

    @Override
    public int[] calculatePagesNum(int page, int total) {
        if(total < PAGES_ON_SCREEN) {
            int[] pagesNum = new int[total];

            for(int i = 0; i < total; i++) {
                pagesNum[i] = i + 1;
            }

            return pagesNum;

        } else {
            int[] pagesNum = new int[PAGES_ON_SCREEN];

            if (page <= PAGE_MIDDLE_INDEX) {
                for (int i = 0; i < PAGES_ON_SCREEN; i++) {
                    pagesNum[i] = i + 1;
                }
            } else if (page > total - (PAGES_ON_SCREEN - PAGE_MIDDLE_INDEX)) {
                for (int i = 0; i < PAGES_ON_SCREEN; i++) {
                    pagesNum[i] = total - PAGES_ON_SCREEN + (i + 1);
                }
            } else {
                for (int i = 0; i < PAGES_ON_SCREEN; i++) {
                    pagesNum[i] = page - PAGE_MIDDLE_INDEX + (i + 1);
                }
            }

            return pagesNum;
        }
    }

    @Override
    public int getOffset(int page) {
        return (page - 1) * COUNT_PER_PAGE;
    }

    @Override
    public String addSearchToOrder (String search) {
        if(search != null) {
            return "&search=" + search;
        } else {
            return "";
        }
    }
}
