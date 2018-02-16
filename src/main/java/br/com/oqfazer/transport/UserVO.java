package br.com.oqfazer.transport;

import br.com.oqfazer.domain.event.EventService;
import br.com.oqfazer.domain.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class UserVO extends User {

    private List<EventVO> owner;
    private List<EventVO> participation;


    public static User voToObject(UserVO userVO) {
        User user = new User();

        user.setId(userVO.getId());
        user.setName(userVO.getName());
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword());
        user.setEmail(userVO.getPassword());
        user.setPhone(userVO.getPhone());
        user.setAuthorities(userVO.getAuthorities());

        return user;
    }

    public static UserVO objectToVo(User user) {
        UserVO userVO = new UserVO();

        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setUsername(user.getUsername());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setAuthorities(user.getAuthorities());

        return userVO;
    }

    public static List<User> listVoToListObject(List<UserVO> userVOList) {
        Stream<UserVO> userList = userVOList.stream();
        return userList.map(UserVO::voToObject).collect(Collectors.toList());
    }

    public static List<UserVO> listObjectToListVo(List<User> userList) {
        Stream<User> userVOList = userList.stream();
        return userVOList.map(UserVO::objectToVo).collect(Collectors.toList());
    }
}
