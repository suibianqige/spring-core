package cn.ucaner.spring.tiny.core.io;

import java.io.File;

/**
* @Package：cn.ucaner.spring.tiny.core.io   
* @ClassName：FileSystemResourceLoader   
* @Description：   <p> 资源的加载：即提供获取Resource的方法 </p>
* @Author： -    
* @Modify By：   
* @ModifyTime：  2018年4月27日
* @Modify marker：   
* @version    V1.0
 */
public class FileSystemResourceLoader extends DefaultResourceLoader {

	/*
	 * @see com.lonton.core.io.ResourceLoader#getResource(java.lang.String)
	 * 默认的DefaultResourceLoader返回的就是FileSystemResource，本来我们只需要在这里调用
	 * 父类的getResource()方法就行了，但是考虑未来可能修改DefaultResourceLoader，我将其在写
	 * 一遍，让其依赖于FileSystemResource而不是DefaultResourceLoader
	 */
	@Override
	public Resource getResource(String path) {
		return new FileSystemResource(path);
	}
	
	//添加通过File对象获取Resource
	public Resource getResource(File file){
		return new FileSystemResource(file);
	}
	
}
