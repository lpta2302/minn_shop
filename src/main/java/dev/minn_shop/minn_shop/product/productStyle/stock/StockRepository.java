package dev.minn_shop.minn_shop.product.productStyle.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, StockId> {
    @Query("""
    Select st from Stock st
    join Size s on s = st.size
    join ProductStyle ps on ps.id = :productStyleId
    where s.value = :size
    """)
    public Optional<Stock> findBySizeAndStyleId(String size, int productStyleId);

    @Query("""
    Select st from Stock st
    where st.productStyle.id = :productStyleId and
    st.size.value in :sizeValue
    """)
    public List<Stock> findBySizesAndStyleIdIn(int productStyleId, List<String> sizeValue);
}
