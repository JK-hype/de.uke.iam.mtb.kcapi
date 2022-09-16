package de.uke.iam.mtb.kcapi.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import de.uke.iam.mtb.dto.enums.Sex;
import de.uke.iam.mtb.dto.kc.KcBiosampleDto;
import de.uke.iam.mtb.dto.kc.KcDiagnosisDto;
import de.uke.iam.mtb.dto.kc.KcLesionDto;
import de.uke.iam.mtb.dto.kc.KcMafDto;
import de.uke.iam.mtb.dto.kc.KcMorphologyDto;
import de.uke.iam.mtb.dto.kc.KcPatientDto;
import de.uke.iam.mtb.dto.kc.KcTherapyDto;
import de.uke.iam.mtb.kcapi.model.KcBiosampleEntity;
import de.uke.iam.mtb.kcapi.model.KcDiagnosisEntity;
import de.uke.iam.mtb.kcapi.model.KcLesionEntity;
import de.uke.iam.mtb.kcapi.model.KcMafEntity;
import de.uke.iam.mtb.kcapi.model.KcMorphologyEntity;
import de.uke.iam.mtb.kcapi.model.KcPatientEntity;
import de.uke.iam.mtb.kcapi.model.KcTherapyEntity;

public class KcModelMapperBuilder {

        private ModelMapper modelMapper;

        public KcModelMapperBuilder() {
                this.modelMapper = new ModelMapper();
        }

        public KcModelMapperBuilder setKcPatientEntityToDtoConverter() {
                TypeMap<KcPatientEntity, KcPatientDto> propertyMapper = modelMapper
                                .createTypeMap(KcPatientEntity.class, KcPatientDto.class);
                // Patient
                propertyMapper.addMappings(
                                mapper -> mapper.using(ctx -> Sex.getSexByGermanName(((String) ctx.getSource())))
                                                .map(KcPatientEntity::getSex, KcPatientDto::setSex));
                // Diagnosis
                propertyMapper
                                .addMappings(mapper -> mapper
                                                .using(ctx -> map(ctx,
                                                                new TypeToken<List<KcDiagnosisDto>>() {
                                                                }.getType()))
                                                .map(KcPatientEntity::getDiagnosisList,
                                                                KcPatientDto::setDiagnosisList));
                // Lesion
                propertyMapper
                                .addMappings(mapper -> mapper
                                                .using(ctx -> map(ctx, new TypeToken<List<KcLesionDto>>() {
                                                }.getType()))
                                                .map(KcPatientEntity::getLesionList,
                                                                KcPatientDto::setLesionList));
                // Maf
                propertyMapper
                                .addMappings(mapper -> mapper
                                                .using(ctx -> map(ctx, new TypeToken<List<KcMafDto>>() {
                                                }.getType())

                                                )
                                                .map(KcPatientEntity::getMafList, KcPatientDto::setMafList));
                // Morphology
                propertyMapper
                                .addMappings(mapper -> mapper
                                                .using(ctx -> map(ctx,
                                                                new TypeToken<List<KcMorphologyDto>>() {
                                                                }.getType()))
                                                .map(KcPatientEntity::getMorphologyList,
                                                                KcPatientDto::setMorphologyList));
                // Therapy
                propertyMapper
                                .addMappings(mapper -> mapper
                                                .using(ctx -> map(ctx, new TypeToken<List<KcTherapyDto>>() {
                                                }.getType()))
                                                .map(KcPatientEntity::getTherapyList,
                                                                KcPatientDto::setTherapyList));
                // Biosample
                propertyMapper
                                .addMappings(mapper -> mapper
                                                .using(ctx -> map(ctx, new TypeToken<List<KcBiosampleDto>>() {
                                                }.getType()))
                                                .map(KcPatientEntity::getBiosampleList,
                                                                KcPatientDto::setBiosampleList));

                return this;

        }

        public KcModelMapperBuilder setKcPatientDtoToEntityConverter() {
                TypeMap<KcPatientDto, KcPatientEntity> propertyMapper = modelMapper
                                .createTypeMap(KcPatientDto.class, KcPatientEntity.class);
                // Patient
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> ((Sex) ctx.getSource()).getGermanName())
                                .map(KcPatientDto::getSex, KcPatientEntity::setSex));
                // Diagnosis
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> {
                        List<KcDiagnosisEntity> diagnosisEntities = new ArrayList<>();
                        if (ctx.getSource() != null) {
                                String id = castList(ctx.getSource(), KcDiagnosisDto.class).get(0).getPatientId();
                                diagnosisEntities = map(
                                                ctx,
                                                new TypeToken<List<KcDiagnosisEntity>>() {
                                                }.getType());
                                diagnosisEntities.forEach(e -> e.setPatient(createKcPatientWithId(id)));
                        }
                        return diagnosisEntities;
                }).map(KcPatientDto::getDiagnosisList, KcPatientEntity::setDiagnosisList));
                // Lesion
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> {
                        List<KcLesionEntity> lesionEntities = new ArrayList<>();
                        if (ctx.getSource() != null) {
                                String id = castList(ctx.getSource(), KcLesionDto.class).get(0).getPatientId();
                                lesionEntities = map(
                                                ctx,
                                                new TypeToken<List<KcLesionEntity>>() {
                                                }.getType());
                                lesionEntities.forEach(e -> e.setPatient(createKcPatientWithId(id)));
                        }
                        return lesionEntities;
                }).map(KcPatientDto::getLesionList, KcPatientEntity::setLesionList));
                // Morphology
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> {
                        List<KcMorphologyEntity> morphologyEntities = new ArrayList<>();
                        if (ctx.getSource() != null) {
                                String id = castList(ctx.getSource(), KcMorphologyDto.class).get(0).getPatientId();
                                morphologyEntities = map(
                                                ctx,
                                                new TypeToken<List<KcMorphologyEntity>>() {
                                                }.getType());
                                morphologyEntities.forEach(e -> e.setPatient(createKcPatientWithId(id)));
                        }
                        return morphologyEntities;
                }).map(KcPatientDto::getMorphologyList, KcPatientEntity::setMorphologyList));
                // Therapy
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> {
                        List<KcTherapyEntity> therapyEntities = new ArrayList<>();
                        if (ctx.getSource() != null) {
                                String id = castList(ctx.getSource(), KcTherapyDto.class).get(0).getPatientId();
                                therapyEntities = map(
                                                ctx,
                                                new TypeToken<List<KcTherapyEntity>>() {
                                                }.getType());
                                therapyEntities.forEach(e -> e.setPatient(createKcPatientWithId(id)));
                        }
                        return therapyEntities;
                }).map(KcPatientDto::getTherapyList, KcPatientEntity::setTherapyList));
                // maf
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> {
                        List<KcMafEntity> mafEntities = new ArrayList<>();
                        if (ctx.getSource() != null) {
                                String id = castList(ctx.getSource(), KcMafDto.class).get(0).getPatientId();
                                mafEntities = map(
                                                ctx,
                                                new TypeToken<List<KcMafEntity>>() {
                                                }.getType());
                                mafEntities.forEach(e -> e.setPatient(createKcPatientWithId(id)));
                        }
                        return mafEntities;
                }).map(KcPatientDto::getMafList, KcPatientEntity::setMafList));
                // biosample
                propertyMapper.addMappings(mapper -> mapper.using(ctx -> {
                        List<KcBiosampleEntity> mafEntities = new ArrayList<>();
                        if (ctx.getSource() != null) {
                                String id = castList(ctx.getSource(), KcBiosampleDto.class).get(0).getPatientId();
                                mafEntities = map(
                                                ctx,
                                                new TypeToken<List<KcBiosampleDto>>() {
                                                }.getType());
                                mafEntities.forEach(e -> e.setPatient(createKcPatientWithId(id)));
                        }
                        return mafEntities;
                }).map(KcPatientDto::getBiosampleList, KcPatientEntity::setBiosampleList));

                return this;
        }

        private KcPatientEntity createKcPatientWithId(String id) {
                if (id == null) {
                        return null;
                }
                KcPatientEntity patient = new KcPatientEntity();
                patient.setId(id);
                return patient;
        }

        private <T> List<T> castList(Object obj, Class<T> clazz) {
                List<T> result = new ArrayList<T>();
                if (obj instanceof List<?>) {
                        for (Object o : (List<?>) obj) {
                                result.add(clazz.cast(o));
                        }
                        return result;
                }
                return null;
        }

        private <T> T map(MappingContext<Object, Object> ctx, Type destionationType) {
                return ctx.getSource() == null ? null
                                : modelMapper.map(
                                                (ctx
                                                                .getSource()),
                                                destionationType);
        }

        public KcModelMapperBuilder setStrictMatching() {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                return this;
        }

        public ModelMapper getModelMapper() {
                return modelMapper;
        }

}
