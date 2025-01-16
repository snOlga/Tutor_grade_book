package course_project.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.entity.RoleEntity;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
