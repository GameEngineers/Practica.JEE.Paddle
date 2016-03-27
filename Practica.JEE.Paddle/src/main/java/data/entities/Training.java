package data.entities;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Training {

    public static final int NUM_TRAINING_PLAYERS = 4;

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn
    private User trainer;

    @ManyToOne
    @JoinColumn
    private Court court;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date time;

    @ManyToMany(fetch = FetchType.EAGER)
    @Size(max=NUM_TRAINING_PLAYERS)
    private List<User> players;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Calendar initialDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Calendar finalDate;

    public Training() {
    }

    public Training(String trainingName, User trainer, Court court, DayOfWeek dayOfWeek, Date time, List<User> players,
            Calendar initialDate, Calendar finalDate) {
        assert trainer != null && court != null && dayOfWeek != null && time != null;
        assert initialDate != null && finalDate != null && initialDate.getTimeInMillis() <= finalDate.getTimeInMillis();
        this.trainingName = trainingName;
        this.trainer = trainer;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.players = players;
        this.court = court;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public User getTrainer() {
        return trainer;
    }

    public List<User> getPlayers() {
        return players;
    }

    public Calendar getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Calendar initialDate) {
        this.initialDate = initialDate;
    }

    public Calendar getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Calendar finalDate) {
        this.finalDate = finalDate;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Training) obj).id;
    }

    @Override
    public String toString() {

        return "Training [id=" + id + ", trainer=" + trainer + ", court=" + court + ", time="
                + new SimpleDateFormat("hh:mm").format(time.getTime()) + "dayOfWeek=" + dayOfWeek.toString() + ", players=" + players
                + "initialDate=" + new SimpleDateFormat("dd/MM/yyyy").format(initialDate.getTime()) + ",finalDate="
                + new SimpleDateFormat("dd/MM/yyyy").format(finalDate.getTime()) + "]";
    }

}
