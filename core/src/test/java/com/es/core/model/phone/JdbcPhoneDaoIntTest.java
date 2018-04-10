package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationTestContext-core.xml")
public class JdbcPhoneDaoIntTest {

    private final Long phoneIdWithEmptyColorSet = 1013L;
    private final Set<Color> expectedColorSetForPhoneId1017 = new HashSet<>(Arrays.asList(
            new Color(1000L,"Black"),
            new Color(1003L, "Blue"),
            new Color(1004L, "Red")
    ));
    private final List<Phone> orderedByPricePhonesWithQueryBlackBerry = Arrays.asList(
            new Phone(1983L, "BlackBerry",	"BlackBerry Curve 3G", BigDecimal.valueOf(100)),
            new Phone(1975L, "BlackBerry",	"BlackBerry Bold 9780 T-Mobile", BigDecimal.valueOf(130))
    );
    private final List<Phone> orderedByPricePhonesLimitOfSix= Arrays.asList(
            new Phone(1065L, "AT&T",	"AT&T R225", BigDecimal.valueOf(20)),
            new Phone(1497L, "Amazon",	"Amazon Fire", BigDecimal.valueOf(50)),
            new Phone(1422L, "Alcatel",	"Alcatel OneTouch PIXI 7", BigDecimal.valueOf(79)),
            new Phone(1011L, "ARCHOS",	"ARCHOS 40 Cesium", BigDecimal.valueOf(99))
            );
    private final List<Phone> orderedByPriceDescPhonesLimitOfThirteen= Arrays.asList(
            new Phone(1578L, "Apple",	"Apple iPad Pro 12.9-inch", BigDecimal.valueOf(1229)),
            new Phone(1646L, "Asus",	"Asus PadFone Infinity", BigDecimal.valueOf(1200)),
            new Phone(1577L, "Apple",	"Apple iPad Pro 10.5-inch", BigDecimal.valueOf(1079)),
            new Phone(1597L, "Apple",	"Apple iPhone 6s Plus", BigDecimal.valueOf(1069))
    );

    @Resource
    private PhoneDao phoneDao;

    @Test
    @Transactional
    public void testGetLowerBoundInPhones() {
        assertEquals((long)phoneDao.get(1000L).get().getId(),1000L);
    }

    @Test
    @Transactional
    public void testGetUpperBoundInPhones() {
        assertEquals((long)phoneDao.get(1999L).get().getId(),1999L);
    }

    @Test
    @Transactional
    public void testPhoneUpdate() {
        Phone phone = phoneDao.get(1001L).get();
        phone.setDescription("");
        phoneDao.save(phone);
        assertEquals(phoneDao.get(1001L).get().getDescription(),"");
    }

    @Test
    @Transactional
    public void testPhoneInsert() {
        Phone phone = new Phone();
        phone.setBrand("Brandfff");
        phone.setModel("Model");
        phoneDao.save(phone);
        Phone insertedPhone = phoneDao.get(phone.getId()).get();
        assertTrue(insertedPhone.getId().equals(phone.getId()));
        assertTrue(insertedPhone.getBrand().equals(phone.getBrand()));
        assertTrue(insertedPhone.getModel().equals(phone.getModel()));
    }

    @Test
    @Transactional
    public void testGetColorSet() {
        Set<Color> actualColorset = phoneDao.get(1017L).get().getColors();
        actualColorset.equals(expectedColorSetForPhoneId1017);
    }

    @Test
    @Transactional
    @SuppressWarnings("unchecked")
    public void testGetColorSetAfterUpdate() {
        Phone phone = phoneDao.get(1017L).get();
        Set<Color> expectedColorSet = phone.getColors();
        expectedColorSet.add(new Color(1010L, "Silver"));
        phone.setColors(expectedColorSet);
        phoneDao.save(phone);
        Set<Color> actualColorSet = phoneDao.get(1017L).get().getColors();
        assertTrue(actualColorSet.equals(expectedColorSet));
    }

    @Test
    @Transactional
    public void testGetEmptyColorSet() {
        Set<Color> actualColorSet = phoneDao.get(phoneIdWithEmptyColorSet).get().getColors();
        Set<Color> expectedColorSet = new HashSet<>();
        assertTrue(actualColorSet.equals(expectedColorSet));
    }

    @Test
    @Transactional
    public void testFindAllInOrderWithQuery() {
        List<Phone> expectedOrderedByPricePhoneList = phoneDao.findAllInOrder(0,2,OrderEnum.PRICE, "blackberry");
        assertEquals(expectedOrderedByPricePhoneList.size(), orderedByPricePhonesWithQueryBlackBerry.size());
        assertPhones(expectedOrderedByPricePhoneList, orderedByPricePhonesWithQueryBlackBerry);
    }

    @Test
    @Transactional
    public void testFindAllInAscendindOrderWithoutQuery() {
        List<Phone> orderedByPricePhoneList = phoneDao.findAllInOrder(0,6,OrderEnum.PRICE, null);
        orderedByPricePhoneList.forEach(e -> System.out.println(e.getId() + " " + e.getModel() + e.getBrand() + e.getPrice()));
        assertEquals(orderedByPricePhoneList.size(), orderedByPricePhonesLimitOfSix.size());
        assertPhones(orderedByPricePhoneList, orderedByPricePhonesLimitOfSix);
    }

    @Test
    @Transactional
    public void testFindAllInDescendindOrderWithoutQuery() {
        List<Phone> orderedByPriceDescPhoneList = phoneDao.findAllInOrder(0,13,OrderEnum.PRICE_DESC, null);
        assertEquals(orderedByPriceDescPhoneList.size(), orderedByPriceDescPhonesLimitOfThirteen.size());
        assertPhones(orderedByPriceDescPhoneList, orderedByPriceDescPhonesLimitOfThirteen);
    }

    private void assertPhones(List<Phone> expectedPhoneList, List<Phone> actualPhoneList) {
        for(int i = 0; i < expectedPhoneList.size(); i++) {
            Phone actualPhone = actualPhoneList.get(i);
            Phone expectedPhone = expectedPhoneList.get(i);
            assertEquals(expectedPhone.getId(), actualPhone.getId());
            assertEquals(expectedPhone.getBrand(), actualPhone.getBrand());
            assertEquals(expectedPhone.getModel(), actualPhone.getModel());
        }
    }
}
