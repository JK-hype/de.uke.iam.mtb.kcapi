package de.uke.iam.mtb.kcapi.model;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "pool", name = "morphology")
@Getter
@Setter
public class KcMorphologyEntity {

    @Id
    private UUID id;
    private String morphology;
    private Date date;
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
