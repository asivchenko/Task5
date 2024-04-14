package org.example.dataRequest.Enum;

import lombok.Data;


public enum ProductRegistryType {
    NSO("NSO"),   ///Проблемы с кирилицей при воде утилитой curl  этого нет  а только при вводе request SOAPUI

    SMO("CMO"),
    EGO("ЕЖОЭ"),
    DBDS("ДБВС");

    public String getProductRegistryType() {
        return productRegistryType;
    }

    private  final String productRegistryType;
    ProductRegistryType(String productRegistryType)
    {

        this.productRegistryType= productRegistryType;
    }
    public String getvalue (){
        return  productRegistryType;
    }
    public static ProductRegistryType  fromValue  (String value )
    {
        for (ProductRegistryType productRegistryType : ProductRegistryType.values())
        {
            if (productRegistryType.getvalue().equals(value))
                return productRegistryType;
        }
        throw new IllegalArgumentException("НЕ известное productregistrytype=" +value);

    }

}
