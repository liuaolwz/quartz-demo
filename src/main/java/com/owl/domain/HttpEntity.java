package com.owl.domain;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
public class HttpEntity {
    private String url;
    private HttpMethod method;
    private Boolean async;
    private Map<String,String> param;
}
