package com.geico.mobile.android.ace.geicoapppresentation.framework.widget;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.geico.mobile.android.ace.geicoapppresentation.policy.AcePhoneNumberFormattingTextWatcher;

/**
 * {@link android.widget.EditText} that sets its own {@link AcePhoneNumberFormattingTextWatcher}
 * to format phone number as the user types.
 *
 * @author Austin Morgan
 */
public class AcePhoneNumberFormattingEditText<T extends AcePhoneNumberFormattingTextWatcher> extends AppCompatEditText {
    private TextWatcher textWatcher;
    public AcePhoneNumberFormattingEditText(@NonNull Context context) {
        super(context);
        setUpView();
    }

    public AcePhoneNumberFormattingEditText(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public AcePhoneNumberFormattingEditText(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        setInputType(InputType.TYPE_CLASS_PHONE);
        setKeyListener(DigitsKeyListener.getInstance(false, false));
        textWatcher = createTextWatcher();
        addTextChangedListener(textWatcher);
    }

    protected TextWatcher createTextWatcher() {
        return new AcePhoneNumberFormattingTextWatcher(this);
    }

    protected TextWatcher getTextWatcher(){
        return textWatcher;
    }
}
