package com.octo.bankoperations.mapper;

import com.octo.bankoperations.domain.Utilisateur;
import com.octo.bankoperations.dto.UtilisateurDTO;

public class UtilisateurMapper {

    public static UtilisateurDTO map(Utilisateur utilisateur){
        if(utilisateur == null) return null;
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setFirstname(utilisateur.getFirstname());
        utilisateurDTO.setLastname(utilisateur.getLastname());
        utilisateurDTO.setBirthdate(utilisateur.getBirthdate());
        utilisateurDTO.setAdresse1(utilisateur.getAdresse().getAdresse1());
        utilisateurDTO.setAdresse2(utilisateur.getAdresse().getAdresse2());
        utilisateurDTO.setVille(utilisateur.getAdresse().getVille());
        utilisateurDTO.setGender(utilisateur.getGender());
        utilisateurDTO.setUsername(utilisateur.getUsername());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        return utilisateurDTO;
    }
}
