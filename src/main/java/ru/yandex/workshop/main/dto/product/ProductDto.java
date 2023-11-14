package ru.yandex.workshop.main.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.workshop.main.dto.validation.New;
import ru.yandex.workshop.main.model.product.License;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    @NotBlank(groups = {New.class}, message = "Необходимо указать имя продукта")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s№-]+$", message = "Неверные символы в названии товара")
    @Size(min = 2, max = 255, message = "Длина названия продукта должна быть от 2 до 255 символов")
    String name;

    @NotBlank(groups = {New.class}, message = "Необходимо указать описание продукта")
    @Size(min = 2, max = 1024, message = "Длина описания продукта должна быть от 2 до 1024 символов")
    String description;

    @NotBlank(groups = {New.class}, message = "Необходимо указать версию продукта")
    @Size(min = 2, max = 30, message = "Длина наименования версии продукта должна быть от 2 до 30 символов")
    String version;

    @NotNull(groups = {New.class}, message = "Необходимо указать категорию продукта.")
    Long category;

    @NotNull(groups = {New.class}, message = "Необходимо указать тип лицензии.")
    License license;

    @NotNull(groups = {New.class}, message = "Необходимо указать вендора продукта.")
    Long vendor;

    @PositiveOrZero
    @NotNull(groups = {New.class}, message = "Необходимо указать цену продукта.")
    Float price;

    @PositiveOrZero
    @NotNull(groups = {New.class}, message = "Необходимо указать количество продукта.")
    Integer quantity;

    Boolean installation;

    @PositiveOrZero
    @Schema(description = "Обязательно должна быть при создании, если есть возможность купить с установкой")
    Float installationPrice;
}