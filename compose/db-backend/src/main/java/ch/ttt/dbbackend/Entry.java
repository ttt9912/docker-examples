package ch.ttt.dbbackend;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Entry {
    @Id
    private String key;

    public Entry() {
    }

    public Entry(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key='" + key + '\'' +
                '}';
    }
}
