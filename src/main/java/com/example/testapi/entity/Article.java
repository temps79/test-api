package com.example.testapi.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Map;

@Entity(name = "article")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long id;
    @Column
    @JsonProperty
    @NotBlank
    @Size(max = 100)
    private String title;
    @Column
    @JsonProperty
    @NotBlank
    private String author;

    @ElementCollection
    @Size(min = 1)
    private Map<String, String> content;

    @Column
    @JsonProperty
    private String publisherUsername;


    @Column
//    @JsonFormat( shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-ddTHH:mm:ssZ" )
    private Instant publishDate;
}
