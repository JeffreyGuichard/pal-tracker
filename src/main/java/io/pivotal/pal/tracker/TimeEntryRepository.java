package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntryToCreate);


    public TimeEntry find(long timeEntryId);


    public List<TimeEntry> list();


    public TimeEntry update(long timeEntryId, TimeEntry expected);


    public void delete(long timeEntryId);

}
