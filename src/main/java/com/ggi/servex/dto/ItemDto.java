package com.ggi.servex.dto;

import com.ggi.servex.entity.OderDetailEntity;
import lombok.Data;

import java.util.List;

@Data
public class ItemDto {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private Boolean isAvailable;

    private List<OderDetailEntity> oderDetails;

}
