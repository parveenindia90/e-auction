package com.auction.seller.app.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProductInfo {

    private String productName;

    private String shortDesc;

    private String detailsDesc;

    private String category;

    private String startingPrice;

    private Date bidEndDate;

    private String errorMessage;
}
