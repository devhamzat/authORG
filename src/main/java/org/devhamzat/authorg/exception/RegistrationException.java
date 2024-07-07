package org.devhamzat.authorg.exception;

public class RegistrationException extends RuntimeException{
    private int statusCode;
    public RegistrationException(String message,int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode(){
        return statusCode;
    }
}
