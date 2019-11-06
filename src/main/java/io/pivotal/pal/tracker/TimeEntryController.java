package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TimeEntryController {

    private  TimeEntryRepository repository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               MeterRegistry meterRegistry) {
        this.repository = timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping( "/time-entries" )
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = this.repository.create(timeEntryToCreate);

        actionCounter.increment();
        timeEntrySummary.record(repository.list().size());

        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }

    @GetMapping( "/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = this.repository.find(id);
        if(timeEntry == null){
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }
        actionCounter.increment();
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
    }

    @GetMapping( "/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntries = this.repository.list();
        actionCounter.increment();
        return new ResponseEntity<List<TimeEntry>>(timeEntries, HttpStatus.OK);
    }

    @PutMapping( "/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = this.repository.update(id, timeEntry);
        if(updatedTimeEntry == null){
            return new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }
        actionCounter.increment();
        return new ResponseEntity<TimeEntry>(updatedTimeEntry, HttpStatus.OK);
    }

    @DeleteMapping( "/time-entries/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public ResponseEntity delete(@PathVariable long id) {
        this.repository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(repository.list().size());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
