package org.example.dataRequest.Enum;

import lombok.Data;


public enum ProductRegisterState {
    CLOSE("Закрыт"),
    OPEN("OPEN"),
    RESERVED("Зарезервирован"),
    DELETED("Удален");

    private final String productRegisterState;

    ProductRegisterState(String productRegisterState) {
        this.productRegisterState = productRegisterState;
    }

    public String getvalue() {
        return productRegisterState;
    }

    public static ProductRegisterState  fromValue  (String value )
    {
        for (ProductRegisterState productRegisterState : ProductRegisterState.values())
        {
            if (productRegisterState.getvalue().equals(value))
                return productRegisterState;
        }
        throw new IllegalArgumentException("НЕ известное сотояние=" +value);

    }






}