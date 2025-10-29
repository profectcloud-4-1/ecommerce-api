package profect.group1.goormdotcom.category.controller.external.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

import profect.group1.goormdotcom.category.service.CategoryService;


@RestController
@RequestMapping("/internal/v1/category")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryInternalController {
    private final CategoryService categoryService;
    
}
