package com.shf.service;


import com.shf.pojo.Member;

public interface MemberService {
//    根据手机号查询会员
    public Member findByTelephone(String telephone);

//    注册会员
    public void add(Member member);
}
