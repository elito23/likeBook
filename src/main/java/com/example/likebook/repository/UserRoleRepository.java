package com.example.likebook.repository;

import com.example.likebook.model.entity.UserRole;
import com.example.likebook.model.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByUserRole(UserRoleEnum userRole);

}
