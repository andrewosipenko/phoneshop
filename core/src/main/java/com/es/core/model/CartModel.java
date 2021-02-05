package com.es.core.model;

import com.es.core.exceptions.OutOfStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartModel {

    private List<CartItemModel> cartItems;

    private Long totalQuantity;

    private BigDecimal totalCost;

    public CartModel() {
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public List<CartItemModel> getCartItems() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        return cartItems;
    }

    public void addToCart(CartItemModel cartItem) {
        cartItems.add(cartItem);
        recalculateBalance();
    }

    private void recalculateBalance() {
        Long resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
    }

    public Long countQuantity() {
        return getCartItems().stream()
                             .mapToLong(CartItemModel::getQuantity)
                             .sum();
    }

    public BigDecimal countCost() {
        return getCartItems().stream()
                             .reduce(BigDecimal.ZERO, ((currentCost, cartItem) -> returnTotalCost(currentCost, cartItem)),
                                     BigDecimal::add);
    }

    private BigDecimal returnTotalCost(BigDecimal currentTotalCost, CartItemModel cartItemModel) {
        BigDecimal totalCost = cartItemModel.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItemModel.getQuantity()));
        return currentTotalCost.add(totalCost);
    }

    public void updateAddingToCart(CartItemModel addedCartItem, CartItemModel cartItemToAdd) {
        if (cartItemToAdd.getQuantity() <= addedCartItem.getPhone().getStock() - addedCartItem.getQuantity()) {
            addedCartItem.setQuantity(cartItemToAdd.getQuantity() + addedCartItem.getQuantity());
            cartItems.set(cartItems.indexOf(addedCartItem), addedCartItem);
            recalculateBalance();
        } else {
            throw new OutOfStockException();
        }
    }

    public void deleteFomCart(PhoneModel phoneModelToDelete) {
        Optional<CartItemModel> cartItemModelToDelete = cartItems.stream()
                                                                 .filter(cartItemModel -> cartItemModel.getPhone().equals(phoneModelToDelete))
                                                                 .findAny();
        if (cartItemModelToDelete.isPresent()) {
            cartItems.remove(cartItemModelToDelete.get());
            recalculateBalance();
        }
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void updateCart(CartItemModel addedCartItem, CartItemModel cartItemToAdd) {
        if (cartItemToAdd.getQuantity() <= addedCartItem.getPhone().getStock()) {
            addedCartItem.setQuantity(cartItemToAdd.getQuantity());
            cartItems.set(cartItems.indexOf(addedCartItem), addedCartItem);
            recalculateBalance();
        } else {
            throw new OutOfStockException();
        }
    }
}
