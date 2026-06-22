package com.eqdom.foldersearch.service;

import com.eqdom.foldersearch.Statut;
import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.entity.Client;
import com.eqdom.foldersearch.entity.Contrat;
import com.eqdom.foldersearch.entity.Dossier;
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

    public ContractService(DossierRepository dossierRepository,ContratRepository contractRepository, TemplateEngine templateEngine, PdfGenerator pdfGenerator) {
        this.dossierRepository = dossierRepository;
        this.contractRepository = contractRepository;
        this.templateEngine = templateEngine;
        this.pdfGenerator = pdfGenerator;
    }

    public ContratDto generateContract(String numDossier)throws Exception{
        Context context = new Context();

        Dossier dossier = dossierRepository.findByNumDossier(numDossier).orElseThrow();
        Client client = dossier.getClient();

        context.setVariable("dossier", dossier);
        context.setVariable("client", client);

        String html = templateEngine.process("contrat",context);
        String filename ="contrat_"+ numDossier + ".pdf";

        String cheminPdf = pdfGenerator.generatePdf(html,filename);

        Contrat contrat = new Contrat();
        contrat.setNumContrat("C-" + numDossier);
        contrat.setType("Contrat de crédit");
        contrat.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat.setDocumentOriginal(cheminPdf);
        contrat.setDocumentOriginal(cheminPdf);
        contrat.setDossier(dossier);

        Contrat saved = contractRepository.save(contrat);

        ContratDto dto = new ContratDto();

        dto.setNumContrat(saved.getNumContrat());
        dto.setType(saved.getType());
        dto.setStatut(saved.getStatut().name());

        dto.setNumDossier(saved.getDossier().getNumDossier());
        dto.setNomClient(saved.getDossier().getClient().getNom());

        return dto;
    }


}
