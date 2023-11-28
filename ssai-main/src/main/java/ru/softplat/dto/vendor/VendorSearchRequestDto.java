package ru.softplat.dto.vendor;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.softplat.model.vendor.Country;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VendorSearchRequestDto {
    String text;
    List<Country> countries;
}
