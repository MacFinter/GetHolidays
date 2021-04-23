package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "holidays")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_type_id")
    private int eventTypeId;

    @Column(name = "end_at")
    private Date endAt;

    @Column(name = "start_at")
    private Date startAt;

    private String name;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "search_name")
    private String searchName;

    @Column(name = "deleted_at")
    private Date deletedAt;

    private Integer position;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
