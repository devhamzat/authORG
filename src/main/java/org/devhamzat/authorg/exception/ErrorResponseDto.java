package org.devhamzat.authorg.exception;

import lombok.Data;
import org.devhamzat.authorg.utils.Status;

@Data
public class ErrorResponseDto {
    private Status status;
    private String message;
    private int statusCode;
}
