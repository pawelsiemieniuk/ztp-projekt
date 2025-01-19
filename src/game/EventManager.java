package game;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
	//private Map<IEventListener, EventType> listeners = new HashMap<IEventListener, EventType>();
	private List<Pair<EventType, IEventListener>> eventEntries = new ArrayList<Pair<EventType, IEventListener>>();
	
	public void subscribe(EventType eventType, IEventListener listener) {
		eventEntries.add(new Pair(eventType, listener));
	}
	public void unsubscribe(EventType eventType, IEventListener listener) {
		for(Pair<EventType, IEventListener> eventEntry : eventEntries) {
			if(eventEntry.getKey().equals(eventType) && eventEntry.getValue().equals(listener)) {
				eventEntries.remove(eventEntry);
				return;
			}
		}
	}
	public void notify(EventType eventType, String data) {
		for(Pair<EventType, IEventListener> eventEntry : eventEntries) {
			if(eventEntry.getKey().equals(eventType)) {
				eventEntry.getValue().update(data);
			}
		}
	}
}

class Pair<T1, T2> {
	private T1 key;
	private T2 value;
	
	Pair(T1 key, T2 value) {
		this.key   = key;
		this.value = value;
	}
	public T1 getKey() {
		return key;
	}
	public T2 getValue() {
		return value;
	}
}
