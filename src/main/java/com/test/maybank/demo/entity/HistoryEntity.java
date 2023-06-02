package com.test.maybank.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "history_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhistory_table")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "downloaded_date_time")
    private LocalDateTime downloadedDateTime;

    @Column(name = "last_downloaded_file",columnDefinition = "LONGTEXT")
    private String lastDownloadedFile;

}
