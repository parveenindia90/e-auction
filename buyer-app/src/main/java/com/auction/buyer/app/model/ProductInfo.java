package com.auction.buyer.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    private String productName;

    private String shortDesc;

    private String detailsDesc;

    private String category;

    private String startingPrice;

    private Date bidEndDate;

    private String errorMessage;
}
