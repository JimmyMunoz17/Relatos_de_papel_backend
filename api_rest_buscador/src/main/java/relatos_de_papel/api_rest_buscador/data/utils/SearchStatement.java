package relatos_de_papel.api_rest_buscador.data.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchStatement {

    private String key;
    private Object value;
    private SearchOperation operation;

}
