package ch.ttt.dbbackend;

import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, String> {
}
