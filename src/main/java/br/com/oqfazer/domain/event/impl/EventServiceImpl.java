package br.com.oqfazer.domain.event.impl;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.category.CategoryService;
import br.com.oqfazer.domain.event.Event;
import br.com.oqfazer.domain.event.EventRepository;
import br.com.oqfazer.domain.event.EventService;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.exception.ExistException;
import br.com.oqfazer.transport.CityVO;
import br.com.oqfazer.transport.EventVO;
import br.com.oqfazer.transport.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public EventVO save(EventVO eventVO) throws ExistException {
        Event eventEntity = this.findByName(eventVO.getName());
        if (eventEntity != null) throw new ExistException();
        else {
            for (Category cat : eventVO.getCategories()) {
                if (cat.getId() == null) categoryService.save(cat);
            }
            eventEntity = repository.save(eventVO);
            return EventVO.objectToVo(eventEntity);
        }
    }

    @Override
    public EventVO edit(EventVO eventVO) {
        return repository.save(eventVO);
    }

    @Override
    public void delete(EventVO eventVO) {
        repository.delete(eventVO);
    }

    @Override
    public List<EventVO> getAllEvents() {
        return EventVO.listObjectToListVo(repository.findAll());
    }

    @Override
    public EventVO findById(Long id) {
        return EventVO.objectToVo(repository.findById(id));
    }

    @Override
    public EventVO findByName(String name) {
        return EventVO.objectToVo(repository.findByName(name));
    }

    @Override
    public List<EventVO> findByCity(CityVO cityVO) {
        return EventVO.listObjectToListVo(repository.findByCity(cityVO));
    }

    @Override
    public List<EventVO> findByOwner(UserVO ownerVO) {
        return EventVO.listObjectToListVo(repository.findByOwner(ownerVO));
    }

    @Override
    public List<EventVO> findByParticipation(UserVO participationVO) {
        return EventVO.listObjectToListVo(repository.findByParticipants(participationVO));
    }
}
