package KangWCB.comgram.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SearchDto {

    private String word;
    private String cond;
}
