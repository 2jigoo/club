package org.zerock.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
 
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM ClubMember m WHERE m.fromSocial = :social AND m.email =:email")
    Optional<ClubMember> findByEmail(String email, boolean social);

}
