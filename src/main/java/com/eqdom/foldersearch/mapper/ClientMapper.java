package com.eqdom.foldersearch.mapper;

import com.eqdom.foldersearch.dto.ClientDto;
import com.eqdom.foldersearch.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();

        dto.setCni(client.getCni());
        dto.setNom(client.getNom());
        dto.setPrenoms(client.getPrenoms());
        dto.setAdresse(client.getAdresse());
        dto.setTelephone(client.getTelephone());

        return dto;
    }
}
