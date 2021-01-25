package org.lpw.photon.ctrl.validate;

/**
 * @author lpw
 */
public abstract class IdValidatorSupport extends ValidatorSupport {
    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return validator.isId(parameter);
    }
}
