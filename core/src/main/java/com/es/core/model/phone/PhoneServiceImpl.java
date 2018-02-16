package com.es.core.model.phone;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public Optional<Phone> get(Long key) {
        return phoneDao.get(key);
    }

    @Override
    public List<Phone> findInOrder(String orderBy, int offset, int limit) {
        return phoneDao.findByModelInOrder("%", orderBy, offset, limit);
    }

    @Override
    public List<Phone> findByModelInOrder(String model, String orderBy, int offset, int limit) {
        return phoneDao.findByModelInOrder("%" + model + "%", orderBy, offset, limit);
    }

    @Override
    public long productsCount() {
        return phoneDao.productsCountWithModel("%");
    }

    @Override
    public long productsCountByModel(String model) {
        return phoneDao.productsCountWithModel("%" + model + "%");
    }

    @Override
    public Optional<Phone> getByIdAndColor(long id, String colorCode) {
        Optional<Phone> phoneOptional = phoneDao.get(id);

        if (phoneOptional.isPresent()) {
            Phone phone = phoneOptional.get();
            List<Color> colors = phone.getColors().stream()
                    .filter(color1 -> color1.getCode().equals(colorCode))
                    .collect(Collectors.toList());
            if (!colors.isEmpty()) {
                phone.setColors(new HashSet<>(colors));
                return Optional.of(phone);
            }
        }

        return Optional.empty();
    }
}
