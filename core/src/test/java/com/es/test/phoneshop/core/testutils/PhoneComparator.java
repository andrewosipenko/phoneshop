package com.es.test.phoneshop.core.testutils;

import com.es.phoneshop.core.phone.model.Color;
import com.es.phoneshop.core.phone.model.Phone;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PhoneComparator implements Comparator<Phone> {
    @Override
    public int compare(Phone o1, Phone o2) {
        if (o1 == o2)
            return 0;
        if (o1 == null || o2 == null || o1.getClass() != o2.getClass())
            return 1;
        Object[][] compared = {
                {o1.getId(), o2.getId()}, {o1.getBrand(), o2.getBrand()}, {o1.getModel(), o2.getModel()}, {o1.getWeightGr(), o2.getWeightGr()},
                {o1.getAnnounced(), o2.getAnnounced()}, {o1.getDeviceType(), o2.getDeviceType()}, {o1.getOs(), o2.getOs()},
                {o1.getDisplayResolution(), o2.getDisplayResolution()}, {o1.getPixelDensity(), o2.getPixelDensity()},
                {o1.getDisplayTechnology(), o2.getDisplayTechnology()}, {o1.getBatteryCapacityMah(), o2.getBatteryCapacityMah()},
                {o1.getBluetooth(), o2.getBluetooth()}, {o1.getPositioning(), o2.getPositioning()}, {o1.getImageUrl(), o2.getImageUrl()},
                {o1.getDescription(), o2.getDescription()}
        };
        for (Object[] comparedPair : compared)
            if (!Objects.equals(comparedPair[0], comparedPair[1]))
                return 1;
        BigDecimal[][] bigDecimalsCompared = {
                {o1.getBackCameraMegapixels(), o2.getBackCameraMegapixels()}, {o1.getDisplaySizeInches(), o2.getDisplaySizeInches()},
                {o1.getFrontCameraMegapixels(), o2.getFrontCameraMegapixels()}, {o1.getHeightMm(), o2.getHeightMm()},
                {o1.getInternalStorageGb(), o2.getInternalStorageGb()}, {o1.getLengthMm(), o2.getLengthMm()}, {o1.getWidthMm(), o2.getWidthMm()},
                {o1.getPrice(), o2.getPrice()}, {o1.getRamGb(), o2.getRamGb()}, {o1.getStandByTimeHours(), o2.getStandByTimeHours()},
                {o1.getTalkTimeHours(), o2.getTalkTimeHours()}
        };
        for (BigDecimal[] comparedPair : bigDecimalsCompared)
            if (Objects.compare(comparedPair[0], comparedPair[1], Comparator.naturalOrder()) != 0)
                return 1;
        return compareColors(o1.getColors(), o2.getColors());
    }

    private int compareColors(Set<Color> colors1, Set<Color> colors2) {
        if (colors1.size() != colors2.size())
            return 1;
        List<Color> colorList1 = colors1.stream().sorted(Comparator.comparing(Color::getId)).collect(Collectors.toList());
        List<Color> colorList2 = colors2.stream().sorted(Comparator.comparing(Color::getId)).collect(Collectors.toList());
        int n = colors1.size();
        for (int i = 0; i < n; i++) {
            Color color1 = colorList1.get(i);
            Color color2 = colorList2.get(i);
            if (!Objects.equals(color1.getId(), color2.getId()) || !Objects.equals(color1.getCode(), color2.getCode()))
                return 1;
        }
        return 0;
    }
}
