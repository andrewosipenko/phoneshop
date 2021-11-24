package com.es.core.model.phone;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PhoneSearchComparator implements Comparator<Phone> {
    private final String[] words;

    public PhoneSearchComparator(String[] words) {
        this.words = words;
    }

    private int getCoincidencePercent(Phone phone) {
        List<String> phoneModelWords = Arrays.asList(phone.getModel().trim().toLowerCase().split("\\s+"));
        int coincidenceCount = 0;
        for (String word : words) {
            if (phoneModelWords.contains(word)) {
                coincidenceCount++;
            }
        }
        return  coincidenceCount * 100 / phoneModelWords.size();
    }

    public int compare(Phone o1, Phone o2) {
        return getCoincidencePercent(o2) - getCoincidencePercent(o1);
    }
}
