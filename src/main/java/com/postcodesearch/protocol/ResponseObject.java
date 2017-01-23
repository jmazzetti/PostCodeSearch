package com.postcodesearch.protocol;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ResponseObject {

    @JsonInclude(Include.NON_NULL)
    private Map<String, Object> result;
    @JsonInclude(Include.NON_NULL)
    private Collection<ErrorObject> errors;
    private String id;

    private ResponseObject() {
    }

    public static ResponseObject responseSuccess(final String id, final Map<String, Object> result) {
        ResponseObject resp = new ResponseObject();
        resp.setResult(result);
        resp.setId(id);
        return resp;
    }


    public static ResponseObject responseFailure(final String id, final String code, final String message, final Map<String, Object> result) {
        ResponseObject resp = new ResponseObject();

        Collection<ErrorObject> errors = new ArrayList<>();
        errors.add(new ErrorObject(code, message));

        resp.setErrors(errors);
        resp.setId(id);
        resp.setResult(result);
        return resp;
    }

    public static ResponseObject responseFailure(final String id, final Collection<ErrorObject> errors, final Map<String, Object> result) {
        ResponseObject resp = new ResponseObject();

        resp.setErrors(errors);
        resp.setId(id);
        resp.setResult(result);
        return resp;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Collection<ErrorObject> getErrors() {
        return errors;
    }

    public void setErrors(Collection<ErrorObject> errors) {
        this.errors = errors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
