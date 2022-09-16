package de.uke.iam.mtb.kcapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.uke.iam.mtb.kcapi.model.KcTherapyEntity;

@Repository
public interface KcTherapyRepository extends JpaRepository<KcTherapyEntity, Integer> {
}
