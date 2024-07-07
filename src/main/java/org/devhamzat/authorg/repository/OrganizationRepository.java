package org.devhamzat.authorg.repository;

import org.devhamzat.authorg.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
}
