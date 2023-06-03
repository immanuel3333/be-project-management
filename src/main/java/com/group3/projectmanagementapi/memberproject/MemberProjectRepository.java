package com.group3.projectmanagementapi.memberproject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group3.projectmanagementapi.memberproject.models.MemberProject;

public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {

    List<MemberProject> findByProjectId(Long idProject);

    @Query(value = "select c.id as id_cust, c.name, mp.id as id_member from customeruser c left join member_project mp on c.id  = mp.customeruser_id", nativeQuery = true)
    List<MemberProject> getCustomerUserDetails();
}
