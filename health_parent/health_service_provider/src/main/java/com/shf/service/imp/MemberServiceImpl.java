package com.shf.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.shf.dao.MemberDao;
import com.shf.pojo.Member;
import com.shf.service.MemberService;
import com.shf.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass=MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

//    保存会员信息
    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null){
//            使用MD5将明文密码进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

//    根据月份查询会员数量
    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + ".31";
            memberCount.add(memberDao.findMemberCountBeforeDate(date));
        }
        return memberCount;
    }
}
