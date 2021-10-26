package com.inside.mc1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Message entity class for DAO level.
 */
@Entity
@Table(name = "Message")
@Data
@NoArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "session_id", nullable = false)
    private Integer sessionId;
    @Temporal(TemporalType.DATE)
    @Column(name = "mc1_timestamp", nullable = false)
    private Date mc1Timestamp;
    @Column(name = "mc2_timestamp", nullable = false)
    private Date mc2Timestamp;
    @Column(name = "mc3_timestamp", nullable = false)
    private Date mc3Timestamp;
}
