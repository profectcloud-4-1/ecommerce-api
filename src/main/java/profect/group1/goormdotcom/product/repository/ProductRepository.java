package profect.group1.goormdotcom.product.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import profect.group1.goormdotcom.product.repository.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID>{
}
