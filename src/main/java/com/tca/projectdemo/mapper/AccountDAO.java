package com.tca.projectdemo.mapper;

import com.tca.projectdemo.entity.Account;
import com.tca.projectdemo.entity.AccountExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * AccountDAO继承基类
 */
@Mapper
public interface AccountDAO extends MyBatisBaseDao<Account, Integer, AccountExample> {
}