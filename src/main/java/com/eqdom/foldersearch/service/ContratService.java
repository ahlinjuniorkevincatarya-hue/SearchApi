package com.eqdom.foldersearch.service;

import com.eqdom.foldersearch.ContractType;
import com.eqdom.foldersearch.Statut;
import com.eqdom.foldersearch.client.SignatureClient;
import com.eqdom.foldersearch.dto.ContratDto;
import com.eqdom.foldersearch.dto.SignatureRequest;
import com.eqdom.foldersearch.dto.SignatureResponse;
import com.eqdom.foldersearch.entity.Client;
import com.eqdom.foldersearch.entity.Contrat;
import com.eqdom.foldersearch.entity.Dossier;
import com.eqdom.foldersearch.exception.DuplicateResourceException;
import com.eqdom.foldersearch.exception.ResourceNotFoundException;
import com.eqdom.foldersearch.mapper.ContractMapper;
import com.eqdom.foldersearch.repository.ContratRepository;
import com.eqdom.foldersearch.repository.DossierRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.eqdom.foldersearch.util.PdfGenerator;

import java.util.List;

@Service
public class ContratService {
    private final DossierRepository dossierRepository;
    private final ContratRepository contractRepository;
    private final TemplateEngine templateEngine;
    private final PdfGenerator pdfGenerator;
    private final MinioService minioService;
    private final ContractMapper contractMapper;
    private final SignatureClient signatureClient;

    public ContratService(DossierRepository dossierRepository,ContratRepository contractRepository, TemplateEngine templateEngine, PdfGenerator pdfGenerator, MinioService minioService, ContractMapper contractMapper,SignatureClient signatureClient) {
        this.dossierRepository = dossierRepository;
        this.contractRepository = contractRepository;
        this.templateEngine = templateEngine;
        this.pdfGenerator = pdfGenerator;
        this.minioService = minioService;
        this.contractMapper = contractMapper;
        this.signatureClient = signatureClient;
    }

    public String getNumDossier(String referenceContrat) {

        Contrat contrat = contractRepository
                .findByReferenceContrat(referenceContrat)
                .orElseThrow(() ->
                        new RuntimeException("Contrat introuvable"));

        return contrat.getDossier().getNumDossier();
    }

    public void updateStatutDossier(Dossier dossier) {

        List<Contrat> contrats = dossier.getContrats();

        if (contrats.isEmpty()) {
            dossier.setStatut(Statut.VIDE);
        }
        else if (contrats.stream().allMatch(c -> c.getStatut() == Statut.SIGNE)) {
            dossier.setStatut(Statut.SIGNE);
        }
        else {
            dossier.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        }

        dossierRepository.save(dossier);
    }

    public ContratDto generateContract(String numDossier, ContractType type)throws Exception{

        Context context = new Context();
        Dossier dossier = dossierRepository.findByNumDossier(numDossier).orElseThrow();
        String reference = "CTR-" + dossier.getNumDossier()+"-"+type.name();


        if(contractRepository.existsByDossierAndType(dossier, type)){
            throw new DuplicateResourceException("Contrat deja existant pour ce type");
        }

        Client client = dossier.getClient();

        context.setVariable("dossier", dossier);
        context.setVariable("client", client);
        context.setVariable("type", type);

        String html = templateEngine.process("contrat",context);

        byte[] pdf = pdfGenerator.generatePdf(html);

        String key =reference+".pdf";

        minioService.uploadFile(key,pdf);

        Contrat contrat = new Contrat();

        contrat.setReferenceContrat(reference);
        contrat.setType(type);
        contrat.setStatut(Statut.EN_ATTENTE_DE_SIGNATURE);
        contrat.setDocumentOriginal(key);
        contrat.setDossier(dossier);

        Contrat saved = contractRepository.save(contrat);
        updateStatutDossier(dossier);

        return contractMapper.toDto(saved);
    }

    public byte[] getPdf(String referenceContrat) throws Exception{
        Contrat contrat = contractRepository
                    .findByReferenceContrat(referenceContrat)
                    .orElseThrow(()-> new ResourceNotFoundException("contrat " + referenceContrat + " introuvable"));

        String documentKey;

        documentKey = contrat.getDocumentOriginal();

        return minioService.downloadFile(documentKey);

    }

    public SignatureResponse requestSignature(String referenceContrat) throws Exception {

        Contrat contrat = contractRepository.findByReferenceContrat(referenceContrat)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat " + referenceContrat + " introuvable"));

        if (contrat.getStatut() != Statut.EN_ATTENTE_DE_SIGNATURE) {
            throw new IllegalStateException("Le contrat n'est pas en attente de signature.");
        }

        byte[] pdf = minioService.downloadFile(contrat.getDocumentOriginal());

        SignatureRequest request = new SignatureRequest();

        request.setDocumentId(contrat.getReferenceContrat());

        // Temporaire
        request.setSignataireEmail("tpktheone@gmail.com");

        request.setPdf(pdf);

        return signatureClient.requestSignature(request);
    }

    public void saveEnvelopeId(String referenceContrat, String envelopeId) throws Exception {
        Contrat contrat = contractRepository.findByReferenceContrat(referenceContrat).orElseThrow(()-> new ResourceNotFoundException("contrat " + referenceContrat + " introuvable"));
        contrat.setEnvelopeId(envelopeId);
        contractRepository.save(contrat);
    }

    public String getEnvelopeId(String referenceContrat) throws Exception {
        Contrat contrat = contractRepository.findByReferenceContrat(referenceContrat).orElseThrow(() -> new ResourceNotFoundException("contrat " + referenceContrat + " introuvable"));
        return contrat.getEnvelopeId();
    }

    public void saveSignedDocument(String referenceContrat, byte[] pdf) throws Exception {

        Contrat contrat = contractRepository
                .findByReferenceContrat(referenceContrat)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contrat " + referenceContrat + " introuvable"));

        String key = "CTR-" + referenceContrat + "-signed.pdf";

        key = minioService.uploadFile(key, pdf);

        contrat.setDocumentSigne(key);

        contrat.setStatut(Statut.SIGNE);

        contractRepository.save(contrat);
        updateStatutDossier(contrat.getDossier());
    }

    public void afterSignature(String referenceContrat) {

        signatureClient.afterSignature(referenceContrat);

    }

    public byte[] getSignedPdf(String referenceContrat) throws Exception {

        Contrat contrat = contractRepository
                .findByReferenceContrat(referenceContrat)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat " + referenceContrat + " introuvable"));

        if (contrat.getDocumentSigne() == null) {
            throw new IllegalStateException("Le contrat n'est pas encore signé.");
        }

        return minioService.downloadFile(contrat.getDocumentSigne());
    }

    public String getSignatureUrl(String referenceContrat) throws Exception {
        return signatureClient.getSignatureUrl(referenceContrat);
    }


}
