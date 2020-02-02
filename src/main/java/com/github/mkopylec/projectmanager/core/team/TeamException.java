package com.github.mkopylec.projectmanager.core.team;

import com.github.mkopylec.projectmanager.core.common.RequirementsValidationException;
import com.github.mkopylec.projectmanager.core.common.ValidationErrorCode;

import java.util.List;

class TeamException extends RequirementsValidationException {

    TeamException(List<Enum<? extends ValidationErrorCode>> errorCodes) {
        super(errorCodes);
    }
}