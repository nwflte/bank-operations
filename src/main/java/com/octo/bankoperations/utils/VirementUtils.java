package com.octo.bankoperations.utils;

import com.octo.bankoperations.dto.VirementDTO;

import java.time.Instant;
import java.util.Random;

public class VirementUtils {
    private VirementUtils() {
    }

    public static String generateReference() {
        return Instant.now().toString() + new Random().nextLong();
    }

    public static boolean isVirementInterne(VirementDTO virementDto) {
        String codeBankEmetteur = AccountUtils.getBankCode(virementDto.getRibEmetteur());
        String codeBankBeneficiaire = AccountUtils.getBankCode(virementDto.getRibBeneficiaire());
        return AccountUtils.getBankCode(codeBankEmetteur).equals(AccountUtils.getBankCode(codeBankBeneficiaire));
    }
}
