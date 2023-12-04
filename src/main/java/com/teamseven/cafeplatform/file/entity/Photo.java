package com.teamseven.cafeplatform.file.entity;

import com.teamseven.cafeplatform.file.common.Classify;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String directory;
    private String orgName;
    private String saveName;
    private Classify classify;
}