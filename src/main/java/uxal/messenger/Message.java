package uxal.messenger;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "messages")
public class Message {
    @Id    
    @GeneratedValue
    @Column(name = "id")
    Integer id;

    @Column(name = "sender")
    Integer sender;

    @Column(name = "destination")
    Integer destination;

    @Column(name = "text")
    String text;

    @Column(name = "sending_time")
    @CreationTimestamp
    private Date sendingTime;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getSender(){
        return sender;
    }

    public void setSender(Integer sender){
        this.sender = sender;
    }

    public Integer getDestination(){
        return destination;
    }

    public void setDestination(Integer destination){
        this.destination = destination;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public Date getSendingTime(){
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime){
        this.sendingTime = sendingTime;
    }
}
