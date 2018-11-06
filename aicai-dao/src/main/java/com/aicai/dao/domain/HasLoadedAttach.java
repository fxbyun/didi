package com.aicai.dao.domain;

/**
 * 由数据表对应的DO类implements此接口。
 * 很多DO类都通过xxId关联到其他数据表/DO，那在这个DO类中，这个field定义为只有xxId好，还是携带完整DO好?
 * answer:各有各的优缺点，两种方式在遇到困难时都有办法解决。
 * 在hibernate时代大部分是这样关联到对象的定义。
 * 在mybatis时代，也统一为定义为关联到对象，而不是定义为只关联到id.
 * 但数据提供者一般默认不提供完整关联对象，这携带数据里只有id是有效的，
 * 数据接受者(拿到此DO的使用者)可通过hasAttachLoaded()判断携带的关联对象，只有id有效，还是整个DO都是真实的数据表内容。
 * 
 * 数据提供者一般默认不提供完整关联对象，只是new一个空对象后放id进去，这样就少了很多不必要的DB关联查询，节省了DB消耗
 * 如果调用者确实需要一些真实关联对象，可在查询接口中通过 {@link com.aicai.dao.domain.NeedAttachBit NeedAttachBit}
 * 传递给提供者，要求提供者根据xxId从DB或cache中返回真实对象。
 * 
 * 更多资料：
 * http://wiki.zwc.com/wiki/index.php/AicaiDaoRelateObject
 * 
 * @author zhoufeng
 *
 */
public interface HasLoadedAttach {
	public boolean hasAttachLoaded(long bitForPart);
}
