package br.com.oqfazer.domain.user;

import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A classe UserService e reponsavel por gerenciar as operacoes de CRUD de usuario.
 * @author Thiago Fortunato
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * O metodo e reponsavel por salvar um objeto usuario.
     * Caso o usuario ja exista no banco de dados e retornado uma excecao
     * @param user
     * @return User
     * @throws ExistException
     */
    public User save(final User user) throws ExistException {
        User userEntity = loadUserByUsername(user.getUsername());
        if (userEntity != null) throw new ExistException();
        else {
            userEntity = new User(user.getName(), user.getUsername(), encoder.encode(user.getPassword()), user.getAuthorities(), user.getObservation());
            userRepository.save(userEntity);
        }
        return userEntity;
    }

    /**
     * O metodo e reponsavel por editar um objeto usuario.
     * Caso o usuario informe um novo password será gerado uma nova criptografica
     * para persistir no banco de dados.
     * @param user
     */
    public User edit(final User user) {
        if(user.getPassword().equals("") || user.getPassword() == null){
            User userEntity = userRepository.findById(user.getId());
            user.setPassword(userEntity.getPassword());
            return userRepository.save(user);
        }else {
            return userRepository.save(new User(user.getId(), user.getName(), user.getUsername(), encoder.encode(user.getPassword()), user.getAuthorities(), user.getObservation()));
        }
    }

    /**
     * O metodo e reponsavel por deletar usuario.
     * @param user
     */
    public void delete(final User user) {
        userRepository.delete(user);
    }

    /**
     * O metodo e reponsavel por retornar um usuario realizando a busca por username
     * @param username
     * @return User
     */
    public User loadUserByUsername(String username) {
        User userEntity = this.userRepository.findByUsername(username);
        return userEntity;
    }

    /**
     * O metodo e reponsavel por retornar um usuario realizando a busca por nome
     * @param name
     * @return User
     */
    public List<User> loadUsersByName(String name) {
        List<User> userList = this.userRepository.findByName(name);
        return userList;
    }

    /**
     * O metodo e reponsavel por retornar uma lista de usuarios.
     * @return
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
