package de.uke.iam.mtb.kcapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.uke.iam.mtb.dto.kc.KcBiosampleDto;
import de.uke.iam.mtb.dto.kc.KcDiagnosisDto;
import de.uke.iam.mtb.dto.kc.KcLesionDto;
import de.uke.iam.mtb.dto.kc.KcMafDto;
import de.uke.iam.mtb.dto.kc.KcMorphologyDto;
import de.uke.iam.mtb.dto.kc.KcPatientDto;
import de.uke.iam.mtb.dto.kc.KcTherapyDto;
import de.uke.iam.mtb.kcapi.dao.KcBiosampleRepository;
import de.uke.iam.mtb.kcapi.dao.KcDiagnosisRepository;
import de.uke.iam.mtb.kcapi.dao.KcLesionRepository;
import de.uke.iam.mtb.kcapi.dao.KcMafRepository;
import de.uke.iam.mtb.kcapi.dao.KcMorphologyRepository;
import de.uke.iam.mtb.kcapi.dao.KcPatientRepository;
import de.uke.iam.mtb.kcapi.dao.KcTherapyRepository;
import de.uke.iam.mtb.kcapi.model.KcBiosampleEntity;
import de.uke.iam.mtb.kcapi.model.KcDiagnosisEntity;
import de.uke.iam.mtb.kcapi.model.KcLesionEntity;
import de.uke.iam.mtb.kcapi.model.KcMafEntity;
import de.uke.iam.mtb.kcapi.model.KcMorphologyEntity;
import de.uke.iam.mtb.kcapi.model.KcPatientEntity;
import de.uke.iam.mtb.kcapi.model.KcTherapyEntity;
import de.uke.iam.mtb.kcapi.util.KcModelMapperBuilder;

@Service
public class KcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KcService.class);

    private final ModelMapper dtoToEntityMapper = new KcModelMapperBuilder().getModelMapper();

    private final KcPatientRepository patientRepository;
    private final KcMafRepository mafRepository;
    private final KcMorphologyRepository morphologyRepository;
    private final KcTherapyRepository therapyRepository;
    private final KcDiagnosisRepository diagnosisRepository;
    private final KcLesionRepository lesionRepository;
    private final KcBiosampleRepository biosampleRepository;

    public KcService(KcPatientRepository patientRepository, KcMafRepository mafRepository,
            KcMorphologyRepository morphologyRepository, KcTherapyRepository therapyRepository,
            KcDiagnosisRepository diagnosisRepository, KcLesionRepository lesionRepository,
            KcBiosampleRepository biosampleRepository) {
        this.patientRepository = patientRepository;
        this.mafRepository = mafRepository;
        this.morphologyRepository = morphologyRepository;
        this.therapyRepository = therapyRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.lesionRepository = lesionRepository;
        this.biosampleRepository = biosampleRepository;
    }

    @Transactional
    public void savePatient(KcPatientDto patientDto) {
        ModelMapper mapper = new KcModelMapperBuilder().setKcPatientDtoToEntityConverter().setStrictMatching()
                .getModelMapper();
        patientRepository.save(mapper.map(patientDto, KcPatientEntity.class));
        LOGGER.info("Succesfully wrote " + patientDto + " to kc.pool");
    }

    @Transactional
    public void saveMafs(List<KcMafDto> mafDtos) {
        mafDtos.stream().map((dto) -> dtoToEntityMapper.map(dto, KcMafEntity.class))
                .forEach((entity) -> mafRepository.save(entity));
        LOGGER.info("Succesfully wrote " + mafDtos + " to kc.pool");
    }

    @Transactional
    public void saveMorphologies(List<KcMorphologyDto> morphologyDtos) {
        morphologyDtos.stream().map((dto) -> dtoToEntityMapper.map(dto, KcMorphologyEntity.class))
                .forEach((entity) -> morphologyRepository.save(entity));
        LOGGER.info("Succesfully wrote " + morphologyDtos + " to kc.pool");
    }

    @Transactional
    public void saveTherapies(List<KcTherapyDto> therapieDtos) {
        therapieDtos.stream().map((dto) -> dtoToEntityMapper.map(dto, KcTherapyEntity.class))
                .forEach((entity) -> therapyRepository.save(entity));
        LOGGER.info("Succesfully wrote " + therapieDtos + " to kc.pool");
    }

    @Transactional
    public void saveDiagnosis(List<KcDiagnosisDto> diagnosisDtos) {
        diagnosisDtos.stream().map((dto) -> dtoToEntityMapper.map(dto, KcDiagnosisEntity.class))
                .forEach((entity) -> diagnosisRepository.save(entity));
        LOGGER.info("Succesfully wrote " + diagnosisDtos + " to kc.pool");
    }

    @Transactional
    public void saveLesions(List<KcLesionDto> lesionDtos) {
        lesionDtos.stream().map((dto) -> dtoToEntityMapper.map(dto, KcLesionEntity.class))
                .forEach((entity) -> lesionRepository.save(entity));
        LOGGER.info("Succesfully wrote " + lesionDtos + " to kc.pool");
    }

    @Transactional
    public void saveBiosamples(List<KcBiosampleDto> biosampleDtos) {
        biosampleDtos.stream().map((dto) -> dtoToEntityMapper.map(dto, KcBiosampleEntity.class))
                .forEach((entity) -> biosampleRepository.save(entity));
        LOGGER.info("Succesfully wrote " + biosampleDtos + " to kc.pool");
    }

    @Transactional
    public List<KcPatientDto> getAllPatients() {
        ModelMapper mapper = new KcModelMapperBuilder().setKcPatientEntityToDtoConverter()
                .getModelMapper();
        List<KcPatientDto> patientDtos = patientRepository.findAll().stream()
                .map((entity) -> mapper.map(entity, KcPatientDto.class)).collect(Collectors.toList());
        if (patientDtos.isEmpty()) {
            LOGGER.warn("Could not find any patients");
        } else {
            LOGGER.info("Succesfully found " + patientDtos.size() + " patients");
        }
        return patientDtos;
    }

    @Transactional
    public KcPatientDto getPatientById(String id) {
        Optional<KcPatientEntity> patientEntityOpt = patientRepository.findById(id);
        ModelMapper mapper = new KcModelMapperBuilder().setKcPatientEntityToDtoConverter()
                .getModelMapper();
        if (patientEntityOpt.isPresent()) {
            KcPatientDto patientDto = mapper.map(patientEntityOpt.get(), KcPatientDto.class);
            LOGGER.info("Succesfully found " + patientDto.getId());
            return patientDto;
        } else {
            LOGGER.warn("Could not find patient with id: " + id);
            return null;
        }
    }

    @Transactional
    public List<KcMafDto> getMafsById(String id) {
        List<KcMafDto> mafDtos = getPatientById(id).getMafList();
        LOGGER.info("Succesfully found " + mafDtos);
        return mafDtos;
    }

    @Transactional
    public List<KcMorphologyDto> getMorphologiesById(String id) {
        List<KcMorphologyDto> morphologyDtos = getPatientById(id).getMorphologyList();
        LOGGER.info("Succesfully found " + morphologyDtos);
        return morphologyDtos;
    }

    @Transactional
    public List<KcTherapyDto> getTherapiesById(String id) {
        List<KcTherapyDto> therapyDtos = getPatientById(id).getTherapyList();
        LOGGER.info("Succesfully found " + therapyDtos);
        return therapyDtos;
    }

    @Transactional
    public List<KcDiagnosisDto> getDiganosisById(String id) {
        List<KcDiagnosisDto> diagnosisDtos = getPatientById(id).getDiagnosisList();
        LOGGER.info("Succesfully found " + diagnosisDtos);
        return diagnosisDtos;
    }

    @Transactional
    public List<KcLesionDto> getLesionsById(String id) {
        List<KcLesionDto> lesionsDtos = getPatientById(id).getLesionList();
        LOGGER.info("Succesfully found " + lesionsDtos);
        return lesionsDtos;
    }

    @Transactional
    public List<KcBiosampleDto> getBiosamplesById(String id) {
        List<KcBiosampleDto> biosampleDtos = getPatientById(id).getBiosampleList();
        LOGGER.info("Succesfully found " + biosampleDtos);
        return biosampleDtos;
    }

    @Transactional
    public void deletePatient(String id) {
        patientRepository.deleteById(id);
        LOGGER.info("Succesfully deleted patient with id: " + id);
    }
}
