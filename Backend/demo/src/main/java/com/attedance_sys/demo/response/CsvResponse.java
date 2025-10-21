package com.attedance_sys.demo.response;

import lombok.Data;

@Data
public class CsvResponse {
    private String csvToString ;

    public CsvResponse(String csvToString) {
        this.csvToString = csvToString;
    }
}
