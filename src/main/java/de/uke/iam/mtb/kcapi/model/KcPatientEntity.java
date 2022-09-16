package de.uke.iam.mtb.kcapi.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "pool", name = "patients")
@Getter
@Setter
@ToString
public class KcPatientEntity {

    @Id
    @Column(name = "pid")
    private String id;
    @Column(name = "gender")
    private String sex;
    @Column(name = "birthdate")
    private Date birthday;
    private String site;
    private String applicant;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KcBiosampleEntity> biosampleList;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KcMafEntity> mafList;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KcDiagnosisEntity> diagnosisList;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KcLesionEntity> lesionList;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KcMorphologyEntity> morphologyList;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KcTherapyEntity> therapyList;
}
