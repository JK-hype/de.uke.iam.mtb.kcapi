package de.uke.iam.mtb.kcapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.uke.iam.mtb.kcapi.model.KcMorphologyEntity;

@Repository
public interface KcMorphologyRepository extends JpaRepository<KcMorphologyEntity, Integer> {
}
