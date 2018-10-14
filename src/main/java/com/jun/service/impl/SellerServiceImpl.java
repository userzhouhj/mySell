package com.jun.service.impl;

import com.jun.dao.SellerMapper;
import com.jun.domain.Seller;
import com.jun.domain.SellerExample;
import com.jun.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public Seller findByOpenId(String openId) {

        SellerExample sellerExample = new SellerExample();
        SellerExample.Criteria criteria = sellerExample.createCriteria();
        criteria.andSellerOpenidEqualTo(openId);

        List<Seller> sellerList = sellerMapper.selectByExample(sellerExample);
        if(sellerList.size() > 0){
            return sellerList.get(0);
        }

        return null;
    }
}
