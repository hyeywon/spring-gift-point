package gift.api.wishlist.service;

import gift.api.member.domain.Member;
import gift.api.member.repository.MemberRepository;
import gift.api.product.domain.Product;
import gift.api.product.repository.ProductRepository;
import gift.api.wishlist.domain.Wish;
import gift.api.wishlist.dto.WishRequest;
import gift.api.wishlist.dto.WishResponse;
import gift.api.wishlist.repository.WishRepository;
import gift.global.exception.NoSuchEntityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final WishRepository wishRepository;

    public WishService(MemberRepository memberRepository, ProductRepository productRepository,
        WishRepository wishRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.wishRepository = wishRepository;
    }

    public List<WishResponse> getItems(Long memberId, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findAllByMemberId(memberId, createPageableWithProduct(pageable));
        if (wishes.hasContent()) {
            return wishes.getContent()
                    .stream()
                    .map(WishResponse::of)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional
    public void add(Long memberId, WishRequest wishRequest) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoSuchEntityException("member"));
        Product product = productRepository.findById(wishRequest.productId())
            .orElseThrow(() -> new NoSuchEntityException("product"));
        wishRepository.save(wishRequest.toEntity(member, product));
    }

    @Deprecated
    @Transactional
    public void update(Long memberId, WishRequest wishRequest) {
        Wish wish = findWishByMemberIdAndProductId(memberId, wishRequest.productId());
        wish.updateQuantity(wishRequest.quantity());
    }

    @Transactional
    public void delete(Long id) {
        wishRepository.deleteById(id);
    }

    @Transactional
    public void delete(Long memberId, Long productId) {
        Wish wish = findWishByMemberIdAndProductId(memberId, productId);
        wishRepository.delete(wish);
    }

    private Pageable createPageableWithProduct(Pageable pageable) {
        Sort sort = Sort.by(pageable.getSort()
            .get()
            .map(order -> order.withProperty("product." + order.getProperty()))
            .collect(Collectors.toList()));
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    private Wish findWishByMemberIdAndProductId(Long memberId, Long productId) {
        return wishRepository.findByMemberIdAndProductId(memberId, productId)
            .orElseThrow(() -> new NoSuchEntityException("wish"));
    }
}
