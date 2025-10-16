package profect.group1.goormdotcom.brand.service;

import java.util.UUID;
import java.time.LocalDateTime;
import profect.group1.goormdotcom.brand.repository.BrandRepository;
import profect.group1.goormdotcom.brand.domain.Brand;
import profect.group1.goormdotcom.brand.repository.mapper.BrandMapper;
import org.springframework.stereotype.Service;
import profect.group1.goormdotcom.brand.repository.entity.BrandEntity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import profect.group1.goormdotcom.brand.controller.dto.request.BrandListRequestDto;
import profect.group1.goormdotcom.brand.repository.BrandJpaRepository;

@Service
public class BrandService {
    private final BrandRepository repo;
    private final BrandJpaRepository jpaRepo;
    private final BrandMapper mapper;

    public BrandService(BrandRepository repo, BrandJpaRepository jpaRepo, BrandMapper mapper) {
        this.repo = repo;
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    public Brand create(String name, String description, String website, String actor) {
        BrandEntity brandEntity = BrandEntity.builder()
            .name(name)
            .description(description)
            .website(website)
            .createdBy(actor)
            .updatedBy(actor)
            .build();
        return mapper.toDomain(jpaRepo.save(brandEntity));
    }

    public Brand edit(String brandId, String name, String description, String website, String actor) {
        BrandEntity entity = jpaRepo.findById(UUID.fromString(brandId)).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        boolean isChanged = false;
        if (name != null && !name.equals(entity.getName())) {
            entity.setName(name);
            isChanged = true;
        }
        if (description != null && !description.equals(entity.getDescription())) {
            entity.setDescription(description);
            isChanged = true;
        }
        if (website != null && !website.equals(entity.getWebsite())) {
            entity.setWebsite(website);
            isChanged = true;
        }
        if (isChanged) entity.setUpdatedBy(actor);
        return mapper.toDomain(jpaRepo.saveAndFlush(entity));
    }

    public void delete(String brandId, String actor) {
        BrandEntity entity = jpaRepo.findById(UUID.fromString(brandId)).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(actor);
        jpaRepo.saveAndFlush(entity);
    }

    public Brand findById(String brandId) {
        BrandEntity entity = jpaRepo.findById(UUID.fromString(brandId)).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        return mapper.toDomain(entity);
    }

    public List<Brand> searchBrands(BrandListRequestDto body) {
        List<List<String>> filter = null;
        if (body.getFilter() != null && !body.getFilter().isBlank()) {
            filter = Arrays.stream(body.getFilter().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(pair -> pair.split(":", 2))
                .filter(arr -> arr.length == 2 && !arr[0].isBlank())
                .map(arr -> List.of(arr[0].trim(), arr[1].trim()))
                .collect(Collectors.toList());
            if (filter.isEmpty()) {
                filter = null;
            }
        }

        List<BrandEntity> entities = repo.searchBrands(
            body.getSort(), 
            body.getOrder(), 
            body.getSearchField(), 
            body.getKeyword(), 
            body.getPage(), 
            body.getSize(),
            filter
        );
        return entities.stream().map(mapper::toDomain).toList();
    }
}
