package org.j4el.com.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "TITLE")
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

    @CreatedBy
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "UPDATED_ON")
    @LastModifiedDate
    private LocalDateTime updateOn;

    @Column(name = "STATUS")
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        COMPLETED, DUE;
    }
}
