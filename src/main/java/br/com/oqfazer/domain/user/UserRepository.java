package br.com.oqfazer.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A classe UserRepository e reponsavel por pelas operacoes de CRUD de user.
 *
 * @author Thiago Fortunato
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findById(Long id);
}
