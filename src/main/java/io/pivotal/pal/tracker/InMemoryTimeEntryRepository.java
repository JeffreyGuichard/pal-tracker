package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry> hashMap;

    private int index;

    @Autowired
    public InMemoryTimeEntryRepository()
    {
        hashMap = new HashMap<>();
        this.index = 1;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        if(timeEntry.getId() == 0){
            timeEntry.setId(index);
        }
        hashMap.put(timeEntry.getId(), timeEntry);
        index++;
        return timeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return hashMap.get( timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList( hashMap.values() );

    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {

        TimeEntry te = hashMap.get( timeEntryId );
        if(te != null){
            te.setProjectId(timeEntry.getProjectId());
            te.setDate(timeEntry.getDate());
            te.setUserId(timeEntry.getUserId());
            te.setHours(timeEntry.getHours());
        }
        return te;
    }

    @Override
    public void delete(long timeEntryId) {
        hashMap.remove(timeEntryId);
    }
}
