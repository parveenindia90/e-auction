package com.auction.buyer.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiResponse {

    private boolean success;

    private String message;
}
