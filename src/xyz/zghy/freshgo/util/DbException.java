package xyz.zghy.freshgo.util;

/**
 * @author ghy
 * @date 2020/7/3 下午7:47
 */

public class DbException extends BaseException {
	public DbException(Throwable ex){
		super("数据库操作错误："+ex.getMessage());
	}
}
