package com.jun.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtil {

    /**
     * 分页生成工具
     * @param list 分页的列表
     * @param total 总页数
     * @param page 第几页
     * @param size 每页的大小
     * @param <T>
     * @return
     */
    public static <T> PageInfo getPageInfo(List<T> list,int total,int page,int size ){
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setPages(total);
        pageInfo.setPageNum(page);
        pageInfo.setPageSize(size);

        return pageInfo;
    }

    public static <T> List getList (List<T> list,int page,int size){
        List<T> resultList = list.stream().limit(page * size).collect(Collectors.toList());

        if(page > 1){
            resultList = resultList.stream().skip((page -1) * size).collect(Collectors.toList());
        }
        return resultList;
    }

    public static <T> int getTotal(List<T> list,int size){
        return list.size()%size > 0 ? list.size()/size+1 : list.size()/size;
    }
}
