package uxal.messenger;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "avatars")
public class Avatar {

    @Id
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "image")
    @Lob
    private byte[] image;

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public byte[] getImage(){
        return image;
    }

    public void setImage(byte[] image){
        this.image = image;
    }
}
