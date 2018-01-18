package au.com.rainmore.modules.mails.resources.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto implements Serializable {

    private Map<String, String> info = new HashMap<>();
    private Map<String, String> errors = new HashMap<>();
    private Map<String, String> warn = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public ResponseDto setErrors(Map<String, String> errors) {
        this.errors = errors;
        return this;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public ResponseDto setInfo(Map<String, String> info) {
        this.info = info;
        return this;
    }

    public Map<String, String> getWarn() {
        return warn;
    }

    public ResponseDto setWarn(Map<String, String> warn) {
        this.warn = warn;
        return this;
    }
}
