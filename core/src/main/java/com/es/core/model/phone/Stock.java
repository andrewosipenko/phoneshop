package com.es.core.model.phone;

public class Stock {
    private Phone phone;
    private Integer stock;
    private Integer reserved;

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock1 = (Stock) o;

        if (phone != null ? !phone.equals(stock1.phone) : stock1.phone != null) return false;
        if (stock != null ? !stock.equals(stock1.stock) : stock1.stock != null) return false;
        return reserved != null ? reserved.equals(stock1.reserved) : stock1.reserved == null;
    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (reserved != null ? reserved.hashCode() : 0);
        return result;
    }
}
