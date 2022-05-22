package uxal.messenger;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer>{
    @Query(value = "SELECT * FROM messages " +
    "WHERE (sender = ?1 and destination = ?2 " + 
    "or sender = ?2 and destination=?1) " +
    "and sending_time < ?3 " + 
    "order by sending_time desc "+
    "limit ?4", nativeQuery = true)
    List<Message> getDialogBefore(Integer user_1, Integer user_2, Date before, Integer maxNum);

    @Query(value = "SELECT * FROM messages " +
    "WHERE (sender = ?1 and destination = ?2 " + 
    "or sender = ?2 and destination=?1) " +
    "and sending_time > ?3 " + 
    "order by sending_time "+
    "limit ?4", nativeQuery = true)
    List<Message> getDialogAfter(Integer user_1, Integer user_2, Date before, Integer maxNum);
}
