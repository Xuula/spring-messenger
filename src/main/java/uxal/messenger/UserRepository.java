package uxal.messenger;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
    @Query(value = "SELECT * FROM users  WHERE nick = ?1", nativeQuery = true)
    public List<User> findByNick(String nick);

    @Query(value = "SELECT * FROM users  WHERE id = ?1", nativeQuery = true)
    public Optional<PublicUserData> getPublicUserData(Integer id);

    @Query(value = "SELECT * FROM users  WHERE nick like CONCAT('%',?1,'%')", nativeQuery = true)
    public List<PublicUserData> searchByNick(String partial_nick);
}
