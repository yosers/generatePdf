package com.test.maybank.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonRs<T> {
 
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Long total;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;

    public CommonRs(int status, String message) {
            
        super();
        this.status = status;
        this.message = message;
        this.total = null;
    }
}
