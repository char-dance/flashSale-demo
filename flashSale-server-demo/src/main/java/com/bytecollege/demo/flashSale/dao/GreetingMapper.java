package com.bytecollege.demo.flashSale.dao;

import org.apache.ibatis.annotations.Select;

//@Mapper
public interface GreetingMapper {

	@Select("select content from greeting where id = #{id}")
	GreetingEntity getContentById(int id);
}
