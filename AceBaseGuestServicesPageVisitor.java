package com.geico.mobile.android.ace.geicoappbusiness.firststart;

import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesPageEnum.AceGuestServicesPageVisitor;

public abstract class AceBaseGuestServicesPageVisitor<I, O> implements AceGuestServicesPageVisitor<I, O> {

    @Override
    public O visitActivateAccountPage(I input) {
        return visitAny(input);
    }

    protected abstract O visitAny(I input);

    @Override
    public O visitIntentPage(I input) {
        return visitAny(input);
    }

    @Override
    public O visitLetsGetYouLoggedInPage(I input) {
        return visitAny(input);
    }

    @Override
    public O visitPolicyHolderPage(I input) {
        return visitAny(input);
    }

    @Override
    public O visitTelematicsEnrollmentPage(I input) {
        return visitAny(input);
    }

    @Override
    public O visitWelcomePage(I input) {
        return visitAny(input);
    }
}
