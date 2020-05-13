package com.octo.bankoperations.exceptions;

import com.octo.bankoperations.enums.VirementStatus;

public class IllegalReceivedVirementStatusException extends IllegalArgumentException {
    public IllegalReceivedVirementStatusException(VirementStatus status) {
        super("Received external virement with status :" + status);
    }
}
