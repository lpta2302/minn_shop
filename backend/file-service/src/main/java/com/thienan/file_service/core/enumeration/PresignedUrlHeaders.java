package com.thienan.file_service.core.enumeration;

public enum PresignedUrlHeaders {
    ACL("x-amz-acl");
    private final String value;
    private PresignedUrlHeaders(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }
}
