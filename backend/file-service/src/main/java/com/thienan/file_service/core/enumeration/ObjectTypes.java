package com.thienan.file_service.core.enumeration;

public enum ObjectTypes {
    PRODUCT_VARIANT("product-variant");
    private final String value;
    private ObjectTypes(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }
}
