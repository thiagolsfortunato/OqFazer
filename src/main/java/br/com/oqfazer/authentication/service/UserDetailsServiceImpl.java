package br.com.oqfazer.authentication.service;

import br.com.oqfazer.authentication.factory.UserFactory;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.domain.user.UserRepository;
import br.com.oqfazer.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * A classe UserDetailsServiceImpl e responsavel por implementar o servico de autenticacao do usuario
 * @author Thiago Fortunato
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * O metodo e responsavel por verificar se o usuario informado no login
     * existe no banco de dados. Caso encontre o usuario o metodo factory
     * e chamado para criacao de um novo objeto usuario autenticado pelo spring
     * @param username
     * @return userDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User userEntity = this.userRepository.findByUsername(username);

        if (userEntity == null) {
            try {
                throw new NotFoundException(String.format("No userEntity found with username '%s'.", username));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            return UserFactory.create(userEntity);
        }
        return null;
    }

}
