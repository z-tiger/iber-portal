package com.iber.portal;

import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.model.base.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @describe 测试基类
 * 
 * @author ruanpeng
 * @date 2014年6月30日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring.xml","classpath:spring/spring-mybatis.xml" })
public class JunitBase {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMapper memberMapper;
    @Before
    public void testbefore(){
        System.out.println("=======================test start===========================");
    }
    @After
    public void testAfter(){
        System.out.println("===========================test end========================");
    }

    @Test
    public void memberListTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("name","黄剑威");
        map.put("offset",1);
        map.put("rows", 10);

        List<Member> members = memberMapper.selectAll(map);
        for (Member member : members) {
            log.info("memberPhone>>>"+member.getPhone());
            log.debug(member.getName());
            log.debug(member.getFaceId());
            System.out.println(member.getFaceId());

        }
    }
    @Test
    public void test(){
       Member member= memberMapper.selectByPrimaryKey(110);
        System.out.println(">>>>member");
        System.out.println("memberName:"+member.getName());
    }



}
