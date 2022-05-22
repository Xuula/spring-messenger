package uxal.messenger;

import java.sql.Timestamp;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    // TODO: Initialize random
    String randomAlphanumeric(int len){
        String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqestuvwxyz0123456789";
        char[] chars = new char[len];
        
        Random random = new Random();
        for(int i=0; i<len; i++){
            int rndIdx = random.nextInt(alphanum.length());
            chars[i] = alphanum.charAt(rndIdx);
        }
        return new String(chars);
    }

    Session(){
        super();
        id = randomAlphanumeric(32);
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Timestamp getExpiresAt(){
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt){
        this.expiresAt = expiresAt;
    }
}
