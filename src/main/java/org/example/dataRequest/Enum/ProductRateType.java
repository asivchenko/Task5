package org.example.dataRequest.Enum;

public enum ProductRateType {
        DIFERENCE("дифференцированная"),
        PROGRESS("PROGRESS") ; //  ("прогрессивная");

        public String getProductRateType() {
            return productRateType;
        }

        private  final String productRateType;
        ProductRateType(String productRateType)
        {

            this.productRateType= productRateType;
        }
        public String getvalue (){
            return  productRateType;
        }
        public static org.example.dataRequest.Enum.ProductRateType fromValue  (String value )
        {
            for (ProductRateType productRateType : ProductRateType.values())
            {
                if (productRateType.getvalue().equals(value))
                    return productRateType;
            }
            throw new IllegalArgumentException("НЕ известное productRateType=" +value);

        }
    }

