package org.j4el.com.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,name = "TITLE")
    @Basic(optional = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SCHEDULED_DATE")
    @Basic(optional = false)
    private LocalDateTime scheduledDate;

    @Column(name = "LOCATION")
    @Basic(optional = false)
    private String location;

    @Column(name = "STATUS")
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status{
        COMPLETED, DUE;
    }
}
