package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.patterns.AceModification;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserLoginSettingsFlow;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationResponse;

import java.util.ArrayList;

/**
 * A class that modifies login settings flow fields based on update user login information response
 *
 * @author John Sung, Geico
 */
public class AceUserLoginSettingsFlowModification implements AceModification<AceUserLoginSettingsFlow> {
    private final MitPrepareToUpdateUserLoginInformationResponse response;

    public AceUserLoginSettingsFlowModification(@NonNull MitPrepareToUpdateUserLoginInformationResponse response) {
        this.response = response;
    }

    @Override
    public void modify(@NonNull AceUserLoginSettingsFlow subject) {
        subject.setPasswordHint(response.getPasswordHint());
        subject.setPrepareRequestState(AceInformationState.CURRENT);
        subject.setQuestionOne(response.getSecurityQuestions().get(0));
        subject.setQuestionTwo(response.getSecurityQuestions().get(1));
        subject.setSecurityQuestionAnswers(isPasswordResetByAdmin(subject) ? new ArrayList<>() : response.getSecurityQuestionAnswers());
        subject.setSecurityQuestions(response.getSecurityQuestions());
        subject.setUserId(response.getUserId());
    }

    private boolean isPasswordResetByAdmin(AceUserLoginSettingsFlow flow) {
        return "Password Reset by Admin".equals(flow.getPasswordHint());
    }
}
