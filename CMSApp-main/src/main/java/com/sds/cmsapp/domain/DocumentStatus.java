package com.sds.cmsapp.domain;

public enum DocumentStatus {
	DRAFT(100),
    IN_REVIEW(200),
    REVIEWED(300),
    PUBLISHED(400),
    REJECTED(500),
	DEPRECATED(600);

    private final int statusCode;

    DocumentStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}