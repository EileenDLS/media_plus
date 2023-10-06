package com.darlingweb.jupiter.entity.request;

import com.darlingweb.jupiter.entity.db.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FavoriteRequestBody {
    @JsonProperty("favorite")
    private Item favoriteItem;

    public Item getFavoriteItem() {
        return favoriteItem;
    }

}
