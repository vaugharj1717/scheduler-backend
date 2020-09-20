package com.Utilities;

public class Response {
    private Object responseObject;      //object to return to frontend
    private boolean ok;                 //false if there is Exception
    private boolean success;            //false if business reason for failure of request

    public Response(Object responseObject, boolean ok, boolean success) {
        this.responseObject = responseObject;
        this.ok = ok;
        this.success = success;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
