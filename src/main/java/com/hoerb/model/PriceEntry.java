package com.hoerb.model;

import java.time.LocalDateTime;

public record PriceEntry(
        double price,
        LocalDateTime timestamp
) {
}
