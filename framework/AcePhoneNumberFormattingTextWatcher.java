package com.geico.mobile.android.ace.geicoapppresentation.policy;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Formats US phone numbers as user types while limiting length to 10 numeric characters.
 * Overrides default behavior of PhoneNumberFormattingTextWatcher which treats phone number
 * inputs beginning with 1 as containing a country code. This class will only display as a
 * 10-digit phone number "(###) ###-####" regardless of input numbers to maintain consistent
 * behavior across platforms.
 *
 * @author Austin Morgan
 */
public class AcePhoneNumberFormattingTextWatcher extends PhoneNumberFormattingTextWatcher {

    private final EditText phoneNumberView;
    private boolean shouldConsiderLimitingPhoneNumberLength = true;

    public AcePhoneNumberFormattingTextWatcher(@NonNull EditText phoneNumberView) {
        this.phoneNumberView = phoneNumberView;
    }

    @Override
    public synchronized void afterTextChanged(Editable editable) {
        // Avoid infinite loop of TextWatcher methods caused by calling EditText.setText()
        if (shouldConsiderLimitingPhoneNumberLength) {
            considerLimitingPhoneNumberLengthToTenDigits(editable);
            return;
        }
        shouldConsiderLimitingPhoneNumberLength = true;
    }

    private void considerLimitingPhoneNumberLengthToTenDigits(Editable editable) {
        String phoneNumberDigits = editable.toString().replaceAll("[\\D]", "");
        if (phoneNumberDigits.length() >= 10) {
            setPhoneNumberText(phoneNumberDigits.substring(0, 10));
            return;
        }
    }

    protected void setPhoneNumberText(String phoneNumber) {
        shouldConsiderLimitingPhoneNumberLength = false;
        phoneNumberView.setText(PhoneNumberUtils.formatNumber(phoneNumber, Locale.US.getCountry()));
        // Reset cursor to the end of the string because EditText.setText() will move it to the beginning
        phoneNumberView.setSelection(phoneNumberView.getText().length());
    }
}
