package de.uke.iam.mtb.kcapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.uke.iam.mtb.kcapi.model.KcBiosampleEntity;

@Repository
public interface KcBiosampleRepository extends JpaRepository<KcBiosampleEntity, String> {
}
