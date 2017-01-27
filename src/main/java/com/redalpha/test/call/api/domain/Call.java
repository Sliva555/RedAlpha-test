package com.redalpha.test.call.api.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alevkovsky on 1/25/2017.
 */
@Data
public class Call {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private final String date = SIMPLE_DATE_FORMAT.format(new Date());

}
