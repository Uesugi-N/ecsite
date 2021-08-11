package com.portfolio.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.model.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long>{

}
