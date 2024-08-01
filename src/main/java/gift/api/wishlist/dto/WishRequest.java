package gift.api.wishlist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import gift.api.member.domain.Member;
import gift.api.product.domain.Product;
import gift.api.wishlist.domain.Wish;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record WishRequest(
    @NotNull(message = "Product id is mandatory")
    @Positive(message = "Product id must be greater than zero")
    Long productId,
    @NotNull(message = "Quantity is mandatory")
    @Positive(message = "Quantity must be greater than zero")
    Integer quantity
) {
    public Wish toEntity(Member member, Product product) {
        return new Wish(member, product, quantity);
    }
}