package br.com.oqfazer.transport;

import br.com.oqfazer.domain.event.Event;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class EventVO extends Event {

    public static Event voToObject(EventVO eventVO) {
        Event event = new Event();
        return event;
    }

    public static EventVO objectToVo(Event event) {
        EventVO eventVO = new EventVO();
        return eventVO;
    }

    public static List<Event> listVoToListObject(List<EventVO> eventVOList) {
        Stream<EventVO> eventList = eventVOList.stream();
        return eventList.map(EventVO::voToObject).collect(Collectors.toList());
    }

    public static List<EventVO> listObjectToListVo(List<Event> userList) {
        Stream<Event> userVOList = userList.stream();
        return userVOList.map(EventVO::objectToVo).collect(Collectors.toList());
    }
}
