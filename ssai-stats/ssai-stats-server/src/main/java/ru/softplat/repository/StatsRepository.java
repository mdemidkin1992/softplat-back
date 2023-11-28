package ru.softplat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.softplat.SellerReportEntry;
import ru.softplat.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Long>,
        QuerydslPredicateExecutor<Stats> {

    @Query("SELECT new ru.softplat.SellerReportEntry( " +
            "s.product.name, sum (s.quantity), sum(s.amount)) " +
            "FROM Stats s " +
            "WHERE s.dateBuy BETWEEN :start AND :end " +
            "GROUP BY s.product.seller.id, s.product.id, s.product.name ")
    List<SellerReportEntry> getAllStats(
            LocalDateTime start,
            LocalDateTime end);

    @Query("SELECT new ru.softplat.SellerReportEntry( " +
            "s.product.name, sum (s.quantity), sum(s.amount)) " +
            "FROM Stats s " +
            "WHERE s.product.seller.id IN :sellerId " +
            "AND s.dateBuy BETWEEN :start AND :end " +
            "GROUP BY s.product.seller.id, s.product.id, s.product.name ")
    List<SellerReportEntry> getStatsByProduct(
            List<Long> sellerId,
            LocalDateTime start,
            LocalDateTime end);
}

