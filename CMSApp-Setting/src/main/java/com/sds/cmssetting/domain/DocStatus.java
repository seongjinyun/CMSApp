package com.sds.cmssetting.domain;

public enum DocStatus {
	DRAFT(100, "초안", "draft"),
    IN_REVIEW(200, "리뷰 중", "inReview"),
    REVIEWED(300, "리뷰 완료", "reviewed"),
    REJECTED(500, "반려", "rejected");

    private final int statusCode;
    private final String statusName;
    private final String statusNameEn;

    DocStatus(int statusCode, String statusName, String statusNameEn) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.statusNameEn = statusNameEn;
    }

    public int getStatusCode() {
        return statusCode;
    }
    
    public String getStatusName() {
    	return statusName;
    }
    
    public String getStatusNameEn() {
    	return statusNameEn;
    }
}