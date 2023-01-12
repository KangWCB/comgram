package KangWCB.comgram.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SearchResponseDto<T> {
    private String cond;
    private int count;
    private List<T> list;
}
