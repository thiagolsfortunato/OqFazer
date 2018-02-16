package br.com.oqfazer.domain.user;

import br.com.oqfazer.domain.event.EventService;
import br.com.oqfazer.exception.ExistException;
import br.com.oqfazer.transport.EventVO;
import br.com.oqfazer.transport.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * A classe UserService e reponsavel por gerenciar as operacoes de CRUD de usuario.
 * @author Thiago Fortunato
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    EventService eventService;

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
     * @param userVO
     * @return UserVO
     * @throws ExistException
     */
    public UserVO save(final UserVO userVO) throws ExistException {
        User userEntity = loadUserByUsername(userVO.getUsername());
        if (userEntity != null) throw new ExistException();
        else {
            userEntity = new User(userVO.getName(), userVO.getUsername(), encoder.encode(userVO.getPassword()), userVO.getEmail(), userVO.getPhone(),  userVO.getAuthorities());
            userRepository.save(userEntity);
        }
        return UserVO.objectToVo(userEntity);
    }

    /**
     * O metodo e reponsavel por editar um objeto usuario.
     * Caso o usuario informe um novo password será gerado uma nova criptografica
     * para persistir no banco de dados.
     * @param userVO
     * @return UserVO
     * @throws ExistException
     */
    public UserVO edit(final UserVO userVO) throws ExistException {
        User userById = userRepository.findById(userVO.getId());
        User userEntity = userRepository.findByUsername(userVO.getUsername());
        if(userEntity != null) compareUsers(userById, userEntity);
        if(userVO.getPassword().equals("") || userVO.getPassword() == null){
            userVO.setPassword(userById.getPassword());
            return userRepository.save(userVO);
        }else {
            return UserVO.objectToVo(userRepository.save(new User(userVO.getId(), userVO.getName(), userVO.getUsername(), encoder.encode(userVO.getPassword()), userVO.getEmail(), userVO.getAuthorities(), userVO.getPhone())));
        }
    }

    private void compareUsers(final User user1, final User user2) throws ExistException {
        if(!Objects.equals(user1, user2)) {
            throw new ExistException();
        }
    }
    /**
     * O metodo e reponsavel por deletar usuario.
     * @param user
     * @return void
     */
    public void delete(final UserVO user) {
        userRepository.delete(user);
    }

    /**
     * O metodo e reponsavel por retornar um usuario realizando a busca por username
     * @param username
     * @return UserVO
     */
    public UserVO loadUserByUsername(String username) {
        User userEntity = this.userRepository.findByUsername(username);
        UserVO userVO = UserVO.objectToVo(userEntity);
        userVO.setOwner(eventService.findByOwner(userVO));
        userVO.setParticipation(eventService.findByParticipation(userVO));

        return userVO;
    }

    /**
     * O metodo e reponsavel por retornar um usuario realizando a busca por nome
     * @param name
     * @return UserVO
     */
    public List<UserVO> loadUsersByName(String name) {
        List<User> userList = this.userRepository.findByName(name);
        return UserVO.listObjectToListVo(userList);
    }

    /**
     * O metodo e reponsavel por retornar uma lista de usuarios.
     * @return UserVO
     */
    public List<UserVO> getAllUsers() {
        return UserVO.listObjectToListVo(userRepository.findAll());
    }

}
