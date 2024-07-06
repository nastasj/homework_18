package models;

import lombok.Data;

import java.util.List;

@Data
public class AddBookRequestModel {

    private String userId;
    private List<Isbn> collectionOfIsbns;

    @Data
    public static class Isbn {
        private String isbn;
    }

}
