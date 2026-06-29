package com.eqdom.foldersearch.service;

import com.eqdom.foldersearch.ContractType;
import com.eqdom.foldersearch.Statut;
import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.entity.Client;
import com.eqdom.foldersearch.entity.Contrat;
import com.eqdom.foldersearch.entity.Dossier;
import com.eqdom.foldersearch.mapper.ContractMapper;
import com.eqdom.foldersearch.repository.ContratRepository;
import com.eqdom.foldersearch.repository.DossierRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.eqdom.foldersearch.util.PdfGenerator;

@Service
public class ContractService {
    private final DossierRepository dossierRepository;
    private final ContratRepository contractRepository;
    private final TemplateEngine templateEngine;
    private final PdfGenerator pdfGenerator;
    private final MinioService minioService;
    private final ContractMapper contractMapper;

    public ContractService(DossierRepository dossierRepository,ContratRepository contractRepository, TemplateEngine templateEngine, PdfGenerator pdfGenerator, MinioService minioService, ContractMapper contractMapper) {
        this.dossierRepository = dossierRepository;
        this.contractRepository = contractRepository;
        this.templateEngine = templateEngine;
        this.pdfGenerator = pdfGenerator;
        this.minioService = minioService;
        this.contractMapper = contractMapper;
    }

    public ContratDto generateContract(String numDossier, ContractType type)throws Exception{

        Context context = new Context();
        Dossier dossier = dossierRepository.findByNumDossier(numDossier).orElseThrow();
        String reference = "CTR-" + dossier.getNumDossier()+"-"+type.name();


        if(contractRepository.existsByDossierAndType(dossier, type)){
            throw new RuntimeException("Contrat deja existant pour ce type");
        }

        Client client = dossier.getClient();

        context.setVariable("dossier", dossier);
        context.setVariable("client", client);
        context.setVariable("type", type);

        String html = templateEngine.process("contrat",context);

        byte[] pdf = pdfGenerator.generatePdf(html);

        String key = "contracts/"+reference+".pdf";

        minioService.uploadFile(key,pdf);

        Contrat contrat = new Contrat();

        contrat.setReferenceContrat(reference);
        contrat.setType(type);
        contrat.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat.setDocumentOriginal(key);
        contrat.setDossier(dossier);

        Contrat saved = contractRepository.save(contrat);

        return contractMapper.toDto(saved);
    }


}
