package com.up959875.mobiletraveljournal.other;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormHandler {

    public FormHandler(){

    }

    public void addWatcher(final TextInputEditText input, final TextInputLayout layout) {
        input.addTextChangedListener(new TextInputWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (input.hasFocus())
                    validateInput(input, layout);
            }
        });
    }


    public boolean validateInput(TextInputEditText input, TextInputLayout layout) {
        String value = input.getText() == null ? "" : input.getText().toString();
        layout.setErrorEnabled(true);
        if (value.isEmpty()) {
            layout.setError("Field cannot be empty");
            input.requestFocus();
            return false;
        } else if (input.getInputType() == (InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + InputType.TYPE_CLASS_TEXT) && !isValidEmail(value)) {
            layout.setError("Invalid email");
            input.requestFocus();
            return false;
        } else
            layout.setErrorEnabled(false);
        return true;
    }


    public boolean validateInputsEquality(TextInputEditText input1, TextInputEditText input2, TextInputLayout layout2) {
        if (!isEqual(input1, input2)) {
            if(input1.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT) {
                layout2.setError("Password does not match");
                input2.requestFocus();
            }
            else if (input1.getInputType() == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + InputType.TYPE_CLASS_TEXT) {
                layout2.setError("Email does not match");
                input2.requestFocus();
            }
            return false;
        } else
            layout2.setErrorEnabled(false);
        return true;
    }

    private boolean isEqual(TextInputEditText input1, TextInputEditText input2) {
        if (input1.getText() != null && input2.getText() != null)
            return input1.getText().toString().equals(input2.getText().toString());
        else return false;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void clearInput(TextInputEditText input, TextInputLayout layout) {
        clearText(input);
        offWatcher(layout);
        clearFocus(input);
    }


    private void clearText(TextInputEditText input) {
        input.setText("");
    }


    private void offWatcher(TextInputLayout layout) {
        layout.setErrorEnabled(false);
    }


    private void clearFocus(TextInputEditText input) {
        input.clearFocus();
    }
}