package org.devhamzat.authorg.repository;

import org.devhamzat.authorg.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    Optional<Organization> findByName(String name);
}
