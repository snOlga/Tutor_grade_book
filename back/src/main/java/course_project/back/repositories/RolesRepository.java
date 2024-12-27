package course_project.back.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import course_project.back.entity.RoleEntity;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {
    public RoleEntity findByName(String name);
}
