package br.com.bonaldo.simianchecker.domains;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "samples")
public class DnaSample {

    @Id
    private String id;

    private String[][] cells;
    private boolean isSimian;

    private LocalDateTime createdDate;

    public DnaSample(final String[][] cells) {
        this.cells = cells;
    }
}