package de.uke.iam.mtb.kcapi.model;

import java.lang.reflect.Field;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import lombok.Getter;
import lombok.Setter;

@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@Entity
@Table(schema = "pool", name = "maf")
@Getter
@Setter
public class KcMafEntity {

    @Id
    private UUID id;
    @Column(name = "biosample_id")
    private String biosampleId;
    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] genes;
    private String transcript;
    @Column(name = "variant_classification")
    private String variantClassification;
    private String chromosome;
    @Column(name = "variant_type")
    private String variantType;
    private Long start;
    // necessary, as "end" is a key word in sql
    @Column(name = "\"end\"")
    private Long end;
    @Column(name = "ref_genome")
    private String refGenome;
    @Column(name = "ref_seq")
    private String refSeq;
    @Column(name = "ref_allele")
    private String refAllele;
    @Column(name = "tumor_seq_allele_1")
    private String tumorSeqAllele1;
    @Column(name = "tumor_seq_allele_2")
    private String tumorSeqAllele2;
    @Column(name = "variant_on_gene")
    private String variantOnGene;
    @Column(name = "variant_on_protein")
    private String variantOnProtein;
    @Column(name = "dbsnp_rs")
    private String dbsnpRs;
    @Column(name = "n_depth")
    private Integer nDepth;
    @Column(name = "t_depth")
    private Integer tDepth;
    @Column(name = "protein_position")
    private String proteinPosition;
    private String codons;
    private String strand;
    private String consequence;
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private KcPatientEntity patient;

    @Override
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        boolean isFirst = true;
        String toString = this.getClass().getSimpleName() + "(";

        for (Field field : fields) {
            toString += addToString(field, isFirst);
            isFirst = false;
        }

        toString += ")";
        return toString;
    }

    private String addToString(Field field, boolean isFirst) {
        String toString = "";
        try {
            if (field.getType().equals(KcPatientEntity.class)) {
                String id = field.get(this) == null ? null : ((KcPatientEntity) field.get(this)).getId();
                return ", patientId:" + id;
            }
            if (isFirst) {
                toString += field.getName() + ": " + field.get(this);
            } else {
                toString += ", " + field.getName() + ": " + field.get(this);
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return toString;
    }
}