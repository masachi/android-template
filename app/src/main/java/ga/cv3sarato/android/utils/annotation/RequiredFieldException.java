package ga.cv3sarato.android.utils.annotation;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.utils.toast.ToastUtils;

public class RequiredFieldException extends Exception {

    private int fieldName;
    private String errorMessage;

    public RequiredFieldException(int fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public RequiredFieldException(int fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldName() {
        return fieldName;
    }

    public void setFieldName(int fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
