package org.lpw.photon.ctrl.validate;

import org.springframework.stereotype.Controller;

@Controller(Validators.NOT_EQUALS)
public class NotEqualsValidatorImpl extends ValidatorSupport {
    @Override
    public boolean validate(ValidateWrapper validate, String[] parameters) {
        if (parameters[0] == null)
            return parameters[1] != null;

        return !parameters[0].equals(parameters[1]);
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return Validators.PREFIX + "equals";
    }
}
