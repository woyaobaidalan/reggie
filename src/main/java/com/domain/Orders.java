package com.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    private Integer status;

    private Long userId;

    private Long AddressBookId;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    private Integer payMethod;

    private Double amount;

    private String remark;

    private String phone;

    private String address;

    private String userName;

    private String consignee;



}
