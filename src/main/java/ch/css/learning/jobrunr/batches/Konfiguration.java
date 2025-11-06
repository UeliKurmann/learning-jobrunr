package ch.css.learning.jobrunr.batches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Konfiguration {

    int jahr;
    String kanton;
    String template;
}
