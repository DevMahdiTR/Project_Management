package com.example.pfe_project.repository.token;

import com.example.pfe_project.models.Token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "select t from Token t inner join UserEntity u on t.userEntity.id = u.id where u.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> fetchAllValidTokenByUser(long id);

    Optional<Token> findByToken(String token);
}
