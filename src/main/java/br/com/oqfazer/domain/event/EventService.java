package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.exception.ExistException;
import br.com.oqfazer.transport.CityVO;
import br.com.oqfazer.transport.EventVO;
import br.com.oqfazer.transport.UserVO;

import java.util.List;

public interface EventService {

    EventVO save(EventVO eventVO) throws ExistException;

    EventVO edit(EventVO eventVO);

    void delete(EventVO eventVO);

    List<EventVO> getAllEvents();

    EventVO findById(Long id);

    EventVO findByName(String name);

    List<EventVO> findByCity(CityVO cityVO);

    List<EventVO> findByOwner(UserVO ownerVO);

    List<EventVO> findByParticipation(UserVO participationVO);
}
