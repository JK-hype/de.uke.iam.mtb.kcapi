package de.uke.iam.mtb.kcapi.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.uke.iam.lib.json.GsonHelper;
import de.uke.iam.mtb.dto.kc.KcBiosampleDto;
import de.uke.iam.mtb.dto.kc.KcDiagnosisDto;
import de.uke.iam.mtb.dto.kc.KcLesionDto;
import de.uke.iam.mtb.dto.kc.KcMafDto;
import de.uke.iam.mtb.dto.kc.KcMorphologyDto;
import de.uke.iam.mtb.dto.kc.KcPatientDto;
import de.uke.iam.mtb.dto.kc.KcTherapyDto;
import de.uke.iam.mtb.kcapi.service.KcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class KcController {

        private final KcService service;

        private final String kcUrl = "/kc";
        private final String patientUrl = kcUrl + "/patient";
        private final String mafsUrl = kcUrl + "/mafs";
        private final String morphologiesUrl = kcUrl + "/morphologies";
        private final String therapiesUrl = kcUrl + "/therapies";
        private final String diagnosisUrl = kcUrl + "/diagnosis";
        private final String lesionsUrl = kcUrl + "/lesions";
        private final String biosamplesUrl = kcUrl + "/biosamples";

        private final String mappingErrorMessage = "Mapping error. Could not convert this dto. Please provide a dto according to the documentation";
        private final String entityNotFoundErrorMessage = "Could not find patient entity with this id";

        private final String savesList = "Save list";
        private final String getById = "Get by id";
        private final String savesIfPatientExists = "Save list, if the patient with the patientId already exists";
        private final String emptyButNotNull = "Can be empty, but not null. The patientId of all dtos must be set";
        private final String idNotNull = "Patient id. Cannot be null";

        public KcController(KcService service) {
                this.service = service;
        }

        @Operation(summary = "Saves", description = "")
        @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Saved <patientDto>"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage) })
        @PostMapping(patientUrl)
        public ResponseEntity<String> savePatient(
                        @Parameter(description = "Cannot be null", required = true, schema = @Schema(implementation = KcPatientDto.class)) @RequestBody KcPatientDto patientDto) {
                service.savePatient(patientDto);
                return new ResponseEntity<>("Saved " + patientDto, HttpStatus.CREATED);
        }

        @Operation(summary = savesList, description = savesIfPatientExists)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Saved <size of the maf list> maf entries"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @PostMapping(mafsUrl)
        public ResponseEntity<String> saveMafs(
                        @Parameter(description = emptyButNotNull, required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = KcMafDto.class)))) @RequestBody List<KcMafDto> mafDtos) {
                service.saveMafs(mafDtos);
                return new ResponseEntity<>("Saved " + mafDtos.size() + " maf entries", HttpStatus.CREATED);
        }

        @Operation(summary = savesList, description = savesIfPatientExists)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Saved <size of the morphology list> morphology entries"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @PostMapping(morphologiesUrl)
        public ResponseEntity<String> saveMorphologies(
                        @Parameter(description = emptyButNotNull, required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = KcMorphologyDto.class)))) @RequestBody List<KcMorphologyDto> morphlogyDtos) {
                service.saveMorphologies(morphlogyDtos);
                return new ResponseEntity<>("Saved " + morphlogyDtos.size() + " morphology entries",
                                HttpStatus.CREATED);
        }

        @Operation(summary = savesList, description = savesIfPatientExists)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Saved <size of the therapy list> therapy entries"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @PostMapping(therapiesUrl)
        public ResponseEntity<String> saveTherapies(
                        @Parameter(description = emptyButNotNull, required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = KcTherapyDto.class)))) @RequestBody List<KcTherapyDto> therapyDtos) {
                service.saveTherapies(therapyDtos);
                return new ResponseEntity<>("Saved " + therapyDtos.size() + " therapy entries", HttpStatus.CREATED);
        }

        @Operation(summary = savesList, description = savesIfPatientExists)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Saved <size of the diagnosis list> diagnosis entries"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @PostMapping(diagnosisUrl)
        public ResponseEntity<String> saveDiagnosis(
                        @Parameter(description = emptyButNotNull, required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = KcDiagnosisDto.class)))) @RequestBody List<KcDiagnosisDto> diagnosisDtos) {
                service.saveDiagnosis(diagnosisDtos);
                return new ResponseEntity<>("Saved " + diagnosisDtos.size() + " diagnosis entries", HttpStatus.CREATED);
        }

        @Operation(summary = savesList, description = savesIfPatientExists)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Saved <size of the lesion list> lesion entries"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @PostMapping(lesionsUrl)
        public ResponseEntity<String> saveLesions(
                        @Parameter(description = emptyButNotNull, required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = KcLesionDto.class)))) @RequestBody List<KcLesionDto> lesionDtos) {
                service.saveLesions(lesionDtos);
                return new ResponseEntity<>("Saved " + lesionDtos.size() + " lesions entries", HttpStatus.CREATED);
        }

        @Operation(summary = savesList, description = savesIfPatientExists)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Saved <size of the biosample list> biosample entries"),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @PostMapping(biosamplesUrl)
        public ResponseEntity<String> saveBiosamples(
                        @Parameter(description = emptyButNotNull, required = true, content = @Content(array = @ArraySchema(schema = @Schema(implementation = KcBiosampleDto.class)))) @RequestBody List<KcBiosampleDto> biosampleDtos) {
                service.saveBiosamples(biosampleDtos);
                return new ResponseEntity<>("Saved " + biosampleDtos.size() + " maf entries", HttpStatus.CREATED);
        }

        @Operation(summary = getById, description = "")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found patient with id", content = {
                                        @Content(schema = @Schema(implementation = KcPatientDto.class)) }),
                        @ApiResponse(responseCode = "400", description = "Found no patient with id") })
        @GetMapping(patientUrl + "/{id}")
        public ResponseEntity<String> getPatientById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                KcPatientDto patient = service.getPatientById(id);
                String body;
                HttpStatus status;
                if (patient == null) {
                        status = HttpStatus.BAD_REQUEST;
                        body = "{}"; // empty object
                } else {
                        status = HttpStatus.OK;
                        body = GsonHelper.get().getNewGson().toJson(patient);
                }
                return new ResponseEntity<>(body, status);
        }

        @Operation(summary = "Get all", description = "")
        @ApiResponse(responseCode = "200", description = "Found all patients", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcPatientDto.class))) })
        @GetMapping(kcUrl + "/patients")
        @ResponseBody
        public List<KcPatientDto> getAllPatients() {
                return service.getAllPatients();
        }

        @Operation(summary = getById, description = "Gets the patient with this id first and extracts then the maf list")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found mafs with id", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcMafDto.class))) }),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @GetMapping(mafsUrl + "/{id}")
        @ResponseBody
        public List<KcMafDto> getMafsById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                return service.getMafsById(id);
        }

        @Operation(summary = getById, description = "Gets the patient with this id first and extracts then the morphology list")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found morphologies with id", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcMorphologyDto.class))) }),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @GetMapping(morphologiesUrl + "/{id}")
        @ResponseBody
        public List<KcMorphologyDto> getMorpholiesById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                return service.getMorphologiesById(id);
        }

        @Operation(summary = getById, description = "Gets the patient with this id first and extracts then the therapy list")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found therapies with id", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcTherapyDto.class))) }),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @GetMapping(therapiesUrl + "/{id}")
        @ResponseBody
        public List<KcTherapyDto> getTherapiesById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                return service.getTherapiesById(id);
        }

        @Operation(summary = getById, description = "Gets the patient with this id first and extracts then the diagnosis list")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found diagnosis with id", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcDiagnosisDto.class))) }),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @GetMapping(diagnosisUrl + "/{id}")
        @ResponseBody
        public List<KcDiagnosisDto> getDiagnosisById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                return service.getDiganosisById(id);
        }

        @Operation(summary = getById, description = "Gets the patient with this id first and extracts then the lesion list")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found lesions with id", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcLesionDto.class))) }),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @GetMapping(lesionsUrl + "/{id}")
        @ResponseBody
        public List<KcLesionDto> getLesionsById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                return service.getLesionsById(id);
        }

        @Operation(summary = getById, description = "Gets the patient with this id first and extracts then the biosample list")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Found biosamples with id", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = KcBiosampleDto.class))) }),
                        @ApiResponse(responseCode = "400", description = mappingErrorMessage),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @GetMapping(biosamplesUrl + "/{id}")
        @ResponseBody
        public List<KcBiosampleDto> getBiosamplesById(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                return service.getBiosamplesById(id);
        }

        @Operation(summary = "Delete by id", description = "")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Deleted patient with id <id>"),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @DeleteMapping(patientUrl + "/{id}")
        public ResponseEntity<String> deletePatient(
                        @Parameter(description = idNotNull, required = true) @PathVariable String id) {
                service.deletePatient(id);
                return new ResponseEntity<>("Deleted patient with id: " + id, HttpStatus.OK);
        }

        @Operation(summary = "Delete", description = "Delete patient with the id delievered in the KcPatientDto")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Deleted patient with id <id>"),
                        @ApiResponse(responseCode = "400", description = entityNotFoundErrorMessage) })
        @DeleteMapping(patientUrl)
        public ResponseEntity<String> deletePatient(
                        @Parameter(description = "Cannot be null and must at least include the patientId", required = true, schema = @Schema(implementation = KcPatientDto.class)) @RequestBody KcPatientDto patientDto) {
                return deletePatient(patientDto.getId());
        }

        @ExceptionHandler({ EntityNotFoundException.class })
        public ResponseEntity<String> handleEntityNotFoundException() {
                return new ResponseEntity<>(entityNotFoundErrorMessage, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({ MappingException.class })
        public ResponseEntity<String> handleMappingException() {
                return new ResponseEntity<>(
                                mappingErrorMessage,
                                HttpStatus.BAD_REQUEST);
        }
}
