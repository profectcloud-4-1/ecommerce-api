package profect.group1.goormdotcom.product.controller.external.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import profect.group1.goormdotcom.product.service.ProductService;

@RestController
@RequestMapping("/internal/v1/product")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInternalController {
    private final ProductService productService;

}
