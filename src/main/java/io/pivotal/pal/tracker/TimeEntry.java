package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Objects;


public class TimeEntry
{
    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public TimeEntry()
    {
    }

    public TimeEntry(
             long projectId,
             long userId,
             LocalDate date,
             int hours )
    {
        this. date = date;
         this.hours = hours;
         this.projectId = projectId;
         this.userId = userId;

    }
    public TimeEntry(
            long timeEntryId,
            long projectId,
            long userId,
            LocalDate date,
            int hours )
    {
        this.id = timeEntryId;
        this. date = date;
        this.hours = hours;
        this.projectId = projectId;
        this.userId = userId;

    }
    public void setId(long timeEntryId){
        this.id = timeEntryId;
    }
    public long getId() {
        return this.id;
    }

    public void setProjectId( long projectId) {
      this.projectId = projectId;
    }
    public long getProjectId() {
        return this.projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeEntry)) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return id == timeEntry.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
